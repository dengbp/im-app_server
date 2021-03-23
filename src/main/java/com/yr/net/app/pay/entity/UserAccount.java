package com.yr.net.app.pay.entity;

import java.math.BigDecimal;
import java.io.Serializable;
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

    /**
     * 用户id
     */
    private String userId;

    /**
     * 剩余财富，单位分
     */
    private BigDecimal balance;

    /**
     * 用户头像
     */
    private String icon;



    /**
     * 登录/显示用户名(关联im-server的展示昵称)
     */
    private String userName;

    /**
     * 充值时间,格式:yyyymmddHHMMSS
     */
    private Long createTime;


    /**
     * 状态 0正常1：挂失2：过期
     */
    public static Integer NORMAL = 0;
    private Integer state;


}
