package com.github.shaylau.geekCourse;

import com.github.shaylau.geekCourse.entity.MyWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author ShayLau
 * @date 2022/2/13 2:49 PM
 */
@SpringBootTest(classes = MyWebTestApplication.class)
@SpringBootApplication
public class MyWebTestApplication {
    @Resource
    private MyWeb myWeb;

    public static void main(String[] args) {
        SpringApplication.run(MyWebTestApplication.class);
    }

    @Bean
    public void print(){
        myWeb.printWebInfo();
    }

}
