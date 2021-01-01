package com.yr.net.app.customer.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserCoordinate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * Description 获取位置在正方形内的所有用户
 * @param minlng
 * @param maxlng
 * @param minlat
 * @param maxlat
     * @throws AppException
     * @return com.yr.net.app.customer.entity.UserCoordinate
     * @Author dengbp
     * @Date 8:46 PM 12/26/20
     **/

    List<UserCoordinate> selectByAndCoordina(double minlng, double maxlng, double minlat, double maxlat)throws AppException;
}
