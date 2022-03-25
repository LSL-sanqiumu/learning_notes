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

SpringBoot会根据引入的场景启动器，自动配置好引入场景所需要的配置。例如：

1. 引入starter-tomcat场景后，就会自动配置好Tomcat： 

   ```xml
   <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-tomcat</artifactId>
         <version>2.3.4.RELEASE</version>
         <scope>compile</scope>
   </dependency>
   ```

2. 引入starter-web场景：

   - 自动配置好SpringMVC：引入SpringMVC全套组件（视图解析器等）。
   - 自动配置好Web常见功能，例如：字符编码问题，所有web开发的常见场景配置。

3. SpringBoot会默认配置包结构：

   - 默认情况下主配置类所在包及其所有子包里的组件都会被扫描，无需Spring的包扫描配置。

   - 可以使用`@SpringBootApplication(scanBasePackages="com.lsl")`或者`@ComponentScan `改变包扫描路径。

     ```java
     @SpringBootApplication 该注解等同于下面三个注解的功能集合体
     @SpringBootConfiguration // 标记为配置类
     @EnableAutoConfiguration // 自动配置
     @ComponentScan // 包扫描，默认扫描当前包和子包，使spring注解生效
     ```

4. 自动配置好的配置都有默认值：

   - 例如配置好Tomcat，也就为Tomcat配置了一个默认端口。
   - 默认配置最终都是映射到某个类上，如：MultipartProperties；配置文件的值最终会绑定在某个类上，这个类将由容器管理并创建对象。
   - **对默认配置进行修改，就使用yaml文件或properties文件。**

5. 自动配置的按需加载：

   1. SpringBoot的所有的自动配置功能都在`spring-boot-autoconfigure.jar`包里面，里面包含了所有的自动配置项，只有引入了相关场景，相关场景的自动配置才生效。
   2. 按需加载：通过starter来开启自动配置，引入了哪些starter场景，哪些场景的自动配置才会开启。

查看SpringBoot的自动配置配置了哪些东西和配置的数量：

```java
@SpringBootApplication
public class LslApplication {
    public static void main(String[] args) {
        // SpringApplication.run(LslApplication.class,args);
        ConfigurableApplicationContext run = SpringApplication.run(LslApplication.class, args);
        int count = run.getBeanDefinitionCount();
        String[] names = run.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("自动配置数量："+count);
    }
}
```

## 开发技巧

### Lombok

idea中安装lombok插件，并在springboot项目中引入Lombok插件来简化JavaBean，关于Lombok的注解使用：

1. @Data：生成getset方法。
2. @ToString：tostring方法。
3. @NoArgsConstructor、@AllArgsConstructor：生成无参或有参构造器。
4. @EqualsAndHashCode：重写equals和hashcode方法。
5. @Slf4j：日志记录器。

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <!-- 项目打包时排除lombok的jar包 -->
                    <exclude>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### dev-tools

`ctrl + f9`重启SpringBoot项目。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

### processor

```xml
<!-- 配置处理器的依赖，这样在使用yml配置文件时就有提示了 -->       
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-configuration-processor</artifactId>
	<optional>true</optional>
</dependency>

 <build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <!-- 打包时不加入配置处理器的jar文件 -->   
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

# Web开发场景-SpringMVC

Spring Boot 为 Spring MVC 提供了自动配置，并在 Spring MVC 默认功能的基础上添加了以下特性：

1. 引入了 ContentNegotiatingViewResolver 和 BeanNameViewResolver（视图解析器）。
2. 对包括 WebJars 在内的静态资源的支持。
3. 自动注册 Converter、GenericConverter 和 Formatter （转换器和格式化器）。
4. 对 HttpMessageConverters 的支持（Spring MVC 中用于转换 HTTP 请求和响应的消息转换器）。
5. 自动注册 MessageCodesResolver（用于定义错误代码生成规则）。
6. 支持对静态首页（index.html）的访问。
7. 自动使用 ConfigurableWebBindingInitializer。


只要我们在 Spring Boot 项目中的 pom.xml 中引入了 spring-boot-starter-web ，即使不进行任何配置，也可以直接使用 Spring MVC 进行 Web 开发。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## web场景下的静态资源

web开发场景下，SpringBoot项目默认情况下在classpath路径下有几个目录是**默认的静态资源存放目录**：

1. ` /static `、`/public `、`/resources `、` /META-INF/resources`。
2. 当访问某些资源时，映射路径不经controller处理时，默认是到静态资源目录下寻找并访问。

**静态资源映射原理：**静态资源请求映射的路径是`/**`。当发起请求来访问资源，会先通过controller进行处理，所有不能经controller处理的请求就交给了静态资源处理器来处理，如果找不到资源就报404错误。 

**在yml配置文件中自定义静态资源相关配置：**

1. `static-path-pattern`：用来设置静态资源的**映射前缀**（默认是`static-path-pattern: /**`）。
   - 例1：请求访问`localhost:8888/a.png`，会在静态资源目录下寻找，找到就能成功访问a.png资源。
   - 例2：请求访问`localhost:8888/r/a.png`，会在静态资源目录下的`r`目录下寻找，找到就能成功访问a.png资源。
   - 配置为`static-path-pattern: /res/**`，访问静态资源时就得加上res前缀，如`localhost:8888/res/r/a.png`，资源仍然是到默认的静态资源目录下寻找。
2. `static-locations`：用于自定义默认静态资源存放路径，修改后原默认静态资源目录失效。
   - 默认的值为：`[classpath:/static/,classpath:/public/,classpath:/resources/,classpath:/META-INF/resources/]`。

```yaml
spring:
  mvc:
    static-path-pattern: /res/**
  web:
    # 修改默认静态资源路径，修改后原默认静态资源目录失效
    resources:
      static-locations: [classpath:/newstatic/,classpath:/newtemplates/]
      #static-locations: classpath:/newstatic/
```

**对webjar的支持：**

还支持webjar，会自动映射 /webjars/**，见https://www.webjars.org/。

```xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.5.1</version>
</dependency>
```

访问地址：`http://localhost:8080/webjars/jquery/3.5.1/jquery.js`，后面地址要按照依赖里面的包路径。

**欢迎页面：**

静态资源路径（这里说路径即目录）下的index.html可以作为欢迎页面，访问`localhost:port/`会自动跳转至这个页面，但是是在没有配置静态资源前缀的前提下。

**网页标签图标：**

静态资源路径下的favicon.ico图标可以作为网页标签图标，只有当没有配置静态资源路径前缀的时候才有效。

## 请求处理

**关于请求映射路径：**

web开发场景下，前端控制器的请求映射路径最前面的`/`不再是代表`localhost:port/webappName/`，而是代表`localhost:port/`。

也可以设置一个前置路径，设置后所有的访问都要加上该前置路径，在application.yml中设置：

```yaml
server:
	servlet:
		content-path: /webapp
# （配置好以后好，前端控制器请求映射路径中最前面的`/`也就代表了`localhost:port/webapp/`）
```

### 接收请求参数

@PathVariable：获取路径变量，可以指定key来获取某一个，也可以直接获取全部变量值。





















