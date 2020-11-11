package com.yr.net.app.common.entity;

import lombok.Data;

/**
 * All rights Reserved, Designed By SEGI
 * <pre>
 * Copyright:  Copyright(C) 2018
 * Company:    SEGI.
 * Author:     dengbp
 * CreateDate: 2018/6/20
 * </pre>
 * <p>
 *     token信息
 * </p>
 */
@Data
public class GeneralToken {
    /** 成功有效时间 */
    private long expiresIn;
    /** 普通Token */
    private String accessToken;
    /** 失败ID */
    private String errorCode;
    /** 失败消息 */
    private String errMessage;
    public static GeneralToken instance = new GeneralToken();
    private GeneralToken(){

    }
}
