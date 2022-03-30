# week13

## 学习资料

- ****Redis 核心技术与实战（蒋德钧）****

## Redis 集群高可用

- 主从复制
- 哨兵模式
- 集群模式

### 哨兵模式

redis sentinel 使用 raft 协议使用可高可用

## Redisson  缓存分布式框架

基于 NettyNIO

## 内存网格技术

## 消息通讯系统

系统间通信方式

- 基于文件（在相同机器），无法跨机器
- 基于内存（在相同机器），无法跨机器
- 基于数据库（在不同机器），实时性查
- 基于 RPC （在不同机器），调用关系复杂
- 基于 Socket，复杂度高
- 基于消息通信

## Windows 平台单间主从集群哨兵

### 1、生成 Redis 服务

```bash
#生成 windows服务
redis-server --service-install config/redis-6379.conf --service-name redis6379
redis-server --service-install config/redis-6380.conf --service-name redis6380
redis-server --service-install config/redis-6381.conf --service-name redis6381
redis-server --service-install config/redis-6382.conf --service-name redis6382
redis-server --service-install config/redis-6383.conf --service-name redis6383
redis-server --service-install config/redis-6384.conf --service-name redis6384

#卸载windows服务
redis-server --service-uninstall --service-name  redis6379
redis-server --service-uninstall --service-name  redis6380
redis-server --service-uninstall --service-name  redis6381
redis-server --service-uninstall --service-name  redis6382
redis-server --service-uninstall --service-name  redis6383
redis-server --service-uninstall --service-name  redis6384

#查看windows服务查看是否启动成功
#win+R
#services.msc
```

### 2、主从

```bash
#启动主从
redis-server ./config/master-slave-config/6379.conf
redis-server ./config/master-slave-config/6380.conf
redis-server ./config/master-slave-config/6381.conf
```

### 3、集群

```bash
#服务查询端口

netstat -ano|findstr "6379"

#netstat -ano|findstr 6380、6381、6382、端口...

#kill 端口

taskkill /pid 6379 /F

#启动redis集群，基于redis服务已启动

redis-cli --cluster create 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384 --cluster-replicas 1
```

### 4、哨兵

```bash
#启动哨兵
redis-server ./config/sentinel-config/sentinel-26379.conf --sentinel
redis-server ./config/sentinel-config/sentinel-26380.conf --sentinel
redis-server ./config/sentinel-config/sentinel-26381.conf --sentinel
```

## 使用 docker 测试 Redis

### 主从测试

```bash
#拉取 Redis
docker pull redis
#运行 6379
docker run  -p 6379:6379 -v ~/redis/conf/:/usr/local/etc/redis/conf/ -v ~/redis/data/master-slave/6379/:/usr/local/etc/redis/data/master-slave/6379  --name redis-master  redis redis-server /usr/local/etc/redis/conf/redis-6379.conf
#运行 6380
docker run  -p 6380:6380 -v ~/redis/conf/:/usr/local/etc/redis/conf/ -v ~/redis/data/master-slave/6380/:/usr/local/etc/redis/data/master-slave/6380  --name redis-slave  redis redis-server /usr/local/etc/redis/conf/redis-6380.conf

#运行redis-cli 6380 端口，replica 设置 
redis-cli -p 6380
replicaof 127.0.0.1 6379
```