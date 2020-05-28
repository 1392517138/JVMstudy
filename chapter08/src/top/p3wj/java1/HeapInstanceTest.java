package top.p3wj.java1;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Aaron
 * @description -Xms600m -Xmx600m
 * @date 2020/5/24 3:07 PM
 */
public class HeapInstanceTest {
    byte[] buffer = new byte[new Random().nextInt(1024 * 200)];

    public static void main(String[] args) {
        ArrayList<HeapInstanceTest> list = new ArrayList<>();
        while (true) {
            list.add(new HeapInstanceTest());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
