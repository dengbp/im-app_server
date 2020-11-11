package com.yr.net.app.customer.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户设置
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String untitled2;

    /**
     * 功能编码
     */
    private String funCode;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 功能类型
     */
    private String funType;

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
