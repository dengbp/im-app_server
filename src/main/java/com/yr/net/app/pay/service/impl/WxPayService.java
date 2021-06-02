package com.yr.net.app.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.configure.AppProperties;
import com.yr.net.app.configure.WxConfig;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.pay.dto.PayReqDto;
import com.yr.net.app.pay.entity.OrderBill;
import com.yr.net.app.pay.entity.UserOrder;
import com.yr.net.app.pay.service.IOrderBillService;
import com.yr.net.app.pay.service.IOrderSeqService;
import com.yr.net.app.pay.service.IOrderService;
import com.yr.net.app.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengbp
 * @since 2018-05-22
 **/
@Slf4j
@Service
public class WxPayService {

    @Resource
    private IOrderService orderService;

    @Autowired
    IUserInfoService userInfoService;

    @Resource
    private IOrderBillService orderBillService;

    @Resource
    private AppProperties appProperties;

    @Resource
    private IOrderSeqService orderSeqService;

    @Resource
    private WxConfig wxConfig;


    public List<UserOrder> query()throws AppException{
        return orderService.list(new LambdaQueryWrapper<UserOrder>().eq(UserOrder::getUserId,AppUtil.getCurrentUserId()));
    }

    /**
     * 微信App支付，请求微信下单接口生成预付款信息返回 prepay_id 给客户端
     *
     * @param request
     * @param payReqDto
     */
    public Map<String, String> appPay(HttpServletRequest request, PayReqDto payReqDto) throws Exception {
        log.info("开始调微信统一下单接口");
        // 封装参数返回App端
        Map<String, String> result = new HashMap<>();
        // 设置客户端的ip地址
        String spbill_create_ip = PaymentUtils.getIpAddress(request);
        if (!PaymentUtils.isIp(spbill_create_ip)) {
            spbill_create_ip = "127.0.0.1";
        }
        UserOrder order = UserOrder.createOrder(DateUtil.current_yyyyMMddHHmmss().concat(orderSeqService.getSeq()+""),spbill_create_ip,payReqDto);
        WXPay wxpay = new WXPay(wxConfig);
        Map<String, String> params = new HashMap<>();
        // 商品描述
        params.put("body", "App-webChat-pay");
        // 商户订单号
        params.put("out_trade_no", order.getOutTradeNo());
        //params.put("sign_type", "MD5");
        // 总金额(分)
        params.put("total_fee", order.getTotalFee().toString());
        // 终端IP
        params.put("spbill_create_ip", spbill_create_ip);
        // 通知地址
        params.put("notify_url", appProperties.getWx().getNotify_url());
        // 交易类型:JS_API=公众号支付、NATIVE=扫码支付、APP=app支付
        params.put("trade_type", "APP");
       /* *//** 签名 *//*
        String sign = PaymentUtils.sign(params, appProperties.getWx().getApi_key());
        params.put("sign", sign);
        String xmlData = PaymentUtils.mapToXml(params);
        log.info("请求入参:{}",xmlData);
        */
        Map<String, String> wxOrderResult = wxpay.unifiedOrder(params);
        if("FAIL".equals(wxOrderResult.get("return_code"))){
            log.error(wxOrderResult.get("return_msg"));
            throw new RuntimeException(wxOrderResult.get("return_msg"));
        }
        if (StringUtils.isBlank(wxOrderResult.get("prepay_id"))) {
            log.error("微信支付下单成功后，返回的prepay_id为空");
            throw new RuntimeException("微信支付下单成功后，返回的prepay_id为空");
        }
       /* String wxRetXmlData = HttpUtil.sendPostXml(appProperties.getWx().getCreate_order_url(), xmlData, null);
        Map wxRetMapData = PaymentUtils.xmlToMap(wxRetXmlData);*/
        log.info("调微信统一下单返回结果: {}", wxOrderResult);

        result.put("appid", appProperties.getWx().getApp_id());
        result.put("partnerid", appProperties.getWx().getMch_id());
        result.put("prepayid", wxOrderResult.get("prepay_id"));
        result.put("noncestr", wxOrderResult.get("nonce_str"));
        result.put("timestamp", RandomUtil.getTimestamp()+"");
        result.put("package", "Sign=WXPay");
        /** 对返回给App端的数据进行签名 */
        String signature = WXPayUtil.generateSignature(result, appProperties.getWx().getApi_key());
        result.put("sign", signature);
        //result.put("sign", PaymentUtils.sign(result, appProperties.getWx().getApi_key()));
        orderService.save(order);
        return result;
    }

