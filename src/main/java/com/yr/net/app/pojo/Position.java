package com.yr.net.app.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dengbp
 * @ClassName Position
 * @Description 坐标
 * @date 2020-11-22 03:33
 */
@Data
public class Position {

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    public Position(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Position() {
    }
}
