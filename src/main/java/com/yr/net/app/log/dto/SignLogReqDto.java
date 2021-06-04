package com.yr.net.app.log.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName SignLogReqDto
 * @Description TODO
 * @date 2/21/21 12:09 PM
 */
@Data
public class SignLogReqDto implements Serializable {

    /** 被查看的用户轨迹*/
    @NotNull
    private String userId;

    /** 需要支付的项目id(代码定死-1：代表用户轨迹项目id,支付的时候也要传-1) */
    @NotNull
    private Long itemId;
}
