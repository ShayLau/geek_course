package com.github.shaylau.geekCourse.ways;

import com.github.shaylau.geekCourse.Fibonacci;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 使用CyclicBarrier
 *
 * @author ShayLau
 * @date 2022/1/30 2:03 PM
 */
public class Way6 {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CyclicBarrier cyclicBarrier=new CyclicBarrier(1,()->{
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        });
        Thread fiboThread = new Thread(() -> {
            try {
                System.out.println("异步计算结果为：" + Fibonacci.sum());
            } finally {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        fiboThread.start();
    }
}
