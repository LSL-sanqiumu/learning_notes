# CSS

## css的引入

```html
<link rel="stylesheet" type="text/css" href="" media="all" />
```

## 选择器

### 标签选择器

以HTML标签作为选择器。

### 类选择器

> 语法格式：`.classname` （.加上类名）

使用时要为元素赋予class属性和值，如果要为元素的class属性设置多个属性值时则要**用空格隔开**。

句法：

- **.类名{属性：值；......}**
- **元素选择符.类名{属性：值；......}**          选择有该类的元素
- **类选择符.类名{属性：值；......}**              选择同时有该两个class属性值的元素，不区分先后顺序
- **元素选择符.类名.类名{属性：值；......}**  选择该元素而且拥有这两个class值的元素

### ID选择器

> 语法格式：**#+ID值**（#是一个散列字元，也叫井号、哈希字符、哈希记号、三连棋棋盘）

使用时要为元素设置ID属性和值。

句法：

- **#ID属性值 {属性：值；......}**

注意：ID选择符不能串在一起使用，因为ID属性值不能是以空格分隔的列表，其他和类选择符基本一致。

### 复合选择器

后代

子（子元素）选择器

并集选择器

伪类选择器





## 字体

font-style：italic是斜体，normal是不倾斜。

复合属性：`font: font-style font-weight  font-size/line-height font-family;`

1. 按上面顺序书写，属性间以空格隔开；
2. 不需要设置的属性值可以省略（会使用默认值），但font-size、font-family必须存在；
3. font：正斜 粗细 大小/行高 字体；

## 文本属性

文本的外观，比如文本的颜色、对齐文本、装饰文本、文本缩进、行间距等。

- color：文本颜色，规范建议使用十六进制的RGB颜色值，字母小写，颜色可以缩写时使用缩写；
- text-align：文本对齐，left（默认）、right、center；
- text-decoration：装饰文本，给文本添加下划线、删除线、上划线等，none（默认）、underline（下划线）、overline（上划线）、line-through（删除线）；
- text-indent：文本缩进，通常是将段落的首行缩进，数值，单位px或em等，em是相对单位，相对当前元素（font-size）的1个文字的大小，如果当前元素没有设置font-size，则相对父元素；
- line-height：行高（行间距），行间距由上间距、文本高度、下间距组成，值、像素，行高不加单位时指当前元素的多少倍；
- text-shadow：文字阴影，只有四个值（和盒子阴影的前四个值是一样的）。

## 元素显示模式

元素显示模式：元素以什么方式进行显示；掌握元素的显示方式阔以有助于对元素的使用。

分类：块元素、行内元素、行内块元素；

块元素特点：

1. 独占一行；
2. 高度、宽度、内边距、外边距可以控制；
3. 宽度默认是父级宽度的100%；
4. 是一个容器及盒子，里面可以放行内元素、块元素（文字类的除外，比如p）。

行内元素特点：

1. 一行可显示多个行内元素；
2. 宽度、高度直接设置无效；
3. 默认宽度为其本身内容宽度；
4. 内容只能是容纳文本或其他行内元素。
5. a标签内不能再放a标签，a标签内可以放块级元素，但给a转换为块元素更安全。

行内块元素：img、input、td，同时具有块元素和行内元素的一些特点：

1. 一行可显示多个，两个行内块元素间存在间隙，很难控制这个间隙；
2. 默认宽度为其本身内容的宽度；
3. 高度、宽度、内边距、外边距可以控制。

不同模式间的转换：display

## 背景

背景颜色：background-color：transparent（透明的）、颜色；

背景图片：background-image：url()；

背景平铺：background-repeat：repeat（默认）、no-repeat、repeat-x、repeat-y；

背景位置：background-position：x y；

- x坐标、y坐标，x和y可以使用精确单位或方位名词（top、bottom、right、left、center），如果只指定一个方位名词，另一个忽略，此时忽略的是默认居中;
- 如果是精确单位，第一个一定是x坐标，第二个一定是y坐标；
- 如果只指定一个数值，那么这个数值一定是x坐标，另一个默认center；
- 如果使用混合使用，第一个一定是x坐标，第二个一定是y坐标。

背景固定：background-attachment：scroll（随内容滚动，默认）、fixed（固定）；

```css
/* 复合写法，位置没有限制 */
background：背景颜色 背景图片地址 背景平铺 背景图像滚动 背景图片位置；
```

