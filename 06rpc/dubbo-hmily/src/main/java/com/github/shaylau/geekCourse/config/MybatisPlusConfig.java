package com.github.shaylau.geekCourse.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ShayLau
 * @date 2022/3/11 11:44
 */
@Configuration
@MapperScan("com.github.shaylau.geekCourse.mapper")
public class MybatisPlusConfig {
}
