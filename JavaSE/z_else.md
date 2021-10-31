## 流程控制和循环

### 键盘输入Scanner

**如何从键盘获取数据：Scanner类**

- 1.导包：[**import java.util.Scanner;**]
- 2.Scanner的实例化：**Scanner scan = new Scanner(System.in);**
- 3.调用**Scanner**类的相关方法，获取指定类型的变量

```java
import java.util.Scanner;
class ScannerTest{

public static void main(String[] args){

Scanner scan = new Scanner(System.in);

int num = scan.nextInt();

System.out.println(num);

}

}
```

代码：正确性、可读性、健壮性、高效率低存储（时间复杂度、空间复杂度（衡量算法好坏））



## Eclipse

### 配置

编码和字体配置：

- 编码：window→preference→general→workspace
- 字体：window→preference→general→appearance→Colors and Fonts

代码风格设置：

- window→preference→Java→Code Style→Code Templates →comments→types→edit

  - ```java
    /**
     * @Description
     * @author lslFlag  Email:liangsheng_lin@163.com
     * @version
     * @date ${date}${time}
     *
     */
    ```

- window→preference→Java→Code Style→Code Templates →comments→methods→edit

  - ```java
    /**
     *@Description
     *@author lslFlag
     *@data ${date}${time}
     * ${tags}
     */
    ```

把一些不需要的在窗口上会显示的关掉：

- window→perspective→customize perspective→menu visibility→file→new    先把new都设置为不显示然后再重新进来设置new里面需要显示的

插件安装：

- help→eclipse marketplace wizard
- 可以去安装Darkest Dark Theme主题，直接寻找然后等待下载并安装

Debug调试：

- 熟练使用debug功能
- 关于debug功能Sept into功能异常的解决：
  - 可能是debug配置里jre选择出错，应该选择jdk

### 快捷键

