package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserCoordinate;
import com.yr.net.app.customer.mapper.UserCoordinateMapper;
import com.yr.net.app.customer.service.IUserCoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserCoordinateServiceImpl extends ServiceImpl<UserCoordinateMapper, UserCoordinate> implements IUserCoordinateService {

    @Autowired
    UserCoordinateMapper userCoordinateMapper;

    @Override
    public UserCoordinate findByUserId(String userId) throws AppException {
        LambdaQueryWrapper<UserCoordinate> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserCoordinate::getUserId,userId);
        queryWrapper.orderByDesc(UserCoordinate::getCreatedTime);
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public List<UserCoordinate> selectByAndCoordina(double minlng, double maxlng, double minlat, double maxlat) throws AppException {
        return userCoordinateMapper.selectNear(minlng,maxlng,minlat,maxlat);
    }
}
