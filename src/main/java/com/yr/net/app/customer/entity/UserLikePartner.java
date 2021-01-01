package com.yr.net.app.customer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 当前用户喜欢其它用户信息表
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLikePartner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前用户id
     */
    private String userId;

    /**
     * 喜欢的用户id
     */
    private String partnerId;

    /**
     * 1:喜欢，2：不喜欢
     */
    private Integer tag;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;


}
