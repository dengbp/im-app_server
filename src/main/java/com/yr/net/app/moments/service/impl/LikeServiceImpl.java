package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentsLikeQueryBo;
import com.yr.net.app.moments.entity.Like;
import com.yr.net.app.moments.mapper.LikeMapper;
import com.yr.net.app.moments.service.ILikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.moments.service.IUserMomentsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengbp
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {

    @Override
    public Map<Long, AtomicInteger> getCommentLikeTotal(CommentsLikeQueryBo queryBo) throws AppException {
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getState,1);
        if (StringUtils.isNotBlank(queryBo.getMomentsIds())) {
            queryWrapper.in(Like::getCommentId,queryBo.getMomentsIds());
        }
        Map<Long, AtomicInteger> result = new HashMap<>(32);
        total(this.list(queryWrapper),result);
        return result;
    }
}
