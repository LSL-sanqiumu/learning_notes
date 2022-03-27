## BFC

## 概念

BFC（Block Formatting Context），格式化上下文，指一个独立的渲染区域，或者说是一个隔离的独立容器。

（一个独立的封闭空间，不会影响到其外面的内容。）

## 条件

可以形成BFC的元素有：

1. 浮动元素，float；
2. 绝对定位元素，position；
3. 元素的display为（inline-block、table-cell、table-caption、flex）中的一个；
4. 元素的overflow除visible以外的值（hidden、auto、scroll）；
5. 根元素body。

## 特性

1. 内部的box会在垂直方向上一个接一个放置；
2. 垂直方向上的距离由margin决定；
3. BFC的区域不会和浮动（float）的元素的区域重叠；
4. 计算BFC的高度，浮动元素也参与计算；
5. BFC就是页面上的一个独立容器，容器里面的子元素不会影响外面的元素。