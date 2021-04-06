package com.yr.net.app.message.controller;


import com.alibaba.fastjson.JSONObject;
import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.message.controller.dto.FollowReqDto;
import com.yr.net.app.message.service.IUserRelationService;
import com.yr.net.app.tools.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description 用户关注控制器
 * @Author dengbp
 * @Date 1:50 AM 3/29/21
 **/
@RestController
@RequestMapping("/message/user-relation")
@Slf4j
public class UserRelationController {

    @Resource
    private IUserRelationService userRelationService;

    @PostMapping("/follow")
    @ControllerEndpoint(operation = "用户关注操作", exceptionMessage = "用户关注操作询失败")
    @ResponseBody
    @Log("用户关注接口")
    public RestResult follow(@RequestBody FollowReqDto reqDto){
        userRelationService.follow(reqDto);
        return RestResult.ok();
    }


    @PostMapping("/state")
    @ControllerEndpoint(operation = "查询是否已关注", exceptionMessage = "查询是否已关注失败")
    @ResponseBody
    @Log("查询是否已关注接口")
    public RestResult stateReq(@RequestBody JSONObject jsonParam){
        if (StringUtils.isBlank(jsonParam.getString("byOperatorId"))){
            return RestResult.error("byOperatorId 不能为空");
        }
        return RestResult.ok().setResult( userRelationService.followState(jsonParam.getString("byOperatorId")));
    }

}
