package com.yr.net.app.log.controller;


import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.log.service.IUserExchangeLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Description 用户交易记录控制器
 * @Author dengbp
 * @Date 8:18 PM 6/2/21
 **/
@Slf4j
@RestController
@RequestMapping("/log/user-exchange-log")
public class UserExchangeLogController {

    @Resource
    private IUserExchangeLogService userExchangeLogService;
/*
    @RequestMapping(value = "/record", method = {RequestMethod.GET, RequestMethod.POST})
    @ControllerEndpoint(operation = "用户交易记录保存", exceptionMessage = "用户交易记录保存失败")
    @ResponseBody
    @Log("用户交易记录保存接口")
    public void record(@RequestBody @Valid ExchangeLogReqDto reqDto) {
        log.info("用户交易记录保存");
        userExchangeLogService.insert(reqDto);
    }*/


}
