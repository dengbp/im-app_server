package com.yr.net.app.moments.dto;

import com.yr.net.app.common.entity.QueryRequestPage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description TODO
 * @date 2019-11-28 10:27
 */
@Data
public class MomentsLikeReqDto implements Serializable {

    public static Integer COMMENT=1;
    public static Integer THEME=0;

    public static Integer LIKE=1;
    public static Integer UNLIKE=0;

    /** 发布动态或评论用户 */
    @NotNull
    private String publicUserId;
    @NotNull
    private Long momentId;

    /**
     * 点赞类型 0：对主题点赞；1：对评论内容点赞
     */
    @NotNull
    private Integer type;

    /**
     * 点赞状态 0：取消赞；   1：点赞
     */
    @NotNull
    private Integer state;

}
