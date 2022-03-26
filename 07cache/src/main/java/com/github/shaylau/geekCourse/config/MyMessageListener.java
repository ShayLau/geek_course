package com.github.shaylau.geekCourse.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @author ShayLau
 * @date 2022/3/26 9:07 PM
 */
@Component
public class MyMessageListener extends MessageListenerAdapter {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        super.onMessage(message, pattern);
        System.out.println("收到消息体:" + message.getBody());
    }
}
