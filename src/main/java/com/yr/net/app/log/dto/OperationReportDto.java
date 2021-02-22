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
     * 被操作(浏览用户信息；用户动态；点赞；关注；拉黑等)对象id
     */
    @NotNull
    private String byOperatorId;

    /**
     * 操作类型 0：查看用户信息；1：用户动态；2:点赞；3：关注；4：拉黑;.....
     */
    @NotNull
    private Integer operatorType;
}
