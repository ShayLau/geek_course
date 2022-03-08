package com.github.shaylau.geekCourse.service;


import com.github.shaylau.geekCourse.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Order" + System.currentTimeMillis(), 9.9f);
    }
}
