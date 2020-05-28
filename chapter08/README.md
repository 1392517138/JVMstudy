## 08堆空间

#### JVM学习路线和内容回顾

![image-20200523203003162](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523203003162.png)

**![image-20200523204000784](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523204000784.png)**

**LV：本地变量表**

**OS：操作数栈**

**DL：动态链接**

**RA：返回地址**

#### 堆空间的概述——进程中的唯一性

![image-20200523204316746](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523204316746.png)

 **一个进程对应一个jvm实例，一个运行时数据区。一个进程中的多个线程共享同一个方法区、堆空间，各自拥有程序计数器、本地方法栈、虚拟机栈**

●一个JVM实例只存在一个堆内存，堆也是Java内存管 理的核心区域。
●Java 堆区在JVM启动的时候即被创建，其空间大小也就确定了。是JVM管理的最大一块内存空间。
➢堆内存的大小是可以调节的。
●《Java虚拟机规范》规定，堆可以处于**物理上不连续的内存空间中**，但在**逻辑上它应该被视为连续的**。（涉及到物理内存和虚拟内存）
●所有的线程共享Java堆，在这里还可以划分线程私有的缓冲区(ThreadLocal Allocation Buffer， TLAB) 。

1.准备：设置不同的堆空间大小

![image-20200523210916854](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523210916854.png)

![image-20200523210939487](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523210939487.png)

![image-20200523210958825](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523210958825.png)

2.分别跑起来,**接下来我们来看一下这两个进程**

之后在jdk1.8/contents/homt/bin目录下找到jvisualvm

![image-20200523211323483](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523211323483.png)

![image-20200523211426660](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523211426660.png)

若没有visual gc,则点击菜单中工具里的插件,此时就有了

![image-20200523212238752](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523212238752.png)

#### 堆空间关于对象创建和GC的概述

●《Java虚拟机规范》中对Java堆的描述是:所有的对象实例以及数组都应当在运行时分配在堆上。| (The heap is the run-time data area fromwhich memory for all class instances and arrays is allocated )
➢我要说的是:“几乎”所有的对象实例都在这里分配内存。从实际使用角度看的。
●数组和对象可能永远不会存储在栈上，因为栈帧中保存引用，这个引用指向对象或者数组在堆中的位置。
●在方法结束后，堆中的对象不会马上被移除，仅仅在垃圾收集的时候才会被移除。
●堆，是GC ( Garbage Collection, 垃圾收集器)执行垃圾回收的重点区域。

![image-20200523213852303](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523213852303.png)

对应这样的场景

![image-20200523213906307](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523213906307.png)

main方法结束后，s1,s2就被弹出栈，堆中的s1,s2实例就没有被引用了，当GC进行判断的时候，发现s1,s2没有被引用，就判断为垃圾.**如果变为栈中的一走，堆中的对象就被回收，那么GC的频率将特别高**

来看一下对象的创建：

![image-20200523214532877](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523214532877.png)

#### 堆的戏份内容结构

![image-20200523214652258](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523214652258.png)

![image-20200523214917451](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523214917451.png)

![image-20200523215640280](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523215640280.png)

![image-20200523215613959](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523215613959.png)

**加起来刚好10m,逻辑上是3部分，但是实际管辖的是这两部分：新生区、老年区**

![image-20200523215656382](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523215656382.png)

![image-20200523220204078](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523220204078.png)

![image-20200523220258808](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200523220258808.png)

#### 堆空间大小的设置和查看

●Java堆区用于存储Java对象实例，那么堆的大小在JVM启动时就已经设定好了，大家可以通过选项"-XmX"和"-Xms"来进行设置。
➢“-Xms"用于表示堆区的起始内存，等价于-XX: Ini tialHeapSize
➢“-Xmx"则用于表示堆区的最大内存，等价于-XX : MaxHeapSize
●一旦堆区中的内存大小超过“-Xmx"所指定的最大内存时，将会抛出OutOfMemoryError异常。
通常会将-Xms 和-Xmx两个参数配置相同的值，其**目的是为了能够在java垃圾回收机制清理完堆区后不需要重新分隔计算堆区的大小，从而提高性能。**
●默认情况下，初始内存大小:物理电脑内存大小/ 64 
						  最大内存大小:物理电脑内存大小/ 4

