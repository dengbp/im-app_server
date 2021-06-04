package com.yr.net.app.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_Bill")
public class OrderBill implements Serializable {

    private static final long serialVersionUID = 1L;


    public  OrderBill(){

    }

    public OrderBill(String payLoad, Integer orderType, String outTradeNo, LocalDate createTime) {
        this.payLoad = payLoad;
        this.orderType = orderType;
        this.outTradeNo = outTradeNo;
        this.createTime = createTime;
    }


    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /** 回调报文内容 */
    @TableField("pay_load")
    private String payLoad;

    /** 支付类型：0:微信，1支付宝 */
        @TableField("order_Type")
    private Integer orderType;


     /** 支付流水(商户订单号) */
        @TableField("out_Trade_No")
    private String outTradeNo;

        @TableField("create_Time")
    private LocalDate createTime;







}
