package top.p3wj.java1;

/**
 * @author Aaron
 * @description -Xms600m    -Xmx600m
 * -NewRatio 默认为2，即1：2
 * 自适应：
 * -XX:NewRatio：设置新生代与老年代但比例.=
 * -XX:-UseAdaptiveSizePolicy：关闭自适应的内存分配策略（暂时用不到）
 * -XX:SurvivorRatio：设置新生代中Eden区与Survivor区的比例。默认值为8
 * -Xmn: 设置新生代代空间代大小。若此命令与-XX:NewRatio矛盾，则以此命令为准（一般不使用）
 * @date 2020/5/24 11:02 AM
 */
public class EdenSurvivorTest {
    public static void main(String[] args) {
        System.out.println("为只是来打个酱油");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
