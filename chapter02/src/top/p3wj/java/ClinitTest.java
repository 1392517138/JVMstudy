package top.p3wj.java;

/**
 * @author Aaron
 * @description
 * @date 2020/4/30 9:42 PM
 */
public class ClinitTest {
    //任何一个类声明以后，内部至少存在一个类的构造器
    private int a  = 1;
    private static  int c = 3;
    public static void main(String[] args) {
        int b = 2;
    }

    public ClinitTest(){
        a = 10;
        int d = 20;
    }
}
