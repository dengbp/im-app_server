package com.yr.net.app.log.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.log.dto.OperationReqDto;
import com.yr.net.app.log.service.IUserTrackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/log/user-track")
public class UserTrackController {

    @Resource
    private IUserTrackService userTrackService;

    @PostMapping("operation")
    @ControllerEndpoint(operation = "谁看过谁接口", exceptionMessage = "谁看过谁接口失败")
    @ResponseBody
    @Log("谁看过谁接口")
    public RestResult operationBy(@RequestBody @Valid OperationReqDto reqDto){
        return RestResult.ok().setResult(userTrackService.findByUserId(reqDto.getUserId(),reqDto.getOperatorType()));
    }

}
