package com.github.shaylau.geekCourse.config;

import com.github.shaylau.geekCourse.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ShayLau
 * @date 2022/2/12 5:15 PM
 */
@Configuration
public class WebConfiguration {

    @Bean
    public Student student() {
        return new Student("法外狂徒张三", 40);
    }

}
