package com.github.shaylau.geekCourse.learnNote.week04;

import java.util.Vector;

/**
 * @author ShayLau
 * @date 2022/1/26 4:09 PM
 */
public class VectorTest {

    void addIfNotExist(Vector v, Object o) {
        if (!v.contains(o)) {
            System.out.println("当前执行线程：" + Thread.currentThread().getName() + ":,新增数据：" + o);
            v.add(o);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Vector<String> vector = new Vector<>();
        VectorTest vectorTest = new VectorTest();

        Thread thread1 = new Thread(() -> {
            int num = 1000;
            while (num > 0) {
                num--;
                vectorTest.addIfNotExist(vector, num);
            }
        });
        Thread thread2 = new Thread(() -> {
            int num = 1000;
            while (num > 0) {
                num--;
                vectorTest.addIfNotExist(vector, num);
            }
        });
        thread1.start();
        thread2.start();
    }
}
