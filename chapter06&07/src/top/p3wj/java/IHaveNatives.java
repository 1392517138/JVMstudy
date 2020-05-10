package top.p3wj.java;

/**
 * @author Aaron
 * @description
 * @date 2020/5/10 3:25 PM
 */
public class IHaveNatives {
    public native void Native1(int x);

    native static public long Native2();

    native synchronized private float Native3(Object o);

    native void Native4(int[] art) throws Exception;
}
