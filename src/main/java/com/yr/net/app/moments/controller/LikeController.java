package com.yr.net.app.moments.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.moments.dto.MomentsLikeReqDto;
import com.yr.net.app.moments.dto.UserMomentsReqDto;
import com.yr.net.app.moments.service.ILikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Description 点赞控制器
 * @Author dengbp
 * @Date 20:40 2020-12-17
 **/
@RestController
@RequestMapping("/moments/like")
public class LikeController {

    @Resource
    private ILikeService likeService;

    @PostMapping("/add")
    @ControllerEndpoint(operation = "动态点赞", exceptionMessage = "动态点赞失败")
    @ResponseBody
    @Log("动态点赞接口")
    public RestResult add(@RequestBody @Valid MomentsLikeReqDto reqDto){
        likeService.add(reqDto);
        return RestResult.ok();
    }
}
