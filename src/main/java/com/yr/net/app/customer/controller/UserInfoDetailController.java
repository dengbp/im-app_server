package com.yr.net.app.customer.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserDetailSetRequestDto;
import com.yr.net.app.customer.service.IUserInfoDetailService;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/customer/user-info-detail")
public class UserInfoDetailController {

    @Resource
    private IUserInfoDetailService userInfoDetailService;

    @PostMapping("/more")
    @ControllerEndpoint(operation = "查询用户详细信息", exceptionMessage = "查询用户详细信息失败")
    @ResponseBody
    @Log("查询用户详细信息")
    public RestResult more(@Valid UserBaseInfoRequestDto requestDto) {
        return RestResult.ok().setResult(userInfoDetailService.findUserDetail(requestDto));
    }

    @PostMapping("/motto/edit")
    @ControllerEndpoint(operation = "内心独白设置", exceptionMessage = "内心独白设置失败")
    @ResponseBody
    @Log("内心独白设置")
    public RestResult mottoEdit(String motto){
        if (StringUtils.isBlank(motto)) {
            return RestResult.error("内容不能为空");
        }
        userInfoDetailService.updateMotto(AppUtil.getCurrentUserId(),motto);
        return RestResult.ok();
    }

    @PostMapping("/more/edit")
    @ControllerEndpoint(operation = "详细信息编辑", exceptionMessage = "详细信息编辑失败")
    @ResponseBody
    @Log("详细信息编辑")
    public RestResult detailEdit(@Valid UserDetailSetRequestDto requestDto){
        userInfoDetailService.updateDetail(AppUtil.getCurrentUserId(),requestDto);
        return RestResult.ok();
    }

}
