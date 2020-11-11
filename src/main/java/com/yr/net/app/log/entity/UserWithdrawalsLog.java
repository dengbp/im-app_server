package com.yr.net.app.log.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户提现日志
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserWithdrawalsLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 提现流水 支付方和接收方要同个流水号
     */
    private String flowCode;

    /**
     * 提现用户id
     */
    private Long userId;

    /**
     * 提现时间
     */
    private LocalDateTime withdrawalsTime;

    /**
     * 提现状态 0成功1失败
     */
    private Integer withdrawalsState;

    /**
     * 提现金额
     */
    private BigDecimal withdrawalsAmount;


}
