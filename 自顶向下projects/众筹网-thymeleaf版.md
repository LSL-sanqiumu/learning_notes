

# 一.环境搭建

## 1.项目骨架搭建：

![](img/项目骨架.png)

在父项目`my_atcrowdfundingweb`下创建三个子模块：

1. `atcrowdfunding01-admin-webui`：webapp项目，主模块，打包设为war。（引入依赖：`atcrowdfunding02-admin-component`）
2. `atcrowdfunding02-admin-component`：用于主要依赖的放置，减少主模块中pom文件的依赖配置。（引入依赖：`atcrowdfunding03-admin-entity`）
3. `atcrowdfunding03-admin-entity`：存放实体类。

辅助模块：

1. atcrowdfunding04-common-util：工具类管理，工具类也可直接放在主模块中，这样就不用再创建这个项目。
2. atcrowdfunding05-common-reverse：mybatis的逆向工程，用于生成数据库表的实体类、mapper接口、mapper.xml等，因为我们只用到生成的这些文件，所以抽出来单独使用。

## 2.依赖选择与管理

父项目使用dependencyManagement来管理依赖，具体项目模块再引入所需的。

### 使用spring涉及的相关依赖：

```xml
<lsl.spring.version>5.3.11</lsl.spring.version>
```

```xml
<!-- 使用spring涉及的相关依赖 start -->
<!-- Spring-ORM 提供了通过 ROM 技术访问数据库的简化样板，比如 Hibernate，My(i)Bati -->
<!-- https://mvnrepository.com/artifact/org.springframework/spring-orm -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-orm</artifactId>
    <version>5.3.11</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.11</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.3.11</version>
    <scope>test</scope>
</dependency>
<!-- AOP织入 -->
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.7</version>
    <scope>runtime</scope>
</dependency>
<!-- Cglib是一个强大的、高性能的代码生成包，它广泛被许多AOP框架使用，为他们提供方法的拦截 拦截器时用到 -->
<!-- https://mvnrepository.com/artifact/cglib/cglib -->
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.3.0</version>
</dependency>
<!-- 使用spring涉及的相关依赖 end -->
```

### 数据库、操作数据库的相关依赖：

```xml
<!-- 数据库、操作数据库的相关依赖 start -->
<!-- 数据库驱动 -->
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.21</version>
</dependency>
<!-- 数据库连接池的依赖 druid -->
<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.8</version>
</dependency>
<!-- MyBatis的依赖 -->
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.6</version>
</dependency>
<!-- spring与mybatis整合需要的依赖 -->
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>
<!-- mybatis分页插件 pagehelper -->
<!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>4.0.0</version>
</dependency>
<!-- 数据库、操作数据库的相关依赖 end -->
```

### 日志系统搭建需要的依赖：

```xml
<!-- 日志系统搭建需要的依赖 start -->
<!-- 日志：log4j -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.32</version>
</dependency>
<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.7</version>
</dependency>
<!-- 其他日志框架的中间转换包 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/jcl-over-slf4j -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>1.7.32</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jul-to-slf4j</artifactId>
    <version>1.7.32</version>
</dependency>
<!-- 日志系统搭建需要的依赖 end -->
```

### json：

```xml
<!-- spring进行json数据转换的依赖 start -->
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.13.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.13.0</version>
</dependency>
<!--  可以将一个Json字符转成一个Java对象，或者将一个Java转化为Json字符串 -->
<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.5</version>
</dependency>
<!-- spring进行json数据转换的依赖 end -->
```

### junit测试：

```xml
<!-- junit测试 start -->
<!-- https://mvnrepository.com/artifact/junit/junit -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
<!-- junit测试 end -->
```

### servlet的依赖：

（与前端进行数据展示等需要用到servlet的相关接口实现）

```xml
<!-- servlet容器相关依赖 start -->
<!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
<!-- servlet容器相关依赖 end -->
```

### thyme leaf的依赖：

```xml
<!--  thyme leaf的依赖 start -->
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
<!--  thyme leaf的依赖 end -->
```

### 权限管理，SpringSecurity：

```xml
<lsl.spring.security.version>5.5.3</lsl.spring.security.version>
```

```xml
<!-- SpringSecurity start  -->
<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-web -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-web</artifactId>
    <version>${lsl.spring.security.version}</version>
</dependency>
<!-- SpringSecurity 配置 -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-config</artifactId>
    <version>${lsl.spring.security.version}</version>
</dependency>

<!-- SpringSecurity 标签库 -->
<!-- https://mvnrepository.com/artifact/javax.servlet/jstl -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-taglibs</artifactId>
    <version>${lsl.spring.security.version}</version>
</dependency>
<!-- SpringSecurity e -->
```

## 3.数据库的创建

