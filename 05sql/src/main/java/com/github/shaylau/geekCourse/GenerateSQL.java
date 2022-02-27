package com.github.shaylau.geekCourse;

import java.io.*;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 生成SQL
 *
 * @author ShayLau
 * @date 2022/2/27 6:27 PM
 */
public class GenerateSQL {

    public static final String TABLE_NAME = "order_info";

    public static final String INSERT_SQL = "insert into " + TABLE_NAME + " values ";

    public static final long numbers = 1000000;

    public static void main(String[] args) throws IOException {
        generateSQLToFile();
    }


    /**
     * 生成插入文件到SQL文件
     *
     * @throws IOException
     */
    public static void generateSQLToFile() throws IOException {
        File file = new File("/users/mini/Downloads/test.sql");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter outputStream = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStream);
        bufferedWriter.write(INSERT_SQL);
        for (int i = 1; i <= numbers; i++) {
            bufferedWriter.write(valuesSQL());
            if (i == numbers) {
                bufferedWriter.write(";");
            } else {
                bufferedWriter.write(",");
            }
            System.out.println("正在生成第" + i + "条数据！");
        }
        bufferedWriter.close();
        outputStream.close();
    }


    /**
     * sql  values语句
     *
     * @return
     */
    public static String valuesSQL() {
        Random random = new Random();
        String id = UUID.randomUUID().toString().replace("-", "");
        String goodsId = String.valueOf(random.nextInt(3));
        String userId = String.valueOf(random.nextInt(3));
        int payStatus = random.nextInt(3);
        int orderStatus = random.nextInt(2);
        double money = Math.random();
        long current = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer();
        buffer.append("(\'");
        buffer.append(id);
        buffer.append("\',");
        buffer.append(goodsId);
        buffer.append(",");
        buffer.append(userId);
        buffer.append(",");
        buffer.append(orderStatus);
        buffer.append(",");
        buffer.append(payStatus);
        buffer.append(",");
        buffer.append(money);
        buffer.append(",");
        buffer.append(current);
        buffer.append(",");
        buffer.append(current);
        buffer.append(")");
        return buffer.toString();
    }

    /**
     * sql  values语句
     *
     * @return
     */
    public static Object[] values() {
        Object[] obj = new Object[8];
        Random random = new Random();
        String id = UUID.randomUUID().toString().replace("-", "");
        obj[0] = id;
        String goodsId = String.valueOf(random.nextInt(3));
        obj[1] = goodsId;
        String userId = String.valueOf(random.nextInt(3));
        obj[2] = userId;
        int payStatus = random.nextInt(3);
        obj[3] = payStatus;
        int orderStatus = random.nextInt(2);
        obj[4] = orderStatus;
        double money = Math.random();
        obj[5] = money;
        long current = System.currentTimeMillis();
        obj[6] = current;
        obj[7] = current;
        return obj;
    }
}