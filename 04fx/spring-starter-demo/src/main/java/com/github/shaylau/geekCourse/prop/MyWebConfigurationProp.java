package com.github.shaylau.geekCourse.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * web属性配置
 *
 * @author ShayLau
 * @date 2022/2/13 2:29 PM
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "myweb")
public class MyWebConfigurationProp {

    /**
     * web name
     */
    private String webName;

    /**
     * ip 地址
     */
    private String ipaddress;

    /**
     * 端口
     */
    private Integer port;

}
