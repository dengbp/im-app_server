package com.yr.net.app.moments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.dto.AddMomentDto;
import com.yr.net.app.moments.dto.UserMomentsRespDto;
import com.yr.net.app.moments.dto.UserMomentsReqDto;
import com.yr.net.app.moments.entity.UserMoments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户动态接口
 *
 * @author dengbp
 */
public interface IUserMomentsService extends IService<UserMoments> {

    /**
     * Description 发布动态
     * @param addMomentDto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 1:57 PM 2/24/21
     **/

    void add(AddMomentDto addMomentDto)throws AppException;

    /**
     * Description 删除动态
     * @param id 发布的动态id
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 6:16 PM 3/30/21
     **/
    void delete(Long id) throws AppException;

    /**
     * Description todo
     * @param reqDto
     * @throws AppException
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.yr.net.app.moments.dto.UserMomentsDto>
     * @Author dengbp
     * @Date 20:58 2020-12-17
     **/
    List<UserMomentsRespDto> findUserMoments(UserMomentsReqDto reqDto)throws AppException;


}
