package com.yr.net.app.customer.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserLoveRequestSetDto;
import com.yr.net.app.customer.service.IUserLoveRequestService;
import com.yr.net.app.tools.AppUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/customer/user-love-request")
public class UserLoveRequestController {

    @Resource
    private IUserLoveRequestService userLoveRequestService;

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


}
