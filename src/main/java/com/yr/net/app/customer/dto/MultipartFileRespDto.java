package com.yr.net.app.customer.dto;

import lombok.Data;

/**
 * @author dengbp
 * @ClassName MultipartFileRespDto
 * @Description TODO
 * @date 1/16/21 8:08 PM
 */
@Data
public class MultipartFileRespDto {

    private Long id;
    private String fileUrl;
    /**
     * 对应缩略图、视频第一帧
     **/
    private String previewUrl;
}
