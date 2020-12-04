package com.yr.net.app.customer.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.AddBaseInfoRequestDto;
import com.yr.net.app.customer.dto.OnlineRequestDto;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.entity.UserCoordinate;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserCoordinateService;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.pojo.Position;
import com.yr.net.app.tools.AppUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/customer/user-info/")
public class UserInfoController {

    @Autowired
    IUserInfoService  userInfoService;

    @Autowired
    IUserCoordinateService userCoordinateService;


    @PostMapping("base")
    @ControllerEndpoint(operation = "查询用户基本信息", exceptionMessage = "查询用户基本信息失败")
    @ResponseBody
    @Log("查询用户基本信息")
    public RestResult getOnlineUsers(@Valid UserBaseInfoRequestDto requestDto)throws AppException{
        return RestResult.ok().setResult(userInfoService.getUserInfo(requestDto));
    }


    @PostMapping("online")
    @ControllerEndpoint(operation = "查询用户在线", exceptionMessage = "查询用户在线失败")
    @ResponseBody
    @Log("查询用户在线")
    public RestResult getOnlineUsers(@Valid OnlineRequestDto query)throws AppException{
        return RestResult.ok().setResult(userInfoService.findOnline(query));
    }

    @PostMapping("vicinity")
    @ControllerEndpoint(operation = "查询附近在线用户", exceptionMessage = "查询附近在线用户失败")
    @ResponseBody
    @Log("查询附近用户")
    public RestResult vicinity()throws AppException{
        String userId = AppUtil.getCurrentUserId();
        UserCoordinate coordinate = userCoordinateService.findByUserId(userId);
        Position position = new Position(coordinate.getLongitude(),coordinate.getLatitude());
        return RestResult.ok().setResult(userInfoService.findNear(AppUtil.getCurrentUserId(),position));
    }


    @PostMapping("addBaseInfo")
    @ControllerEndpoint(operation = "完善基本信息", exceptionMessage = "完善基本信息失败")
    @ResponseBody
    @Log("完善基本信息")
    public RestResult addMoreInfo(@Valid AddBaseInfoRequestDto baseInfoRequestDto)throws AppException{
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(baseInfoRequestDto,userInfo);
        return RestResult.ok().setResult(userInfoService.save(userInfo));
    }

    @PostMapping("base_add")
    @ControllerEndpoint(operation = "第一次个人信息完善", exceptionMessage = "第一次个人信息完善失败")
    @ResponseBody
    @Log("第一次个人信息完善")
    public RestResult baseAdd(@Valid AddBaseInfoRequestDto baseInfoRequestDto)throws AppException{
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(baseInfoRequestDto,userInfo);
        return RestResult.ok().setResult(userInfoService.save(userInfo));
    }

    @RequestMapping("icon/setting")
    @ControllerEndpoint(operation = "用户头像设置", exceptionMessage = "用户头像设置失败")
    @ResponseBody
    @Log("用户头像设置")
    public RestResult iconSetting(@RequestParam("file") MultipartFile file)throws AppException{
        if (file==null) {
            return RestResult.error("file为空");
        }
        return RestResult.ok().setResult(userInfoService.updateIcon(file));
    }

}
