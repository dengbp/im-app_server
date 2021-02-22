package com.yr.net.app.customer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.yr.net.app.customer.dto.UserLoveRequestSetDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 恋爱要求
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLoveRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

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

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    public static UserLoveRequest create(UserLoveRequestSetDto dto){
        UserLoveRequest loveRequest = new UserLoveRequest();
        loveRequest.setUserId(dto.getUserId());
        loveRequest.setJobRequest(dto.getJobRequest());
        loveRequest.setCharacterRequest(dto.getCharacterRequest());
        loveRequest.setBodyWeight(dto.getBodyWeight());
        loveRequest.setBodyHeight(dto.getBodyHeight());
        loveRequest.setMarita(dto.getMarita());
        loveRequest.setEducation(dto.getEducation());
        loveRequest.setSalary(dto.getSalary());
        loveRequest.setZodiac(dto.getZodiac());
        loveRequest.setBloodType(dto.getBloodType());
        loveRequest.setAgeRange(dto.getAgeRange());
        loveRequest.setC1(dto.getC1());
        loveRequest.setC2(dto.getC2());
        loveRequest.setC3(dto.getC3());
        loveRequest.setCreatedTime(LocalDateTime.now());
        loveRequest.setUpdatedTime(LocalDateTime.now());
        return loveRequest;
    }


}