1.

![image-20200524094400141](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524094400141.png)

2.

![image-20200524094557146](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524094557146.png)

这里出现一个问题为初始与最大是一样的值

```java
开发者建议将出事堆内存和最大堆内存设置成相同堆值
```

因为在不断扩容和释放的过程中会对系统造成额外压力

![image-20200524094936162](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524094936162.png)

那么这个数怎么算出来的呢?我们先添加这一行，并打开命令行输入jps(查看当前程序进程)，jstat查看某进程在使用过程中内存使用情况

![image-20200524095019676](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524095019676.png)

![image-20200524095242021](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524095242021.png)

OC：总量	OU：你使用了多少

![image-20200524105828009](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524105828009.png)

![image-20200524095859867](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524095859867.png)

但是为什么运行时是575m呢

![image-20200524105754582](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524105754582.png)

3.另一种方式查看,并把Thread.sleep去掉

![image-20200524100434564](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524100434564.png)

![image-20200524103121521](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524103121521.png)

#### OOM的说明与举例

并设置参数-Xms600m	-Xmx600m

![image-20200524105038912](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524105038912.png)

![image-20200524105133836](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524105133836.png)

打开jvisualvm,再跑起来

![image-20200524105213428](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524105213428.png)

![image-20200524105239794](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524105239794.png)

查看抽样器，可查看原因，原因为byte[]太多

![image-20200524105351397](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524105351397.png)

#### 新生代与老年代中相关参数的设置

-  存储在JVM中的Java对象可以被划分为两类:
  ➢一类是生命周期较短的瞬时对象，这类对象的创建和消亡都非常迅速
  ➢另外一类对象的生命周期却非常长，在某些服端的情况下还能够与JVM的生命周期保持一致。
- Java堆区进一步细分的话， 可以划分为年轻代(YoungGen)和老年代(OldGen)
- 其中年轻代又可以划分为Eden空间、Survivor0空间和Survivor1空间 (有时也叫做from区、to区)。

![image-20200524105658826](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524105658826.png)

下面这参数开发中一般不会调：
![image-20200524110122166](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524110122166.png)

● 配置新生代与老年代在堆结构的占比。
➢默认-XX:NewRatio=2，表示新生代占1，老年代占2，新生代占整个堆的1/3
➢可以修改-XX:NewRatio=4，表示新生代占1，老年代占4，新生代占整个堆的1/5

1.方式一

![image-20200524110354979](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524110354979.png)

![image-20200524110501737](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524110501737.png)

默认比例1:2

如果某些对象生命周期长的较多，则可以考虑把老年代调得大一些

2.方式二

通过jinfo -flag 

![image-20200524110937645](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524110937645.png)

- 在HotSpot中，Eden 空间和另外两个Survivor空间缺省所占的比例是8:1:1
-  当然开发人员可以通过选项“-XX:SurvivorRatio”调整这个空间比例。比如-XX: SurvivorRatio=8
- **几乎所有**的Java对象都是在Eden区被new出来的。
- 绝大部分的Java对象的销毁都在新生代进行了。
  ➢IBM公司的专门研究表明，新生代中80%的对象都是“朝生夕死”的。
- 可以使用选项"-Xmn"设置新生代最大内存大小
  ➢这个参数一般使用默认值就可以了。

**那么是8:1:1吗**

那么理论新生代是200m,则eden为160m，打开jvisualvm看一下

![image-20200524111531405](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524111531405.png)

我们发现变成6:1:1了，我们从命令行看一下

![image-20200524111713713](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524111713713.png)

还是6:1:1

这是因为其中还存在一个自适应的机制,默认想关闭可使用：这个'-'

```java
-XX:-UseAdaptiveSizePolicy：关闭自适应的内存分配策略（暂时用不到）
```

![image-20200524111949573](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524111949573.png)

![image-20200524112115834](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524112115834.png)

但发现还是6:1:1

这时候就要用到-XX:SurvivorRatio

![image-20200524112215387](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524112215387.png)

![image-20200524112228284](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524112228284.png)

