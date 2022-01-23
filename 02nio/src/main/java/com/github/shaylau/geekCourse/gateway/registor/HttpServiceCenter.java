package com.github.shaylau.geekCourse.gateway.registor;

import java.util.*;

/**
 * Http 服务中心
 * 使用Map 模拟注册中心地址
 */
public class HttpServiceCenter {
    private static Map<String, List<String>> serviceAddressMap = new HashMap<>();

    static {
        List<String> demoService = new ArrayList<>();
        demoService.add("http://localhost:8081");
        demoService.add("http://localhost:8082");
        serviceAddressMap.put("demo", demoService);
    }

    /**
     * 根据服务名获取服务地址
     *
     * @param serviceName 服务名称
     * @return 服务列表
     */
    public static List<String> getServiceCenterAddress(String serviceName) {
        return serviceAddressMap.getOrDefault(serviceName, Collections.emptyList());
    }
}
