package top.p3wj.java1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaron
 * @description -Xms9m -Xmx9m -XX:+PrintGCDetails
 * @date 2020/5/27 12:45 PM
 */
public class GCTest {
    public static void main(String[] args) {
        int i = 0;
        try {
            List<String> list = new ArrayList<>();
            String a = "p3wj.top";
            while (true) {
                list.add(a);
                a = a + a;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("遍历的次数为： " + i);
        }
    }

}
