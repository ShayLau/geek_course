package com.github.shaylau.geekCourse.custom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * // Broker+Connection
 */
public final class KmqBroker {

    public static final int CAPACITY = 10000;

    private final Map<String, MyQueue> kmqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name) {
        kmqMap.putIfAbsent(name, new MyQueue(name, CAPACITY));
    }

    public MyQueue findKmq(String topic) {
        return this.kmqMap.get(topic);
    }

    public KmqProducer createProducer() {
        return new KmqProducer(this);
    }

    public KmqConsumer createConsumer() {
        return new KmqConsumer(this);
    }

}
