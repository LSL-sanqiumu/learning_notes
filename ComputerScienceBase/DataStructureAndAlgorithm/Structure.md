# ~~概念~~

数据项：数据的不可分的最小单位，一个数据元素可由若干个数据项组成。

数据元素：组成数据的、有一定意义的基本单位。

数据：描述客观事物的符号（对于计算机来说是可输入、可识别、可操作的）。

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

# 数据结构

![](img/0.type.png)

**概述：**

抽象的终要落地，逻辑数据结构无非是在顺序存储和链式存储的基础上的抽象延伸。

**线性表：**

线性表是零个或多个的数据元素的有限序列，元素之间有顺序，除了第一个和最后一个，其余的都有一个直接前驱元素和一个直接后驱元素。在线性表的顺序存储结构中，元素在存储空间中是紧密相连的、一个挨着一个的，如果需要删除某个元素则又需要把空出来的内存补上，如果需要插入则又需要移动元素来挪出一块地方来供插入。所以线性表的顺序存储的最大的缺点就是对插入和删除不友好，无论插入或删除都会有几率要移动元素。

线性结构：具体的常见的线性结构有：数组、链表、栈、队列和串等。

非线性结构：具体的结构形式为二维数组、多维数组、广义表、树结构和图结构等。

常用的数据结构：数组（array）、栈（stack）、队列（queue）、链表（linked list）、树（tree）、图（graph）、堆（heap）、散列表（hash）。

<img src="img/数据结构.svg" style="zoom:50%;" />

数据结构的学习：xxx结构是是什么？说出其优缺点和应用场景，如何实现。

# 复杂度

算法效率度量方法：

- 事后统计方法：完成算法后测试不同情况下的运行时间。
- 事前分析估算方法：算法程序编制前使用统计方法对算法进行估算（时间复杂度、空间复杂度）。

## 时间复杂度

**决定程序运行时间的因素：**时间复杂度，用来度量算法的运行时间，程序运行时的消耗时间取决于：

1. 算法采用的策略和方法；（算法好坏的根本）
2. 编译产生的代码质量；（软件因素）
3. 问题的输入规模；
4. 机器执行指令的速度。（硬件因素）

