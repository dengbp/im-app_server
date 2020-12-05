package com.yr.net.app.customer.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户信息表
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfoDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 性格
     */
    private String disposition;

    /**
     * 内心独白
     */
    private String motto;

    /**
     * 婚姻状况:0 单身、1已婚、2离异、3丧偶
     */
    private Integer marita;

    /**
     * 文化程度
     */
    private String education;

    /**
     * 职业
     */
    private String job;

    /**
     * 薪资
     */
    private String salary;

    /**
     * 住房情况 一个人住、和家人住，...
     */
    private String housing;

    /**
     * 是否想要小孩 0：不想要；1：暂时无考虑；2：想要
     */
    private Integer giveBirth;

    /**
     * 能否接受异地恋 0：不；1接受
     */
    private Integer distanceLove;

    /**
     * 喜欢异性类型 简单描述，喜欢的类型
     */
    private String loveType;

    /**
     * 能否接受婚前性行为 0接受，1不接受
     */
    private Integer premaritalSex;

    /**
     * 是否愿意与父母同住 0接受，1不接受
     */
    private Integer liveWithParents;

    /**
     * 最有魅力的部位 臀、胸、脸等等
     */
    private String charmPart;

    /**
     * 预留字段
     */
    private String c1;

    /**
     * 预留字段
     */
    private String c2;

    /**
     * 预留字段
     */
    private String c3;

    /**
     * 预留字段
     */
    private String c4;

    /**
     * 预留字段
     */
    private String c5;

    /**
     * 预留字段
     */
    private String c6;

    /**
     * 是否已购车 0：无；1：已买
     */
    private Integer carPurchase;

    /**
     * 籍贯
     */
    private String nativeLand;

    /**
     * 是否有房 0：无；1：已买房
     */
    private Integer housePurchase;

    /**
     * 创建人
     */
        @TableField("CREATED_BY")
    private String createdBy;

    /**
     * 创建时间
     */
        @TableField("CREATED_TIME")
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
        @TableField("UPDATED_BY")
    private String updatedBy;

    /**
     * 更新时间
     */
        @TableField("UPDATED_TIME")
    private LocalDateTime updatedTime;


}