```
Ctrl+1 快速修复(最经典的快捷键,就不用多说了)

Ctrl+Shift+O 自动导入所需要的包（这个用的次数也相当多）
Ctrl+D: 删除当前行 
Ctrl+Alt+↓ 复制当前行到下一行(复制增加)
Ctrl+Alt+↑ 复制当前行到上一行(复制增加)
Alt+↓ 当前行和下面一行交互位置(特别实用,可以省去先剪切,再粘贴了)
Alt+↑ 当前行和上面一行交互位置(同上)

Alt+← 前一个编辑的页面
Alt+→ 下一个编辑的页面(当然是针对上面那条来说了)
Alt+Enter 显示当前选择资源(工程,or 文件 or文件)的属性
Shift+Enter 在当前行的下一行插入空行(这时鼠标可以在当前行的任一位置,不一定是最后)
Shift+Ctrl+Enter 在当前行插入空行(原理同上条)
Ctrl+Q 定位到最后编辑的地方
Ctrl+L 定位在某行 (对于程序超过100的人就有福音了)
Ctrl+M 最大化当前的Edit或View (再按则反之)
Ctrl+/ 注释当前行,再按则取消注释
Ctrl+O 快速显示 OutLine
Ctrl+T 快速显示当前类的继承结构
Ctrl+W 关闭当前Editer
Ctrl+K 参照选中的Word快速定位到下一个
Ctrl+E 快速显示当前Editer的下拉列表(如果当前页面没有显示的用黑体表示)
Ctrl+/(小键盘) 折叠当前类中的所有代码
Ctrl+×(小键盘) 展开当前类中的所有代码
Ctrl+Space 代码助手完成一些代码的插入(但一般和输入法有冲突,可以修改输入法的热键,也可以暂用Alt+/来代替)
Ctrl+Shift+E 显示管理当前打开的所有的View的管理器(可以选择关闭,激活等操作)
Ctrl+J 正向增量查找(按下Ctrl+J后,你所输入的每个字母编辑器都提供快速匹配定位到某个单词,如果没有,则在stutes line中显示没有找到了,查一个单词时,特别实用,这个功能Idea两年前就有了)
Ctrl+Shift+J 反向增量查找(和上条相同,只不过是从后往前查)
Ctrl+Shift+F4 关闭所有打开的Editer
Ctrl+Shift+X 把当前选中的文本全部变味小写
Ctrl+Shift+Y 把当前选中的文本全部变为小写
Ctrl+Shift+F 格式化当前代码
Ctrl+Shift+P 定位到对于的匹配符(譬如{}) (从前面定位后面时,光标要在匹配符里面,后面到前面,则反之)

下面的快捷键是重构里面常用的,本人就自己喜欢且常用的整理一下(注:一般重构的快捷键都是Alt+Shift开头的了)
Alt+Shift+R 重命名 (是我自己最爱用的一个了,尤其是变量和类的Rename,比手工方法能节省很多劳动力)
Alt+Shift+M 抽取方法 (这是重构里面最常用的方法之一了,尤其是对一大堆泥团代码有用)
Alt+Shift+C 修改函数结构(比较实用,有N个函数调用了这个方法,修改一次搞定)
Alt+Shift+L 抽取本地变量( 可以直接把一些魔法数字和字符串抽取成一个变量,尤其是多处调用的时候)
Alt+Shift+F 把Class中的local变量变为field变量 (比较实用的功能)
Alt+Shift+I 合并变量(可能这样说有点不妥Inline)
Alt+Shift+V 移动函数和变量(不怎么常用)
Alt+Shift+Z 重构的后悔药(Undo)

编辑
作用域 功能 快捷键 
全局 查找并替换 Ctrl+F 
文本编辑器 查找上一个 Ctrl+Shift+K 
文本编辑器 查找下一个 Ctrl+K 
全局 撤销 Ctrl+Z 
全局 复制 Ctrl+C 
全局 恢复上一个选择 Alt+Shift+↓ 
全局 剪切 Ctrl+X 
全局 快速修正 Ctrl1+1 
全局 内容辅助 Alt+/ 
全局 全部选中 Ctrl+A 
全局 删除 Delete 
全局 上下文信息 Alt+？
Alt+Shift+?
Ctrl+Shift+Space 
Java编辑器 显示工具提示描述 F2 
Java编辑器 选择封装元素 Alt+Shift+↑ 
Java编辑器 选择上一个元素 Alt+Shift+← 
Java编辑器 选择下一个元素 Alt+Shift+→ 
文本编辑器 增量查找 Ctrl+J 
文本编辑器 增量逆向查找 Ctrl+Shift+J 
全局 粘贴 Ctrl+V 
全局 重做 Ctrl+Y 

 
查看
作用域 功能 快捷键 
全局 放大 Ctrl+= 
全局 缩小 Ctrl+- 

 
窗口
作用域 功能 快捷键 
全局 激活编辑器 F12 
全局 切换编辑器 Ctrl+Shift+W 
全局 上一个编辑器 Ctrl+Shift+F6 
全局 上一个视图 Ctrl+Shift+F7 
全局 上一个透视图 Ctrl+Shift+F8 
全局 下一个编辑器 Ctrl+F6 
全局 下一个视图 Ctrl+F7 
全局 下一个透视图 Ctrl+F8 
文本编辑器 显示标尺上下文菜单 Ctrl+W 
全局 显示视图菜单 Ctrl+F10 
全局 显示系统菜单 Alt+- 

 
导航
作用域 功能 快捷键 
Java编辑器 打开结构 Ctrl+F3 
全局 打开类型 Ctrl+Shift+T 
全局 打开类型层次结构 F4 
全局 打开声明 F3 
全局 打开外部javadoc Shift+F2 
全局 打开资源 Ctrl+Shift+R 
全局 后退历史记录 Alt+← 
全局 前进历史记录 Alt+→ 
全局 上一个 Ctrl+, 
全局 下一个 Ctrl+. 
Java编辑器 显示大纲 Ctrl+O 
全局 在层次结构中打开类型 Ctrl+Shift+H 
全局 转至匹配的括号 Ctrl+Shift+P 
全局 转至上一个编辑位置 Ctrl+Q 
Java编辑器 转至上一个成员 Ctrl+Shift+↑ 
Java编辑器 转至下一个成员 Ctrl+Shift+↓ 
文本编辑器 转至行 Ctrl+L 

 
搜索
作用域 功能 快捷键 
全局 出现在文件中 Ctrl+Shift+U 
全局 打开搜索对话框 Ctrl+H 
全局 工作区中的声明 Ctrl+G 
全局 工作区中的引用 Ctrl+Shift+G 

 
文本编辑
作用域 功能 快捷键 
文本编辑器 改写切换 Insert 
文本编辑器 上滚行 Ctrl+↑ 
文本编辑器 下滚行 Ctrl+↓ 

 
文件
作用域 功能 快捷键 
全局 保存 Ctrl+X 
Ctrl+S 
全局 打印 Ctrl+P 
全局 关闭 Ctrl+F4 
全局 全部保存 Ctrl+Shift+S 
全局 全部关闭 Ctrl+Shift+F4 
全局 属性 Alt+Enter 
全局 新建 Ctrl+N 

 
项目
作用域 功能 快捷键 
全局 全部构建 Ctrl+B 

 
源代码
作用域 功能 快捷键 
Java编辑器 格式化 Ctrl+Shift+F 
Java编辑器 取消注释 Ctrl+\ 
Java编辑器 注释 Ctrl+/ 
Java编辑器 添加导入 Ctrl+Shift+M 
Java编辑器 组织导入 Ctrl+Shift+O 
Java编辑器 使用try/catch块来包围 未设置，太常用了，所以在这里列出,建议自己设置。
也可以使用Ctrl+1自动修正。 

 
运行
作用域 功能 快捷键 
全局 单步返回 F7 
全局 单步跳过 F6 
全局 单步跳入 F5 
全局 单步跳入选择 Ctrl+F5 
全局 调试上次启动 F11 
全局 继续 F8 
全局 使用过滤器单步执行 Shift+F5 
全局 添加/去除断点 Ctrl+Shift+B 
全局 显示 Ctrl+D 
全局 运行上次启动 Ctrl+F11 
全局 运行至行 Ctrl+R 
全局 执行 Ctrl+U 

 
重构
作用域 功能 快捷键 
全局 撤销重构 Alt+Shift+Z 
全局 抽取方法 Alt+Shift+M 
全局 抽取局部变量 Alt+Shift+L 
全局 内联 Alt+Shift+I 
全局 移动 Alt+Shift+V 
全局 重命名 Alt+Shift+R 
全局 重做 Alt+Shift+Y
————————————————
版权声明：本文为CSDN博主「weasleyqi」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weasleyqi/article/details/7896576
```



