package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.entity.AppConstant;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.configure.AppProperties;
import com.yr.net.app.customer.dto.*;
import com.yr.net.app.customer.entity.UserMultimedia;
import com.yr.net.app.customer.mapper.UserMultimediaMapper;
import com.yr.net.app.customer.service.IUserMultimediaService;
import com.yr.net.app.moments.bo.CommentMultiQueryBo;
import com.yr.net.app.moments.entity.UserMoments;
import com.yr.net.app.moments.entity.UserMomentsSub;
import com.yr.net.app.moments.service.IUserMomentsService;
import com.yr.net.app.pojo.BaiduMapPositionResponse;
import com.yr.net.app.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dengbp
 */
@Slf4j
@Service
public class UserMultimediaServiceImpl extends ServiceImpl<UserMultimediaMapper, UserMultimedia> implements IUserMultimediaService {


    @Resource
    private AppProperties appProperties;
    @Resource
    private IUserMomentsService userMomentsService;

    @Override
    public MultipartFileRespDto saveFile(MultipartFile file) throws AppException {
        String originName = file.getOriginalFilename();
        log.info("begin upload file[{}]",originName);
        String path = FileUtil.getFilePath(appProperties,originName);
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            UserMultimedia userMultimedia = this.setUserMul(file,path,path.substring(path.lastIndexOf("/")+1));
            this.save(userMultimedia);
            log.info("upload file[{}]success",originName);
            MultipartFileRespDto respDto = new MultipartFileRespDto();
            respDto.setId(userMultimedia.getId());
            respDto.setFileUrl(userMultimedia.getUrl());
            respDto.setPreviewUrl(userMultimedia.getPreviewUrl());
            return respDto;
        } catch (Exception e) {
            log.error("上传失败",e);
            throw new AppException(e.getMessage());
        }
    }

    @Override
    public void updateMulInfo(String ids, Integer using,int mulType, int isFree, String price, CoordinateRequestDto coordinate, String showWord) throws AppException {
        BaiduMapPositionResponse positionResponse = AddressByCoordUtil.getAdd(coordinate.getLatitude(),coordinate.getLongitude());
        String addr = positionResponse==null?"地址异常":positionResponse.getFormattedAddress();
        UserMoments moment = null;
        if (using==1){
            moment = UserMomentsSub.buildUserMoment(addr,isFree,price,showWord);
            userMomentsService.saveOrUpdate(moment);
        }
        UserMoments finalMoment = moment;
        Arrays.asList(ids.split(",")).forEach(id->{
            /** 如果是用于动态 */
            UserMultimedia userMultimedia = this.setUserMulInfo(Long.parseLong(id.trim()),addr, finalMoment ==null?null: finalMoment.getId(),using,mulType);
            if (using != null){
                userMultimedia.setBeUsed(using);
            }
            this.updateById(userMultimedia);
        });
    }

    @Override
    public List<MultimediaResponseDto> videoList(VideoRequestDto requestDto) throws AppException {
        Page<UserMultimedia> queryPage = new Page<>();
        LambdaQueryWrapper<UserMultimedia> queryWrapper = new LambdaQueryWrapper();
        if (-1 != requestDto.getType().intValue()) {
            queryWrapper.eq(UserMultimedia::getMulType,requestDto.getType());
        }
        SortUtil.handlePageSort(requestDto,queryPage, "CREATED_TIME", AppConstant.ORDER_DESC, false);
        return search(queryPage,queryWrapper);
    }

    private List<MultimediaResponseDto> search(Page queryPage,LambdaQueryWrapper queryWrapper){
        IPage<UserMultimedia> page = this.page(queryPage,queryWrapper);
        List<MultimediaResponseDto> result = new ArrayList<>();
        if (!page.getRecords().isEmpty()){
            page.getRecords().forEach(e->{
                MultimediaResponseDto multimedia = new MultimediaResponseDto();
                result.add(multimedia);
                BeanUtils.copyProperties(e,multimedia);
            });
        }
        return result;
    }

    @Override
    public List<MultimediaResponseDto> getAlbum(AlbumRequestDto requestDto) throws AppException {
        Page<UserMultimedia> queryPage = new Page<>();
        LambdaQueryWrapper<UserMultimedia> queryWrapper = new LambdaQueryWrapper();
        if (-1 != requestDto.getType().intValue()) {
            queryWrapper.eq(UserMultimedia::getMulType,requestDto.getType());
        }
        SortUtil.handlePageSort(requestDto,queryPage,  false);
        String userId = StringUtils.isBlank(requestDto.getUserId())?AppUtil.getCurrentUserId():requestDto.getUserId();
        queryWrapper.eq(UserMultimedia::getUserId,userId).eq(UserMultimedia::getBeUsed,requestDto.getUserId());
        return search(queryPage,queryWrapper);
    }

    @Override
    public void albumDel(List<Long> ids) throws AppException {
           this.removeByIds(ids);
    }

    @Override
    public Integer getAlbumCount(String userId) throws AppException {
        userId = StringUtils.isBlank(userId)?AppUtil.getCurrentUserId():userId;
        LambdaQueryWrapper<UserMultimedia> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserMultimedia::getUserId,userId).eq(UserMultimedia::getBeUsed,0).eq(UserMultimedia::getMulType,0);
        return this.count(queryWrapper);
    }

    @Override
    public List<UserMultimedia> findByComment(CommentMultiQueryBo commentMultiQueryBo,Integer type) throws AppException {
        LambdaQueryWrapper<UserMultimedia> queryWrapper = new LambdaQueryWrapper<>();
        /** 动态类型多媒体 */
        queryWrapper.eq(UserMultimedia::getBeUsed,1).eq(UserMultimedia::getType,type);
        if (StringUtils.isNotBlank(commentMultiQueryBo.getMomentsIds())) {
            queryWrapper.in(UserMultimedia::getCommentId,commentMultiQueryBo.getMomentsIds());
        }
        return this.list(queryWrapper);
    }


    private UserMultimedia setUserMul(MultipartFile file,String path,String storeName) throws Exception {
        String originName = file.getOriginalFilename();
        String extName = originName.substring(originName.lastIndexOf(".") + 1);
        UserMultimedia userMultimedia = new UserMultimedia();
        userMultimedia.setUserId(AppUtil.getCurrentUserId());
        userMultimedia.setCreatedTime(LocalDateTime.now());
        userMultimedia.setState(0);
        userMultimedia.setFileSize(file.getSize());
        userMultimedia.setFormat(extName);
        userMultimedia.setUrl(appProperties.getMultimedia_url()+"/"+storeName);
        userMultimedia.setUploadTime(LocalDateTime.now());
        userMultimedia.setPath(path);
        userMultimedia.setMultimediaName(originName);
        userMultimedia.setStoreName(storeName);
        String addName = storeName.substring(0,storeName.lastIndexOf("."));
        /** 视频处理 */
        if(FILE_TYPE.contains(extName)){
            VideoUtil.randomGrabberFFmpegImage(path,appProperties.getMultimedia_path(),addName);
            userMultimedia.setPreviewUrl(appProperties.getMultimedia_url()+"/"+addName+".jpg");
        }else {
            String preFileName = addName+"_small.png";
            ImgCompressUtil.writeToFile(appProperties.getMultimedia_path()+preFileName, ImgCompressUtil.resize(userMultimedia.getPath(),0.25));
            userMultimedia.setPreviewUrl(appProperties.getMultimedia_url()+"/"+preFileName);
        }
        return userMultimedia;
    }


    private UserMultimedia setUserMulInfo(Long mulId,String addr,Long commentId,Integer using,Integer mulType){
        UserMultimedia userMultimedia = new UserMultimedia();
        userMultimedia.setId(mulId);
        userMultimedia.setAddr(addr);
        if (commentId != null){
            userMultimedia.setCommentId(commentId);
        }
        userMultimedia.setType(UserMultimedia.THEME);
        userMultimedia.setBeUsed(using);
        userMultimedia.setMulType(mulType);
        userMultimedia.setUserId(AppUtil.getCurrentUserId());
        return userMultimedia;
    }
}
