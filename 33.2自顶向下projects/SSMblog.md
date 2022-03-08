# SSM

一个简单的SSM，配置好SSM好，引入thymeleaf的依赖：

```xml
<!-- （使用Thymeleaf模板引擎）-->
<dependency>
<groupId>org.thymeleaf</groupId>
<artifactId>thymeleaf</artifactId>
<version>3.0.2.RELEASE</version>
</dependency>
<dependency>
<groupId>org.thymeleaf</groupId>
<artifactId>thymeleaf-spring4</artifactId>
<version>3.0.2.RELEASE</version>
</dependency>
```

sprimgMVC的配置中设置使用thymeleaf的解析器来解析：

```xml
<!-- thymeleaf的视图解析器 会与ContentNegotiatingViewResolver冲突-->
<bean id="viewResolver" class="org.thymeleaf.spring4.view.ThymeleafViewResolver">
<property name="characterEncoding" value="UTF-8"/>
<property name="templateEngine" ref="templateEngine"/>
</bean>
<!-- 模板引擎 -->
<bean id="templateEngine" class="org.thymeleaf.spring4.SpringTemplateEngine">
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

html页面的头部记得加上一个约束：

```html
<html xmlns:th="http://www.thymeleaf.org">
```



## 搭建

数据库与表：

```sql
create database ssmdb character set utf8 collate utf8_general_ci;
create table t_blog(
	`id` bigint(20) NOT NULL auto_increment,
    `title` varchar(255) default null,
	`content` longtext,
	`create_time` datetime(6) DEFAULT NULL,
    `views` int(11) DEFAULT NULL,
  	`type` varchar(20) DEFAULT NULL,
  	`user` varchar(20) DEFAULT NULL,
	`description` varchar(255) DEFAULT NULL,
    primary key(id)
)engine=innodb default charset=utf8;
insert into t_blog(title,content,create_time,`views`,type,`user`,description) values
('如何吃透一个项目',
 '# 项目

我就简单点回答，做好以下七个步骤即可：

1、弄清楚这个项目的作用和主要用到的技术及框架

2、部署项目，并设置debug模式

3、先从前端每个主要功能都走一遍

4、每个action的方法打断点，action中因为有断点，故每个后台acting、service、DAO都走一遍

5、用visio或艺图把类结构图和代码流程图画出来（问下自己，有没有可以参考的visio文档？）

6、尝试修改一些代码逻辑，让项目继续跑起来，看看能发生什么奇妙的事情

7、抽离主干代码，重建工程，再重新填充逻辑代码，尝试是否能让项目跑起来且功能基本一致
这七个步骤不光能吃透Java项目，也可以用于其他语言开发的项目，也可以拿来学习各种开源项目。',
now(),
333,
'经验',
'三丶秋暮',
'在项目中学习是最好的提升途径，本文概括了学习项目中的七个主要步骤，在学习时使用这七个步骤可快速地理解项目，吃透项目的各个知识点。');
```

```sql
insert into t_blog(title,content,create_time,`views`,type,`user`,description) values
('Spring概述',
 '2002年，Spring雏形interface21发布，在interface21的基础上经过重新设计，并不断丰富内涵，于2004年3月24号Spring发布1.0版本，其创始人是Rod  Johnson。Spring是一个为了全方位地简化Java开发而创建的开源框架，是一个轻量级的控制反转和面向切面编程的框架；其为了简化Java开发采取了以下四种策略：
- 基于POJO的轻量级和最小侵入式编程；
- 通过依赖注入和面向接口实现松耦合；
- 基于切面和惯例进行声明式编程；
- 通过切面和模板减少样板代码。',
now(),
444,
'spring',
'三三',
'spring是为了简化JavaEE开发而创建的开源框架，主要是AOP和IOC的运用。');
insert into t_blog(title,content,create_time,`views`,type,`user`,description) values
('mybatis-一款轻量级框架',
 'mybatis内容',
now(),
444,
'spring',
'三三',
'mybatis在servlet的基础上拓展了很多功能，弥补了servlet的不足，使得开发大型项目更加方便');
```

