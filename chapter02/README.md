

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

**因为变量在准备阶段就已经被分配到方法区中，此时具有零值（默认值），赋与类变量值是在初始化阶段，在准备所属的链接阶段之后**

[Java前向引用]: https://www.cnblogs.com/nokiaguy/p/3156357.html

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

**Extention Class Loader 与 System Class Loader都间接继承了ClassLoader，所以他们也被称为自定义加载器**

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

[关于类加载器的双亲委派模型]: https://blog.csdn.net/javazejian/article/details/73413292



#### 虚拟机自带的加载器

**启动类加载器(引导类加载器，Bootstrap ClassLoader)**
➢这个类加载使用**C/C++语言实现**的，嵌套在JVM内部（即JVM的一部分）。
➢它用来加载Java的核心库(JAVA HOME/jre/lib/rt. jar、resources.jar或sun . boot.class .path路径下的内容) ,用于提供JVM自身需要的类
➢并不继承自java. lang.ClassLoader,没有父加载器。
➢加载扩展类和应用程序类加载器，并指定为他们的父类加载器。
➢出于安全考虑，Bootstrap启动类加载器只加载包名为java、javax、sun等开头的类

**扩展类加载器( Extension ClassLoader)**
➢**Java语言编写**，由sun . misc. Launcher$ExtClassLoader实现(Launcher的一个内部类，代码有体现)。
➢派生于ClassLoader类
➢父类加载器为启动类加载器:
➢从java.ext. dirs系统属性所指定的目录中加载类库，或从JDK的安装目录的jre/lib/ext子目录(扩展目录)下加载类库。**如果用户创建的JAR放在此目录下，也会自动由扩展类加载器加载。**

<u>Extension ClassLoader为Launcher的一个内部类</u>

![image-20200501230138066](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200501230138066.png)

**应用程序类加载器(系统类加载器，AppClassLoader )**
➢java语言编写，由sun. misc. Launcher$AppClassLoader实现
➢派生于ClassLoader类
➢父类加载器为扩展类加载器
➢它负责加载环境变量classpath或系统属性java.class.path 指定路径下的类库
➢**该类加载是程序中默认的类加载器**，一般来说，Java应用的类都是由它来完成加载
➢通过ClassLoader#getSystemClassLoader ()方法可以获取到该类加载器

![image-20200501230540170](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200501230540170.png)

**ClassLoader1**

![image-20200501231701249](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200501231701249.png)

结果： 

[rt.jar的作用]: https://blog.csdn.net/u011305680/article/details/80380532

![image-20200501231734513](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200501231734513.png)

![image-20200501232806861](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200501232806861.png)

同理：

![image-20200501234742495](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200501234742495.png)

**用户自定义类加载器**
●在Java的日常应用程序开发中，类的加载几乎是由上述3种类加载器相互配合执行的，在必要时，我们还可以自定义类加载器，来定制类的加载方式。
●为什么要自定义类加载器?
➢隔离加载类
➢修改类加载的方式
➢扩展加载源
➢防止源码泄漏

**隔离加载类**

由于中间件都有自己的依赖的大包，然后在同一个工程里边如果引入多个框架的话有可能会出现比如某些类路径一样、类型也相同，那在这种情况下呢就会出类的冲突，我们就需要做一个类的仲裁。像主流的容器类的框架，他们都会自定义这个类加载器。

**修改类加载方式**

在整个类的加载中，bootstrap是一定会使用的，因为他一定会加载系统核心的api。其他类可能就是不是必须的，在实际情况中，可以在要用的时候再引入。在需要的时候进行一个动态的加载。

**扩展加载源**

除了前面提到了加载的类可以比如有本地的物理磁盘，通过网络中，通过jar包中等等去加载之外，还可以考虑像比如说数据库当中，甚至说这个电视机的机顶盒等等，我们去加载这个字节码文件的来源，所以通过自定义类加载器，可以来扩展加载来源。

**防止源码泄漏**

Java代码实际上是很容易被编译和篡改的，有了这个字节码文件以后，没有这个反编译的一些手段的话很容易的就被反编译了，容易被篡改。为了防止被编译和篡改，对这个字节码文件来进行加密，你自己在运行的时候把它再还原成内存中的这个类去执行的时候，我们需要解密，那这个时候呢，我们可以去自定义类加载器去实现这样的一个解密操作。

**用户自定义类加载器实现步骤:**
1.开发人员可以通过继承抽象类java. lang. ClassLoader类的方式， 实现自己的类加载器，以满足一些特殊的需求

2.在JDK1.2之前， 在自定义类加载器时，总会去继承ClassLoader类并重写loadClass()方法，从而实现自定义的类加载类，但是在JDK1.2之后已不再建议用户去覆盖loadClass()方法，而是建议把自定义的类加载逻辑写在findClass()方法中

