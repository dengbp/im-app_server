package com.yr.net.app.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.log.entity.UserExchangeLog;
import com.yr.net.app.log.service.IUserExchangeLogService;
import com.yr.net.app.pay.controller.enums.ExchangeItem;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.pay.dto.ExchangeLogRespDto;
import com.yr.net.app.pay.entity.UserAccount;
import com.yr.net.app.pay.entity.UserOrder;
import com.yr.net.app.pay.mapper.UserAccountMapper;
import com.yr.net.app.pay.service.IUserAccountService;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

    @Autowired
    IUserInfoService userInfoService;
    @Resource
    private IUserExchangeLogService userExchangeLogService;

    @Resource
    private WxPayService wxPayService;

    @Override
    public List<UserAccount> ranking() throws AppException {
        return this.list(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getState,UserAccount.NORMAL).orderByDesc(UserAccount::getBalance).last("limit 0 , 10"));

    }

    @Override
    public UserAccount getBalance() throws AppException {
        UserAccount account = getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUserId,AppUtil.getCurrentUserId()));
        return account==null?new UserAccount():account;
    }

    @Override
    public List<ExchangeLogRespDto> transacLog() throws AppException {
        List<UserExchangeLog> logs = userExchangeLogService.list(new LambdaQueryWrapper<UserExchangeLog>()
                .eq(UserExchangeLog::getPayUserId,AppUtil.getCurrentUserId())
                .eq(UserExchangeLog::getExchangeState,UserExchangeLog.SUCCESS)
                .orderByDesc(UserExchangeLog::getExchangeTime));
        if (logs == null){
            return new ArrayList<>();
        }
        List<ExchangeLogRespDto> respDtos = new ArrayList<>();
        logs.forEach(l->{
            ExchangeLogRespDto respDto = new ExchangeLogRespDto();
            if (l.getExchangeType()==UserExchangeLog.INCOME_TYPE){
                respDto.setAmount("+" + (l.getExchangeAmount()/100));
            }
            if (l.getExchangeType()==UserExchangeLog.PAY_TYPE){
                respDto.setAmount("-" + (l.getExchangeAmount()/100));
            }
            respDto.setTime(DateUtil.getDateFormat(l.getExchangeTime(),DateUtil.FULL_TIME_SPLIT_PATTERN));
            respDto.setItemName(l.getExchangeItem());
            if (l.getExchangeItemType() != ExchangeItem.recharge.getType()){
                respDto.setItemName(l.getExchangeItem() + "(收款用户：" + l.getReceiveUserId() + ")");
            }
            respDtos.add(respDto);
        });
        return respDtos;
    }

    @Override
    public void updateByUserId(String userId, Integer totalFee) throws AppException {
        UserAccount userAccount = getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUserId,userId));
        if (userAccount != null){
            userAccount.setBalance(totalFee + userAccount.getBalance());
            userAccount.setUpdateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
            updateById(userAccount);
            return;
        }
        save(build(userId,totalFee));
    }

    private UserAccount build(String userId, Integer totalFee){
        UserAccount userAccount = new UserAccount();
        userAccount.setBalance(totalFee);
        userAccount.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        userAccount.setState(UserAccount.NORMAL);
        userAccount.setUserId(userId);
        UserInfo userInfo = userInfoService.getByUserId(userId);
        userAccount.setIcon(userInfo.getIcon());
        userAccount.setUserName(userInfo.getUserName());
        return userAccount;
    }

    @Override
    public boolean pay(ExchangeLogReqDto reqDto) throws AppException {
        String payUserId = AppUtil.getCurrentUserId();
        String receiveUserId = reqDto.getReceiveUser();
        UserAccount payAccount = getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUserId,payUserId));
        if (payAccount == null){
            return false;
        }
        if (payAccount.getBalance() > reqDto.getAmount()){
            payAccount.setBalance(payAccount.getBalance() - reqDto.getAmount());
            updateById(payAccount);
            reqDto.setPayType(UserExchangeLog.PAY_TYPE);
            reqDto.setPayUserId(AppUtil.getCurrentUserId());
            userExchangeLogService.insert(reqDto);
        }
        /** -1代表轨迹收费项，钱只扣，不转给收款用户 */
        if (reqDto.getItemId()==-1){
            reqDto.setAmount(0);
        }
        UserAccount receiveAccount = getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUserId,receiveUserId));
        if (receiveAccount==null){
            save(build(receiveUserId,reqDto.getAmount()));
            return true;
        }else {
            receiveAccount.setBalance(receiveAccount.getBalance()+reqDto.getAmount());
            updateById(receiveAccount);
            return true;
        }
    }
}
