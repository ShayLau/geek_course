package com.github.shaylau.geekCourse.beanload;

import com.github.shaylau.geekCourse.entity.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 使用自动装配初始化Bean
 *
 * @author ShayLau
 * @date 2022/2/12 5:12 PM
 */
@SpringBootApplication
public class AutoConfigurationLoadDemo {

    @Resource
    private Student student;

    @Bean
    public void print() {
        System.out.println("使用Configuration中定义的Student Bean实现自动装配，打印Student信息：" + student);
    }

    public static void main(String[] args) {
        SpringApplication.run(AutoConfigurationLoadDemo.class, args);
    }


}
