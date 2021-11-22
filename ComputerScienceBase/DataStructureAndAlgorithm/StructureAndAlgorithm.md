# 概述

数据：描述客观事物的符号（对于计算机来说是可输入、可识别、可操作的）。

数据元素：组成数据的、有一定意义的基本单位。

数据项：数据的不可分的最小单位，一个数据元素可由若干个数据项组成。

数据对象：性质相同的数据元素的集合。

数据结构：相互之间存在一种或多种特定关系的数据元素的集合。（分为逻辑结构和物理结构（存储结构））

**逻辑结构：数据对象中数据元素之间的相互关系**

- 集合结构：数据元素同属一个集合，此外没有其他的相互关系。
- 线性结构：数据元素一对一的关系所组成的线性关系。
- 树形结构：数据元素一对多的关系所形成的的层次关系。
- 图形结构：数据元素多对多的关系。

**物理结构（存储结构）：数据逻辑结构在计算机中的存储形式**（将数据及其逻辑关系存储到计算机中的结构方法）

- 顺序存储结构：数据元素存放在地址连续的存储单元。
- 链式存储结构：数据元素存放在任意的存储单元，存储单元可连续也可不连续（依靠存放地址的指针来定位数据元素）。

数据类型：一组性质相同的值的集合及定义在此集合上的一些操作的总称。（值的不同，所需要的内存大小不同，根据值来设定好一个内存大小）

抽象数据类型：对已有的数据类型的抽象，是操作，一个数学模型及定义在该模型上的操作。（抽象数据类型体现了程序设计中问题分解、抽象和信息隐藏的特性。抽象数据类型把实际生活中的问题分解为多个规模小且易处理的问题，然后建立一个计算机能处理的数据模型，并把每个功能模块的实现细节作为一个独立的单元，从而使具体实现过程隐藏起来）

**算法：解决特定问题求解的步骤的描述（计算机中表现为指令的有限序列）**

算法特性：输入、输出、有穷性（在可接受时间内完成）、确定性（每一步骤都有确定含义且不会出现二义性）、可行性（每一步都能通过有限次数完成）。

好的算法：正确性、可读性、健壮性、高效率、低存储量。

算法效率度量方法：

- 事后统计方法：完成算法后测试不同情况下的运行时间。
- 事前分析估算方法：算法程序编制前使用统计方法对算法进行估算（时间复杂度、空间复杂度）。

**时间复杂度：用来度量算法的运行时间**

程序运行时的消耗时间取决于：

1. 算法采用的策略和方法；（算法好坏的根本）
2. 编译产生的代码质量；（软件因素）
3. 问题的输入规模；
4. 机器执行指令的速度。（硬件因素）

