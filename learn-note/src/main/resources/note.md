week1

生成字节码文件
javac  -g HelloByteCode.java

查看字节码文件
javap -v -p HelloByteCode






week2

使用串行化GC
java -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:SerialGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis

使用Java8 并行GC
java -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:ParallelGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis

使用G1Gc(jdk9及以上)  garbage first 
java   -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:G1GC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis

使用CMSGC(Concurrent mark sweep) 并发标记清除 jvm heap 按照一定大小分为多个Region 

java -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:CMSGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis


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

week6

MySQL章节
（必做）基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）。

（选做）尽可能多的从“常见关系数据库”中列的清单，安装运行，并使用上一题的 SQL 测试简单的增删改查。

（选做）基于上一题，尝试对各个数据库测试 100 万订单数据的增删改查性能。

（选做）尝试对 MySQL 不同引擎下测试 100 万订单数据的增删改查性能。

（选做）模拟 1000 万订单数据，测试不同方式下导入导出（数据备份还原）MySQL 的速度，包括 jdbc 程序处理和命令行处理。思考和实践，如何提升处理效率。

（选做）对 MySQL 配置不同的数据库连接池（DBCP、C3P0、Druid、Hikari），测试增删改查 100 万次，对比性能，生成报告。