创建数据库：

```sql
create database IF NOT EXISTS `project_crowd1` CHARACTER SET utf8 COLLATE utf8_general_ci;
```

创建用户表：

```sql
create table if not exists `t_admin`(
id int not null auto_increment comment '主键', 
login_acct varchar(255) not null comment '登录账号',
user_pswd char(32) not null comment '登录密码',
user_name varchar(255) not null comment '昵称',
email varchar(255) not null comment '邮件地址',
create_time char(19) comment '创建时间', 
primary key (id)
)engine=innodb default charset=utf8;
```

## 4.mybatis逆向工程

另开一个项目（不是在`my_atcrowdfundingweb`项目下），命名为atcrowdfunding05-common-reverse，用于根据数据库表来生成对应数据库表的实体类、mapper接口、mapper.xml文件，待后面直接复制进`my_atcrowdfundingweb`项目。该maven项目结构如下：

![](img/mybatis_reverse.png)

pom.xml：

```xml
<dependencies>
  <!-- mybatis的依赖 -->
  <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
  <dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.6</version>
  </dependency>
  <!-- mybatis逆向工程需要用到的依赖 -->
  <!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-maven-plugin -->
  <dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.4.0</version>
  </dependency>
</dependencies>

<!-- 控制 Maven 在构建项目过程中的相关配置 -->
<build>
  <finalName>atcrowdfunding05-common-reverse</finalName>
  <!-- 构建过程中用到的插件 -->
  <plugins>
  <!-- 具体插件，逆向工程的操作是以构建过程中插件形式出现的 -->
    <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.4.0</version>
        <!-- 插件的依赖 -->
        <dependencies>
        <!-- 逆向工程的核心依赖 -->
          <!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->
          <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.4.0</version>
          </dependency>
          <!-- 数据库连接池 -->
          <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
          <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.5</version>
          </dependency>
          <!-- MySQL 驱动 -->
          <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
          <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.25</version>
          </dependency>
        </dependencies>
    </plugin>
  </plugins>
</build>
```

generatorConfig.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!-- mybatis-generator:generate -->
    <context id="lslTables" targetRuntime="MyBatis3">
        <commentGenerator>
            <!-- 是否去除自动生成的注释 true:是;false:否 -->
            <property name="suppressAllComments" value="true" />
        </commentGenerator>
        <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
        <jdbcConnection
                driverClass="com.mysql.cj.jdbc.Driver" connectionURL="jdbc:mysql://localhost:3306/project_crowd" userId="root" password="123456">
        </jdbcConnection>
        <!-- 默认 false，把 JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true 时把
        JDBC DECIMAL
        和 NUMERIC 类型解析为 java.math.BigDecimal -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>
        <!-- targetProject:生成 Entity 类的路径 -->
        <javaModelGenerator targetProject=".\src\main\java"
                            targetPackage="com.lsl.crowd.entity">
            <!-- enableSubPackages:是否让 schema 作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
            <!-- 从数据库返回的值被清理前后的空格 -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- targetProject:XxxMapper.xml 映射文件生成的路径 -->
        <sqlMapGenerator targetProject=".\src\main\java"
                         targetPackage="com.lsl.crowd.mapper">
            <!-- enableSubPackages:是否让 schema 作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </sqlMapGenerator>
        <!-- targetPackage：Mapper 接口生成的位置 -->
        <javaClientGenerator type="XMLMAPPER"
                             targetProject=".\src\main\java"
                             targetPackage="com.lsl.crowd.mapper">
            <!-- enableSubPackages:是否让 schema 作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaClientGenerator>
        <!-- 数据库表名字和我们的 entity 类对应的映射指定  如果要创建其他表的，下次修改这个即可创建 -->
        <table tableName="t_admin" domainObjectName="Admin" />
    </context>
</generatorConfiguration>
```

配置好后：执行maven插件命令，如下

![](img/mybatis_generator.png)

![](img/generator.png)



最后生成如上图的类、接口和含SQL命令的xml文件，把文件移到项目的entity、dao包下。

## 5.子项目依赖配置

`atcrowdfunding02-admin-component`的常用依赖：

```xml
<dependencies>
    <!-- spring的相关依赖 start -->
    <!-- Spring-ORM 提供了通过 ROM 技术访问数据库的简化样板，比如 Hibernate，My(i)Batis -->
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-orm -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-orm</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!-- Cglib是一个强大的、高性能的代码生成包，它广泛被许多AOP框架使用，为他们提供方法的拦截 -->
    <!-- https://mvnrepository.com/artifact/cglib/cglib -->
    <dependency>
        <groupId>cglib</groupId>
        <artifactId>cglib</artifactId>
    </dependency>
    <!-- spring的相关依赖 start -->

    <!-- 数据库 start -->
    <!-- 数据库驱动 -->
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- 数据库连接池的依赖 -->
    <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
    </dependency>
    <!-- 数据库 end -->

    <!-- mybatis  start -->
    <!-- MyBatis的依赖 -->
    <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
    </dependency>
    <!-- spring与mybatis整合 -->
    <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
    </dependency>
    <!-- mybatis分页插件 -->
    <!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper</artifactId>
    </dependency>
    <!-- mybatis  end -->

    <!-- 日志系统搭建需要的依赖 start -->
    <!-- 日志：log4j -->
    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
    </dependency>
    <!-- 日志系统搭建需要的依赖 end -->

    <!-- spring进行json数据转换的依赖 start -->
    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    <!--  可以将一个Json字符转成一个Java对象，或者将一个Java转化为Json字符串 -->
    <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
    </dependency>
    <!-- spring进行json数据转换的依赖 end -->
