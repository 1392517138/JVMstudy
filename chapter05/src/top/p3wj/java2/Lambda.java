package top.p3wj.java2;

/**
 * @author Aaron
 * @description
 * @date 2020/5/8 4:51 PM
 */
//有无都可以[只要是只有一个抽象方法]
@FunctionalInterface
interface Func {
    public boolean func(String str);
}

public class Lambda {
    public void lambda(Func func) {
        return;
    }

    public static void main(String[] args) {
        Lambda lambda = new Lambda();
        Func func = s -> {
            return true;
        };
        lambda.lambda(func);
        lambda.lambda(str -> {
            return true;
        });
    }
}
