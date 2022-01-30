package com.github.shaylau.geekCourse.ways;

import com.github.shaylau.geekCourse.Fibonacci;
import com.github.shaylau.geekCourse.ways.threads.Way3Runnable;

/**
 * 使用Object wait notify等待通知机制
 *
 * @author ShayLau
 * @date 2022/1/29 3:44 PM
 */
public class Way3 {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        Object object = new Object();
        Thread fiboThread = new Thread(new Way3Runnable(object));
        fiboThread.start();
        synchronized (object) {
            object.wait();
        }
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }


}
