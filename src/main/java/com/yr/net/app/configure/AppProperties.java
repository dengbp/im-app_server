package com.yr.net.app.configure;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author dengbp
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"file:${CONF_DIR}/app.properties"})
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private ShiroProperties shiro = new ShiroProperties();
    private SwaggerProperties swagger = new SwaggerProperties();
    private InterfaceProperties interfaceProperties = new InterfaceProperties();
    private MysqlProperties mysqlProperties = new MysqlProperties();

    private String admin_url;
    private String admin_secret;
    private boolean use_random_name;
    /** 从配置读到乱码 要设置 */
    private String welcome_for_new_user;
    private String welcome_for_back_user;
    private boolean new_user_robot_friend;
    private String robot_friend_id;
    private String robot_welcome;
    private boolean openAopLog = true;
    private boolean autoOpenBrowser = true;
    private String[] autoOpenBrowserEnv = {};
    private String multimedia_url;
    private String multimedia_path;
    private WxProperties wx = new WxProperties();
}
