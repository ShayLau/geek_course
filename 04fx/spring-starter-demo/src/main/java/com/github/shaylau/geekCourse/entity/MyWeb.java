package com.github.shaylau.geekCourse.entity;

import com.github.shaylau.geekCourse.prop.MyWebConfigurationProp;

/**
 * @author ShayLau
 * @date 2022/2/13 3:25 PM
 */
public class MyWeb {
    public MyWebConfigurationProp prop;

    public void printWebInfo() {
        System.out.println("my web:" + prop.getWebName() + ",web address:" + prop.getIpaddress() + ",port:" + prop.getPort());
    }
}
