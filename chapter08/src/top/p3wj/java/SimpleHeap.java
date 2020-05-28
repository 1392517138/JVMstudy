package top.p3wj.java;

/**
 * @author Aaron
 * @description
 * @date 2020/5/23 9:36 PM
 */
public class SimpleHeap {
    private int id;

    public SimpleHeap(int id) {
        this.id = id;
    }

    public void show() {
        System.out.println("My ID is " + id);
    }

    public static void main(String[] args) {
        SimpleHeap s1 = new SimpleHeap(1);
        SimpleHeap s2 = new SimpleHeap(2);
        int[] ints = new int[10];
        Object[] objects = new Object[10];

    }
}
