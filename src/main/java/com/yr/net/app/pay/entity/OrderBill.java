package com.yr.net.app.pay.entity;

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
@TableName("Order_Bill")
public class OrderBill implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId("order_Bill_Id")
    private Integer orderBillId;

        @TableField("user_Id")
    private String userId;

        @TableField("order_Type")
    private Integer orderType;

        @TableField("pay_Amount")
    private Long payAmount;

        @TableField("out_Trade_No")
    private String outTradeNo;

        @TableField("create_Time")
    private LocalDate createTime;

        @TableField("update_Time")
    private LocalDate updateTime;

    private Integer deleted;


}
