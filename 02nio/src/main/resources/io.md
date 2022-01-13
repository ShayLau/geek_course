## 同步异步与阻塞非阻塞

同步异步是通信方式
阻塞、非阻塞是线程处理模式

## 同步与异步

- 同步阻塞  BIO
- 同步非阻塞 NIO
- IO 多路复用 io multi plexing
    - fd(File description)文件描述符
    - select多路复用模型
    - poll多路复用模型
    - epoll 多路复用模型
- 信号驱动IO：用户空间等待 IO数据（网络 IO、磁盘 IO）,内核空间在准备好IO数据后，内核空间发送信号（Sign）给用户空间，用户空间收到信号后，用户空间revefrom查IO数据
- 异步 IO:内核等待数据准备完成，然后将数据拷贝到用户进程缓冲区，然后发送信号告诉用户进程 IO 操作执行完毕（与 SIGIO 相比，一个是发送信号告诉用户进程数据准备完毕，一个是 IO执行完毕）



![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/af4566cf-76e7-4fbd-aeec-dbaed2437379/Untitled.png)