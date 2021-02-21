package com.yr.net.app.log.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录日志
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserSignLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 登录时间
     */
    private LocalDateTime signInTime;

    /**
     * 登录地点
     */
    private String signAddr;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
