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
public class UserInfoResponseDto implements Serializable {

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

    private String picUrl;
    /** 所在城市 */
    private String  nowLife;

    private int age;
}
