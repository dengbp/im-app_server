package com.yr.net.app.customer.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.CoordinateRequestDto;
import com.yr.net.app.customer.entity.UserCoordinate;
import com.yr.net.app.customer.service.IUserCoordinateService;
import com.yr.net.app.customer.service.IUserInfoDetailService;
import com.yr.net.app.log.entity.UserSignLog;
import com.yr.net.app.log.service.IUserSignLogService;
import com.yr.net.app.pojo.BaiduMapPositionResponse;
import com.yr.net.app.tools.AddressByCoordUtil;
import com.yr.net.app.tools.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 */
@Slf4j
@RestController
@RequestMapping("/customer/user-coordinate/")
public class UserCoordinateController {

    @Autowired
    IUserCoordinateService userCoordinateService;

    @Resource
    private IUserSignLogService userSignLogService;


    @PostMapping("coordinate")
    @ControllerEndpoint(operation = "用户经纬度", exceptionMessage = "用户经纬度上报失败")
    @ResponseBody
    @Log("用户经纬度上报")
    public RestResult coordinate(@RequestBody @Valid CoordinateRequestDto request)throws AppException{
        try {
            UserCoordinate userCoordinate = this.copyProperties(request,new UserCoordinate());
            userCoordinateService.save(userCoordinate);
            userSignLogService.save(this.createLog(userCoordinate));
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.error("根据经维度获取地址异常");
        }
        return RestResult.ok();
    }

    private UserSignLog createLog(UserCoordinate userCoordinate){
        UserSignLog log = new UserSignLog();
        log.setSignInTime(LocalDateTime.now());
        log.setCreatedTime(LocalDateTime.now());
        log.setUserId(AppUtil.getCurrentUserId());
        log.setSignAddr(userCoordinate.getCity()+userCoordinate.getDistrict());
        return log;
    }

    private UserCoordinate copyProperties(CoordinateRequestDto sources, UserCoordinate target)throws Exception{
        target.setUserId(AppUtil.getCurrentUserId());
        target.setLatitude(new BigDecimal(sources.getLatitude()));
        target.setLongitude(new BigDecimal(sources.getLongitude()));
        target.setCreatedTime(LocalDateTime.now());
        BaiduMapPositionResponse response = AddressByCoordUtil.getAdd(target.getLatitude().toString(),target.getLongitude().toString());
        if (response == null){
            throw new Exception("上传经维度异常");
        }
        BeanUtils.copyProperties(response,target);
        return target;
    }

}
