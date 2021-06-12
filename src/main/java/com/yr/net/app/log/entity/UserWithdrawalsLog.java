package com.yr.net.app.log.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
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
    private String userId;


    /**
     * 提现收款支付宝帐号
     */
    private String account;

    /**
     * 提现时间
     */
    private LocalDateTime withdrawalsTime;

    /**
     * 提现状态 0已提现成功1待处理
     */
    private Integer withdrawalsState;

    /**
     * 提现金额
     */
    private Integer withdrawalsAmount;


    public static UserWithdrawalsLog build(String flowCode,String account,Integer withdrawalsAmount){
        UserWithdrawalsLog log = new UserWithdrawalsLog();
        log.setFlowCode(flowCode);
        log.setUserId(AppUtil.getCurrentUserId());
        log.setWithdrawalsAmount(withdrawalsAmount);
        log.setWithdrawalsTime(LocalDateTime.now());
        log.setWithdrawalsState(1);
        log.setAccount(account);
        return log;
    }

}