# 面向对象程序设计

类和对象的内存分配：

创建好类和类的属性、行为后在main方法里面把类实例化-即创建对象，此时在栈空间会开辟一个内存给该对象用来存放在该对象的属性、行为在堆空间的内存地址，如果在main方法里引用该对象的方法或属性时，一些局部变量会在栈空间开辟一段内存用来存放这些数据，当方法结束后会根据后进先出原则将变量清除出去，到main方法结束时，相应的存放的对象的属性和行为也会被当做垃圾回收

![面向对象思想](D:\LSL\Learning\CS\Java\JavaLearningNote\Java基础\img\面向对象思想.png)

JVM内存结构:程序--->字节码文件--->解释运行；编译生成一个或多个字节码文件，再使用JVM中的类的加载器和解释器对生成的字节码文件进行解释

匿名对象：

- 创建的对象没有显式赋给一个变量名
- 只调用一次
- 开发中常在方法里直接使用

2021.03.13 P202

方法的重载：

- 概念：同一个类中允许一个以上的同名方法，只要他们的参数个数或者参数类型不同即可
- 特点：与返回值类型、参数名称无关，只看参数列表，并且参数列表必须不同（参数类型或个数）

- 可变个数方法  例如 **public void show(数据类型  ...  变量名){ }**          （可以传入个数是0个，1个，两个......，实际上参数也就相当一个数组，可以使用**变量名.length**访问传入形参个数和用**变量名[n]**访问传入的某一个形参）
  - 此方法和**public void show(数据类型[]  变量名){ }** 相同，不能同时出现在一个类中
  - 可变形参只能是一个并且声明在末尾，例如**public void show(int i,  String ... strs) {}**

（重写后再）

值传递机制：

- 形参：方法小括号里声明的参数
- 实参：方法调用时实际传给形参的值
- 参数是基本数据类型，此时实参赋给形参的是实参真实存储的数据值
- 参数是引用数据类型，此时实参赋给形参的是实参存储数据的地址值（含变量的数据类型）

 递归（recursion）：

- 函数自身调用自身来实现某一特定计算

219

## 关键字

### this

this关键字：

- 可用于修饰类的属性、方法、构造器，意为”当前对象的...“
- 类的方法中：调用当前对象属性或方法
- 类的构造器中：调用当前正在创建的对象属性或方法
- 特殊情况下，如果方法形参和类属性同名或构造器形参和类属性同名，必须用this修饰来表明这是属性
- 调用构造器
  - “this(形参列表);”调用本类中指定的构造器
  - 不能调用自己
  - 规定：必须声明在当前构造器首行
  - 构造器内部最多只能声明一个“this(形参列表);”

### package

package关键字：

- 为了更好的管理项目中的类，提出了包的概念
- package用来声明类或接口所属的包，声明在类的首行

