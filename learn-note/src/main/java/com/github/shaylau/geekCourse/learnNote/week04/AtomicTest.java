package com.github.shaylau.geekCourse.learnNote.week04;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ShayLau
 * @date 2022/2/7 11:27
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(1);

        integer.getAndIncrement();
        AtomicTest atomicTest=new AtomicTest();
        System.out.println(atomicTest.getSystemTime());

    }

    public native String getSystemTime();

    static {
        System.loadLibrary("nativedatetimeutils");
    }

}
