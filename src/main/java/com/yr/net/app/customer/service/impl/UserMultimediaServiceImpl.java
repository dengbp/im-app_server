package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.net.app.common.entity.AppConstant;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.configure.AppProperties;
import com.yr.net.app.customer.dto.AlbumRequestDto;
import com.yr.net.app.customer.dto.CoordinateRequestDto;
import com.yr.net.app.customer.dto.MultimediaResponseDto;
import com.yr.net.app.customer.dto.VideoRequestDto;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.entity.UserMultimedia;
import com.yr.net.app.customer.mapper.UserMultimediaMapper;
import com.yr.net.app.customer.service.IUserMultimediaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 */
@Slf4j
@Service
public class UserMultimediaServiceImpl extends ServiceImpl<UserMultimediaMapper, UserMultimedia> implements IUserMultimediaService {


    @Resource
    private AppProperties appProperties;

    @Override
    public void saveFile(MultipartFile file, int type, int isFree, String price, CoordinateRequestDto coordinate,String showWord) throws AppException {
        String originName = file.getOriginalFilename();
        log.info("begin upload file[{}]",originName);
        String path = FileUtil.getFilePath(appProperties,originName);
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            this.save(createUserMultimedia( file, path.substring(path.lastIndexOf("/")), AddressByCoordUtil.getAdd(coordinate.getLatitude(),coordinate.getLongitude()).getFormattedAddress(), path, isFree, price,showWord));
            log.info("upload file[{}]success",originName);
        } catch (Exception e) {
            log.error("上传失败",e);
            throw new AppException(e.getMessage());
        }
    }

    @Override
    public IPage<MultimediaResponseDto> videoList(VideoRequestDto requestDto) throws AppException {
        Page<UserMultimedia> queryPage = new Page<>();
        LambdaQueryWrapper<UserMultimedia> queryWrapper = new LambdaQueryWrapper();
        if (-1 != requestDto.getType().intValue()) {
            queryWrapper.eq(UserMultimedia::getType,requestDto.getType());
        }
        SortUtil.handlePageSort(requestDto,queryPage, "CREATED_TIME", AppConstant.ORDER_DESC, false);
        return search(queryPage,queryWrapper);
    }

    private IPage<MultimediaResponseDto> search(Page queryPage,LambdaQueryWrapper queryWrapper){
        IPage<UserMultimedia> page = this.page(queryPage,queryWrapper);
        IPage<MultimediaResponseDto> responseDtoIPage = new Page<>();
        BeanUtils.copyProperties(page.getRecords(),responseDtoIPage.getRecords());
        return responseDtoIPage;
    }

    @Override
    public IPage<MultimediaResponseDto> getAlbum(AlbumRequestDto requestDto) throws AppException {
        Page<UserMultimedia> queryPage = new Page<>();
        LambdaQueryWrapper<UserMultimedia> queryWrapper = new LambdaQueryWrapper();
        if (-1 != requestDto.getType().intValue()) {
            queryWrapper.eq(UserMultimedia::getType,requestDto.getType());
        }
        SortUtil.handlePageSort(requestDto,queryPage,  false);
        String userId = StringUtils.isBlank(requestDto.getUserId())?AppUtil.getCurrentUserId():requestDto.getUserId();
        queryWrapper.eq(UserMultimedia::getUserId,userId);
        return search(queryPage,queryWrapper);
    }


    private UserMultimedia createUserMultimedia(MultipartFile file,String storeName,String addr,String path,int isFree,String price,String showWord){
        String originName = file.getOriginalFilename();
        String extName = originName.substring(originName.lastIndexOf(".") + 1);
        UserMultimedia userMultimedia = new UserMultimedia();
        userMultimedia.setUserId(AppUtil.getCurrentUserId());
        userMultimedia.setAddr(addr);
        userMultimedia.setCreatedTime(LocalDateTime.now());
        userMultimedia.setState(0);
        userMultimedia.setFileSize(file.getSize());
        userMultimedia.setFormat(extName);
        userMultimedia.setUrl(appProperties.getMultimedia_url()+"/"+path);
        userMultimedia.setUploadTime(LocalDateTime.now());
        userMultimedia.setIsFree(isFree);
        userMultimedia.setPath(path);
        userMultimedia.setPrice(new BigDecimal(price));
        userMultimedia.setMultimediaName(originName);
        userMultimedia.setStoreName(storeName);
        userMultimedia.setShowWord(showWord);
        return userMultimedia;
    }
}
