package top.p3wj.java1;

/**
 * @author Aaron
 * @description -Xms60m -Xmx60m -XX:NewRatio=2 -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * 则Eden:16 S0:2 S1:2 O:40
 * @date 2020/5/27 4:35 PM
 */
public class YoungOldAreaTest {
    public static void main(String[] args) {
        /**
         * 20m此时Eden跟S区都放不下，直接进入老年代
         */
        byte[] buffer = new byte[1024 * 1024 * 20];//20m
    }
}
