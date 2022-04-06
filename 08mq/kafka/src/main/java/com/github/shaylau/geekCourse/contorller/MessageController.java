package com.github.shaylau.geekCourse.contorller;

import com.github.shaylau.geekCourse.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShayLau
 * @date 2022/4/6 11:43
 */
@RestController
@RequestMapping("/message")
public class MessageController {


    private final String TOPIC_NAME = "mytopic";

    @Autowired
    private MessageService messageService;

    @RequestMapping("/sendMessage")
    public void sendMessage() {
        messageService.sendMessage(TOPIC_NAME, "hello world");
    }


}
