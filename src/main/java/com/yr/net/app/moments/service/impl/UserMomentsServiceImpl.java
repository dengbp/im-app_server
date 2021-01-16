package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.net.app.common.entity.AppConstant;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.bo.CommentMultiQueryBo;
import com.yr.net.app.moments.bo.CommentsLikeQueryBo;
import com.yr.net.app.moments.dto.UserMomentsRespDto;
import com.yr.net.app.moments.dto.UserMomentsReqDto;
import com.yr.net.app.moments.entity.Multimedia;
import com.yr.net.app.moments.entity.UserMoments;
import com.yr.net.app.moments.mapper.UserMomentsMapper;
import com.yr.net.app.moments.service.ICommentAreaService;
import com.yr.net.app.moments.service.ILikeService;
import com.yr.net.app.moments.service.IMultimediaService;
import com.yr.net.app.moments.service.IUserMomentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.SortUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户动态服务类
 * @author dengbp
 */
@Slf4j
@Service
public class UserMomentsServiceImpl extends ServiceImpl<UserMomentsMapper, UserMoments> implements IUserMomentsService {

    @Autowired
    private IMultimediaService multimediaService;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private ICommentAreaService commentAreaService;

    @Override
    public List<UserMomentsRespDto> findUserMoments(UserMomentsReqDto reqDto) throws AppException {
        Page<UserMoments> page = new Page<>();
        SortUtil.handlePageSort(reqDto, page, "CREATED_TIME", AppConstant.ORDER_DESC, false);
        LambdaQueryWrapper<UserMoments> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserMoments::getState,0);
        if (StringUtils.isNotBlank(reqDto.getUserId())) {
            wrapper.eq(UserMoments::getUserId,reqDto.getUserId());
        }
        IPage<UserMoments> infoIPage = this.baseMapper.selectPage(page, wrapper);
        List<UserMomentsRespDto> userInfoResponses = new ArrayList<>();
        BeanUtils.copyProperties(infoIPage.getRecords(), userInfoResponses);
        this.assembly(infoIPage.getRecords(), userInfoResponses);
        Page<UserMomentsRespDto> result = new Page();
        result.setRecords(userInfoResponses);
        result.setSize(infoIPage.getSize());
        result.setTotal(infoIPage.getTotal());
        result.setCurrent(infoIPage.getCurrent());
        return result.getRecords();
    }

    private String queryCondition(List<UserMoments> source){
        StringBuffer sb = new StringBuffer();
        if (source != null) {
            source.forEach(e->{
                sb.append(e.getId()).append(",");
            });
        }
        return sb.substring(0,sb.lastIndexOf(","));
    }

    private void assembly(List<UserMoments> source,List<UserMomentsRespDto> userMomentsResp){
        String queryCondition = this.queryCondition(source);
        /** 取动态多媒体数据、评论和点赞数量 **/
        if (StringUtils.isNotBlank(queryCondition)) {
            Map<Long, AtomicInteger> commentTotal = commentAreaService.getCommentTotal(new CommentAreaQueryBo(queryCondition));
            Map<Long, AtomicInteger> likeTotal = likeService.getCommentLikeTotal(new CommentsLikeQueryBo(queryCondition));
            List<Multimedia> commentMulti = multimediaService.findByComment(new CommentMultiQueryBo(queryCondition));
            commentMulti.forEach(m->
                userMomentsResp.stream().filter(res ->{
                    if(res.getId().longValue()==m.getCommentId().longValue()){
                        res.setUrl(m.getHttpUrl());
                        if (commentTotal.containsKey(res.getId())){
                            res.setCommentTotal(commentTotal.get(res.getId()).get());
                        }
                        if (likeTotal.containsKey(res.getId())){
                            res.setLikeTotal(likeTotal.get(res.getId()).get());
                        }
                    }
                    return true;
                })
            );
        }
    }
}
