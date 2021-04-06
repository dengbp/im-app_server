package com.yr.net.app.message.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户关注(关系)
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关注的用户id
     */
    private String userId;

    /**
     * 被关注或被拉黑用户id
     */
    private String relationId;


    public static Integer FOLLOW = 1;
    public static Integer UN_FOLLOW = 0;
    /**
     * 状态   0取消关注，1关注  2：已拉黑
     */
    private Integer state;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
