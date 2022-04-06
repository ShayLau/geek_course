package com.github.shaylau.geekCourse.custom;

public class KmqConsumer<T> {

    private final KmqBroker broker;

    private MyQueue myQueue;

    public KmqConsumer(KmqBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.myQueue = this.broker.findKmq(topic);
        if (null == myQueue) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
    }

    public KmqMessage<T> poll() {
        return myQueue.poll();
    }

}
