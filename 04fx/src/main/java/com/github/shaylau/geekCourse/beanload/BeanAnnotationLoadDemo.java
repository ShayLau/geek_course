package com.github.shaylau.geekCourse.beanload;

import com.github.shaylau.geekCourse.entity.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring bean Annotation load
 * 使用注解方式 加载方式
 *
 * @author ShayLau
 * @date 2022/2/12 4:34 PM
 */
public class BeanAnnotationLoadDemo {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xml/annotationScan.xml");
        School school = (School) applicationContext.getBean("school");
        System.out.println("学校:" + school.getName() + ",班级数量：" + school.getGradeList().size());
    }
}
