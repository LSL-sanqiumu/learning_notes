

# 小而美

# 一、SSM环境搭建

## 1.依赖导入

Spring、SpringMVC的依赖：

```xml
<!-- Spring、SpringMVC start  -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.16</version>
</dependency>
<!-- 使用AOP时需要导入的包 -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.8</version>
    <scope>runtime</scope>
</dependency>

<!-- spring事务处理 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>5.3.16</version>
</dependency>
<!-- Spring整合JDBC mybatis运行需要jdbc包-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.16</version>
</dependency>
<!-- Spring、SpringMVC end  -->
```

Mybatis、数据库驱动、数据库连接池、jdbc的依赖：

```xml
<!-- Mybatis、mybatis-spring、数据库驱动、jdbc start -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.7</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.27</version>
</dependency>
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid</artifactId>
	<version>1.2.8</version>
</dependency>
<!-- Mybatis、mybatis-spring、数据库驱动、jdbc end -->
```

thymeleaf模板引擎的依赖：

```xml
<!-- thymeleaf start -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf</artifactId>
    <version>3.0.15.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf-spring5 -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
    <version>3.0.15.RELEASE</version>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
<!-- thymeleaf end -->
```

lombok插件：

```xml
<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.22</version>
    <scope>provided</scope>
</dependency>
```

slf4j日志包：

```xml
<!-- 日志 start -->
<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.11</version>
    <scope>test</scope>
</dependency>
<!-- 日志 end -->
```

pagehelper——MyBatis分页插件：

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.1.11</version>
</dependency>
```





## 2.配置环境

### applicationContext.xml

用spring容器注册数据源等，整合mybatis。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop.xsd
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx.xsd">
    <context:component-scan base-package="com.lsl"/>
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.passwd}"/>
    </bean>
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>
    <!--声明mybatis的扫描器，创建dao对象-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <property name="basePackage" value="com.lsl.dao"/>
    </bean>

</beans>
```

### mybatis-config.xml

用mybatis全局配置来开启mybatis日志输出。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--settings：控制mybatis全局行为-->
    <settings>
        <!--设置mybatis输出日志-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
</configuration>
```

### springmvc-config.xml

配置视图解析器。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    
       <context:component-scan base-package="com.lsl"/>
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
        <property name="prefix" value="/WEB-INF/view/"/>
        <property name="suffix" value=".html"/>
        <property name="templateMode" value="HTML5"/>
        <property name="cacheable" value="false"/>
        <property name="characterEncoding" value="UTF-8"/>
    </bean>
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>
</beans>
```

### web.xml

中央调度器，初始化springmvc、spring容器。

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
    <display-name>Archetype Created Web Application</display-name>

    <!-- 启动spring root context （父容器）start -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
    <!-- 启动spring root context （父容器）end -->
    <!-- 过滤器 处理post接收到乱码 start -->
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceRequestEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!-- 过滤器 处理post接收到乱码 end -->
    <!-- 启动spring root context （父容器 start 需要的监听器 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!-- 启动spring root context （父容器）end 需要的监听器 -->
    <!-- 启动 spring app context （子容器） start-->
    <servlet>
        <servlet-name>myspringmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-config.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>myspringmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    <!-- 启动 spring app context （子容器） end-->

</web-app>
```

### 事务配置

Spring——事务配置，测试完成后再加事务的配置。

```xml
<!-- 配置事务 -->
<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>
<aop:config>
    <aop:pointcut id="txPointcut" expression="execution(* *..*ServiceImpl.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
</aop:config>
<!-- 配置事务通知 -->
<tx:advice id="txAdvice" transaction-manager="txManager">
    <tx:attributes>
        <!-- 查询方法：配置只读属性，让数据库知道这是一个查询操作，能够进行一定优化 -->
        <tx:method name="get*" read-only="true"/>
        <tx:method name="find*" read-only="true"/>
        <tx:method name="query*" read-only="true"/>
        <tx:method name="count*" read-only="true"/>
        <!-- 增删改方法就配置事务传播行为和回滚异常 -->
        <tx:method name="save*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
        <tx:method name="update*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
        <tx:method name="remove*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
        <tx:method name="batch*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
    </tx:attributes>
</tx:advice>
```

## 3.测试

测试的：com.lsl.dao.TestMapper、com.lsl.test、com/lsl/dao/TestMapper.xml。

访问：http://localhost:8081/blog/get

## 4.全局异常处理

使用注解来配置全局异常处理。

1、定义异常处理器

```java
@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = Logger.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
		String str = "Request URL : {" + request.getRequestURL() +"}" + "Exception : {" + e + "}";
        logger.log(Level.INFO,str);
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
```

该error视图为`WEB-INF/view/error/error.html`。

需要处理哪些异常，就加上。

## 5.搭建日志

**使用log4j：**

1、导入依赖

```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

2、log4j配置：log4j.properties

```properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码 console,file
#设置
log4j.rootLogger=DEBUG,console

# 配置：输出到控制台
#指定控制台日志输出的Appender
log4j.appender.console=org.apache.log4j.ConsoleAppender
#指定输出消息格式 org.apache.log4j.PatternLayout的默认规则是：%m%n
log4j.appender.console.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
```

# 二、静态资源导入

resources目录下新建static目录，将css、js、第三方库、图片等静态资源引入。

# 三、功能分析与实现

## 首页index.xml

### 功能分析

1、博客展示：博客数量、博客标题、博客发布时间、博客简介、博客封面图地址，一页最多展示最新的6篇

2、分类展示：分类名称和相关分类的博客数量

3、标签显示：标签名称和相关标签所对应到博客数量

### 1、博客展示实现

使用PageHelper分页插件（[Mybatis-PageHelper/HowToUse.md at master · pagehelper/Mybatis-PageHelper (github.com)](https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md)）。



















