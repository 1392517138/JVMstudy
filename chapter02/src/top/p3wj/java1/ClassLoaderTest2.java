package top.p3wj.java1;

/**
 * @author Aaron
 * @description
 * @date 2020/5/2 10:09 AM
 */
public class ClassLoaderTest2 {
    public static void main(String[] args) throws ClassNotFoundException {
        //1
        ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
        System.out.println(classLoader); //null
        //2 通过线程获取该上下文的一个加载器，上下文在自定义的这个程序当中
        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader1); //sun.misc.Launcher$AppClassLoader@18b4aac2
        //3
        ClassLoader classLoader2 = ClassLoader.getSystemClassLoader().getParent();
        System.out.println(classLoader2);//sun.misc.Launcher$ExtClassLoader@610455d6
    }
}
