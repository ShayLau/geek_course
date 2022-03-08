package com.github.shaylau.geekCourse;

import com.github.shaylau.geekCourse.api.ServiceProviderDesc;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

@SpringBootApplication
public class RpcfxServerApplication {

    public static void main(String[] args) throws Exception {

//		// start zk client
//		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").namespace("rpcfx").retryPolicy(retryPolicy).build();
//		client.start();
//
//
//		// register service
//		// xxx "io.kimmking.rpcfx.demo.api.UserService"
//
//		String userService = "io.kimking.rpcfx.demo.api.UserService";
//		registerService(client, userService);
//		String orderService = "io.kimking.rpcfx.demo.api.OrderService";
//		registerService(client, orderService);


        // 进一步的优化，是在spring加载完成后，从里面拿到特定注解的bean，自动注册到zk

        SpringApplication.run(RpcfxServerApplication.class, args);
    }

    private static void registerService(CuratorFramework client, String service) throws Exception {
        ServiceProviderDesc userServiceSesc = ServiceProviderDesc.builder().host(InetAddress.getLocalHost().getHostAddress()).port(8080).serviceClass(service).build();
        // String userServiceSescJson = JSON.toJSONString(userServiceSesc);

        try {
            if (null == client.checkExists().forPath("/" + service)) {
                client.create().withMode(CreateMode.PERSISTENT).forPath("/" + service, "service".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        client.create().withMode(CreateMode.EPHEMERAL).forPath("/" + service + "/" + userServiceSesc.getHost() + "_" + userServiceSesc.getPort(), "provider".getBytes());
    }


}
