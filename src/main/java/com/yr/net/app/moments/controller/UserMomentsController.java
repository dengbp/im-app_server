package com.yr.net.app.moments.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.moments.dto.UserMomentsReqDto;
import com.yr.net.app.moments.service.IUserMomentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description 用户动态
 * @Author dengbp
 * @Date 20:40 2020-12-17
 **/
@RestController
@RequestMapping("/moments/user-moments")
public class UserMomentsController {

    @Autowired
    private IUserMomentsService userMomentsService;

    @PostMapping("/list")
    @ControllerEndpoint(operation = "用户动态", exceptionMessage = "用户动态失败")
    @ResponseBody
    @Log("用户动态")
    public RestResult mottoEdit(@Valid UserMomentsReqDto reqDto){
        return RestResult.ok().setResult(userMomentsService.findUserMoments(reqDto));
    }

}
