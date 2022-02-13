package com.github.shaylau.geekCourse;

import com.github.shaylau.geekCourse.entity.MyWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author ShayLau
 * @date 2022/2/13 3:41 PM
 */
@SpringBootApplication
public class StarterRefApplication {

    @Resource
    MyWeb myWeb;

    @Bean
    public void printWebInfo() {
        myWeb.printWebInfo();
    }

    public static void main(String[] args) {
        SpringApplication.run(StarterRefApplication.class, args);
    }
}
