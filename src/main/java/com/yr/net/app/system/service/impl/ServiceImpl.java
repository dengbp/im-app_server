package com.yr.net.app.system.service.impl;


import com.yr.net.app.common.constant.SexConstant;
import com.yr.net.app.configure.AppProperties;
import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.model.PCSession;
import com.yr.net.app.pojo.*;
import com.yr.net.app.shiro.AuthDataSource;
import com.yr.net.app.shiro.TokenAuthenticationToken;
import com.yr.net.app.sms.SmsService;
import com.yr.net.app.system.service.Service;
import com.yr.net.app.tools.DateUtil;
import com.yr.net.app.tools.RateLimiter;
import com.yr.net.app.tools.ShortUUIDGenerator;
import com.yr.net.app.tools.Utils;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.*;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.sdk.*;
import cn.wildfirechat.sdk.model.IMResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static com.yr.net.app.model.PCSession.PCSessionStatus.*;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    private AppProperties mIMConfig;


    @Value("${sms.super_code}")
    private String superCode;

    @Value("${logs.user_logs_path}")
    private String userLogPath;

    @Autowired
    private ShortUUIDGenerator userNameGenerator;

    @Autowired
    private AuthDataSource authDataSource;

    private RateLimiter rateLimiter;

    @Value("${wfc.compat_pc_quick_login}")
    protected boolean compatPcQuickLogin;

    @Autowired
    IUserInfoService userInfoService;

    private ConcurrentHashMap<String, Boolean> supportPCQuickLoginUsers = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        ChatConfig.initAdmin(mIMConfig.getAdmin_url(), mIMConfig.getAdmin_secret());
        rateLimiter = new RateLimiter(60, 200);
    }

    private String getIp() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    @Override
    public RestResult sendCode(String mobile) {
        String remoteIp = getIp();
        LOG.info("request send sms from {}", remoteIp);

        //判断当前IP发送是否超频。
        //另外 cn.wildfirechat.app.shiro.AuthDataSource.Count 会对用户发送消息限频
        if (!rateLimiter.isGranted(remoteIp)) {
            return RestResult.result(RestResult.RestCode.ERROR_SEND_SMS_OVER_FREQUENCY.code, "IP " + remoteIp + " 请求短信超频", null);
        }

        try {
            String code = Utils.getRandomCode(4);
            RestResult.RestCode restCode = authDataSource.insertRecord(mobile, code);

            if (restCode != RestResult.RestCode.SUCCESS) {
                return RestResult.error(restCode);
            }


            restCode = smsService.sendCode(mobile, code);
            if (restCode == RestResult.RestCode.SUCCESS) {
                return RestResult.ok(restCode);
            } else {
                authDataSource.clearRecode(mobile);
                return RestResult.error(restCode);
            }
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
            authDataSource.clearRecode(mobile);
        }
        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
    }

    @Override
    public RestResult login(String mobile, String code, String clientId, int platform) {
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(mobile, code);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        } catch (IncorrectCredentialsException ice) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        } catch (LockedAccountException lae) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        } catch (ExcessiveAttemptsException eae) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        } catch (AuthenticationException ae) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        }
        if (subject.isAuthenticated()) {
            long timeout = subject.getSession().getTimeout();
            LOG.info("Login success " + timeout);
        } else {
            token.clear();
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        }


        try {
            //使用电话号码查询用户信息。
            IMResult<InputOutputUserInfo> userResult = UserAdmin.getUserByMobile(mobile);

            //如果用户信息不存在，创建用户
            InputOutputUserInfo user;
            boolean isNewUser = false;
            if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_EXIST) {
                LOG.info("User not exist, try to create");

                //获取用户名。如果用的是shortUUID生成器，是有极小概率会重复的，所以需要去检查是否已经存在相同的userName。
                //ShortUUIDGenerator内的main函数有测试代码，可以观察一下碰撞的概率，这个重复是理论上的，作者测试了几千万次次都没有产生碰撞。
                //另外由于并发的问题，也有同时生成相同的id并同时去检查的并同时通过的情况，但这种情况概率极低，可以忽略不计。
                String userName;
                int tryCount = 0;
                do {
                    tryCount++;
                    userName = userNameGenerator.getUserName(mobile);
                    if (tryCount > 10) {
                        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                    }
                } while (!isUsernameAvailable(userName));


                user = new InputOutputUserInfo();
                user.setName(userName);
                if (mIMConfig.isUse_random_name()) {
                    String displayName = "用户" + (int) (Math.random() * 10000);
                    user.setDisplayName(displayName);
                } else {
                    user.setDisplayName(mobile);
                }
                user.setMobile(mobile);
                IMResult<OutputCreateUser> userIdResult = UserAdmin.createUser(user);
                if (userIdResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    user.setUserId(userIdResult.getResult().getUserId());
                    isNewUser = true;
                } else {
                    LOG.info("Create user failure {}", userIdResult.code);
                    return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                }


            } else if (userResult.getCode() != 0) {
                LOG.error("Get user failure {}", userResult.code);
                return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
            } else {
                user = userResult.getResult();
            }

            //使用用户id获取token
            IMResult<OutputGetIMTokenData> tokenResult = UserAdmin.getUserToken(user.getUserId(), clientId, platform);
            if (tokenResult.getErrorCode() != ErrorCode.ERROR_CODE_SUCCESS) {
                LOG.error("Get user failure {}", tokenResult.code);
                return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
            }

            subject.getSession().setAttribute("userId", user.getUserId());

            //返回用户id，token和是否新建
            LoginResponse response = new LoginResponse();
            response.setUserId(user.getUserId());
            response.setToken(tokenResult.getResult().getToken());
            response.setRegister(isNewUser);
            UserInfo userInfo = userInfoService.getByUserId(user.getUserId());
            response.setIsNewUser(1);
            if (isNewUser) {
                response.setIsNewUser(0);
                if (userInfo == null) {
                    this.userConvert(user,userInfo);
                    userInfoService.save(userInfo);
                }
                if (!StringUtils.isEmpty(mIMConfig.getWelcome_for_new_user())) {
                    sendTextMessage("admin", user.getUserId(), mIMConfig.getWelcome_for_new_user());
                }

                if (mIMConfig.isNew_user_robot_friend() && !StringUtils.isEmpty(mIMConfig.getRobot_friend_id())) {
                    ;
                    RelationAdmin.setUserFriend(user.getUserId(), mIMConfig.getRobot_friend_id(), true, null);
                    if (!StringUtils.isEmpty(mIMConfig.getRobot_welcome())) {
                        sendTextMessage(mIMConfig.getRobot_friend_id(), user.getUserId(), mIMConfig.getRobot_welcome());
                    }
                }
            } else {
                if (!StringUtils.isEmpty(mIMConfig.getWelcome_for_back_user())) {
                    sendTextMessage("admin", user.getUserId(), mIMConfig.getWelcome_for_back_user());
                }
            }

            subject.getSession().setAttribute("userInfo", userInfo);

            return RestResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Exception happens {}", e);
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }
    }

    private ServiceImpl userConvert(InputOutputUserInfo source,UserInfo target){
        target.setUserId(source.getUserId());
        target.setUserName(source.getDisplayName());
        target.setPassword(source.getPassword());
        target.setPhone(source.getMobile());
        /** 先暂时设置为 女性，生日为当前时间 */
        target.setSex(SexConstant.Female.getSex());
        target.setBirthday(Integer.parseInt(DateUtil.current_yyyyMMdd()));
        return this;
    }

    private boolean isUsernameAvailable(String username) {
        try {
            IMResult<InputOutputUserInfo> existUser = UserAdmin.getUserByName(username);
            if (existUser.code == ErrorCode.ERROR_CODE_NOT_EXIST.code) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sendPcLoginRequestMessage(String fromUser, String toUser, int platform, String token) {
        Conversation conversation = new Conversation();
        conversation.setTarget(toUser);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(94);
        if (platform == ProtoConstants.Platform.Platform_WEB) {
            payload.setPushContent("Web端登录请求");
        } else if (platform == ProtoConstants.Platform.Platform_OSX) {
            payload.setPushContent("Mac 端登录请求");
        } else if(platform == ProtoConstants.Platform.Platform_LINUX) {
            payload.setPushContent("Linux 端登录请求");
        } else if(platform == ProtoConstants.Platform.Platform_Windows) {
            payload.setPushContent("Windows 端登录请求");
        } else {
            payload.setPushContent("PC 端登录请求");
        }

        payload.setExpireDuration(3 * 60 * 1000);
        payload.setPersistFlag(ProtoConstants.PersistFlag.Not_Persist);
        JSONObject data = new JSONObject();
        data.put("p", platform);
        data.put("t", token);
        payload.setBase64edData(Base64Utils.encodeToString(data.toString().getBytes()));

        try {
            IMResult<SendMessageResult> resultSendMessage = MessageAdmin.sendMessage(fromUser, conversation, payload);
            if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                LOG.info("send message success");
            } else {
                LOG.error("send message error {}", resultSendMessage != null ? resultSendMessage.getErrorCode().code : "unknown");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("send message error {}", e.getLocalizedMessage());
        }

    }

    private void sendTextMessage(String fromUser, String toUser, String text) {
        Conversation conversation = new Conversation();
        conversation.setTarget(toUser);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(1);
        payload.setSearchableContent(text);


        try {
            IMResult<SendMessageResult> resultSendMessage = MessageAdmin.sendMessage(fromUser, conversation, payload);
            if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                LOG.info("send message success");
            } else {
                LOG.error("send message error {}", resultSendMessage != null ? resultSendMessage.getErrorCode().code : "unknown");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("send message error {}", e.getLocalizedMessage());
        }

    }


    @Override
    public RestResult createPcSession(CreateSessionRequest request) {
        String userId = request.getUserId();
        // pc端切换登录用户时，还会带上之前的cookie，通过请求里面是否带有userId来判断是否是切换到新用户
        if(request.getFlag() == 1 && !StringUtils.isEmpty(userId)){
            Subject subject = SecurityUtils.getSubject();
            userId = (String) subject.getSession().getAttribute("userId");
        }

        if (compatPcQuickLogin) {
            if (userId != null && supportPCQuickLoginUsers.get(userId) == null) {
                userId = null;
            }
        }

        PCSession session = authDataSource.createSession(userId, request.getClientId(), request.getToken(), request.getPlatform());
        if (userId != null) {
            sendPcLoginRequestMessage("admin", userId, request.getPlatform(), session.getToken());
        }
        SessionOutput output = session.toOutput();
        return RestResult.ok(output);
    }

    @Override
    public RestResult loginWithSession(String token) {
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        // comment start 如果确定登录不成功，就不通过Shiro尝试登录了
        TokenAuthenticationToken tt = new TokenAuthenticationToken(token);
        PCSession session = authDataSource.getSession(token, false);
        if (session == null) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_EXPIRED);
        } else if (session.getStatus() == Session_Created) {
            return RestResult.error(RestResult.RestCode.ERROR_SESSION_NOT_SCANED);
        } else if (session.getStatus() == Session_Scanned) {
            session.setStatus(Session_Pre_Verify);
            LoginResponse response = new LoginResponse();
            try {
                IMResult<InputOutputUserInfo> result = UserAdmin.getUserByUserId(session.getConfirmedUserId());
                if (result.getCode() == 0) {
                    response.setUserName(result.getResult().getDisplayName());
                    response.setPortrait(result.getResult().getPortrait());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return RestResult.result(RestResult.RestCode.ERROR_SESSION_NOT_VERIFIED, response);
        } else if (session.getStatus() == Session_Pre_Verify) {
            return RestResult.error(RestResult.RestCode.ERROR_SESSION_NOT_VERIFIED);
        } else if(session.getStatus() == Session_Canceled) {
            return RestResult.error(RestResult.RestCode.ERROR_SESSION_CANCELED);
        }
        // comment end

        // 执行认证登陆
        // comment start 由于PC端登录之后，可以请求app server创建群公告等。为了保证安全, PC端登录时，也需要在app server创建session。
        try {
            subject.login(tt);
        } catch (UnknownAccountException uae) {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        } catch (IncorrectCredentialsException ice) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        } catch (LockedAccountException lae) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        } catch (ExcessiveAttemptsException eae) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        } catch (AuthenticationException ae) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        }
        if (subject.isAuthenticated()) {
            LOG.info("Login success");
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        }
        // comment end

        session = authDataSource.getSession(token, true);
        if (session == null) {
            subject.logout();
            return RestResult.error(RestResult.RestCode.ERROR_CODE_EXPIRED);
        }
        subject.getSession().setAttribute("userId", session.getConfirmedUserId());

        try {
            //使用用户id获取token
            IMResult<OutputGetIMTokenData> tokenResult = UserAdmin.getUserToken(session.getConfirmedUserId(), session.getClientId(), session.getPlatform());
            if (tokenResult.getCode() != 0) {
                LOG.error("Get user failure {}", tokenResult.code);
                subject.logout();
                return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
            }
            //返回用户id，token和是否新建
            LoginResponse response = new LoginResponse();
            response.setUserId(session.getConfirmedUserId());
            response.setToken(tokenResult.getResult().getToken());
            return RestResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            subject.logout();
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }
    }

    @Override
    public RestResult scanPc(String token) {
        Subject subject = SecurityUtils.getSubject();
        String userId = (String) subject.getSession().getAttribute("userId");
        return authDataSource.scanPc(userId, token);
    }

    @Override
    public RestResult confirmPc(ConfirmSessionRequest request) {
        Subject subject = SecurityUtils.getSubject();
        String userId = (String) subject.getSession().getAttribute("userId");
        if (compatPcQuickLogin) {
            if (request.getQuick_login() > 0) {
                supportPCQuickLoginUsers.put(userId, true);
            } else {
                supportPCQuickLoginUsers.remove(userId);
            }
        }

        return authDataSource.confirmPc(userId, request.getToken());
    }

    @Override
    public RestResult cancelPc(CancelSessionRequest request) {
        return authDataSource.cancelPc(request.getToken());
    }

    @Override
    public RestResult changeName(String newName) {
        Subject subject = SecurityUtils.getSubject();
        String userId = (String) subject.getSession().getAttribute("userId");
        try {
            IMResult<InputOutputUserInfo> existUser = UserAdmin.getUserByName(newName);
            if (existUser != null) {
                if (existUser.code == ErrorCode.ERROR_CODE_SUCCESS.code) {
                    if (userId.equals(existUser.getResult().getUserId())) {
                        return RestResult.ok(null);
                    } else {
                        return RestResult.error(RestResult.RestCode.ERROR_USER_NAME_ALREADY_EXIST);
                    }
                } else if (existUser.code == ErrorCode.ERROR_CODE_NOT_EXIST.code) {
                    existUser = UserAdmin.getUserByUserId(userId);
                    if (existUser == null || existUser.code != ErrorCode.ERROR_CODE_SUCCESS.code || existUser.getResult() == null) {
                        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                    }

                    existUser.getResult().setName(newName);
                    IMResult<OutputCreateUser> createUser = UserAdmin.createUser(existUser.getResult());
                    if (createUser.code == ErrorCode.ERROR_CODE_SUCCESS.code) {
                        return RestResult.ok(null);
                    } else {
                        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                    }
                } else {
                    return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                }
            } else {
                return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }
    }



    @Override
    public RestResult saveUserLogs(String userId, MultipartFile file) {
        File localFile = new File(userLogPath, userId + "_" + file.getOriginalFilename());

        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }

        return RestResult.ok(null);
    }

    @Override
    public RestResult addDevice(InputCreateDevice createDevice) {
        try {
            Subject subject = SecurityUtils.getSubject();
            String userId = (String) subject.getSession().getAttribute("userId");

            if (!StringUtils.isEmpty(createDevice.getDeviceId())) {
                IMResult<OutputDevice> outputDeviceIMResult = UserAdmin.getDevice(createDevice.getDeviceId());
                if (outputDeviceIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    if (!createDevice.getOwners().contains(userId)) {
                        return RestResult.error(RestResult.RestCode.ERROR_NO_RIGHT);
                    }
                } else if (outputDeviceIMResult.getErrorCode() != ErrorCode.ERROR_CODE_NOT_EXIST) {
                    return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                }
            }

            IMResult<OutputCreateDevice> result = UserAdmin.createOrUpdateDevice(createDevice);
            if (result != null && result.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return RestResult.ok(result.getResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
    }

    @Override
    public RestResult getDeviceList() {
        Subject subject = SecurityUtils.getSubject();
        String userId = (String) subject.getSession().getAttribute("userId");
        try {
            IMResult<OutputDeviceList> imResult = UserAdmin.getUserDevices(userId);
            if (imResult != null && imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return RestResult.ok(imResult.getResult().getDevices());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
    }


    @Override
    public RestResult delDevice(InputCreateDevice createDevice) {
        try {
            Subject subject = SecurityUtils.getSubject();
            String userId = (String) subject.getSession().getAttribute("userId");

            if (!StringUtils.isEmpty(createDevice.getDeviceId())) {
                IMResult<OutputDevice> outputDeviceIMResult = UserAdmin.getDevice(createDevice.getDeviceId());
                if (outputDeviceIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    if (outputDeviceIMResult.getResult().getOwners().contains(userId)) {
                        createDevice.setExtra(outputDeviceIMResult.getResult().getExtra());
                        outputDeviceIMResult.getResult().getOwners().remove(userId);
                        createDevice.setOwners(outputDeviceIMResult.getResult().getOwners());
                        IMResult<OutputCreateDevice> result = UserAdmin.createOrUpdateDevice(createDevice);
                        if (result != null && result.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                            return RestResult.ok(result.getResult());
                        } else {
                            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                        }
                    } else {
                        return RestResult.error(RestResult.RestCode.ERROR_NO_RIGHT);
                    }
                } else {
                    if (outputDeviceIMResult.getErrorCode() != ErrorCode.ERROR_CODE_NOT_EXIST) {
                        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                    } else {
                        return RestResult.error(RestResult.RestCode.ERROR_NOT_EXIST);
                    }
                }
            } else {
                return RestResult.error(RestResult.RestCode.ERROR_INVALID_PARAMETER);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
    }
}
