package com.yr.net.app.moments.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description 发布动态
 * @date 2019-11-28 10:27
 */
@Data
public class AddMomentDto implements Serializable {

    /**
     * 经度
     */
    @NotNull(message = "longitude 不能为空")
    private String longitude;

    /**
     * 纬度
     */
    @NotNull(message = "latitude 不能为空")
    private String latitude;

    /**
     * 多媒体访问url（文件上传成功后返回的id）
     */
    private String url;

    /**
     * 配文、我的秀言
     */
    private String showWord;


    /**
     * 关联多媒体id（文件上传成功后返回的id）
     */
    private Long multimediaId;


    /**
     * 是否收费 0:免费:1收费
     */
    @NotNull
    private Integer isFree;

    /**
     * 解锁值
     */
    private BigDecimal price;

    /**
     * 对应缩略图、视频第一帧
     **/
    private String previewUrl;

}
