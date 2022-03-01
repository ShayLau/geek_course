package com.github.shaylau.geekCourse.aop;

import com.github.shaylau.geekCourse.commons.anno.ReadOnly;
import com.github.shaylau.geekCourse.config.DbSelectorHolder;
import com.github.shaylau.geekCourse.commons.enums.DbTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ShayLau
 * @date 2022/2/27 9:32 PM
 */
@Aspect
@Component
public class DataSourceRouteAspect {


    @Pointcut("@annotation(com.github.shaylau.geekCourse.commons.anno.ReadOnly)")
    public void readOnlyPointCut() {
    }

    /**
     * 在方法执行前设置数据库 key
     *
     * @param point
     */
    @Before("readOnlyPointCut()")
    public void before(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        ReadOnly readOnly = method.getAnnotation(ReadOnly.class);
        if (!readOnly.readOnly()) {
            //主库操作数据
            DbSelectorHolder.setSelect(DbTypeEnum.write);
        } else {
            //从库操作数据
            DbSelectorHolder.setSelect(DbTypeEnum.read);
        }
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     */
    @Around("readOnlyPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        //前置
//        System.out.println("before");

        //执行切点
        joinPoint.proceed();

        //后置
//        System.out.println("after");

    }


    /**
     * 在方法执行后移除 数据库 key
     */
    @After("readOnlyPointCut()")
    public void after() {
        // 移除线程本地数据库源变量
        DbSelectorHolder.remove();
    }

}