</dependencies>
```

atcrowdfunding01-admin-webui的依赖：

```xml
<dependencies>
  <dependency>
    <groupId>org.example</groupId>
    <artifactId>atcrowdfunding02-admin-component</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>

  <!--  thyme leaf的依赖 start -->
  <!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf -->
  <dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf</artifactId>
  </dependency>
  <!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf-spring5 -->
  <dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
  </dependency>
  <!--  thyme leaf的依赖 end -->

  <!-- servlet容器相关依赖 start -->
  <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
  <dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <scope>provided</scope>
  </dependency>
  <!-- servlet容器相关依赖 end -->
  
  <!-- junit测试 start -->
  <!-- https://mvnrepository.com/artifact/junit/junit -->
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <scope>test</scope>
  </dependency>
  <!-- junit测试 end -->
</dependencies>
```

## 6.spring整合mybatis

目标：进行数据库的增删改查操作。

思路：传统的jdbc编程太过繁琐、耦合高，使用spring、mybatis框架来连接、操作数据库更加方便、快捷；数据库连接池、SqlSessionFactory等交由spring的IOC容器管理。整合具体步骤：

1. 确定mybatis和spring的依赖都导入完毕；
2. 确定好映射文件、映射接口所在目录并创建；
3. 编写初始化spring容器的配置文件（这里先配置好数据源、SqlSessionFactory、自动映射配置MapperScannerConfigurer，要使用到spring的注解，所以也开启注解扫描）；
4. 编写mybatis的全局配置文件，数据源已经交由spring管理了，所以这里不需要配数据源，其它的按需配置；
5. 单元测试。

代码：

1.只用来管理mybatis的spring配置文件：spring-init-mybatis.xml

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <!-- 导入外部properties文件 -->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!-- 开启spring注解扫描 -->
    <context:component-scan base-package="com.lsl.crowd.service"/>

    <!-- 数据源 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.name}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:mybatis/mybatis-config.xml"/>
        <!-- mapper.xml文件 -->
        <property name="mapperLocations" value="classpath:mybatis/mapper/*Mapper.xml"/>
        <!-- 分页插件的使用 -->

    </bean>
    <!-- 自动扫描 将Mapper接口生成代理注入到Spring -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!-- 扫描该包下的接口 然后创建各自接口的动态代理类 -->
        <property name="basePackage" value="com.lsl.crowd.dao"/>
    </bean>
</beans>
```

```properties
# 连接数据库的配置文件
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/project_crowd?characterEncoding=utf8&amp;useUnicode=true&amp;useSSL=false&amp;serverTimezone=Asia/Shanghai
jdbc.name=root
jdbc.password=123456
```

2.映射器（接口和SQL映射文件）准备好、service层准备好后进行单元测试：

```java
@RunWith(SpringJUnit4ClassRunner.class)
/* 初始化容器 */
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class CrowdTest {

    /* 测试数据源 */
    @Autowired
    DataSource dataSource;
    @Test
    public void testDataSource() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    /* 测试mybatis */
    @Autowired
    AdminService adminService;
    @Test
    public void testMyBatis(){
        Admin admin = adminService.getAdmin(1);
        System.out.println(admin);
    }
}
```

## 7.搭建日志系统

### 日志概述

系统在运行过程中出了问题就需要通过日志来进行排查，所以我们在上手任何新技术的时候，都要习惯性的关注一下它是如何打印日志的。

![](img/log.png)



### 简单搭建

上面的步骤已经引入了日志需要的相关依赖：

```xml
<!-- 日志系统搭建需要的依赖 start -->
<!-- 日志：log4j -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
</dependency>
<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
</dependency>
<!-- 日志系统搭建需要的依赖 end -->
```

