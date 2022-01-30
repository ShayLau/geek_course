package com.github.shaylau.geekCourse.ways;

import com.github.shaylau.geekCourse.Fibonacci;

import java.util.Random;
import java.util.concurrent.*;


/**
 * 使用 Future实现
 *
 * @author ShayLau
 * @date 2022/1/30 2:42 PM
 */
public class Way7 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(Fibonacci::sum);

        System.out.println("异步计算结果为：" + future.get());
        executor.shutdown();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }
}
