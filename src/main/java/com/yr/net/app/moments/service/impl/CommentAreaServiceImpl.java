package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.entity.CommentArea;
import com.yr.net.app.moments.mapper.CommentAreaMapper;
import com.yr.net.app.moments.service.ICommentAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.moments.service.IUserMomentsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengbp
 */
@Service
public class CommentAreaServiceImpl extends ServiceImpl<CommentAreaMapper, CommentArea> implements ICommentAreaService {

    @Override
    public Map<Long, AtomicInteger> getCommentTotal(CommentAreaQueryBo queryBo) throws AppException {

        LambdaQueryWrapper<CommentArea> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentArea::getState,0);
        if (StringUtils.isNotBlank(queryBo.getMomentsIds())) {
            queryWrapper.in(CommentArea::getCommentId,queryBo.getMomentsIds());
        }
        List<CommentArea> list = this.list(queryWrapper);
        Map<Long, AtomicInteger> result = new HashMap<>(32);
        total(list,result);
        return result;
    }
}
