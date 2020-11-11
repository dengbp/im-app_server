package com.yr.net.app.log.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户轨迹表(记录查看其它用户信息等) 
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserTrack implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 浏览用户id
     */
    private Long userId;

    /**
     * 被操作对象id
     */
    private Long viewId;

    /**
     * 操作类型 0：用户信息；1：用户动态；2:点赞；3：关注；4：拉黑;.....
     */
    private Integer viewType;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;


}
