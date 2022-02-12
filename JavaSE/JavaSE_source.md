集合

## ArrayList源码分析

### 构造器源码

无参构造器：

<img src="source_img/1.arrList_cons.png" style="zoom:67%;" />

调用ArrayList的无参构造器，会创建一个空的Object类型的数组。

<img src="source_img/2.arrList_cons2.png" style="zoom:67%;" />

调用ArrayList的有参构造器，会根据传入的整型参数创建一个长度为该参数值的Object数组。

### add()流程探究

![](source_img/3.add_1.png)

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





