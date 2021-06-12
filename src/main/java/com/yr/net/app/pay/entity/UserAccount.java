package com.yr.net.app.pay.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 剩余财富(余额)，单位分
     */
    private Integer balance;

    /**
     * 用户头像
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登录/显示用户名(关联im-server的展示昵称)
     */
    private String userName;

    /**
     * 第一次充值时间,格式:yyyymmddHHMMSS
     */
    private Long createTime;

    /**
     * 充值/支付/提现时间,格式:yyyymmddHHMMSS
     */
    private Long updateTime;

    /**
     * 状态 0正常1：禁用
     */
    public static Integer NORMAL = 0;
    private Integer state;


}
