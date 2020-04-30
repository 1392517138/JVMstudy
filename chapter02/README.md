## 02类加载子系统

![image-20200430152256052](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430152256052.png)

如果自己手写一一个Java虚拟机的话，主要考虑哪些结构呢?
**类加载器**  和  **执行引擎**

#### 类加载器与类的加载过程

![image-20200430152521659](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430152521659.png)

对于第三点常量池

常量池中有具体的符号引用

在运行时加载到内存里，就叫做运行时常量池

![image-20200430153242226](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430153242226.png)

![image-20200430153339552](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430153339552.png)

1. 编译后到Car class文件放在硬盘上
2. 物理磁盘中的文件通过二进制流的方式加载到内存
3. 通过类加载器存放在方法区中（即DNA元数据模版）
4. 通过getClassLoader()获取是谁加载的这个类（即获得了这个类的类加载器）
5. 在内存当中调用这个Car 这个class的类构造器就可以在堆中创建几个对象
6. 针对具体的对象也可调用getClass可以获取类本身（即你是由哪个类所创建的对象）



![image-20200430154126531](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430154126531.png)

###### 一个简单的例子

![image-20200430154157028](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430154157028.png)

自定义类使用的是系统类加载器，如果加载的过程中不是一个合法的字节码文件，会抛出异常

##### 加载:

1.通过一个类的全限定名获取定义此类的二进制字芹流
2.将这个字节流所代表的静态存储结构转化为方法区(7以前叫永久代，以后叫元数据，理解为存的模版，有需要根据这个模版在堆中生成对象，和spring容器差不多)的运行时数据结构
3.**在内存中生成一个代表这个类的java. lang.Class对象**（生成Class实例在这个阶段），作为方法区这个类
的各种数据的访问入口

##### 补充:加载. class文件的方式

- 从本地系统中直接加载
- 通过网络获取，典型场景: Web Applet
- 从zip压缩包中读取，成为日后jar、war格式的基础
- 运行时计算生成，使用最多的是:动态代理技术
- 由其他文件生成，典型场景: JSP应用
- 从专有数据库中提取. class文件,比较少见
- 从加密文件中获取，典型的防Class文件被反编译的保护措施

#### 类的加载过程

##### 验证(Verify) :

目的在于确保Class文件的字节流中包含信息符合当前虚拟机要求，保证被加载类的正确性，不会危害虚拟机自身安全。

主要包括四种验证，文件格式验证，元数据验证，字节码验证，符号引用验证。

##### 准备(Prepare) :

为类变量分配内存并且设置该类变量的默认初始值，即零值。

这里不包含用final修饰的static,因为final在编译的时候就会分配了，准备阶段会显式初始化;

这里不会为实例变量分配初始化，类变量会分配在方法区中，而实例变量是会随着对象一起分配到Java堆中。

##### 解析(Resolve) :

- 将常量池内的符号引用转换为直接引用的过程。
- 事实上，解析操作往往会伴随着JVM在执行完初始化之后再执行。
- 符号引用就是一组符号来描述所引用的目标。符号引用的字面量形式明确定义在《java虚拟机规范》的Class文件格式中。直接引用就是直接指向目标的指针、相对偏移量或一个间接定位到目标的句柄。
- 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型等。对应常量池中的CONSTANT Class info、 CONSTANT Fieldref info、 CONSTANT Methodref info等 。

**打开字节码文件查看二进制**

![image-20200430203116981](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430203116981.png)

字节码启始为CAFE BABE代表JAVA虚拟机的特定标识，验证过程

![image-20200430205325554](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430205325554.png)

反编译后，常量池中会加载这些许多的类

解析环境会转为直接引用

##### 初始化:

- **初始化阶段就是执行类构造器方法<clinit>()的过程**。
- 此方法不需定义，是javac编译器自动收集类中的所有类变量的赋值动作和静
  态代码块中的语句合并而来。
- 构造器方法中指令按语句在源文件中出现的顺序执行。
- **<clinit> ()不同于类的构造器**。(关联: 构造器是虚拟机视角下的<init>() )
- 若该类具有父类，JVM会 保证子类的<clinit>()执行前，父类的<clinit> ()
  已经执行完毕。
- 虚拟机必须保证-一个类的<clinit>()方法在多线程下被同步加锁。

![image-20200430211522096](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430211522096.png)

![image-20200430211858677](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430211858677.png)

![image-20200430212932602](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430212932602.png)

**注意**

![image-20200430213238905](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430213238905.png)

可以赋值，若前面没有声明不能引用

因为变量在准备阶段就已经被分配到方法区中，此时具有零值（默认值），赋与类变量值是在初始化阶段，在准备所属的链接阶段之后

[Java前向引用](https://www.cnblogs.com/nokiaguy/p/3156357.html)

若没有类变量与静态代码块，则不会存在<clinit>

![image-20200430214411600](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430214411600.png)

**此时就有了**

![image-20200430214516338](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430214516338.png)

**构造器方法**
![image-20200430220020097](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430220020097.png)

![image-20200430220906230](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430220906230.png)

一个类的<clinit>()方法在多线程下被同步加锁

![image-20200430230446539](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430230446539.png)

**注意，若业务中出现此情况，会将其他线程变成阻塞状态**

### 类的加载器

- JVM支持两种类型的类加载器，分别为**引导类加载器(Bootstrap** 
  **ClassLoader) **和 **自定义类加载器(User-Defined ClassLoader)**
- 从概念上来讲，自定义类加载器一-般指的是程序中由开发人员自定义的一类
  类加载器，但是Java虚拟机规范却没有这么定义，而是**将所有派生于抽象类**
  **ClassLoader的类加载器都划分为自定义类加载器**
- 无论类加载器的类型如何划分，在程序中我们最常见的类加载器始终只有3
  个，如下所示:

![image-20200430231135242](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430231135242.png)

Extention Class Loader 与 System Class Loader都间接继承了ClassLoader，所以他们也被称为自定义加载器

![image-20200430231203583](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200430231203583.png)

1. **Class类中**

```
@CallerSensitive
public ClassLoader getClassLoader() {
    ClassLoader cl = getClassLoader0();
    if (cl == null)
        return null;
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
        ClassLoader.checkClassLoaderPermission(cl, Reflection.getCallerClass());
    }
    return cl;
}
```

![image-20200501000100707](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200501000100707.png)

[关于类加载器的双亲委派模型](https://blog.csdn.net/javazejian/article/details/73413292)

