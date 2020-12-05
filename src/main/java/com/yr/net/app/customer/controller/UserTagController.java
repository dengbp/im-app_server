package com.yr.net.app.customer.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.UserTagRequestDto;
import com.yr.net.app.customer.service.IUserTagService;
import com.yr.net.app.tools.AppUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/customer/user-tag")
public class UserTagController {

    @Resource
    private IUserTagService userTagService;

    @PostMapping("/more")
    @ControllerEndpoint(operation = "查询用户爱好信息", exceptionMessage = "查询用户爱好信息失败")
    @ResponseBody
    @Log("查询用户爱好信息")
    public RestResult getOnlineUsers(String userId) {
        return RestResult.ok().setResult(userTagService.findByUserId(userId));
    }


    @PostMapping("/interest/edit")
    @ControllerEndpoint(operation = "用户爱好设置", exceptionMessage = "用户爱好设置失败")
    @ResponseBody
    @Log("用户爱好设置")
    public RestResult edit(@Valid UserTagRequestDto requestDto) {
        userTagService.setUserTag(AppUtil.getCurrentUserId(),requestDto);
        return RestResult.ok();
    }
}
