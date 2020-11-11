package com.yr.net.app.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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

    /**
     * 大行业类型id
     */
    private Long industryId;

    /**
     * 行业名称
     */
    private String industryName;

    /**
     * 行业类型 0it1服务业2金融业
     */
    private Integer industryType;

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
