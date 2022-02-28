# week7

## 学习资料

- ****MySQL 实战 45 讲(林晓斌)****
- 高性能 MYSQL(书籍)
- MySQL是怎样运行的：从根儿上理解MySQL(书籍)

## innodb engine 数据库锁

### 测试锁流程

首先需要将数据库事务自动提交改为非自动提交（set autocommit=0），然后进行锁测试

### 锁的说明

- 行锁-排它锁：数据的update 修改操作，会在行上加上排它锁（X锁），防止数据被多个数据库连接同时修改的并发问题,排它锁属于悲观锁。
- 行锁-间隙锁：对一段范围数据内的数据进行加锁，如 where id>1
- 数据共享锁：对行数据并发读取的锁

### 部分查询数据库的 SQL

```sql
show  create table  table_name \G

//查看db engine
show engine innodb status\G

// 事务的级别
set  autocommit=0;
```

### 行锁

```sql
mysql> select * from demo;
+----+
| id |
+----+
|  1 |
|  2 |
|  5 |
|  6 |
|  7 |
+----+
5 rows in set (0.00 sec)

mysql> update  demo set id=8 where id=5 ;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> select * from performance_schema.data_locks\G
*************************** 1. row ***************************
               ENGINE: INNODB
       ENGINE_LOCK_ID: 140610542541960:1074:140610504777216
ENGINE_TRANSACTION_ID: 2861
            THREAD_ID: 49
             EVENT_ID: 79
        OBJECT_SCHEMA: example
          OBJECT_NAME: demo
       PARTITION_NAME: NULL
    SUBPARTITION_NAME: NULL
           INDEX_NAME: NULL
OBJECT_INSTANCE_BEGIN: 140610504777216
            LOCK_TYPE: TABLE
            LOCK_MODE: IX
          LOCK_STATUS: GRANTED
            LOCK_DATA: NULL
*************************** 2. row ***************************
               ENGINE: INNODB
       ENGINE_LOCK_ID: 140610542541960:13:4:6:140610803815456
ENGINE_TRANSACTION_ID: 2861
            THREAD_ID: 49
             EVENT_ID: 79
        OBJECT_SCHEMA: example
          OBJECT_NAME: demo
       PARTITION_NAME: NULL
    SUBPARTITION_NAME: NULL
           INDEX_NAME: PRIMARY
OBJECT_INSTANCE_BEGIN: 140610803815456
            LOCK_TYPE: RECORD
            LOCK_MODE: X,REC_NOT_GAP
          LOCK_STATUS: GRANTED
            LOCK_DATA: 5
2 rows in set (0.00 sec)

mysql> commit;
Query OK, 0 rows affected (0.01 sec)

mysql> select * from performance_schema.data_locks\G
Empty set (0.00 sec)

```

### 间隙锁(Gap 锁)

```sql
//设置数据的事物隔离界别  

mysql> set autocommit=0;
Query OK, 0 rows affected (0.00 sec)

mysql> select *  from demo  where id>1 and id<5 for update;
+----+
| id |
+----+
|  2 |
+----+
1 row in set (0.00 sec)

//查询数据库 锁表信息

mysql> select * from performance_schema.data_locks\G
*************************** 1. row ***************************
               ENGINE: INNODB
       ENGINE_LOCK_ID: 140610542542752:1074:140610504778240
ENGINE_TRANSACTION_ID: 2860
            THREAD_ID: 56
             EVENT_ID: 33
        OBJECT_SCHEMA: example
          OBJECT_NAME: demo
       PARTITION_NAME: NULL
    SUBPARTITION_NAME: NULL
           INDEX_NAME: NULL
OBJECT_INSTANCE_BEGIN: 140610504778240
            LOCK_TYPE: TABLE
            LOCK_MODE: IX
          LOCK_STATUS: GRANTED
            LOCK_DATA: NULL
*************************** 2. row ***************************
               ENGINE: INNODB
       ENGINE_LOCK_ID: 140610542542752:13:4:3:140610803820064
ENGINE_TRANSACTION_ID: 2860
            THREAD_ID: 56
             EVENT_ID: 33
        OBJECT_SCHEMA: example
          OBJECT_NAME: demo
       PARTITION_NAME: NULL
    SUBPARTITION_NAME: NULL
           INDEX_NAME: PRIMARY
OBJECT_INSTANCE_BEGIN: 140610803820064
            LOCK_TYPE: RECORD
            LOCK_MODE: X
          LOCK_STATUS: GRANTED
            LOCK_DATA: 2
*************************** 3. row ***************************
               ENGINE: INNODB
       ENGINE_LOCK_ID: 140610542542752:13:4:6:140610803820408
ENGINE_TRANSACTION_ID: 2860
            THREAD_ID: 56
             EVENT_ID: 33
        OBJECT_SCHEMA: example
          OBJECT_NAME: demo
       PARTITION_NAME: NULL
    SUBPARTITION_NAME: NULL
           INDEX_NAME: PRIMARY
OBJECT_INSTANCE_BEGIN: 140610803820408
            LOCK_TYPE: RECORD
            LOCK_MODE: X,GAP
          LOCK_STATUS: GRANTED
            LOCK_DATA: 5
3 rows in set (0.00 sec)

mysql>
```

