package top.p3wj.java1;

/**
 * @author Aaron
 * @description
 * @date 2020/4/30 11:22 PM
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        //获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        /**
         * sun.misc.Launcher$AppClassLoader@18b4aac2
         * 得到了这个结果值，是Launcher的内部类
         */
        //获取琦上层，扩展类加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader);
        /**
         * sun.misc.Launcher$ExtClassLoader@610455d6
         * 他们不是一个继承关系，只是上一级。类似于a/b/c/abc.txt
         */
        //获取不到引导类加载器
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader);
        /**
         * null
         */
        //用户自定义类来说:默认使用系统类加载器进行加载
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);
        /**
         * sun.misc.Launcher$AppClassLoader@18b4aac2
         */
        //String类使用引导累加载器进行加载的, --->Java的核心类库都是使用引导类加载器加载的.它是用C,C++编写的
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);
        /**
         * null
         */

    }
}
