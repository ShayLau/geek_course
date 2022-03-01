package com.github.shaylau.geekCourse.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shaylau.geekCourse.commons.anno.ReadOnly;
import com.github.shaylau.geekCourse.mapper.OrderInfoMapper;
import com.github.shaylau.geekCourse.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author ShayLau
 * @date 2022/2/27 9:30 PM
 */
@Service
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
        OrderInfo orderInfo = getById(id);

        System.out.println("订单信息：" + orderInfo);
        return orderInfo;
    }

    /**
     * 插入订单数据
     */
    @ReadOnly(readOnly = false)
    public void insertOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("1");
        orderInfo.setGId("1");
        orderInfo.setUId("1");
        orderInfo.setStatus(1);
        orderInfo.setPayStatus(1);
        orderInfo.setCreateTime(System.currentTimeMillis());
        orderInfo.setUpdateTime(System.currentTimeMillis());
        orderInfoMapper.insert(orderInfo);
    }
}
