package com.yr.net.app.log.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dengbp
 * @ClassName UserTrackRespDto
 * @Description TODO
 * @date 1/25/21 1:26 PM
 */
@Data
public class UserTrackRespDto implements Serializable {
    /**
     * 操作(浏览用户信息；用户动态；点赞；关注；拉黑等)用户id
     */
    private String operatorId;
    /**
     * 操作(浏览用户信息；用户动态；点赞；关注；拉黑等)用户昵称
     */
    private String operatorName;
    /**
     * 操作(浏览用户信息；用户动态；点赞；关注；拉黑等)用户头像
     */
    private String operatorIcon;
    /** 操作人的身高 单位 CM*/
    private int bodyHeight;
    /**
     * 操作人的体重 单位KG
     */
    private BigDecimal bodyWeight;
    /** 操作人的星座 */
    private String zodiac;
    /** 操作人的年龄 */
    private int age;
    /**
     * 操作类型 0：查看用户信息；1：用户动态；2:点赞；3：关注；4：拉黑;.....
     */
    private Integer operatorType;
    /**
     * 浏览时间
     */
    private String viewTime;

}
