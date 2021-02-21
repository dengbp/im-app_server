package com.yr.net.app.log.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.log.dto.SignLogReqDto;
import com.yr.net.app.log.service.IUserSignLogService;
import com.yr.net.app.tools.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录轨迹控制器
 * @author dengbp
 */
@RestController
@RequestMapping("/log/user-sign-log")
@Slf4j
public class UserSignLogController {

    @Resource
    private IUserSignLogService userSignLogService;

    @PostMapping("/list")
    @ControllerEndpoint(operation = "登录记录查询", exceptionMessage = "登录记录查询失败")
    @ResponseBody
    @Log("登录记录查询接口")
    public RestResult list(@RequestBody SignLogReqDto reqDto){
        return RestResult.ok().setResult(userSignLogService.searchByUserId(StringUtils.isBlank(reqDto.getUserId())? AppUtil.getCurrentUserId():reqDto.getUserId()));
    }

}