    /**
     * 微信支付通知接口 回调数据示例
     *
     * <xml>
     * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
     * <attach><![CDATA[支付测试]]></attach>
     * <bank_type><![CDATA[CFT]]></bank_type>
     * <fee_type><![CDATA[CNY]]></fee_type>
     * <is_subscribe><![CDATA[Y]]></is_subscribe>
     * <mch_id><![CDATA[10000100]]></mch_id>
     * <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>
     * <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>
     * <out_trade_no><![CDATA[1409811653]]></out_trade_no>
     * <result_code><![CDATA[SUCCESS]]></result_code>
     * <return_code><![CDATA[SUCCESS]]></return_code>
     * <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>
     * <sub_mch_id><![CDATA[10000100]]></sub_mch_id>
     * <time_end><![CDATA[20140903131540]]></time_end>
     * <total_fee>1</total_fee><coupon_fee><![CDATA[10]]></coupon_fee>
     * <trade_type><![CDATA[JSAPI]]></trade_type>
     * <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>
     * </xml>
     *
     * <p>
     * 收到微信通知后应返回微信已经收到通知，不然微信会回调多次 可能会出现保存多次订单流水的现象
     * <p>
     * 返回给微信通知示例如下
     *
     * <xml>
     * <return_code><![CDATA[SUCCESS]]></return_code>
     * <return_msg><![CDATA[OK]]></return_msg>
     * </xml>
     *
     * @param request
     * @param response
     */
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String wxRetXml = PaymentUtils.getRequestData(request);
        log.info("微信回调：{}",wxRetXml);
        Map<String, String> wxRetMap = PaymentUtils.xmlToMap(wxRetXml);

