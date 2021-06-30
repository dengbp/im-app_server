package com.yr.net.app.customer.dto;

import com.yr.net.app.common.entity.QueryRequestPage;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description TODO
 * @date 2019-11-28 10:27
 */
@Data
public class AlbumRequestDto extends QueryRequestPage {


    private String userId;

    /** 多媒体类型 0：图片；1：视频(-1取全部) */
    @NotNull(message = "多媒体类型不能为空")
    private Integer type;

    /**
     * 用处:0个人资料里的相册(或视频),1个人动态，...
     */
    @NotNull
    private Integer beUsed;

}
