package com.yr.net.app.moments.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName UserMomentsDto
 * @Description TODO
 * @date 2020-12-17 20:53
 */

@Data
public class UserMomentsRespDto {

    /**
     * 用户动态id
     **/
    private Long id;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 发布主题(秀言)
     */
    private String publicTheme;

    /**
     * 发布时间
     */
    private LocalDateTime publicTime;

    /**
     * 发布地址
     */
    private String publicAddr;

    /**
     * 是否收费 0:免费:1收费
     */
    private Integer isFree;

    /**
     * 解锁值
     */
    private BigDecimal price;

    /**
     * 点赞量
     */
    private Integer likeTotal;

    /**
     * 浏览量
     */
    private Integer viewTotal;

    /**
     * 评论量
     */
    private Integer commentTotal;

    /**
     * 多媒体访问url
     */
    private String url;



}
