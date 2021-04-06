package com.yr.net.app.log.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户交易日志
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserExchangeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易流水 支付方和接收方要同个流水号
     */
    private String flowCode;

    public static Integer INCOME_TYPE = 1;
    public static Integer PAY_TYPE = 0;
    /**
     * 交易类型 0支付1：收入
     */
    private Integer exchangeType;

    /**
     * 交易用户id
     */
    private String userId;

    /**
     * 交易时间
     */
    private LocalDateTime exchangeTime;


    public static Integer SUCCESS = 0;
    public static Integer FAIL = 1;
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

    /**
     * 交易项目类型 0:用户联系方式；1：用户相册、用户信息项目；2：用户动态项目
     */
    public static Integer USER_INFO = 1;
    public static Integer MOMENT = 2;

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

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
