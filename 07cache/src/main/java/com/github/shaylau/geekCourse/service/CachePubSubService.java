package com.github.shaylau.geekCourse.service;

import com.github.shaylau.geekCourse.constant.MqConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ShayLau
 * @date 2022/3/26 7:36 PM
 */
@Component
public class CachePubSubService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * redis生产者测试
     *
     * @param data
     * @return
     */
    public void sendData(String data) {
        redisTemplate.convertAndSend(MqConstants.topic, data);
    }

}
