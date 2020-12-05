package com.yr.net.app.customer.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserLoveRequestSetDto;
import com.yr.net.app.customer.entity.UserLoveRequest;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * @author dengbp
 */
public interface IUserLoveRequestService extends IService<UserLoveRequest> {

    /**
     * Description 根据用户id查征友要求
     * @param requestDto
     * @throws AppException
     * @return com.yr.net.app.customer.entity.UserLoveRequest
     * @Author dengbp
     * @Date 01:45 2020-12-06
     **/

    UserLoveRequest findByUserId(UserBaseInfoRequestDto requestDto)throws AppException;

    /**
     * Description 征友要求设置
     * @param userId
 * @param requestSetDto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 03:45 2020-12-06
     **/

    void setLoveRequest(String userId, UserLoveRequestSetDto requestSetDto)throws AppException;
}
