package com.yr.net.app.log.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 收费项目(当用户设置收费后会生成对应收费项目)
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChargeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 价格 单位分
     */
    private BigDecimal price;

    /**
     * 状态 0有效1无效
     */
    private Integer state;

    /**
     * 项目类型 1：用户相册、用户信息项目；2：用户动态项目
     */
    private Integer itemType;

    /**
     * 项目描述
     */
    private String itemDesc;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
