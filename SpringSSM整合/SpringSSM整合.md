#  SSM整合开发

## 概述

1. SpringMVC：界面层，处理接收请求，显示处理结果。
2. Spring：业务层，处理业务逻辑，spring创建Service、Dao、工具类等对象。
3. MyBatis：持久层，访问数据库的，对数据增删改查。（前身是IBatis，所以SSM也叫SSI）

**SSM中涉及到的两个容器：**

1. 第一个容器：SpringMVC容器，管理Controller控制器对象的。
2. 第二个容器：Spring容器，管理Service、Dao、工具类对象的。

SSM中我们要做的是：把使用到的对象交给合适的容器创建、管理。（把Controller、web开发的相关对象交给springmvc容器，也就是把这些对象写在springmvc容器的配置文件中，把Service、Dao、工具类对象定义在spring容器的配置文件中，让spring管理这些对象）。

**springmvc容器如何访问spring容器？**springmvc和spring有一种确定的关系，**springmvc是spring的子容器**，子容器可以访问父容器的内容。子容器的Controller可以访问父容器的Service。

## 具体操作步骤

1. 建立数据库；
2. 建立web项目；
3. 加入依赖：spring、springmvc、mybatis三个框架的依赖，jackson依赖，MySQL驱动，Druid连接池，jservlet依赖；
4. web.xml配置：
   - 注册中央调度器DispatcherServlet（目的：创建springmvc容器对象，才能创建Controller对象；创建servlet，才能接收用户请求）；
   - 注册spring的监听器ContextLoaderListener（目的：创建spring的容器对象，才能创建Service、Dao等对象）；
   - 注册字符集过滤器（目的：处理post请求的乱码问题）；
5. 创建包：Controller包、Service包、dao等，实体类包名创建好；
6. 写springmvc、spring、mybatis的配置文件：
   - springmvc配置文件：
   - spring配置文件：
   - mybatis配置文件：
   - 数据库的属性配置文件：
7. 写业务代码：dao接口、mapper文件、service和实现类、controller、实体类；
8. 页面。

### 一：导入依赖

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

### 二：整合Spring-MyBatis

MyBatis的全局配置：**mybatis-config.xml（resources/conf/mybatis-config.xml）：**

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
    <!--设置别名-->
    <typeAliases>
        <!--name:实体类所在包名；不是实体类的包名也可以-->
        <package name="com.lsl.domain"/>
    </typeAliases>
    <!--SQL mapper（sql映射文件）的位置-->
    <mappers>
        <!--
            name：包名，这个包中的所有mapper.xml都能一次性全部加载
            使用package要求：
            1.mapper.xml文件名称要和dao接口名必须完全一样，包括大小写
            2.mapper.文件和dao接口必须在同一目录
        -->
        <package name="com.lsl.dao"/>
    </mappers>
</configuration>
```

Spring用来管理所有的业务逻辑组件，Spring容器的配置文件：resources/conf/applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <!--spring配置文件：声service、dao、工具类、事务控制、aop-->
	<!-- 引入类路径下的properties文件来配置数据源 -->
    <context:property-placeholder location="classpath:conf/jdbc.properties"/>
    <!--声明数据源，连接数据库-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
    <!--SqlSessionFactoryBean：用来创建SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 指定全局 -->
        <property name="configLocation" value="classpath:conf/mybatis.xml"/>
    </bean>
    <!-- 声明mybatis的扫描器 -->
    <!-- 配置扫描Dao接口包，动态实现Dao接口注入到spring容器中 -->
    <!-- 解释 ：https://www.cnblogs.com/jpfss/p/7799806.html-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <property name="basePackage" value="com.lsl.dao"/>
    </bean>
    <!-- 注解扫描 -->
    <context:component-scan base-package="com.lsl.service"/>
    <!--事务配置：注解的配置，aspectj的配置；程序代码基本调试通之后再加-->
    
</beans>
```

**resources/conf/jdbc.properties：**

```properties
jdbc.url=jdbc:mysql://localhost:3306/ssmtest?useUnicode=true&characterEncoding=UTF-8
jdbc.username=root
jdbc.password=123456
```



### 三：Spring-tx—配置事务

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
    <!-- spring事务管理器的配置 -->
    <!-- 扫描软件包，使注解生效 -->
    <context:component-scan base-package="com.lsl.crowd.service"/>
    <!-- 配置事务管理器 -->
    <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!-- 装配数据源，运行时就能从ioc容器拿到了 -->
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!-- 配置事务切面 -->
    <aop:config>
        <aop:pointcut id="txPointcut" expression="execution(* *..*ServiceImpl.*(..))"/>
        <!-- 将切点表达式和事务通知关联 -->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>
    <!-- 配置事务通知 -->
    <tx:advice id="txAdvice" transaction-manager="txManager">
        <!-- 配置事务属性 -->
        <tx:attributes>
            <!-- 查询方法：配置只读属性，让数据库知道这是一个查询操作，能够进行一定优化 -->
            <tx:method name="get*" read-only="true"/>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="query*" read-only="true"/>
            <tx:method name="count*" read-only="true"/>
            <!-- 增删改方法：配置事务传播行为、回滚异常 -->
            <!--propagation="REQUIRED" ：
            REQUIRED，默认值，表示当前方法必须工作在事务中，如果当前线程没有已经开启的事务则自己开启；如果有则使用已有的
            REQUIRES_NEW：建议使用的值，不管当前线程是否有事务，都要自己开事务并在自己的事务中执行，不会受到其他事务的影响
            -->
            <!--rollback-for：配置事务方法针对什么样的异常就回滚
            默认：运行时异常回滚
            建议：运行时异常、编译时异常都回滚
            -->
            <!-- tx:method是必须要配置的，如果某个方法没有配置对应的tx:method，则事务对这个方法不生效 -->
            <tx:method name="save*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
            <tx:method name="update*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
            <tx:method name="remove*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
            <tx:method name="batch*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
        </tx:attributes>
    </tx:advice>
</beans>
```

### 四：整合Spring-SpringMVC

**web.xml：注册SpringMVC的中央调度器、拦截器、监听器、过滤器、springmvc容器，注册Spring容器**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <display-name>Archetype Created Web Application</display-name>

    <!-- 启动spring root context （父容器） start -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:conf/applicationContext.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!-- 启动spring root context （父容器） end -->
    
    <!-- 启动中央调度器并初始化spring app context （子容器） start -->
    <servlet>
        <servlet-name>myweb</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- springMVC springmvc容器 -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:conf/springmvc-config.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!-- 启动中央调度器并初始化spring app context （子容器） start -->
    <servlet-mapping>
        <servlet-name>myweb</servlet-name>
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>

    <!--注册字符集过滤器-->
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!--设置项目中使用的字符编码-->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>

        <!--强制请求对象（HttpServletRequest）使用-->
        <init-param>
            <param-name>forceRequestEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
        <!--强制应答对象(HttpServletResponse)使用-->
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <!--强制所有请求先经过过滤器-->
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

SpringMVC容器的配置文件：**springmvc-config.xml，配置视图解析器，开启注解扫描**

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
    <!--声明：组件扫描器-->
    <context:component-scan base-package="com.lsl.controller"/>
    <!--声明：-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--前缀：视图文件路径-->
        <property name="prefix" value="/WEB-INF/student/"/>
        <!--后缀：视图文件的拓展名-->
        <property name="suffix" value=".jsp"/>
    </bean>
    <!--把数据转为json的注解驱动，ObjectMapper-->
    <!--解决静态资源访问问题-->
    <mvc:annotation-driven/>
    <mvc:default-servlet-handler/>
</beans>
```

​	