**这样就成功了**

如果new一个对象非常大，Eden区放不下了，这个时候就放入老年代

![image-20200524112607343](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524112607343.png)

![image-20200524112851080](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524112851080.png)

#### 图解对象分配的一般过程

1.为新对象分配内存是一件非常严谨和复杂的任务，JVM的设计者们不仅需要考虑内存如何分配、在哪里分配等问题，并且由于内存分配算法与内存回收算法密切相关，所以还需要考虑GC执行完内存回收后是否会在内存空间中产生内存碎片。

2.new的对象先放伊甸园区。此区有大小限制。

3.当伊甸园的空间填满时，程序又需要创建对象，JVM的垃圾回收器将对伊甸园区进行垃圾回收(Minor GC)， 将伊甸园区中的不再被其他对象所引用的对象进行销毁。再加载新的对象放到伊甸园区

4.然后将伊甸园中的剩余对象移动到幸存者0区。

5.如果再次触发垃圾回收，此时上次幸存下来的放到幸存者0区的，如果没有回收，就会.
放到幸存者1区。

6.如果再次经历垃圾回收，此时会重新放回幸存者0区，接着再去幸存者1区。

**啥时候能去养老区呢?可以设置次数。默认是15次。**
**● 可以设置参数:**

```java
 -XX:MaxTenuringThreshold=<N>
```

**进行设置。**

7.在养老区，相对悠闲。当养老区内存不足时，再次触发GC: Major GC，进行养老区的内
存清理。

8.若养老区执行了Major GC之 后发现依然无法进行对象的保存，**就会产生OOM异常java.lang. OutOfMemoryError: Java heap space**

1.

![image-20200524143438116](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524143438116.png)

Eden区放着放满了（此时用户线程停止，触发GC【判断哪些是垃圾，红色的是垃圾，然后释放】）就往S0或S1【幸存者区】放。可以看到是一，我们为每个对象分配了一个年龄计数器。此时Eden区里面就没有数据了，被清空了。

2.

此时又放：

![image-20200524143922880](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524143922880.png)

伊甸园区满了，又进行一次Minor GC，是放在S0还是S1呢？是放在S1当中。进入1之后，S1为空的，就称为to【表示空】。**当放入进S1，即这个1标注1号的柱子，此时S0中的1号柱子也要进行判断，当发现这俩还被占用，还不能被销毁，就把S0区的放入S1区并增长2。此时S1就是from区，S0空了就是to区，以此类推。**

3.

![image-20200524143454989](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524143454989.png)

走着走着就进入特殊情况：
Eden又满了，就继续放入S0，S1进行判断，1号还用就放入S0。我们发现S1中有两个已经达到15了，就晋升至老年代，放入Old,此时就不进行判断它了。**只有当有柱子再进入老年代的时候才进行判断。15称为阈值（临界值，默认的）**

Eden区满出发YGC，S区满了不会触发。Eden触发GC的时候会将Eden与S区（属于被动）一起进行垃圾回收。

当S区满了，没有达到15的情况下，有可能进入Old，也有可能从Eden直接到达Old

**总结**：

- **针对幸存者s0,s1区的总结:复制之后有交换，谁空谁是to.**
- **关于垃圾回收:频繁在新生区收集，很少在养老区收集，几乎不在永久区/元空间收集。**

#### 对象分配的特殊情况

![image-20200524145910809](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524145910809.png)

EdenGC后此时Eden是空的，假如Eden是10m，要放一个12m的对象，可直接放入老年代，则在老年代中分配内存空间。

如果老年代放不下，eg：老年代有20m，但是使用了10m，还剩10m放不下12m，此时进行FGC/major GC。若回收以后，还是不够放12m（或是老年代本身就是10m不够），此时就是OOM【如果不让他自动调整内存空间的话】。

若从Eden过来的放到S区发现放不下，则直接进入老年代。

#### 代码举例与JVisualVm演示对象分配过程

我们来模拟这个过程：
1.

![image-20200524153209187](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524153209187.png)

2.

![image-20200524153217281](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524153217281.png)

进入YGC,放入S1

3.

![image-20200524153234816](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524153234816.png)

第二次YGC，从S1放入S0,S1变to区

