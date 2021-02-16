package com.yr.net.app.customer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户相册
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserMultimedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 原上传的多媒体名称
     */
    private String multimediaName;


    /**
     * 多媒体存储url
     */
    private String path;

    /**
     * 多媒体访问url
     */
    private String url;
    /**
     * 对应缩略图、视频第一帧
     **/
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
     * 存储文件名称
     */
    private String storeName;

    /**
     * 状态 0:正常；1：已删除
     */
    private Integer state;

    /**
     * 多媒体大小
     */
    private Long fileSize;

    /**
     * 多媒体格式
     */
    private String format;



    /**
     * 多媒体类型 0：图片；1：视频
     */
    private Integer type;

    /**
     * 用处:0个人资料里的相册(或视频),1个人动态，...
     */
    private Integer beUsed;

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