## 慢查询

```sql

mysql> show variables like '%slow%';
+-----------------------------+-------------------------------------------------+
| Variable_name               | Value                                           |
+-----------------------------+-------------------------------------------------+
| log_slow_admin_statements   | OFF                                             |
| log_slow_extra              | OFF                                             |
| log_slow_replica_statements | OFF                                             |
| log_slow_slave_statements   | OFF                                             |
| slow_launch_time            | 2                                               |
| slow_query_log              | OFF                                             |
| slow_query_log_file         | /usr/local/mysql/data/macdeMacBook-Pro-slow.log |
+-----------------------------+-------------------------------------------------+
7 rows in set (0.00 sec)

mysql> set slow_query_log=1;
ERROR 1229 (HY000): Variable 'slow_query_log' is a GLOBAL variable and should be set with SET GLOBAL
mysql> set global slow_query_log=1;
Query OK, 0 rows affected (0.01 sec)

mysql>
```

## DB的优化

- 隐式转换
- SQL性能查询慢查询 slowlog 、监控系统（orzdba）
- 加索引，索引类型   Hash 类型 和 B+树
    - Hash  ：将 key 做 hash 分不到 hash 桶
    - B树：按照磁盘块存储数据
    - B+树：数据全放在叶子节点，树节点放 key，不需要回溯树节点
- 为什么使用 id 自增，防止页分裂
- 主键索引和非主键索引的性能，非主键索引指向的是主键索引，会进行一次回表操作（找到列的主键索引）
- 索引的选择，最左原则，使用 distanct（ column）/count 的比率

### 相关性能问题

- 修改表会造成性能的问题：索引重建、锁表、抢占资源、主从时延
- 数据量到达一定阶段相关会造成性能的问题：该字段类型、建索引、加字段

### 相关优化

- 批量插入的优化
    - 使用 prepareStatement 预声明的方式
    - 批量操作（insert into table values(),(),()）
    - 原生 load data，使用 csv 格式
    - 先删除索引，导完数据，在重建索引
- 数据更新
    - 少用范围操作，防止产生 GAP 间隔锁问题
- 模糊查询
    - like 使用前缀匹配 ，如  name like ’李%‘，防止不走索引
    - MYSQL 的全文检索倒排索引
    - 使用第三方引擎 ES、**solr**
- 连接查询
    - 使用小表连接
    - 避免大表连接，产生笛卡尔积，如 A表 B表 百万级表，生成100W*100W 结果集问题
- 索引失效问题
    - like、not 、notin、函数操作
    - 减少使用 or
    - 强制走索引  force index
- 查询优化
    - 能在数据库进行的操作，在数据库进行操作，如 100w 数据的 sum（）、avg()
    - 避免使用临时表
    - 分析类报表需求，每天在晚上执行批次计算任务，然后需要的时候进行数据统计
    
    ### union 和 union  all的区别
    
    union  会进行去重，而 union all 不去重
    

## 思考题

- 为什么不使用 hash 结构做index索引？
- 为什么b+tree更适合做索引？ b+数
- 为什么主键长度不能过大？ 主键长度过大，索引的数据文件也会变大

```sql
//部分数据正序部分数据倒序
order by if (id<5, -id,id);
```

## id主键的生成

- 自增主键，缺点：数据量在一点范围不够使用，不支持分布式情况，暴露业务情况
- uuid，缺点：
- 时间戳，缺点：
- 时间戳+随机数，缺点：
- 雪花算法(机器标识码+时间戳+随机数)

## 分页的使用

- 

# 数据库的分库分表

- 读写压力
    - 多机集群
    - 主从复制
- 高可用性
    - 故障转义
    - 主从切换
- 容量问题
    - 数据库拆分
    - 分库分表
- 一致性问题
    - 分布式事务
    - XA/柔性事务

### 主从复制原理

1. 主机：开启binlog日志，产生binlog
2. 从机：拉取主机binlog日志生成relaylog，然后sql线程执行操作处理binlog日志

## binlog格式

- row,十分精确
- statement,
- mixed,混合模式（row+statment）

### 相关binlog命令及演示

