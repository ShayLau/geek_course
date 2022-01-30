package com.github.shaylau.geekCourse;

/**
 * 斐波那契数列
 *
 * @author ShayLau
 * @date 2022/1/29 3:44 PM
 */
public class Fibonacci {

    public static int sum() {
        return fibo(36);
    }

    /**
     * 递归实现斐波那契数列
     *
     * @param a
     * @return
     */
    public static int fibo(int a) {
        if (a < 2) return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
