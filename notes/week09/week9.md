# week9

## 学习资料

- ****RPC 实战与核心原理（何小锋）****

### RPC（remote procedure cal）

远程过程调用

### 自定义RPC实现

- RPC请求过滤
- 动态代理（Javassist）和字节码增强技术（ByteBuddy）

### Java实现动态代理的实现

1. 生成代理类 **Proxy.newInstance();**
2. 动态代理具体实现类 ：**InvocationHandle.invoke()** 
    1. `ProxyGenerator::``generateProxyClass（）` 用来生成代理类
    2. 其中`sun.misc.ProxyGenerator.saveGeneratedFiles`  参数控制是否将代理类保存到磁盘

### 字节码增强 ByteBuddy

- [美团-字节码增强技术](https://tech.meituan.com/2019/09/05/java-bytecode-enhancement.html)

## 实例代码获取bean方式

- `ApplicationContext`
- `ApplicationContextAware`,通过实现Spring的`ApplicationContextAware`的`setApplicationContext(）` 将Spring的容器设置到实现他的类中，这样就可以通过自定义的类型，操作Spring中的bean

```java
public class DemoResolver implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object resolve(String serviceClass) {
        return this.applicationContext.getBean(serviceClass);
    }
}
```

### 其他知识

泛型：JVM的泛型支持，会将泛型擦除

## 分布式+TCC实现转账

结合 dubbo+hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:

- 用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;
- 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;
- 设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

```sql

create schema  tccdb1;
create schema  tccdb2;
use tccdb1;
use tccdb2;
create table account
(
    id             bigint auto_increment primary key,
    user_id        varchar(128) not null,
    cny_balance    decimal      not null comment '人民币余额',
    dollar_balance decimal      not null comment '美元余额',
    create_time    datetime     not null,
    update_time    datetime     null
);

create table freeze_amount
(
    id                   bigint auto_increment primary key,
    user_id              varchar(128) not null,
    freeze_cny_amount    decimal      not null comment '冻结人民币金额，扣款暂存余额',
    freeze_dollar_amount decimal      not null comment '冻结美元金额，扣款暂存余额',
    create_time          datetime     not null,
    update_time          datetime     null
)

```