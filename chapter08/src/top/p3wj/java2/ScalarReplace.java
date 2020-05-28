package top.p3wj.java2;

/**
 * @author Aaron
 * @description -Xmx16m -Xms16m -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:-EliminateAllocations
 * @date 2020/5/28 1:50 PM
 */
public class ScalarReplace {
    public static class User {
        public int id;
        public String name;
    }

    public static void alloc() {
        User u = new User(); //未发生逃逸，替换成标量
        u.id = 5;
        u.name = "www.p3wj.top";
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为： " + (end - start) + "ms");
    }
}
