package com.github.shaylau.geekCourse.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ShayLau
 * @date 2022/3/11 17:17
 */
@Configuration
public class DataSourceConfig {


    @Bean("tccDataSource1")
    public DataSource tccDataSource1() {
        HikariDataSource dataSource = DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://localhost:3306/tccdb1").username("root").password("admin123").type(HikariDataSource.class).build();
        dataSource.setMaximumPoolSize(100);
        dataSource.setAutoCommit(true);
        return dataSource;
    }

    @Bean("tccDataSource2")
    public DataSource tccDataSource2() {
        HikariDataSource dataSource = DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://localhost:3306/tccdb2").username("root").password("admin123").type(HikariDataSource.class).build();
        dataSource.setMaximumPoolSize(100);
        dataSource.setAutoCommit(true);
        return dataSource;
    }
}
