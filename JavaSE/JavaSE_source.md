# List

## ArrayList源码分析

### 构造器源码

无参构造器：

<img src="source_img/1.arrList_cons.png" style="zoom:67%;" />

调用ArrayList的无参构造器，会创建一个空的Object类型的数组。

<img src="source_img/2.arrList_cons2.png" style="zoom:67%;" />

调用ArrayList的有参构造器，会根据传入的整型参数创建一个长度为该参数值的Object数组。

### add()流程探究

使用到的测试代码：

![](source_img/3.add_1.png)

方法执行流程：（无参构造、首次添加数据）

<img src="source_img/444.svg" style="zoom:150%;" />

不引起扩容的情况下，使用无参构造器并add()的流程如下：

1. 调用ArrayList的空参构造器，ArrayList底层会创建一个空数组用于存放数据。

2. （调用ArrayList的add()方法时，添加的是基本数据类型会自动装箱成包装类）。

3. add()方法里调用ensureCapacityInternal()方法：

   1. 先调用calculateCapacity()方法来计算出需要的最小容量：

      - 如果elementData是默认的空数组`DEFAULTCAPACITY_EMPTY_ELEMENTDATA`，那么就进入if语句里，取出`DEFAULT_CAPACITY`（这个的值为10，也就是默认的初始容量）。
      - 如果elementData不是空数组，那么就返回传入的形参值`size+1`（size初始值为0）。

   2. 再调用ensureExplicitCapacity()方法，判断是否需要扩容，如果elementData数组的长度比minCapacity小，那就调用grow()方法来进行扩容。

      - 第一次往集合add()数据时，minCapacity为10，而elementData为0，所以会进入if语句来调用grow()方法进行扩容。
      - 第二次往集合add()数据时，此时minCapacity=size+1=2，而elementData.length=10，因此不需要扩容。

   3. grow()方法：（如果elementData数组的长度比minCapacity小，那就调用grow()方法来进行扩容）

      - ```java
        private void grow(int minCapacity) { //  minCapacity=10
            int oldCapacity = elementData.length; // oldCapacity=0
            // 扩容1.5倍
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            // 如果newCapacity比minCapacity小，那就扩容
            if (newCapacity - minCapacity < 0)
                newCapacity = minCapacity;
            /* 如果扩容后长度比MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8大
           	   那就执行hugeCapacity来返回Integer.MAX_VALUE或 MAX_ARRAY_SIZE*/
            if (newCapacity - MAX_ARRAY_SIZE > 0)
                newCapacity = hugeCapacity(minCapacity);
            // 保留原有数据，完成扩容
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
        ```

4. 总结以上：

   1. ArrayList的空参构造器，不会初始化底层数组对象的容量。
   2. 由空参构造器创建的集合，第一次往集合添加元素时才将底层数组对象设置容量，默认为10。
   3. 需要扩容的时候，默认扩容为当前集合容量的1.5倍。
   4. 集合容量有限，最大容量为Integer.MAX_VALUE。

执行到第二个for循环时，会进行扩容：

1. size表示底层数组的下标，当第十一次添加数据时，size=10，此时minCapacity=size+1=11。
2. 接下来的流程和上面一样，最后会进入grow()方法来进行扩容。
3. 进入grow方法的扩容过程：
   1. oldCapacity = elementData.length = 10；
   2. newCapacity = oldCapacity + (oldCapacity >> 1) = 10 + 10 / 2 = 15；
   2. newCapacity - minCapacity < 0 不成立；
   2. newCapacity - MAX_ARRAY_SIZE > 0 不成立；
   2. elementData = Arrays.copyOf(elementData, newCapacity) ===> 创建新数组并复制旧数据，扩容完成。

如果使用有参构造器，仅是构造器的差别，add()的方法的执行逻辑是没有什么变化的。

```java
ArrayList arrList = new ArrayList(5);
```

```java
public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }
```

ArrayList的有参构造器：

1. 传入的参数即为底层elementData数组的初始容量。
2. 扩容是在add()方法执行中来完成的，扩容时扩容为原来容量的1.5倍。

看源码可以这么做：先去看实现了什么效果，然后带着问题去追源码（是怎么实现这个效果的？）。

## Vector源码分析

### 构造器源码

使用无参构造器，底层elementData数组初始化容量为10：

<img src="source_img/4.vector_cons.png" style="zoom: 67%;" />

使用有参构造器，传入参数即为底层elementData数组初始化容量：

<img src="source_img/4.vector2_cons.png"  />

### add()流程探究

测试代码：

![](source_img/5.v_add.png)

方法流程：

<img src="source_img/555.svg" style="zoom:120%;" />

add()的流程如下：

1. 因为使用无参构造器，底层elementData数组默认初始化容量为10。

2. 使用add()为Vector对象添加基本数据类型的数据，会先进行装箱。

3. add()方法里调用了ensureCapacityHelper()方法，该方法用来确定容量是否需要扩容：

   - 当容量不足时，需要扩容，就会调用grow()方法，该方法源码如下：

     ```java
     private void grow(int minCapacity) {
         // 10
         int oldCapacity = elementData.length;
         // capacityIncrement=0，所以只要需要扩容就都是扩容2倍
         int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                          capacityIncrement : oldCapacity);
         if (newCapacity - minCapacity < 0)
             newCapacity = minCapacity;
         if (newCapacity - MAX_ARRAY_SIZE > 0)
             newCapacity = hugeCapacity(minCapacity);
         elementData = Arrays.copyOf(elementData, newCapacity);
     }
     ```

结论：

1. add()方法被synchronized修饰，加了同步锁，是线程安全的。
2. Vector每一次扩容都扩为原来的两倍。

使用有参构造器，初始化的容量是传入的参数值，扩容机制在add()的过程中实现，仍然是按两倍扩容的方式扩容。

## LinkedList源码分析



















