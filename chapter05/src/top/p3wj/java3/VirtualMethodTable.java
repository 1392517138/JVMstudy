package top.p3wj.java3;

/**
 * @author Aaron
 * @description
 * @date 2020/5/8 5:26 PM
 */
interface Friendly {
    void sayHello();

    void sayGoodbye();
}

public class VirtualMethodTable {

}

class Dog {
    public void sayHello() {

    }

    @Override
    public String toString() {
        return "Dog";
    }
}

class Cat implements Friendly {
    public void eat() {

    }

    @Override
    public void sayHello() {

    }

    @Override
    public void sayGoodbye() {

    }

    @Override
    protected void finalize() {

    }

    @Override
    public String toString() {
        return "Cat";
    }
}

class CockerSpaniel extends Dog implements Friendly {
    @Override
    public void sayHello() {
        super.sayHello();
    }

    @Override
    public void sayGoodbye() {

    }
}

