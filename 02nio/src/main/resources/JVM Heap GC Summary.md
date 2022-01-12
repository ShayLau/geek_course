# week2

## 垃圾分类器列表

- Serial GC (序列化 GC)
- Parallel GC  (并行 GC)
- CMS GC (Concurrent Mark  Sweep GC)
- G1 GC (Garbage First  GC )

## 垃圾回收算法

- 分代收集，将堆内存分为新生代（Young Generation）和老年代（Old Generation），新生成的对象都会进入到新生代，在新生代的对象经过一定次数的还未被回收掉之后，就会进入到老年代
    - 新生代:在标记复制的基础上，将新生代分为两大部分，Eden 伊甸园区和 2 个Survivor幸存者区，默认比例：8:1:1,
    - 新生代回收过程：基于标记复制算法，使用eden 和一个  01 Survivor 区域存放新生对象（02Survivor未使用），当 young区 发生 GC时，将GC后仍存活的对象放到未使用的 02 Survivor 上， 这时 eden 区域和01Survivor 就空闲了，下次新生代 GC就将上一步的 02 Survivor和 Eden 区域的仍存活的对象，存放到 01Survivor 上，周而复始。
- 标记-清除，标记不再使用的对象内存（基于 GCRoot 可达性），清除掉不再使用的对象内存
- 标记-复制，因为标记清除会产生内存碎片，标记清除算法将内存空间按照一定比例划分（一部分使用，一部分空闲），在标记清除之后，仍存活的对象全部复制到空闲的内存部分，算法会浪费一定的内存空间
- 标记-整理，在标记清除的基础上，将仍存活的对象，将内存碎片全部移动到内存的一侧，因为有移动内存的过程，会增加 GC的时间

## 垃圾回收实验

### 实验说明

