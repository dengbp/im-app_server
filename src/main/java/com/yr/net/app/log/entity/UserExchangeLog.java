package com.yr.net.app.log.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * 用户交易日志
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "user_exchange_log")
public class UserExchangeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易流水
     */
    private String flowCode;

    public static Integer INCOME_TYPE = 1;
    public static Integer PAY_TYPE = 0;
    /**
     * 交易类型 0支付1：收入
     */
    private Integer exchangeType;

    /**
     * 支付用户id
     */
    private String payUserId;

    /**
     * 支付用户id
     */
    private String receiveUserId;

    /**
     * 交易时间
     */
    private LocalDateTime exchangeTime;


    public static transient  Integer SUCCESS = 0;
    public static  transient Integer FAIL = 1;
    /**
     * 交易状态 0成功1失败
     */
    private Integer exchangeState;

    /**
     * 交易金额
     */
    private BigDecimal exchangeAmount;

    /**
     * 交易项目
     */
    private String exchangeItem;


    private Integer exchangeItemType;

    /**
     * 交易项目id
     */
    private Long itemId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;


}
