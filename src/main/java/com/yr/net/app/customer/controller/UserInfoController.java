package com.yr.net.app.customer.controller;


import com.yr.net.app.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.AddBaseInfoRequestDto;
import com.yr.net.app.customer.dto.OnlineRequestDto;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.tools.AppUtil;
import org.springframework.beans.BeanUtils;
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
    public RestResult getOnlineUsers(@Valid OnlineRequestDto query)throws AppException{
        return RestResult.ok().setResult(userInfoService.findOnline(query));
    }
    @PostMapping("/vicinity")
    @ControllerEndpoint(operation = "查询附近在线用户", exceptionMessage = "查询附近在线用户失败")
    @ResponseBody
    public RestResult vicinity(@Valid OnlineRequestDto query)throws AppException{
        return RestResult.ok().setResult(userInfoService.findOnline(query));
    }


    @PostMapping("/addBaseInfo")
    @ControllerEndpoint(operation = "完善基本信息", exceptionMessage = "完善基本信息失败")
    @ResponseBody
    public RestResult addBaseInfo(@Valid AddBaseInfoRequestDto baseInfoRequestDto)throws AppException{
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(baseInfoRequestDto,userInfo);
        return RestResult.ok().setResult(userInfoService.save(userInfo));
    }



}
