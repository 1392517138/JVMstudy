package top.p3wj.java;

/**
 * @author Aaron
 * @description -Xms10m -Xmx10m
 * @date 2020/5/23 9:04 PM
 */
public class HeapDemo {
    public static void main(String[] args) {
        System.out.println("start...");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end...");
    }
}