css3，ie9+才支持：背景色半透明：`background-color：rgba(0, 0, 0, .8);`，a是alpha。

## CSS三大特性

### 层叠性

层叠性主要解决样式冲突问题，相同选择器设置相同的样式（值不同），此时一个样式就会覆盖另一个冲突的样式。

重叠性原则：就近原则，哪个离body结构近哪个就执行，注意**只是覆盖冲突的样式，并不是全部覆盖**。（如果不同css文件样式冲突呢？一般都不会出现不同文件下的样式冲突，因为样式文件都对应一个结构）。

### 继承性

子元素继承父元素的某些样式，如text-、font-、line-、这些开头的可以继承，以及color属性。

line-height：可以不加单位，不加单位时表倍数，当前元素的倍数。

常用的css可继承的属性：

- font：组合字体

- font-family：规定元素的字体系列

- font-weight：设置字体的粗细

- font-size：设置字体的尺寸

- font-style：定义字体的风格

- text-indent：文本缩进

- text-align：文本水平对齐

- line-height：行高

- color：文本颜色

- visibility：元素可见性

- 光标属性：cursor


### 优先级/特指度

解决不同书写方式下的相同内容样式冲突问题

选择器不同时，看权重：

- 继承或者*、连结符（>、+等）：`0,0,0,0`；
- 元素选择器：`0,0,0,1`；
- 类、属性选择、伪类选择器：`0,0,1,0`；
- ID选择器：`0,1,0,0`；
- 行内样式style=""：`1,0,0,0`；
- `!important`最重要：无穷大。

复合选择器的权重叠加：

权重会叠加但不会有进位，权重比较是从左往右一个个比较。

## 盒模型

![](image/盒子模型.jpg)

盒子模型有两个，标准盒模型和替代盒模型：

- 标准盒模型（box-sizing：content-box）（默认的）：width和height是指content的大小，整个盒子的大小为border+padding+content；
- 替代盒模型（box-sizing：border-box）：width和height是指整个盒子的大小；
- 指定了width和height时，它俩一个是content不变（标准盒模型），一个是盒子大小不会变（替代盒模型）。

### **border：**边框

border：border-width border-style border-color；简写没有顺序要求，规范这样写。

border-style：solid(实线) dashed(虚线) dotted(点线)；

border-top：宽 样式 颜色；

border-collapse:collapse；把相邻边框合并在一起；

圆角边框：border-radius：半径；(椭)圆与边框的交集行成圆角效果，值为半径或百分比。



### **padding：**内边距

padding-x：值；x：left、top、bottom、right；

padding：5px；上下左右都是5px；

padding：5px 10px；上下是5px，左右是10px；

padding：5px 10px 15px；上、右、下，左的复值使用右的；

padding：top right bottom left；四个值，上右下左的顺序；

总结：如果没有指定值某个方位的值，则按照上-下、左-右的关系来确定，padding的值的顺序按照顺时针排放（上右下左）。

注意：如果盒子本身没有指定width/height，则padding不会影响盒子的width/height，（继承的width、height不算指定，width、height不是不能继承吗）。

### **margin：**外边距

margin-x：值；x：left、top、bottom、right；和padding基本一致。

margin：0 auto；块级元素必须设置了宽度才能实现水平居中。行内块、行内元素居中对齐则是给父元素使用text-aligin：center；

关于外边距的问题：父子元素外边距会合并

1. 解决相邻元素垂直外边距的合并：尽量只给一个盒子加margin值；
2. 浮动的元素没有内外边距合并问题；
3. 嵌套块元素垂直外边距的塌陷：
   - ![](image/margin塌陷问题.png)
4. 总结就是：上下相邻或嵌套的块级盒子会垂直方向上发生外边距合并（浮动的元素不会）。

清除网页元素的内外边距：

```css
* {
    padding: 0;
    margin: 0;
}
```

注意：行内元素为了照顾兼容性，尽量只设置左右内外边距，不要设置上下内外边距。

### box-shadow:盒子阴影

CSS3新增盒子阴影：`box-shadow：h-shadow v-shadow blur spread color inset;`

![](image/box-shadow.png)

盒子阴影不占用空间，不会影响其他的盒子。

## 浮动

