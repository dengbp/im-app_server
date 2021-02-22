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
     * 用户id
     */
    private String userId;

    /**
     * 职业要求
     */
    private String jobRequest;

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
     * 婚姻状况要求
     */
    private String marita;




    /**
     * 学历要求
     */
    private String education;

    /**
     * 薪资范围
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
