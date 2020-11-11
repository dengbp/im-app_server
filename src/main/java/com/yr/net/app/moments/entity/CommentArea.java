package com.yr.net.app.moments.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 评论信息
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题id或评论id
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论时间
     */
    private LocalDateTime commentTime;

    /**
     * 评论地址
     */
    private String commentAddr;

    /**
     * 状态 0:正常；1：已删除
     */
    private Integer state;

    /**
     * 评论类型 0：对主题评论；1：对评论内容评论
     */
    private Integer type;

    /**
     * 评论者id
     */
    private Long userId;

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
