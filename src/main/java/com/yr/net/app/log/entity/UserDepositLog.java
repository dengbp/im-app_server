package com.yr.net.app.log.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户充值记录
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDepositLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充值流水 支付方和接收方要同个流水号
     */
    private String flowCode;

    /**
     * 充值用户id
     */
    private Long userId;

    /**
     * 充值时间
     */
    private LocalDateTime depositTime;

    /**
     * 充值状态 0成功1失败
     */
    private Integer depositState;

    /**
     * 提现金额
     */
    private BigDecimal depositAmount;


}
