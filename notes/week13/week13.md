# week13

## 学习资料

- ****Kafka 核心技术与实战（胡夕）****

## 自定义 MQ实现大纲

### 针对基础版消息发送、消息消费说明

1. 基于分布式思想：增加注册中心，注册中心主要作用记录 消息队列的中继器 Broker 信息
2. 增加功能角色：消息生产者、消息消费者
3. 增加生产者功能：发送消息、
4. 增加消费者功能：消费消息、消费消费确认（提交偏移量）、消费的不同方式（消息推拉）
5. 高级功能：基于 Http 服务模式，增加接口消息生产发送、