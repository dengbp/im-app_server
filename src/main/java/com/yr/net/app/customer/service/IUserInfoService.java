package com.yr.net.app.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.OnlineQuery;
import com.yr.net.app.customer.dto.UserInfoResponse;
import com.yr.net.app.customer.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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

     IPage<UserInfoResponse> findOnline(OnlineQuery query)throws AppException;
}
