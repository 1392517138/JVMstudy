package javapp.langpp;

/**
 * @author Aaron
 * @description
 * @date 2020/5/2 10:23 AM
 */
public class String {

    static {
        System.out.println("我是自定义的String类的静态代码块");
    }

    public static void main(java.lang.String[] args) {
        System.out.println("hello String");
        String tp = new String();
        System.out.println("这是一个" + tp.getClass().getClassLoader());
    }
}
