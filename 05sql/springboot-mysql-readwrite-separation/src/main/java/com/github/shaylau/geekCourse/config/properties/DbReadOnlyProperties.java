package com.github.shaylau.geekCourse.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库配置
 *
 * @author ShayLau
 * @date 2022/2/27 9:37 PM
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "db.readonly")
public class DbReadOnlyProperties {

    /**
     * 数据库驱动
     */
    private String driverClassName;
    /**
     * url
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

}
