week1
极客课程资料：深入拆解 Java 虚拟机（郑雨迪 ）

生成字节码文件
javac  -g HelloByteCode.java

查看字节码文件
javap -v -p HelloByteCode






week2
极客课程资料：深入拆解 Java 虚拟机（郑雨迪 ）

使用串行化GC
java -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:SerialGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis

使用Java8 并行GC
java -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:ParallelGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis

使用G1Gc(jdk9及以上)  garbage first 
java   -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:G1GC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis

使用CMSGC(Concurrent mark sweep) 并发标记清除 jvm heap 按照一定大小分为多个Region 

java -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:CMSGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis


week3
极客课程资料： Netty 编程实战（傅健）

interrupt()中断操作，如果线程处于 wait()、sleep()状态 则可以使用 interrupt（）进行打断

wait 和 sleep 的区别，wait 会释放对象锁、sleep 不会释放对象锁


线程池原理，线程池的核心线程数、线程总数、排队队列（BlockingQueue）、拒绝策略

顶级接口：Executor

执行服务：ExecutorService


线程池实现：ThreadPoolExecutor

线程池工具类： Executors




week4
极客课程资料： Java并发编程实战（王宝令）


jni 技术 java native interface

线程池使用 生产者-消费者的模型



week5 
Spring介绍  
Spring bean 的加载
1.xml方式配置
2.@Bean注解方式
3.自动配置方式
Spring starter 的使用方式
1.@Confuration的使用
2.META-INF/spring.factories的使用
@SpringBootApplication 中的@EnableAutoConfiguration
Spring的事务

原生JDBC
JPA
mybaties
Hibernate 
数据库的连接池的使用
1.common pool
2.c3p0
3.druid
4.Hikari   暂未完成需要补充