```bash
//查看binlog日志
cd /usr/local/mysql/data

mysqlbinlog -vv binlog.000001
```

### 主从复制的方式

- 异步复制，主库不知道从库执行的结果
- 半同步复制，从库在执行relaylog后发送ack确认信息，超时就不会等待从库没有及时返回ack信息
- 组复制，涉及到多个主节点同事同步到从节点的冲突问题，需要学习？？？

## 配置MYSQL主从

```sql
//禁用binlog
SQL_LOG_BIN=0;
//启动binlog
SQL_LOG_BIN=1;
```

# 作业部分

## 生成测试数据部分

### 使用datafaker 记录（未完成）

datafaker 是使用python编写的，命令的执行的格式有点麻烦，该软件的问题是 在mac环境下，执行的时候会报错，时间原因没有继续测试如下,

```bash
datafaker file /home out.txt 10 --meta meta.txt --format json

datafaker  mysql file  /Users/mini/Downloads/datafaker/tmp.txt  mysql  order_info 1  --meta meta.txt

datafaker file /Users/mini/Downloads/datafaker/tmp.txt order_info 1

datafaker file /Users/mini/Downloads/datafaker/ out.txt 10 --meta meta.txt --format json

The "freeze_support()" line can be omitted if the program is not going to be frozen to produce an executable
```

```bash
id||varchar(50)||id[:inc(id,1)]
g_id||varchar(50)||商品id[:enum(1,2)]
u_id||varchar(50)||用户id[:enum(1,2)]
status||int||订单状态  1.已取消 2.已完成[:enum(1, 2)]
money||int||金额 long(10, 0, 1)
pay_status||int||1.未支付 2.支付中 3.已支付 [:enum(1, 2, 3)]
create_time||decimal(4,2)||创建时间[:datetime]
update_time||bigint||修改时间[:datetime]
```

## 数据库插入性能测试

1. 使用java程序编写100w条数据，`插入100w条数据的时间大约在 1分钟55秒左右`
    
    ![Untitled](week7%20c2fd9/Untitled.png)
    
2. 使用loaddata的方式踩坑
- secure_file_priv 的配置，需要在mysql的文件配置文件可允许的导入导出路径，否则无法使用loaddata加载数据
- macos下，执行的导入文件需要有对应的读写权限，否则会出现（(OS errno 13 - Permission denied)），需要在mysql的配置文件中配置路径，不能global中配置
- macos下执行依然报错，查看搜索引擎，问题出现在mysql的文件连接上，改动后仍无效，需要询问助教老师
- load data 官方文档：[https://dev.mysql.com/doc/refman/8.0/en/load-data.html](https://dev.mysql.com/doc/refman/8.0/en/load-data.html)

```bash
[mysqld]
secure_file_priv=/Users/mini/Downloads/
```

执行如下：

```bash
mini@minideMac-mini ~ % mysql -u root -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 61
Server version: 8.0.28 MySQL Community Server - GPL

Copyright (c) 2000, 2022, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> LOAD DATA INFILE '/Users/mini/Downloads/test.sql' INTO TABLE shop.order_info;
ERROR 1290 (HY000): The MySQL server is running with the --secure-file-priv option so it cannot execute this statement
mysql> show variables like '%-secure-file-priv option%';
Empty set (0.01 sec)

mysql> show variables like '%secure%';
+--------------------------+-------+
| Variable_name            | Value |
+--------------------------+-------+
| require_secure_transport | OFF   |
| secure_file_priv         | NULL  |
+--------------------------+-------+
2 rows in set (0.01 sec)

mysql> set global secure_file_priv='/Users/mini/Downloads';
ERROR 1238 (HY000): Variable 'secure_file_priv' is a read only variable

mysql> LOAD DATA INFILE '/Users/mini/Downloads/test.sql' INTO TABLE shop.order_info;
ERROR 13 (HY000): Can't get stat of '/Users/mini/Downloads/test.sql' (OS errno 13 - Permission denied);

mysql> LOAD DATA INFILE '/Users/mini/Downloads/test.sql' INTO TABLE shop.order_info;
ERROR 29 (HY000): File '/Users/mini/Downloads/test.sql' not found (OS errno 1 - Operation not permitted)
```

## 数据库分库分表程序

- 基于`AbstractRoutingDataSource`
- 基于ShardingSphere实现

### 相关参考

- [http://ckjava.com/2020/08/04/SpringBoot-Mybatis-AbstractRoutingDataSource-aop-annotation-understand-practice/](http://ckjava.com/2020/08/04/SpringBoot-Mybatis-AbstractRoutingDataSource-aop-annotation-understand-practice/)

## 问题

- reply重放和binlog日志的先后顺序和规则？
- load data  方式插入数据的效率