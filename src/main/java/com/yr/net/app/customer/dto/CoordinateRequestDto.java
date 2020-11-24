package com.yr.net.app.customer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName CoordinateRequest
 * @Description TODO
 * @date 2020-11-11 17:50
 */
@Data
public class CoordinateRequestDto implements Serializable {

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
}
