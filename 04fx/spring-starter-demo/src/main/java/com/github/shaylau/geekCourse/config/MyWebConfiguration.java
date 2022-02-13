package com.github.shaylau.geekCourse.config;

import com.github.shaylau.geekCourse.entity.MyWeb;
import com.github.shaylau.geekCourse.prop.MyWebConfigurationProp;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author ShayLau
 * @date 2022/2/13 2:28 PM
 */
@EnableConfigurationProperties(MyWebConfigurationProp.class)
@Configuration
public class MyWebConfiguration {

    @Resource
    MyWebConfigurationProp myWebConfigurationProp;

    @Bean
    public MyWeb myWeb() {
        MyWeb myWeb = new MyWeb();
        myWeb.prop = myWebConfigurationProp;
        return myWeb;
    }
}
