package com.github.shaylau.geekCourse.controller;

import com.github.shaylau.geekCourse.service.OrderService;
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

    @RequestMapping("/info")
    public void getOrderInfo() {
        orderService.getOrderInfo();
    }


    @RequestMapping("/save")
    public void saveOrderInfo() {
        orderService.insertOrderInfo();
    }
}
