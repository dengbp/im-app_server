package com.yr.net.app.customer.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.customer.dto.*;
import com.yr.net.app.customer.service.IUserMultimediaService;
import com.yr.net.app.tools.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * Description 用户多媒体上传接口
     * @param file 文件
     * @param relationId 关联源文件id,视频封面使用
     * @return com.yr.net.app.base.dto.RestResult
     * @Author dengbp
     * @Date 23:59 2020-11-24
     **/
    @RequestMapping("/upload")
    @ResponseBody
    @ControllerEndpoint(operation = "用户多媒体上传", exceptionMessage = "用户多媒体上传失败")
    @Log("用户多媒体上传")
    public RestResult upload(@RequestParam("file") MultipartFile file,@RequestPart(value="relationId",required=false) String relationId)throws IOException {
        if (file == null) {
            return RestResult.error("上传失败，文件为空");
        }
        return RestResult.ok().setResult( userMultimediaService.saveFile(file,relationId));
    }

    /**
     * Description 多媒体上传后信息的补录
     * @return com.yr.net.app.base.dto.RestResult
     * @Author dengbp
     * @Date 23:59 2020-11-24
     **/

    @RequestMapping(value="/mutSet", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @ControllerEndpoint(operation = "用户多媒体信息", exceptionMessage = "用户多媒体信息失败")
    @Log("动态发布信息")
    public RestResult mutSet(@RequestBody @Valid MultipartInfoRequestDto requestDto){
        CoordinateRequestDto coordinate = new CoordinateRequestDto();
        coordinate.setLatitude(requestDto.getLatitude());
        coordinate.setLongitude(requestDto.getLongitude());
        if(Arrays.asList(requestDto.getMulIds().split(",")).isEmpty()){
            return RestResult.error("动态id[mulIds]不能为空");
        }
        userMultimediaService.updateMulInfo(requestDto.getMulIds(),requestDto.getUsing(),Integer.parseInt(requestDto.getType()),Integer.parseInt(requestDto.getIsFree()),requestDto.getPrice(),coordinate,requestDto.getShowWord());
        return RestResult.ok();
    }




    @PostMapping("/list")
    @ResponseBody
    @ControllerEndpoint(operation = "视频列表页面接口", exceptionMessage = "视频列表页面接口失败")
    @Log("视频列表页面接口")
    public RestResult list(@RequestBody @Valid VideoRequestDto requestDto){
        requestDto.setUserId(AppUtil.getCurrentUserId());
        return RestResult.ok().setResult(userMultimediaService.videoList(requestDto));
    }


    @PostMapping("/albumC")
    @ResponseBody
    @ControllerEndpoint(operation = "用户相册里的相片数接口", exceptionMessage = "用户相册里的相片数查询失败")
    @Log("用户相册里的相片数接口")
    public RestResult albumCount(@RequestBody  String userId){
        return RestResult.ok().setResult(userMultimediaService.getAlbumCount(userId));
    }


    @PostMapping("/album")
    @ResponseBody
    @ControllerEndpoint(operation = "用户相册查询接口", exceptionMessage = "用户相册查询接口失败")
    @Log("用户相册查询接口")
    public RestResult album(@RequestBody @Valid AlbumRequestDto requestDto){
        requestDto.setUserId(StringUtils.isBlank(requestDto.getUserId())?AppUtil.getCurrentUserId():requestDto.getUserId());
        return RestResult.ok().setResult(userMultimediaService.getAlbum(requestDto));
    }


    @PostMapping("/album_del")
    @ResponseBody
    @ControllerEndpoint(operation = "用户相片删除接口", exceptionMessage = "用户相片删除失败")
    @Log("用户相片删除接口")
    public RestResult albumDel(@RequestBody DeleteAlbumReqDto reqDto){
        if (StringUtils.isBlank(reqDto.getIds())){
            return RestResult.error("多媒体id不能为空");
        }
        List<Long> listIds = Arrays.asList(reqDto.getIds().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        userMultimediaService.albumDel(listIds);
        return RestResult.ok();
    }

}
