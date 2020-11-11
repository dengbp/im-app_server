package com.yr.net.app.customer.controller;


import com.yr.net.app.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.OnlineQuery;
import com.yr.net.app.customer.service.IUserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/customer/user-info")
public class UserInfoController {

    @Autowired
    IUserInfoService userInfoService;

    @PostMapping("/online")
    @ControllerEndpoint(operation = "查询用户在线", exceptionMessage = "查询用户在线失败")
    @ResponseBody
    public RestResult getOnlineUsers(@Valid OnlineQuery query)throws AppException{
        return RestResult.ok().setResult(userInfoService.findOnline(query));
    }

}
