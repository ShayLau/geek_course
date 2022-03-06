package com.github.shaylau.geekCourse.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shaylau.geekCourse.entity.OrderInfo;
import com.github.shaylau.geekCourse.mapper.OrderInfoMapper;
import org.apache.shardingsphere.transaction.annotation.ShardingSphereTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 使用分布式保存
 *
 * @author ShayLau
 * @date 2022/2/27 10:16 PM
 */
@Component
public class OrderService extends ServiceImpl<OrderInfoMapper, OrderInfo> {

    /**
     * Execute XA.
     *
     * @return transaction type
     */
    @Transactional
    @ShardingSphereTransactionType(TransactionType.XA)
    public TransactionType insertOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGId("1");
        orderInfo.setUId(1L);
        orderInfo.setStatus(1);
        orderInfo.setPayStatus(1);
        orderInfo.setCreateTime(System.currentTimeMillis());
        orderInfo.setUpdateTime(System.currentTimeMillis());
        save(orderInfo);
        return TransactionTypeHolder.get();
    }
}
