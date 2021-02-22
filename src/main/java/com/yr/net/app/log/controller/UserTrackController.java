package com.yr.net.app.log.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.log.dto.OperationReportDto;
import com.yr.net.app.log.dto.OperationReqDto;
import com.yr.net.app.log.service.IUserTrackService;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户轨迹控制器
 * @author dengbp
 */
@RestController
@RequestMapping("/log/user-track")
public class UserTrackController {

    @Resource
    private IUserTrackService userTrackService;

    @PostMapping("operation")
    @ControllerEndpoint(operation = "谁看过谁相关信息接口", exceptionMessage = "谁看过谁接口失败")
    @ResponseBody
    @Log("谁看过谁相关信息接口")
    public RestResult operationBy(@RequestBody @Valid OperationReqDto reqDto){
        return RestResult.ok().setResult(userTrackService.findByUserId(reqDto.getUserId(),reqDto.getOperatorType()));
    }

    @PostMapping("save")
    @ControllerEndpoint(operation = "用户操作记录上报", exceptionMessage = "用户操作记录上报失败")
    @ResponseBody
    @Log("用户操作记录上报接口")
    public RestResult save(@RequestBody @Valid OperationReportDto reqDto){
        if (StringUtils.isBlank(reqDto.getUserId())){
            reqDto.setUserId(AppUtil.getCurrentUserId());
        }
        userTrackService.saveTrack(reqDto);
        return RestResult.ok();
    }

}
