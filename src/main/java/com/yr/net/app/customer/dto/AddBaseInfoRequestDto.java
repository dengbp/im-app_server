package com.yr.net.app.customer.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yr.net.app.common.entity.QueryRequestPage;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description 完善基本信息
 * @date 2019-11-28 10:27
 */
@Data
public class AddBaseInfoRequestDto {

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
     * 身份证号
     */
    private String idNo;

    /**
     * 身高
     */
    private BigDecimal bodyHeight;

    /**
     * 体重
     */
    private BigDecimal bodyWeight;


    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信号
     */
    private String webChatNo;

    /**
     * QQ号
     */
    private String qqNo;

    /**
     * 籍贯
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

}
