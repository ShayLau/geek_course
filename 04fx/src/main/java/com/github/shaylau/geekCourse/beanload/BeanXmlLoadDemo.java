package com.github.shaylau.geekCourse.beanload;

import com.github.shaylau.geekCourse.entity.Grade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring bean Xml load
 * xml 加载方式
 *
 * @author ShayLau
 * @date 2022/2/12 4:07 PM
 */
public class BeanXmlLoadDemo {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xml/bean.xml");
        Grade grade = (Grade) applicationContext.getBean("grade");
        System.out.println("班级:" + grade.getName() + "，学生数量：" + grade.getStudentList().size());
        //输出班级中学生信息
        grade.getStudentList().forEach(System.out::print);
    }
}
