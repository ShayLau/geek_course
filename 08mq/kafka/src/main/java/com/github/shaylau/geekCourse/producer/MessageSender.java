package com.github.shaylau.geekCourse.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author ShayLau
 * @date 2022/4/6 11:15
 */
@Component
public class MessageSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    /**
     * 发送消息
     *
     * @param topic
     * @param data
     */
    public void send(String topic, Object data) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data.toString());
        future.addCallback(res -> System.out.println("发送成功"), ex -> System.out.println("发送失败"));
    }


}
