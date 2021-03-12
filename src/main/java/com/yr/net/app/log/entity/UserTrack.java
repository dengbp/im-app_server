package com.yr.net.app.log.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户轨迹表(记录查看其它用户信息等)
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserTrack implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 浏览用户id
     */
    private String userId;

    /** 浏览用户名称 */
    private String userName;

    /**
     * 浏览用户头像
     */
    private String userIcon;

    /**
     * 被操作(浏览用户信息；用户动态；点赞；关注；拉黑等)用户对象id
     */
    private String byOperatorId;

    /**
     * 被操作(浏览用户信息；用户动态；点赞；关注；拉黑等)对象昵称
     */
    private String byOperatorName;

    /**
     * 被操作(浏览用户信息；用户动态；点赞；关注；拉黑等)对象头像
     */
    private String byOperatorIcon;

    /**
     * 操作类型 0：查看用户信息；1：用户动态；2:点赞；3：关注；4：拉黑;.....
     */
    private Integer operatorType;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;



}
