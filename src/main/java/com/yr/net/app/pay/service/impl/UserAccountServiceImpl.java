package com.yr.net.app.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.pay.entity.UserAccount;
import com.yr.net.app.pay.mapper.UserAccountMapper;
import com.yr.net.app.pay.service.IUserAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

    @Override
    public List<UserAccount> ranking() throws AppException {
        return this.list(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getState,UserAccount.NORMAL).orderByDesc(UserAccount::getBalance).last("limit 0 , 10"));

    }
}
