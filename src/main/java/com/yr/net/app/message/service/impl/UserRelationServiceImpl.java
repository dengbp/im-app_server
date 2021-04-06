package com.yr.net.app.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.message.controller.dto.FollowReqDto;
import com.yr.net.app.message.controller.dto.RelationStateRespDto;
import com.yr.net.app.message.entity.UserRelation;
import com.yr.net.app.message.mapper.UserRelationMapper;
import com.yr.net.app.message.service.IUserRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserRelationServiceImpl extends ServiceImpl<UserRelationMapper, UserRelation> implements IUserRelationService {

    @Override
    public void follow(FollowReqDto reqDto) throws AppException {

        UserRelation relation = new UserRelation();
        relation.setRelationId(reqDto.getRelationId());
        relation.setUserId(AppUtil.getCurrentUserId());
        relation.setState(reqDto.getState());
        relation.setCreatedBy(AppUtil.getCurrentUserId());
        relation.setCreatedTime(LocalDateTime.now());
        relation.setUpdatedTime(LocalDateTime.now());
        relation.setUpdatedBy(AppUtil.getCurrentUserId());
        if (!this.list(new LambdaQueryWrapper<UserRelation>().eq(UserRelation::getRelationId,relation.getRelationId()).eq(UserRelation::getUserId,relation.getUserId())).isEmpty()){
            this.update(new LambdaUpdateWrapper<UserRelation>().set(UserRelation::getState,relation.getState()).set(UserRelation::getUpdatedBy,relation.getUpdatedBy()).set(UserRelation::getUpdatedTime,relation.getUpdatedTime()).eq(UserRelation::getRelationId,relation.getRelationId()).eq(UserRelation::getUserId,relation.getUserId()));
            return;
        }
        save(relation);
    }

    @Override
    public RelationStateRespDto followState(String byOperatorId) throws AppException {
        RelationStateRespDto response = new RelationStateRespDto();
        response.setState(0);
        List<UserRelation> list = list(new LambdaQueryWrapper<UserRelation>().eq(UserRelation::getRelationId,byOperatorId).eq(UserRelation::getUserId,AppUtil.getCurrentUserId()));
        if (list.isEmpty()){
            return response;
        }
        response.setState(list.get(0).getState());
        return response;
    }

    @Override
    public UserRelation getFollowState(String userId, String relationId) throws AppException {
        List<UserRelation> list = list(new LambdaQueryWrapper<UserRelation>().eq(UserRelation::getUserId,userId).eq(UserRelation::getRelationId,relationId).eq(UserRelation::getState,UserRelation.FOLLOW).orderByDesc(UserRelation::getCreatedTime));
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}
