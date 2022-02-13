package com.github.shaylau.geekCourse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 学校
 *
 * @author ShayLau
 * @date 2022/2/12 4:36 PM
 */
@AllArgsConstructor
@Data
@Component
public class School {
    /**
     * 姓名
     */
    private String name;
    /**
     * 班级
     */
    private List<Grade> gradeList;

    public School() {
        this.name = "北京市第一中学";
        Grade grade = new Grade();
        grade.setName("一年级");
        List<Grade> gradeList=new ArrayList<>();
        gradeList.add(grade);
        this.gradeList=gradeList;
    }
}
