package com.yr.net.app.monitor.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yr.net.app.common.entity.QueryRequestPage;
import com.yr.net.app.monitor.entity.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * @author d
 */
public interface ILogService extends IService<SysLog> {



    IPage<SysLog> findLogs(QueryRequestPage request, SysLog sysLog);

    void deleteLogs(String[] logIds);

    @Async
    void saveLog(ProceedingJoinPoint point, SysLog log) throws JsonProcessingException;
}
