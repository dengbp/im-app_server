package com.yr.net.app.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.OnlineRequestDto;
import com.yr.net.app.customer.dto.UserInfoResponseDto;
import com.yr.net.app.customer.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
