package com.github.shaylau.geekCourse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 班级
 *
 * @author ShayLau
 * @date 2022/2/12 4:24 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    /**
     * 班级名称
     */
    private String name;
    /**
     * 学生
     */
    private List<Student> studentList;
}
