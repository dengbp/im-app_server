package com.yr.net.app.base.controller;


import com.yr.net.app.base.dto.PlacesRequestDto;
import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.base.service.IChPlacesService;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description 省市区控制器
 * @Author dengbp
 * @Date 2:09 AM 2/16/21
 **/
@RestController
@RequestMapping("/base/ch-places")
@Slf4j
public class ChPlacesController {

    @Resource
    private IChPlacesService chPlacesService;

    @PostMapping("places/list")
    @ControllerEndpoint(operation = "省市查询", exceptionMessage = "省市查询失败")
    @ResponseBody
    @Log("省市查询接口")
    public RestResult character(@RequestBody PlacesRequestDto requestDto){
        if (requestDto.getParentId() == null){
            return RestResult.error("父节点id不能为空");
        }
        return RestResult.ok().setResult(chPlacesService.findByParentId(requestDto.getParentId()));
    }


}
