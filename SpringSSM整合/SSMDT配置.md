# Spring配置

## 依赖

使用spring需要导包，导入spring-webmvc的包，由于maven的特性会帮忙把spring-webmvc的所有依赖到的包都导入，其中就包括了spring-core，也就是spring所需要的包：

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

maven仓库：[Maven Repository: spring (mvnrepository.com)](https://mvnrepository.com/tags/spring)。

## beans.xml

resources文件夹需要beans.xml等文件，xml约束如下：

```xml
<!-- spring用于创建对象的配置文件，基本约束 -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```

```xml
<!-- 使用p、c命名空间时 -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!--p命名空间注入：可直接注入属性；p：property-->
    <bean id="user" class="com.lsl.pojo.User" p:name="梁胜林" p:id="10010"/>
    <!--c命名空间注入：通过有参构造器注入；c：constructor-arg-->
    <bean id="user2" class="com.lsl.pojo.User" c:id="12" c:name="lsl" scope="prototype"/>
</beans>
```

其他情况下文件头需要添加的内容：

```xml
<!-- 使用注解时 -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

     <!-- 扫描包使注解生效，注解生效时默认bean的id为注册进Spring的类的类名小写(多个单词组合时是驼峰形式)，
	如果要标识bean的id为其他，可在@Component("")设置 -->
    <context:component-scan base-package="com.lsl.pojo"/>
    <!-- 开启注解支持 -->
    <context:annotation-config/> 

</beans>
```

```xml
<!-- 面向切面 -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
</beans>
```

引入全部约束：

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
    
</beans>

```

约束简要说明：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    //上面两个是基础IOC的约束，必备
    xmlns:context="http://www.springframework.org/schema/context"
    //上面一个是开启注解管理Bean对象的约束
    xmlns:aop="http://www.springframework.org/schema/aop"
    //aop的注解约束
    xmlns:tx="http://www.springframework.org/schema/tx"
    //事务的约束
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans.xsd
    //上面两个是基础IOC的约束，必备
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd
    //上面一个是开启注解管理Bean对象的约束
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop.xsd
    //aop的注解约束
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx.xsd">
    //事务的约束
</beans>

```

# SpringMVC配置

## 依赖

使用thyme leaf模板引擎，需要javax.servlet-api依赖。

```xml
<!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.8</version>
</dependency>
```

## 中央调度器

web.xml里配置中央调度器（也就是相当配置了一个servlet）：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app  xmlns = "http://xmlns.jcp.org/xml/ns/javaee"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jsp.org/xml/ns/javaee/web-app_4_0.xsd"
          version="4.0"
          metadata-complete="true"
>
  <display-name>Archetype Created Web Application</display-name>
  
    <servlet>
        <servlet-name>myspringmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value> 
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>myspringmvc</servlet-name>
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>
    
</web-app> 
```

## 视图与注解

resources目录里的springmvc.xml配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <!-- 组件扫描器，使注解生效 -->
    <context:component-scan base-package="com.lsl.controller"/>
    <!-- 视图器的配置，为了简化控制器的视图路径 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 前缀：视图文件路径，也是相对于自己webapp根目录-->
        <property name="prefix" value="/WEB-INF/view/"/>
        <!--后缀：视图文件的拓展名-->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

# MyBatis配置

## 依赖

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
<!-- Spring整合JDBC -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.16</version>
</dependency>
<!-- Mybatis、mybatis-spring、数据库驱动、jdbc end -->
```

## 全局配置

mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbcurl}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="BlogMapper.xml"/> <!-- 指定SQL语句文件 -->
    </mappers>
</configuration>
```

```properties
# jdbc.properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/jdbctest?characterEncoding=utf8
jdbc.username=root
jdbc.password=123456
```

SSM整合时这个全局配置文件可以省略。

## 映射文件

xxxMapper.xml：

```xml
<!-- BlogMapper.xml 定义SQL语句 -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="BlogMapper">
    <!--mybatis会自动创建对象并把查询结果放到对象对应的属性上，需要告知resultType-->
    <!--查询结果集的列名一定要和对象的属性名对应，不对应的时候使用as起别名-->
    <!--select语句的id用于标识，通过id可以使用该SQL语句-->
    <select id="getAll" resultType="com.lsl.domain.Student">
        select id as sid,name as sname,birth as sbirth from t_student
    </select>
</mapper>
```

# Servlet配置

```xml
<!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
</dependency>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app  xmlns = "http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jsp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0"
         metadata-complete="true"
         >


