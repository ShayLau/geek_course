package com.github.shaylau.geekCourse.controller;

import com.github.shaylau.geekCourse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShayLau
 * @date 2022/3/2 18:47
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 保存订单
     */
    @PostMapping("/save")
    public void saveOrder() {
        orderService.saveOrder();
    }


    public void saveBatchData(){

    }
}
