package com.github.shaylau.geekCourse.controller;

import com.github.shaylau.geekCourse.api.RpcfxRequest;
import com.github.shaylau.geekCourse.api.RpcfxResponse;
import com.github.shaylau.geekCourse.server.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调用控制器
 *
 * @author ShayLau
 * @date 2022/3/8 14:10
 */
@RestController
public class InvokingController {

    @Autowired
    RpcfxInvoker invoker;

    @PostMapping("/")
    public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
        return invoker.invoke(request);
    }

}
