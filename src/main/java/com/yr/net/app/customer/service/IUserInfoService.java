package com.yr.net.app.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.net.app.common.entity.QueryRequestPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.*;
import com.yr.net.app.customer.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.net.app.pojo.Position;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserInfoService extends IService<UserInfo> {


    /**
     * Description todo
     * @param query
     * @return java.util.List<com.yr.net.app.customer.dto.UserInfoResponse>
     * @throws AppException AppException
     * @Author dengbp
     * @Date 14:20 2020-11-11
     **/

     List<UserBaseInfoResponseDto> findOnline(OnlineRequestDto query)throws AppException;

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
     * Description 完善基本信息
     * @param baseInfo
     * @throws AppException AppException
     * @return void
     * @Author dengbp
     * @Date 12:57 PM 2/23/21
     **/
    void updateByUserId(AddBaseInfoRequestDto baseInfo)throws AppException;

    /**
     * Description 找当前用户的附近人
     * @param requestPage
     * @param userId
 * @param position
     * @throws AppException AppException
     * @return java.util.List<com.yr.net.app.customer.dto.NearUserResponseDto>
     * @Author dengbp
     * @Date 03:35 2020-11-22
     **/
    List<NearUserResponseDto> findNear(QueryRequestPage requestPage,String userId, Position position) throws AppException;

    /**
     * Description 个人头像设置
     * @param file
     * @return java.lang.String 头像url
     * @Author dengbp
     * @Date 23:59 2020-12-04
     **/
    String updateIcon(MultipartFile file)throws AppException;

    /**
     * Description 获取用户基本信息
     * @param requestDto
     * @throws AppException
     * @return com.yr.net.app.customer.dto.UserBaseInfoResponseDto
     * @Author dengbp
     * @Date 00:28 2020-12-05
     **/
    UserBaseInfoResponseDto getUserInfo(UserBaseInfoRequestDto requestDto)throws AppException;

    UserInfo getRandOne();

}
