package com.yr.net.app.tools;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yr.net.app.pojo.BaiduMapPositionResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * @author dengbp
 * @ClassName AddressUntils
 * @Description TODO
 * @date 2020-11-25 00:50
 */
public class AddressByCoordUtil {

    private final static Logger logger = LoggerFactory.getLogger(AddressByCoordUtil.class);
    private static final String ADDR_URL = "http://api.map.baidu.com/geocoder?";

    private static final String RESULT_OK = "OK";

    /**
     *根据经纬度获取省市区
     * @param lat 维度
     * @param log 经度
     * @return
     */
    public static BaiduMapPositionResponse getAdd(String lat, String log){
        String param = "location="+lat+","+log+"&output=json";
        String res = "";
        BaiduMapPositionResponse response = null;
        try {
             res = HttpUtil.sendPost(ADDR_URL+param,"GET",null);
            JSONObject jsonObject = JSONObject.parseObject(res);
            if (StringUtils.equals(RESULT_OK,(String)jsonObject.get("status"))) {
                JSONObject result = jsonObject.getJSONObject("result");
                if (result != null){
                    String formattedAddress = (String) result.get("formatted_address");
                    String cityCode = result.getInteger("cityCode").toString();
                    JSONObject addressComponent = result.getJSONObject("addressComponent");
                    String city = addressComponent.getString("city");
                    String district = addressComponent.getString("district");
                    String province = addressComponent.getString("province");
                    String street = addressComponent.getString("street");
                    String streetNumber = addressComponent.getString("street_number");
                    response = new BaiduMapPositionResponse(province,cityCode,city,district,street,streetNumber,formattedAddress);
                }
            }
        } catch (Exception e) {
            logger.error("获取地址信息异常{}",e.getMessage());
            return null;
        }
        return response;
    }
}
