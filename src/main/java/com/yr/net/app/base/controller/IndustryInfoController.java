package com.yr.net.app.base.controller;


import com.yr.net.app.base.dto.JobInfoRequestDto;
import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.base.service.IIndustryInfoService;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/base/industry-info")
public class IndustryInfoController {

    @Resource
    private IIndustryInfoService iIndustryInfoService;

    @PostMapping("job/list")
    @ControllerEndpoint(operation = "职业数据查询", exceptionMessage = "职业数据查询失败")
    @ResponseBody
    @Log("职业数据查询接口")
    public RestResult jobList(@RequestBody JobInfoRequestDto requestDto){
        return RestResult.ok().setResult(iIndustryInfoService.getJobInfo(requestDto.getParentId()));
    }

}
