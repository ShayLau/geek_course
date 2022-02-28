package com.github.shaylau.geekCourse.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shaylau.geekCourse.anno.ReadOnly;
import com.github.shaylau.geekCourse.dao.OrderInfoMapper;
import com.github.shaylau.geekCourse.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ShayLau
 * @date 2022/2/27 9:30 PM
 */
@Component
public class OrderInfoService extends ServiceImpl<OrderInfoMapper, OrderInfo> {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 读取订单数据
     *
     * @param id 订单 id
     */
    @ReadOnly
    public OrderInfo getOrderInfo(String id) {
        return orderInfoMapper.selectById(id);

    }

    /**
     * 插入订单数据
     */
    @ReadOnly(readOnly = false)
    public void insertOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfoMapper.insert(orderInfo);
    }
}
