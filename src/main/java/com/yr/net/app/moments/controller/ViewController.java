package com.yr.net.app.moments.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.moments.dto.MomentsViewReqDto;
import com.yr.net.app.moments.service.IViewService;
import com.yr.net.app.tools.AppUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Description 用户浏览动态控制器(废弃由后台直接实现，在用户加载动态的时候)
 * @Author dengbp
 * @Date 1:13 PM 6/22/21
 */
@RestController
@RequestMapping("/moments/view")
public class ViewController {

    @Resource
    private IViewService viewService;

    @PostMapping("/add")
    @ControllerEndpoint(operation = "动态浏览", exceptionMessage = "动态浏览失败")
    @ResponseBody
    @Log("动态浏览接口")
    public RestResult view(@RequestBody @Valid MomentsViewReqDto viewReqDto){
        viewReqDto.setViewUserId(AppUtil.getCurrentUserId());
        viewService.add(viewReqDto);
        return RestResult.ok();
    }

}
