package com.yr.net.app.customer.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName UserInfoResponse
 * @Description TODO
 * @date 2020-11-11 13:52
 */

@Data
public class UserInfoResponseDto implements Serializable {

    /** 距离 */
    private Integer distance;
    /** 星座 */
    private String zodiac;
}
