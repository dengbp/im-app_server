package com.yr.net.app.common.entity;

/**
 * AJAX返回信息
 * All rights Reserved, Designed By HQYG
 * <pre>
 * Copyright:  Copyright(C) 2018
 * Company:    HQYG.
 * @Author:     dengbp
 * @CreateDate: 2018/5/4
 * </pre>
 * <p>
 * </p>
 */
public class AjaxResponse {

    /**
     * 状态编码 200:成功；00N:对应失败业务码
     */
    private String code;

    /**
     * 状态对应的描述
     */
    private String msg;

    /**
     * 需要返回的信息
     */
    private Object result;

    private static final String SUCCESS = "200";
    private static final String FAIL = "001";
    /** openid为空或不正确*/
    public static final String OPENID_ERROR = "002";
    /** 设备已绑定 */
    public static final String BOUNDED_ERROR = "003";
    /** 设备不存在 */
    public static final String INVALID_ERROR = "004";


    private AjaxResponse() {
    }

//    /**
//     * 构造器
//     * @param code 状态编码  200:成功；1:失败
//     * @param msg 针对状态附加描述
//     */
//    public AjaxResponse(int code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }
//
//    /**
//     *
//     * 构造器
//     * @param code 状态编码  0:成功；1:失败
//     * @param msg 针对状态附加描述
//     * @param result 结果数据对象返回
//     */
//    public AjaxResponse(int code, String msg, Object result) {
//        this.code = code;
//        this.msg = msg;
//        this.result = result;
//    }

    public static AjaxResponse success(){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(SUCCESS);
        return ajaxResponse;
    }

    public static AjaxResponse fail(String errorCode){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(errorCode);
        return ajaxResponse;
    }

    public static AjaxResponse fail(){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(FAIL);
        return ajaxResponse;
    }

    public String getCode() {
        return code;
    }

    public AjaxResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public AjaxResponse setResult(Object result) {
        this.result = result;
        return this;
    }
}
