package com.yr.net.app.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
@TableName("Order_Item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

        @TableField("order_Item_Id")
    private String orderItemId;

        @TableField("order_Id")
    private String orderId;

        @TableField("goods_Id")
    private String goodsId;

        @TableField("goods_Name")
    private String goodsName;

        @TableField("goods_Price")
    private Long goodsPrice;

        @TableField("goods_Count")
    private Integer goodsCount;

        @TableField("goods_Picture")
    private String goodsPicture;

        @TableField("create_Time")
    private LocalDate createTime;

        @TableField("update_Time")
    private LocalDate updateTime;

    private Integer deleted;


}
