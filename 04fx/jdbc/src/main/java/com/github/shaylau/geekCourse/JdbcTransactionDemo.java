package com.github.shaylau.geekCourse;

import java.sql.*;

/**
 * @author ShayLau
 * @date 2022/2/13 4:46 PM
 */
public class JdbcTransactionDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //加载Mysql驱动 (静态加载 static： java.sql.DriverManager.registerDriver(new Driver());)
        Class.forName("com.mysql.cj.jdbc.Driver");
        String user = "root";
        String password = "admin123";
        String mysqlDbUrl = "jdbc:mysql://localhost:3306/demo";
        Connection connection = DriverManager.getConnection(mysqlDbUrl, user, password);

        String sql = "insert into user(user_id,user_name) values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, "jack");

        if (preparedStatement.executeUpdate(sql)>0) {
            connection.rollback();
        }

        preparedStatement.close();
        connection.close();

    }
}
