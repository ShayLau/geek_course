package com.github.shaylau.geekCourse.ways;

import com.github.shaylau.geekCourse.Fibonacci;

/**
 * 使用Thread.join() 方法
 *
 * @author ShayLau
 * @date 2022/1/29 3:44 PM
 */
public class Way1 {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Thread fiboThread = new Thread(() -> {
            int result = Fibonacci.sum();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);
        });

        fiboThread.start();
        fiboThread.join();

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
}
