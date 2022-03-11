package com.github.shaylau.geekCourse.service.impl;

import com.github.shaylau.geekCourse.service.IAccountService;
import lombok.SneakyThrows;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author ShayLau
 * @date 2022/3/11 11:45
 */
@Service
public class AccountService implements IAccountService {


    @Qualifier(value = "tccDataSource1")
    public DataSource dataSource1;


    @Qualifier(value = "tccDataSource2")
    public DataSource dataSource2;


    @SneakyThrows
    @HmilyTCC(confirmMethod = "initConfirm", cancelMethod = "initCancel")
    @Override
    public void initAmountData() {
        Connection ds1c = dataSource1.getConnection();
        Connection ds2c = dataSource2.getConnection();




    }

    @SneakyThrows
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Override
    public void transferAmount() {
        Connection ds1c = dataSource1.getConnection();
        Connection ds2c = dataSource2.getConnection();
        String sql = "";


    }

    @Transactional(rollbackFor = Exception.class)
    public void initConfirm() {

    }


    @Transactional(rollbackFor = Exception.class)
    public void initCancel() {


    }

    @Transactional(rollbackFor = Exception.class)
    public void confirm() {

    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel() {

    }

}
