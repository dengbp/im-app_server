package com.yr.net.app.customer.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户家庭成员
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserMember implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * user_id
     */
    private String untitled3;

    /**
     * 关系 如：父子，母子，兄妹
     */
    private String relation;

    /**
     * 称呼 如：父亲，母亲，哥哥，妹妹
     */
    private String appellation;

    /**
     * 职业
     */
    private String profession;

    /**
     * 状态 0正常1删除
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
