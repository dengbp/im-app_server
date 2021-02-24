package com.yr.net.app.moments.dto;

import com.yr.net.app.common.entity.QueryRequestPage;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description 增加评论
 * @date 2019-11-28 10:27
 */
@Data
public class AddMomentDto implements Serializable {

    /** 评论ID */
    private Long id;

    /**
     * 被评论的主题id或评论id
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论地址
     */
    private String commentAddr;

    /**
     * 状态 0:正常；1：已删除
     */
    private Integer state;

    /**
     * 被评论的类型 0：对主题评论；1：对评论内容评论
     */
    private Integer type;

    /**
     * 评论者id
     */
    private String userId;
    /**
     * 评论者昵称
     */
    private String userName;

    /** 评论者头像  */
    private String icon;


}
