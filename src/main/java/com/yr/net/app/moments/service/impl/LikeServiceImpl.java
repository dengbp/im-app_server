package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentsLikeQueryBo;
import com.yr.net.app.moments.dto.MomentsLikeReqDto;
import com.yr.net.app.moments.entity.Like;
import com.yr.net.app.moments.mapper.LikeMapper;
import com.yr.net.app.moments.service.ILikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.moments.service.IUserMomentsService;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengbp
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {

    /**
     * 点赞状态 0：取消赞；   1：有效赞
     */
    @Override
    public void add(MomentsLikeReqDto dto) throws AppException {
        Like like = new Like();
        like.setLikeUserId(StringUtils.isBlank(dto.getLikeUserId())?AppUtil.getCurrentUserId():dto.getLikeUserId());
        like.setPublicUserId(dto.getPublicUserId());
        like.setCommentId(dto.getMomentId());
        like.setType(dto.getType());
        like.setState(dto.getState());
        like.setLikeTime(LocalDateTime.now());
        if (!list(new LambdaQueryWrapper<Like>().eq(Like::getCommentId,like.getCommentId())
        .eq(Like::getLikeUserId,like.getLikeUserId()).eq(Like::getPublicUserId,like.getPublicUserId())
        .eq(Like::getType,like.getType())).isEmpty()){
            update(new LambdaUpdateWrapper<Like>().set(Like::getState,like.getState()).set(Like::getLikeTime,like.getLikeTime())
                    .eq(Like::getCommentId,like.getCommentId())
                    .eq(Like::getLikeUserId,like.getLikeUserId()).eq(Like::getPublicUserId,like.getPublicUserId())
                    .eq(Like::getType,like.getType()));
            return;
        }
        save(like);
    }

    @Override
    public Map<Long, AtomicInteger> getCommentLikeTotal(CommentsLikeQueryBo queryBo,Integer type) throws AppException {
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<Like>().eq(Like::getState,1).eq(Like::getType,type);
        if (queryBo.getMomentsIds()!=null && !queryBo.getMomentsIds().isEmpty()) {
            queryWrapper.in(Like::getCommentId,queryBo.getMomentsIds());
        }
        Map<Long, AtomicInteger> result = new HashMap<>();
        total(this.list(queryWrapper),result);
        return result;
    }

    @Override
    public Like getByMomentAndUser(Long commentId, String userId) throws AppException {
        List<Like> likes = this.list(new LambdaQueryWrapper<Like>().eq(Like::getCommentId,commentId).eq(Like::getLikeUserId,userId).orderByDesc(Like::getLikeTime));
        if (likes.isEmpty()){
            return null;
        }
        return likes.get(0);
    }
}
