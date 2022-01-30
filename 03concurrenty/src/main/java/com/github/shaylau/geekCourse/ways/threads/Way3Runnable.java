package com.github.shaylau.geekCourse.ways.threads;

import com.github.shaylau.geekCourse.Fibonacci;

/**
 * @author ShayLau
 * @date 2022/1/29 5:33 PM
 */
public class Way3Runnable implements Runnable {
    public int result;
    public Object object;

    public Way3Runnable(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (object) {
            result = Fibonacci.sum();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);
            object.notifyAll();
        }
    }
}
