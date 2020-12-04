package com.yr.net.app.customer.dto;

import lombok.Data;

/**
 * @author dengbp
 * @ClassName NearUserResponseDto
 * @Description TODO
 * @date 2020-11-22 03:29
 */
@Data
public class NearUserResponseDto {

    private String userId;

    private int age;
    /** 单位 CM*/
    private int bodyHeight;
    /** 距离单位 KM*/
    private double distance;

    /** 用户头像  */
    private String icon;
}