4.

![image-20200524153251994](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524153251994.png)

继续上面的步骤

5.

![image-20200524153505398](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524153505398.png)

**最终Old满了，抛出OOM，因为我们始终放在ArrayList中，没有垃圾**

**可以看见在Eden峰底底时候执行了一次GC。执行完一次gc后，Eden区再不断地去增长**

MetaSpace就一直处于类数据的加载，比较平稳，没有去加载一些额外的类。

#### 常用优化工具概述与Jprofiler的演示

常用的调优工具

- JDK命令行
- Eclipse : Memory Analyzer Tool
- Jconsole
- VisualVM
- Jprofiler
- Java Flight Recorder
- GCViewer
- GC Easy

![image-20200524170539641](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200524170539641.png)

#### Minor Gc、Major GC和Full GC的对比

JVM在进行GC时，并非每次都对上面三个内存区域(新生代、老年代；方法区)一起回收的，大部分时候回收的都是指新生代（80%）。
针对HotSpot VM的实现，它里面的GC按照回收区域又分为两大种类型:一种是部分收集(Partial GC)，一种是整堆收集(Full GC)
**部分收集:不是完整收集整个Java堆的垃圾收集。其中又分为:**
➢新生代收集(Minor GC / Young GC) :只是新生代(Eden/S0,S1)的垃圾收集
➢老年代收集(Major GC / Old GC) :只是老年代的垃圾收集。
		1.目前，只有CMS GC会有单独收集老年代的行为。
		2.**注意，很多时候Major GC会和Full GC混淆使用，需要具体分辨是老年代**
			**回收还是整堆回收**。
➢混合收集(Mixed GC): 收集整个新生代以及部分老年代的垃圾收集。
		1.目前，只有G1 GC会有这种行为
**整堆收集(Ful1 GC):收集整个java堆和方法区的垃圾收集。**

我们实际上希望出现GC情况少一些。GC也是垃圾回收的线程来做的，对应的另一个线程是用户线程（我们真正要执行代码所用到的线程）。GC的线程在判断哪些是垃圾的时候，**会让用户线程做一个暂停**，用户线程一暂停，程序执行的一个吞吐量就会差一些。减少GC就减少了这个STW的频率，用户就会被较少的干预到。**重点关注Major GC和Full GC，因为他们两个所产生的暂停时间比Minor GC长**

**最简单的分代GC策略的触发条件：**

●年轻代GC(Minor GC )触发机制:
➢当年轻代空间不足时，就会触发Minor GC， 这里的年轻代满指的是Eden代满，Survivor满不会引发GC。( 每次Minor GC会清理年轻代的内存。)
➢因为Java **对象大多都具备朝生夕灭的特性**，所以Minor GC非常频繁，一般回收速度也比较快。这一定义既清晰又易于理解。
➢Minor GC会引发STW， 暂停其它用户的线程，等垃圾回收结束，用户线
程才恢复运行。

![image-20200527123619636](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527123619636.png)

**老年代GC (Major GC/Fu1l GC)触发机制:**
➢指发生在老年代的GC，对象从老年代消失时，我们说“Major GC”或“Full GC”
发生了。
➢出现了Major GC，经常会伴随至少一次的Minor GC (但非绝对的，Parallel
Scavenge收集器的收集策略里就有直接)。
	1.也就是在老年代空间不足时，会先尝试进行Major GC的策略选择过程触发		Minor GC。如果之后空间还不足，则触发Major GC
➢Major GC的速度一般会比Minor GC慢10倍以上，STW的时间更长。
➢如果Major GC后，内存还不足，就报OOM了。

●Ful l GC触发机制: **(后面细讲)**
触发Full GC执行的情况有如下五种:
(1)调用System.gc()时，系统建议执行Full GC，但是不必然执行
(2)老年代空间不足
(3)方法区空间不足
(4)通过Minor GC后进入老年代的平均大小大于老年代的可用内存.
(5)由Eden区、survivor space0 ( From Space) 区向survivor space1 (To Space)区复制时，对象大小大于To Space可用内存，则把该对象转存到老年代，且老年代的可用内存小于该对象大小
说明: **full gc是开发或调优中尽量要避免的。这样暂时时间会短一些。**



