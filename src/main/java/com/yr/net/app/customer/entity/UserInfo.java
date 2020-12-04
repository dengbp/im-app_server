package com.yr.net.app.customer.entity;

import java.math.BigDecimal;
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
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id对应im-server的_uid
     */
    private String userId;
    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private Integer birthday;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身高
     */
    private BigDecimal bodyHeight;

    /**
     * 体重
     */
    private BigDecimal bodyWeight;

    /**
     * 身份证号
     */
        @TableField("ID_NO")
    private String idNo;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信号
     */
        @TableField("web_chat_NO")
    private String webChatNo;

    /**
     * QQ号
     */
        @TableField("QQ_NO")
    private String qqNo;

    /**
     * 老家
     */
    private String nativeLand;

    /**
     * 现所在城市
     */
    private String nowLife;

    /**
     * 是否有房 0：无；1：已买房
     */
    private Integer housePurchase;

    /**
     * 在线状态 0：离线；1：在线
     */
    private Integer status;

    /** 用户头像  */
    private String icon;

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
