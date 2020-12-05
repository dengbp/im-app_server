package com.yr.net.app.customer.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserDetailSetRequestDto;
import com.yr.net.app.customer.entity.UserInfoDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dengbp
 */
public interface IUserInfoDetailService extends IService<UserInfoDetail> {

    /**
     * Description 获取用户详细信息
     * @param requestDto
     * @throws AppException
     * @return com.yr.net.app.customer.entity.UserInfoDetail
     * @Author dengbp
     * @Date 01:16 2020-12-06
     **/

    UserInfoDetail findUserDetail(UserBaseInfoRequestDto requestDto)throws AppException;

    /**
     * Description 内心独白设置
     * @param userId
 * @param motto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 02:43 2020-12-06
     **/
    void updateMotto(String userId,String motto)throws AppException;

    /**
     * Description 更新详细资料
     *   @param userId
     * @param detailSetRequestDto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 03:00 2020-12-06
     **/
    void updateDetail(String userId,UserDetailSetRequestDto detailSetRequestDto)throws AppException;

}