#### GC举例与日志分析

![image-20200527154345824](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527154345824.png)

![image-20200527154358889](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527154358889.png)

字符串常量池存在堆空间的，（以前在方法区）

不断添加，导致了OOM

![image-20200527154517442](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527154517442.png)

GC年轻代，FullGC（老年代+整个堆区、方法区）

出现OOM一定会经历Full GC，

1.

![image-20200527155034072](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527155034072.png)

2.

![image-20200527155904978](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527155904978.png)

3.

![image-20200527160910583](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527160910583.png)



#### 堆空间分代思想

**为什么需要把Java堆分代?不分代就不能正常工作了吗?**
●经研究，不同对象的生命周期不同。70%-99%的对象是临时对象。
➢新生代:有Eden、两块大小相同的Survivor(又称为from/to， s0/s1)构成，to总为空。
➢老年代:存放新生代中经历多次GC仍然存活的对象。

![image-20200527161159646](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527161159646.png)

**为什么需要把Java堆分代?不分代就不能正常工作了吗?**
●其实不分代完全可以，分代的唯一理由就是**优化GC性能**。如果没有分代，那所有的对象都在一块，就如同把一个学校的人都关在一个教室。GC的时 候要找到哪些对象没用，这样就会对堆的所有区域进行扫描。而很多对象都是朝生夕死的，如果分代的话，把新创建的对象放到某一地方，当GC的时候先把这块存储“朝生夕死”对象的区域进行回收，这样就会腾出很大的空间出来。

![image-20200527161311571](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527161311571.png)

#### 内存分配策略(或对象提升（Promotion）规则)

如果对象在Eden出生并经过第一次MinorGC后仍然存活，并且能被Survivor
容纳的话，将被移动到Survivor空间中，并将对象年龄设为1。对象在Survivor区中每熬过一次MinorGC，年龄就增加1 岁，当它的年龄增加到一定程度(默认为15岁，其实每个JVM、每个GC都有所不同)时，就会被晋升到老年代中。
对象晋升老年代的年龄阈值，可以通过选项**-XX: MaxTenuringThreshold**来设置。

针对不同年龄段的对象分配原则如下所示:
●优先分配到Eden
●大对象直接分配到老年代
➢尽量避免程序中出现过多的大对象
●长期存活的对象分配到老年代
●动态对象年龄判断
➢如果Survivor 区中相同年龄的所有对象大小的总和大于Survivor空间的一半，年龄大于或等于该年龄的对象可以直接进入老年代，无须等到MaxTenuringThreshold中要求的年龄。
●空间分配担保
➢-XX: HandlePromotionFailure

![image-20200527164220614](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527164220614.png)

#### 为对象分配内存：TLAB

**为什么有TLAB ( Thread Local Allocation Buffer ) ?**
●堆区是线程共享区域，任何线程都可以访问到堆区中的共享数据
●由于对象实例的创建在JVM中非常频繁，因此在并发环境下从堆区中划分内
存空间是线程不安全的
●为避免多个线程操作同一地址，需要使用加锁等机制，进而影响分配速度。

**什么是TLAB ?**
●从内存模型而不是垃圾收集的角度，对Eden区域继续进行划分，JVM为
**每个线程分配了一个私有缓存区域**，它包含在Eden空间内。
●多线程同时分配内存时，使用TLAB可以避免一系列的非线程安全问题，同时还能够提升内存分配的吞吐量，因此我们可以将这种内存分配方式称之为**快速分配策略**。
●据说所有OpenJDK衍生出来的JVM都提供了TLAB的设计。

![image-20200527164854508](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527164854508.png)

每个线程有一份，使用完了再用公共的

**TLAB的再说明:**
●尽管不是所有的对象实例都能够在TLAB中成功分配内存，**但JVM确实是TLAB作为内存分配的首选。**

在程序中，开发人员可以通过选项“-XX:UseTLAB”设置是否开启TLAB空间。
●默认情况下，TLAB空间的内存非常小，**仅占有整个Eden空间的1%**，当然我们可以通过选项“-XX:TLABWasteTargetPercent”设置TLAB空间所占用Eden空间的百分比大小。
●一旦对象在TLAB空间分配内存失败时，JVM就会尝试着通过**使用加锁机制**确保数据操作的原子性，从而直接在Eden空间中分配内存。

