package com.yr.net.app.moments.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.moments.dto.ReportAbusementReqDto;
import com.yr.net.app.moments.service.IAbusementReportService;
import com.yr.net.app.tools.AppUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/moments/report")
public class AbusementReportController {

    @Resource
    private IAbusementReportService abusementReportService;

    @PostMapping("/add")
    @ControllerEndpoint(operation = "举报", exceptionMessage = "举报失败")
    @ResponseBody
    @Log("举报接口")
    public RestResult add(@RequestBody @Valid ReportAbusementReqDto reqDto){
        reqDto.setReportUserId(AppUtil.getCurrentUserId());
        abusementReportService.add(reqDto);
        return RestResult.ok().setMessage("举报成功");
    }

}
