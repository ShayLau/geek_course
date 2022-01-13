package com.github.shaylau.geekCourse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author ShayLau
 * @date 2022/1/13 4:25 PM
 */
public class HttpNetWorkRequestHandle {


    /**
     * 处理 Socket网络请求
     * <description>
     * 将Http 协议的文本信息
     * </description>
     *
     * @param socket
     */
    public static void handle(Socket socket) throws IOException {
        //自动刷新流
        boolean autoRefresh = true;
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), autoRefresh);
        printWriter.println("HTTP/1.1 200 OK");
//        printWriter.println("Content-Type:text/html ;charset=utf-8");
        String htmlBody = "Hello World";
        printWriter.println("Content-length:" + htmlBody.getBytes().length);
        printWriter.println();
        printWriter.write(htmlBody);
        printWriter.close();
        socket.close();
    }

}
