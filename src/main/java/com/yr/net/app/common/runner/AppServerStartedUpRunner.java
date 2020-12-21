package com.yr.net.app.common.runner;

import com.yr.net.app.configure.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;

/**
 * @author dengbp
 */
@Slf4j
@Component
public class AppServerStartedUpRunner implements ApplicationRunner {

    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    AppProperties appProperties;

    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean testRedis = false;
        try {
            /*// 测试 Redis连接是否正常
            redisService.exists("segi_test");
            log.info("缓存初始化 ······");
            log.info("缓存用户数据 ······");
            List<User> list = this.userService.list();
            for (User user : list) {
                userManager.loadUserRedisCache(user);
            }*/
            testRedis = true;
        } catch (Exception e) {
            log.error("app-server启动失败，{}", e.getMessage());
            e.printStackTrace();
            log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");
            context.close();
        }
        if (testRedis) {
            InetAddress address = InetAddress.getLocalHost();
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            String loginUrl = appProperties.getShiro().getLoginUrl();
            if (StringUtils.isNotBlank(contextPath)){
                url += contextPath;
            }
            if (StringUtils.isNotBlank(loginUrl)) {
                url += loginUrl;
            }
            log.info(" __    ___   _      ___   _     ____ _____  ____ ");
            log.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
            log.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
            log.info("                                                      ");
            log.info("app-server 权限系统启动完毕，地址：{}", url);

            boolean auto = appProperties.isAutoOpenBrowser();
            String[] autoEnv = appProperties.getAutoOpenBrowserEnv();
            if (auto && ArrayUtils.contains(autoEnv, active)) {
                String os = System.getProperty("os.name");
                // 默认为 windows时才自动打开页面
                if (StringUtils.containsIgnoreCase(os, "windows")) {
                    //使用默认浏览器打开系统登录页
                    Runtime.getRuntime().exec("cmd  /c  start " + url);
                }
            }
        }
    }
}
