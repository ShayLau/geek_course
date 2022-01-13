package com.github.shaylau.geekCourse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ShayLau
 * @date 2022/1/13 3:41 PM
 */
public class HttpExample2 {
    public static void main(String[] args) throws IOException {
        int httpPort = 8082;
        ServerSocket serverSocket = new ServerSocket(httpPort);
        while (true) {
            final Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    HttpNetWorkRequestHandle.handle(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
