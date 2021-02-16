package com.yr.net.app.log.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName UserTrackRespDto
 * @Description TODO
 * @date 1/25/21 1:26 PM
 */
@Data
public class UserTrackRespDto implements Serializable {

    /**
     * 浏览用户id
     */
    private String userId;

    /** 浏览用户名称 */
    private String userName;

    /**
     * 被操作(浏览用户信息；用户动态；点赞；关注；拉黑等)对象id
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
     * 浏览时间
     */
    private String viewTime;

}
