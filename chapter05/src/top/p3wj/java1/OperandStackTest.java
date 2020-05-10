package top.p3wj.java1;

/**
 * @author Aaron
 * @description
 * @date 2020/5/6 7:28 PM
 */
public class OperandStackTest {
    public void testAddOpearation() {
        byte i = 15;
        int j = 8;
        int k = i + j;
//         int m = 800;
    }

    public int getSum() {
        int m = 10;
        int n = 20;
        int k = m + n;
        return k;
    }

    public void testGetSum() {
        int i = getSum();
        int j = 10;
    }

    /**
     * 程序员面试中，常见的i++和++i的区别，放到字节码篇章时再介绍
     */
    public void add() {
        //第一类问题
        int i1 = 10;
        i1++;

        int i2 = 10;
        ++i2;

        //第二类问题
        int i3 = 10;
        int i4 = i3++;

        int i5 = 10;
        int i6 = ++i5;

        //第三类问题
        int i7 = 10;
        i7 = i7++;

        int i8 = 10;
        i8 = ++i8;

        //第四类问题，没有标准答案（跟编译器有关）
        int i9 = 10;
        int i10 = i9++ + ++i9;

    }
}
