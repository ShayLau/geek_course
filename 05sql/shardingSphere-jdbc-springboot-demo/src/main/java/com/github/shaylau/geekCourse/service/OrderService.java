package com.github.shaylau.geekCourse.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shaylau.geekCourse.entity.OrderInfo;
import com.github.shaylau.geekCourse.mapper.OrderInfoMapper;
import org.springframework.stereotype.Component;

/**
 * @author ShayLau
 * @date 2022/2/27 10:16 PM
 */
@Component
public class OrderService extends ServiceImpl<OrderInfoMapper, OrderInfo> {

    public static final String example_id = "s1";

    /**
     * 获取订单信息
     */
    public void getOrderInfo() {
        OrderInfo orderInfo = getById(example_id);
        System.out.println("订单信息：" + orderInfo);
    }

    /**
     * 插入订单数据
     */
    public void insertOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(example_id);
        orderInfo.setGId("1");
        orderInfo.setUId("1");
        orderInfo.setStatus(1);
        orderInfo.setPayStatus(1);
        orderInfo.setCreateTime(System.currentTimeMillis());
        orderInfo.setUpdateTime(System.currentTimeMillis());
        save(orderInfo);
    }
}
