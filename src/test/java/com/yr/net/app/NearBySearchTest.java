package com.yr.net.app;

import com.yr.net.app.customer.entity.UserCoordinate;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserCoordinateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengbp
 * @ClassName NearBySearchTest
 * @Description 搜附近的人
 * @date 12/26/20 8:14 PM
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class NearBySearchTest {

    private SpatialContext spatialContext = SpatialContext.GEO;

    @Autowired
    IUserCoordinateService userCoordinateService;

    @Test
    public void search(){
        UserCoordinate coordinate = userCoordinateService.findByUserId("userId");
        double lng = coordinate.getLongitude().doubleValue();
        double lat = coordinate.getLatitude().doubleValue();

        //1.获取外接正方形
        Rectangle rectangle = getRectangle(5, lng, lat);
        //2.获取位置在正方形内的所有用户
        List<UserCoordinate> users = userCoordinateService.selectByAndCoordina(rectangle.getMinX(), rectangle.getMaxX(), rectangle.getMinY(), rectangle.getMaxY());
        //3.剔除半径超过指定距离的多余用户
        users = users.stream()
                .filter(a -> getDistance(a.getLongitude().doubleValue(), a.getLatitude().doubleValue(), lng, lat) <= 5)
                .collect(Collectors.toList());
    }

    /**
     * Description 获取外接正方形
     * @param distance	  搜索距离范围 单位km
 * @param userLng 当前用户的经度
 * @param userLat 当前用户的纬度
     * @return org.locationtech.spatial4j.shape.Rectangle
     * @Author dengbp
     * @Date 8:31 PM 12/26/20
     **/
    private Rectangle getRectangle(double distance, double userLng, double userLat) {
        return spatialContext.getDistCalc()
                .calcBoxByDistFromPt(spatialContext.makePoint(userLng, userLat),
                        distance * DistanceUtils.KM_TO_DEG, spatialContext, null);
    }

    /***
     * 球面中，两点间的距离
     * @param longitude 经度1
     * @param latitude  纬度1
     * @param userLng   经度2
     * @param userLat   纬度2
     * @return 返回距离，单位km
     */
    private double getDistance(Double longitude, Double latitude, double userLng, double userLat) {
        return spatialContext.calcDistance(spatialContext.makePoint(userLng, userLat),
                spatialContext.makePoint(longitude, latitude)) * DistanceUtils.DEG_TO_KM;
    }
}
