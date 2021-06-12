package com.yr.net.app.pay.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName WithdrawReqDto
 * @Description 提现申请
 * @date 6/8/21 1:42 PM
 */
@Data
public class WithdrawReqDto {

    /** 收款支付宝帐号 */
    @NotNull
    private String account;
    /**
     * 提现金额 单位分
     */
    @NotNull
    private int amount;
}
