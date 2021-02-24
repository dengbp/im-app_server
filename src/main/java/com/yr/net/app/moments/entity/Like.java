package com.yr.net.app.moments.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 点赞信息
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("moments_like")
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 发布用户id
     */
    private String publicUserId;
    /**
     * 点赞主题id或评论id
     */
    private Long commentId;

    /**
     * 点赞用户id
     */
    private String likeUserId;

    /**
     * 点赞时间
     */
    private LocalDateTime likeTime;

    /**
     * 点赞地址
     */
    private String likeAddr;

    /**
     * 点赞类型 0：对主题点赞；1：对评论内容点赞
     */
    private Integer type;

    /**
     * 点赞状态 0：取消赞；   1：有效赞
     */
    private Integer state;

}
