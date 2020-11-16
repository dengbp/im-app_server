package com.yr.net.app.customer.service.impl;

import com.yr.net.app.customer.entity.ShiroSession;
import com.yr.net.app.customer.mapper.ShiroSessionMapper;
import com.yr.net.app.customer.service.IShiroSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class ShiroSessionServiceImpl extends ServiceImpl<ShiroSessionMapper, ShiroSession> implements IShiroSessionService {

}
