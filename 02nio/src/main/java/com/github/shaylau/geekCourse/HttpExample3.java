package com.github.shaylau.geekCourse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ShayLau
 * @date 2022/1/13 3:41 PM
 */
public class HttpExample3 {
    public static void main(String[] args) throws IOException {
        int httpPort = 8083;
        ServerSocket serverSocket = new ServerSocket(httpPort);
        ExecutorService threadPoolExecutor = Executors.newScheduledThreadPool(5);
        while (true) {
            final Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> {
                try {
                    HttpNetWorkRequestHandle.handle(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
