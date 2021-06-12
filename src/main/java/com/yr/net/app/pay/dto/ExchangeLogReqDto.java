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
 * @Description TODO
 * @date 6/2/21 8:31 PM
 */
@Data
public class ExchangeLogReqDto implements Serializable {

    /**
     * 支付金额 单位分
     */
    @NotNull
    private int amount;

    /**
     * 交易项目类型 0:用户基本信息(用户信息表)；1：用户相册(用户相册表)；2：用户动态项目(动态表)；3：用户活动轨迹;4:充值
     */
    @NotNull
    private Integer itemType;

    /**
     * 交易项目id
     */
    @NotNull
    private Long itemId;

    /**
     * 收款用户id(像用户活动轨迹，查看用户信息这类(目前只有用户轨迹查看要收费)，应该支付给平台,平台用户id写死：yiren)
     */
    @NotNull
    private String receiveUser;

    /** 交易类型 0支付1：收入 */
    private Integer payType;

    private String payUserId;



    public UserExchangeLog buildEntity() {
        UserExchangeLog log = new UserExchangeLog();
        log.setCreatedBy(payUserId);
        log.setCreatedTime(LocalDateTime.now());
        log.setExchangeAmount(amount);
        log.setExchangeItem("undefined");
        ExchangeItem item = ExchangeItem.getByType(itemType);
        if (item != null) {
            log.setExchangeItem(item.getDesc());
        }
        log.setExchangeItemType(itemType);
        log.setExchangeState(UserExchangeLog.SUCCESS);
        log.setExchangeTime(LocalDateTime.now());
        log.setItemId(itemId);
        log.setExchangeType(payType);
        log.setPayUserId(payUserId);
        log.setReceiveUserId(receiveUser);
        return log;
    }
}
