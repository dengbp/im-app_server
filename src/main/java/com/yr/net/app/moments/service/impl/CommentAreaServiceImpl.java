package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.dto.AddMomentAreaDto;
import com.yr.net.app.moments.dto.CommentRespDto;
import com.yr.net.app.moments.entity.CommentArea;
import com.yr.net.app.moments.entity.UserMoments;
import com.yr.net.app.moments.mapper.CommentAreaMapper;
import com.yr.net.app.moments.service.ICommentAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.moments.service.IUserMomentsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengbp
 */
@Service
public class CommentAreaServiceImpl extends ServiceImpl<CommentAreaMapper, CommentArea> implements ICommentAreaService {

    @Autowired
    private IUserMomentsService userMomentsService;
    @Autowired
    IUserInfoService userInfoService;

    @Override
    public void add(AddMomentAreaDto dto) throws AppException {
        CommentArea commentArea = new CommentArea();
        BeanUtils.copyProperties(dto,commentArea);
        commentArea.setCommentTime(LocalDateTime.now());
        commentArea.setState(CommentArea.NORMAL);
        commentArea.setCommentId(dto.getCommentId());
        this.save(commentArea);
    }

    @Override
    public List<CommentArea> getByMomentId(Long momentId, Integer type,Integer limit) throws AppException {
        return list(new LambdaQueryWrapper<CommentArea>().eq(CommentArea::getCommentId,momentId).eq(CommentArea::getType,type).eq(CommentArea::getState,CommentArea.NORMAL).orderByDesc(CommentArea::getCommentTime).last("limit "+limit));
    }

    @Override
    public void delete(AddMomentAreaDto dto) throws AppException {
        this.update(new LambdaUpdateWrapper<CommentArea>().set(CommentArea::getState,CommentArea.DELETE).eq(CommentArea::getId,dto.getId()));
    }

    @Override
    public List<CommentRespDto> list(AddMomentAreaDto dto) throws AppException {
        List<CommentArea> commentAreas =  this.list(new LambdaQueryWrapper<CommentArea>().eq(CommentArea::getState,CommentArea.NORMAL).eq(CommentArea::getCommentId,dto.getCommentId()));
        List<CommentRespDto> resp = new ArrayList<>();
        if (commentAreas.isEmpty()){
            return resp;
        }
        commentAreas.forEach(a->{
            String userId = "";
            String userName = "";
            CommentArea parent = this.getById(dto.getCommentId());
            if (parent==null){
                UserMoments moments = userMomentsService.getById(dto.getCommentId());
                userId = moments.getUserId();
                UserInfo userInfo = userInfoService.getByUserId(moments.getUserId());
                userName = userInfo.getUserName();
            }else {
                userId =parent.getUserId();
                userName = parent.getUserName();
            }
            resp.add(CommentRespDto.assembly(a,userId,userName));});
        return resp;
    }

    @Override
    public Map<Long, AtomicInteger> getCommentTotal(CommentAreaQueryBo queryBo,Integer type) throws AppException {

        LambdaQueryWrapper<CommentArea> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentArea::getState,CommentArea.NORMAL).eq(CommentArea::getType,type);
        if (queryBo.getMomentsIds()!=null && !queryBo.getMomentsIds().isEmpty()) {
            queryWrapper.in(CommentArea::getCommentId,queryBo.getMomentsIds());
        }
        List<CommentArea> list = this.list(queryWrapper);
        Map<Long, AtomicInteger> result = new HashMap<>(32);
        total(list,result);
        return result;
    }
}
