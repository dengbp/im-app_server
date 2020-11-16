package com.yr.net.app.customer.controller;


import com.yr.net.app.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.CoordinateRequestDto;
import com.yr.net.app.customer.entity.UserCoordinate;
import com.yr.net.app.customer.service.IUserCoordinateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 */
@Slf4j
@RestController
@RequestMapping("/customer/user-coordinate")
public class UserCoordinateController {

    @Autowired
    IUserCoordinateService userCoordinateService;

    @PostMapping("/coordinate")
    @ControllerEndpoint(operation = "查询用户在线", exceptionMessage = "查询用户在线失败")
    @ResponseBody
    public RestResult coordinate(@Valid CoordinateRequestDto request)throws AppException{
        userCoordinateService.save(this.copyProperties(request,new UserCoordinate()));
        return RestResult.ok();
    }

    private UserCoordinate copyProperties(CoordinateRequestDto sources, UserCoordinate target){
        target.setUserId(Long.parseLong(sources.getUserId()));
        target.setLatitude(new BigDecimal(sources.getLatitude()));
        target.setLongitude(new BigDecimal(sources.getLongitude()));
        target.setCreatedTime(LocalDateTime.now());
        return target;
    }

}
