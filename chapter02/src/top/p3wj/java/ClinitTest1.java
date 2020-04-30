package top.p3wj.java;

/**
 * @author Aaron
 * @description
 * @date 2020/4/30 10:01 PM
 */
public class ClinitTest1 {
    static class Father{
        public static int A = 1;
        static {
            A = 2;
        }
    }

    static class Son extends Father{
        public static int B = A;
    }

    public static void main(String[] args) {
        /**
         * 加载 Father()类，其次加载Son类，结果为2
         */
        System.out.println(Son.B);
    }
}
