package com.github.shaylau.geekCourse.custom;

public class KmqProducer {

    private KmqBroker broker;

    public KmqProducer(KmqBroker broker) {
        this.broker = broker;
    }

    public boolean send(String topic, KmqMessage message) {
        MyQueue myQueue = this.broker.findKmq(topic);
        if (null == myQueue) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        return myQueue.push(message);
    }
}
