package com.yr.net.app.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 快捷打招呼
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShortcutGreet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息内容
     */
    private String shortcutContent;

    /**
     * 消息类型 0文本 1表情图
     */
    private Integer shortcutType;

    /**
     * 消息排序 在终端显示从小到大排序
     */
    private Integer shortcutSort;

    /**
     * 消息状态 0：正常1已废弃
     */
    private Integer shortcutState;

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
