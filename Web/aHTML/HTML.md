# HTML

学习标签要记住标签的语义。

## div、span

div、span标签是没有语义的，可以将它们当作一个存放东西的盒子。

div，division的缩写，意为分割、分区；div是一个块级元素，单独占一块（一行）

span意为跨度、跨距，是一个行内元素，一行内可以存在多个该标签。

```html
<div></div>
<span></span>
```



## 文字基础标签

```html
<p></p>					段落
<h1></h1>到<h6></h6>    1-6级标题，独占一行，默认样式：加粗、字号大小	
<del></del>			删除线			
<em></em>			斜体
<strong></strong>	粗体
<ins></ins> 		下划线
```

## 实体符号

空格：`&nbsq;`；大于号：`&gt;`；小于号：`&lt;`。

## img

```html
<img src="" alt="" title="" width="" height="" border=""/>
<!--属性之间无顺序要求，以空格分开-->
<!--alt：当图片无法显示时的替代文字；title：鼠标放于图片上时的提示文字，图片加不加载出来都起作用；-->
<!--宽度、高度修改一个就可以，另一个会自动等比例缩放-->
<!--border：设置边框宽度等，后面用css操作-->
```



## a

a是anchor的缩写，意为锚。

```html
<a href="" target=""></a>
<!--href：链接地址，内部链接或外部链接；空链接：#来代替；下载链接：链接地址是文件，文件是.exe或.zip格式-->
<!--target：_black-重新开一个窗口加载；_self：当前页跳转-->
<!--各种网页元素都可以加超链接-->
```

锚点链接：用于快速定位页面中某个位置

- 锚点链接的href属性值为`#idName`的形式idName为目标标签的id属性名。

## 列表

有序列表：

```html
<ol type="">
    <li></li>
    <li></li>		有序列表，数字、罗马数字、字母等
    .........
</ol>
<!--可在li里再嵌套-->
<!--type：1、A、I、a、i-->
```

无序列表：

```html
<ul type="">
    <li></li>
    <li></li>		无序列表，实心圆点、方形等
    .........
</ul>
<!--type：circle、square-->
```



描述列表：

```html
<dl>
    <dt>项目</dt>
    <dd>相关描述</dd>
    <dt></dt>
    <dd></dd>
    ...
</dl>
<!--dl只能出现dt、dd；经常是一个dt对应多个dd-->
```

## 表格

```html
<table>
    <thead>
    <tr>			<!--table row-->
    	<th></th>   <!--table head，加粗并居中显示-->
        ...
    </tr>
    </thead>
    <tbody>
    	<tr>
    	<td></td>   <!--table data-->
        ...
    </tr>
    ...
    </tbody>
    <tfoot>
    
    </tfoot>
</table>
<!--table默认边框宽度为0，table的边框分为外框和单元格的框-->
<!--table的属性：实际开发很少使用属性 -->
```

|   属性名    |       属性值        |              描述               |
| :---------: | :-----------------: | :-----------------------------: |
|    align    | left、right、center |   表格相对周围元素的对齐方式    |
|   border    |       像素值        |        默认没有边框宽度         |
| cellpadding |       像素值        | 单元边缘与内容的空白距，默认1px |
| cellspacing |       像素值        |   单元格之间空白，默认的是2px   |
|    width    |   像素值或百分比    |            表格宽度             |

合并单元格：用于td标签

- `rowspan = "合并个数" `：跨行合并；
- `colspan = "合并个数"`：跨列合并；
- 先确定目标单元格，再指定合并个数，合并从目标单元格开始，合并完需要删除多余的单元格。

## 表单

```html
<!--发送请求并携带数据，用来收集用户信息-->
<!--通常由表单域（表单区域）、表单控件（表单元素）、提示信息（文字提示）组成-->
<form action="" name="" method="">
    用户名：<input type="text" name="username" value="">
    密码：<input type="password" name="password" >
    <!--单选按钮通过相同的name实现多选一-->
    性别：男<input type="radio" name="sex"/>女<input type="radio" name="sex"/>
    爱好：打游戏<input type="checkbox" name="hobby"/>
    	 听音乐<input type="checkbox" name="hobby"/>
    	 运动<input type="checkbox" name="hobby"/>
    
    <select />
    <textarea />
</form>
```

### input：

| type属性 |                描述                |
| :------: | :--------------------------------: |
|  button  |             可点击按钮             |
| checkbox |               复选框               |
|   file   |              文件上传              |
|  hidden  |         定义隐藏的输入字段         |
|  image   |            图像提交按钮            |
| password |                密码                |
|  radio   |              单选按钮              |
|  reset   |       重置按钮，清楚表单数据       |
|  submit  |  提交按钮，把表单数据发送给服务器  |
|   text   | 单行的输入字段，默认可输入20个字符 |

- name和value属性是每个表单元素都应该有的值，主要给后台人员使用；
- 单选按钮和复选框要有相同的name；
- 单选按钮和复选框可以有checked属性，用于指定默认选中的选项；
- text的表单控件可以使用maxlength属性设置可输入字符数；

### label:

label是input元素标记标签，用于绑定一个表单元素，当点击<label>标签内的文本时，浏览器就会自动将焦点（光标）转到或选择对应的表单元素，用来增加用户体验。

```html
<label for="sex">男</label>
<input type="radio" name="sex" id="sex"/>
<!--label元素应该和input元素的id一致-->
<input type="radio" name="sex"/>
```

### select：

```html
<select>
	<option></option>
    <option slected="selected"></option> <!--默认选中-->
    <option></option>
    ...
</select>
```

### textarea：

```html
<textarea cols="" rows="">
	默认显示文字......
</textarea>
<!--cols：一行的字数-->
<!--rows：文本域默认展示的行数-->
<!--实际开发都不会用-->
```

## 查询文档

百度

[w3school 在线教程](https://www.w3school.com.cn/)

[MDN Web Docs (mozilla.org)](https://developer.mozilla.org/zh-CN/)

















