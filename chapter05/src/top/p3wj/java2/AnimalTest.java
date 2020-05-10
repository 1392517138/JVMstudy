package top.p3wj.java2;

import com.sun.org.apache.xpath.internal.operations.String;

/**
 * @author Aaron
 * @description 说明早期绑定和晚期绑定的例子
 * @date 2020/5/8 9:51 AM
 */
class Animal {
    public void eat() {
        System.out.println("动物进食");
    }
}

interface Huntable {
    void hunt();
}

class Dog extends Animal implements Huntable {
    @Override
    public void eat() {
        System.out.println("狗吃骨头");
    }

    @Override
    public void hunt() {
        System.out.println("捕食耗子，多管闲事");
    }
}

class Cat extends Animal implements Huntable {
    public Cat() {
        super();
    }

    public Cat(String name) {
        this();//调用当前类的构造器，早期绑定
    }

    @Override
    public void eat() {
        super.eat();
        System.out.println("猫吃鱼");
    }

    @Override
    public void hunt() {
        System.out.println("捕食耗子，天经地义");
    }
}

public class AnimalTest {
    /**
     * 在编译期间确定不下来
     */
    public void showAnimal(Animal animal) {
        animal.eat();//表现为：晚期绑定。不知道是哪个吃
    }

    public void showHunt(Huntable h) {
        h.hunt();//表现为：晚期绑定。因为接口本身就不可被实例化
    }
}
