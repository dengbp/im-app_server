package com.yr.net.app.moments.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description 发布动态
 * @date 2019-11-28 10:27
 */
@Data
public class AddSimpleMomentDto implements Serializable {


    /**
     * 多媒体访问url（文件上传成功后返回的id）
     */
    private String url;

    /**
     * 配文、我的秀言
     */
    private String showWord;

    /**
     * 对应缩略图、视频第一帧
     **/
    private String previewUrl;

}
