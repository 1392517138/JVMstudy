package top.p3wj.java2;

/**
 * @author Aaron
 * @description
 * @date 2020/5/28 1:43 PM
 */
public class SynchronizedTest {
    public void f() {
        Object hollis = new Object();
        synchronized (hollis) {
            System.out.println(hollis);
        }
    }
}