后面在resource目录下加上一个日志的配置文件就好了，这里不涉及过多的关于日志的知识；配置文件名为logback.xml，具体内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <!-- 指定日志输出的位置 这里是输出到控制台 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- 日志输出的格式 -->
            <!-- 按照顺序分别是：时间、日志级别、线程名称、打印日志的类、日志主体
            内容、换行 -->
            <pattern>[%d{HH:mm:ss.SSS}] [%-5level] [%thread] [%logger]
                [%msg]%n</pattern>
        </encoder>
    </appender>
    <!-- 设置全局日志级别。日志级别按顺序分别是：DEBUG、INFO、WARN、ERROR -->
    <!-- 指定任何一个日志级别都只打印当前级别和后面级别的日志。 -->
    <root level="INFO">
        <!-- 指定打印日志的 appender，这里通过“STDOUT”引用了前面配置的 appender -->
        <appender-ref ref="STDOUT" />
    </root>
    <!-- 根据特殊需求指定局部日志级别 -->
    <logger name="com.lsl.crowd.mapper" level="DEBUG"/>
</configuration>
```

## 8.spring声明式事务搭建

目标：由 Spring 来全面接管数据库事务。用声明式代替编程式。

思路：spring的事务使用了AOP思想，面向切面来为数据库操作添加事务处理。（复习spring的AOP思想与实现）

具体实现步骤：

1. 确定所需依赖都已导入；（spring-orm中就已经包括了spring-tx的包）
2. spring的事务，创建spring容器初始化配置spring-tx.xml，配置事务管理器和切面、切点、通知等；

代码：

需要的依赖spring、spring-tx、aop的：

```xml
<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
        </dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <scope>runtime</scope>
</dependency>
<!-- Cglib是一个强大的、高性能的代码生成包，它广泛被许多AOP框架使用，为他们提供方法的拦截 -->
<!-- https://mvnrepository.com/artifact/cglib/cglib -->
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
</dependency>
```

spring-init-tx.xml：

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
        <!-- 数据源已经在另一个spring容器配置文件声明创建了，运行时就能从ioc容器拿到了 -->
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

## 9.整合SpringMVC

SpringMVC是基于模型-视图-控制器（model、view、controller）模式实现，用于构建灵活、松耦合的web应用程序。（前端请求处理、页面展示渲染、参数接收处理、表单参数接收处理等。）

**SpringMVC与Servlet：**

Servlet：性能最好，处理Http请求的标准。SpringMVC：开发效率高（好多共性的东西都封装好了，是对Servlet的封装，核心的DispatcherServlet最终继承自HttpServlet）这两者的关系，就如同MyBatis和JDBC，一个性能好，一个开发效率高，是对另一个的封装。（复习servlet、springmvc的核心）


整合步骤：

1. 依赖导入（这里页面使用thymeleaf模板引擎来渲染，需要导入thymeleaf的依赖）；
2. SpringMVC的全局配置文件，spring-init-mvc.xml，配置视图解析器、开启注解支持、配置拦截器；
3. Tomcat的web.xml的配置（配置servlet（中央调度器）、处理字符编码问题的过滤器、以及初始化IOC容器）；
4. 测试。

spring-init-mvc.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <!-- 组件扫描器，使注解生效 -->
    <context:component-scan base-package="com.lsl.crowd.controller"/>

    <!-- thymeleaf的视图解析器 会与冲突：ContentNegotiatingViewResolver-->
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

web.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app  xmlns = "http://xmlns.jcp.org/xml/ns/javaee"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jsp.org/xml/ns/javaee/web-app_4_0.xsd"
          version="4.0"
          metadata-complete="true"
>

  <display-name>Archetype Created Web Application</display-name>

  <!-- Tomcat加载Spring的配置文件，根据Spring的配置文件初始化 IOC 容器。  -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring-init-*.xml</param-value>
  </context-param>
  <!-- 注册spring的监听器 -->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>

  <!-- 注册字符集过滤器，解决编码问题 start -->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <!-- 设置项目中使用的字符编码 -->
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <!-- 强制请求对象（HttpServletRequest）使用 -->
    <init-param>
      <param-name>forceRequestEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
    <!-- 强制应答对象(HttpServletResponse)使用 -->
    <init-param>
      <param-name>forceResponseEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <!-- 强制所有请求先经过过滤器 -->
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!-- 注册字符集过滤器，解决编码问题 end -->

  <!--注册中央调度器-->
  <servlet>
    <servlet-name>springDispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- 根据配置初始化 springmvc容器 -->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:spring-web-mvc.xml</param-value>
    </init-param>
    <!-- 随服务启动而加载 -->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>springDispatcherServlet</servlet-name>
    <url-pattern>*.do</url-pattern>
    <url-pattern>*.json</url-pattern>
  </servlet-mapping>
</web-app>
```























