package com.github.shaylau.geekCourse.service;

import com.github.shaylau.geekCourse.anno.ReadOnly;
import org.springframework.stereotype.Component;

/**
 * @author ShayLau
 * @date 2022/2/27 9:30 PM
 */
@Component
public class OrderInfoService {

    /**
     * 读取订单数据
     */
    @ReadOnly
    public void getOrderInfo() {

    }

    /**
     * 插入订单数据
     */
    @ReadOnly(readOnly = false)
    public void insertOrderInfo() {

    }
}
