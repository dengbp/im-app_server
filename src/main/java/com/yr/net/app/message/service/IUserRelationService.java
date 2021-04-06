package com.yr.net.app.message.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.message.controller.dto.FollowReqDto;
import com.yr.net.app.message.controller.dto.RelationStateRespDto;
import com.yr.net.app.message.entity.UserRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.checkerframework.checker.units.qual.A;

/**
 * @author dengbp
 */
public interface IUserRelationService extends IService<UserRelation> {

    /**
     * Description 关注操作
     * @param reqDto
     * @return void
     * @throws AppException
     * @Author dengbp
     * @Date 2:11 AM 3/29/21
     **/


    void follow(FollowReqDto reqDto)throws AppException;

    /**
     * Description todo
     * @param byOperatorId
     * @throws AppException
     * @return com.yr.net.app.message.controller.dto.RelationStateRespDto
     * @Author dengbp
     * @Date 10:53 AM 4/6/21
     **/

    RelationStateRespDto followState(String byOperatorId)throws AppException;




    /**
     * Description 查已关注 状态信息
     * @param userId 当前要查的用户id
 * @param relationId  被关注的用户id
     * @throws AppException
     * @return com.yr.net.app.message.entity.UserRelation
     * @Author dengbp
     * @Date 2:19 AM 3/29/21
     **/

     UserRelation getFollowState(String userId,String relationId)throws AppException;

}
