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