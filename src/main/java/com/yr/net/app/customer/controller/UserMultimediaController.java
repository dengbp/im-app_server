package com.yr.net.app.customer.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.AlbumRequestDto;
import com.yr.net.app.customer.dto.CoordinateRequestDto;
import com.yr.net.app.customer.dto.VideoRequestDto;
import com.yr.net.app.customer.service.IUserMultimediaService;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dengbp
 */
@Slf4j
@RestController
@RequestMapping("/customer/user-multimedia")
public class UserMultimediaController {

    @Resource
    IUserMultimediaService userMultimediaService;

    /**
     * Description todo
     * @param file 文件
 * @param type type: 多媒体类型 0：图片；1：视频
     * @return com.yr.net.app.base.dto.RestResult
     * @Author dengbp
     * @Date 23:59 2020-11-24
     **/
    @RequestMapping("/upload")
    @ResponseBody
    @ControllerEndpoint(operation = "用户多媒体上传", exceptionMessage = "用户多媒体上传失败")
    @Log("用户多媒体上传")
    public RestResult upload(@RequestParam("file") MultipartFile file,@Valid CoordinateRequestDto coordinate,String type,String isFree, String price,String showWord){
        if (file == null) {
            return RestResult.error("上传失败，文件为空");
        }
        if (StringUtils.isBlank(type)) {
            return RestResult.error("上传失败，文件类型为空");
        }
        if (StringUtils.isBlank(isFree)) {
            return RestResult.error("上传失败，是否开放为空");
        }
        userMultimediaService.saveFile(file,Integer.parseInt(type),Integer.parseInt(isFree),price,coordinate,showWord);
        return RestResult.ok();
    }


    @PostMapping("/list")
    @ResponseBody
    @ControllerEndpoint(operation = "视频列表页面接口", exceptionMessage = "视频列表页面接口失败")
    @Log("视频列表页面接口")
    public RestResult list(@Valid VideoRequestDto requestDto){
        requestDto.setUserId(AppUtil.getCurrentUserId());
        return RestResult.ok().setResult(userMultimediaService.videoList(requestDto));
    }


    @PostMapping("/album")
    @ResponseBody
    @ControllerEndpoint(operation = "用户相册\\视频查询接口", exceptionMessage = "用户相册\\视频查询接口失败")
    @Log("用户相册\\视频查询接口")
    public RestResult album(@Valid AlbumRequestDto requestDto){
        requestDto.setUserId(AppUtil.getCurrentUserId());
        return RestResult.ok().setResult(userMultimediaService.getAlbum(requestDto));
    }



}
