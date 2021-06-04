package com.yr.net.app.pay.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.pay.service.IUserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/pay/user-account")
@Slf4j
public class UserAccountController {

    @Resource
    private IUserAccountService userAccountService;

    @PostMapping("ranking")
    @ControllerEndpoint(operation = "财富排行", exceptionMessage = "用户财富排行失败")
    @ResponseBody
    @Log("用户财富排行接口")
    public RestResult ranking()throws AppException {
        return RestResult.ok().setResult(userAccountService.ranking());
    }


    @PostMapping("item")
    @ControllerEndpoint(operation = "平台内部支付", exceptionMessage = "平台内部支付失败")
    @ResponseBody
    @Log("平台内部支付接口")
    public RestResult item(@RequestBody ExchangeLogReqDto pay){
        log.info("开始执行下单...");
        if (userAccountService.pay(pay)){
            return RestResult.ok();
        }
        return RestResult.error("支付失败");
    }

    @PostMapping("balance")
    @ControllerEndpoint(operation = "余额查询", exceptionMessage = "余额查询失败")
    @ResponseBody
    @Log("余额查询接口")
    public RestResult balance()throws AppException {
        return RestResult.ok().setResult(userAccountService.getBalance());
    }

}
