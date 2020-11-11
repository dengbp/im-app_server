package com.yr.net.app.customer.service.impl;

import com.yr.net.app.customer.entity.UserAccount;
import com.yr.net.app.customer.mapper.UserAccountMapper;
import com.yr.net.app.customer.service.IUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

}
