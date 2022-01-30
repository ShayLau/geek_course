package com.github.shaylau.geekCourse.ways;

import com.github.shaylau.geekCourse.Fibonacci;

import java.util.concurrent.*;


/**
 * 使用FutureTask实现
 *
 * @author ShayLau
 * @date 2022/1/30 2:42 PM
 */
public class Way8 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        FutureTask<Integer> future = new FutureTask<>(Fibonacci::sum);
        new Thread(future).start();
        System.out.println("异步计算结果为：" + future.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }
}
