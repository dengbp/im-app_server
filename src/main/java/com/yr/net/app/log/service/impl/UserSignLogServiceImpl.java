package com.yr.net.app.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.entity.UserSignLog;
import com.yr.net.app.log.mapper.UserSignLogMapper;
import com.yr.net.app.log.service.IUserSignLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserSignLogServiceImpl extends ServiceImpl<UserSignLogMapper, UserSignLog> implements IUserSignLogService {

    @Override
    public List<UserSignLog> searchByUserId(String userId) throws AppException {
        return this.list(new LambdaQueryWrapper<UserSignLog>().eq(UserSignLog::getUserId,userId).last("limit 0 , 50"));
    }
}
