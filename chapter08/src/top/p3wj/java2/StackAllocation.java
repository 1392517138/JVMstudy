package top.p3wj.java2;

/**
 * @author Aaron
 * @description -Xmx1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 * @date 2020/5/28 1:10 PM
 */
public class StackAllocation {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            alloc();
        }
        //查看执行时间
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为： " + (end - start) + "ms");
        //为了方便查看堆内存中的对象个数，线程sleep
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void alloc() {
        User user = new User(); //未发生逃逸
    }

    static class User {

    }
}
