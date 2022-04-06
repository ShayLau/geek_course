package com.github.shaylau.geekCourse.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author ShayLau
 * @date 2022/4/6 11:13
 */
@Component
public class MessageListener {

    /**
     * 消费者监听消息
     *
     * @param message 消息
     */
    @KafkaListener(topics = "mytopic")
    public void listen(String message) {
        System.out.println("收到消息：" + message);
    }

}
