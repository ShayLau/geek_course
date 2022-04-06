# week13

## 学习资料

- ****Kafka 核心技术与实战（胡夕）****

## Kafka 搭建

### 单机版 Docker 配置

```yaml
version: "3"
services:
  zookeeper:
    image: 'bitnami/zookeeper:latest'
    ports:
      - '2181:2181'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: 'bitnami/kafka:latest'
    ports:
      - '9092:9092'
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper
```

### 集群版配置

```yaml
version: '2'

services:
  zookeeper:
    image: 'bitnami/zookeeper:latest'
    ports:
     - '2181:2181'
    environment:
     - ALLOW_ANONYMOUS_LOGIN=yes
  kafka1:
    image: 'bitnami/kafka:latest'
    ports:
      - '9092'
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
  kafka2:
    image: 'bitnami/kafka:latest'
    ports:
      - '9092'
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
  kafka3:
    image: 'bitnami/kafka:latest'
    ports:
      - '9092'
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
```

### 基于 Springboot 实现 Kafka 消息收发

- [Springboot Kafka 说明](https://spring.io/projects/spring-kafka#overview)
- [Springboot Kafka 快速入门文档](https://docs.spring.io/spring-kafka/docs/current/reference/html/#preface)

## 自定义 MQ实现大纲

### 针对基础版消息发送、消息消费说明

1. 基于分布式思想：增加注册中心，注册中心主要作用记录 消息队列的中继器 Broker 信息
2. 增加功能角色：消息生产者、消息消费者
3. 增加生产者功能：发送消息、
4. 增加消费者功能：消费消息、消费消费确认（提交偏移量）、消费的不同方式（消息推拉）
5. 高级功能：基于 Http 服务模式，增加接口消息生产发送、