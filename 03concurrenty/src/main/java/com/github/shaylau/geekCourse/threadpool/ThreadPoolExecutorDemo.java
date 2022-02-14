package com.github.shaylau.geekCourse.threadpool;

import java.util.concurrent.*;

/**
 * @author ShayLau
 * @date 2022/2/7 17:00
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, blockingQueue);

        Future<Object> future = threadPoolExecutor.submit(() -> System.out.print("HelloWorld"), "1");

        System.out.printf((String) future.get());
        threadPoolExecutor.shutdown();

    }
}
