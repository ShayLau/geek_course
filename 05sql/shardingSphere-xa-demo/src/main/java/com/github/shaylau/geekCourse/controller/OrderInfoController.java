package com.github.shaylau.geekCourse.controller;

import com.github.shaylau.geekCourse.service.OrderService;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShayLau
 * @date 2022/2/27 10:16 PM
 */
@RequestMapping("/order")
@RestController
public class OrderInfoController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/save")
    public void saveOrderInfo() {
        TransactionType transactionType = orderService.insertOrderInfo();
        System.out.println("是分布式事务:" + TransactionType.isDistributedTransaction(transactionType));
    }
}
