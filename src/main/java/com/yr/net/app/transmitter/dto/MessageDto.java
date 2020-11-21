package com.yr.net.app.transmitter.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName MessageDto
 * @Description TODO
 * @date 2020-11-22 01:10
 */
@Data
public class MessageDto {

    @NotNull(message="发送用户不能为空")
    private String fromUser;
    @NotNull(message="接收用户不能为空")
    private String toUser;
    @NotNull(message="发送内容不能为空")
    private String message;
}
