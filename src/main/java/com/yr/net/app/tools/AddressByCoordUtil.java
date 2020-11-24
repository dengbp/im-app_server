package com.yr.net.app.tools;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    public static void main(String[] args) {
        // lat 31.2990170   纬度
        //log 121.3466440    经度
        String add = AddressByCoordUtil.getAdd("121.3466440", "31.2990170");
        logger.info(add);

    }

    /**
     *根据经纬度获取省市区
     * @param log
     * @param lat
     * @return
     */
    public static String getAdd(String log, String lat ){
        //lat 小  log  大
        //参数解释: 纬度,经度 采用高德API可参考高德文档https://lbs.amap.com/
        String key = "4f979382885e4f573ba0fb1298eb7f13";
        String urlString = "https://restapi.amap.com/v3/geocode/regeo?location="+lat+","+log+"&extensions=base&batch=false&roadlevel=0&key="+key;
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line+"\n";
            }
            in.close();
            //解析结果
            JSONObject jsonObject = JSONObject.parseObject(res);
            logger.info(jsonObject.toJSONString());
            JSONObject jsonObject1 = jsonObject.getJSONObject("regeocode");
            res =jsonObject1.getString("formatted_address");
        } catch (Exception e) {
            logger.error("获取地址信息异常{}",e.getMessage());
            return null;
        }
        return res;
    }
}
