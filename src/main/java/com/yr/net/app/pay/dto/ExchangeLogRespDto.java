package com.yr.net.app.pay.dto;

import com.yr.net.app.log.entity.UserExchangeLog;
import com.yr.net.app.pay.controller.enums.ExchangeItem;
import com.yr.net.app.tools.AppUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName ExchangeLogReqDto
 * @Description 帐单记录
 * @date 6/2/21 8:31 PM
 */
@Data
public class ExchangeLogRespDto implements Serializable {

    /**
     * 交易金额(有正有负) 单位分
     */
    private String amount;

    /**
     * 交易项目名称
     */
    private String itemName;

    /**
     * 交易时间
     */
    private String time;

}
