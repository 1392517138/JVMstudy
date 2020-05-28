package top.p3wj.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Aaron
 * @description
 * @date 2020/5/24 10:35 AM
 */
public class OOMTest {
    public static void main(String[] args) {
        ArrayList<Picture> list = new ArrayList<>();
        //通过while(true)不断地去添加
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024 * 1024)));
        }
    }
}

class Picture {
    private byte[] pixels;

    public Picture(int length) {
        this.pixels = new byte[length];
    }
}