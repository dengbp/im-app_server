package com.yr.net.app.monitor.controller;

import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.entity.AppResponse;
import com.yr.net.app.monitor.endpoint.AppHttpTraceEndpoint;
import com.yr.net.app.monitor.entity.AppHttpTrace;
import com.yr.net.app.tools.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequestMapping("febs/actuator")
public class AppActuatorController {

    @Autowired
    private AppHttpTraceEndpoint httpTraceEndpoint;

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    @ControllerEndpoint(exceptionMessage = "请求追踪失败")
    public AppResponse httpTraces(String method, String url) {
        AppHttpTraceEndpoint.FebsHttpTraceDescriptor traces = httpTraceEndpoint.traces();
        List<HttpTrace> httpTraceList = traces.getTraces();
        List<AppHttpTrace> febsHttpTraces = new ArrayList<>();
        httpTraceList.forEach(t -> {
            AppHttpTrace febsHttpTrace = new AppHttpTrace();
            febsHttpTrace.setRequestTime(DateUtil.formatInstant(t.getTimestamp(), DateUtil.FULL_TIME_SPLIT_PATTERN));
            febsHttpTrace.setMethod(t.getRequest().getMethod());
            febsHttpTrace.setUrl(t.getRequest().getUri());
            febsHttpTrace.setStatus(t.getResponse().getStatus());
            febsHttpTrace.setTimeTaken(t.getTimeTaken());
            if (StringUtils.isNotBlank(method) && StringUtils.isNotBlank(url)) {
                if (StringUtils.equalsIgnoreCase(method, febsHttpTrace.getMethod())
                        && StringUtils.containsIgnoreCase(febsHttpTrace.getUrl().toString(), url))
                    febsHttpTraces.add(febsHttpTrace);
            } else if (StringUtils.isNotBlank(method)) {
                if (StringUtils.equalsIgnoreCase(method, febsHttpTrace.getMethod()))
                    febsHttpTraces.add(febsHttpTrace);
            } else if (StringUtils.isNotBlank(url)) {
                if (StringUtils.containsIgnoreCase(febsHttpTrace.getUrl().toString(), url))
                    febsHttpTraces.add(febsHttpTrace);
            } else {
                febsHttpTraces.add(febsHttpTrace);
            }
        });
        Map<String, Object> data = new HashMap<>();
        data.put("rows", febsHttpTraces);
        data.put("total", febsHttpTraces.size());
        return new AppResponse().success().data(data);
    }
}
