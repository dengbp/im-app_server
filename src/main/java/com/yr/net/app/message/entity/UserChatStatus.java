package com.yr.net.app.message.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 聊天状态信息
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserChatStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 消息发送时间
     */
    private LocalDateTime clientSendTime;

    /**
     * 消息接收时间
     */
    private LocalDateTime clientReceiveTime;

    /**
     * 服务器接收时间
     */
    private LocalDateTime serverReceiveTime;

    /**
     * 服务器发送时间
     */
    private LocalDateTime serverSendTime;

    /**
     * 服务器节点id
     */
    private Integer serverId;

    /**
     * 是否已读 0已读1未读
     */
    private Integer readed;

    /**
     * 消息类型 0：文本；1：图片；2：视频
     */
    private Integer messageType;

    /**
     * 发送者id
     */
    private Long senderId;

    /**
     * 接收者id
     */
    private Long receiverId;

    /**
     * 发送状态 0:发送成功1发送失败
     */
    private Integer status;

    /**
     * 消息状态 0正常1用户操作了删除
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
