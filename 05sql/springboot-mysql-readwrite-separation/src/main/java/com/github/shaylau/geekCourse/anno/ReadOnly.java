package com.github.shaylau.geekCourse.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 只读注解
 *
 * @author ShayLau
 * @date 2022/2/27 9:26 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {
    boolean readOnly() default true;
}
