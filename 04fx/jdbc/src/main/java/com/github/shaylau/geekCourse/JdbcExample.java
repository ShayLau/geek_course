package com.github.shaylau.geekCourse;



import java.sql.*;

/**
 * 原生JDBC实现
 *
 * @author ShayLau
 * @date 2022/2/13 3:45 PM
 */
public class JdbcExample {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //加载Mysql驱动 (静态加载 static： java.sql.DriverManager.registerDriver(new Driver());)
        Class.forName("com.mysql.cj.jdbc.Driver");
        String user = "root";
        String password = "admin123";
        String mysqlDbUrl = "jdbc:mysql://localhost:3306/demo";
        Connection connection = DriverManager.getConnection(mysqlDbUrl, user, password);

        //执行SQL

        //第一种方式
        String sql = "select  * from user where user_id=1";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        ResultSet resultSet1 = statement.getResultSet();
        printResultSet(resultSet1);


        //第二种方式
        String sql2 = "select  * from user where user_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql2);
        preparedStatement.setObject(1,2);
        ResultSet resultSet2 = preparedStatement.executeQuery();
        printResultSet(resultSet2);

        //关闭statement和Connection
        statement.close();
        preparedStatement.close();
        connection.close();
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int userId = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            System.out.println(userId + ":" + userName);
        }
    }
}