浮动元素特性：脱离文档流、浮动的盒子不再保留原来的位置、浮动元素一行中显示且顶端对齐、具有行内块特性。

浮动初衷就是为了解决文字环绕的；浮动元素经常与标准流父级元素搭配使用；浮动元素不会压住它下面标准流的文字或图片。

清除浮动：

- 清除浮动的本质就是清除浮动元素造成的影响；
- 如果父元素有高度就不需要清除；
- 清除浮动后，父元素就会根据浮动的子盒子自动检测高度；
- 清除浮动的策略是闭合浮动。

清除浮动的方法：（**清除浮动就是元素块远离某种类型（左或右）浮动的元素或远离所有（both）的浮动元素**）

1. 额外标签法（隔墙法），W3C推荐的做法：在最后一个浮动的元素的后面添加一个清除了浮动的块级元素；（不常用）

   ```html
   style {
   	.float1 { float: left; }
   	.clear { clear: both; }
   }
   <div>
       <div class="float1"></div>
       <div class="float1"></div>
       <div class="float1"></div>
       <div class="clear"></div>
   </div>
   ```

   

2. 父级添加overflow属性，将其属性值设为hidden、auto或scroll；（代码简单但无法显示溢出的部分）

3. 父级添加after伪元素（额外标签法的升级版）；（没有增加标签，结构更简单，但需要考虑版本兼容）

   ```css
   .clearfix:after {	
   	content: "";
   	display: block;
   	height: 0;
   	clear: both;
   	visibility: hidden;
   }
   .clearfix {   /* IE6、7 专用 */
   	*zoom: 1;
   }
   ```

   

4. 父级添加双伪元素。

   ```css
   .clearfix:before, 
   .clearfix:after {	
   	content: "";
   	display: table;
   }
   .clearfix:after {	
   	clear: both;
   }
   .clearfix {   /* IE6、7 专用 */
   	*zoom: 1;
   }
   ```

## 切图





## 四种定位

定位，常用于盒子自由的在某个盒子内移动或将其固定在屏幕中某个位置（可在覆盖在盒子上层）。

四种定位：

1. `position：static`：静态定位，默认的定位方式，相当于没有偏移量的定位；
2. `position：relative`：相对定位，相对于本身原来的位置来设置偏移量进行定位（原有位置继续占有，不会脱离标准流）；
3. `position：absolute`：
   - 绝对定位，相对于祖先元素进行定位（如果没有祖先元素或祖先元素没有定位，则相对于浏览器进行定位）；
   - 如果祖先元素有定位，则以最近的有定位的祖先元素为参考点来偏移；
   - 绝对定位会脱离标准流，原有位置不再保留占有。
4. `position：fixed`：固定定位，将元素固定于浏览器可视区域。
   - 以浏览器可视窗口为参照点，跟父元素没有关系，不随滚动条滚动；
   - 脱离标准流，可以看作是特殊的绝对定位。
5. `position：stickey`：粘性定位，以可视窗口为参考，占有位置。

结合top、bottom、left、right四个属性使用，这四个属性表示偏移量，是相对于元素边线的偏移量。

子绝父相：子级使用绝对定位，父级要用相对定位

- 子级绝对定位，脱离标准流，不会影响其他盒子的位置。

叠放顺序：

- `z-index`：正、负、0、auto（默认），数值越大在越位于前面。

定位的特殊性：

- 行内元素添加了绝对定位或固定定位，可直接设置宽高；（这两个都脱离了标准流，相当于行内块）
- 块级元素添加了绝对定位或固定定位，如果不设置宽高，则默认是内容大小；
- 绝对定位或固定定位都脱离标准流，不会触发外边距合并问题；
- 绝对定位或固定定位会压住它下面的标准流的所有内容，浮动则不会压住文字或图片。

## 显示与隐藏

让一个元素在页面中显示或隐藏。

1. display：显示隐藏，使用none属性值，block属性值还有显示元素的意思；
2. visibility：显示隐藏，visible（元素可视）、hidden（元素隐藏），隐藏后继续占有原来的位置；
3. overflow：隐式隐藏，溢出隐藏，visible、hidden、scroll（总是有滚动条）、auto（在溢出的时候出现滚动条）。

## CSS高级技巧

### 精灵图

精灵图（sprites）：为了有效地减少请求的次数，提高访问速度

