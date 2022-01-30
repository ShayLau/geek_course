package com.github.shaylau.geekCourse.ways;

import com.github.shaylau.geekCourse.Fibonacci;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用CountDownLatch
 *
 * @author ShayLau
 * @date 2022/1/30 2:03 PM
 */
public class Way5 {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread fiboThread = new Thread(() -> {
            try {
                System.out.println("异步计算结果为：" + Fibonacci.sum());
            } finally {
                countDownLatch.countDown();
            }
        });
        fiboThread.start();

        //等待countDownLatch可执行
        countDownLatch.await();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
}
