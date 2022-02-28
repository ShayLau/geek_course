package com.github.shaylau.geekCourse.controller;

import com.github.shaylau.geekCourse.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单信息控制器
 *
 * @author ShayLau
 * @date 2022/2/28 18:05
 */
@RequestMapping("/order/info")
@RestController
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;


    @RequestMapping("/info")
    public void getOrderInfo() {
        orderInfoService.getOrderInfo("1");
    }

    @RequestMapping("/save")
    public void saveOrderInfo() {
        orderInfoService.insertOrderInfo();
    }
}
