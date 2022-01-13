package com.github.shaylau.geekCourse;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author ShayLau
 * @date 2022/1/13 3:41 PM
 */
public class HttpExample1 {

    public static void main(String[] args) throws IOException {
        int httpPort = 8081;
        ServerSocket serverSocket = new ServerSocket(httpPort);
        while (true) {
            HttpNetWorkRequestHandle.handle(serverSocket.accept());
        }
    }
}
