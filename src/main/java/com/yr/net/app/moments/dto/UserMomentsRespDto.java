package com.yr.net.app.moments.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
     * 发布地址(city+district组成)
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
     * 评论量
     */
    private Integer commentTotal;



    /**
     * 发布者昵称
     */
    private String userName;

    /** 发布者头像  */
    private String icon;

    /**
     * 发布者性别，1男2女
     */
    private Integer sex;

    private List<ImagesBean> imagesBean;

    private VideoBean videoBean;


    @Data
    public static class ImagesBean {
        /**
         * 多媒体访问url
         */
        private String url;


        /**
         * 对应缩略图或视频第一帧
         **/
        private String previewUrl;
    }

    @Data
    public static class VideoBean {
        /**
         * 视频第一帧
         **/
        private String previewUrl;
        /**
         * 视频访问url
         */
        private String url;
    }

}
