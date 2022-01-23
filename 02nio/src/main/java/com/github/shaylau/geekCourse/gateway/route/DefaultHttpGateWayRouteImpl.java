package com.github.shaylau.geekCourse.gateway.route;

import com.github.shaylau.geekCourse.gateway.registor.HttpServiceCenter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认的服务路由实现
 */
public class DefaultHttpGateWayRouteImpl implements HttpGateWayRoute {

    //路由基础
    private AtomicInteger routeBase = new AtomicInteger(0);

    private final int roundRobinLimit = 1000;

    @Override
    public String route(String uri) {
        String separator = "/";
        String str1 = uri.substring(uri.indexOf(separator) + 1);
        String serviceName = str1.substring(str1.indexOf(separator) + 1);
        List<String> serviceAddress = HttpServiceCenter.getServiceCenterAddress(serviceName);
        if (serviceAddress.isEmpty()) {
            return "no service";
        } else {
            int serviceSize = serviceAddress.size();
            int round = routeBase.getAndIncrement();
            if (round > roundRobinLimit) {
                routeBase.set(0);
            }
            return serviceSize == 1 ? serviceAddress.get(0) : serviceAddress.get(routeBase.get() / serviceSize);
        }
    }
}
