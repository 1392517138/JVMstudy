##第一章笔记
####此记录来源于尚硅谷JVM(宋红康)，在此记录以做分享
![image-20200429194556090](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429194556090.png)



#### JAVA代码执行流程

![image-20200429194831110](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429194831110.png)

每个字节码文件对于一个类

JAVA编译器（前端编译器）

编译器环节任何一个失败了，都不能生成字节码文件

![image-20200429194908948](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429194908948.png)

操作系统并不识别字节码指令，所以需要后端编译器，JIT解释为机器指令。由执行引擎完成。

**第一次编译**：把源文件编译成字节码文件

**第二次编译**：将字节码文件编译为机器指令。同时因为机器指令他是需要反复执行的，将热点代码缓存起来了（JIT），提高性能。所以<u>由翻译字节码、JIT编译器两部分组成</u>

![image-20200429195145051](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429195145051.png)

Java编译器输入的指令流基本上是一种基于**栈的指令集架构**，另外一种指令集架构则
是基于寄存器的指令集架构。
具体来说:这两种架构之间的区别:
●**基于栈式架构的特点**
➢设计和实现更简单，适用于资源受限的系统; .
➢避开了寄存器的分配难题:使用零地址指令方式分配。
即是一个栈的操作，我们只需要关心栈顶
➢指令流中的指令大部分是零地址指令，其执行过程依赖于操作栈。指令集更小, [但相比于寄存器操作更多，下图有解释].
编译器容易实现。
➢不需要硬件支持，可移植性更好，更好实现跨平台。
栈是一个内存层面，不跟硬件打交道
**基于寄存器架构的特点**
➢典型的应用是x86的二进制指令集:比如传统的PC以及Android的Davlik虛
拟机。
➢指令集架构则完全依赖硬件，可移植性差
➢性能优秀和执行更高效: 
因为基于cpu，比较快，对硬件耦合度较高
➢花费更少的指令去完成一项操作。
➢在大部分情况下，基于寄存器架构的指令集往往都以一地址指令、二地址指令
和三地址指令为主，而基于栈式架构的指令集却是以零地址指令为主。

![image-20200429201350985](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429201350985.png)

```
* 在out中反编译 javap -v
```

![image-20200429203538252](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429203538252.png)

![image-20200429203420941](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429203420941.png)
**总结:** 
**由于跨平台性的设计，Java的指令都是根据栈来设计的**。不同平台CPU架构不
同，所以不能设计为基于寄存器的。优点是跨平台， 指令集小，编译器容易
实现，缺点是性能下降，实现同样的功能需要更多的指令。
时至今日，尽管嵌入式平台已经不是Java程序的主流运行平台了(准确来说应.
该是HotSpotVM的宿主环境已经不局限于嵌入式平台了)，那么为什么不将
架构更换为基于寄存器的架构呢? 
栈:
跨平台性、指令集小、指令多;执行性能比寄存器差。
寄存器存在跟硬件的耦合，移植性差一些

JVM**生命周期**-三个状态

![image-20200429211035248](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429211035248.png)

```java
* jps 打印当前程序执行过程中对进程
```

![image-20200429224531656](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429224531656.png)

![image-20200429233622851](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200429233622851.png)

```
运行时环境区，单例的
public class Runtime {
    private static Runtime currentRuntime = new Runtime();
```

**针对于上方调用Runtime类或System类的exit方法，或Runtime类的halt方法贴出代码**

System类：

```
public static void exit(int status) {
    Runtime.getRuntime().exit(status);
}
```

```
public void exit(int status) {
    SecurityManager security = System.getSecurityManager();
    if (security != null) {
        security.checkExit(status);
    }
    Shutdown.exit(status);
}
```

```
class Shutdown {

    /* Shutdown state */
    private static final int RUNNING = 0;
    private static final int HOOKS = 1;
    private static final int FINALIZERS = 2;
    private static int state = RUNNING;
```

```
static void exit(int status) {
    boolean runMoreFinalizers = false;
    synchronized (lock) {
        if (status != 0) runFinalizersOnExit = false;
        switch (state) {
        case RUNNING:       /* Initiate shutdown */
            state = HOOKS;
            break;
        case HOOKS:         /* Stall and halt */
            break;
        case FINALIZERS:
            if (status != 0) {
                /* Halt immediately on nonzero status */
                halt(status);
            } else {
                /* Compatibility with old behavior:
                 * Run more finalizers and then halt
                 */
                runMoreFinalizers = runFinalizersOnExit;
            }
            break;
        }
    }
    if (runMoreFinalizers) {
        runAllFinalizers();
        halt(status);
    }
    synchronized (Shutdown.class) {
        /* Synchronize on the class object, causing any other thread
         * that attempts to initiate shutdown to stall indefinitely
         */
        sequence();
        halt(status);
    }
}
```

Runtime类：

```
public void halt(int status) {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
        sm.checkExit(status);
    }
    Shutdown.halt(status);
}
```

```
static void halt(int status) {
    synchronized (haltLock) {
        halt0(status);
    }
}

static native void halt0(int status);
```

**JVM发展历史**

![image-20200430001351315](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200430001351315.png)



