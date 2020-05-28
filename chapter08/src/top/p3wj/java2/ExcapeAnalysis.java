package top.p3wj.java2;

/**
 * @author Aaron
 * @description 逃逸分析
 * 如何判断是否发生了逃逸分析，就看new的对象实体是否有可能在方法外被调用
 * @date 2020/5/28 12:13 AM
 */
public class ExcapeAnalysis {
    public ExcapeAnalysis obj;

    /**
     * 方法返回ExcapeAnalysis对象，发生逃逸
     */
    public ExcapeAnalysis getInstance() {
        return obj == null ? new ExcapeAnalysis() : obj;
    }

    /**
     * 为成员属性赋值，发生逃逸
     */
    public void setObj() {
        this.obj = new ExcapeAnalysis();
    }
    //思考：如果当前的obj引用声明为static的？仍然会发生逃逸

    /**
     * 对象的作用域仅在当前方法中有效，没有发生逃逸
     */
    public void useEscapeAnalysis() {
        ExcapeAnalysis e = new ExcapeAnalysis();
    }

    /**
     * ！！引用成员变量的值，发生逃逸。getInstance中new了一个，本身就是外面的
     */
    public void useEscapeAnalysis1() {
        ExcapeAnalysis e = getInstance();
        //getInstance().xxx()同样会发生逃逸
    }
}
