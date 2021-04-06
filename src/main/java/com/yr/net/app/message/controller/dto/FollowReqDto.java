package com.yr.net.app.message.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName FollowReqDto
 * @Description TODO
 * @date 3/29/21 1:53 AM
 */
@Data
public class FollowReqDto implements Serializable {

    /**
     * 被关注用户id
     */
    @NotNull
    private String relationId;

    public static Integer FOLLOW = 1;
    public static Integer UN_FOLLOW = 0;
    /**
     * 状态   0取消关注，1关注  2：已拉黑
     */
    private Integer state;
}
