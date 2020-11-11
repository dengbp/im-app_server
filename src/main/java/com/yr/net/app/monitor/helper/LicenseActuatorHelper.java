package com.yr.net.app.monitor.helper;

import com.google.common.base.Predicates;
import com.yr.net.app.common.annotation.Helper;
import com.yr.net.app.monitor.endpoint.AppMetricsEndpoint;
import com.yr.net.app.monitor.entity.JvmInfo;
import com.yr.net.app.monitor.entity.ServerInfo;
import com.yr.net.app.monitor.entity.TomcatInfo;
import com.yr.net.app.tools.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MrBird
 */
@Helper
public class LicenseActuatorHelper {

    private static final BigDecimal DECIMAL = new BigDecimal("1048576");

    @Autowired
    private AppMetricsEndpoint metricsEndpoint;

    public List<AppMetricsEndpoint.FebsMetricResponse> getMetricResponseByType(String type) {
        AppMetricsEndpoint.ListNamesResponse listNames = metricsEndpoint.listNames();
        Set<String> names = listNames.getNames();
        Iterable<String> jvm = names.stream()
                .filter(Predicates.containsPattern(type)::apply)
                .collect(Collectors.toList());
        List<AppMetricsEndpoint.FebsMetricResponse> metricResponseList = new ArrayList<>();
        jvm.forEach(s -> {
            AppMetricsEndpoint.FebsMetricResponse metric = metricsEndpoint.metric(s, null);
            metricResponseList.add(metric);
        });
        return metricResponseList;
    }

    public JvmInfo getJvmInfoFromMetricData(List<AppMetricsEndpoint.FebsMetricResponse> metrics) {
        JvmInfo jvmInfo = new JvmInfo();
        metrics.forEach(d -> {
            String name = d.getName();
            AppMetricsEndpoint.Sample sample = d.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "jvm.memory.max":
                    jvmInfo.setJvmMemoryMax(convertToMB(value));
                    break;
                case "jvm.memory.committed":
                    jvmInfo.setJvmMemoryCommitted(convertToMB(value));
                    break;
                case "jvm.memory.used":
                    jvmInfo.setJvmMemoryUsed(convertToMB(value));
                    break;
                case "jvm.buffer.memory.used":
                    jvmInfo.setJvmBufferMemoryUsed(convertToMB(value));
                    break;
                case "jvm.buffer.count":
                    jvmInfo.setJvmBufferCount(value);
                    break;
                case "jvm.threads.daemon":
                    jvmInfo.setJvmThreadsdaemon(value);
                    break;
                case "jvm.threads.live":
                    jvmInfo.setJvmThreadsLive(value);
                    break;
                case "jvm.threads.peak":
                    jvmInfo.setJvmThreadsPeak(value);
                    break;
                case "jvm.classes.loaded":
                    jvmInfo.setJvmClassesLoaded(value);
                    break;
                case "jvm.classes.unloaded":
                    jvmInfo.setJvmClassesUnloaded(value);
                    break;
                case "jvm.gc.memory.allocated":
                    jvmInfo.setJvmGcMemoryAllocated(convertToMB(value));
                    break;
                case "jvm.gc.memory.promoted":
                    jvmInfo.setJvmGcMemoryPromoted(convertToMB(value));
                    break;
                case "jvm.gc.max.data.size":
                    jvmInfo.setJvmGcMaxDataSize(convertToMB(value));
                    break;
                case "jvm.gc.live.data.size":
                    jvmInfo.setJvmGcLiveDataSize(convertToMB(value));
                    break;
                default:
            }
        });
        return jvmInfo;
    }

    public TomcatInfo getTomcatInfoFromMetricData(List<AppMetricsEndpoint.FebsMetricResponse> metrics) {
        TomcatInfo tomcatInfo = new TomcatInfo();
        metrics.forEach(d -> {
            String name = d.getName();
            AppMetricsEndpoint.Sample sample = d.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "tomcat.sessions.created":
                    tomcatInfo.setTomcatSessionsCreated(value);
                    break;
                case "tomcat.sessions.expired":
                    tomcatInfo.setTomcatSessionsExpired(value);
                    break;
                case "tomcat.sessions.active.current":
                    tomcatInfo.setTomcatSessionsActiveCurrent(value);
                    break;
                case "tomcat.sessions.active.max":
                    tomcatInfo.setTomcatSessionsActiveMax(value);
                    break;
                case "tomcat.sessions.rejected":
                    tomcatInfo.setTomcatSessionsRejected(value);
                    break;
                case "tomcat.global.error":
                    tomcatInfo.setTomcatGlobalError(value);
                    break;
                case "tomcat.global.sent":
                    tomcatInfo.setTomcatGlobalSent(value);
                    break;
                case "tomcat.global.request.max":
                    tomcatInfo.setTomcatGlobalRequestMax(value);
                    break;
                case "tomcat.threads.current":
                    tomcatInfo.setTomcatThreadsCurrent(value);
                    break;
                case "tomcat.threads.config.max":
                    tomcatInfo.setTomcatThreadsConfigMax(value);
                    break;
                case "tomcat.threads.busy":
                    tomcatInfo.setTomcatThreadsBusy(value);
                    break;
                default:
            }
        });
        return tomcatInfo;
    }

    public ServerInfo getServerInfoFromMetricData(List<AppMetricsEndpoint.FebsMetricResponse> jdbcInfo,
                                                  List<AppMetricsEndpoint.FebsMetricResponse> systemInfo,
                                                  List<AppMetricsEndpoint.FebsMetricResponse> processInfo) {
        ServerInfo serverInfo = new ServerInfo();
        jdbcInfo.forEach(j -> {
            String name = j.getName();
            AppMetricsEndpoint.Sample sample = j.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "jdbc.connections.active":
                    serverInfo.setJdbcConnectionsActive(value);
                    break;
                case "jdbc.connections.max":
                    serverInfo.setJdbcConnectionsMax(value);
                    break;
                case "jdbc.connections.min":
                    serverInfo.setJdbcConnectionsMin(value);
                    break;
                default:
            }
        });
        systemInfo.forEach(s -> {
            String name = s.getName();
            AppMetricsEndpoint.Sample sample = s.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "system.cpu.count":
                    serverInfo.setSystemCpuCount(value);
                    break;
                case "system.cpu.usage":
                    serverInfo.setSystemCpuUsage(value);
                    break;
                default:
            }
        });
        processInfo.forEach(p -> {
            String name = p.getName();
            AppMetricsEndpoint.Sample sample = p.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "process.cpu.usage":
                    serverInfo.setProcessCpuUsage(value);
                    break;
                case "process.uptime":
                    serverInfo.setProcessUptime(value);
                    break;
                case "process.start.time":
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    numberFormat.setMaximumFractionDigits(20);
                    numberFormat.setGroupingUsed(false);
                    long timeMillis = Long.valueOf(StringUtils.replace(numberFormat.format(value), ".", ""));
                    String startTime = DateUtil.getDateFormat(new Date(timeMillis), DateUtil.FULL_TIME_SPLIT_PATTERN);
                    serverInfo.setProcessStartTime(startTime);
                default:
            }
        });
        return serverInfo;
    }

    private static Double convertToMB(Object value) {
        return new BigDecimal(String.valueOf(value))
                .divide(DECIMAL, 3, RoundingMode.HALF_UP).doubleValue();
    }
}
