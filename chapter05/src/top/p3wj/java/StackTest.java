package top.p3wj.java;

/**
 * @author Aaron
 * @description
 * @date 2020/5/3 11:36 PM
 */
public class StackTest {
    public static void main(String[] args) {
        StackTest test = new StackTest();
        test.methodA();
    }

    public void methodA() {
        int i = 10;
        int j = 20;
        methodB();
    }

    private void methodB() {
        int k = 10;
        int m = 40;
    }
}
