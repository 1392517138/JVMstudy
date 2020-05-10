package top.p3wj.java2;

/**
 * @author Aaron
 * @description
 * @date 2020/5/8 10:55 AM
 */
class Father {
    public Father() {
        System.out.println("father的构造器");
    }

    public static void showStatic(String str) {
        System.out.println("father " + str);
    }

    public final void showFinal() {
        System.out.println("father show final");
    }

    public void showCommon() {
        System.out.println("father 普通方法");
    }

}

public class Son extends Father {
    public Son() {
        //invokespecial
        super();
    }

    public Son(int age) {
        //invokespecial
        this();
    }

    //不是重写的父类的静态方法，因为静态方法不能被重写
    public static void showStatic(String str) {
        System.out.println("son " + str);
    }

    public void showPrivate(String str) {
        System.out.println("son private " + str);
    }

    public void show() {
        //invokestatic
        showStatic("p3wj.top");
        //invokestatic
        Father.showStatic("good!");
        //invokespecial
        showPrivate("hello!");
        //invokespecial
        super.showCommon();
        //虚方法：编译期间无法确定下来的
        //invokevirtual,虽然是这个但是被final修饰他不是一个虚方法
        showFinal();

        //invokespecial，加上super,显示地表示是一个父类地方法
        super.showFinal();
        //invokevirtural,因为有可能该子类会重写这方法，如果加上super就是invokespecial
        showCommon();
        info();

        MethodInterface in = null;
        //invokeinterface
        in.methodA();

    }

    public void info() {

    }

    public void display(Father f) {
        f.showCommon();
    }

    public static void main(String[] args) {
        Son so = new Son();
        so.show();
    }
}

interface MethodInterface {
    void methodA();
}
