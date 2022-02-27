package com.github.shaylau.geekCourse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 使用原生JDBC的方式插入数据测试
 *
 * @author ShayLau
 * @date 2022/2/27 7:14 PM
 */
public class InsertTableTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String user = "root";
        String password = "admin123";
        String mysqlDbUrl = "jdbc:mysql://localhost:3306/shop";
        Connection connection = DriverManager.getConnection(mysqlDbUrl, user, password);
        String sql = GenerateSQL.INSERT_SQL + "(?,?,?,?,?,?,?,?)";
        System.out.println(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        LocalDateTime start = LocalDateTime.now();
        for (int i = 1; i <= 1000000; i++) {
            Object[] obj = GenerateSQL.values();
            preparedStatement.setObject(1, obj[0]);
            preparedStatement.setObject(2, obj[1]);
            preparedStatement.setObject(3, obj[2]);
            preparedStatement.setObject(4, obj[3]);
            preparedStatement.setObject(5, obj[4]);
            preparedStatement.setObject(6, obj[5]);
            preparedStatement.setObject(7, obj[6]);
            preparedStatement.setObject(8, obj[7]);
            preparedStatement.addBatch();
            System.out.println("添加批次数据" + i + ";");
        }
        preparedStatement.executeBatch();
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println(duration);
    }
}
