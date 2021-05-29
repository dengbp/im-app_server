package com.yr.net.app.pay.controller;

import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.pay.dto.PayReqDto;
import com.yr.net.app.pay.service.impl.WxPayService;
import com.yr.net.app.tools.HttpContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信支付
 *
 * @author dengbp
 * @since 2018-05-20
 **/
@Slf4j
@RestController
@Api(tags = "微信支付接口")
@RequestMapping("/api/wx/pay")
public class WxPayController {

    @Resource
    private WxPayService wxPayService;




    @PostMapping("app")
    @ControllerEndpoint(operation = "微信App支付", exceptionMessage = "微信App支付失败")
    @ResponseBody
    @Log("微信App支付接口")
    public RestResult appPay(@RequestBody PayReqDto pay) {
        log.info("开始执行下单...");
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        return RestResult.ok().setResult((wxPayService.appPay(request, pay)));
    }



    @PostMapping("query")
    @ControllerEndpoint(operation = "支付记录查询", exceptionMessage = "支付记录查询失败")
    @ResponseBody
    @Log("支付记录查询接口")
    public RestResult query() {
        log.info("套餐查询...");
        return RestResult.ok().setResult((wxPayService.query()));
    }

/*    @ApiOperation("微信App支付退款")
    @GetMapping("/app/refund")
    public RestResult appRefund(Long orderId) throws Exception {
         wxPayService.wxRefund(orderId);
         return RestResult.ok();
    }*/

 /*   @GetMapping("/qrcode")
    @ApiOperation("微信扫码支付预下单")
    public RestResult qrCodePay(Long orderId, HttpServletRequest request, HttpServletResponse response) {
        return wxPayService.qrCodePay(orderId, response, request);
    }

    @GetMapping("/xcx")
    @ApiOperation("小程序扫支付预下单")
    public RestResult xcxPay(Long orderId, HttpServletRequest request, HttpServletResponse response) {
        return wxPayService.xcxPay(orderId, request);
    }*/

    @ApiOperation("微信支付通知")
    @RequestMapping(value = "/notify", method = {RequestMethod.GET, RequestMethod.POST})
    public void appNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        wxPayService.notify(request, response);
    }

}
