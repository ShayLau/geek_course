package com.github.shaylau.geekCourse.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author ShayLau
 * @date 2022/3/8 16:14
 */
@Aspect
public class RpcAop {

    @Pointcut("com.github.shaylau.geekCourse.service")
    public void rpcPointCut(){
    }



    @Before("rpcPointCut()")
    public void before(JoinPoint point) {


    }





    @After("rpcPointCut()")
    public void after() {


    }

    @AfterThrowing
    public void exception(Throwable throwable){

      //  throwable.getMessage();
    }


}
