package com.yr.net.app.moments.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.dto.ReportAbusementReqDto;
import com.yr.net.app.moments.entity.AbusementReport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dengbp
 */
public interface IAbusementReportService extends IService<AbusementReport> {

    void add(ReportAbusementReqDto reqDto)throws AppException;
}
