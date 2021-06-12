package com.yr.net.app.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.entity.UserWithdrawalsLog;
import com.yr.net.app.log.mapper.UserWithdrawalsLogMapper;
import com.yr.net.app.log.service.IUserWithdrawalsLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.pay.dto.WithdrawReqDto;
import com.yr.net.app.pay.entity.UserAccount;
import com.yr.net.app.pay.service.IOrderSeqService;
import com.yr.net.app.pay.service.IUserAccountService;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dengbp
 */
@Service
public class UserWithdrawalsLogServiceImpl extends ServiceImpl<UserWithdrawalsLogMapper, UserWithdrawalsLog> implements IUserWithdrawalsLogService {

    @Resource
    private IUserAccountService userAccountService;
    @Resource
    private IOrderSeqService orderSeqService;


    @Override
    public boolean withdraw(WithdrawReqDto dto) throws AppException {
        UserAccount account = userAccountService.getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getState,UserAccount.NORMAL).eq(UserAccount::getUserId, AppUtil.getCurrentUserId()));
        if (account==null){
            return false;
        }
        if (account.getBalance()<dto.getAmount()){
            return false;
        }
        account.setBalance(account.getBalance() - dto.getAmount());
        userAccountService.updateById(account);
        this.save(UserWithdrawalsLog.build(DateUtil.current_yyyyMMddHHmmss().concat(orderSeqService.getSeq()+""),dto.getAccount(),dto.getAmount()));
        return true;
    }
}
