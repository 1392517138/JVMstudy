package top.p3wj.java1;

/**
 * @author Aaron
 * @description
 * @date 2020/5/8 8:54 AM
 */
public class DynamicLinkingTest {
    int num = 10;

    public void methodA() {
        System.out.println("methodA()...");
    }

    public void methodB() {
        System.out.println("methodB()...");
        methodA();

        num++;
    }
}