        // 当返回的return_code为SUCCESS则回调成功
        if ("SUCCESS".equalsIgnoreCase(wxRetMap.get("return_code"))) {
            // 通知微信收到回调
            String resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();

            // TODO 保存订单流水 具体细节待实现
            orderBillService.save(new OrderBill());

            log.info("notify success");
        } else {
            log.error("notify failed");
        }
    }

    /**
     * {
     *   "appId":"wxxxx",
     *   "partnerid":"xxxx",
     *   "noncestr":"f7382ae04f15cf4e5fd5fbecf342",
     *   "prepayid":"xxxx",
     *   "timeStamp":"20180906095441"
     *   "package":"Sign=WXPay",
     *   "sign":"AE3E21CCB1DF50B65A0531000E9EF788"
     * }
     */

    /**
     * 微信扫码支付
     *
     * @param orderId
     * @param response
     * @param request
     * @return
     * @throws Exception
     *//*
    public Result qrCodePay(Long orderId, HttpServletResponse response, HttpServletRequest request) {
        // 校验订单信息
        Order order = orderService.findOne(orderId);
        if (order.getStatus() != OrderStatus.CREATE.getStatus()) {
            log.error(ExceptionMessage.ORDER_STATUS_INCORRECTNESS + " orderId: {}", orderId);
            throw new ValidateException(ExceptionMessage.ORDER_STATUS_INCORRECTNESS);
        }

        String nonce_str = RandomUtil.randomStr(12);
        String outTradeNo = 1 + RandomUtil.randomNum(15);
        String spbill_create_ip = PaymentUtils.getIpAddress(request);
        if (!PaymentUtils.isIp(spbill_create_ip)) {
            spbill_create_ip = "127.0.0.1";
        }
        Map<String, Object> params = new TreeMap<>();
        // 扫码支付需要参数
        params.put("appid", appProperties.getWx().getApp_id());
        params.put("mch_id", appProperties.getWx().getMch_id());
        params.put("nonce_str", nonce_str);
        params.put("body", "微信扫码支付");
        params.put("out_trade_no", outTradeNo);
        params.put("total_fee", order.getTotalFee());
        params.put("spbill_create_ip", spbill_create_ip);
        params.put("notify_url", appProperties.getWx().getRefund_url());
        // 交易类型:JS_API=公众号支付、NATIVE=扫码支付、APP=app支付
        params.put("trade_type", "NATIVE");
        String sign = PaymentUtils.sign(params, appProperties.getWx().getApi_key());
        params.put("sign", sign);

        String responseXml = HttpUtil.sendPostXml(appProperties.getWx().getCreate_order_url(), PaymentUtils.mapToXml(params), null);
        Map<String, String> responseMap = PaymentUtils.xmlToMap(responseXml);

        Assert.notNull(responseMap, ExceptionMessage.XML_DATA_INCORRECTNESS.getMessage());
        // return_code为微信返回的状态码，SUCCESS表示成功，return_msg 如非空，为错误原因 签名失败 参数格式校验错误
        if ("SUCCESS".equalsIgnoreCase(responseMap.get("return_code")) && "SUCCESS".equals(responseMap.get("result_code"))) {
            log.info("wx pre pay success response: {}", responseMap);
            // 二维码中需要包含微信返回的信息
            PaymentUtils.createQRCode(responseMap.get("code_url"), response);
            // TODO 保存订单信息

            return Result.success(ExceptionMessage.SUCCESS.getMessage());
        }
        log.error("wx pre pay error response: {}", responseMap);
        return Result.error(ExceptionMessage.WX_NATIVE_PRE_PAY_FAILED);
    }


    *//**
     * 微信退款
     *
     * @param orderId
     * @return
     * @throws Exception
     *//*
    public Result wxRefund(Long orderId) throws Exception {

        // 校验订单信息
        Order order = orderService.findOne(orderId);
        if (order.getStatus() != OrderStatus.CREATE.getStatus()) {
            log.error(ExceptionMessage.ORDER_STATUS_INCORRECTNESS + " orderId: {}", orderId);
            throw new ValidateException(ExceptionMessage.ORDER_STATUS_INCORRECTNESS);
        }

        String nonceStr = RandomUtil.randomStr(15);
        String out_refund_no = RandomUtil.randomStr(15);

        SortedMap<String, Object> params = new TreeMap<>();
        // 公众账号ID
        params.put("appid", appProperties.getWx().getApp_id());
        // 商户号
        params.put("mch_id", appProperties.getWx().getMch_id());
        // 随机字符串
        params.put("nonce_str", nonceStr);
        // 商户订单号
        params.put("out_trade_no", order.getOutTradeNo());
        // 订单金额
        params.put("total_fee", order.getTotalFee().toString());
        // 商户退款单号
        params.put("out_refund_no", out_refund_no);
        // 退款原因
        params.put("refund_fee", order.getTotalFee().toString());
        // 退款结果通知url
        params.put("notify_url", appProperties.getWx().getRefund_notify_url());
        // 签名
        params.put("sign", PaymentUtils.sign(params, appProperties.getWx().getApi_key()));
        String data = PaymentUtils.mapToXml(params);

        // 微信退款需要证书
        CloseableHttpClient httpClient = HttpUtil.sslHttpsClient(appProperties.getWx().getCertificate_path(), appProperties.getWx().getApi_key());

        // 向微信发起退款
        String responseXml = HttpUtil.sendSslXmlPost(appProperties.getWx().getRefund_url(), data, null, httpClient);

        Map<String, String> responseMap = PaymentUtils.xmlToMap(responseXml);
        Assert.notNull(responseMap, ExceptionMessage.XML_DATA_INCORRECTNESS.getMessage());
        // return_code为微信返回的状态码，SUCCESS表示申请退款成功，return_msg 如非空，为错误原因 签名失败 参数格式校验错误
        if ("SUCCESS".equalsIgnoreCase(responseMap.get("return_code"))) {
            log.info("wx refund success response:{}", responseMap);
            // 修改订单状态为退款保存退款订单等操作
            return Result.error(ExceptionMessage.SUCCESS);
        }
        log.error("wx refund error response:{}", responseMap);
        return Result.error(ExceptionMessage.WX_REFUND_FAILED);
    }

    *//**
     * 微信小程序支付
     *
     * @param orderId
     * @param request
     *//*
    public Result xcxPay(Long orderId, HttpServletRequest request) {
        Order order = orderService.findOne(orderId);
        User user = userService.findOne(order.getUserId());
        String nonce_str = RandomUtil.randomNum(12);
        String outTradeNo = 1 + RandomUtil.randomNum(11);
        String spbill_create_ip = PaymentUtils.getIpAddress(request);
        if (!PaymentUtils.isIp(spbill_create_ip)) {
            spbill_create_ip = "127.0.0.1";
        }
        // 小程序支付需要参数
        SortedMap<String, Object> reqMap = new TreeMap<>();
        reqMap.put("appid", appProperties.getWx().getApp_id());
        reqMap.put("mch_id", appProperties.getWx().getMch_id());
        reqMap.put("nonce_str", nonce_str);
        reqMap.put("body", "Mimi Programing Pay");
        reqMap.put("out_trade_no", outTradeNo);
        reqMap.put("total_fee", order.getTotalFee().toString());
        reqMap.put("spbill_create_ip", spbill_create_ip);
        reqMap.put("notify_url", appProperties.getWx().getNotify_url());
        reqMap.put("trade_type", appProperties.getWx().getTrade_type());
        reqMap.put("openid", user.getOpenid());
        String sign = PaymentUtils.sign(reqMap, appProperties.getWx().getApi_key());
        reqMap.put("sign", sign);
        String xml = PaymentUtils.mapToXml(reqMap);
        String result = HttpUtil.sendPostXml(appProperties.getWx().getCreate_order_url(), xml, null);
        Map<String, String> resData = PaymentUtils.xmlToMap(result);
        log.info("resData:{}", resData);
        if ("SUCCESS".equals(resData.get("return_code"))) {
            Map<String, Object> resultMap = new LinkedHashMap<>();
            //返回的预付单信息
            String prepay_id = resData.get("prepay_id");
            resultMap.put("appId", appProperties.getWx().getApp_id());
            resultMap.put("nonceStr", nonce_str);
            resultMap.put("package", "prepay_id=" + prepay_id);
            resultMap.put("signType", "MD5");
            resultMap.put("timeStamp", RandomUtil.getDateStr(14));
            String paySign = PaymentUtils.sign(resultMap, appProperties.getWx().getApi_key());
            resultMap.put("paySign", paySign);
            log.info("return data:{}", resultMap);
            return Result.success(resultMap);
        }
        return Result.error(ExceptionMessage.WEI_XIN_PAY_FAIL);
    }*/

}
