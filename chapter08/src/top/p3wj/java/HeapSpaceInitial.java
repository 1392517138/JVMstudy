package top.p3wj.java;

/**
 * @author Aaron
 * @description 1.设置堆空间大小参数
 * -Xms用来设置堆空间（年轻代+老年代）堆初始内存大小
 * -X 是jvm堆运行参数
 * ms  是memory start
 * -Xms用来设置堆空间（年轻代+老年代）堆最大内存大小
 * 2.默认堆空间的大小
 * 初始化内存大小：物理内存大小 / 64
 * 最大内存大小： 物理内存大小 / 4
 * 3.手动设置: -Xms600m -Xmx600m
 * 开发者建议将出事堆内存和最大堆内存设置成相同堆值
 * 4.查看设置参数：方式一：jps   / jstat -gc 进程id
 * 方式二：-XX:+PrintGCDetails
 * @date 2020/5/24 9:27 AM
 */
public class HeapSpaceInitial {
    public static void main(String[] args) {
        //返回Java虚拟机中的堆内存总量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        //返回Java虚拟机试图使用堆最大堆内存
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        System.out.println("-Xms : " + initialMemory + "M");
        System.out.println("-Xmx : " + maxMemory + "M");
//        System.out.println("系统内存大小：" + initialMemory * 64.0 / 1024 + "G");
//        System.out.println("系统内存大小：" + maxMemory * 4.0 / 1024 + "G");
//        try {
//            Thread.sleep(1000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
