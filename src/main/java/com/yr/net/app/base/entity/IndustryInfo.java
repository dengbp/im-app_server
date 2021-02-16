package com.yr.net.app.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 行业信息
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IndustryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 父类id
     */
    private Long industryParentId;

    /**
     * 行业名称
     */
    private String industryName;

    /**
     * 行业代码，对应的行业代码
     */
    private String industryCode;

    /**
     * 行业大类型 0it1服务业2金融业...从0开始递增(需要小类的得独立加字段，目前好像还用不到小类)
     */
    private Integer industryType;

    /**
     * 深度 深度，从1递增
     */
    private Integer depth;

    /**
     * 所有父级id 分类的层级关系，从最高级到自己
     */
    private String parentIdList;

    /**
     * 状态 状态：0禁用，1启用
     */
    private Integer status;

    /**
     * 优先级 值越大，同级显示的时候越靠前
     */
    private Integer priority;

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
