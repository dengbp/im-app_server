package com.yr.net.app.customer.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 恋爱要求设置
 *
 * @author dengbp
 */
@Data
public class UserLoveRequestSetDto{

    private static final long serialVersionUID = 1L;


    /**
     * 工作要求 有无工作要求：0：要有工作；1：有正当工作；2：无要求
     */
    private Integer jobRequest;

    /**
     * 性格要求
     */
    private String characterRequest;

    /**
     * 体重
     */
    private String bodyWeight;

    /**
     * 身高
     */
    private String bodyHeight;

    /**
     * 爱好要求
     */
    private String interestRequest;

    /**
     * 婚姻状况 0:要求未婚，1：离异也可以，2：无要求
     */
    private Integer marita;

    /**
     * 是否接受异地 0:不接受；1：接受
     */
    private Integer distanceRelation;

    /**
     * 喜欢定居城市
     */
    private String enjoyLifeCity;

    /**
     * 文化程度
     */
    private String education;

    /**
     * 薪资
     */
    private String salary;

    /** 星座 **/
    private String zodiac;

    /** 血型 **/
    private String bloodType;

    /** 年龄范围 **/
    private String ageRange;

    /**
     * 预留字段
     */
    private String c1;

    /**
     * 预留字段
     */
    private String c2;

    /**
     * 预留字段
     */
    private String c3;

}
