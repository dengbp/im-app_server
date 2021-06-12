package com.yr.net.app.pay.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.entity.UserWithdrawalsLog;
import com.yr.net.app.log.service.IUserWithdrawalsLogService;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.pay.dto.WithdrawReqDto;
import com.yr.net.app.pay.service.IUserAccountService;
import com.yr.net.app.tools.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/pay/user-account")
@Slf4j
public class UserAccountController {

    @Resource
    private IUserAccountService userAccountService;
    @Resource
    private IUserWithdrawalsLogService userWithdrawalsLogService;


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
        return RestResult.error("余额不足，请去充值");
    }

    @PostMapping("balance")
    @ControllerEndpoint(operation = "余额查询", exceptionMessage = "余额查询失败")
    @ResponseBody
    @Log("余额查询接口")
    public RestResult balance()throws AppException {
        return RestResult.ok().setResult(userAccountService.getBalance());
    }




    @PostMapping("transac/log")
    @ControllerEndpoint(operation = "交易记录(帐单)查询", exceptionMessage = "交易记录(帐单)查询失败")
    @ResponseBody
    @Log("交易记录(帐单)查询接口")
    public RestResult transacLog()throws AppException {
        return RestResult.ok().setResult(userAccountService.transacLog());
    }



    @PostMapping("withdraw")
    @ControllerEndpoint(operation = "余额提现", exceptionMessage = "余额提现失败")
    @ResponseBody
    @Log("余额提现接口")
    public RestResult withdraw(@RequestBody @Valid WithdrawReqDto dto)throws AppException {
        if(userWithdrawalsLogService.withdraw(dto)){
            return RestResult.ok().setMessage("提现预约成功,T+1到帐");
        }
        return RestResult.error("提现预约失败,余额不足");
    }

    @PostMapping("withdraw/log")
    @ControllerEndpoint(operation = "余额提现记录", exceptionMessage = "余额提现记录查询失败")
    @ResponseBody
    @Log("余额提现记录查询接口")
    public RestResult withdrawLog()throws AppException {
        List<UserWithdrawalsLog> logs = userWithdrawalsLogService.list(new LambdaQueryWrapper<UserWithdrawalsLog>().eq(UserWithdrawalsLog::getUserId, AppUtil.getCurrentUserId()));
        return RestResult.ok().setResult(logs==null?new ArrayList<>():logs);
    }





}
