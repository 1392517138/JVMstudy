package top.p3wj.java1;

/**
 * @author Aaron
 * @description
 * @date 2020/5/2 10:20 AM
 */
public class StringTest {
    public static void main(String[] args) {
        //在输出之前，有一个String对象的创建.并在src下创建一个java.lang包,并创建一个String对象
        String str = new String();
        System.out.println("hello,Aaron!");

        //自定义的本类，在父类中都没有，所以他就是系统类加载器.--->sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(StringTest.class.getClassLoader());
    }
}
