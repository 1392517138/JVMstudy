package top.p3wj.java1;

import java.util.Date;

/**
 * @author Aaron
 * @description
 * @date 2020/5/4 5:38 PM
 */
public class LocalVariablesTest {
    private int count = 0;

    public static void main(String[] args) {
        LocalVariablesTest test = new LocalVariablesTest();
        int num = 10;
        test.test1();
    }

    //关于Slot的使用理解
    public LocalVariablesTest() {
        this.count = 1;
    }

    //练习
    public static void testStatic() {
        LocalVariablesTest localVariablesTest = new LocalVariablesTest();
        Date date = new Date();
        int count = 10;
        System.out.println(count);
        //因为this不存在与当前局部变量表中
//        System.out.println(this.count);
    }

    public void test1() {
        Date date = new Date();
        String name1 = "p3wj.top";
        String info = test2(date, name1);
        System.out.println(date + name1);
    }

    public String test2(Date dateP, String name2) {
        dateP = null;
        name2 = "Aaron";
        double weight = 130.5; //2个Slot
        char gender = '男';
        return dateP + name2;
    }

    public void test3() {
        count++;
    }

    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        //变量c使用之前已经销毁的变量b占据的Slot位置
        int c = a + 1;
    }

    /**
     *变量的分类：
     *
     * 按照数据类型分：1、基本数据类型	2、引用数据类型
     *
     * 按照在类中声明的位置分：
     *
     * 1、成员变量：在使用前，都经历过默认初始化赋值
     *
     * 1.1类变量：linking的prepare阶段：给类变量默认赋值 ---> initial阶段：给类变量显式赋值即静态代码块赋值
     *
     * 1.2实例变量：随着对象的创建、会在空间中分配实例变量空间，并进行默认赋值
     *
     * 2、局部变量：在使用前，必须要进行显式赋值，否则编译不通过
     */
//    public void test5Temp(){
//        int num;
//        System.out.println(num);
//    }
}
