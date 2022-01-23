package com.github.shaylau.geekCourse.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

public class DefaultHttpGateWayFilterImpl implements HttpGateWayFilter {

    @Override
    public void filter(FullHttpRequest fullHttpRequest) {
        HttpMethod httpMethod = fullHttpRequest.method();
        if (httpMethod.equals(HttpMethod.GET)) {
            System.out.println("get请求过滤！");
        }
    }
}
