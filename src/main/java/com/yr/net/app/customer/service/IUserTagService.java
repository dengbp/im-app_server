package com.yr.net.app.customer.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.UserTagRequestDto;
import com.yr.net.app.customer.entity.UserTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserTagService extends IService<UserTag> {

    /**
     * Description 取用户兴趣爱好信息
     * @param userId
     * @throws AppException
     * @return java.util.List<com.yr.net.app.customer.entity.UserTag>
     * @Author dengbp
     * @Date 02:16 2020-12-06
     **/

    List<UserTag>  findByUserId(String userId)throws AppException;

    /**
     * Description 设置用户标签(目前是兴趣爱好)
     * @param userId
 * @param requestDto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 04:13 2020-12-06
     **/
    void setUserTag(String userId, UserTagRequestDto requestDto)throws AppException;

}
