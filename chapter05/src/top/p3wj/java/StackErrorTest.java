package top.p3wj.java;

/**
 * @author Aaron
 * @description 演示栈中异常StackOverflowError
 * @date 2020/5/4 3:00 PM
 * <p>
 * 默认情况下：count: 11=0823
 * 设置栈大小: -Xss256k count:1874
 */
public class StackErrorTest {
    private static int count = 1;

    public static void main(String[] args) {
        System.out.println(count);
        count++;
        main(args);
    }
}
