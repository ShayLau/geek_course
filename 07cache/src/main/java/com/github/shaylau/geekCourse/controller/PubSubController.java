package com.github.shaylau.geekCourse.controller;

import com.github.shaylau.geekCourse.service.CachePubSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis 消息订阅发布
 *
 * @author ShayLau
 * @date 2022/3/26 9:17 PM
 */
@RequestMapping("/pubsub")
@RestController
public class PubSubController {

    @Autowired
    private CachePubSubService cachePubSubService;


    /**
     * 发送消息
     *
     * @param data
     */
    @RequestMapping(method = RequestMethod.GET, value = "sendMessage")
    public void sendMessage(String data) {
        cachePubSubService.sendData(data);
    }

}