![image-20200527170603051](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527170603051.png)

默认是开启的

![image-20200527170706159](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527170706159.png)

#### 小结堆空间的参数设置
[官网说明](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html)

- -XX:+PrintFlagsInitial :查看所有的参数的默认初始值
- -XX: +PrintFlagsFinal : 查看所有的参数的最终值(可能会存在修改,
  不再是初始值)
-  -Xms: 初始堆空间内存 (默认为物理内存的1/64)
- -Xmx: 最大堆空间内存(默认为物理内存的1/4)
- -Xmn: 设置新生代的大小。(初始值及最大值) 
- -XX: NewRatio:配置新生代与老年代在堆结构的占比

- -XX: SurvivorRatio:设置新生代中Eden和S0/S1空间的比例

- -XX: MaxTenuri ngThreshold:设置新生代垃圾的最大年龄
- -XX: +PrintGCDetails:输出详细的GC处理日志
  ➢打印gc简要信息:  ①-XX: +PrintGC.    ②-verbose:gc
- -XX: HandlePromotionFalilure:是否设置空间分配担保

```java
/**
 * @author Aaron
 * @description-
 * 测试堆空间常用的JVM参数：
 * -XX:+PrintFlagsInitial :查看所有的参数的默认初始值
 * -XX:+PrintFlagsFinal : 查看所有的参数的最终值(可能会存在修改,不再是初始值)
 *              具体查看某个参数的指令：jps：查看当前运行中的进程
 *                                  jinfo -flag SurvivorRatio 进程id
 * -Xms: 初始堆空间内存 (默认为物理内存的1/64)
 * -Xmx: 最大堆空间内存(默认为物理内存的1/4)
 * -Xmn: 设置新生代的大小。(初始值及最大值)
 * -XX:NewRatio: 配置新生代与老年代在堆结构的占比
 * -XX:SurvivorRatio:设置新生代中Eden和S0/S1空间的比例
 * -XX:MaxTenuri ngThreshold:设置新生代垃圾的最大年龄
 * -XX:+PrintGCDetails:输出详细的GC处理日志
 *   ➢打印gc简要信息:  ①-XX: +PrintGC.    ②-verbose:gc
 * -XX:HandlePromotionFalilure:是否设置空间分配担保
 *   配置新生代与老年代在堆结构的占比
 * @date 2020/5/27 5:13 PM
 */
public class HeapArgsTest {
    public static void main(String[] args) {

    }
}
```

测试程序

-----

1.PrintFlagInital

![image-20200527212718272](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527212718272.png)

![image-20200527212802046](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527212802046.png)

2.PrintFlagFinal

![image-20200527213044211](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527213044211.png)

![image-20200527213134281](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527213134281.png)

":="代表重新做的赋值

3.注意若SurvivorRatio设置Eden区过大，导致S区过小的话，理想状态下GC过程中，Eden都被回收了，没进入S区。不过不是理想情况下就会被放到Old区，导致minor GC失去意义。

- 在发生Minor GC之 前，**虚拟机会检查老年代最大可用的连续空间是否大于新生代所有对象的总空间。**
  - 如果大于，则此次Minor GC是安 全的
  - 如果小于，则虚拟机会查看-XX: HandlePromotionFai lure设置值是否允许担保失败。
    ➢如果HandlePromotipnFailure=true，**那么会继续检查老年代最大可**
    **用连续空间是否大于历次晋升到老年代的对象的平均大小。**
    		1.如果大于，则尝试进行一次Minor GC， 但这次Minor GC依			然是有风险的;
    		2.如果小于，则改为进行- -次Full GC。
    		3.如果HandlePromotionFailure=false， 则改为进行一次Full 			GC。
  - 在JDK6 Update24之 后，HandlePromot ionFailure参数不会再影响到虚拟机的空
    间分配担保策略，观察OpenJDK中 的源码变化，虽然源码中还定义了
    HandlePromot ionFailure参数，但是在代码中已经不会再使用它。JDK6 Update24之后的规则变为**只要老年代的连续空间大于新生代对象总大小或者历次晋升的平均大小就会进行Minor GC，否则将进行Full GC。也就是默认为true**

