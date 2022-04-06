package com.github.shaylau.geekCourse.service;

import com.github.shaylau.geekCourse.producer.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShayLau
 * @date 2022/4/6 11:44
 */
@Service
public class MessageService {

    @Autowired
    private MessageSender messageSender;


    /**
     * 发送消息
     *
     * @param topic
     * @param message
     */
    public void sendMessage(String topic, String message) {
        messageSender.send(topic, message);
    }

}
