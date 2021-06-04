package com.yr.net.app.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.log.service.IUserExchangeLogService;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.pay.entity.UserAccount;
import com.yr.net.app.pay.entity.UserOrder;
import com.yr.net.app.pay.mapper.UserAccountMapper;
import com.yr.net.app.pay.service.IUserAccountService;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public List<UserAccount> ranking() throws AppException {
        return this.list(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getState,UserAccount.NORMAL).orderByDesc(UserAccount::getBalance).last("limit 0 , 10"));

    }

    @Override
    public UserAccount getBalance() throws AppException {
        return getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUserId,AppUtil.getCurrentUserId()));
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
            userExchangeLogService.insert(reqDto);
        }
        UserAccount receiveAccount = getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUserId,receiveUserId));
        if (receiveAccount==null){
            save(build(receiveUserId,reqDto.getAmount()));
            return true;
        }else {
            receiveAccount.setBalance(receiveAccount.getBalance()+reqDto.getAmount());
            updateById(payAccount);
            return true;
        }
    }
}
