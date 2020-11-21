package com.yr.net.app.shiro;

import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.model.PCSession;
import com.yr.net.app.model.Record;
import com.yr.net.app.pojo.SessionOutput;
import com.yr.net.app.tools.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(AuthDataSource.class);

    static class Count {
        long count;
        long startTime;

        void reset() {
            count = 1;
            startTime = System.currentTimeMillis();
        }

        boolean increaseAndCheck() {
            long now = System.currentTimeMillis();
            if (now - startTime > 86400000) {
                reset();
                return true;
            }
            count++;
            if (count > 10) {
                return false;
            }
            return true;
        }
    }

    private static ConcurrentHashMap<String, Record> mRecords = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, PCSession> mPCSession = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, Count> mCounts = new ConcurrentHashMap<>();

    @Value("${sms.super_code}")
    private String superCode;


    public RestResult.RestCode insertRecord(String mobile, String code) {
        if (!Utils.isMobile(mobile)) {
            LOG.error("Not valid mobile {}", mobile);
            return RestResult.RestCode.ERROR_INVALID_MOBILE;
        }

        Record record = mRecords.get(mobile);
        if (record != null && System.currentTimeMillis() - record.getTimestamp() < 60 * 1000) {
            LOG.error("Send code over frequency. timestamp {}, now {}", record.getTimestamp(), System.currentTimeMillis());
            return RestResult.RestCode.ERROR_SEND_SMS_OVER_FREQUENCY;
        }
        Count count = mCounts.get(mobile);
        if (count == null) {
            count = new Count();
            mCounts.put(mobile, count);
        }

        if (!count.increaseAndCheck()) {
            LOG.error("Count check failure, already send {} messages today", count.count);
            RestResult.RestCode c = RestResult.RestCode.ERROR_SEND_SMS_OVER_FREQUENCY;
            c.msg = "发送给用户 " + mobile + " 超出频率限制";
            return c;
        }
        mRecords.put(mobile, new Record(code, mobile));
        return RestResult.RestCode.SUCCESS;
    }

    public void clearRecode(String mobile) {
        mRecords.remove(mobile);
    }

    public RestResult.RestCode verifyCode(String mobile, String code) {
        /*if (StringUtils.isEmpty(superCode) || !code.equals(superCode)) {
            Record record = mRecords.get(mobile);
            if (record == null || !record.getCode().equals(code)) {
                LOG.error("not empty or not correct");
                return RestResult.RestCode.ERROR_CODE_INCORRECT;
            }
            if (System.currentTimeMillis() - record.getTimestamp() > 5 * 60 * 1000) {
                LOG.error("Code expired. timestamp {}, now {}", record.getTimestamp(), System.currentTimeMillis());
                return RestResult.RestCode.ERROR_CODE_EXPIRED;
            }
        }*/
        return RestResult.RestCode.SUCCESS;
    }

    public PCSession createSession(String userId, String clientId, String token, int platform) {
        PCSession session = new PCSession();
        session.setConfirmedUserId(userId);
        session.setStatus(StringUtils.isEmpty(userId) ? PCSession.PCSessionStatus.Session_Created : PCSession.PCSessionStatus.Session_Scanned);
        session.setClientId(clientId);
        session.setCreateDt(System.currentTimeMillis());
        session.setPlatform(platform);
        session.setDuration(300 * 1000); //300 seconds

        if (StringUtils.isEmpty(token)) {
            token = UUID.randomUUID().toString();
        }

        session.setToken(token);

        mPCSession.put(token, session);
        return session;
    }

    public PCSession getSession(String token, boolean clear) {
        PCSession session = mPCSession.get(token);
        if (clear) {
            mPCSession.remove(token);
        }
        return session;
    }

    public RestResult scanPc(String userId, String token) {
        PCSession session = mPCSession.get(token);
        if (session != null) {
            SessionOutput output = session.toOutput();
            if (output.getExpired() > 0) {
                session.setStatus(PCSession.PCSessionStatus.Session_Scanned);
                session.setConfirmedUserId(userId);
                output.setStatus(PCSession.PCSessionStatus.Session_Scanned);
                output.setUserId(userId);
                return RestResult.ok(output);
            } else {
                return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
            }
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
        }
    }

    public RestResult confirmPc(String userId, String token) {
        PCSession session = mPCSession.get(token);
        if (session != null) {
            SessionOutput output = session.toOutput();
            if (output.getExpired() > 0) {
                session.setStatus(PCSession.PCSessionStatus.Session_Verified);
                output.setStatus(PCSession.PCSessionStatus.Session_Verified);
                session.setConfirmedUserId(userId);
                return RestResult.ok(output);
            } else {
                return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
            }
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
        }
    }

    public RestResult cancelPc(String token) {
        PCSession session = mPCSession.get(token);
        if (session != null) {
            session.setStatus(PCSession.PCSessionStatus.Session_Canceled);
        }

        return RestResult.ok(null);
    }

    public RestResult.RestCode checkPcSession(String token) {
        PCSession session = mPCSession.get(token);
        if (session != null) {
            if (session.getStatus() == PCSession.PCSessionStatus.Session_Verified) {
                //使用用户id获取token
                return RestResult.RestCode.SUCCESS;
            } else {
                if (session.getStatus() == PCSession.PCSessionStatus.Session_Created) {
                    return RestResult.RestCode.ERROR_SESSION_NOT_SCANED;
                } else if (session.getStatus() == PCSession.PCSessionStatus.Session_Canceled) {
                    return RestResult.RestCode.ERROR_SESSION_CANCELED;
                } else {
                    return RestResult.RestCode.ERROR_SESSION_NOT_VERIFIED;
                }
            }
        } else {
            return RestResult.RestCode.ERROR_SESSION_EXPIRED;
        }
    }

    public String getUserId(String token, boolean clear) {
        PCSession session = mPCSession.get(token);
        if (clear) {
            mPCSession.remove(token);
        }

        if (session != null) {
            return session.getConfirmedUserId();
        }

        return null;
    }
}
