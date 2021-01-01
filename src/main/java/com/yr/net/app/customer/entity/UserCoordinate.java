package com.yr.net.app.customer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户在线经纬度
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserCoordinate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /** 省 */
    private String province;
    /** 市编码 */
    private String cityCode;
    /** 市 */
    private String city;
    /** 区 */
    private String district;
    /** 街道 */
    private String street;
    /** 门牌号 */
    private String  streetNumber;
    /** 从省到门牌连串一起的详细地址*/
    private String formattedAddress;


}
