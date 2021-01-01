package com.yr.net.app.pojo;

import lombok.Data;

/**
 * @author dengbp
 * @ClassName BaiduMapPositionResponse
 * @Description TODO
 * @date 12/26/20 5:20 PM
 */
@Data
public class BaiduMapPositionResponse {

    /** 省 */
    private String province;
    /** 市编码 */
    private String cityCode;
    /** 市 */
    private String city;
    /** 区 */
    private String district;
    /** 街道 */
    private String street;
    /** 门牌号 */
    private String  streetNumber;
    /** 从省到门牌连串一起的详细地址*/
    private String formattedAddress;


    public BaiduMapPositionResponse(String province, String cityCode, String city, String district, String street, String streetNumber, String formattedAddress) {
        this.province = province;
        this.cityCode = cityCode;
        this.city = city;
        this.district = district;
        this.street = street;
        this.streetNumber = streetNumber;
        this.formattedAddress = formattedAddress;
    }
}
