package com.yr.net.app.log.service.impl;

import com.yr.net.app.log.entity.UserSignLog;
import com.yr.net.app.log.mapper.UserSignLogMapper;
import com.yr.net.app.log.service.IUserSignLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class UserSignLogServiceImpl extends ServiceImpl<UserSignLogMapper, UserSignLog> implements IUserSignLogService {

}
