package com.github.shaylau.geekCourse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生
 *
 * @author ShayLau
 * @date 2022/2/12 4:13 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
}