3.在编写自定义类加载器时，如果没有太过于复杂的需求，可以直接继承URLClassLoader类，这样就可以避免自己去编写findClass()方法及其获取字节码流的方式<!--如没有一些解密操作-->使自定义类加载器编写更加简洁。

```
/**
 * @author Aaron
 * @description 自定义用户类加载器
 * @date 2020/5/2 9:48 AM
 */
public class CustomClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //根据路径name,以二进制流的方式读到内存里面，形成一个字节数组
            byte[] result = getClassFromCustomPath(name);
            if (result == null) {
                throw new FileNotFoundException(name);
            } else {
                // defineClass方法将字节码转化为类
                return defineClass(name, result, 0, result.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ClassNotFoundException(name);
    }

    private byte[] getClassFromCustomPath(String name) {
        // 从自定义路径中加载指定类，返回类的字节码文件
        // 如果指定路径的字节码文件进行了加密，需要在此方法中解密操作
        InputStream in = null;
        ByteArrayOutputStream out = null;
        String path = "/Users/john/" + name + ".class";
        try {
            in = new FileInputStream(path);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            Class<?> clazz = Class.forName("One", true, customClassLoader);
            Object obj = clazz.newInstance();
            // cn.xpleaf.coding.c4.CustomClassLoader@610455d6
            System.out.println(obj.getClass().getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**ClassLoader类，它是一个抽象类，其后所有的类加载器都继承自**
**ClassLoader ( 不包括启动类加载器)**

| 方法名称                                           | 描述                                                         |
| -------------------------------------------------- | ------------------------------------------------------------ |
| getParent()                                        | 返回该类加载器的超累加载器                                   |
| loadClass(String name)                             | 加载名称为name的类，返回结果为java.lang.Class类的实例        |
| findLoadedClass(String name)                       | 查找名称为name的已经被加载过的类，返回结果为java.lang.Class类的实例 |
| defineClass(String name,byte[] b,int off, int len) | 把字节数组b中的内容转换为一个Java类，返回结果为java.lang.Class类的实例 |
| resolveClass(Class<?> c)                           | 连接指定的一个Java类                                         |

| 方式一：获取当前类的ClassLoader                |
| ---------------------------------------------- |
| class.getClassLoader()                         |
| 方式二：获取当前线程上下文的ClassLoader        |
| Thread.currentThread().getContextClassLoader() |
| 方式三：获取系统的ClassLoader                  |
| ClassLoader.getSystemClassLoader()             |
| 方式四：获取调用者的ClassLoader                |
| DriverManager.getCallerClassLoader()           |

```
public class ClassLoaderTest2 {
    public static void main(String[] args) throws ClassNotFoundException {
        //1
        ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
        System.out.println(classLoader); //null
        //2 通过线程获取该上下文的一个加载器，上下文在自定义的这个程序当中
        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader1); //sun.misc.Launcher$AppClassLoader@18b4aac2
        //3
        ClassLoader classLoader2 = ClassLoader.getSystemClassLoader().getParent();
        System.out.println(classLoader2);//sun.misc.Launcher$ExtClassLoader@610455d6
    }
}
```

#### 双亲委派机制

Java虛拟机对class文件采用的是**按需加载**的方式，也就是说当需要使用该
类时才会将它的class文件加载到内存生成class对象。而且加载某个类的
class文件时，Java虚拟机采用的是**双亲委派模式**，即把请求交由父类处理，
它是-种任务委派模式。

[静态代码块执行顺序]: https://blog.csdn.net/qq_35868412/article/details/89360250

<!--静态代码块在第三个阶段（初始化）被调用，把静态代码块，静态变量显示赋值放在<clinit>中-->

![image-20200502105349223](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502105349223.png)

![image-20200502110226182](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502110226182.png)

从结果来看不是执行的自定义实现的String。为了这种防止，引入双亲委派机制

![image-20200502105501988](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502105501988.png)

所以String不会由AppClassLoader加载,会由引导类加载器加载

再举一个例子：

![image-20200502110556907](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502110556907.png)

1. 想要去执行main方法，则main方法所在的类需要被加载
2. 为这个String,委托给BootstrapLoader。它就加载了核心API的java.lang中的String,但是没有main方法，所以报错

**再举一个例子**

他这个双亲委派针对于包名，类名相同的情况下，加如我设置包名不同（自己定义的类），它为AppClassLoader

![image-20200502114100458](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502114100458.png)

![image-20200502114130213](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502114130213.png)

下面对以上流程作出一个解释：

[首先介绍下什么是SPI]: https://blog.csdn.net/whp1473/article/details/80164254


我们在程序中需要用到SPI接口，它属于这个核心API。那我们就使用双亲委派机制，**依次 **到引导类加载器，然后到引导类加载器去加载rt.jar。SPI的核心类就加载过来了。那么这里边会存在一些interface接口，那接口呢，需要用一些具体的实现类了，那具体实现类呢，这就涉及到一些第三方的jar包了，我们要加载的是JDBC的jar包，那我们要加载第三方的时候呢，这个时候因为你是第三方的不属于核心的API，其实就应该是由我们所谓的AppClassLoader加载，所以这就出现一个叫 **反向委派** ，一直这样委派就委派到AppClassLoader，这块儿实际上是由我们当前线程的，通过getContextClassLoader获取到的，然后由他来加载我们SPI接口的具体实现类，jdbc.jar包里边儿的这些API，所以这里边儿我们就会看到接口是由Bootstrap ClassLoader加载的，而具体接口的实现类，是第三方的,是由我们这个ContextClassLoader加载的，而ContextClassLoader呢，就是我们的AppClassLoader，在前边获取类加载器的时候，演示过通过线程来获取，一般情况下我们拿到的都是一个AppClassLoader去加载我们第三方的jdbc.jar包下的API，这是这个图想说明的。

**双亲委派优势**
➢避免类的重复加载 即加载有一个层次关系

<!--如：BootstrapClassLoader->ExtClassLoader->AppClassLoader,上面有图-->
➢保护程序安全，防止核心API被随意篡改
	自定义类: java.1ang. String
	自定义类: java. lang. ShkStart
java. lang. SecurityException:Prohibited package name: java.lang

举例：

![image-20200502120549976](/Users/piwenjing/Library/Application Support/typora-user-images/image-20200502120549976.png)

阻止报名为java.lang包。我们按照双亲委派机制依次往上，Bootstrap ClassLoader发现为java开头,则发现为自己管的，它就去加载这个类。java.lang包的访问需要权限，**java.lang.SecurityException**,阻止我们用这个报名定义我们的自定义类。你可能会问这个跟之前String区别，我的理解是：因为存在这个机制，String本来就会在Bootstrap ClassLoader中加载成功，所以String不会影响。但是若Aaron加载成功（本来引导类加载器它自身没有这个东西），它可能就会怀疑是恶意的，会对它自己有影响。

#### 在双亲委派机制中还有一个 “沙箱安全机制”。

<!--在这只是介绍下这个名字，前面例子已说明-->

自定义String类，但是在加载自定义String类的时候会率先使用引导类加载器加载，而引导类加载器在加载的过程中会先如载jdk自带的文件) (rt. jar包中java\lang\String. class)，报错信息说没有main方法,就是因为加载的是rt. jar包中的String类。这样可以保证对java核心源代码的保护，这就是**沙箱安全机制**。

#### 其他补充内容

●在JVM中表示两个class对象是否为同一个类存在两个必要条件:
➢类的完整类名必须一致， 包括包名。<!--前面javapp.langpp.String已说明-->
➢加载这个类的ClassLoader (指ClassLoader实例对象)必须相同。<!--例如自定义的为AppClassLoader,核心中的String为Bootstrap ClassLoader,前面例子也说明了,跟上一个例子相同-->
●换句话说，在JVM中，即使这两个类对象(class对象)来源同一个Class文件，被同一个虚拟机所加载，但只要加载它们的ClassLoader实例对象不同，那么这两个类对象也是不相等的。

#### **对类加载器的引用**

JVM必须知道一个类型是由启动加载器加载的还是由用户类加载器加载的。如果一个类型是由用户类加载器加载的，**那么JVM会将这个类加载器的一个引用作为类型信息的一部分保存在方法区中**。当解析一个类型到另一个类型的引用的时候，JVM需要保证这两个类型的类加载器是相同的<!--后面学习到动态链接再解释-->。

#### **类的主动使用和被动使用**

Java程序对类的使用方式分为:主动使用和被动使用。
●主动使用，又分为七种情况:
➢创建类的实例
➢访问某个类或接口的静态变量，或者对该静态变量赋值
➢调用类的静态方法
➢反射(比如: Class . forName ("top. p3wj. java.StringTest") ) 
➢初始化一个类的子类 <!--它的父类也会被初始化-->
➢Java虚拟机启动时被标明为启动类的类
➢JDK 7开始提供的动态语言支持:
java. lang. invoke . MethodHandle实例的解析结果REF_getStatic、 REF_putStatic、 REF_invokeStatic句柄对应的类没有初始化，则初始化
●除了以上七种情况，其他使用Java 类的方式都被看作是对**类的被动使用**，都**不会导致类的初始化**。

[什么是句柄]: https://blog.csdn.net/wyx0224/article/details/83385168

解释：**（类的加载过程：加载->链接[验证、准备、解析]->初始化）**

就是说当你要是被动使用的话会被加载，毕竟使用了，一旦使用了我们就需要加载到内存当中。但是呢，在初始化的时候不一定会去调用这个<clinit>方法，这个方法会涉及到静态的属性静态代码块的一个执行了，你要是静态的属性会有一个显示赋值，尤其是这个静态代码块是否执行要看是否执行过这个<clinit>，所以呢，主动使用被动使用的区别就在于这个操作是否执行了。<!--之后再做验证-->

