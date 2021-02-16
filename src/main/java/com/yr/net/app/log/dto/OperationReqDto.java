package com.yr.net.app.log.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName OperationReqDto
 * @Description TODO
 * @date 1/25/21 1:53 PM
 */

@Data
public class OperationReqDto {

    private String userId;
    /**
     * 操作类型 0：查看用户信息；1：用户动态；2:点赞；3：关注；4：拉黑;.....
     */
    @NotNull
    private Integer operatorType;
}
