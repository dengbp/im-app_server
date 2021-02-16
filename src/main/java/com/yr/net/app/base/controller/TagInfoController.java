package com.yr.net.app.base.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.base.service.ITagInfoService;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description 标签数据控制器
 * @Author dengbp
 * @Date 2:09 AM 2/16/21
 **/
@RestController
@RequestMapping("/base/tag-info")
public class TagInfoController {

    @Resource
    private ITagInfoService tagInfoService;


    @PostMapping("character/list")
    @ControllerEndpoint(operation = "性格数据查询", exceptionMessage = "性格数据查询失败")
    @ResponseBody
    @Log("性格数据查询接口")
    public RestResult character(){
        return RestResult.ok().setResult(tagInfoService.character());
    }

}
