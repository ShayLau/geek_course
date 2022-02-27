package com.github.shaylau.geekCourse;

import java.io.*;
import java.sql.*;

/**
 * 使用原生JDBC的方式插入数据测试
 *
 * @author ShayLau
 * @date 2022/2/27 7:14 PM
 */
public class InsertTableByFileTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String user = "root";
        String password = "admin123";
        String mysqlDbUrl = "jdbc:mysql://localhost:3306";
        Connection connection = DriverManager.getConnection(mysqlDbUrl, user, password);
        File file = new File("/users/mini/Downloads/test.sql");
        if (!file.exists()) {
            throw new RuntimeException("没有对应的sql文件");
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String sql = "";
        while ((sql += bufferedReader.readLine()) != null) {
            System.out.println(sql);
        }
        System.out.println(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        boolean execute = preparedStatement.execute(sql);
        System.out.println(execute);

    }
}
