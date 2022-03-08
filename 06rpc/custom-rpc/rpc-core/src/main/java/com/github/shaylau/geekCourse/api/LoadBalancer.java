package com.github.shaylau.geekCourse.api;

import java.util.List;

public interface LoadBalancer {

    String select(List<String> urls);

}
