package com.yr.net.app.moments.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 查看信息
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("moments_view")
public class View implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 动态主题id
     */
    private Long momentId;

    /**
     * 发布动态主题的用户id
     */
    private String publicUserId;

    /**
     * 查看的用户id
     */
    private String viewUserId;

    /**
     * 查看时间
     */
    private LocalDateTime viewTime;

    /**
     * 查看地址
     */
    private String likeAddr;



}
