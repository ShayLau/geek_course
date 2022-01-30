package com.github.shaylau.geekCourse.ways;

import com.github.shaylau.geekCourse.Fibonacci;

import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore信号量机制
 *
 * @author ShayLau
 * @date 2022/1/29 3:44 PM
 */
public class Way2 {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        Thread fiboThread = new Thread(() -> {
            System.out.println("异步计算结果为：" + Fibonacci.sum());
            semaphore.release();
        });
        fiboThread.start();
        semaphore.acquire();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        semaphore.release();
    }
}
