package com.yr.net.app.customer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName CoordinateRequest
 * @Description TODO
 * @date 2020-11-11 17:50
 */
@Data
public class MultipartInfoRequestDto implements Serializable {

    /**
     * 经度
     */
    @NotNull(message = "longitude 不能为空")
    private String longitude;

    /**
     * 纬度
     */
    @NotNull(message = "latitude 不能为空")
    private String latitude;

    private String mulIds;

    /** 用处:0个人资料里的相册(或视频),1个人动态 */
    @NotNull
    private Integer using;

    /** 多媒体类型 0：图片；1：视频 */
    @NotNull
    private String type;
    @NotNull
    private String isFree;

    private String price;
    private String showWord;
}