- 包的命名都是小写xxxyyyzzz，尽量“见名知义”
- 每"."一次，就代表一层文件目录
- 同一个包下，不能命名同名的接口、类
  - 不同的包下可以
- JDK中主要的包：
  - java.lang             java语言核心类，如String、Math、Integer、System、Thread，提供常用功能
  - java.net               与网络相关的操作的类和接口
  - java.io                  包含能提供多种输入/输出功能的类
  - java.util                实用工具类，如定义系统特性、接口的集合框架类、使用与日期日历相关的函数
  - java.text               java格式化相关的类
  - java.sql                 JDBC数据库编程的相关类/接口
  - java.awt                抽象窗口工具集的多个类，用来构建和管理应用程序的图形用户界面

### import

import关键字：

- 导入指定包下的类、接口
- 在包和类之间声明，如需导入多个，并列导入即可
- 使用"xxx.*"表示导入xxx包下的所有结构
- 使用的类或接口是java.lang包或本包下定义的，可以省略import结构
- 如果在源文件中使用了不同包下的同名的类则必须至少有一个类需要以全类名的方式进行显示“包的完整路径.类名”
- 包的子包需要再次导入
- "import  static"导入指定类或接口中的静态结构：属性或方法 

### super

super关键字：

- 用来区分子类和父类，和this关键字有些相像之处，含义为当前对象的父类
- 区分重写与被重写的方法，区分父类、子类都显式声明了的属性
  - super.方法          表示的是父类中的声明的方法
  - super.属性          表示的是父类中的声明的属性
- 调用父类构造器：
  - 子类构造器中首行声明super(形参列表)来调用父类构造器
  - 构造器调用构造器时，super(形参列表)和this(形参列表)不能同时出现
  - 在构造器的首行，没有显式声明super(形参列表)或this(形参列表)，则默认调用父类中的空参构造器（super()）（被调用的是显式声明的，如果父类中没有显式声明，则报错）
  - 在类的多个构造器中，至少有一个使用了super来调用父类的构造器
  - this是先在当前类里找，如果没找到也会去父类里找，有点就近原则的意思

### static

- 编写类时并没有产生实质上的对象，当通过new关键字时才产生了对象并进行了内存空间分配；为了实现特定的属性或方法只存在一份内存里，就发展了static关键字
- static，静态的
  - 修饰属性(静态变量、类变量)、方法（静态方法）、代码块、内部类
  - 类中常量也常常声明为static
  - 静态变量随着类的加载而加载，静态方法只能调用静态的方法和属性，非静态方法可以调用静态的和非静态的
  - 静态方法内不能使用this、super关键字

开发中，如何确定是否要声明static：

- 属性可以被多个对象共享且都相同
- 操作静态属性的方法
- 工具类中的方法习惯声明为static，比如：Math、Arrays、Collections

### final

- 最终的，修饰类、方法、变量、对象
- 修饰类
  - 修饰后的类不能被继承，例如String类、System类、StringBuffer类
- 修饰方法
  - 修饰后的方法不能被重写，比如Object类的getClass()	
- 修饰变量（修饰的“变量”就成为一个常量）
  - 修饰属性：显式赋值、代码块中的初始化、构造器中初始化（不能在方法中赋值是因为通过构造器后对象就建立起来了，已经成为了一个常量）
  - 修饰局部变量：修饰形参只能使用这个形参  
- static final：修饰属性（全局常量）、修饰方法（很少）

## 补充：object类和包装类的使用

Object类：

- Object类是所有Java类的根父类
- 类的声明中没有使用extends来指明父类，则默认父类为java.lang.Object
- Object类（只声明了一个空参构造器）的属性、方法就具有通用性

==和equals():

- ==  
  - 基本数据类型比较：比较的是两个变量保存的数据是否相等（不一定要相同数据类型）
  - 引用数据类型比较：比较的是地址值是否相同，即两个引用是否指向同一个实体
- equals()
  - 一个只适用于引用数据类型的方法
  - 使用方法：对象1.equals(对象2)
  - Object类中的equals()和==的作用相同
  - String、Date、File、包装类等都重写了Object类的equals()方法，它们的equals()比较的是两个对象的内容是否相等(对象里面的实体内容)

自定义类对equals()的重写：

- 比较的是内容而不是地址
- 自动生成或手写（要会手写）

Object类中toString():

- 输出：包地址.类名@内存地址值
- 输出对象的引用时就调用了这个方法
- String、Date、File、包装类等都重写了Object类的toString()方法，返回的是实体内容信息
- 也可以自动去生成

单元测试：

- 包

