package com.yr.net.app.log.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.dto.OperationReportDto;
import com.yr.net.app.log.dto.UserTrackRespDto;
import com.yr.net.app.log.entity.UserTrack;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 浏览、关注、点赞等服务类
 * @author dengbp
 */
public interface IUserTrackService extends IService<UserTrack> {

    /**
     * Description 按某个用户查其操作(浏览、点赞等)过的用户
     * @param userId
 * @param operatorType 操作类型 0：查看用户信息；1：用户动态；2:点赞；3：关注；4：拉黑;.....
     * @return java.util.List<com.yr.net.app.log.dto.UserTrackRespDto>
     * @throws AppException
     * @Author dengbp
     * @Date 1:29 PM 1/25/21
     **/


    List<UserTrackRespDto> findByUserId(String userId,Integer operatorType)throws AppException;

    /**
     * Description todo
     * @param report
     * @return void
     * @throws AppException
     * @Author dengbp
     * @Date 2:38 AM 2/22/21
     **/
    void saveTrack(OperationReportDto report)throws AppException;

    /**
     * Description 取粉丝量
     * @param userId
     * @throws AppException
     * @return java.lang.Integer
     * @Author dengbp
     * @Date 1:35 PM 2/22/21
     **/
    Integer getFans(String userId)throws AppException;

    /**
     * Description 取关注量
     * @param userId
     * @throws AppException
     * @return java.lang.Integer
     * @Author dengbp
     * @Date 1:35 PM 2/22/21
     **/
    Integer getFollows(String userId)throws AppException;

}
