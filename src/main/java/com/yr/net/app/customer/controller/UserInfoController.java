package com.yr.net.app.customer.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.entity.QueryRequestPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.AddBaseInfoRequestDto;
import com.yr.net.app.customer.dto.OnlineRequestDto;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserBaseInfoResponseDto;
import com.yr.net.app.customer.entity.UserCoordinate;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserCoordinateService;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.pojo.Position;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author dengbp
 */
@Slf4j
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
    public RestResult getOnlineUsers(@RequestBody @Valid UserBaseInfoRequestDto requestDto)throws AppException{
        UserBaseInfoResponseDto responseDto = userInfoService.getUserInfo(requestDto);
        log.info("用户基本信息:{}", JSONObject.toJSONString(responseDto));
        return RestResult.ok().setResult(responseDto);
    }


    @PostMapping("online")
    @ControllerEndpoint(operation = "查询用户在线", exceptionMessage = "查询用户在线失败")
    @ResponseBody
    @Log("查询用户在线")
    public RestResult getOnlineUsers(@RequestBody @Valid OnlineRequestDto query)throws AppException{
        return RestResult.ok().setResult(userInfoService.findOnline(query));
    }

    @PostMapping("vicinity")
    @ControllerEndpoint(operation = "查询附近在线用户", exceptionMessage = "查询附近在线用户失败")
    @ResponseBody
    @Log("查询附近用户")
    public RestResult vicinity(@RequestBody QueryRequestPage page)throws AppException{
        if (page.getPageNum()==0 || page.getPageSize()==0) {
            return RestResult.error("page信息不能为空");
        }
        log.info("请求入参：PageNum:{},PageSize:{}",page.getPageNum(),page.getPageSize());
        String userId = AppUtil.getCurrentUserId();
        log.info("用户附近查询，用户id={}",userId);
        UserCoordinate coordinate = userCoordinateService.findByUserId(userId);
        Position position = new Position(coordinate.getLongitude(),coordinate.getLatitude());
        return RestResult.ok().setResult(userInfoService.findNear(page,AppUtil.getCurrentUserId(),position));
    }


    @PostMapping("base_add")
    @ControllerEndpoint(operation = "第一次个人信息完善", exceptionMessage = "第一次个人信息完善失败")
    @ResponseBody
    @Log("第一次个人信息完善")
    public RestResult baseAdd(@RequestBody @Valid AddBaseInfoRequestDto baseInfoRequestDto)throws AppException{
        if (baseInfoRequestDto.getBirthday()==null || baseInfoRequestDto.getBirthday() > Integer.parseInt(DateUtil.current_yyyyMMdd())){
            return RestResult.error("出生日期不能为空或大于当前时间");
        }
        if (StringUtils.isBlank(baseInfoRequestDto.getUserName())){
            return RestResult.error("请输入昵称");
        }
        if (baseInfoRequestDto.getBirthday()==null){
            return RestResult.error("请选择生日");
        }
        if (baseInfoRequestDto.getSex()==null){
            return RestResult.error("请选择性别");
        }
        if (baseInfoRequestDto.getBodyHeight()==null){
            return RestResult.error("请输入身高");
        }
        if (baseInfoRequestDto.getBodyHeight()==null){
            return RestResult.error("请输入体重");
        }
        baseInfoRequestDto.setUserId(AppUtil.getCurrentUserId());
        userInfoService.updateByUserId(baseInfoRequestDto);
        return RestResult.ok();
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
