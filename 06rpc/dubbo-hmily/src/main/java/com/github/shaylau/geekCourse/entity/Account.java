package com.github.shaylau.geekCourse.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ShayLau
 * @date 2022/3/11 11:45
 */
@Data
public class Account {


    private String id;

    private String userId;

    private double cnyBalance;

    private double dollarBalance;

    private Date createTime;

    private Date updateTime;
}
