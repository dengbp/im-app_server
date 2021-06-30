package com.yr.net.app.moments.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 违规举报
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AbusementReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 举报内容
     */
    private String content;

    /**
     * 被举报的动态的用户id
     */
        @TableField("public_User_Id")
    private String publicUserId;

    /**
     * 被举报的动态id 
     */
        @TableField("moment_Id")
    private Long momentId;

    /**
     * 举报的用户id
     */
        @TableField("report_User_Id")
    private String reportUserId;


}
