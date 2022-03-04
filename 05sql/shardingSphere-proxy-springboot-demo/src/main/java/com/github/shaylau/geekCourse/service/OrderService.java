package com.github.shaylau.geekCourse.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shaylau.geekCourse.entity.OrderInfo;
import com.github.shaylau.geekCourse.mapper.OrderInfoMapper;
import org.springframework.stereotype.Component;

/**
 * @author ShayLau
 * @date 2022/3/3 10:36
 */
@Component
public class OrderService extends ServiceImpl<OrderInfoMapper, OrderInfo> {

    /**
     * 插入订单数据
     */
    public void saveOrder() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGId("1");
        orderInfo.setUId(1L);
        orderInfo.setStatus(1);
        orderInfo.setPayStatus(1);
        orderInfo.setMoney(0.0);
        orderInfo.setCreateTime(System.currentTimeMillis());
        orderInfo.setUpdateTime(System.currentTimeMillis());
        save(orderInfo);
    }

    public void saveBatchData(){

    }


}