参考时间复杂度的方法由来：[（数据结构）十分钟搞定时间复杂度（算法的时间复杂度） - 简书 (jianshu.com)](https://www.jianshu.com/p/f4cca5ce055a)

使用大O阶方法：（函数的阶数对函数的增长速率影响最大）

- 记法：记总执行次数`T(n) = O(f(n))`，n为问题规模，**随着输入大小 n 的增大，算法执行需要的时间的增长速度可以用 f(n) 来描述**。
- 大O阶方法推导时间复杂度：（通过计算程序执行次数再使用下式推导）
  1. 用常数1取代运行时间中的所有加法常数；
  2. 修改后的运行次数函数中只保留最高阶；
  3. 如果最高阶项存在且不是1，则去除和这个项相乘的常数。

```java
public void runTime() {
   int i = 0;               // 需要执行1次
   System.out.println(i);   // 需要执行1次
}
// 运算次数：T(n)=2 ===> 复杂度O(1)   用1取代加法次数
```

```java
public void runTime() {
     for(int i = 0; i<n; i++) {      // 需要执行 (n + 1) 次
        System.out.println(i);       // 需要执行 n 次
    }
   System.out.println("Hi");            // 需要执行 1 次
} 
// 运算次数：T(n)=(n+1)+n+1=2n+2 ===> 复杂度O(n)   有阶数去除常数项
```

```java
public void runTime() {
     for(int i = 0; i< n; i++) {       // 需要执行 (n + 1) 次
         for(int i = 0; j < n; j++){   // 需要执行 (n + 1) 次
         	System.out.println(i);     // 需要执行（n * n）次
         }
    }
   System.out.println("Hi");            // 需要执行 1 次
} 
// 运算次数：T(n)= n^2 + 2n+1 ===> 复杂度O(n^2)   有阶数取最高阶
```

常见时间复杂度所耗费的时间：

```xml
O(1) < O(logn) < O(n) < O(nlogn) < O(n^2) < O(n^3) < O(2^n) < O(n!) < O(n^n) 
```

**空间复杂度：计算算法所需的存储空间实现**

- S(n)=O(f(n))，n为问题规模，f(n)为关于n所占存储空间的函数。

# 数据结构

![](img/type.png)

一般来说，按照数据的逻辑结构对其进行简单的分类，包括线性结构和非线性结构两类。

线性结构：具体的常见的线性结构有：数组、链表、栈、队列和串等。

非线性结构：具体的结构形式为二维数组、多维数组、广义表、树结构和图结构等。

常用的数据结构：数组（array）、栈（stack）、队列（queue）、链表（linked list）、树（tree）、图（graph）、堆（heap）、散列表（hash）。

# 线性结构

## 数组

**稀疏数组：**

- 使用场景：当一个数组中大部分元素是同一个值时；
- 稀疏数组处理方法：把一个数组中不同值的信息（行、列和对应值）以及数组的信息（row、column、有多少个不同的值）都记录进另一个小规模的数组（稀疏数组）。

**代码实现：**



## 队列

队列是一个有序列表，遵循先入先出原则，可用数组和链表实现。

### 使用数组实现

**一般实现：**

<img src="img/queue_array.png" style="zoom: 50%;" />

front：用于定位数组的头部，取值-1，头部索引=(front+1)，每取出一次数据front要加1；

rear：用于定位队列的最后一个数据，队列中每加入一个数据rear就加1，实现定位；

maxSize：用于充当队列的最大容量；

队列需要的功能：

1. 创建队列：初始化队列的容量（arr[maxSize]）、初始化“指针”（front、rear）；
2. 往队列中添加数据：如果队列满载则不能添加；
3. 从队列中取出数据：要符合先进先出，队列中没有数据时无法取出；
4. 队列是否满载的判断；
5. 队列数据是否为空的判断；
6. 展示队列全部数据、展示队列头部数据。

```java
public class ArrayQueue {
    private int rear;
    private int front;
    private int maxSize;
    private int[] arr;
    public ArrayQueue(int arrayMaxSize){
        maxSize = arrayMaxSize;
        arr = new int[maxSize];
        // 头部前
        front = -1;
        // 指向队列最后一个数据
        rear = -1;
    }
    // 判断队列是否已经满
    public boolean isFull(){
        return rear == maxSize - 1;
    }
    // 判断队列是否为空
    public boolean isEmpty(){
        return front == rear;
    }
    // 添加数据进队列
    public void addQueue(int n){
        if (isFull()){
            throw new RuntimeException("队列已满！");
        }
        rear++;
        arr[rear] = n;
    }
    // 取出队列中数据
    public int getQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列为空！");
        }
        front++;
        return arr[front];
    }
    // 显示队列中所有数据
    public void showQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列为空！");
        }
        for (int i = 0; i < arr.length;i++){
            System.out.printf("arr[%d]=%d\n",i,arr[i]);
        }
    }
    // 获取队列的头数据
    public int showHead(){
        if (isEmpty()){
            throw new RuntimeException("队列为空！");
        }
        return arr[front+1];
    }
}
```

**使用环形数组实现：**

<img src="img/queue_circle_array.png" style="zoom:50%;" />

使用数据简单实现队列中存在一个问题：就是一个队列满载后，即使取出一些数据把位置“空出来”，但空出来的位置已经是不能复用的了，那怎么才能实现一个当满载后再取出数据后还能往队列添加数据的队列呢？答案就是：在数组中把一个位置当为缓冲区，满载时，当取出一个数据，就把这个缓冲区转化为队列的有效位置，而取出数据后空出来的位置就再次充当缓冲区，这样就能把头尾相连起来了，就实现了一个环形的数组充当的队列。（如果不满载，缓冲区就逐渐后移就好）

具体实现思路：

1. 先要初始化队列（包含数组、front（队列头数据索引）、rear（缓冲区索引）），注意环形数组需要一个容量充当缓冲区，所以实现的队列的有效容量为最大容量减去1；
2. 考虑maxSize、front、rear的联系（这里利用了它们之间的一些数学关系）。

```java
public class CircleQueue {
    // 队列最大容量 注意此时有效容量是(maxSize - 1) 多出来的一个用于实现环形
    private int maxSize;
    // front指向队列头
    private int front;
    // rear 队列尾部
    private int rear;
    // 存放数据的地方
    private int[] arr;

    public CircleQueue(int maxSize) {
        this.maxSize = maxSize;
        front = 0;
        rear = 0;
        arr = new int[this.maxSize];
    }
    // 队列是否满了
    public boolean isFull(){
       return  ((rear + 1) % maxSize) == front;
    }
    // 判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }
    // 添加数据 进队列
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列已满，不能添加！");
            return;
        }
        arr[rear] = n;
        // 一个数除以另一个数，如果被除数比除数小，那么余数就是被除数本身
        rear = (rear + 1) % maxSize;
    }
    // 出队列
    public void getQueue(){
        if (isEmpty()){
            System.out.println("队列为空，不能取数据！");
            return;
        }
        int value = arr[front];
        front = (front + 1) % maxSize;
    }
    // 显示队列数据
    public void showQueue(){
        if (isEmpty()){
            System.out.println("队列为空，没有数据！");
            return;
        }
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n",i % maxSize,arr[i % maxSize]);
        }
    }
    // 队列有效个数
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }
    // 显示头部数据
    public int showHead(){
        if (isEmpty()){
            System.out.println("队列为空，没有数据！");
            return 0;
        }
        return arr[front];
    }
}
```

### 使用链表实现



## 链表



# 非线性结构

## 图



## 树













