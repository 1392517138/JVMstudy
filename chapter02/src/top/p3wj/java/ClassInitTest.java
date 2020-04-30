package top.p3wj.java;

/**
 * @author Aaron
 * @description
 * @date 2020/4/30 9:11 PM
 */
public class ClassInitTest {
    private static int num = 1;

    static {
        num = 2;
        number = 20;
        System.out.println(num);
//        System.out.println(number); //报错，非法前向引用
    }

    /**
     * linking之prepare: number = 0 ---> initial: 20 ---> 10
     * 会按照顺序
     */
    private static int number  = 10;
    public static void main(String[] args) {
        System.out.println(ClassInitTest.num);
        System.out.println(number);
        /**
         * 运行结果，2,10
         */
    }
}
