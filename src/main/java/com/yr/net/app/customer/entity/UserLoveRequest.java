package com.yr.net.app.customer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
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

    /**
     * 用户id
     */
    private Long userId;

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
    private BigDecimal bodyWeight;

    /**
     * 身高
     */
    private BigDecimal bodyHeight;

    /**
     * 爱好要求
     */
    private String interestRequest;

    /**
     * 婚姻状况 0:要求未婚，1：离异也可以，2：无要求
     */
    private Integer maritalStatus;

    /**
     * 是否接受异地 0:不接受；1：接受
     */
    private Integer distanceRelation;

    /**
     * 喜欢定居城市
     */
    private String enjoyLifeCity;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
