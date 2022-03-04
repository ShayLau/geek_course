package com.github.shaylau.geekCourse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author ShayLau
 * @date 2022/3/3 15:44
 */
public class ShardingProxyTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int dataNumber = 1000000;
        int batchNumber = dataNumber / 10;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String user = "root";
        String password = "root";
        String mysqlDbUrl = "jdbc:mysql://localhost:3307/sharding_db";
        Connection connection = DriverManager.getConnection(mysqlDbUrl, user, password);
        String sql = "insert into order_info ( g_id,u_id,status,pay_status,money,create_time,update_time) values (?,?,?,?,?,?,?)";
        System.out.println(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        LocalDateTime start = LocalDateTime.now();
        int count = 1;
        for (int i = 1; i <= dataNumber; i++) {
            Object[] obj = values();
            preparedStatement.setObject(1, obj[0]);
            preparedStatement.setObject(2, obj[1]);
            preparedStatement.setObject(3, obj[2]);
            preparedStatement.setObject(4, obj[3]);
            preparedStatement.setObject(5, obj[4]);
            preparedStatement.setObject(6, obj[5]);
            preparedStatement.setObject(7, obj[6]);
            preparedStatement.addBatch();
            System.out.println("添加批次数据" + i + ";");
            if (i % batchNumber == 0) {
                preparedStatement.executeBatch();
                System.out.println("执行批次数据:" + (count - 1) * batchNumber + "~" + count * batchNumber + "插入！");
                count++;
            }
        }
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println(duration);
    }

    /**
     * sql  values语句
     *
     * @return
     */
    public static Object[] values() {
        Object[] obj = new Object[8];
        Random random = new Random();
        String goodsId = String.valueOf(random.nextInt(3));
        obj[0] = goodsId;
        obj[1] = random.nextInt(10);
        int payStatus = random.nextInt(3);
        obj[2] = payStatus;
        int orderStatus = random.nextInt(2);
        obj[3] = orderStatus;
        double money = Math.random();
        obj[4] = money;
        long current = System.currentTimeMillis();
        obj[5] = current;
        obj[6] = current;
        return obj;
    }
}