参考时间复杂度的方法由来：[（数据结构）十分钟搞定时间复杂度（算法的时间复杂度） - 简书 (jianshu.com)](https://www.jianshu.com/p/f4cca5ce055a)

**使用大O阶方法：**（函数的阶数对函数的增长速率影响最大）

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

| **时间复杂度** | **阶**             | **f(n) 举例** |
| :------------: | :----------------- | :------------ |
|   常数复杂度   | O(1)               | 1             |
|   对数复杂度   | O(logn)            | logn + 1      |
|   线性复杂度   | O(n)               | n + 1         |
| 线性对数复杂度 | O(nlogn)           | nlogn + 1     |
|   k 次复杂度   | O(n²)、O(n³)、.... | n² + n +1     |
|   指数复杂度   | O(2n)              | 2n + 1        |
|   阶乘复杂度   | O(n!)              | n! + 1        |

最好情况时间复杂度，最坏情况时间复杂度。平均情况时间复杂度。一般都算最坏的。



## 空间复杂度

**空间复杂度：**（程序运行中需要的存储）

- 空间复杂度和时间复杂度一样，反映的也是一种趋势，只不过这种趋势是代码运行过程中**临时变量**占用的内存空间。
- S(n)=O(f(n))，n为问题规模（数据集大小），f(n)为关于n所占存储空间的函数。

代码在计算机中的运行所占用的存储空间呐，主要分为 3 部分：

- 代码本身所占用的。
- 输入数据所占用的。
- 临时变量所占用的。
- 前面两个部分是本身就要占这些空间，与代码的性能无关，所以我们**在衡量代码的空间复杂度的时候，只关心运行过程中临时占用的内存空间。**

空间复杂度分析例如：

```java
// 传入的数据所占内存不计，临时变量sum和i都只开一份内存，是常阶数
// 空间复杂度就是 O(1)
public void sum(int arr[]){
    int sum = 0;
    for(int i = 0; i < arr.length; i++){
    	sum += arr[i];
    }
}
// O(n)
public void appendNum(int arr[]){
    ArrayList list = new ArrayList();
    for(int i = 0; i < arr.length; i++){
    	list.add(i);
    }
}
```



# 数组

## 数组

数组是可以在内存中连续存储多个数据元素的结构，在内存中的分配也是连续的，数组中的元素通过数组下标进行访问，数组下标从0开始。

优点：按照索引访问元素，速度快、按照索引遍历数组方便。

缺点：

1. 数组的大小固定后就无法扩容了（如果要扩容得新建另一个数组）。
2. 数组只能存储一种类型的数据。
3. 添加，删除的操作慢，因为要移动其他的元素。

适用场景：频繁查询，对存储空间要求不大、很少增加和删除的场景下。

## 稀疏数组

- 使用场景：当一个数组中大部分元素是同一个值时。
- 稀疏数组处理方法：把一个数组中不同值的信息（行、列和对应值）以及数组的信息（row、column、有多少个不同的值）都记录进另一个小规模的数组（稀疏数组）。

# 队列Queue

队列是一个有序列表，遵循先入先出原则，可用数组和链表实现。

## **单队列：**

<img src="img/1.1queue_array.png" style="zoom: 50%;" />

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

## **循环队列：**

**使用环形数组实现：**

<img src="img/1.2queue_circle_array.png" style="zoom:50%;" />

使用数据简单实现队列中存在一个问题：就是一个队列满载后，即使取出一些数据把位置“空出来”，但空出来的位置已经是不能复用的了，那怎么才能实现一个当满载后再取出数据后还能往队列添加数据的队列呢？答案就是：在数组中把一个位置当为缓冲区，满载时，当取出一个数据，就把这个缓冲区转化为队列的有效位置，而取出数据后空出来的位置就再次充当缓冲区，这样就能把头尾“相连”起来了，就实现了一个环形的数组充当的队列。（如果不满载，缓冲区就逐渐后移就好）

具体实现思路：

1. 先要初始化队列（包含数组、front（队列头数据索引）、rear（缓冲区索引）），注意环形数组需要一个容量充当缓冲区，所以实现的队列的有效容量为最大容量减去1；
2. 考虑maxSize、front、rear的联系（这里利用了它们之间的一些算法）。

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



# 链表LinkedList

链表就是链式存储实现的线性表。链表使用节点来存储数据，所谓节点，只不过是一个包含这些信息的类的对象：包含要存储的信息和其他存储着相同类型信息的节点对象。

```java
// 一个简单的节点类
class Node{
    private String info; // 存储的信息
    private Node next; // 节点对象
    其他方法...
}
```

## 单向链表

单向链表的节点只能单向指向下一个节点，也可以通过节点存储的信息确定存储的顺序。

**1.确定节点要存储的信息：**

```java
class HeroNode {
    // 节点要存储的信息
    public int no;
    public String name;
    public String nickname;
    // 指向下一个节点
    public HeroNode next;
    // 已经忽略节点的构造方法和toString方法
}
```

**2.链表功能的实现：**

```java
// 这是一个单向链表
class SingleLinkedList {
    // 链表头部不存储数据，只用于定位首个节点有无
    private HeroNode head = new HeroNode(0,"","");
    // 显示链表所有数据
    public void show(){
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head.next;
        while (true){
            System.out.println(temp.toString());
            if (temp.next == null){
                break;
            }
            temp = temp.next;
        }
    }
}
```

a.链表操作：简单的节点添加操作

```java
// 添加节点：遍历到列表的最后一个数据，加上引用完成节点添加
public void add(HeroNode heroNode){
    HeroNode temp = head;
    // 如果遍历到最后一个节点，退出循环
    while (true){
        if (temp.next == null) {
            break;
        }
        temp = temp.next;
    }
    // 添加节点
    temp.next = heroNode;
}
```

b.链表操作：节点存储信息带序号，要求链表中节点按序号排序并且序号不重复

```java
// 有编号的节点，按编号顺序添加
public void addByOrder(HeroNode heroNode){
    HeroNode temp = head;
    // 判断是否有已经有相同编号的标志位
    boolean falg = false;
    // 遍历查询
    while (true) {
        // 如果遍历到末尾，说明可以直接在末尾添加节点
        if (temp.next == null){
            break;
        }
        // 判断是否存在重复的编号
        if (temp.next.no > heroNode.no){
            break;
        }else if (temp.next.no == heroNode.no){
            falg = true;
            break;
        }
        // 指针后移，实现遍历
        temp = temp.next;
    }
    // 添加操作，flag为true表示存在相同编号的节点，不能再进行添加操作
    if (falg) {
        System.out.println("存在相同编号的数据，不能再添加！");
    }else {
        heroNode.next = temp.next;
        temp.next = heroNode;
    }
}
```

c.链表操作：根据传入的节点来修改节点的信息

```java
// 修改节点信息
public void update(HeroNode newNode){
    HeroNode temp = head.next;
    if (temp == null){
        System.out.println("没有节点！");
        return;
    }
    // 标志位，存在相同编号的节点即视为能进行修改操作
    boolean flag = false;
    while (true) {
        if (temp == null) {
            System.out.println("已经遍历完全部节点。");
            break;
        }
        if (temp.no == newNode.no){
            flag = true;
            break;
        }
        temp = temp.next;
    }
    if (flag) {
        temp.name = newNode.name;
        temp.nickname = newNode.nickname;
    }else {
        System.out.println("该节点不存在，无法更改！");
    }
}
```

d.链表操作：根据编号删除节点，失去引用的节点会被垃圾回收

```java
// 删除节点
public void delete(int no) {
    HeroNode temp = head;
    boolean flag =false;
    if (temp.next == null){
        System.out.println("链表为空！");
        return;
    }
    while (true){
        if (temp.next == null){
            break;
        }
        if (temp.next.no == no){
            flag = true;
            break;
        }
        temp = temp.next;
    }
    if (flag){
        temp.next = temp.next.next;
    }else {
        System.out.println("要删除的节点不存在！");
    }
}
```



## 双向链表

双向链表即在单链表的每个节点，再设置一个指向其前驱节点的指针域。

**1.确定节点要存储的信息**

```java
class Node {
    // 节点要存储的信息
    public int no;
    public String name;
    public String nickname;
    // 指向下一个节点
    public Node next;
    // 指向前一个节点
    public Node pre;
}
```

**2.链表功能的实现**

```java
public class DoubleLinkedList {
    // 链表头
    private Node head = new Node(0,"","");
    // 返回头节点
    public Node returnHeadNode(){
        return head;
    }
```

功能1：显示全部节点信息

```java
public void show(){
    if (head.next == null){
        System.out.println("链表为空！");
        return ;
    }
    Node temp = head.next;
    while (true){
        if (temp == null){
            break;
        }
        System.out.println(temp);
        temp = temp.next;
    }
}
```

功能2：添加节点进链表

```java
public void add(Node node){
    Node temp = head;
    while (true){
        if (temp.next == null){
            break;
        }
        temp = temp.next;
    }
    temp.next = node;
    node.pre = temp;
}
```

功能3：修改指定节点

```java
public void update(Node node){
    if (head.next == null){
        System.out.println("链表为空！");
        return;
    }
    Node temp = head.next;
    boolean flag = false;
    // 遍历寻找要修改的节点
    while (true){
        if (temp == null){
            System.out.println("已经遍历完全部链表！");
            break;
        }
        if (temp.no == node.no){
            flag = true;
            break;
        }
        temp = temp.next;
    }
    if (flag){
        temp.name = node.name;
        temp.nickname = node.nickname;
    }else {
        System.out.println("不存在");
    }
}
```

功能4：删除指定节点

```java
public void delete(int no) {
    Node temp = head.next;
    boolean flag =false;
    if (temp.next == null){
        System.out.println("链表为空！");
        return;
    }
    // 遍历：找到要删除的节点 temp.next
    while (true){
        if (temp == null){
            System.out.println("已经遍历全部节点！");
            break;
        }
        if (temp.no == no){
            flag = true;
            break;
        }
        temp = temp.next;
    }
    if (flag){
        temp.pre.next = temp.next;
        if (temp.next != null){
            temp.next.pre = temp.pre;
        }
    }else {
        System.out.println("要删除的节点不存在！");
    }
}
```

## 环形链表

### 形成单向环形链表：

```java
public class CircleLinkedList {
    private Girl headFirst = new Girl(-1);
    public void addGirl(int nums){
        if (nums < 1){
            System.out.println("nums的值错误！");
            return;
        }
        Girl curGirl = null;
        for (int i = 1; i <= nums; i++) {
            Girl girl = new Girl(i);
            if (i == 1){
                headFirst = girl;
                headFirst.setNext(girl);
                curGirl = headFirst;
            }else {
                curGirl.setNext(girl);
                girl.setNext(headFirst);
                curGirl = girl;
            }
        }
    }
    // 遍历
    public void showGirl(){
        if (headFirst == null){
            System.out.println("链表为空！");
            return;
        }
        Girl curGirl = headFirst;
        while (true){
            System.out.printf("小孩 %d \n", curGirl.getNo());
            if (curGirl.getNext() == headFirst){
                break;
            }
            curGirl =curGirl.getNext();
        }
    }

}

class Girl {
    private int no;
    private Girl next;
    public Girl(int no) {
        this.no = no;
    }
	...
}
```

### 解决约瑟夫问题：

```txt
设编号为1、2、3、...、n的n个人围成一圈，约定编号为k(1<=k<=n)，的人从1开始报数，数到m的那个人出列，他的下一位又从1开始报数，数到m的那个人又出列，直到所有人出列为止（由此产生一个出队编号的序列）。
```

```java
// 根据用户输入形成出圈的序列: 从哪开始数 每次数多少下 最初圈中有多少个
public void countGirl(int startNo, int countNum, int nums){
    if (headFirst == null || startNo < 1 || startNo > nums){
        System.out.println("参数有误！");
        return;
    }
    Girl helper = headFirst;
    while (true) {
        if (helper.getNext() == headFirst){
            break;
        }
        helper = helper.getNext();
    }
    // 确定到起始位置
    for (int i = 0; i < startNo - 1; i++) {
        headFirst = headFirst.getNext();
        helper = helper.getNext();
    }
    // 从起始位置开始报数出圈，直到只有一个节点
    while (true){
        if (helper == headFirst) {
            break;
        }
        // 按countNum移动，到达位置就出圈
        for (int i = 0; i < countNum - 1; i++) {
            headFirst = headFirst.getNext();
            helper = helper.getNext();
        }
        System.out.printf("小孩%d出圈\n",headFirst.getNo());
        headFirst = headFirst.getNext();
        helper.setNext(headFirst);
    }
    System.out.printf("最后留在圈中的是：%d\n",headFirst.getNo());
}
```

# 栈

## 概述

1. 栈（stack，又名堆栈）是一个先入后出（FILO-First In Last Out）的有序列表。 
2. 栈(stack)是一种特殊线性表，其元素的插入和删除只能在线性表的同一端进行。允许插入和删除的一端，也就是能变化的一端，称为栈顶(Top)，另一端为固定的一端，称为栈底(Bottom)。 
3. 根据栈的定义可知，最先放入栈中元素在栈底，最后放入的元素在栈顶，而删除元素刚好相反，最后放入的元素最先删除，最先放入的元素最后删除。

## 应用场景

1. 子程序的调用：在跳往子程序前，会先将下个指令的地址存到堆栈中，直到子程序执行完后再将地址取出，以回到原来的程序中。
2. 处理递归调用：和子程序的调用类似，只是除了储存下一个指令的地址外，也将参数、区域变量等数据存入堆栈中。
3. 表达式的转换[中缀表达式转后缀表达式]与求值(实际解决)。 
4. 二叉树的遍历。 
5.  图形的深度优先(depth 一 first)搜索法。

# 哈希(散列)表

散列表（Hash table，也叫哈希表），是根据关键码值(Key value)而直接进行访问的数据结构。也就是说，它通过把关键码值映射到表中的一个位置来访问该位置记录有的数据，以加快查找的速度。这个把关键码值映射到表中具体位置的映射函数叫做散列函数，存放记录的数组叫做散列表（哈希表）。

<img src="img/2.hashTable.png" style="zoom: 50%;" />

**实现hash表，以存储员工名字的hash表为例：**

## 实体类：

```java
public class Emp {
    private int id;
    private String name;
    private Emp next;
    // 构造器、get/set方法略
}
```

## 链表：

```java
public class EmpLinkedList {
    private Emp head;

    public EmpLinkedList(Emp head) {
        this.head = head;
    }

    public EmpLinkedList() {
    }
}
```

链表的功能函数：

```java
public void add(Emp emp){
    if (head == null){
        head = emp;
        return;
    }
    Emp curEmp = head;
    while (true) {
        if (curEmp.getNext() == null){
            break;
        }
        curEmp = head.getNext();
    }
    curEmp.setNext(emp);
}
// 遍历链表的信息
public void list(int no){
    if (head == null){
        System.out.println("第" + (no+1) + "条链表为空！");
        return;
    }
    System.out.print("第" + (no+1) + "条链表信息为");
    Emp curEmp = head;
    while (true){
        System.out.printf("=> id=%d name=%s\t",curEmp.getId(),curEmp.getName());
        if (curEmp.getNext() == null){
            break;
        }
        curEmp = curEmp.getNext();
    }
    System.out.println();
}
public Emp findEmpById(int id) {
    if (head == null) {
        System.out.println("链表为空~~~");
        return null;
    }
    Emp curEmp = head;
    while (true) {
        if (curEmp.getId() == id) {
            break;
        }
        if (curEmp.getNext() == null) {
            curEmp = null;
            break;
        }
        curEmp = curEmp.getNext();
    }
    return curEmp;
}
```

## 哈希表：

哈希表初始化：

```java
public class HashTable {
    private EmpLinkedList[] empLinkedLists;
    private int size;
    // 初始化哈希表结构
    public HashTable(int size){
        this.size = size;
        this.empLinkedLists = new EmpLinkedList[size];
        for (int i = 0; i < size; i++) {
            empLinkedLists[i] = new EmpLinkedList();
        }
    }
```

哈希表的映射函数：（以简单的求余运算为例）

```java
public int hashFun(int id){
    // 余数都不会超过模数size，因此求余出来的在整型值在 [0,size)，能映射到数组的下标
    return id % size; 
}
```

为哈希表添加功能函数，例如遍历所有存储在哈希表中的值、通过关键码查出含该关键码的数据、往表中加数据等：

```java
// 遍历哈希表
public void list(){
    for (int i = 0; i < size; i++) {
        empLinkedLists[i].list(i);
    }
}
// 往指定的EmpLinkedList添加雇员
public void add(Emp emp){
    int empLinkedListNo = hashFun(emp.getId());
    empLinkedLists[empLinkedListNo].add(emp);
}
```

通过关键码找数据：

```java
public void findEmpById(int id){
    int empLinkedListNo = hashFun(id);
    Emp emp = empLinkedLists[empLinkedListNo].findEmpById(id);
    if (emp == null){
        System.out.println("在哈希表中没有找到！");
    }else {
        System.out.printf("在第%d条链表中找到了 雇员id=%d name=%s \n",(empLinkedListNo + 1),id,emp.getName());
    }
}
```



# 树Tree

树，一种数据结构 ，它是由 n (n>=1 )个有限节点组成一个具有层次关系的集合 。

这种数据结构能提高数据存储、读取的效率，例如利用二叉排序树(Binary Sort Tree)，既可以保证数据的检索速度，同时也可以保证数据的插入、删除和修改的速度。

![](img/5.堆.png)

## 二叉树

二叉树：每个节点最多只能有两个子节点。

![](img/3.二叉树.png)

- 满二叉树：所有子节点都在最后一层，并且节点总数是2^-1（n为层数）的树。
- 完全二叉树：叶子结点只能出现在最下层和次下层，且最下层的叶子结点集中在树的左部并连续，次下层的叶子节点集中在树的右部且连续。（需要注意的是，满二叉树肯定是完全二叉树，而完全二叉树不一定是满二叉树。）

### **创建二叉树：**

1.二叉树使用节点存储信息，所以先把节点建起来：

```java
public class Node {
    private int id;
    ......                  // 节点存储的信息
    private Node leftNode;  // 存储左节点的地址
    private Node rightNode; // 存储右节点的地址

    public Node(int id) {
        this.id = id;
    }
    ...... // get、set方法
}
```

2.创建节点并把节点连成树：（这里使用手动方式）

```java
public class ThisIsATree {
    public static void main(String[] args) {
        Node root = new Node(6);
        Node n1 = new Node(5);
        Node n2 = new Node(10);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(8);
        Node n6 = new Node(9);
        Node n7 = new Node(2);
        // 第一层root
        // 第二层
        root.setLeftNode(n1);
        root.setRightNode(n2);
        // 第三层
        n1.setLeftNode(n3);
        n1.setRightNode(n4);
        n2.setLeftNode(n5);
        n2.setRightNode(n6);
        // 第四层
        n3.setLeftNode(n7);
    }
}
```

### **二叉树遍历：**

- 前序遍历：先输出父节点，再遍历左子树，最后遍历右子树。
- 中序遍历：先遍历左子树，再输出父节点，再遍历右子树。
- 后续遍历：先遍历左子树，再遍历右子树，最后再输出父节点。
- （看父节点输出顺序，就可以确定是哪个遍历）

```java
// 前序遍历
public void preOrder(){ 
    System.out.println(this);
    // 遍历树中所有左节点
    if (this.leftNode != null){
        this.leftNode.preOrder();
    }
    // 遍历树中所有右节点
    if (this.rightNode != null){
        this.rightNode.preOrder();
    }
}
// 中序遍历
public void infixOrder(){
    if (this.leftNode != null){
        this.leftNode.infixOrder();
    }
    System.out.println(this);
    if (this.rightNode != null){
        this.rightNode.infixOrder();
    }
}
// 后续遍历
public void postOrder(){
    if (this.leftNode != null){
        this.leftNode.postOrder();
    }
    if (this.rightNode != null){
        this.rightNode.postOrder();
    }
    System.out.println(this);
}
```

总结：

1. 使用了递归实现左节点、右节点的遍历。
2. 快速确定遍历输出：可看成一颗颗小树，父、左（父、左、右）、右（父、左、右）。

### 二叉树查找：

```java
// 节点
public Node preOrderSearch(int id){
    if (this.id == id){
        return this;
    }
    Node result = null;
    if (this.leftNode != null){
        result = this.leftNode.preOrderSearch(id);
    }
    if (result != null){
        return result;
    }
    if (this.rightNode != null){
        result = this.rightNode.preOrderSearch(id);
    }
    return result;
}
public Node infixOrderSearch(int id){
    Node result = null;
    if (this.leftNode != null){
        result = this.leftNode.preOrderSearch(id);
    }
    if (result != null){
        return result;
    }
    if (this.id == id){
        return this;
    }
    if (this.rightNode != null){
        result = this.rightNode.preOrderSearch(id);
    }
    return result;
}
public Node postOrderSearch(int id){
    Node result = null;
    if (this.leftNode != null){
        result = this.leftNode.preOrderSearch(id);
    }
    if (result != null){
        return result;
    }
    if (this.rightNode != null){
        result = this.rightNode.preOrderSearch(id);
    }
    if (result != null){
        return result;
    }
    if (this.id == id){
        return this;
    }
    // 如果找不到，返回null
    return result;
}
```

```java
// 树
public Node preOrderSearch(int id){
    if (root != null){
        return root.preOrderSearch(id);
    }else {
        return null;
    }
}
public Node infixOrderSearch(int id){
    if (root != null){
        return root.infixOrderSearch(id);
    }else {
        return null;
    }
}
public Node postOrderSearch(int id){
    if (root != null){
        return root.postOrderSearch(id);
    }else {
        return null;
    }
}
```

### 删除节点

```java
// 节点：删除叶子节点或父子节点的子树
public void delNode(int id){
    if (this.leftNode != null && this.leftNode.id == id){
        this.leftNode = null;
        return;
    }
    if (this.rightNode != null && this.rightNode.id == id){
        this.leftNode = null;
        return;
    }
    if (this.leftNode != null){
        this.leftNode.delNode(id);
    }
    if (this.rightNode != null){
        this.rightNode.delNode(id);
    }
}
```

```java
// 树
public void delNode(int id){
    if (root != null){
        if (root.getId() == id){
            root = null;
        }else {
            root.delNode(id);
        }
    }else {
        System.out.println("空树！");
    }
}
```

如果目标节点是非叶子节点，如何只删除该节点但不删除其下面的子节点？

- 如果是叶子节点就可以直接删除。
- 如果不是叶子节点，那么可以指定如下规则：
  1. 如果该非叶子节点只有一个子节点，那么该子节点就替代被删除的节点的位置。
  2. 如果该非叶子节点有两个子节点，那么左子节点就替代被删除的节点的位置，右子节点就变为该子节点的右子节点。





## 顺序存储二叉树

![](img/4.顺序存储二叉树.png)

```java
public class ArrayBinaryTree {
    private int[] arr;
    public ArrayBinaryTree(int[] arr){
        this.arr = arr;
    }

    public static void main(String[] args) {
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(new int[]{1,2,3,4,5,6,7});
        arrayBinaryTree.preOrder(); // 1245367
    }
    // 树的前序遍历
    public void preOrder(){
        this.preOrder(0);
    }
    public void preOrder(int index){
        if (arr == null || arr.length == 0){
            System.out.println("空数组，不能转为二叉树！");
        }
        // 输出当前元素
        System.out.println(arr[index]);
        if ((index*2+1) < arr.length){
            preOrder(2*index + 1);
        }
        if ((index*2+2) < arr.length){
            preOrder(2*index + 2);
        }
    }
}
```

实际应用：堆排序。

## 线索化二叉树

<img src="img/6.线索化二叉树.png" style="zoom: 50%;" />

如上图，当线索化二叉树后，Node节点属性的leftNode、rightNode有以下两种情况：

1. leftNode可能指向的是其节点对象的左子树或前驱节点。（如：节点1的指向左子树，节点10的指向前驱节点）
2. rightNode可能指向的是其节点对象的右子树或后继节点。（如：节点1的指向右子树，节点14的指向后继节点）

代码实现中序线索二叉树，在二叉树的基础上加上线索化的功能：





# 堆heap

堆是什么？堆是一类特殊的树，是为了实现排序而设计的一种数据结构，它不是面向查找操作的。堆的通用特点就是父节点会大于或小于所有子节点。堆并不一定就是完全二叉树，平时使用完全二叉树的原因是易于存储、便于索引。

- 大顶堆：每个结点的值都**大于**或**等于**其左右孩子结点的值。
- 小顶堆：每个结点的值都**小于**或**等于**其左右孩子结点的值。
- 一般升序使用大顶堆，降序使用小顶堆。

## 二叉堆





# 图graph