包装类（Wrapper）：

- 包装类：针对八种基本数据类型定义相应的引用类型

包装类、基本数据类型、String三者之间的转换

- 基本数据类型--->包装类：调用包装类的构造器，注意Boolean的构造器有些优化
- 包装类--->基本数据类型：调用包装类Xxx的xxxValue()
- 【】JDK5.0新特性：
  - 自动装箱：基本数据类型可以直接赋给相应的包装类对象，基本数据类型--->包装类
  - 自动拆箱：包装类对象可以赋给相应基本数据类型变量，包装类--->基本数据类型
- 基本数据类型、包装类<--->String
  - String--->...  ...：调用相应包装类的parseXxx();
  - ...  ...--->String：String类的valueOf();

【**面试**】

```java
public void test() {
    Integer i = new Integer(1);
    Integer j = new Integer(1);
    System.out.println(i == j);//false
    
    //Integer内部定义的IntegerCache结构中定义了Integer[]，保存了从-128~127范围内的整数
    //如果我们用自动装箱的方式给Integer赋值的范围时可以直接使用数组中的元素，不用再去new了
    //目的：提高效率
    
    Integer m = 1;
    Integer n = 1;
    System.out.println(m == n);//true
    
    Integer x = 128;
    Integer y = 128;
    System.out.println(x == y);//false
}
```

P309  2021.03.31

## 设计模式

设计模式是**在大量的实践中总结和理论化之后优选的代码结构、编程风格、 以及解决问题的思考方式**。设计模免去我们自己再思考和摸索。就像是经典 的棋谱，不同的棋局，我们用不同的棋谱。”套路”

### 单例(Singleton)设计模式

单例模式就是单个对象单个构造器，创建的类中只能有一个构造器、一个对象，在类里完成对象实例化。

#### 饿汉模式：

- 坏处：对象加载时间过长；好处：线程安全

```java
//例如
public class Account{
    private Account() {
        
    }
    private static Account acc = new Account();
    public static Account getAccount() {
        return acc;
    }
}
```

#### 懒汉模式：

- 延时对象创建

- ```java
  public class Account{
      private Account() {
      }
      private static Account acc = null;
      public static Account getAccount() {
          if(acc == null){
              acc = new Account();
          }
          return acc;
      }
  }
  ```

## 接口的应用举例：

### 代理模式

- ```java
  public class NetWorkTest {
  	public static void main(String[] args) {
  		Server server = new Server();
  		ProxyServer pro = new ProxyServer(server);
  		pro.browse();
  	}
  }
  interface NetWork {
  	public void browse();
  }
  //被代理类
  class Server implements NetWork {
  
  	@Override
  	public void browse() {
  		System.out.println("真实的访问网络");
  		
  	}
  	
  }
  //代理类
  class ProxyServer implements NetWork {
  	private NetWork work;
  	public ProxyServer(NetWork work) {
  		this.work = work;
  	}
  	@Override
  	public void browse() {
  		check();
  		work.browse();  
  	}
  	public void check() {
  		System.out.println("联网之前的检查工作");
  	}
  }
  
  ```

### 工厂模式

JDK8中的静态方法、默认方法：

```java
public interface CompareA {
    public static void method1 {
        //1.静态方法，只能通过接口来调用
    }
    public default void method2() {
        //2.默认方法，只能通过实现类的对象来调用，可以通过实现类来重写，重写后调用的是重写后的方法
    }
    default void method3() {
        //默认方法
    }
}
class SubClass extends SuperClass implements CompareA {
    public void method2() {
        System.out.println("SubClass");
        //3.子类(或实现类)继承的父类和实现的接口中声明了同名同参数的默认方法，
        //在子类没有重写此方法的情况下，默认调用父类同名同参数的方法---->类优先原则
    }
}
class SubClass implements CompareA, CompareB {
    public void method2() {
        System.out.println("SubClass");
        //4.实现类实现了多个接口，而这多个接口中定义了同名同参数的默认方法，
        //那么在实现类没有重写此方法的情况下，会报错---->接口冲突
    }
}
//5.在子类(或实现类)的方法中调用父类、接口中被重写的方法
class SubClass extends SuperClass implements CompareA, CompareB{
    public void method2() {
        System.out.println("杭州");
    }
    public void method3() {
        System.out.println("上海");
    }
    public void myMethod() {
    	method3();//调用自己定义的重写的方法
        super.method3();//父类中声明的
        				//调用接口中默认的
        CompareA.super.method3();
        CompareA.super.method3();
    }
}
```

