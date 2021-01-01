package com.yr.net.app.customer.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.LoveReportRequestDto;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserLoveRequestSetDto;
import com.yr.net.app.customer.service.IUserLikePartnerService;
import com.yr.net.app.customer.service.IUserLoveRequestService;
import com.yr.net.app.tools.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@Slf4j
@RestController
@RequestMapping("/customer/user-love-request")
public class UserLoveRequestController {

    @Resource
    private IUserLoveRequestService userLoveRequestService;

    @Resource
    private IUserLikePartnerService userLikePartnerService;

    @PostMapping("/info")
    @ControllerEndpoint(operation = "查询用户征友要求信息", exceptionMessage = "查询用户征友要求信息失败")
    @ResponseBody
    @Log("查询用户征友要求信息")
    public RestResult info(@RequestBody @Valid UserBaseInfoRequestDto requestDto)throws AppException {
        return RestResult.ok().setResult(userLoveRequestService.findByUserId(requestDto));
    }

    @PostMapping("/edit")
    @ControllerEndpoint(operation = "征友要求编辑", exceptionMessage = "征友要求编辑失败")
    @ResponseBody
    @Log("征友要求编辑")
    public RestResult detailEdit(@RequestBody @Valid UserLoveRequestSetDto requestDto){
        userLoveRequestService.setLoveRequest(AppUtil.getCurrentUserId(),requestDto);
        return RestResult.ok();
    }

    @PostMapping("/love/report")
    @ControllerEndpoint(operation = "附近人用户每次左右滑动喜欢或不喜欢接口", exceptionMessage = "设置失败")
    @ResponseBody
    @Log("附近人用户每次左右滑动喜欢或不喜欢接口")
    public RestResult dd(@RequestBody @Valid LoveReportRequestDto reportRequestDto){
        log.info("附近人用户每次左右滑动喜欢或不喜欢接口入参：{}",JSONObject.toJSONString(reportRequestDto));
        userLikePartnerService.add(reportRequestDto);
        return RestResult.ok();
    }


}
