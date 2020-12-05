package com.yr.net.app.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 标签信息
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TagInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签内容
     */
    private String tagContent;

    /**
     * 标签类型 0:性格类；1：爱好类；
     */
    private Integer tagType;

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
