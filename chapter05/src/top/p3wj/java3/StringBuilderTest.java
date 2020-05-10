package top.p3wj.java3;

/**
 * @author Aaron
 * @description 方法中定义局部变量石佛线程安全？具体情况具体分析
 * 如果只有一个线程才可以操作此数据，则必是线程安全的
 * 如果有多个线程操作此数据，则此数据是共享数据，如果不考虑同步机制的话，会存在线程安全问题
 * @date 2020/5/10 3:01 PM
 */
public class StringBuilderTest {
    int num = 10;

    //s1声明方式是安全的,保证了被单线程所使用
    public static void method1() {
        //StringBuilder线程不安全
        StringBuilder s1 = new StringBuilder();
        s1.append("a");
        s1.append("b");
    }

    //sBuilder的操作过程，是线程不安全的。可能被多个线程所调用
    public static void method2(StringBuilder sBuilder) {
        sBuilder.append("a");
        sBuilder.append("b");
    }

    //s1的操作：线程不安全的。返回出去可能能被其他的调用。只看该方法的话是安全的
    public static StringBuilder method3() {
        StringBuilder s1 = new StringBuilder();
        s1.append("a");
        s1.append("b");
        return s1;
    }

    //s2的操作，是线程安全的。返回后s1小消亡了
    public static String method4() {
        StringBuilder s2 = new StringBuilder();
        s2.append("a");
        s2.append("b");
        return s2.toString();
    }

    public static void main(String[] args) {

        StringBuilder s = new StringBuilder();
        new Thread(() -> {
            s.append("a");
            s.append("b");
        }).start();

        method2(s);
    }
}
