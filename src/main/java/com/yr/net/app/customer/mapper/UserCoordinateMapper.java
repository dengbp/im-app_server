package com.yr.net.app.customer.mapper;

import com.yr.net.app.customer.entity.UserCoordinate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dengbp
 */
public interface UserCoordinateMapper extends BaseMapper<UserCoordinate> {

    /**
     * Description 按最大最小经维度查数据
     * @param minlng
 * @param maxlng
 * @param minlat
 * @param maxlat
     * @return java.util.List<com.yr.net.app.customer.entity.UserCoordinate>
     * @Author dengbp
     * @Date 10:15 PM 12/26/20
     **/
    List<UserCoordinate> selectNear(@Param("minlng") double minlng, @Param("maxlng") double maxlng, @Param("minlat") double minlat,@Param("maxlat")  double maxlat);
}
