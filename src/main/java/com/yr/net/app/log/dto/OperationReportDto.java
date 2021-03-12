package com.yr.net.app.log.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName OperationReqDto
 * @Description TODO
 * @date 1/25/21 1:53 PM
 */

@Data
public class OperationReportDto implements Serializable {


    /** 当前操作用户id */
    private String userId;

    /**
     * 被操作内容对应的(浏览用户信息；用户动态；点赞；关注等)用户id
     */
    @NotNull
    private String byOperatorId;

    /**
     * 操作类型(传0) 0：浏览用户信息；1：用户动态；2:主题点赞；3：主题取消点赞 4:评论内容点赞；5：评论内容取消点赞  6：关注；.....
     */
    @NotNull
    private Integer operatorType;

    /** 动态或评论id(动态才需要传) */
    private Long momentId;
}
