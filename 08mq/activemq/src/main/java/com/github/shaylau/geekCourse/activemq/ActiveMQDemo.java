package com.github.shaylau.geekCourse.activemq;

import org.apache.activemq.broker.BrokerService;

/**
 * @author ShayLau
 * @date 2022/3/31 17:41
 */
public class ActiveMQDemo {
    public static void main(String[] args) throws Exception {
        // 尝试用java代码启动一个ActiveMQ broker server
        // 然后用前面的测试demo代码，连接这个嵌入式的server

        BrokerService broker = new BrokerService();
        broker.setUseJmx(true);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
        System.out.println("Broker Started!!!");
        // now lets wait forever to avoid the JVM terminating immediately
        Object lock = new Object();
        synchronized (lock) {
            lock.wait();
        }

    }

}
