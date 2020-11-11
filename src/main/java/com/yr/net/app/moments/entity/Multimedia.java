package com.yr.net.app.moments.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 动态多媒体
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("moments_multimedia")
public class Multimedia implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 动态id或评论id
     */
    private Long momentsId;

    /**
     * 多媒体url
     */
    private String url;

    /**
     * 多媒体访问url
     */
    private String httpUrl;

    /**
     * 多媒体类型 0：图片；1：视频
     */
    private Integer fileType;

    /**
     * 多媒体大小
     */
    private Integer fileSize;

    /**
     * 多媒体格式
     */
    private String format;

    /**
     * 评论类型 0：对主题评论；1：对评论内容评论
     */
    private Integer type;

    /**
     * 状态 0:正常；1：已删除
     */
    private Integer state;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
