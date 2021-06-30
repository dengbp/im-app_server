package com.yr.net.app.moments.service.impl;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.dto.ReportAbusementReqDto;
import com.yr.net.app.moments.entity.AbusementReport;
import com.yr.net.app.moments.mapper.AbusementReportMapper;
import com.yr.net.app.moments.service.IAbusementReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class AbusementReportServiceImpl extends ServiceImpl<AbusementReportMapper, AbusementReport> implements IAbusementReportService {


    @Override
    public void add(ReportAbusementReqDto reqDto) throws AppException {
        AbusementReport report = new AbusementReport();
        report.setContent(reqDto.getReport());
        report.setMomentId(reqDto.getMomentId());
        report.setPublicUserId(reqDto.getPublicUserId());
        report.setReportUserId(reqDto.getReportUserId());
        save(report);
    }
}