#### 堆是分配对象存储的唯一选择吗？

在《深入理解Java虚拟机》中关于Java堆内存有这样一段描述:
随着JIT编译期的发展与**逃逸分析技术**逐渐成熟，**栈上分配、标量替换优化技术**将会导致一些微妙的变化，所有的对象都分配到堆上也渐渐变得不那么“对”了。

在Java虛拟机中，对象是在Java堆中分配内存的，这是一个普遍的常识。但是，有一种特殊情况，那就是**如果经过逃逸分析(Escape Analysis) 后发现，一个对象并没有逃逸出方法的话，那么就可能被优化成栈上分配。**这样就无需在堆上分配内存，也无须进行垃圾回收了。这也是最常见的堆外存储技术。

此外，前面提到的基于openJDK深度定制的TaoBaoVM，其中创新的GCIH (GC
invisible heap) 技术实现off-heap，将生命周期较长的Java对象从heap中移至
heap外，并且GC不能管理GCIH内部的Java对象，以此达到降低Gc的回收频率和提升GC的回收效率的目的。



- 如何将堆上的对象分配到栈，需要使用逃逸分析手段。
- 这是一种可以有效减少Java程序中同步负载和内存堆分配压力的跨函数
  全局数据流分析算法。
- 通过逃逸分析，JavaHotspot编译器能够分析出一个新的对象的引用的
  使用范围从而决定是否要将这个对象分配到堆上。
- 逃逸分析的基本行为就是分析对象动态作用域:
  ➢当一个对象在方法中被定义后，对象只在方法内部使用，则认为没有
  发生逃逸。
  ➢当一个对象在方法中被定义后，它被外部方法所引用，则认为发生逃
  逸。例如作为调用参数传递到其他地方中。

![image-20200527221831042](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527221831042.png)

new 的这个V对象没有发生逃逸，就放在栈上

![image-20200527222004329](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200527222004329.png)

绿色方框中的代码因为StringBuffer()被返回了，跳出了该方法作用于，产生了逃逸，不能在栈中分配

**参数设置:**
●在JDK 6u23版本之后，HotSpot中默认就已经开启了逃逸分析。
如果使用的是较早的版本，开发人员则可以通过:
➢选项“-XX: +DoEscapeAnalysis" 显式开启逃逸分析
➢通过选项“-XX: +PrintEscapeAnalysis"查看逃逸分析的筛选结果

**结论**

**开发中能使用局部变量的，就不要使用在方法外定义。**

#### 代码优化之栈上分配

使用逃逸分析，编译器可以对代码做如下优化:
**一、栈上分配。**将堆分配转化为栈分配。如果一个对象在子程序中被分配，要使指向该对象的指针永远不会逃逸，对象可能是栈分配的候选，而不是堆分配。
**二、同步省略。**如果一个对象被发现只能从一个线程被访问到，那么对于这个对象的操作可以不考虑同步。
**三、分离对象或标量替换。**有的对象可能不需要作为一-个连续的内存结构存在也可以被访问到，那么对象的部分(或全部)可以不存储在内存，而是存储在CPU寄存器中。

**一、栈上分配**

●JIT编译器在编译期间根据逃逸分析的结果，发现如果一一个对象并没有逃逸出方法的话，就可能被|优化成栈上分配。分配完成后，继续在调用栈内执行，最后线程结束，栈空间被回收，局部变量对象也被回收。这样就无须进行垃圾回收了。
**●常见的栈上分配的场景**
➢在逃逸分析中，已经说明了。分别是给成员变量赋值、方法返回值、实例引用传递。

没有开启逃逸分析，则所有对象都是在堆中去分配空间

1.

![image-20200528131508123](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528131508123.png)

![image-20200528131519506](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528131519506.png)

打开jvisualvm

![image-20200528132354514](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528132354514.png)

![image-20200528132027310](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528132027310.png)

现在将'-'改为'+'

![image-20200528132057352](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528132057352.png)

