package com.yr.net.app.base.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName UserBaseInfoRequestDto
 * @Description TODO
 * @date 2020-12-05 00:26
 */
@Data
public class JobInfoRequestDto {

    private String userId;

    @NotNull
    Long parentId;
}
