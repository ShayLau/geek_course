# week8

## 学习资料

- ****分布式技术原理与算法解析（聂鹏程）****

## 分布式数据库

## 数据规模问题

传统数据库数据量级太大，技术发展趋势已出现 分布式数据库，如[阿里的OceanBase](https://www.oceanbase.com/) 、[腾讯的商业 TDSQL](https://cloud.tencent.com/document/product/557)

- 分布式数据库
- 关系型数据库

## 关系型数据库拆分

- 垂直拆分
- 水平拆分

### 垂直拆分

主要是按照业务拆分，如数据库有用户数据、订单数据、商品数据，将多个不同的业务数据按照业务拆分到不同的数据库实例上

### 水平拆分

在垂直拆分到一定程度后，无法再通过垂直拆分优化数据库系统，单业务系统的数据表数据，可通过水平拆分，将一个数据库实例上的数据，拆分成多个实例，常用的方式有两种，一种是分库，另一种是分表

### 拆分的问题

垂直拆分将不同的业务数据拆分到不同的数据库实例上，当操作不同数据库实例上的业务数据，如果同时操作多个业务数据源，同时新增修改业务数据，需要控制业务数据正常落库，使用分布式数据库事务解决相关问题。

# 分布式事务

- 强一致性
- 最终一致性

## 强一致性知识点(XA)

- XA 分布式事务（基于 XA协议，XA 协议是由 X/Open 组织提出的分布式事务处理规范）
- MySQL 5.0  innoDb 引擎支持XA,可通过 show engine 查看 MySQL 服务是否支持 XA，
- XA 使用两阶段处理，xa过程分为 xastart、 xaend、xaprepare、xacommit、xarollback，xaprepare之前的属于一阶段，xacommit、xarollback 属于二阶段
- XA 事务主要在一阶段将多个数据源执行相关事务操作，执行之后如果相关
- 文档说MySQL  在（5~5.7）之间的版本，在执行 XA的事务时会有Bug，如果在 prepare 阶段执行完之后， MySQL 服务 down 掉了，再次重启之后，xa事务就会有问题，解决的方法是在 binlog 中增加 xa 的相关内容
- 在之前的 RocketMQ中间件学习过程中，RocketMQ 的事务消息也是使用二阶段提交的方式。
- 相关框架：SEATA(阿里)、[Atomikos](https://www.atomikos.com/Main/AtomikosResources)、[Narayana](https://www.narayana.io/)

## 最终一致性（BASE）

### TCC说明

**TCC 模式即将每个服务业务操作分为两个阶段，第一个阶段检查并预留相关资源，第二阶段根据所有
服务业务的 Try 状态来操作，如果都成功，则进行 Confirm 操作，如果任意一个 Try 发生错误，则全
部 Cancel。**

- 第一阶段（预留资源）：
    - Try
- 第二阶段（提交或回滚）：
    - comit
    - callback

Tcc对业务有侵入性，TCC 需要注意的几个问题:

1. 允许空回滚
2. 防悬挂控制
3. 幂等设计

## BASE的模式

### Saga模式(没印象)

Saga 模式没有 try 阶段，直接提交事务，复杂情况下，对回滚操作的设计要求较高。
****

### AT模式

两阶段提交，自动生成反向SQL,如分布式事务中（操作一对应新增操作，操作二对应删除操作），在AT模式下，生成操作一对应的删除操作，操作二的新增操作，如果分布式整体事务执行失败，则执行上述的反向SQL操作。

### BASE框架SEATA

# 实验

## 基于 ShardingSphere-Proxy 实现分库分表

### ShardingSphere-Proxy 环境说明

- ShardingSphere-Proxy 5.0.0版本（本机使用 5.1.0 版本启动 ShardingSphere-Proxy 中间件一直报错，按照官方的 Github 的配置仍然出错，改为使用 5.0版本）
- 本机有两个 MySQL 服务实例（参照配置文件）

### 具体步骤

1. [下载 Sharding-proxy](https://archive.apache.org/dist/shardingsphere/5.0.0/)
2. 我本地的使用环境是 MySQL8.0.28,下载 MySQL 驱动放到上一步解压的 lib 文件夹
3. 配置数据分片和读写分离的配置

### 配置文件

分片配置文件（config-sharding.yaml）

```yaml
schemaName: sharding_db

dataSources:
  ds0: 
    url: jdbc:mysql://localhost:3316/ds0
    username: root
    password: admin123
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 65
  ds1:
    url: jdbc:mysql://localhost:3326/ds1
    username: root
    password: admin123
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 65
rules:
- !SHARDING
  tables:
    order_info:
      actualDataNodes: ds${0..1}.order_info${0..15}
      databaseStrategy:
        standard:
          shardingColumn: u_id
          shardingAlgorithmName: database_inline
      tableStrategy: 
        standard:
          shardingColumn: id
          shardingAlgorithmName: order_info_inline
      keyGenerateStrategy:
        column: id
        keyGeneratorName: snowflake
  bindingTables:
    - order_info
  defaultDatabaseStrategy:
    none:
  defaultTableStrategy:
    none:

#定义分片算法策略
  shardingAlgorithms:
    database_inline:
      type: INLINE
      props:
        algorithm-expression: ds${u_id % 2}
    order_info_inline:
      type: INLINE
      props:
        algorithm-expression: order_info${id % 15}
  keyGenerators:
    snowflake:
      type: SNOWFLAKE
      props:
        worker-id: 123
```

proxy-服务配置文件

```yaml
rules:
  - !AUTHORITY
    users:
      - root@%:root
      - sharding@:sharding
    provider:
      type: ALL_PRIVILEGES_PERMITTED
  - !TRANSACTION
    defaultType: XA
    providerType: Atomikos
props:
  max-connections-size-per-query: 1
  kernel-executor-size: 16
  proxy-frontend-flush-threshold: 128  
  proxy-opentracing-enabled: false
  proxy-hint-enabled: false
  sql-show: true
  check-table-metadata-enabled: false
  show-process-list-enabled: false
  proxy-backend-query-fetch-size: -1
  check-duplicate-table-enabled: false
  sql-comment-parse-enabled: false
  proxy-frontend-executor-size: 0 
  proxy-backend-executor-suitable: OLAP
  proxy-frontend-max-connections: 0 
  sql-federation-enabled: false
```

订单信息表结构

```sql
drop table if exists  order_info;
create table order_info
(
    id          varchar(50) not null comment '订单id',
    g_id        varchar(50) not null comment '商品id',
    u_id        bigint not null comment '用户id',
    status      tinyint     not null comment '订单状态  1.已取消 2.已完成',
    pay_status  tinyint     not null comment '支付状态 1.未支付 2.支付中 3.已支付 ',
    money       double      null comment '金额',
    create_time long  not null comment '创建时间',
    update_time long  not null comment '修改时间'
);
```

### 踩坑说明

分片配置文件说明：

- config-sharding.yaml文件中的 datasource 的 ds0、ds1，需要先创建对应的 MySQL 服务实例
- rules 中的数据库策略databaseStrategy和数据表策略tableStrategy的参数shardingAlgorithmName，都需要使用shardingAlgorithms中配置的内容名称才行
- 算法表达式algorithm-expression，如数据库分片策略名称database_inline的，它的策略是ds${u_id % 2} ，这个 u_id对应的 order_info表字段， rules.tables.order_info中的 u_id 字段取余，来确定使用 ds0 数据源还是 ds1 数据源，这块如果配置错误，在程序执行 sql 的时候会报错，如：Insert statement does not support sharding table routing to multiple data nodes
- 分片策略中的表达式字段，必须确保和字段类型不冲突，如ds${u_id % 2}， u_id%2标识能够取余，我一开始使用 varchar 定义 u_id 字段，一直报错，改成了 bigint 就没出问题

proxy 启动后的MYSQL关系型数据库说明：

- 启动 Sharding-proxy ,相当于在本地启动了一个 MYSQL服务实例，mysql 脚本：mysql -h 127.0.0.1 -P3307 -u root -p -A,用户名密码是 server.xml 中配置的用户名密码,为什么使用-A没搞懂（Reading table information for completion of table and column names You can turn off this feature to get a quicker startup with -A）
- 端口对应 3307

## JDBC添加测试数据到proxy

测试结果：100w 数据添加到 proxy 数据库，约  PT15M39.367S（15 分钟 39 秒）

```java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author ShayLau
 * @date 2022/3/3 15:44
 */
public class ShardingProxyTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int dataNumber = 1000000;
        int batchNumber = dataNumber / 10;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String user = "root";
        String password = "root";
        String mysqlDbUrl = "jdbc:mysql://localhost:3307/sharding_db";
        Connection connection = DriverManager.getConnection(mysqlDbUrl, user, password);
        String sql = "insert into order_info ( g_id,u_id,status,pay_status,money,create_time,update_time) values (?,?,?,?,?,?,?)";
        System.out.println(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        LocalDateTime start = LocalDateTime.now();
        int count = 1;
        for (int i = 1; i <= dataNumber; i++) {
            Object[] obj = values();
            preparedStatement.setObject(1, obj[0]);
            preparedStatement.setObject(2, obj[1]);
            preparedStatement.setObject(3, obj[2]);
            preparedStatement.setObject(4, obj[3]);
            preparedStatement.setObject(5, obj[4]);
            preparedStatement.setObject(6, obj[5]);
            preparedStatement.setObject(7, obj[6]);
            preparedStatement.addBatch();
            System.out.println("添加批次数据" + i + ";");
            if (i % batchNumber == 0) {
                preparedStatement.executeBatch();
                System.out.println("执行批次数据:" + (count - 1) * batchNumber + "~" + count * batchNumber + "插入！");
                count++;
            }
        }
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println(duration);
    }

  
    public static Object[] values() {
        Object[] obj = new Object[8];
        Random random = new Random();
        String goodsId = String.valueOf(random.nextInt(3));
        obj[0] = goodsId;
        obj[1] = random.nextInt(10);
        int payStatus = random.nextInt(3);
        obj[2] = payStatus;
        int orderStatus = random.nextInt(2);
        obj[3] = orderStatus;
        double money = Math.random();
        obj[4] = money;
        long current = System.currentTimeMillis();
        obj[5] = current;
        obj[6] = current;
        return obj;
    }
}
```