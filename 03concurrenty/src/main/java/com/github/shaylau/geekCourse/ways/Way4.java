package com.github.shaylau.geekCourse.ways;

import com.github.shaylau.geekCourse.Fibonacci;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lock condition方法
 * <description>
 * condition可以单独使用吗？
 * condition 为什么需要先上锁才能使用？
 * </description>
 *
 * @author ShayLau
 * @date 2022/1/30 2:03 PM
 */
public class Way4 {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Lock lock = new ReentrantLock();
        Condition waitCondition = lock.newCondition();
        Thread fiboThread = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("异步计算结果为：" + Fibonacci.sum());
            } finally {
                waitCondition.signal();
                lock.unlock();
            }
        });
        fiboThread.start();

        lock.lock();
        waitCondition.await();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        lock.unlock();
    }
}
