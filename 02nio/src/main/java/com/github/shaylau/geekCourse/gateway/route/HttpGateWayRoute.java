package com.github.shaylau.geekCourse.gateway.route;

public interface HttpGateWayRoute {

    /**
     * 根据URi 获取真实的服务地址
     *
     * @param uri
     * @return
     */
    String route(String uri);
}
