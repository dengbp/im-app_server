package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.net.app.common.entity.AppConstant;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.entity.UserMultimedia;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.customer.service.IUserMultimediaService;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.bo.CommentMultiQueryBo;
import com.yr.net.app.moments.bo.CommentsLikeQueryBo;
import com.yr.net.app.moments.dto.AddMomentDto;
import com.yr.net.app.moments.dto.UserMomentsRespDto.*;
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
import com.yr.net.app.pojo.BaiduMapPositionResponse;
import com.yr.net.app.tools.AddressByCoordUtil;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.SortUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    IUserInfoService userInfoService;
    @Autowired
    private IUserMultimediaService userMultimediaService;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private ICommentAreaService commentAreaService;
    private static Integer COMMENT_TYPE = 0;

    @Override
    public void add(AddMomentDto addMomentDto) throws AppException {
        UserMoments moment = new UserMoments();
        BeanUtils.copyProperties(addMomentDto,moment);
        BaiduMapPositionResponse response = AddressByCoordUtil.getAdd(addMomentDto.getLatitude().toString(),addMomentDto.getLongitude().toString());
        if (response != null){
            moment.setPublicAddr(response.getCity()+response.getDistrict());
        }
        moment.setPublicTime(LocalDateTime.now());
        moment.setUserId(AppUtil.getCurrentUserId());
        moment.setState(UserMoments.NORMAL);
    }

    @Override
    public List<UserMomentsRespDto> findUserMoments(UserMomentsReqDto reqDto) throws AppException {
        Page<UserMoments> page = new Page<>();
        SortUtil.handlePageSort(reqDto, page, "PUBLIC_TIME", AppConstant.ORDER_DESC, false);
        LambdaQueryWrapper<UserMoments> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserMoments::getState,UserMoments.NORMAL);
        String userId = StringUtils.isBlank(reqDto.getUserId())? AppUtil.getCurrentUserId():reqDto.getUserId();
        wrapper.eq(UserMoments::getUserId,userId);
        IPage<UserMoments> infoIPage = this.baseMapper.selectPage(page, wrapper);
        List<UserMomentsRespDto> userInfoResponses = new ArrayList<>();
        assembly(infoIPage.getRecords(), userInfoResponses);
        return userInfoResponses;
    }

    private String queryCondition(List<UserMoments> source,List<UserMomentsRespDto> userMomentsResp){
        if (source.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        source.forEach(e->{
            sb.append(e.getId()).append(",");
            UserMomentsRespDto respDto = new UserMomentsRespDto();
            UserInfo user = userInfoService.getByUserId(e.getUserId());
            respDto.setUserName(user.getUserName());
            respDto.setIcon(user.getIcon());
            respDto.setSex(user.getSex());
            List<UserMultimedia>  multimedia = userMultimediaService.findByComment(new CommentMultiQueryBo(e.getId().toString()) ,UserMultimedia.THEME);
            if (multimedia != null){
                List<UserMomentsRespDto.ImagesBean> imagesBeans = new ArrayList<>();
                multimedia.forEach(m->{
                    if (m.getMulType().equals(UserMultimedia.IMG)){
                        ImagesBean i = new UserMomentsRespDto.ImagesBean();
                        i.setUrl(m.getUrl());
                        i.setPreviewUrl(m.getPreviewUrl());
                        imagesBeans.add(i);
                    }
                    if(m.getMulType().equals(UserMultimedia.VIDEO)){
                        VideoBean videoBeans = new VideoBean();
                        videoBeans.setUrl(m.getUrl());
                        videoBeans.setPreviewUrl(m.getPreviewUrl());
                        respDto.setVideoBean(videoBeans);
                    }
                });
                respDto.setImagesBean(imagesBeans);
            }
            BeanUtils.copyProperties(e,respDto);
            respDto.setPublicTheme(e.getShowWord());
            userMomentsResp.add(respDto);
        });
        return sb.length()>0?sb.substring(0,sb.lastIndexOf(",")):"";
    }

    private void assembly(List<UserMoments> source, List<UserMomentsRespDto> userMomentsResp) {
        if (source.isEmpty()){
            return;
        }
        /** 组装动态id **/
        String queryCondition = this.queryCondition(source, userMomentsResp);
        if (StringUtils.isBlank(queryCondition)){
            return;
        }
        /** 取动态的评论和点赞数量 **/
        Map<Long, AtomicInteger> commentTotal = commentAreaService.getCommentTotal(new CommentAreaQueryBo(queryCondition), COMMENT_TYPE);
        Map<Long, AtomicInteger> likeTotal = likeService.getCommentLikeTotal(new CommentsLikeQueryBo(queryCondition), COMMENT_TYPE);
        userMomentsResp.forEach(res -> {
            res.setCommentTotal(0);
            res.setLikeTotal(0);
            if (commentTotal.containsKey(res.getId())) {
                res.setCommentTotal(commentTotal.get(res.getId()).get());
            }
            if (likeTotal.containsKey(res.getId())) {
                res.setLikeTotal(likeTotal.get(res.getId()).get());
            }
        });
    }
}
