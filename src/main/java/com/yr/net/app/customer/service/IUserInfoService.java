package com.yr.net.app.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.NearUserResponseDto;
import com.yr.net.app.customer.dto.OnlineRequestDto;
import com.yr.net.app.customer.dto.UserInfoResponseDto;
import com.yr.net.app.customer.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.net.app.pojo.Position;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserInfoService extends IService<UserInfo> {


    /**
     * Description todo
     * @param query
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.yr.net.app.customer.dto.UserInfoResponse>
     * @throws AppException AppException
     * @Author dengbp
     * @Date 14:20 2020-11-11
     **/

     IPage<UserInfoResponseDto> findOnline(OnlineRequestDto query)throws AppException;

     /**
      * Description todo
      * @param userId
      * @return com.yr.net.app.customer.entity.UserInfo
      * @throws AppException AppException
      * @Author dengbp
      * @Date 02:32 2020-11-22
      **/

    UserInfo getByUserId(String userId)throws AppException;

    /**
     * Description 找当前用户的附近人
     * @param userId
 * @param position
     * @throws AppException AppException
     * @return java.util.List<com.yr.net.app.customer.dto.NearUserResponseDto>
     * @Author dengbp
     * @Date 03:35 2020-11-22
     **/
    List<NearUserResponseDto> findNear(String userId, Position position) throws AppException;
}
