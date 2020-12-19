package com.yr.net.app.moments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.dto.UserMomentsRespDto;
import com.yr.net.app.moments.dto.UserMomentsReqDto;
import com.yr.net.app.moments.entity.UserMoments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengbp
 */
public interface IUserMomentsService extends IService<UserMoments> {

    /**
     * Description todo
     * @param reqDto
     * @throws AppException
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.yr.net.app.moments.dto.UserMomentsDto>
     * @Author dengbp
     * @Date 20:58 2020-12-17
     **/
    IPage<UserMomentsRespDto> findUserMoments(UserMomentsReqDto reqDto)throws AppException;

}
