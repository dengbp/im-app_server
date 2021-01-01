package com.yr.net.app.customer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName LoveReportRequestDto
 * @Description TODO
 * @date 12/26/20 11:06 PM
 */

@Data
public class LoveReportRequestDto {

    /** 被滑动的用户id */
    @NotNull
    private String userId;

    @NotNull
    /** 1:喜欢，2：不喜欢 */
    private Integer tag;
}
