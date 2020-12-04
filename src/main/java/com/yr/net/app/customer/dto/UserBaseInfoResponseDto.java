package com.yr.net.app.customer.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dengbp
 * @ClassName UserInfoResponse
 * @Description TODO
 * @date 2020-11-11 13:52
 */

@Data
public class UserBaseInfoResponseDto implements Serializable {

    private String userId;
    /** 身高 单位 CM*/
    private int bodyHeight;
    /**
     * 体重 单位KG
     */
    private BigDecimal bodyWeight;
    /** 距离单位 KM*/
    private double distance;
    /** 星座 */
    private String zodiac;

    /** 所在城市 */
    private String  nowLife;

    private int age;

    /** 用户头像  */
    private String icon;

    /**
     * 多媒体类型 0：图片；1：视频
     */
    private Integer type;

    /**
     * 是否收费 0:免费:1收费
     */
    private Integer isFree;

    /**
     * 解锁值
     */
    private BigDecimal price;
}