![image-20200528132331881](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528132331881.png)

执行速度更快一些

2.将堆空间大小变小，此时会进行GC。我们再在没有开启逃逸分析的情况下：

![image-20200528132832045](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528132832045.png)

2.1

![image-20200528132807467](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528132807467.png)

执行了GC

2.2 现开启逃逸分析

![image-20200528132857990](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528132857990.png)

![image-20200528132921701](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528132921701.png)

发现不仅时间变短了，也根本没有发生GC。因为栈中没有GC

**二、同步省略**

●线程同步的代价是相当高的，同步的后果是降低并发性和性能。
●在动态编译同步块的时候，JIT编译器可以借助逃逸分析来**判断同步块所使用的锁对象是否只能够被一个线程访问而没有被发布到其他线程。**如果没有，那么JIT编译器在编译这个同步块的时候就会取消对这部分代码的同步。这样就能大大提高并发性和性能。这个取消同步的过程就叫同步省略，也**叫锁消除。**

![image-20200528133246945](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528133246945.png)

![image-20200528134535753](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528134535753.png)

字节码中仍然有，但是运行的时候会去掉

**三、分离对象或标量替换**

![image-20200528134718963](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528134718963.png)

![image-20200528134905654](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528134905654.png)
**标量替换参数设置:**
参数-XX: +EliminateAllocations:开启了标量替换(默认打开)，允许将对象打散分配在栈上。

1.先关闭

![image-20200528135425822](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528135425822.png)

![image-20200528135744469](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528135744469.png)

![image-20200528135554838](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528135554838.png)

28ms,进行了一些GC

2.开启后

![image-20200528135707976](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528135707976.png)

明显降低，也没有GC

#### 代码优化及堆堆小结

![image-20200528140017034](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528140017034.png)

![image-20200528140117076](https://cdn.jsdelivr.net/gh/1392517138/imgRepository@master/image-20200528140117076.png)

- 关于逃逸分析的论文在1999年就已经发表了，但直到JDK 1.6才有实现，而且这项技术到如今也并不是十分成熟的。
- 其根本原因就是**无法保证逃逸分析的性能消耗一定能高于他的消耗。虽然经过逃逸分析可以做标量替换、栈上分配、和锁消除。但是逃逸分析自身也是需要进行一系列复杂的分析的，这其实也是一个相对耗时的过程。**
- 一个极端的例子，就是经过逃逸分析之后，发现没有一个对象是不逃逸的。那这个逃逸分析的过程就白白浪费掉了。
- 虽然这项技术并不十分成熟，但是它也是**即时编译器优化技术中一个十分重要的手段。**
- 注意到有一些观点，认为通过逃逸分析，JVM会在栈上分配那些不会逃逸的对象，这在理论上是可行的，但是取决于JVM设计者的选择。据所知，Oracle Hotspot JVM中并未这么做，这一点在逃逸分析相关的文档里已经说明，所以可以明确所有的对象实例都是创建在堆上。
- 目前很多书籍还是基于JDK 7以前的版本，JDK已经发生了很大变化，intern字符串的缓存和静态变量曾经都被分配在永久代上，而永久代已经被元数据区取代。但是，intern字符串缓存和静态变量并不是被转移到元数据区，而是直接在堆上分配，所以这一点同样符合前面一点的结论:对象实例都是分配在堆上。

**小结**

- 年轻代是对象的诞生、成长、消亡的区域，一个对象在这里产生、应用，
  最后被垃圾回收器收集、结束生命。
- 老年代放置长生命周期的对象，通常都是从Survivor区域筛选拷贝过来的
  Java对象。当然，也有特殊情况，我们知道普通的对象会被分配在TLAB上;
  如果对象较大，JVM会 试图直接分配在Eden其他位置上;如果对象太大，完全无法在新生代找到足够长的连续空闲空间，JVM就会直接分配到老年代。
- 当GC只发生在年轻代中，回收年轻代对象的行为被称为MinorGC。当GC
  发生在老年代时则被称为MajorGC或者FullGC。 一般的，MinorGC的发生频率要比MajorGC高很多，即老年代中垃圾回收发生的频率将大大低于年轻代。

