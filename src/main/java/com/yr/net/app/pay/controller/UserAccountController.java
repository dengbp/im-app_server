package com.yr.net.app.pay.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.pay.service.IUserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
