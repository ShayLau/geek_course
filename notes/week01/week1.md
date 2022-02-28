# week 1

## 学习资料

- 深入理解 Java 虚拟机（郑雨迪）

## 字节码技术

### 字节码指令分类

1. 栈
2. 流程控制
3. 对象
4. 算术运算及类型转换

### 基础数据类型指令

![Untitled](week%201%20a9df3/Untitled.png)

### javac 编译测试

```bash

生成字节码文件
javac  -g HelloByteCode.java

查看字节码文件
javap -v -p HelloByteCode > compile_detail.txt
```

## Jvm 类加载器

### JVM 启动参数命名

![Untitled](week%201%20a9df3/Untitled%201.png)

### 垃圾回收

串行 GC启动（SerialGC）

- Xmx1g -Xms512m -Xmn512m -XX:+UseSerialGC

并行 GC(ParallerGC)

- Xmx1g -Xms512m -Xmn512m -XX:+UseParallerGC

并发标记清除 GC(ConcMarkSweepGC)

- Xmx1g -Xms512m -Xmn512m -XX:+ConcMarkSweepGC
- 使用场景：多核 CPU、短暂 GC

并行GC和并发GC的区别

一个会产生 STW ,一个不会产生 STW

G1GC(Garbage First)

- Xmx1g -Xms512m -Xmn512m -XX:+UseG1GC -XX:MaxGCPauseMillis=50
- 最大 GC暂停时间 XX:MaxGCPauseMillis

### jvm 参考文档

- [JVM调试参数命令](https://www.oracle.com/java/technologies/javase/vmoptions-jsp.html#Options)
- jps -mlv （显示进程的启动配置参数）

## 疑问

- CMS GC 过程不懂
- jhsdb jmap  —heap —pid

### 作业

`画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系`

![Untitled](week%201%20a9df3/Untitled%202.png)