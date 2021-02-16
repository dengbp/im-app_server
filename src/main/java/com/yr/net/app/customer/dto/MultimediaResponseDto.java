package com.yr.net.app.customer.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName MultimediaResponseDto
 * @Description TODO
 * @date 2020-12-04 16:24
 */

@Data
public class MultimediaResponseDto {

    /** 多媒体id */
    private Long id;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 多媒体名称
     */
    private String multimediaName;

    /**
     * 配文、我的秀言
     */
    private String showWord;


    /**
     * 多媒体访问url
     */
    private String url;

    private String previewUrl;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 上传地点
     */
    private String addr;

    /**
     * 是否收费 0:免费:1收费
     */
    private Integer isFree;

    /**
     * 解锁值
     */
    private BigDecimal price;



}
