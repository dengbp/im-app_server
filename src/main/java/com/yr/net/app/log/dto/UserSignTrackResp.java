package com.yr.net.app.log.dto;

import com.yr.net.app.log.entity.UserSignLog;
import lombok.Data;

import java.util.List;

/**
 * @author dengbp
 * @ClassName UserSignTrackResp
 * @Description TODO
 * @date 6/4/21 1:16 PM
 */

@Data
public class UserSignTrackResp {

    /** 对用户登录轨迹查看是否已付款费0已付，1未付款 */
    private Integer purview;

    List<UserSignLog> signLogs;

}
