package com.github.shaylau.geekCourse.custom;


import java.util.ArrayList;

public final class MyQueue {

    private String topic;

    private int capacity;

    private int consumerPointOffset;

    private ArrayList<KmqMessage> array;


    public MyQueue(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.consumerPointOffset = 0;
        this.array = new ArrayList<>(capacity);
    }

    /**
     * 拉
     *
     * @return
     */
    public KmqMessage poll() {
        if (array.size() == 0) {
            return null;
        }
        if (consumerPointOffset == array.size()) {
//            array = new ArrayList<>(capacity);
//            consumerPointOffset = 0;
            return null;
        }
        return array.get(consumerPointOffset++);
    }

    /**
     * 推
     *
     * @return
     */
    public boolean push(KmqMessage message) {
//        if ((consumerPointOffset++) > capacity) {
//            return false;
//        }
        return array.add(message);
    }
}