- 把多张小背景图整合到一张大背景图中，主要针对背景图使用；
- 移动大背景图，得到小的背景图；
- 一般背景图都是往左往上移动，是负值。

### 字体图标

字体图标iconfont的使用：展示的是图标，本质是文字，常用于结构样式简单的小图标

1. 下载后得到一个字体文件的压缩包，解压后把fonts文件夹放到页面的根目录；
2. 用CSS引入字体文件，用`@font-face`字体声明；
3. 打开解压后得到的index.html，找到需要的字体图标，复制右下角的小方框；
4. 粘贴复制的小方框到指定的位置，再修改引入位置的字体族，字体图标引入成功。

字体图标下载网站：

1. icomoon字体库：[Icon Font & SVG Icon Sets ❍ IcoMoon](https://icomoon.io/)；
2. 阿里巴巴的：[iconfont-阿里巴巴矢量图标库](https://www.iconfont.cn/)。

追加字体图标：

- 如果需要重新追加图标，可使用解压包文件里的selection.json上传到图标下载网站；
- 然后添加新的图标再生成字体文件，下载字体压缩包，解压后用新的fonts替换原来的fonts文件夹。

### ###css 画三角

```css
.box {
    width: 0;
    height: 0;
    line-height: 0;
    font-size: 0;
    border: 50px solid transparent;
    border-left-color: pink;
}
```

### CSS用户界面样式

鼠标样式-光标形状：

- `cursor: `
  1. default，默认属性值，小白；
  2. pointer，小手；
  3. move，移动；
  4. text，文本；
  5. not-allow，禁止

轮廓线：表单有默认的蓝色轮廓线

- 给表单添加outline：0；或outline：none；样式就可以去掉默认的。

文本域：默认的文本域可以拖拽

- 使用resize：none；样式可以防止拖拽。

### vertical-align属性应用

vertical-align属性经常用于设置图片或者表单（行内块元素）和文字垂直对齐；vertical-align属性只适用于行内元素和行内块元素。

- baseline：基线对齐，默认的对齐方式，元素放置在父元素的基线上；
- top：元素顶端与行中最高元素的顶端对齐；
- middle：元素放置于父元素的中部；
- bottom：元素顶端与行中最低元素的顶端对齐。

图片底部会有一个空白缝隙，原因是行内块元素会和文字的基线对齐，解决办法：

1. 给图片添加vertical-align：middle或top或bottom；（提倡使用）
2. 把图片转为块元素。

### 省略号显示溢出文本

单行文本溢出的省略号代替：

```css
/* 强制在一行内显示文本 */
white-spase: nowrap; 
/* 溢出隐藏 */
overflow: hidden;
/* 超出部分使用省略号代替 */
text-overflow: ellipsis;
```

多行文本溢出的省略号代替：（有较大兼容性问题，适用于webkit浏览器和移动端（移动端大部分是webkit内核））

```css
/* 溢出隐藏 */
overflow: hidden;
/* 超出部分使用省略号代替 */
text-overflow: ellipsis;
display: -webkit-box;
/* 限制一个块内的文本行数 */
-webkit-line-clamp: 2; 
/* 设置或检索伸缩盒对象的子元素的排列方式 */
-webkit-box-orient：vertical;
```

多行文本的更推荐由后台人员来实现。





## 布局	

页面布局的三大核心：盒子模型、浮动、定位；网页布局的本质就是用CSS摆放盒子（把盒子摆放到相应位置）。

网页布局过程：

1. 先准备好相关的网页元素，网页元素基本都是盒子Box；
2. 利用CSS设置好盒子样式，然后摆放到相应位置；
3. 往盒子里面加内容；
4. 网页布局的核心就是用CSS摆放盒子。

CSS提供了三种传统的布局方式（简单地说就是盒子的排列顺序是咋样的），：

- 普通流（标准流/文档流）：标签按照默认的方式进行排列；
  - 块元素独占一行（div、hr、p、h、ul、ol、dl、from、table）；
  - 行内元素从左到右自动排列，一行满了会自动换行（span、i、a、em）。
- 浮动
- 定位

实际开发中一个页面都包含这三种布局（移动端还有新的布局方式）。

网页布局第一准则：多个块级元素纵向排列找标准流，多个块级元素横向排列找浮动。

网页布局第二准则：先设置盒子大小，之后设置盒子的位置。 

为了约束浮动元素位置，我们网页布局一般采取的策略时：先用标准流排列上下位置，内部子元素采用浮动排列左右位置。

浮动布局注意事项：

1. 网页布局第一准则；
2. 一个元素浮动了，其余的兄弟元素也要浮动；

页面布局整体思路：

1. 确定页面版心（可视区）；
2. 分析页面的行模型，执行网页布局第一准则；
3. 列模块经常浮动布局，先确定列大小，再确定列位置，网页布局第二准则；
4. 制作HTML结构（遵循先有结构，后有样式）；
5. 理清楚布局结构；

导航栏：实际开发使用li+a的做法。

## CSS编写规范

![](image/css编写规范.png)

## 浏览器前缀

![](image/浏览器前缀.png)

# Emmet语法

![](image/emmet.png)

5. `div.xxx`，`div#xxx`
6. `div.xxx$*5`
7. `div{$*5}`

# HTML5和CSS3

## 初始化css

```css
* {
    margin: 0;
    padding: 0;
}
em,
i {
    font-style: normal;
}
li {
    list-style: none;
}
img {
    border: 0;
    vertical-align: middle;
}
button {
    cursor: pointer;
}
a {
    color: #666;
    text-decoration: none;
}
a:hover {
    color: #c81623;
}
button,input {
    font-family:Microsoft YaHei,Heiti SC,tahoma,arial,Hiragino Sans GB,"\5B8B\4F53",sans-serif;
    border: 0;
    outline: n
}
body{
    -webkit-font-smoothing:antialiased;
    background-color:#fff;
    font:12px/1.5 Microsoft YaHei,Heiti SC,tahoma,arial,Hiragino Sans GB,"\5B8B\4F53",sans-serif;
	color:#666;
}
.hide,.none{
    display:none;
}
.clearfix:after{
    visibility:hidden;clear:both;display:block;content:".";height:0
}
.clearfix{
    *zoom:1
}
```

## HTML5新增

新增特性都有兼容性问题，IE9+才支持。HTML5新增语义化标签：

- header：头部标签
- nav：导航标签
- article：内容标签
- section：定义文档某个区域
- aside：侧边栏标签
- footer：尾部标签

新增的多媒体标签：

- 音频：audio
- 视频：video

![](image/html5.png)



## CSS3新增





# 转换、过渡、动画

## transform-转换

transform，CSS3，元素的位移（translate）、旋转（rotate）、缩放（scale）等。

### 2D转换

平移：`transform: translate(X,Y);`或`transform: translateX(n);`、`transform: translateY(n);`。

![](image/transform.png)

旋转：`transform: rotate(度数);`，度数单位为deg；

设置旋转中心点：`transform-origin: x y;`（默认旋转中心为集合中心，x、y可以是像素或方位名词（top bottom left right center））；

缩放：`transform: scale(x,y);`，注意点如下：（设置中心点也是通过transform-origin）

![](image/scale.png)

综合性写法：`transform: translate() rotate() scale(); `

![](image/transform问题.png)

### 3D转换

![](image/3d.png)

3D位移：

- `transform: translate3d(x, y, z)`：里面的xyz不能省略；可以单独设置，和2D转换的一致，translateZ()一般都是像素px；

透视：perspective

![](image/透视.png)

- `perspective: 像素`。

3D旋转：

![](image/rotate3d.png)

![](image/方向.png)

- 左手准则适用于绕x、y、z轴正方向的旋转；
- `transform: rotate3d(1, 0, 0, 45deg)`：绕x轴旋转45°；xyz指定矢量。

3D呈现：transform-style

![](image/3d空间.png)







## 过渡

过渡写到本身上，谁做动画给谁加。





## animation动画

通过设置多个节点来精确控制一个或一组动画，常用来实现复杂的动画效果。（比过渡多一些功能：更多变化、更多控制、连续播放等）。

制作动画：

1. 先定义动画（关键帧）：定义动画的语法
   - ```css
     @keyframes 动画名称 {
         /* 开始状态 */ /* 百分比值必须是整数，是用来划分时间的，实现某个时间段是某个状态，可设多个百分比 */
         0% {
         	动画效果    
         }
         /* 结束状态 */
         100% {
         	动画效果
         }
     }
     ```
   
     ![](image/动画序列.png)
2. 再调用动画：使用动画的语法
   - ```css
     /* 调用动画 */
     animation-name: 动画名称；
     /* 持续时间 */
     animation-duration: 秒或毫秒;
     ```

动画常见属性：

![](image/animation.png)

动画调用的简写：

![](image/animation简写.png)

- name（关键帧名）、duration（动画完成时间）必须写。

animation-timing-function（动画的速度曲线细节）：

![](image/速度曲线.png)

- 关于steps(n)：n为整数，该函数指定动画过程中的的加载步数（相当于把动画加载分为几个阶段，可以呈现阶段停顿效果，一步一步实现动画效果）；

- step时序函数有四种书写方式：见《CSS权威指南第四版   P880》；

- 可以实现一个字一个字显示出来：

  ![](image/逐字显示.png)

  

# 移动端

## 概述

移动端开发有单独制作移动端页面和响应式页面两种，目前市场主流还是单独制作移动端页面。

视口：分为视觉视口（屏幕可视区）、布局视口（移动端浏览器默认设置的布局视口）、理想视口（设备多宽，布局视口就多宽）。

meta视口标签：(标准的viewport设置)

```html
<meta name="viewport" content="width=dece-widthhhhh, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
```

![](image/meta视口.png)

2倍图：准备好的图片是实际需要的的两倍（再通过缩放引入图片），解决在手机端的图片变模糊的情况，这就是2倍图。（3倍图、四倍图看实际需要）



背景图片宽高：

background-size：宽度  高度；（单位：长度、百分比、cover（宽高等比拉伸到能覆盖背景区域）、contain（右下角开始等比拉伸到某一边碰到背景区域某一边，类似图片在背景区域所能等比放大完全显示的最大尺寸）；只设置宽高其中一个就是等比设置）

## 移动端技术解决方案

移动端初始化CSS推荐使用normalize.css；

下载地址：[normalize.css (csstools.github.io)](https://csstools.github.io/normalize.css/)；

![](image/移动端css特殊样式.png)

![](image/移动端布局.png)

# 单移动端页面

## 流式(百分比)布局

![](image/流式布局.png)

总结：

- 设定好主体盒子的width，后面的子元素设置为百分值时默认以父盒子为参考，应该设定最大最小宽度，防止尺寸过小或过大引起的布局失效。

## flex弹性布局

弹性盒：设置为display：flex/inline-flex的元素，弹性盒可以是任意元素。

![](image/flexfather.png)

- flex-direction：row（左到右）、column（上到下）、row-reverse、column-reverse（reverse，反转）；（方向按照语言书写方向会有一个默认值，不同的语言下的网页主轴方向不同，例如中文网页左到右，阿拉伯语言网站右到左）；
- justify-content：
  - flex-start、flex-end：紧靠主轴起边或终边；
  - center：把子元素当做整体居于主轴中点；
  - space-between：把第一个和最后一个都紧靠主轴起边或终边，剩余的等空白间隔排序占满剩余行空间；
  - space-around：减去子元素所占宽度，然后剩余的空间等分给各个元素两侧；（相当于等分为左右margin值）；
  - space-evenly：等分空间，分布子元素两侧，使得元素与元素、元素与起边或终边的间隔都是相同的；
- flex-wrap：wrap（换行）、nowrap（不换行，默认值）；
- aligin-items：影响的是垂轴上的对齐，flex-end（下上）、flex-start（上下）、center（垂直居中）、baseline（基线对齐）、stretch（默认，垂直拉伸）；
- aligin-content：影响的是垂直方向上的，值比justify-content多一个stretch（垂直拉伸）；
- flex-flow：row  wrap；

弹性盒元素：设置为display：flex/inline-flex的元素的子元素。

- flex：定义当前弹性子元素分配剩余空白空间，值为数值或百分比，意为多少份；
- aligin-self：定义**某个**弹性子元素对齐方式，侧轴（垂直）上的对齐方式，与aligin-items的值一致；
- order：定义弹性元素排列顺序，数值越小越往前，数值越大离起边越远；

## rem适配布局1

1. 如何使文字随着屏幕大小变化而变化？
2. 流式布局和flex布局主要针对宽度布局，那高度如何设置？
3. 如何使屏幕发生变化时元素和高度等比例缩放？

相对单位：

- em：相对于父元素字体大小（font-size）的单位；
- rem（root em）：相对于HTML元素的字体大小的单位，设置html标签的font-size即可控制其余使用了rem单位的大小；

媒体查询media query：

```css
@media mediatype and|not|only (media feature) { /* not、only */
    css样式
} 
/* 媒体类型：screen（屏幕，手机、平板、电脑等）、print（打印机）、all（所有） */
/* 媒体特性： max-width、min-width、width等，这几个指定宽度*/
```

```css
@media screen and (max-width: 760px) {
    当屏幕宽度不大于760px时执行的css样式代码
}
/* screen 和 and 不能省略 数值单位不能省略 */
```

通过媒体查询引入资源：link标签的media属性

```css
<link rel="stylesheet" media="媒体类型 关键字 (媒体特性)" href=""/>

<link rel="stylesheet" media="screen and (max-width: 320px)" href=""/>
```



![](image/css弊端.png)

less的使用：（Leaner Style Sheets，一种CSS拓展语言，CSS预处理器，在现有CSS的基础上加了程序式语言的特性，其他的有Sass、Stylus）。

创建.less文件，less文件里的操作：

1. 创建变量和变量值；![](image/less变量.png)

2. ```less
   @color: pink;
   body {
       color: @color;
   }
   div {
       color: @color;
   }
   ```

3. 需要编译成CSS文件，借助vscode的easy插件，安装插件后保存less文件会自动生成相对应的css文件，以后通过less文件就可以控制css文件的值；

4. less嵌套-对应css中的后代选择器

   - ```less
     div {
         a {
             color: red;
         }
     }
     /* 对应 div a {} */
     ```

   - ![](image/less嵌套1.png) 

5. less的运算：

   1. 数值都可以使用+、-、/、*这些单位，除法运算要加括号()；

   2. 运算符两侧要有空格，任何数字、颜色、变量都可以参与运算；

   3. 两运算的值的单位以第一个为首选，如果第一个没有则用第二个的单位；

   4. ```less
      @fontsize: 12px;
      div {
          width: @fontsize * 2;
          height: 123rem + 446px;
      }
      ```
   
6. `@import "common"`：导入common.less文件。

rem适配方案：（思考：适配的目标是？任何去实现？如何在实际开发中使用？）

![](image/rem适配.png)

实际上：![](image/实际适配.png)

技术选择：方案1：less、media、rem；方案2：flexible.js、rem（推荐的方案）。

方案一：

1. 选择屏幕的标准值（例如移动端一般750px），然后划分这个标准值为多少份（具体看设计，15份、20份等等）；
2. 每一份的大小就作为html元素的font-size值；
3. 然后根据rem单位就可以实现值的不同，但是实现了等比缩放的效果。

## rem适配布局2

使用flexible.js：

![](image/flexible.png)

vscode的cssrem插件，可以自动将px值转为rem值（需要进入设置里的拓展，找到cssrem的拓展的配置，然后找到RootFontSize，修改值）。

![](image/cssrem.png)





## 混合布局

# 响应式页面

## 媒体查询

![](image/响应式布局容器.png)

```css
.container {
    width: 1100px;
    margin: 0 auto;
}
@media screen and (max-width: 767px) {
    .container {
        width: 100%;
    }
}
@media screen and (min-width: 767px) {
    .container {
        width: 750px;
    }
}
@media screen and (min-width: 992px) {
    .container {
        width: 970px;
    }
}
@media screen and (min-width: 1200px) {
    .container {
        width: 1170px;
    }
}
```



## bootstrap

来自推特，目前最受欢迎的前端框架，基于HTML、CSS、JavaScript，简洁灵活，使得web开发更加快捷。

[Bootstrap中文网 (bootcss.com)](https://www.bootcss.com/)

### 使用

[Bootstrap中文网 (bootcss.com)](https://www.bootcss.com/)，下载。

引入。

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>陆拾陆的blog</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="../static/bootstrap-3.4.1-dist/css/bootstrap.min.css">
</head>
	<body>
	</body>
</html>
```

### 布局容器

![](image/bootstrap布局容器.png)

### 栅格系统

bootstrap将页面划分为12列。

![](image/栅格系统1.png)

注意：

- 如果划分份数不够12，默认向左靠齐。
- 如果划分份数超过12，默认换行。

![](image/栅格_row.png)

![](image/栅格_列偏移.png)

![](image/栅格_列排序.png)

### 响应式工具

![](image/bootstrap_组件.png)







































