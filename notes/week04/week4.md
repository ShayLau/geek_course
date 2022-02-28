# week4

## 学习资料

极客课程资料： Java并发编程实战（王宝令）

## Java 线程

### 守护线程介绍

守护线程在主线程执行结束之后，也会结束，即守护线程中的程序只有在主线程还在运行的时候，才会执行守护线程中的内容。

### 线程知识图谱

![Untitled](week4%20723a0/Untitled.png)

## 知识点

- jni 技术 java native interface
- 线程池使用 生产者-消费者的模型
- Lock实现了可见性，在内部有state变量控制
- wait 和 notify  需要和 synchronized一起使用
- `interrupt()中断操作，如果线程处于 wait()、sleep()状态 则可以使用 interrupt（）进行打断`
- `wait 和 sleep 的区别，wait 会释放对象锁、sleep 不会释放对象锁`
- 线程池原理
    - 线程池的核心线程数、线程总数、排队队列（BlockingQueue）、拒绝策略
    - 顶级接口：Executor执行服务：ExecutorService
    - 线程池实现：ThreadPoolExecutor
    - 线程池工具类： Executors

### 相关问题

- 查看Lock类的lock源码（LockSupport.park方法）
- cas（compare and sweep） 和串行化执行，cas能够比串行化更快嘛
- cpu的 cas指令
- 分段思想 LongAdder   segment
- dubbo中的rpc调度，怎么等待结果返回的