</web-app>
```

# JDBC配置

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<!-- spring整合jdbc -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-jdbc</artifactId>
  <version>5.3.8</version>
</dependency>
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<!-- 运行mysql与java程序对话的驱动程 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.46</version>
</dependency>
```

# Thymeleaf配置

依赖导入：

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

配置thymeleaf视图解析器：（在SpringMVC的全局配置文件中配置）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
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
</beans>
```

# 数据库连接池

要操作数据库：先连接数据库->执行操作->释放；因为连接-释放的过程非常浪费数据库资源，所以就有了池化技术。

池化技术：预先准备好一些资源以便最先连接。

编写连接池：实现一个DataSource接口；需要设置最小连接数、最大连接数、等待超时。

开源数据源实现：DBCP、C3P0、Druid（阿里巴巴的）。使用数据库连接池后，就不用编写连接数据库的代码了。

## Druid

```xml
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid</artifactId>
	<version>1.1.8</version>
</dependency>
```

**直接在Java程序中配置和使用：**（使用Spring也是创建对DruidDataSource的bean）

```java
public class DruidPool {
    public static void main(String[] args) throws SQLException {
        //数据源配置
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/mysqltest?useSSL=false");
        ds.setDriverClassName("com.mysql.jdbc.Driver"); //这个可以缺省的，会根据url自动识别
        ds.setUsername("root");
        ds.setPassword("123456");
        //下面都是可选的配置
        ds.setInitialSize(10);  //初始连接数，默认0
        ds.setMaxActive(30);  //最大连接数，默认8
        ds.setMinIdle(10);  //最小闲置数
        ds.setMaxWait(2000);  //获取连接的最大等待时间，单位毫秒
        ds.setPoolPreparedStatements(true); //缓存PreparedStatement，默认false
        ds.setMaxOpenPreparedStatements(20); //缓存PreparedStatement的最大数量，默认-1（不缓存）。大于0时会自动开启缓存PreparedStatement，所以可以省略上一句代码
        long start = System.currentTimeMillis();
        //获取连接
        for (int i = 0; i < 5000; i++) {
            Connection con = ds.getConnection();
            con.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费了：" + (end - start));
    }
}
```

**通过配置文件druid.properties来初始化数据库连接池并使用：**

```properties
url=jdbc:mysql://localhost:3306/mysqltest?useSSL=false
#这个可以缺省的，会根据url自动识别
driverClassName=com.mysql.jdbc.Driver
username=root
password=123456

##初始连接数，默认0
initialSize=10
#最大连接数，默认8
maxActive=30
#最小闲置数
minIdle=10
#获取连接的最大等待时间，单位毫秒
maxWait=2000
#缓存PreparedStatement，默认false
poolPreparedStatements=true
#缓存PreparedStatement的最大数量，默认-1（不缓存）。大于0时会自动开启缓存PreparedStatement，所以可以省略上一句设置
maxOpenPreparedStatements=20
```

```java
public class DruidPool {
    public static void main(String[] args) throws SQLException {
		//数据源配置
        Properties pro = new Properties();
        //通过当前类的class对象获取资源文件
        InputStream is = DruidPool.class.getResourceAsStream("/druid.properties");
        pro.load(is);
        //返回的是DataSource，不是DruidDataSource
        DataSource dataSource = DruidDataSourceFactory.createDataSource(pro);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            //获取连接
            Connection conn = dataSource.getConnection();
            conn.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费了：" + (end - start ));
    }
}
```



## DBCP

需要用到的jar包：

```xml
<!-- https://mvnrepository.com/artifact/commons-dbcp/commons-dbcp -->
<dependency>
    <groupId>commons-dbcp</groupId>
    <artifactId>commons-dbcp</artifactId>
    <version>1.4</version>
</dependency>
<!-- https://mvnrepository.com/artifact/commons-pool/commons-pool -->
<dependency>
    <groupId>commons-pool</groupId>
    <artifactId>commons-pool</artifactId>
    <version>1.6</version>
</dependency>
```



## C3P0

需要的jar包：

```xml
<!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
<dependency>
    <groupId>com.mchange</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.5.5</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.mchange/mchange-commons-java -->
<dependency>
    <groupId>com.mchange</groupId>
    <artifactId>mchange-commons-java</artifactId>
    <version>0.2.19</version>
</dependency>
```

**直接在Java程序中配置并使用：**（（使用Spring也是创建对DruidDataSource的bean））

```java
// 1.创建数据源
ComboPooledDataSource ds = new ComboPooledDataSource();
// 2.设置数据源参数
ds.setDriverClass(driver);
ds.setJdbcUrl(url);
ds.setUser(user);
ds.setPassword(password);
// 3.设置初始化连接数和最大连接数
ds.setInitialPoolSize(10);
ds.setMaxPoolSize(15);
// 4.获取Connection连接
Connection con = ds.getConnection();
con.close();
```

**通过c3p0-config.xml配置文件初始化并使用：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>    
    <!-- This is default config! -->
    <!-- <default-config>
    	    <property name="initialPoolSize">10</property>
    	    <property name="maxIdleTime">30</property>
    	    <property name="maxPoolSize">100</property>
    	    <property name="minPoolSize">10</property>
    	    <property name="maxStatements">200</property>
    <!-- </default-config>-->
    <!-- This is my config for mysql-->   
    <named-config name="mysql">      
        <property name="driverClass">com.mysql.jdbc.Driver</property>      
        <!-- 设置字符集和时区 -->     
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/mysqltest?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT%2B8&amp;useSSL=false</property>  
        <property name="user">root</property>        
        <property name="password">123456</property>     
        <!-- 初始化连接池中的连接数，取值应在minPoolSize与maxPoolSize之间，默认为3--> 
        <property name="initialPoolSize">6</property>    
        <!--最大空闲时间，超过该秒数未使用则连接被丢弃。若为0则永不丢弃。默认值: 0 -->    
        <property name="maxIdleTime">20</property>     
        <!--连接池中保留的最大连接数。默认值: 15 -->   
        <property name="maxPoolSize">20</property> 
        <!-- 连接池中保留的最小连接数，默认为：3-->     
        <property name="minPoolSize">5</property>    
        <!-- 当连接池连接耗尽时，客户端调用getConnection()后等待获取新连接的时间，
		超时后将抛出SQLException，如设为0则无限期等待，单位毫秒。默认: 0 -->    
        <property name="checkoutTimeout">3000</property>     
        <!--当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。默认值: 3 -->  
        <property name="acquireIncrement">5</property>     
        <!--定义在从数据库获取新连接失败后重复尝试的次数。默认值: 30 ；小于等于0表示无限次-->  
        <property name="acquireRetryAttempts">5</property> 
        <!--重新尝试的时间间隔，默认为：1000毫秒-->    
        <property name="acquireRetryDelay">1000</property>     
        <!--关闭连接时，是否提交未提交的事务，默认为false，即关闭连接，回滚未提交的事务 -->    
        <property name="autoCommitOnClose">false</property>     
        <!--c3p0将建一张名为Test的空表，并使用其自带的查询语句进行测试。
		如果定义了这个参数那么属性preferredTestQuery将被忽略。你不能在这张Test表上进行任何操作，
		它将只供c3p0测试使用。默认值: null -->      
        <property name="automaticTestTable">Test</property>       
        <!--如果为false，则获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常，
		但是数据源仍有效保留，并在下次调用getConnection()的时候继续尝试获取连接。
		如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。默认: false-->       
        <property name="breakAfterAcquireFailure">false</property>  
        <!--每60秒检查所有连接池中的空闲连接。默认值: 0，不检查 -->  
        <property name="idleConnectionTestPeriod">60</property>     
        <!--maxStatementsPerConnection定义了连接池内单个连接所拥有的最大缓存statements数。默认值: 0 -->       
        <!--JDBC的标准参数，用以控制数据源内加载的PreparedStatements数量。
		但由于预缓存的statements属于单个connection而不是整个连接池。所以设置这个参数需要考虑到多方面的因素。
		如果maxStatements与maxStatementsPerConnection均为0，则缓存被关闭。Default: 0-->       
        <property name="maxStatements">200</property>      
        <property name="maxStatementsPerConnection">100</property>  
    </named-config>    
    <!-- This is my config for oracle -->
    <!-- <named-config name="oracle">
    	    <property name="driverClass">oracle.jdbc.driver.OracleDriver</property>
    	    <property name="jdbcUrl">jdbc:oracle:thin:@localhost:1521:</property>
    	    <property name="user">scott</property>
    	    <property name="password">abc123</property>
    	    <property name="initialPoolSize">10</property>
    	    <property name="maxIdleTime">30</property>
    	    <property name="maxPoolSize">100</property>
    	    <property name="minPoolSize">10</property>
    	    <property name="maxStatements">200</property>
    	 </named-config>-->
</c3p0-config>
```

```java
ComboPooledDataSource ds = new ComboPooledDataSource("mysql");
long start = System.currentTimeMillis();
// 4.获取Connection连接
for (int i = 0; i < 5000; i++) {
    Connection con = ds.getConnection();
    con.close();
}
long end = System.currentTimeMillis();
System.out.println("需要时间(ms)：" + (end - start));
```

我
