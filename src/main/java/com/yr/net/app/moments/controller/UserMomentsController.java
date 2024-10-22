package com.yr.net.app.moments.controller;


import com.alibaba.fastjson.JSONObject;
import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.log.dto.OperationReqDto;
import com.yr.net.app.log.service.IUserTrackService;
import com.yr.net.app.message.service.IUserRelationService;
import com.yr.net.app.moments.dto.AddMomentDto;
import com.yr.net.app.moments.dto.AddSimpleMomentDto;
import com.yr.net.app.moments.dto.UserMomentsReqDto;
import com.yr.net.app.moments.service.IUserMomentsService;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Description 用户动态˙控制器
 * @Author dengbp
 * @Date 20:40 2020-12-17
 **/
@RestController
@RequestMapping("/moments/user-moments")
public class UserMomentsController {

    @Autowired
    private IUserMomentsService userMomentsService;


    @Resource
    private IUserRelationService userRelationService;

    @PostMapping("/add")
    @ControllerEndpoint(operation = "发布动态", exceptionMessage = "发布动态失败")
    @ResponseBody
    @Log("发布动态接口")
    public RestResult add(@RequestBody  @Valid AddMomentDto momentDto){
        userMomentsService.add(momentDto);
        return RestResult.ok();
    }

    @PostMapping("/sync")
    @ControllerEndpoint(operation = "发布动态数据同步", exceptionMessage = "发布动态数据同步失败")
    @ResponseBody
    @Log("发布动态数据同步接口")
    public RestResult sync(@RequestBody AddSimpleMomentDto momentDto){
        userMomentsService.add(momentDto);
        return RestResult.ok();
    }

    @PostMapping("/delete")
    @ControllerEndpoint(operation = "用户动态", exceptionMessage = "用户动态失败")
    @ResponseBody
    @Log("用户动态")
    public RestResult delete(@RequestBody JSONObject jsonParam){
        if (jsonParam.get("id") == null){
            return RestResult.error("id不能为空");
        }
        userMomentsService.delete(jsonParam.getLong("id"));
        return RestResult.ok();
    }


    @PostMapping("/list")
    @ControllerEndpoint(operation = "用户动态", exceptionMessage = "用户动态失败")
    @ResponseBody
    @Log("用户动态")
    public RestResult mottoEdit(@RequestBody  @Valid UserMomentsReqDto reqDto){
        return RestResult.ok().setResult(userMomentsService.findUserMoments(reqDto));
    }



    @PostMapping("/fans")
    @ControllerEndpoint(operation = "用户粉丝量", exceptionMessage = "用户粉丝量查询失败")
    @ResponseBody
    @Log("用户粉丝量查询接口")
    public RestResult fans(@RequestBody  OperationReqDto reqDto){
        String userId = StringUtils.isBlank(reqDto.getUserId())? AppUtil.getCurrentUserId():reqDto.getUserId();
        return RestResult.ok().setResult(userRelationService.getFans(userId));
    }

    @PostMapping("/follow")
    @ControllerEndpoint(operation = "用户关注量", exceptionMessage = "用户关注量查询失败")
    @ResponseBody
    @Log("用户关注量查询接口")
    public RestResult follow(@RequestBody OperationReqDto reqDto){
        String userId = StringUtils.isBlank(reqDto.getUserId())? AppUtil.getCurrentUserId():reqDto.getUserId();
        return RestResult.ok().setResult(userRelationService.getFollows(userId));
    }



}
