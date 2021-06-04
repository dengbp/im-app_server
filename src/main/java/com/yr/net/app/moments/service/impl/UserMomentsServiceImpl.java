package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.entity.AppConstant;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.entity.UserMultimedia;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.customer.service.IUserMultimediaService;
import com.yr.net.app.log.entity.UserSignLog;
import com.yr.net.app.log.service.IUserExchangeLogService;
import com.yr.net.app.log.service.IUserSignLogService;
import com.yr.net.app.message.entity.UserRelation;
import com.yr.net.app.message.service.IUserRelationService;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.bo.CommentMultiQueryBo;
import com.yr.net.app.moments.bo.CommentsLikeQueryBo;
import com.yr.net.app.moments.dto.*;
import com.yr.net.app.moments.dto.UserMomentsRespDto.ImagesBean;
import com.yr.net.app.moments.dto.UserMomentsRespDto.VideoBean;
import com.yr.net.app.moments.entity.CommentArea;
import com.yr.net.app.moments.entity.Like;
import com.yr.net.app.moments.entity.UserMoments;
import com.yr.net.app.moments.mapper.UserMomentsMapper;
import com.yr.net.app.moments.service.ICommentAreaService;
import com.yr.net.app.moments.service.ILikeService;
import com.yr.net.app.moments.service.IUserMomentsService;
import com.yr.net.app.pay.controller.enums.ExchangeItem;
import com.yr.net.app.pojo.BaiduMapPositionResponse;
import com.yr.net.app.tools.AddressByCoordUtil;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.RandomUtil;
import com.yr.net.app.tools.SortUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
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
    @Autowired
    private IUserRelationService userRelationService;

    private static Integer COMMENT_TYPE = 0;
    @Autowired
    private IUserExchangeLogService userExchangeLogService;
    @Resource
    private IUserSignLogService userSignLogService;
    @Resource
    private UserMomentsMapper userMomentsMapper;

    @Override
    public void add(AddMomentDto addMomentDto) throws AppException {
        UserMoments moment = new UserMoments();
        BeanUtils.copyProperties(addMomentDto,moment);
        BaiduMapPositionResponse response = AddressByCoordUtil.getAdd(addMomentDto.getLatitude(),addMomentDto.getLongitude());
        if (response != null){
            moment.setPublicAddr(response.getCity()+response.getDistrict());
        }
        moment.setPublicTime(LocalDateTime.now());
        moment.setUserId(AppUtil.getCurrentUserId());
        moment.setState(UserMoments.NORMAL);
        save(moment);
    }

    @Override
    public void add(AddSimpleMomentDto addMomentDto) throws AppException {
        String title = addMomentDto.getShowWord();
        if (StringUtils.isNotBlank(title)){
            if (count(new LambdaQueryWrapper<UserMoments>().eq(UserMoments::getShowWord,title))>0){
                return;
            }
        }
        UserInfo user = userInfoService.getRandOne();
        LocalDateTime now = LocalDateTime.now();
        UserMoments moment = new UserMoments();
        moment.setUserId(user.getUserId());
        moment.setState(UserMoments.NORMAL);
        moment.setPublicTime(now);
        moment.setPublicAddr(user.getNowLife());
        UserSignLog log = userSignLogService.searchByUserId(user.getUserId());
        if (log != null){
            moment.setPublicAddr(log.getSignAddr());
        }
        moment.setIsFree(0);
        moment.setShowWord(title);
        userMomentsMapper.insert(moment);
        UserMultimedia userMultimedia = new UserMultimedia();
        userMultimedia.setUserId(user.getUserId());
        userMultimedia.setCreatedTime(now);
        userMultimedia.setState(0);
        userMultimedia.setFileSize(0L);
        userMultimedia.setFormat("mp4");
        userMultimedia.setUrl(addMomentDto.getUrl());
        userMultimedia.setUploadTime(now);
        userMultimedia.setPreviewUrl(addMomentDto.getPreviewUrl());
        userMultimedia.setMulType(1);
        userMultimedia.setBeUsed(1);
        userMultimedia.setCommentId(moment.getId());
        userMultimedia.setCreatedTime(now);
        userMultimedia.setCreatedBy(user.getUserName());
        userMultimedia.setType(0);
        userMultimediaService.save(userMultimedia);
        addLike(moment.getId(),moment.getUserId());
    }

    /**
     * Description 伪造点赞
     * @param
     * @return void
     * @Author dengbp
     * @Date 1:02 PM 6/1/21
     **/

    private void addLike(Long momentId,String publicUserId){

        for (int i = 0; i< RandomUtil.getRandom(); i++){
            MomentsLikeReqDto likeReqDto = new MomentsLikeReqDto();
            likeReqDto.setType(MomentsLikeReqDto.THEME);
            likeReqDto.setState(MomentsLikeReqDto.LIKE);
            likeReqDto.setMomentId(momentId);
            likeReqDto.setPublicUserId(publicUserId);
            UserInfo userInfo = userInfoService.getOneNotEp(publicUserId);
            likeReqDto.setLikeUserId(userInfo.getUserId());
            likeService.add(likeReqDto);
        }
    }


    public void delete(Long id) throws AppException {
        this.update(new LambdaUpdateWrapper<UserMoments>().set(UserMoments::getState,UserMoments.DELETE).eq(UserMoments::getId,id));
    }

    @Override
    public List<UserMomentsRespDto> findUserMoments(UserMomentsReqDto reqDto) throws AppException {
        Page<UserMoments> page = new Page<>();
        SortUtil.handlePageSort(reqDto, page, "PUBLIC_TIME", AppConstant.ORDER_DESC, false);
        LambdaQueryWrapper<UserMoments> wrapper = new LambdaQueryWrapper<>();
        if (reqDto.getMomentId() != null){
            wrapper.eq(UserMoments::getId,reqDto.getMomentId());
        }
        wrapper.eq(UserMoments::getState,UserMoments.NORMAL);
        if (StringUtils.isNotBlank(reqDto.getUserId())){
            wrapper.eq(UserMoments::getUserId,reqDto.getUserId());
        }
        /** 这里需要用户的爱好信息、活动轨迹查动态信息 */
        String userId = AppUtil.getCurrentUserId();
        IPage<UserMoments> infoIPage = this.baseMapper.selectPage(page, wrapper);
        List<UserMomentsRespDto> userInfoResponses = new ArrayList<>();
        assembly(infoIPage.getRecords(), userInfoResponses);
        return userInfoResponses;
    }

    private Set<Long> queryCondition(List<UserMoments> source,List<UserMomentsRespDto> userMomentsResp){
        Set<Long> set = new HashSet<>();
        if (source.isEmpty()) {
            return set;
        }
        source.forEach(e->{
            set.add(e.getId());
            UserMomentsRespDto respDto = new UserMomentsRespDto();
            UserInfo user = userInfoService.getByUserId(e.getUserId());
            if (user == null){
                return;
            }
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
        return set;
    }

    private void assembly(List<UserMoments> source, List<UserMomentsRespDto> userMomentsResp) {
        if (source.isEmpty()){
            return;
        }
        /** 组装动态id **/
        Set<Long> queryCondition = this.queryCondition(source, userMomentsResp);
        if (queryCondition.isEmpty()){
            return;
        }
        /** 取动态的评论和点赞数量 **/
        Map<Long, AtomicInteger> commentTotal = commentAreaService.getCommentTotal(new CommentAreaQueryBo(queryCondition), COMMENT_TYPE);
        Map<Long, AtomicInteger> likeTotal = likeService.getCommentLikeTotal(new CommentsLikeQueryBo(queryCondition), COMMENT_TYPE);
        userMomentsResp.forEach(res -> {
            res.setCommentTotal(0);
            res.setLikeTotal(0);
            res.setCommentRespDtos(new ArrayList<>());
            setComments(res,res.getId(),0);
            Like like = likeService.getByMomentAndUser(res.getId(),AppUtil.getCurrentUserId());
            res.setLike(like==null?0:like.getState());
            UserRelation relation = userRelationService.getFollowState(AppUtil.getCurrentUserId(),res.getUserId());
            res.setFollow(relation==null?0:relation.getState());
            if (commentTotal.containsKey(res.getId())) {
                res.setCommentTotal(commentTotal.get(res.getId()).get());
            }
            if (likeTotal.containsKey(res.getId())) {
                res.setLikeTotal(likeTotal.get(res.getId()).get());
            }
            res.setPurview(userExchangeLogService.findMomentPayByUser(res.getId(),AppUtil.getCurrentUserId(), ExchangeItem.moment));
        });
    }

    private void setComments(UserMomentsRespDto comment, Long id,Integer type){
        List<CommentArea>  commentAreas = commentAreaService.getByMomentId(id,type,50);
        if (commentAreas.isEmpty()){
            return;
        }
        commentAreas.forEach(a-> {
            comment.getCommentRespDtos().add(CommentRespDto.assembly(a,comment.getUserId(),comment.getUserName()));
            //取评论的回复内容
           /* this.setComments(comment,a.getId(),1);*/
        });
    }
}
