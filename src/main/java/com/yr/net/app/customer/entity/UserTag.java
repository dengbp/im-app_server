package com.yr.net.app.customer.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户标签
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 标签内容（对应标签表的tag_name）
     */
    private String tagContent;

    /**
     * 标签类型 0:性格类；1：爱好类；...
     */
    private Integer tagType;

    /**
     * 状态 0:正常；1:已删除
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
