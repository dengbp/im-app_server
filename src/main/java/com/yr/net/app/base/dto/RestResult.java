package com.yr.net.app.base.dto;

/**
 * Description todo
 * @return
 * @Author dengbp
 * @Date 01:52 2020-11-22
 **/

public class RestResult {
    public enum  RestCode {
        SUCCESS(0, "success"),
        ERROR_INVALID_MOBILE(1, "无效的电话号码"),
        ERROR_SEND_SMS_OVER_FREQUENCY(3, "请求验证码太频繁"),
        ERROR_SERVER_ERROR(4, "服务器异常"),
        ERROR_CODE_EXPIRED(5, "验证码已过期"),
        ERROR_CODE_INCORRECT(6, "验证码错误"),
        ERROR_SERVER_CONFIG_ERROR(7, "服务器配置错误"),
        ERROR_SESSION_EXPIRED(8, "会话不存在或已过期"),
        ERROR_SESSION_NOT_VERIFIED(9, "会话没有验证"),
        ERROR_SESSION_NOT_SCANED(10, "会话没有被扫码"),
        ERROR_SERVER_NOT_IMPLEMENT(11, "功能没有实现"),
        ERROR_GROUP_ANNOUNCEMENT_NOT_EXIST(12, "群公告不存在"),
        ERROR_NOT_LOGIN(13, "没有登录"),
        ERROR_NO_RIGHT(14, "没有权限"),
        ERROR_INVALID_PARAMETER(15, "无效参数"),
        ERROR_NOT_EXIST(16, "对象不存在"),
        ERROR_USER_NAME_ALREADY_EXIST(17, "用户名已经存在"),
        ERROR_SESSION_CANCELED(18, "会话已经取消"),
        BAD_REQUEST(400, "Bad Request"),
        TOO_MANY_REQUESTS(429, "Too Many Requests");
        public int code;
        public String msg;

        RestCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }
    private int code;
    private String message;
    private Object result;

    public static RestResult ok() {
        return new RestResult(RestCode.SUCCESS,null);
    }

    public static RestResult ok(Object object) {
        return new RestResult(RestCode.SUCCESS, object);
    }

    public static RestResult error(String message) {
        return new RestResult(RestCode.ERROR_SERVER_ERROR, message);
    }

    public static RestResult error(RestCode code) {
        return new RestResult(code, null);
    }

    public static RestResult result(RestCode code, Object object){
        return new RestResult(code, object);
    }

    public static RestResult result(int code, String message, Object object){
        RestResult r = new RestResult(RestCode.SUCCESS, object);
        r.code = code;
        r.message = message;
        return r;
    }

    private RestResult(RestCode code, Object result) {
        this.code = code.code;
        this.message = code.msg;
        this.result = result;
    }

    private RestResult(RestCode code, String message) {
        this.code = code.code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public RestResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RestResult setResult(Object result) {
        this.result = result;
        return this;
    }
}
