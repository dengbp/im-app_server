package com.yr.net.app.tools;

import java.text.DecimalFormat;

/**
 * @author dengbp
 * @ClassName DistanceUtil
 * @Description TODO
 * @date 12/26/20 7:34 PM
 */
public class DistanceUtil {

    /** 赤道半径 */
    private static final  double EARTH_RADIUS = 6378137;
    private static double rad(double d){
        return d * Math.PI / 180.0;
    }

    /**
     * Description todo
     * @param lon1
 * @param lat1
 * @param lon2
 * @param lat2
     * @return String  单位：km （保留2位小数）
     * @Author dengbp
     * @Date 7:35 PM 12/26/20
     **/

    public static String getDistance(double lon1,double lat1,double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 *Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        DecimalFormat df=new DecimalFormat("0.00");
        return df.format((float)s/1000);
    }
}
