package com.github.shaylau.geekCourse.aop;

import com.github.shaylau.geekCourse.anno.ReadOnly;
import com.github.shaylau.geekCourse.config.DbSelectorHolder;
import com.github.shaylau.geekCourse.enums.DbTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ShayLau
 * @date 2022/2/27 9:32 PM
 */
@Component
public class DataSourceRouteAspect {


    @Pointcut("@annotation(com.github.shaylau.geekCourse.anno.ReadOnly)")
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
     * 在方法执行后移除 数据库 key
     */
    @After("readOnlyPointCut()")
    public void after() {
        // 移除线程本地数据库源变量
        DbSelectorHolder.remove();
    }

}
