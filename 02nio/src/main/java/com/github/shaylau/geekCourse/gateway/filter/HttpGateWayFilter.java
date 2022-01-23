package com.github.shaylau.geekCourse.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * http 网关路由
 */
public interface HttpGateWayFilter {


    void filter(FullHttpRequest fullHttpRequest);

}
