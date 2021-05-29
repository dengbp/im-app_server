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
@TableName("Goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
            @TableId("goods_Id")
    private Long goodsId;

    /**
     * 分类id
     */
        @TableField("category_Id")
    private Integer categoryId;

    /**
     * 商品名称
     */
        @TableField("goods_Name")
    private String goodsName;

    /**
     * 商品价格 单位分
     */
    private Long goodsPrice;

    /**
     * 商品条码
     */
        @TableField("goods_Barcode")
    private String goodsBarcode;

    /**
     * 商品库存
     */
        @TableField("goods_Inventory")
    private Integer goodsInventory;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 图片url
     */
        @TableField("goods_Picture")
    private String goodsPicture;

    /**
     * 状态
     */
    private Integer status;

        @TableField("create_Time")
    private LocalDate createTime;

        @TableField("update_Time")
    private LocalDate updateTime;

    /**
     * 是否删除
     */
    private Integer deleted;


}
