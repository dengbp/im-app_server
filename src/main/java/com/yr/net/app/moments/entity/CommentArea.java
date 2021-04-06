package com.yr.net.app.moments.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
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

    public static Integer NORMAL = 0;
    public static Integer DELETE = 1;


    //对原主题评论
    public final static int COMMENT_TYPE_SINGLE = 0;
    //回复评论
    public final static int COMMENT_TYPE_REPLY = 1;

    private static final long serialVersionUID = 1L;





    @TableId
    private Long id;

    /**
     * 被评论的主题id或评论id
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
     * 评论的类型 0：对主题评论；1：对评论内容评论
     */
    private Integer type;

    /**
     * 评论者id
     */
    private String userId;
    /**
     * 评论者昵称
     */
    private String userName;

    /** 评论者头像  */
    private String icon;


}
