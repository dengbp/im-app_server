package com.yr.net.app.base.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 会员套餐/级别
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlansLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员等级 0:普通、1:黄金、2:砖石等
     */
    private Integer levelType;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 有效时间范围 单位天
     */
    private Integer timeLimit;

    /**
     * 拥有权限描述
     */
    private String authorityDesc;

    /**
     * 状态 0正常1已下架
     */
    private Integer state;

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
