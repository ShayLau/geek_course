package com.github.shaylau.geekCourse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 读写分离程序
 *
 * @author ShayLau
 * @date 2022/2/27 9:47 PM
 */
@MapperScan("com.github.shaylau.geekCourse.dao.*")
@SpringBootApplication
public class ReadWriteSeparationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReadWriteSeparationApplication.class, args);
    }
}
