

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
<!-- Spring整合JDBC -->
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
  <!-- 启动spring root context （父容器 start -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <!-- 启动spring root context （父容器）start -->
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

