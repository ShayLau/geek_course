package com.github.shaylau.geekCourse.service.impl;

import com.github.shaylau.geekCourse.service.IAccountService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ShayLau
 * @date 2022/3/11 11:45
 */
@Slf4j
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

        String ds1AccountSql = " insert into account(user_id, cny_balance, dollar_balance, create_time, update_time)  values (1, 100,200,curdate(),curdate())";

        String ds1FreezeAccountSql = " insert into freeze_amount(user_id, freeze_cny_amount, freeze_dollar_amount, create_time, update_time)  values (1, 0,0,curdate(),curdate())";

        String ds2AccountSql = " insert into account(user_id, cny_balance, dollar_balance, create_time, update_time)  values (2, 50,1,curdate(),curdate())";

        String ds2FreezeAccountSql = "insert into freeze_amount(user_id, freeze_cny_amount, freeze_dollar_amount, create_time, update_time)  values (2, 0,0,curdate(),curdate());";


        PreparedStatement ps1 = ds1c.prepareStatement(ds1AccountSql);
        PreparedStatement ps2 = ds2c.prepareStatement(ds2AccountSql);

        boolean result1 = ps1.execute() && ps1.execute(ds1FreezeAccountSql);
        boolean result2 = ps2.execute() && ps2.execute(ds2FreezeAccountSql);

        log.info("init account and freezeAccount result:{}", result1 & result2);

        if (!(result1 & result2)) {
            throw new HmilyRuntimeException("初始化数据异常！");
        }

    }

    @SneakyThrows
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Override
    public void transferAmount(double money) {
        Connection ds1c = dataSource1.getConnection();
        Connection ds2c = dataSource2.getConnection();

        //A->B 转money
        String transferSqlA = "update  account set cny_balance=cny_balance-" + money + ",update_time=curdate() " + "where user_id=1 and (cny_balance-" + money + ",)>0";
        String transferSqlB = "update  account set cny_balance=cny_balance-" + money + ",update_time=curdate() " + "where user_id=2";
        String freezeAccountASql = "update freeze_amount set freeze_cny_amount = freeze_cny_amount + " + money + ", " + "update_time = curdate() where user_id = 1";

        PreparedStatement ps1 = ds1c.prepareStatement(transferSqlA);

        boolean result = ps1.execute();
        if (!result) {
            throw new HmilyRuntimeException("账户扣减异常！");
        } else {
            result &= ps1.execute(freezeAccountASql);
            PreparedStatement ps2 = ds2c.prepareStatement(transferSqlB);
            result &= ps2.execute();
            if (!result) {
                throw new HmilyRuntimeException("转账失败！");
            }
        }


    }


    @Transactional(rollbackFor = Exception.class)
    public void initConfirm() {
        log.info("init account and freezeAccount result:true");

    }


    @Transactional(rollbackFor = Exception.class)
    public void initCancel() throws SQLException {
        Connection ds1c = dataSource1.getConnection();
        Connection ds2c = dataSource2.getConnection();
        String deleteAccountSql = "delete table freeze_amount";
        String deleteFreezeAccountSql = "delete table account";

        PreparedStatement ps1 = ds1c.prepareStatement(deleteAccountSql);
        ps1.execute(deleteFreezeAccountSql);
        PreparedStatement ps2 = ds2c.prepareStatement(deleteAccountSql);
        ps2.execute(deleteFreezeAccountSql);
        log.info("init account and freezeAccount result:false");
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirm() {
        log.info("转账成功");

    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(double money) {

        ///根据转账金额，查看A 的冻结金额，决定是否回退冻结金额和记录错误转账记录
        ///
        ///

    }

}