运行一段代码，持续运行 20s，循环创建对象，为了能通过[gceasy](http://www.gceasy.io) 更直观地查看 gc 视图，每次循环线程休眠 1 ms，通过命令行增加打印 gc 信息，分析 gc 信息。

### 垃圾回收代码

```java
package com.github.shaylau.geekCourse.learnNote.week02;

/**
 *演示GC日志生成与解读
 *
 * @date2022/1/11 11:00 AM
 */

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class GCLogAnalysis {
    private static Randomrandom= new Random();

    public static void main(String[] args) throws InterruptedException {

        // 当前毫秒时间戳
        long startMillis = System.currentTimeMillis();
        // 持续运行毫秒数; 可根据需要进行修改
        long timeoutMillis = TimeUnit.SECONDS.toMillis(20);
        // 结束时间戳
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("正在执行...");
        // 缓存一部分对象; 进入老年代
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        // 在此时间范围内,持续循环
        while (System.currentTimeMillis() < endMillis) {
            Thread.sleep(1);
            // 生成垃圾对象
            Object garbage =generateGarbage(100 * 1024);
            counter.increment();
            int randomIndex =random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("执行结束!共生成对象次数:" + counter.longValue());
    }

    // 生成对象
    private static Object generateGarbage(int max) {
        int randomSize =random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}

```

## Serial GC

### 垃圾回收特点

Serial GC收集器是一个单线程工作的收集器，只会使用一个处理器或一条收集线程去完成垃圾收集工作， 在进行垃圾收集时，必须暂停其他所有工作线程，直到它收集结束，对微型计算机（几十兆的内存大小）有一定使用意义。

### 垃圾回收实验

分配1g 的堆空间，初始堆大小 1g 

```bash
java -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:SerialGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis
```

GC 回收日志

```
Java HotSpot(TM) 64-Bit Server VM (25.311-b11) for bsd-amd64 JRE (1.8.0_311-b11), built on Sep 27 2021 13:08:09 by "java_re" with gcc 4.2.1 Compatible Apple LLVM 11.0.0 (clang-1100.0.33.17)
Memory: 4k page, physical 16777216k(1397996k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=1073741824 -XX:MaxHeapSize=1073741824 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseSerialGC 
2022-01-12T15:23:25.082-0800: 1.743: [GC (Allocation Failure) 2022-01-12T15:23:25.082-0800: 1.743: [DefNew: 279616K->34943K(314560K), 0.0542919 secs] 279616K->84974K(1013632K), 0.0544025 secs] [Times: user=0.04 sys=0.02, real=0.05 secs] 
2022-01-12T15:23:26.635-0800: 3.295: [GC (Allocation Failure) 2022-01-12T15:23:26.635-0800: 3.296: [DefNew: 314559K->34944K(314560K), 0.0660045 secs] 364590K->161236K(1013632K), 0.0660963 secs] [Times: user=0.03 sys=0.03, real=0.07 secs] 
2022-01-12T15:23:28.067-0800: 4.727: [GC (Allocation Failure) 2022-01-12T15:23:28.067-0800: 4.727: [DefNew: 314560K->34943K(314560K), 0.0598279 secs] 440852K->240614K(1013632K), 0.0599206 secs] [Times: user=0.04 sys=0.03, real=0.06 secs] 
2022-01-12T15:23:29.458-0800: 6.117: [GC (Allocation Failure) 2022-01-12T15:23:29.458-0800: 6.118: [DefNew: 314559K->34943K(314560K), 0.0566056 secs] 520230K->320480K(1013632K), 0.0567285 secs] [Times: user=0.03 sys=0.02, real=0.06 secs] 
2022-01-12T15:23:30.886-0800: 7.546: [GC (Allocation Failure) 2022-01-12T15:23:30.886-0800: 7.546: [DefNew: 314559K->34943K(314560K), 0.0499048 secs] 600096K->397853K(1013632K), 0.0500055 secs] [Times: user=0.03 sys=0.02, real=0.05 secs] 
2022-01-12T15:23:32.333-0800: 8.992: [GC (Allocation Failure) 2022-01-12T15:23:32.333-0800: 8.992: [DefNew: 314559K->34944K(314560K), 0.0526159 secs] 677469K->478934K(1013632K), 0.0527178 secs] [Times: user=0.03 sys=0.02, real=0.05 secs] 
2022-01-12T15:23:33.791-0800: 10.451: [GC (Allocation Failure) 2022-01-12T15:23:33.791-0800: 10.451: [DefNew: 314560K->34944K(314560K), 0.0574857 secs] 758550K->562832K(1013632K), 0.0576239 secs] [Times: user=0.04 sys=0.03, real=0.05 secs] 
2022-01-12T15:23:35.268-0800: 11.927: [GC (Allocation Failure) 2022-01-12T15:23:35.268-0800: 11.927: [DefNew: 314560K->34943K(314560K), 0.0494650 secs] 842448K->638572K(1013632K), 0.0495720 secs] [Times: user=0.03 sys=0.01, real=0.05 secs] 
2022-01-12T15:23:36.623-0800: 13.282: [GC (Allocation Failure) 2022-01-12T15:23:36.623-0800: 13.282: [DefNew: 314559K->314559K(314560K), 0.0000253 secs]2022-01-12T15:23:36.623-0800: 13.282: [Tenured: 603628K->373128K(699072K), 0.0684513 secs] 918188K->373128K(1013632K), [Metaspace: 2587K->2587K(1056768K)], 0.0686003 secs] [Times: user=0.07 sys=0.00, real=0.07 secs] 
2022-01-12T15:23:38.141-0800: 14.801: [GC (Allocation Failure) 2022-01-12T15:23:38.141-0800: 14.801: [DefNew: 279599K->34941K(314560K), 0.0139032 secs] 652728K->459303K(1013632K), 0.0140295 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2022-01-12T15:23:39.479-0800: 16.138: [GC (Allocation Failure) 2022-01-12T15:23:39.479-0800: 16.138: [DefNew: 314557K->34943K(314560K), 0.0187476 secs] 738919K->537917K(1013632K), 0.0188734 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2022-01-12T15:23:40.816-0800: 17.475: [GC (Allocation Failure) 2022-01-12T15:23:40.816-0800: 17.475: [DefNew: 314559K->34944K(314560K), 0.0217342 secs] 817533K->612325K(1013632K), 0.0218537 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2022-01-12T15:23:42.243-0800: 18.902: [GC (Allocation Failure) 2022-01-12T15:23:42.243-0800: 18.902: [DefNew: 314560K->34944K(314560K), 0.0451400 secs] 891941K->693215K(1013632K), 0.0453952 secs] [Times: user=0.03 sys=0.02, real=0.04 secs] 
Heap
 def new generation   total 314560K, used 289375K [0x0000000780000000, 0x0000000795550000, 0x0000000795550000)
  eden space 279616K,  90% used [0x0000000780000000, 0x000000078f877db8, 0x0000000791110000)
  from space 34944K, 100% used [0x0000000791110000, 0x0000000793330000, 0x0000000793330000)
  to   space 34944K,   0% used [0x0000000793330000, 0x0000000793330000, 0x0000000795550000)
 tenured generation   total 699072K, used 658271K [0x0000000795550000, 0x00000007c0000000, 0x00000007c0000000)
   the space 699072K,  94% used [0x0000000795550000, 0x00000007bd827e88, 0x00000007bd828000, 0x00000007c0000000)
 Metaspace       used 2593K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 277K, capacity 386K, committed 512K, reserved 1048576K
```

### GCEasy分析结果

- jvm年轻代分配 300m，老年代将近 700mb
- 平均 GC42ms,GC13次，1 次 FullGC
- 新生代第一次晋升说明：新生代`279616K->34943K=`244673 ，堆空间`279616K->84974K`=194642，新生代有84974-34943 =50031/1024约等于48.85839 晋升到old 区，查看以下 gceasy 分析图片可得知

![Untitled](week2%20f4be208c4d304b41a173e5ac4aad77f0/Untitled.png)

![Untitled](week2%20f4be208c4d304b41a173e5ac4aad77f0/Untitled%201.png)

![Untitled](week2%20f4be208c4d304b41a173e5ac4aad77f0/Untitled%202.png)

![Untitled](week2%20f4be208c4d304b41a173e5ac4aad77f0/Untitled%203.png)

## Parallel GC

### 垃圾回收特点

Parallel Scavenge 并行清扫GC  根据吞吐量的虚拟机自动调节新生代大小（Eden、Survivor 比例）的垃圾回收器，只需设置堆的大小和-XX：MaxGCPauseMillis参数（最大停顿时间）、- XX：GCTimeRatio（垃圾收集时间占总时间的比率）中的一个参数即可

### 垃圾回收实验

```java
java -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:ParallelGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis
```

GC 回收日志

```
Java HotSpot(TM) 64-Bit Server VM (25.311-b11) for bsd-amd64 JRE (1.8.0_311-b11), built on Sep 27 2021 13:08:09 by "java_re" with gcc 4.2.1 Compatible Apple LLVM 11.0.0 (clang-1100.0.33.17)
Memory: 4k page, physical 16777216k(996988k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=1073741824 -XX:MaxHeapSize=1073741824 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC 
2022-01-12T16:04:05.188-0800: 1.659: [GC (Allocation Failure) [PSYoungGen: 262144K->43506K(305664K)] 262144K->83863K(1005056K), 0.0324568 secs] [Times: user=0.06 sys=0.19, real=0.04 secs] 
2022-01-12T16:04:06.650-0800: 3.121: [GC (Allocation Failure) [PSYoungGen: 305650K->43514K(305664K)] 346007K->152206K(1005056K), 0.0409419 secs] [Times: user=0.06 sys=0.26, real=0.04 secs] 
2022-01-12T16:04:08.028-0800: 4.500: [GC (Allocation Failure) [PSYoungGen: 305658K->43518K(305664K)] 414350K->223311K(1005056K), 0.0293106 secs] [Times: user=0.08 sys=0.15, real=0.03 secs] 
2022-01-12T16:04:09.328-0800: 5.799: [GC (Allocation Failure) [PSYoungGen: 305662K->43514K(305664K)] 485455K->292841K(1005056K), 0.0326057 secs] [Times: user=0.08 sys=0.17, real=0.04 secs] 
2022-01-12T16:04:10.685-0800: 7.156: [GC (Allocation Failure) [PSYoungGen: 305658K->43518K(305664K)] 554985K->366631K(1005056K), 0.0325289 secs] [Times: user=0.08 sys=0.17, real=0.03 secs] 
2022-01-12T16:04:12.011-0800: 8.482: [GC (Allocation Failure) [PSYoungGen: 305662K->43510K(160256K)] 628775K->436645K(859648K), 0.0339463 secs] [Times: user=0.08 sys=0.18, real=0.03 secs] 
2022-01-12T16:04:12.674-0800: 9.145: [GC (Allocation Failure) [PSYoungGen: 160246K->70695K(232960K)] 553381K->471383K(932352K), 0.0090370 secs] [Times: user=0.06 sys=0.02, real=0.01 secs] 
2022-01-12T16:04:13.273-0800: 9.744: [GC (Allocation Failure) [PSYoungGen: 187431K->94543K(232960K)] 588119K->504292K(932352K), 0.0127571 secs] [Times: user=0.10 sys=0.02, real=0.01 secs] 
2022-01-12T16:04:13.877-0800: 10.348: [GC (Allocation Failure) [PSYoungGen: 211279K->108586K(232960K)] 621028K->535440K(932352K), 0.0163402 secs] [Times: user=0.11 sys=0.03, real=0.02 secs] 
2022-01-12T16:04:14.474-0800: 10.945: [GC (Allocation Failure) [PSYoungGen: 225322K->80150K(232960K)] 652176K->565549K(932352K), 0.0248674 secs] [Times: user=0.07 sys=0.11, real=0.02 secs] 
2022-01-12T16:04:15.074-0800: 11.545: [GC (Allocation Failure) [PSYoungGen: 196886K->36617K(232960K)] 682285K->596505K(932352K), 0.0280630 secs] [Times: user=0.05 sys=0.16, real=0.03 secs] 
2022-01-12T16:04:15.674-0800: 12.145: [GC (Allocation Failure) [PSYoungGen: 153283K->38754K(232960K)] 713171K->629774K(932352K), 0.0138000 secs] [Times: user=0.04 sys=0.07, real=0.01 secs] 
2022-01-12T16:04:16.299-0800: 12.770: [GC (Allocation Failure) [PSYoungGen: 155340K->40444K(232960K)] 746359K->665167K(932352K), 0.0152923 secs] [Times: user=0.04 sys=0.08, real=0.02 secs] 
2022-01-12T16:04:16.315-0800: 12.786: [Full GC (Ergonomics) [PSYoungGen: 40444K->0K(232960K)] [ParOldGen: 624723K->328606K(699392K)] 665167K->328606K(932352K), [Metaspace: 2587K->2587K(1056768K)], 0.0475532 secs] [Times: user=0.37 sys=0.02, real=0.05 secs] 
2022-01-12T16:04:16.947-0800: 13.418: [GC (Allocation Failure) [PSYoungGen: 116736K->39877K(232960K)] 445342K->368484K(932352K), 0.0047430 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
2022-01-12T16:04:17.515-0800: 13.986: [GC (Allocation Failure) [PSYoungGen: 156613K->41489K(232960K)] 485220K->406188K(932352K), 0.0081531 secs] [Times: user=0.08 sys=0.00, real=0.01 secs] 
2022-01-12T16:04:18.104-0800: 14.574: [GC (Allocation Failure) [PSYoungGen: 158051K->41741K(232960K)] 522750K->440285K(932352K), 0.0090161 secs] [Times: user=0.08 sys=0.00, real=0.01 secs] 
2022-01-12T16:04:18.687-0800: 15.158: [GC (Allocation Failure) [PSYoungGen: 158477K->35597K(232960K)] 557021K->473285K(932352K), 0.0092683 secs] [Times: user=0.07 sys=0.01, real=0.01 secs] 
2022-01-12T16:04:19.253-0800: 15.724: [GC (Allocation Failure) [PSYoungGen: 152333K->42147K(232960K)] 590021K->512119K(932352K), 0.0091161 secs] [Times: user=0.07 sys=0.01, real=0.01 secs] 
2022-01-12T16:04:19.863-0800: 16.333: [GC (Allocation Failure) [PSYoungGen: 158490K->41221K(232960K)] 628462K->547835K(932352K), 0.0088256 secs] [Times: user=0.08 sys=0.01, real=0.01 secs] 
2022-01-12T16:04:20.407-0800: 16.878: [GC (Allocation Failure) [PSYoungGen: 157957K->39135K(232960K)] 664571K->583673K(932352K), 0.0092087 secs] [Times: user=0.08 sys=0.00, real=0.01 secs] 
2022-01-12T16:04:20.982-0800: 17.452: [GC (Allocation Failure) [PSYoungGen: 155871K->38500K(232960K)] 700409K->618329K(932352K), 0.0080815 secs] [Times: user=0.08 sys=0.00, real=0.01 secs] 
2022-01-12T16:04:21.611-0800: 18.082: [GC (Allocation Failure) [PSYoungGen: 155236K->43103K(232960K)] 735065K->657980K(932352K), 0.0090910 secs] [Times: user=0.08 sys=0.00, real=0.01 secs] 
2022-01-12T16:04:22.267-0800: 18.738: [GC (Allocation Failure) [PSYoungGen: 159839K->44406K(232960K)] 774716K->697234K(932352K), 0.0136956 secs] [Times: user=0.05 sys=0.07, real=0.02 secs] 
2022-01-12T16:04:22.281-0800: 18.752: [Full GC (Ergonomics) [PSYoungGen: 44406K->0K(232960K)] [ParOldGen: 652827K->358960K(699392K)] 697234K->358960K(932352K), [Metaspace: 2587K->2587K(1056768K)], 0.0443845 secs] [Times: user=0.39 sys=0.01, real=0.04 secs] 
2022-01-12T16:04:22.907-0800: 19.378: [GC (Allocation Failure) [PSYoungGen: 116723K->34664K(232960K)] 475684K->393625K(932352K), 0.0045485 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
2022-01-12T16:04:23.508-0800: 19.979: [GC (Allocation Failure) [PSYoungGen: 151400K->39464K(232960K)] 510361K->429558K(932352K), 0.0074922 secs] [Times: user=0.07 sys=0.00, real=0.01 secs] 
Heap
 PSYoungGen      total 232960K, used 61992K [0x00000007aab00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 116736K, 19% used [0x00000007aab00000,0x00000007ac0fff28,0x00000007b1d00000)
  from space 116224K, 33% used [0x00000007b1d00000,0x00000007b438a2e0,0x00000007b8e80000)
  to   space 116224K, 0% used [0x00000007b8e80000,0x00000007b8e80000,0x00000007c0000000)
 ParOldGen       total 699392K, used 390094K [0x0000000780000000, 0x00000007aab00000, 0x00000007aab00000)
  object space 699392K, 55% used [0x0000000780000000,0x0000000797cf3818,0x00000007aab00000)
 Metaspace       used 2593K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 277K, capacity 386K, committed 512K, reserved 1048576K
```

## G1 GC

### 垃圾回收特点

Garbage First  将内存区域分成多个Region 区域，可以根据参数设置XX：G1HeapRegionSize设 定，取值范围为1MB～32MB，且应为2的N次幂。

而对于那些超过了整个Region容量的超级大对象， 将会被存放在N个连续的Humongous Region之中，G1的大多数行为都把Humongous Region作为老年代 的一部分来进行看待

G1收集器除了并发标记外，其余阶段也是要完全暂停用户线程的

垃圾回收过程：

1. 初始标记
2. 并发标记
3. 最终标记
4. 筛选回收

### 垃圾回收实验

```java
java   -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:G1GC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis
```

GC 回收日志

```
Java HotSpot(TM)64-Bit Server VM (25.311-b11) for bsd-amd64 JRE (1.8.0_311-b11), built on Sep27 202113:08:09 by "java_re" with gcc4.2.1 Compatible Apple LLVM11.0.0 (clang-1100.0.33.17)
Memory: 4k page, physical 16777216k(1018924k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=1073741824 -XX:MaxHeapSize=1073741824 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC
2022-01-12T16:05:45.937-0800:0.420: [GC pause (G1 Evacuation Pause) (young),0.0093725 secs]
   [Parallel Time:8.0 ms, GC Workers:10]
      [GC Worker Start (ms): Min:420.4, Avg:420.5, Max:420.6, Diff:0.3]
      [Ext Root Scanning (ms): Min:0.1, Avg:0.4, Max:1.2, Diff:1.1, Sum:3.9]
      [Update RS (ms): Min:0.0, Avg:0.0, Max:0.0, Diff:0.0, Sum:0.0]
         [Processed Buffers: Min:0, Avg:0.0, Max:0, Diff:0, Sum:0]
      [Scan RS (ms): Min:0.0, Avg:0.0, Max:0.1, Diff:0.1, Sum:0.4]
      [Code Root Scanning (ms): Min:0.0, Avg:0.0, Max:0.2, Diff:0.2, Sum:0.2]
      [Object Copy (ms): Min:6.1, Avg:6.8, Max:7.1, Diff:1.0, Sum:67.8]
      [Termination (ms): Min:0.0, Avg:0.3, Max:0.8, Diff:0.8, Sum:3.5]
         [Termination Attempts: Min:1, Avg:1.0, Max:1, Diff:0, Sum:10]
      [GC Worker Other (ms): Min:0.0, Avg:0.0, Max:0.1, Diff:0.1, Sum:0.4]
      [GC Worker Total (ms): Min:7.4, Avg:7.6, Max:7.8, Diff:0.4, Sum:76.2]
      [GC Worker End (ms): Min:428.0, Avg:428.2, Max:428.3, Diff:0.2]
   [Code Root Fixup:0.0 ms]
   [Code Root Purge:0.0 ms]
   [Clear CT:0.1 ms]
   [Other:1.3 ms]
      [Choose CSet:0.0 ms]
      [Ref Proc:0.3 ms]
      [Ref Enq:0.0 ms]
      [Redirty Cards:0.2 ms]
      [Humongous Register:0.1 ms]
      [Humongous Reclaim:0.1 ms]
      [Free CSet:0.0 ms]
   [Eden:51.0M(51.0M)- >0.0B(44.0M) Survivors:0.0B->7168.0K Heap:66.3M(1024.0M)->23.2M(1024.0M)]
 [Times: user=0.01 sys=0.04, real=0.01 secs]
.........
........
2022-01-12T16:06:05.139-0800:19.621: [GC concurrent-root-region-scan-start]
2022-01-12T16:06:05.139-0800:19.621: [GC concurrent-root-region-scan-end,0.0000922 secs]
2022-01-12T16:06:05.139-0800:19.621: [GC concurrent-mark-start]
2022-01-12T16:06:05.140-0800:19.622: [GC concurrent-mark-end,0.0013619 secs]
2022-01-12T16:06:05.140-0800:19.622: [GC remark 2022-01-12T16:06:05.140-0800:19.622: [Finalize Marking,0.0003235 secs] 2022-01-12T16:06:05.141-0800:19.623: [GC ref-proc,0.0000622 secs] 2022-01-12T16:06:05.141-0800:19.623: [Unloading,0.0005347 secs],0.0018573 secs]
 [Times: user=0.01 sys=0.00, real=0.00 secs]
2022-01-12T16:06:05.142-0800:19.624: [GC cleanup 517M->509M(1024M),0.0011657 secs]
 [Times: user=0.01 sys=0.00, real=0.00 secs]
2022-01-12T16:06:05.144-0800:19.625: [GC concurrent-cleanup-start]
2022-01-12T16:06:05.144-0800:19.626: [GC concurrent-cleanup-end,0.0000148 secs]
Heap
 garbage-first heap   total 1048576K, used 614895K [0x0000000780000000, 0x0000000780102000, 0x00000007c0000000)
  region size 1024K,83 young (84992K),8 survivors (8192K)
 Metaspace       used 2593K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 277K, capacity 386K, committed 512K, reserved 1048576K
```

## CMS GC

### 垃圾回收特点

垃圾回收的过程：

1. 初始标记（CMS initial mark）
2. 并发标记（CMS concurrent mark） 
3. 重新标记（CMS remark） 
4. 并发清除（CMS concurrent sweep）

Concurrent  mark sweep 并发标记清除算法，在并发标记和并发清除阶段可以和用户线程同时执行，但是会影响系统应用的吞吐量，主要原因是在并发标记和并发清除阶段会使用一定的线程资源，老年代标记清除会产生一部分碎片信息，会触发 fullgc 移动内存空间碎片

### 垃圾回收实验

```java
java -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:CMSGC.log -Xmx1g -Xms1g com.github.shaylau.geekCourse.learnNote.week02.GCLogAnalysis
```

GC 回收日志

```

Java HotSpot(TM)64-Bit Server VM (25.311-b11) for bsd-amd64 JRE (1.8.0_311-b11), built on Sep27 202113:08:09 by "java_re" with gcc4.2.1 Compatible Apple LLVM11.0.0 (clang-1100.0.33.17)
Memory: 4k page, physical 16777216k(1082472k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=1073741824 -XX:MaxHeapSize=1073741824 -XX:MaxNewSize=357916672 -XX:MaxTenuringThreshold=6 -XX:NewSize=357916672 -XX:OldPLABSize=16 -XX:OldSize=715825152 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
1.725: [GC (Allocation Failure)1.726: [ParNew: 279294K->34944K(314560K),0.0347661 secs] 279294K->91171K(1013632K),0.0350266 secs] [Times: user=0.08 sys=0.19, real=0.04 secs]
3.261: [GC (Allocation Failure)3.261: [ParNew: 314560K->34943K(314560K),0.0315746 secs] 370787K->168881K(1013632K),0.0316581 secs] [Times: user=0.07 sys=0.18, real=0.03 secs]
4.722: [GC (Allocation Failure)4.722: [ParNew: 314559K->34944K(314560K),0.0505156 secs] 448497K->245211K(1013632K),0.0506097 secs] [Times: user=0.47 sys=0.02, real=0.05 secs]
6.283: [GC (Allocation Failure)6.283: [ParNew: 314117K->34943K(314560K),0.0537462 secs] 524384K->328514K(1013632K),0.0538570 secs] [Times: user=0.51 sys=0.02, real=0.05 secs]
7.865: [GC (Allocation Failure)7.865: [ParNew: 314328K->34943K(314560K),0.0499020 secs] 607898K->409691K(1013632K),0.0499941 secs] [Times: user=0.46 sys=0.02, real=0.05 secs]
7.916: [GC (CMS Initial Mark) [1 CMS-initial-mark: 374747K(699072K)] 410083K(1013632K),0.0008167 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
7.917: [CMS-concurrent-mark-start]
7.919: [CMS-concurrent-mark:0.002/0.002 secs] [Times: user=0.01 sys=0.01, real=0.01 secs]
7.920: [CMS-concurrent-preclean-start]
7.921: [CMS-concurrent-preclean:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
7.921: [CMS-concurrent-abortable-preclean-start]
8.758: [CMS-concurrent-abortable-preclean:0.015/0.837 secs] [Times: user=0.08 sys=0.01, real=0.83 secs]
8.758: [GC (CMS Final Remark) [YG occupancy:194334 K (314560 K)]8.758: [Rescan (parallel) ,0.0009174 secs]8.759: [weak refs processing,0.0000208 secs]8.759: [class unloading,0.0002608 secs]8.759: [scrub symbol table,0.0004259 secs]8.760: [scrub string table,0.0002169 secs][1 CMS-remark: 374747K(699072K)] 569082K(1013632K),0.0020277 secs] [Times: user=0.01 sys=0.01, real=0.01 secs]
8.760: [CMS-concurrent-sweep-start]
8.761: [CMS-concurrent-sweep:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
8.761: [CMS-concurrent-reset-start]
8.765: [CMS-concurrent-reset:0.004/0.004 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
9.429: [GC (Allocation Failure)9.429: [ParNew: 314559K->34942K(314560K),0.0205656 secs] 556504K->363072K(1013632K),0.0206607 secs] [Times: user=0.20 sys=0.00, real=0.02 secs]
10.840: [GC (Allocation Failure)10.840: [ParNew: 314558K->34943K(314560K),0.0331771 secs] 642688K->442791K(1013632K),0.0333210 secs] [Times: user=0.32 sys=0.01, real=0.03 secs]
12.300: [GC (Allocation Failure)12.300: [ParNew: 314559K->34943K(314560K),0.0569598 secs] 722407K->531745K(1013632K),0.0570657 secs] [Times: user=0.53 sys=0.03, real=0.05 secs]
12.357: [GC (CMS Initial Mark) [1 CMS-initial-mark: 496802K(699072K)] 531849K(1013632K),0.0001772 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
12.358: [CMS-concurrent-mark-start]
12.359: [CMS-concurrent-mark:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
12.359: [CMS-concurrent-preclean-start]
12.360: [CMS-concurrent-preclean:0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
12.360: [CMS-concurrent-abortable-preclean-start]
13.203: [CMS-concurrent-abortable-preclean:0.013/0.844 secs] [Times: user=0.06 sys=0.02, real=0.84 secs]
13.203: [GC (CMS Final Remark) [YG occupancy:204808 K (314560 K)]13.204: [Rescan (parallel) ,0.0006669 secs]13.204: [weak refs processing,0.0000222 secs]13.204: [class unloading,0.0002304 secs]13.204: [scrub symbol table,0.0004298 secs]13.205: [scrub string table,0.0002175 secs][1 CMS-remark: 496802K(699072K)] 701610K(1013632K),0.0017346 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
13.205: [CMS-concurrent-sweep-start]
13.207: [CMS-concurrent-sweep:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
13.207: [CMS-concurrent-reset-start]
13.208: [CMS-concurrent-reset:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
13.799: [GC (Allocation Failure)13.799: [ParNew: 314555K->34941K(314560K),0.0179027 secs] 626608K->423948K(1013632K),0.0180173 secs] [Times: user=0.17 sys=0.00, real=0.01 secs]
15.313: [GC (Allocation Failure)15.313: [ParNew: 314557K->34942K(314560K),0.0179906 secs] 703564K->500939K(1013632K),0.0181071 secs] [Times: user=0.17 sys=0.00, real=0.02 secs]
16.735: [GC (Allocation Failure)16.736: [ParNew: 314558K->34942K(314560K),0.0343154 secs] 780555K->574072K(1013632K),0.0344281 secs] [Times: user=0.32 sys=0.01, real=0.04 secs]
16.770: [GC (CMS Initial Mark) [1 CMS-initial-mark: 539129K(699072K)] 574853K(1013632K),0.0002292 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
16.770: [CMS-concurrent-mark-start]
16.771: [CMS-concurrent-mark:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
16.771: [CMS-concurrent-preclean-start]
16.772: [CMS-concurrent-preclean:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
16.772: [CMS-concurrent-abortable-preclean-start]
17.603: [CMS-concurrent-abortable-preclean:0.014/0.830 secs] [Times: user=0.08 sys=0.02, real=0.83 secs]
17.603: [GC (CMS Final Remark) [YG occupancy:209762 K (314560 K)]17.603: [Rescan (parallel) ,0.0006849 secs]17.604: [weak refs processing,0.0000202 secs]17.604: [class unloading,0.0002410 secs]17.604: [scrub symbol table,0.0004101 secs]17.604: [scrub string table,0.0001707 secs][1 CMS-remark: 539129K(699072K)] 748892K(1013632K),0.0016586 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
17.605: [CMS-concurrent-sweep-start]
17.606: [CMS-concurrent-sweep:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
17.606: [CMS-concurrent-reset-start]
17.607: [CMS-concurrent-reset:0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
18.180: [GC (Allocation Failure)18.180: [ParNew: 314558K->34942K(314560K),0.0182428 secs] 632774K->429702K(1013632K),0.0183549 secs] [Times: user=0.17 sys=0.00, real=0.02 secs]
19.596: [GC (Allocation Failure)19.596: [ParNew: 314558K->34942K(314560K),0.0173968 secs] 709318K->509848K(1013632K),0.0174928 secs] [Times: user=0.17 sys=0.00, real=0.02 secs]
Heap
 par new generation   total 314560K, used 131183K [0x0000000780000000, 0x0000000795550000, 0x0000000795550000)
  eden space 279616K,34% used [0x0000000780000000, 0x0000000785dfc2f0, 0x0000000791110000)
  from space 34944K,99% used [0x0000000793330000, 0x000000079554fbf8, 0x0000000795550000)
  to   space 34944K,0% used [0x0000000791110000, 0x0000000791110000, 0x0000000793330000)
 concurrent mark-sweep generation total 699072K, used 474905K [0x0000000795550000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2593K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 277K, capacity 386K, committed 512K, reserved 1048576K

```

GCEasy分析结果

![Untitled](week2%20f4be208c4d304b41a173e5ac4aad77f0/Untitled%204.png)

## 引用

- 深入理解Java虚拟机：JVM高级特性与最佳实践（第3版）周志明
- [https://gceasy.io/](https://gceasy.io/)