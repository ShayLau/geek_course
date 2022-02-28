package com.github.shaylau.geekCourse.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

/**
 * 数据链接路由链接配置
 *
 * @author ShayLau
 * @date 2022/2/27 9:08 PM
 */
public class DataSourceRouteConfig extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DbSelectorHolder.getSelect();
    }
}
