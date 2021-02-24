package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.dto.AddMomentDto;
import com.yr.net.app.moments.entity.CommentArea;
import com.yr.net.app.moments.mapper.CommentAreaMapper;
import com.yr.net.app.moments.service.ICommentAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.moments.service.IUserMomentsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class CommentAreaServiceImpl extends ServiceImpl<CommentAreaMapper, CommentArea> implements ICommentAreaService {

    @Override
    public void add(AddMomentDto dto) throws AppException {
        CommentArea commentArea = new CommentArea();
        BeanUtils.copyProperties(dto,commentArea);
        commentArea.setCommentTime(LocalDateTime.now());
        commentArea.setState(CommentArea.NORMAL);
        this.save(commentArea);
    }

    @Override
    public void delete(AddMomentDto dto) throws AppException {
        this.update(new LambdaUpdateWrapper<CommentArea>().set(CommentArea::getState,CommentArea.DELETE).eq(CommentArea::getId,dto.getId()));
    }

    @Override
    public List<CommentArea> list(AddMomentDto dto) throws AppException {
        return this.list(new LambdaQueryWrapper<CommentArea>().eq(CommentArea::getState,CommentArea.NORMAL).eq(CommentArea::getCommentId,dto.getCommentId()).eq(CommentArea::getType,dto.getType()));
    }

    @Override
    public Map<Long, AtomicInteger> getCommentTotal(CommentAreaQueryBo queryBo,Integer type) throws AppException {

        LambdaQueryWrapper<CommentArea> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentArea::getState,0).eq(CommentArea::getType,type);
        if (StringUtils.isNotBlank(queryBo.getMomentsIds())) {
            queryWrapper.in(CommentArea::getCommentId,queryBo.getMomentsIds());
        }
        List<CommentArea> list = this.list(queryWrapper);
        Map<Long, AtomicInteger> result = new HashMap<>(32);
        total(list,result);
        return result;
    }
}
