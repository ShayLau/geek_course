package com.github.shaylau.geekCourse.config;

import com.github.shaylau.geekCourse.config.properties.DbReadOnlyProperties;
import com.github.shaylau.geekCourse.config.properties.DbWriteProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置
 *
 * @author ShayLau
 * @date 2022/2/27 9:36 PM
 */
@Configuration
public class MultiDataSourceConfig {
    // 精确到 master 目录，以便跟其他数据源隔离
    public static final String MAPPER_LOCATION = "classpath:mapper/data/*Mapper.xml";
    public static final String CONFIG_LOCATION = "classpath:mapper/data/config.xml";


    @Autowired
    private DbReadOnlyProperties readOnlyProperties;
    @Autowired
    private DbWriteProperties writeProperties;


    /**
     * 只读数据源
     *
     * @return
     */
    @Bean("readOnlyDataSource")
    public DataSource readOnlyDataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create().driverClassName(readOnlyProperties.getDriverClassName()).url(readOnlyProperties.getUrl()).username(readOnlyProperties.getUsername()).password(readOnlyProperties.getPassword()).type(HikariDataSource.class).build();
        dataSource.setMaximumPoolSize(100);
        dataSource.setAutoCommit(true);
        return dataSource;
    }

    /**
     * 写数据源
     *
     * @return
     */
    @Bean("writeDataSource")
    public DataSource writeDataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create().driverClassName(writeProperties.getDriverClassName()).url(writeProperties.getUrl()).username(writeProperties.getUsername()).password(writeProperties.getPassword()).type(HikariDataSource.class).build();
        dataSource.setMaximumPoolSize(100);
        dataSource.setAutoCommit(true);
        return dataSource;
    }

    /**
     * 多数据源配置
     *
     * @param readOnlyDataSource
     * @param writeDataSource
     * @return
     */
    @Bean
    @Primary
    public DataSource multiRoutingDataSource(@Qualifier("readOnlyDataSource") DataSource readOnlyDataSource,
                                             @Qualifier("writeDataSource") DataSource writeDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("readOnly", readOnlyDataSource);
        targetDataSources.put("write", writeDataSource);

        DataSourceRouteConfig myRoutingDataSource = new DataSourceRouteConfig();
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }


    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("readOnlyDataSource") DataSource readOnlyDataSource,
                                               @Qualifier("writeDataSource") DataSource writeDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(multiRoutingDataSource(readOnlyDataSource, writeDataSource));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MultiDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(MultiDataSourceConfig.CONFIG_LOCATION));
        return sessionFactory.getObject();
    }
}
