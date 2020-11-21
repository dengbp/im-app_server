package com.yr.net.app.customer.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserCoordinate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dengbp
 */
public interface IUserCoordinateService extends IService<UserCoordinate> {


    /**
     * Description 查用户最新坐标
     * @param userId
     * @throws AppException AppException
     * @return com.yr.net.app.customer.entity.UserCoordinate
     * @Author dengbp
     * @Date 03:43 2020-11-22
     **/

    UserCoordinate findByUserId(String userId)throws AppException;
}
