package com.yr.net.app.customer.dto;

import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.tools.DateUtil;
import lombok.Data;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

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

    /**
     * 星座
     */
    private String star;

    /**
     * 属相
     **/
    private String zodiac;

    private String userName;

    /**
     * 性别，1男2女
     */
    private Integer sex;

}
