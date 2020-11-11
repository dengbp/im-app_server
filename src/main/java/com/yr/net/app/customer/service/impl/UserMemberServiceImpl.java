package com.yr.net.app.customer.service.impl;

import com.yr.net.app.customer.entity.UserMember;
import com.yr.net.app.customer.mapper.UserMemberMapper;
import com.yr.net.app.customer.service.IUserMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class UserMemberServiceImpl extends ServiceImpl<UserMemberMapper, UserMember> implements IUserMemberService {

}
