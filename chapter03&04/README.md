## chapter03&04运行时数据区

![image-20200502163249675](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502163249675.png)

前面学习了类加载子系统

![image-20200502163650874](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502163650874.png)

内存是非常重要的系统资源，是硬盘和CPU的中间仓库及桥梁，承载着操作系统和应用程序的实时运行。JVM内存布局规定了Java在运行过程中内存申请、分配、管理的策略，保证了JVM的高效稳定运行。**不同的JVM对于内存的划分方式和管理机制存在着部分差异**。结合JVM虚拟机规范，来探讨一下经典的JVM内存布局。

![image-20200502170234450](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502170234450.png)

**一个进程（一个虚拟机实例）对应一个红色区域**，**一个线程对应一个灰色区域**

![image-20200502212435376](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502212435376.png)

**一个栈帧对应一个方法**

程序计数器（pc寄存器）

![image-20200502212612548](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502212612548.png)



若多个线程都要用

优化主要针对于**堆空间**、**方法区**，虚拟机因只是出栈、入栈的结构，不需优化

95%集中在堆区，5%集中在方法区。

方法区在JDK8以后被换成了元空间，使用的是本地内存，一般情况不会出现内存溢出。

[JDK8之前之后的方法区]: https://blog.csdn.net/qq_41872909/article/details/87903370

一个JVM实例对应一个RunTime实例，对应一个运行时数据区，一个进程

![image-20200503000100924](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503000100924.png)

●线程是一个程序里的运行单元。JVM允许- 一个应用有多个线程并行的执行。
●在Hotspot JVM里， 每个线程都与操作系统的本地线程直接映射工
➢当一个Java线程准备好执行以后，此时一个操作系统的本地线程
也同时创建。Java线程执行终止后，本地线程也会回收。
●操作系统负责所有线程的安排调度到任何一一个可用的CPU上。一旦本地线程初始化成功，它就会调用Java线程中的run()方法。

●如果你使用jconsole或者是任何一个调武工具，都能看到在后台有许多线程
在运行。这些后台线程不包括调用public static void main (String[])的main线程以及所有这个main线程自己创建的线程。
●这些主要的后台系统线程在Hotspot JVM里 主要是以下几个:
➢虚拟机线程:这种线程的操作是需要JVM达到安全点才会出现。这些操作必须在不
同的线程中发生的原因是他们都需要JVM达到安全点，这样堆才不会变化。这种线
程的执行类型包括”stop- the-world"的垃圾收集，线程栈收集，线程挂起以及
偏向锁撤销。
➢周期任务线程:这种线程是时间周期事件的体现(比如中断)，他们一-般用于周期性
操作的调度执行。
➢GC线程:这种线程对在JVM里不同种类的垃圾收集行为提供了支持。
➢编译线程:这种线程在运行时会将字节码编译成到本地代码。
➢信号调度线程:这种线程接收信号并发送给JVM，在它内部通过调用适当的方法进
行处理。

**程序计数器（PC寄存器）**

JAVA8以后在垃圾回收方面有一些变化

[JDK]: oracle.com/technetwork/java/javase/downloads/index.html
[JDK8]: https://docs.oracle.com/javase/specs/ivms/se8/html/

![image-20200503171515736](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503171515736.png)

JVM中的程序计数寄存器(Program Counter Register) 中，Register 的命名源于
CPU的寄存器，寄存器存储指令相关的现场信息。CPU只有 把数据装载到寄存器才能够运行。
这里，并非是广义上所指的物理寄存器，或许将其翻译为PC计数器(或指令计数器)会更加贴切(也称为程序钩子)，并且也不容易引起一些不必要的误会。**JVM中的PC寄存器是对物理PC寄存器的一种抽象模拟。**

<img src="/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503172326244.png" alt="image-20200503172326244" style="zoom:50%;" />

●它是一块很小的内存空间，几乎可以忽略不记。也是运行速度最快的存储区域。
●在JVM规范中，每个线程都有它自己的程序计数器，是线程私有的，生命周期与线程的生命周期保持一致。
●任何时间一个线程都只有一个方法在执行，也就是所谓的**当前方法**。程序计数器会存储当前线程正在执行的Java方法的JVM指令地址;或者,如果是在执行native方法，则是未指定值(undefined) 。<!--因为这是一个JAVA层面的-->

●它是程序控制流的指示器，分支、循环、跳转、异常处理、线程恢复等基础
功能都需要依赖这个计数器来完成。
●字节码解释器工作时就是通过改变这个计数器的值来选取下--条需要执行的字节码指令。
●它是唯一个在Java虚拟机规范中没有规定任何OutOfMemoryError情况的区域。

![image-20200503191205710](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503191205710.png)

偏移地址，PC寄存器中存储的

![image-20200503191449228](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503191449228.png)

![image-20200503192547126](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503192547126.png)

**下面给出例子进行解释**

![image-20200503193108928](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503193108928.png)

![image-20200503193137937](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503193137937.png)

![image-20200503193203613](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503193203613.png)

![image-20200503193338527](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503193338527.png)

![image-20200503193244856](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503193244856.png)

![image-20200503193404486](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503193404486.png)

#### PC寄存器为什么会被设定为线程私有?

我们都知道所谓的多线程在一个特定的时间段内只会执行其中某-一个线程的方法，CPU会不停地做任务切换，这样必然导致经常中断或恢复，如何保证分毫无差呢?**为了能够准确地记录各个线程正在执行的当前商5码指令地址，最好的办法自然是为每一个线程都分配一个PC寄存器**，这样一来各个线程之间便可以进行独立计算，从而不会出现相互干扰的情况。

![image-20200503203634305](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503203634305.png)

<!--以上为单核-->

由于CPU时间片轮限制，众多线程在并发执行过程中，任何一个确定的时刻，一个处理器或者多核处理器中的一个内核，只会执行某个线程中的一条指令。

这样必然导致经常中断或恢复，如何保证分毫无差呢?每个线程在创建后，都会产生自己的程序计数器和栈帧，程序计数器在各个线程之间互不影响。

![image-20200503204207877](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503204207877.png)

**串行与并行**：

![image-20200503204649811](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200503204649811.png)

串行：线程排成队依次被cpu执行。同一个时间点只有一个线程执行

并行：多个线程同时执行

并发：一个核快速地去切换线程，让他们看着像是并行，其实是并发

**垃圾回收器中**【之后会讲到】：

串行：用户线程跟垃圾回收线程不能同时执行。垃圾回收线程只能有一条

并行：垃圾回收线程可以有多条，但这时候用户的线程就在一个停止的状态

并发：用户线程跟垃圾线程是可以同时进行的（不一定是并行的，也肯能是交替的）

