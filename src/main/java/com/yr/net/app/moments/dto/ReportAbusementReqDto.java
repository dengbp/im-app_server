package com.yr.net.app.moments.dto;

import com.yr.net.app.moments.entity.View;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description TODO
 * @date 2019-11-28 10:27
 */
@Data
public class ReportAbusementReqDto implements Serializable {

    /** 被举报的动态的用户id */
    @NotNull
    private String publicUserId;
    /** 被举报的动态id */
    @NotNull
    private Long momentId;
    /** 举报内容 */
    @NotNull
    private String report;


    /** 举报的用户id */
    private String reportUserId;


}
