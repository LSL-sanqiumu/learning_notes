# thymeleaf



## 依赖导入

```xml
<!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf</artifactId>
    <version>3.0.11.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf-spring5 -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
    <version>3.0.11.RELEASE</version>
</dependency>
```

配置thymeleaf视图解析器：

```xml
<!-- thymeleaf的视图解析器 会与冲突ContentNegotiatingViewResolver-->
<bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
<property name="characterEncoding" value="UTF-8"/>
<property name="templateEngine" ref="templateEngine"/>
</bean>
<!-- 模板引擎 -->
<bean id="templateEngine" class="org.thymeleaf.spring5.SpringTemplateEngine">
<property name="templateResolver" ref="templateResolver"/>
</bean>
<!-- 模板解析器 -->
<bean id="templateResolver" class="org.thymeleaf.templateresolver.ServletContextTemplateResolver">
<constructor-arg ref="servletContext"/>
<property name="prefix" value="/WEB-INF/muke/"/>
<property name="suffix" value=".html"/>
<property name="templateMode" value="HTML5"/>
<property name="cacheable" value="false"/>
<property name="characterEncoding" value="UTF-8"/>
</bean>
```

```html
<!-- 在html页面的html标签需加上的 -->
xmlns:th="http://www.thymeleaf.org"
```



## 资源引入

th:href、th:src：通过@{...}表达式，Thymeleaf 可以帮助我们拼接上web应用访问的全路径，同时我们可以通过（）进行参数的拼接

```html
<a th:href="@{/product/comments(prodId=${prod.id})}" >查看</a>
<!-- <a href="/sbe/product/comments?prodId=2">查看</a> -->
<img th:src="@{/images/gtvglogo.png}"  />
```

```html
<a  th:href="@{/product/comments(prodId=${prod.id},prodId2=${prod.id})}" >查看</a>
<!-- 结果 -->
<a href="/sbe/product/comments?prodId=2&amp;prodId2=2">查看</a>
```

# 标签使用

## 文本替换

### th:text

1. `th:text=""`：替换文本，内容含标签不会被解析
2. `th:utext=""`：替换文本，内容如果含标签会被解析

替换内容拼接：`+`和`||`作用一样

```html
<p th:text="text标签：+ ${msg}"></p>
<p th:utext="|utext标签： ${msg}|"></p>
```

替换内容中可进行比较和逻辑判断：>、<、！等

```html
<p th:text="${a} > ${b}"></p>
<p th:utext="!${falg}"></p>
```

表达式：正常情况下 `*{...}` 和 `${...}` 是一样的，但是` *{...} `一般和 **th:object** 进行一起使用来完成对象属性的简写。

```html
<!-- 从域中取值 表示对存入域的变量的引用 -->
<p th:text="*{user.name}"></p>
<p th:text="${exception}"></p>
```

`#{...}`表达式：用于国际化message.properties 属性读取

### th:attr

用于声明html中或自定义属性信息。

### th:value

用于声明html中表单的标签或其它标签的value属性的信息。

### th:action

用于声明html from标签中action属性信息。

### th:id

用于声明html id属性信息。

### th:onclick

用于声明htm 中的onclick事件。



## 条件判断

**th:if** 当条件为true则显示。**th:unless** 当条件为false 则显示

```html
<!-- model.addAttribute("flag",true); 时，以下都会在渲染后显示出来 -->
<p th:if="${flag}">if判断</p>
<p th:unless="!${flag}">unless 判断</p>
```

## 条件选择

**th:switch** ：我们可以通过switch来完成类似的条件表达式的操作。

```html
<div th:switch="${user.name}">
	  <p th:case="'ljk'">User is  ljk</p>
	  <p th:case="ljk1">User is ljk1</p>
</div>
```



## for循环

**th:each** 遍历集合，并且重复当前标签内容。

```html
<table>
 <thead>
   <tr>
     <th>用户名称</th>
     <th>用户年龄</th>
   </tr>
 </thead>
 <tbody>
   <tr th:each="user : ${userList}" th:class="${userStat.odd}? 'odd'">
     <td th:text="${user.name}">Onions</td>
     <td th:text="${user.age}">2.41</td>
   </tr>
</tbody>
    </table>
```

th:each：从userList集合一个个取出数据给user，和foreash类似，并且渲染时整段标签块也会被循环插入。

th:class：html标签的class属性设置。

`${userStat.odd}`：其中通过遍历的变量名+Stat 来获取索引，是否是第一个或最后一个等。

```html
<tr th:each="user : ${userList}" th:class="${userStat.odd}? 'odd'">
```

遍历的变量名+Stat称作状态变量，其属性有：

- index：当前迭代对象的迭代索引，从0开始，这是索引属性；
- count：当前迭代对象的迭代索引，从1开始，这个是统计属性；
- size：迭代变量元素的总量，这是被迭代对象的大小属性；
- current：当前迭代变量；
- even、odd：布尔值，当前循环是否是偶数/奇数（从0开始计算）；
- first：布尔值，当前循环是否是第一个；
- last：布尔值，当前循环是否是最后一个。

# 内联标签

如果需要在内联的css或javascript里使用模板引擎渲染来加入变量，可以在内联处使用：

```html
<!-- th:inline="javascript" -->
<script type="text/javascript" th:inline="javascript">
	var totalRecords = [[${pageInfo.total}]];
</script>
```

```html
<!-- th:css="css" -->
<style type="text/javascript" th:css="css">
	var totalRecords = [[${pageInfo.total}]];
</style>
```

使用`[[${pageInfo.total}]]`的格式去取出域里的值。

# 定义页面模板

 th:fragment 来定义引用片段。

不带参数的引用片段：（页面模板为`_fragment.html `）

```html
<!-- 模板页面定义模板 -->
<div th:fragment="copy">
        &copy; 2011 The Good Thymes Virtual Grocery
</div>
<!-- 或者只定义id -->
<div id="copy">
        &copy; 2011 The Good Thymes Virtual Grocery
</div>
```

```html
<!-- 在其它页面引用模板的两种方式，这两种方式的结果一致 -->
<div th:insert="_fragment :: copy"></div>
<div th:insert="~{_fragment :: copy}"></div>
<!-- 通过id实现引入 -->
<div th:insert="~{_fragment :: #copy}"></div>
```

th:insert和th:replace（和th:include）之间的区别

```html
<!-- 引用替换方式一：把该标签替换为引用片段全部 -->
<head th:replace="_fragments :: head"></head>
<!-- 引用替换方式二：在该标签中插入引用片段的内容 -->    
<head th:insert="_fragments :: head"></head>
<!-- 引用替换方式三：和insert一样 --> 
<head th:include="_fragments :: head"></head>
```



带参数的引用片段：

```html
<!-- 模板页面定义模板，带的参数可以是一个或多个，页面模板为_fragment.html -->
<div th:fragment="copy(info1,info2,....)">
	<p th:text="${info1} + ' - ' + ${info2}">...</p>
</div>
```

```html
<!-- 引用 -->
<div th:insert="_fragment :: frag('a','b')"></div>
```

```html
<!-- 渲染结果 -->
<div>
<div>
    <p>a - b</p>
</div>
</div>
```



































