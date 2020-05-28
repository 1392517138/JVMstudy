package top.p3wj.java;

/**
 * @author Aaron
 * @description -Xms20m -Xmx20m
 * @date 2020/5/23 9:04 PM
 */
public class HeapDemo1 {
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
