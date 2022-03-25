# SpringBoot

Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。

简而言之，SpringBoot项目也就是一个Maven项目，只不过其是spring-boot-starter-parent的依赖和其他的场景启动器、依赖组成，由spring-boot-starter-parent来进行依赖的管理，spring-boot-starter来提供自动配置等。Spring Boot框架的核心就在于依赖管理和自动配置。

# 搭建SpringBoot环境

搭建SpringBoot项目可以通过IDEA的SpringBoot向导来搭建，这里采用手动搭建方式。

**1、创建Maven项目，然后进行以下操作：**

场景启动器的引入：（starter场景的artifactId见https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter。）

```xml
<!-- pom.xml中引入父依赖 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.5</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
<!-- pom.xml中引入场景启动器：spring-boot-starter是必须的 -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    ......
</dependencies>
```

按以下示例创建好主启动类：

```java
@SpringBootApplication
public class Review01QuickstartApplication {
    public static void main(String[] args) {
        SpringApplication.run(Review01QuickstartApplication.class, args);
    }
}
```

**2、用yml文件或properties文件进行配置**

resources目录下创建application.properties或application.yml配置文件。

**3、利用SpringBoot整合MyBatis、Spring、SpringMVC等**

# SpringBoot-说明

## yml配置文件

YAML 是 "YAML Ain't Markup Language"（意为 YAML 不是一种标记语言）的递归缩写。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）。 非常适合用来做以数据为中心的配置文件。

### 基本语法

1.  `key: value`：k-v之间有空格。
2.  大小写敏感，使用`#`来注释。
3.  使用缩进表示层级关系。
4.  缩进不允许使用tab，只允许空格；缩进的空格数不重要，只要相同层级的元素左对齐即可。
5.  字符串无需加引号，如果要加，`' '`与`" "`分别表示字符串中内容会被转义或不转义（例如`\n`原本就是表示换行的转义字符，当使用单引号时会被转义两次，使用双引号时只转义一次（即此时该字符就表示换行）。

### 数据类型

```yaml
# 字面量写法：单个的、不可再分的值。date、boolean、string、number、null
key: value
keys: num 
url: "jdbc:mysql://localhost:3306/mysqldb"
# 对象写法：键值对的集合。map、hash、object 
object: {k1:v1,k2:v2,k3:v3,...}
object: 
    k1: v1
    k2: v2
    ...
# 数组：一组按次序排列的值。array、list、queue、set
k: [v1,v2,v3]
k:
 - v1
 - v2
 - v3
```

使用yaml语法来表示对象——示例：

```yaml
person:
  userName: zhangsan
  boss: false
  birth: 2019/12/12 20:12:33
  age: 18
  pet: 
    name: tomcat
    weight: 23.4
  interests: [篮球,游泳]
  animal: 
    - jerry
    - mario
  score:
    english: 
      first: 30
      second: 40
      third: 50
    math: [131,140,148]
    chinese: {first: 128,second: 136}
  salarys: [3999,4999.98,5999.99]
  allPets:
    sick:
      - {name: tom}
      - {name: jerry,weight: 47}
    health: [{name: mario,weight: 47}]
```

### yaml_处理器

```xml
<!-- 配置处理器的依赖，这样在使用yml配置文件时就有提示了 -->       
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-configuration-processor</artifactId>
	<optional>true</optional>
</dependency>
<!-- 打包时不加入配置处理器的jar文件 -->    
 <build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <exclude>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-configuration-processor</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 场景启动器与依赖管理

**关于spring-boot-starter-parent：**

```xml
<!-- spring-boot-starter-parent的父项目几乎声明了开发中所有常用的依赖的版本号 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.5.5</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

**关于spring-boot-starter：**

spring-boot-starter是所有场景启动器最底层的依赖、最基本的， 包含了对自动配置、日志、yaml的支持等，是不可缺少的，其他的所有starter都会引入有该starter，例如spring-boot-starter-web里第一个导入的依赖就是spring-boot-starter。

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <version>2.5.5</version>
</dependency>
```

**关于自动仲裁机制：**

由于spring-boot-starter-parent的父项目几乎声明了所有开发中常用的依赖的版本号，所以SpringBoot项目中引入依赖时都可以不写版本号（Maven的继承特性），不过引入非版本仲裁（在spring-boot-starter-parent的父项目没有声明）的jar依赖则要写版本号。

**修改依赖的默认版本号：**

```xml
<!-- 1、查看spring-boot-starter-parent 里的 spring-boot-dependencies，里面规定了依赖版本对应的key -->
<!-- 2、然后根据key在当前项目里面重写版本号配置 -->
<properties>
    <mysql.version>5.1.43</mysql.version>
</properties>
```

**starter场景启动器：**

SpringBoot**将我们常用的功能场景抽取出来，做成一系列的场景启动器**，这些启动器可以帮助我们导入实现各个功能所需要的依赖的全部组件，我们只需要在项目中引入这些starters，相关场景的所有依赖就会全部导入。（SpringBoot，抛弃了繁杂的配置，仅需要通过配置文件来进行少量的配置就可以使用相应的功能）

1. `spring-boot-starter-*`： *就是指某种场景，例如spring、mybatis等。
2. 只需要引入starter，这个场景的所有常规需要的依赖都会被引入。
3. SpringBoot所有场景启动器见：https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter。
4. `*-spring-boot-starter`： 这种命名方式的都是第三方为我们提供的用于简化开发的场景启动器。

## 关于自动配置

SpringBoot会根据引入的场景启动器，自动配置好引入场景下的所需要配置。



















