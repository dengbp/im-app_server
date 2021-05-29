package com.yr.net.app.pay.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName PayReqDto
 * @Description TODO
 * @date 5/20/21 2:06 PM
 */
@Data
public class PayReqDto {


    /** 总金额(分) */
    private Integer totalFee;


    /** 支付方式 */
    private Integer type;
}
