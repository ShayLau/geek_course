package com.github.shaylau.geekCourse.entity;

import lombok.Data;

/**
 * 订单信息
 *
 * @author ShayLau
 * @date 2022/2/28 17:57
 */
@Data
public class OrderInfo {

    /**
     * id
     */
    private String id;

    /**
     * gId
     */
    private String gId;

    /**
     * uid
     */
    private String uid;

    /**
     * status
     */
    private Integer status;

    /**
     * payStatus
     */
    private Integer payStatus;

    /**
     * money
     */
    private double money;

    /**
     * createTime
     */
    private Long createTime;

    /**
     * updateTime
     */
    private Long updateTime;
}
