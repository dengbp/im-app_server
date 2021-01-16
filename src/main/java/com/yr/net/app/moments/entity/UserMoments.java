package com.yr.net.app.moments.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户动态
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserMoments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 配文、我的秀言
     */
    private String showWord;


    /**
     * 关联多媒体id
     */
    private Long multimediaId;


    /**
     * 多媒体访问url
     */
    private String url;

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
     * 状态 0：正常；1：已删除
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
