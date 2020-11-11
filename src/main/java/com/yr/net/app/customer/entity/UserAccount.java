package com.yr.net.app.customer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户帐号信息
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 当前金额 单位分
     */
    private BigDecimal amount;

    /**
     * 帐号
     */
    private String amountNumber;

    /**
     * 状态 0正常1：挂失2：过期
     */
    private Integer state;

    /**
     * 有效开始时间
     */
    private LocalDateTime issueTime;

    /**
     * 过期时间
     */
    private LocalDateTime expiryTime;

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
