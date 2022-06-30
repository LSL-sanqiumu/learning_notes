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
    <!-- 测试依赖 -->
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

## 配置文件

### yml配置文件

YAML 是 "YAML Ain't Markup Language"（意为 YAML 不是一种标记语言）的递归缩写。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）。 非常适合用来做以数据为中心的配置文件。

#### 基本语法

1.  `key: value`：k-v之间有空格。
2.  大小写敏感，使用`#`来注释。
3.  使用缩进表示层级关系。
4.  缩进不允许使用tab，只允许空格；缩进的空格数不重要，只要相同层级的元素左对齐即可。
5.  字符串无需加引号，如果要加，`' '`与`" "`分别表示字符串中内容会被转义或不转义（例如`\n`原本就是表示换行的转义字符，当使用单引号时会被转义两次，使用双引号时只转义一次（即此时该字符就表示换行）。

#### 数据类型

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

#### yaml_处理器

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

### 获取配置文件中的值

使用@Value注解，可以获取到yaml或properties配置文件的值，以properties为例：

```properties
# application.properties
school.name=shehuidaxue
website=http://ilyd.top/
```

```java
@Controller
public class GetConfigValue {
    @Value("${school.name}")
    String schoolName;
    @Value("${website}")
    String website;
    @RequestMapping("/web/value")
    public void getValue(){
        System.out.println(schoolName + website);
    }
}
```

### 自定义配置映射到对象

```properties
# application.properties
school.name=xiaodaxue
school.website=http://ilyd.top/
```

```java
@Component
@ConfigurationProperties(prefix = "school")
public class School {
    private String name;
    private String website;
	// get、set、构造器...
}
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

spring-boot-starter是所有场景启动器最底层的依赖、最基本的， 包含了自动化配置支持、日志、yaml文件解析的支持等，是不可缺少的，其他的所有starter都会引入有该starter，例如spring-boot-starter-web里第一个导入的依赖就是spring-boot-starter。

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

## 自动配置

**概述：**

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

**自动配置原理：**





## 开发技巧

### Lombok

idea中安装lombok插件，并在springboot项目中引入Lombok插件来简化JavaBean，关于Lombok的注解使用：

1. @Data：生成getter和setter方法。
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

# Profile功能

为了方便多环境适配，springboot简化了profile功能，使得可以快速切换环境。

**application-profile功能使用：**

1. 默认配置文件：`application.yaml`，在任何时候都会加载的配置文件。

2. 环境配置文件指定的规定：

   - `application-{env}.yaml`，环境标识名`{env}`随便写，例如`application-pro.yaml`。

3. 激活指定环境的两种方式：

   - 在默认配置文件中激活，激活方式为`spring.profiles.active=环境标识名`，例如`spring.profiles.active=pro`。
   - 命令行激活：`java -jar xxx.jar --spring.profiles.active=Xxx环境标识名 --xxxx=xxx ...`，（命令行启动SpringBoot项目jar包文件时可以指定配置文件中的配置项的值，运行时会覆盖原来的值，可指定多个）。

4. 指定好环境配置，默认配置与环境配置会同时生效，当有同名配置项时，profile配置中的优先（环境配置中的相同配置优先）。

5. 也可以在默认配置中配置多个环境：

   ```properties
   spring.profiles.group.myprod[0]=pdd
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

## 请求—路径映射

**关于请求映射路径：**

web开发场景下，前端控制器的请求映射路径最前面的`/`不再是代表`localhost:port/webappName/`，而是代表`localhost:port/`。

也可以设置一个前置路径，设置后所有的访问都要加上该前置路径，在application.yml中设置：

```yaml
server:
	servlet:
		content-path: /webapp
# （配置好以后好，前端控制器请求映射路径中最前面的`/`也就代表了`localhost:port/webapp/`）
```

**RestFul风格请求：**

默认不开启Rest风格，需要手动开启，兼容的PUT、DELETE、PATCH的请求方法必须是post请求，开启restful风格不影响表单原生get、post请求：

```yaml
spring:
  mvc:
    hiddenmethod:
      filter:
        enabled: true # 选择性开启表单rest风格
```

## 请求—数据接收

请求中携带参数、cookie、请求头等信息的接收，参考SpringMVC的注解使用与参数接收。

## 引入thymeleaf

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

引入场景启动器后，会自动配置好thymeleaf，其中两个配置如下：

```java
public static final String DEFAULT_PREFIX = "classpath:/templates/";
public static final String DEFAULT_SUFFIX = ".html";
```

即使用thymeleaf时，默认页面资源放于类路径下的`templates`目录。

如何使用thymeleaf，见Thymeleaf，HTML页面要引入thymeleaf的名称空间：

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

如果需要自定义页面资源放置路径：

```yaml
spring:
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
```

## 使用自定义拦截器

设置自定义拦截器来拦截访问路径，操作如下：

1.自定义拦截器

```java
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    // 目标执行前（controller前）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截逻辑：什么情况下拦截（拦截哪些在拦截器注册时的配置决定），什么情况下放行
        String uri = request.getRequestURI();
        log.info("拦截的请求" + uri);
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            return true; // 返回true，放行
        }
        session.setAttribute("msg","请登录~");
        response.sendRedirect("/");
        return false;
    }
    // 目标方法执行完成后（但页面没有渲染的时候）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    // 页面渲染完之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
```

自定义好拦截器后，注册并配置：

```java
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器并配置拦截路径 
        // 拦截器可以拦截一部分放行一部分：addPathPatterns-拦截路径 excludePathPatterns-放行路径
        // 如下：拦截/下所有资源的同时放行 "/","/login","/css/**","/js/**","/fonts/**","/images/**","/lib/**"
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/","/login","/css/**","/js/**","/fonts/**","/images/**","/lib/**");
    }
}
```

## 文件上传处理

**HTM页面中使用表单上传文件操作：**

```html
<!-- form表单 -->
<form role="form" action="/download" method="post" enctype="multipart/form-data">
   <!-- 单文件 -->
  <label for="img">头像</label>
  <input id="img" type="file" name="headImg" ><br>
    <!-- 多文件 -->
  <label for="mimg">生活照</label>
  <input id="mimg" type="file" name="photos" multiple><br>
  <input type="submit" value="提交">
</form>
```

**处理表单上传的文件：**

```java
@Controller
@Slf4j
public class FileController {
    @PostMapping(value = "/download")
    // MultipartFile会自动封装上传的文件
    public String download(@RequestPart("headImg") MultipartFile headerImg,
                           @RequestPart("photos") MultipartFile[] photos){
        log.info("上传信息:headerImg={},photos={}",headerImg.getSize(), photos.length);
        if(!headerImg.isEmpty()) {
            // 保存到文件服务器
            String originalFilename = headerImg.getOriginalFilename();
            try {
                headerImg.transferTo(new File("D:\\cache\\" + originalFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (photos.length > 0) {
            for (MultipartFile file:
                 photos) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    try {
                        file.transferTo(new File("D:\\cache\\" + originalFilename));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "success";
    }
}
```

**文件上传配置：**

```yaml
spring:
  servlet:
    multipart:
      # 默认限制上传单个文件不超过1MB，总请求不超过10MB
      max-file-size: 10MB
      max-request-size: 100MB
```

## 配置错误页面

默认情况下，SpringBoot将`/error`路径来处理所有错误的映射，对于机器客户端，它将生成JSON响应，其中包含错误、HTTP状态和异常消息的详细信息。对于浏览器客户端，响应一个“ whitelabel”错误视图，以HTML格式呈现相同的数据。

默认的静态资源目录下的`error`目录下的4xx.html、5xx.html会自动被解析，例如在类路径下的`public`静态资源目录下建立一个`error`目录，里面的404.html、505.html等页面资源会和404错误、505错误匹配。需要注意的是，有精确的**错误状态码页面**就匹配精确，没有就模糊匹配（匹配4xx.html）；如果都没有就触发白页。

如果使用模板引擎（例如thymeleaf），那在模板引擎的页面资源目录（templates目录）下创建error目录和错误页面也是一样的。

## 全局异常处理

**自定义错误、异常的处理逻辑：**

1. 使用`@ControllerAdvice`、`@ExceptionHandler`来处理全局异常（底层是 ExceptionHandlerExceptionResolver 支持的；常用的方式）。
2. @ResponseStatus + 自定义异常 ：
   - 底层是 ResponseStatusExceptionResolver ，把responsestatus注解的信息底层调用 response.sendError(statusCode, resolvedReason)；tomcat发送的/error。



## servlet原生组件

SpringBoot支持嵌入式servlet容器，可以使用Servlet、Filter、Listener等这些web元生组件进行开发。





# 数据操作场景

## 整合JDBC场景

### 具体操作

**1、引入jdbc场景启动器、引入数据库驱动：**

```xml
<!-- 导入场景和驱动包 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.46</version> <!-- 利用maven的就近依赖修改版本 -->
</dependency>
<!-- 修改版本号的方法2：在properties指定 -->
<properties>
    <java.version>1.8</java.version>
    <mysql.version>5.1.46</mysql.version>
</properties>
```

jdbc场景导入内容有：HikariCP（数据库连接池）、spring-jdbc、spring-tx。但是没有导入数据库驱动（因此需要另外声明依赖导入），为什么呢？因为官方不知道你要使用哪些数据库，所以数据库驱动没有安装进去，但数据库驱动的版本仲裁还是存在的。

jdbc场景下自动配置好的内容：

1. DataSourceAutoConfiguration ： （数据源的自动配置）
   - 如果要修改数据源相关的就在配置文件中配置**spring.datasource**下的相关内容。
   - 当自己容器中没有数据源，会自动配置一个数据库连接池，**底层配置好的连接池是：HikariDataSource**。
2. DataSourceTransactionManagerAutoConfiguration（事务管理器的自动配置）。
3. JdbcTemplateAutoConfiguration： springboot自带的**JdbcTemplate**，其自动配置，可以来对数据库进行crud。
   - 可以修改这个配置项@ConfigurationProperties(prefix = "spring.jdbc")来修改JdbcTemplate。
   - @Bean @Primary    JdbcTemplate；容器中有JdbcTemplate这个组件。
4. JndiDataSourceAutoConfiguration： jndi的自动配置。
5. XADataSourceAutoConfiguration： 分布式事务相关的。

**2.自定义数据源：**

因为这里使用的是druid，需要导入其依赖。

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    url: jdbc:mysql://localhost:3306/mysqltest?useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: 123456
# 开启日志功能
logging:
  level:
    com.lsl.mappper: debug
```

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.8</version>
</dependency>
```

**3.测试：**直接在项目的test目录里测试。

```java
@SpringBootTest
public class JdbcTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Test
    public void getInfo(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from info");
        for (int i = 0; i < maps.size(); i++) {
            System.out.println(maps.get(i));
        }
    }
}
```

### 关于数据源

如果不使用默认的数据源，而是自己配置其他的数据库连接池时，以druid数据库连接池为例，有两种配置方式：一种是自定义引入，另一种是直接引入相关的数据库连接池场景启动器。

**方式一：自定义引入**

1、先导入依赖。

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.8</version>
</dependency>
```

2、往容器注册组件，当容器中存在数据源时，就不会使用默认的数据源，有两种方式。

```java
// 方式一：
@Component
public class MyDataSources {
    // 将数据源装配进容器 自动配置会默认先判断容器中有没有数据源，如果没有就使用默认的数据源
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        DruidDataSource duridDataSource = new DruidDataSource();
        return duridDataSource;
    }
}
```

```yaml
# 方式二：直接在配置文件中指定spring.datasource.type
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
```

**方式二：starter方式引入**

通过druid的场景启动器引入druid。

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.17</version>
</dependency>
```

引入数据源场景后，就可以直接在yaml配置文件中进行配置了；如果要使用 druid 数据库连接池的功能，都可以在yaml中对druid进行配置，如何配置见：[github.com](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)。

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mysqltest?useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: 123456
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
```

## 整合MyBatis

官方文档：[GitHub - mybatis/spring-boot-starter: MyBatis integration with Spring Boot](https://github.com/mybatis/spring-boot-starter)

### 具体操作

1、引入mybatis的场景启动、引入数据库驱动、引入数据库连接池。

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.0</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.46</version> <!-- 利用maven的就近依赖修改版本 -->
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.17</version>
</dependency>
```

当引入了mybatis的场景后，SpringBoot自动配置好的配置如下：

1. 导入了jdbc、mybatis-spring、spring-tx、HikariCP等。
2. SqlSessionFactory、SqlSessionFactoryBean、DataSource。
3. MybatisProperties：mybatis配置绑定类。
4. SqlSessionTemplate：自动配置好了，组合了SqlSession。

**2、yaml配置数据源**

```yaml
spring:
  mvc:
    static-path-pattern: /resource/**
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    url: jdbc:mysql://localhost:3306/mysqltest?useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: 123456
# 开启日志功能
logging:
  level:
    com.lsl.mappper: debug
```

### 使用配置文件实现数据操作

**1、准备好SQL映射文件和mapper接口**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lsl.dao.InfoMapper">
    <select id="getOne" resultType="java.lang.String">
        select name from info where id=1
    </select>
</mapper>
```

```java
@Mapper
public interface InfoMapper {
    String getOne();
}
```

**2、在yaml配置文件中的配置mybatis：**

```yaml
mybatis:
  # 引入mybatis全局配置文件
  # config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations: classpath:mapper/*.xml # 指定mapper映射文件
  configuration:
    map-underscore-to-camel-case: true # 开启驼峰命名
```

MyBatis的全局配置文件mybatis-config.xml可用可不用：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 开启驼峰命名 表中字段 t_user ===> tUser -->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```

【注意】：yaml配置文件中`config-location: classpath:mybatis/mybatis-config.xml`和`configuration`不能共存，要么使用mybatis-config.xml来配置，要么使用configuration（建议：使用configuration来进行mybatis全局配置）。

**3、测试：**

```java
// service层
@Component
public class InfoService {
    @Autowired
    InfoMapper infoMapper;
    public void showAll(){
        String name = infoMapper.getOne();
        System.out.println(name);
    }
}
```

```java
@SpringBootTest
public class JdbcTest {
    @Autowired
    InfoService infoService;
    @Test
    public void getAll(){
        infoService.showAll();
    }
}
```



### 使用注解实现数据操作

直接在mapper接口的方法上使用`@Insert`、`@Select`、 @Options等注解来实现数据操作，不需要映射文件。

使用@Select将mapper接口的方法与SQL语句绑定，如下：

```java
@Mapper
public interface InfoMapper {
    @Select(value = "select name from info where id=1")
    String getOne();
}
```

映射文件中有以下的SQL：

```xml
<insert id="addStudent" parameterType="com.lsl.pojo.Student" useGeneratedKeys="true" keyProperty="id">
    insert into info(name,age,school) values (#{name},#{age},#{school})
</insert>
<!-- useGeneratedKeys="true" keyProperty="id" 的作用在于插入完成后返回数据库中自增的id值，只对insert有效 -->
<!-- 返回的值放入传参对象的id属性，keyProperty控制返回给哪个属性 -->
```

```java
// 上面SQL语句的注解方式
@Mapper
public interface InfoMapper {
    @Insert(value = "insert into info(name,age,school) values (#{name},#{age},#{school})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean addInfo(Student student);
}
```

```java
@Component
public class InfoService {
    @Autowired
    InfoMapper infoMapper;
    public void addInfo(Student student){
        infoMapper.addInfo(student);
    }
}
```

```java
// 测试，
// @Options(useGeneratedKeys = true, keyProperty = "id")，数据插入后返回主键的值并赋给Student的id属性
@SpringBootTest
public class JdbcTest {
    @Autowired
    InfoService infoService;
    @Test
    public void addOne(){
        Student student = new Student(null,"李梁",33,"稷下学院");
        infoService.addInfo(student);
        System.out.println(student.getId());
    }
}
```

**关于混合模式的使用：注解与映射文件相结合（简单的方法就使用注解，复杂的SQL方法就使用映射文件）。**

1. @SpringBootApplication注解会默认扫描其所在的包及子包。
2. `@MapperScan("com.lsl.xxx")`：用于主配置类上，用来扫描Mapper接口所在包，那就就不用为每一个mapper接口都标注上@Mapper注解。
3. `@Mapper`：用于mapper接口。

### 步骤总结

1. 引入mybatis的场景启动、引入数据库驱动、引入数据库连接池。
2. 在application.yaml中配置，指定mapper-location位置、数据源配置即可。
3. 编写Mapper接口并使用@Mapper注解标注。
4. 简单的SQL直接使用注解方式来与mapper接口的方法绑定；复杂的SQL，就使用mapper.xml来进行绑定。
5. 在主配置类使用`@MapperScan("com.atguigu.admin.mapper") `，mapper接口上就可以不用标注@Mapper注解。

## 整合MybatisPlus

MyBatis-Plus（简称 MP）是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。
[简介 | MyBatis-Plus (baomidou.com)](https://baomidou.com/guide/)，建议在idea中安装 MybatisX 插件 。

### 具体操作

**1、引入mybatis-plus的场景启动、引入数据库驱动、引入数据库连接池。**

```xml
<!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.3.4</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.46</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.17</version>
</dependency>
```

当引入了mybatis-plus-boot-starter后，SpringBoot自动配置好的配置如下：

1. MybatisPlusProperties：配置项绑定，**在yaml配置文件中`mybatis-plus: xxx`就是对mybatis-plus的定制。**
2. SqlSessionFactory、SqlSessionTemplate。
3. mapperLocations自动配置好了，默认值是`classpath*:/mapper/**/*.xml`，表示：任意包的类路径下的mapper文件夹下的任意路径下的xml文件，都是SQL映射文件。
4. `@Mapper`接口标注的接口会被自动扫描生效。（建议在主配置类使用`@MapperScan`注解）
5. 数据源是从容器中获取，给容器放啥数据源就用啥数据源。
6. 默认的数据源的配置：和jdbc、数据库连接池的配置一样

**2、测试：**

1. 创建对应表的实体类，实体类和表的名称映射默认开启驼峰命名方式。

   ```java
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @ToString
   public class Info {
       private Integer id;
       private String name;
       private int age;
       private String school;
       private Date nowadays;
       private Integer pid;
   }
   ```

2. 映射接口继承BaseMapper接口：

   ```java
   // 泛型指定的类型，也决定了SQL语句调用哪个表，如下的接口就是操作`info`表
   // 继承后就可使用接口中的方法进行操作了，也可以再使用映射文件拓展其他的复杂的SQL语句
   @Mapper
   public interface TestMapper extends BaseMapper<Info> {
   
   }
   ```

3. 测试：

   ```java
   @SpringBootTest
   class SpringbootFileApplicationTests {
       @Autowired
       TestMapper testMapper;
       @Test
       void plus(){
           Info info = testMapper.selectById(1);
           System.out.println(info);
       }
   }
   ```

4. **如果需要其他的SQL语句，可以在`resources`目录下新建`mapper`文件夹，该文件夹下的SQL映射文件会被自动扫描生效。**

## 整合Redis

**1、引入Redis的场景启动器：**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

**2、yaml配置文件中配置Redis的地址和端口：**

```yaml
spring:
  redis:
    host: 192.168.137.130
    port: 6379
```

**3、往容器添加一个组件，如下：**该组件用于自定义序列化方式，几乎包含了所有场景。

```java
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // string的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key hash采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        // value、Hash的Value序列化方式使用Jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
```

**4.测试：**

```java
@SpringBootTest
class SpringbootFileApplicationTests {
    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;
    @Test
    void testRedis() {
        // 对String类型数据的操作的对象
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("hello","hello world");
    }
}
```

**切换使用jedis来进行操作：**（jedis就是基于java语言的redis客户端，集成了redis的命令操作，提供了连接池管理。）

1.添加jedis依赖：

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```

2.配置client-type：

```yaml
spring:
    redis:
      host: 192.168.137.129
      port: 6379
      client-type: jedis
```

3.测试

```java

@SpringBootTest
class SpringbootFileApplicationTests {
    @Test
	void jedisTest(){
    	Jedis j = new Jedis("192.168.137.129",6379);
    	System.out.println(j.ping("连接成功"));
	}
}
```

# JUnit5

## 概述

官方文档：[JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

Spring Boot 2.2.0 版本开始引入 JUnit 5 作为单元测试默认库，作为最新版本的JUnit框架，JUnit5与之前版本的Junit框架有很大的不同。由三个不同子项目的几个不同模块组成：JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage。

1. JUnit Platform: Junit Platform是在JVM上启动测试框架的基础，不仅支持Junit自制的测试引擎，其他测试引擎也都可以接入。
2. JUnit Jupiter: JUnit Jupiter提供了JUnit5的新的编程模型，是JUnit5新特性的核心。内部 包含了一个**测试引擎**，用于在Junit Platform上运行。
3. JUnit Vintage: 由于JUint已经发展多年，为了照顾老的项目，JUnit Vintage提供了兼容JUnit4.x、Junit3.x的测试引擎。

![](img/Junit5.jpg)

【注意】：SpringBoot 2.4 以上版本移除了默认对 Vintage 的依赖。如果需要兼容junit4则需要自行引入（也就是不能使用junit4的@Test，只能使用Junit5的）Vintage ：

```xml
<!-- 引入Vintage 用于兼容Junit4 -->
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

如何区分是Junit4的还是Junit5的：`import org.junit.jupiter.api.Test;`——Junit5；`import org.junit.api.Test;`——Junit4。

## 使用环境

以前SpringBoot中单元测试：`@SpringBootTest + @RunWith(SpringTest.class)`。

SpringBoot整合Junit以后：

- 编写测试方法：`@Test`标注（注意需要使用junit5版本的注解，`import org.junit.jupiter.api.Test;`）
- Junit类具有Spring的功能：`@Autowired`、比如 `@Transactional` 标注测试方法，测试完成后自动回滚。

1.场景

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

2.test目录下等。

## 常用注解

JUnit5的注解与JUnit4的注解有所变化：`https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations`。

1. @Test ：表示方法是测试方法。但是与JUnit4的@Test不同，他的职责非常单一不能声明任何属性，拓展的测试将会由Jupiter提供额外测试。
2. @DisplayName :为测试类或者测试方法设置展示名称。
3. @BeforeEach :表示在每个单元测试之前执行。
4. @AfterEach :表示在每个单元测试之后执行。
5. @BeforeAll :表示在所有单元测试之前执行。
6. @AfterAll :表示在所有单元测试之后执行。
7. @Tag :表示单元测试类别，类似于JUnit4中的@Categories。
8. @Disabled :表示测试类或测试方法不执行，类似于JUnit4中的@Ignore。
9. @Timeout :表示测试方法运行如果超过了指定时间将会返回错误。
10. @ExtendWith :为测试类或测试方法提供扩展类引用。
11. @ParameterizedTest ：表示方法是参数化测试。
12. @RepeatedTest：表示方法可重复执行。

`@SpringBootTest`注解包含的Junit5注解：（使用`@SpringBootTest`就可以使用容器功能了）

```java
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith({SpringExtension.class})
```

```java
@DisplayName("Junit5功能测试类")
// @SpringBootTest 以SpringBoot启动的方式测试
public class Junit5Test {

    @DisplayName("为该方法设置的展示名称：单元测试1")
    @Test
    void testDisplayName(){
        System.out.println("单元测试1");
    }
    @Disabled // 禁用单元测试方法或单元测试类
    @DisplayName("为该方法设置的展示名称：单元测试2")
    @Test
    void test2(){
        System.out.println("单元测试2");
    }
    // 超过多少时间就认为超时，并抛出TimeoutException
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @Test
    void testTimeOut() throws InterruptedException {
        Thread.sleep(500);
    }
    // @RepeatedTest(2)：重复测试2次

    // @Test
    @RepeatedTest(2)
    void testRepeatedTest(){
        System.out.println("重复...");
    }
    // @BeforeEach：在每个单元测试执行前执行
    @BeforeEach
    void testBeforeEach(){
        System.out.println("测试方法开始执行：");
    }
    // @BeforeEach：在每个单元测试执行后执行
    @AfterEach
    void testAfterEach(){
        System.out.println("测试方法执行结束！");
    }

    // @BeforeEach：在所有单元测试执行前执行
    @BeforeAll
    static void testBeforeAll(){
        System.out.println("所有测试方法开始执行：");
    }
    // @BeforeEach：在所有单元测试执行后执行
    @AfterAll
    static void testAfterAll(){
        System.out.println("所有测试方法结束！");
    }
}
```

## 使用断言

断言（assertions）是测试方法中的核心部分，用来对测试需要满足的条件进行验证。这些断言方法都是 org.junit.jupiter.api.Assertions 的静态方法。

断言就是用来检查业务逻辑返回的数据是否合理。使用断言的好处是——所有的测试运行结束以后，会有一个详细的测试报告。

JUnit 5 内置的断言可以分成六大类：简单断言、数组断言、组合断言、异常断言、超时断言、快速失败。某个测试中某次断言失败后，该测试方法之后的代码都不会被执行。

### 简单断言

用来对单个值进行简单的验证。如：

| 方法            | 说明                                   |
| --------------- | -------------------------------------- |
| assertEquals    | 判断两个对象或两个基本类型值是否相等   |
| assertNotEquals | 判断两个对象或两个基本类型值是否不相等 |
| assertSame      | 判断两个对象**引用**是否指向同一个对象 |
| assertNotSame   | 判断两个对象**引用**是否指向不同的对象 |
| assertTrue      | 判断给定的**布尔值**是否为 true        |
| assertFalse     | 判断给定的**布尔值**是否为 false       |
| assertNull      | 判断给定的**对象引用**是否为 null      |
| assertNotNull   | 判断给定的**对象引用**是否不为 null    |

### 数组断言

通过 assertArrayEquals 方法来判断两个对象或原始类型的数组是否相等：

```java
@DisplayName("测试数组断言")
@Test
void testArrayAssertions(){
    // (期望值，实际值)
    Assertions.assertArrayEquals(new int[]{1, 2}, new int[] {1, 2});
    System.out.println();
}
```

### 组合断言

assertAll 方法接受多个 org.junit.jupiter.api.Executable 函数式接口的实例作为要验证的断言，可以通过 lambda 表达式很容易的提供这些断言。

```java
@Test
@DisplayName("assert all")
public void all() {
    Assertions.assertAll("Math",
            () -> Assertions.assertEquals(2, 1 + 1),
            () -> Assertions.assertTrue(1 > 0)
    );
}
```

### 异常断言

在JUnit4时期，想要测试方法的异常情况时，需要用**@Rule**注解的ExpectedException变量还是比较麻烦的。而JUnit5提供了一种新的断言方式**Assertions.assertThrows()** ，配合函数式编程就可以进行使用。

```java
@Test
@DisplayName("异常断言")
void testException(){
    // 断定业务逻辑出现异常 预期-实际
    Assertions.assertThrows(ArithmeticException.class,() -> {
        int i = 10 / 0;},"业务逻辑居然正常运行");
}
```

### 超时断言

Junit5还提供了**Assertions.assertTimeout()** 为测试方法设置了超时时间。

```java
@Test
@DisplayName("超时测试")
public void timeoutTest() {
    //如果测试方法时间超过1s将会异常
    Assertions.assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(500));
}
```

### 快速失败

通过 fail 方法直接使得测试失败。

```java
@Test
@DisplayName("fail")
public void shouldFail() {
    if (2 == 2){
        Assertions.fail("This should fail");
    }
}
```

## 前置条件

JUnit 5 中的前置条件（assumptions【假设】）类似于断言，不同之处在于：**断言预期与实际不符合会使得测试方法失败**，而**前置条件不满足只会使得测试方法的执行终止**。前置条件可以看成是测试方法执行的前提，当该前提不满足时，就没有继续执行该单元测试的必要。

```java
@DisplayName("测试前置条件")
@Test
void testAssumptions(){
    // 前置条件不满足，该测试方法失效，相对于满足条件时就@Disabled一样
    Assumptions.assumeTrue(false,"结果不是true");
    System.out.println("前置条件Assumptions");
}
```

## 嵌套测试

JUnit 5 可以通过 Java 中的**内部类**和`@Nested`注解实现嵌套测试，从而可以更好的把相关的测试方法组织在一起。在内部类中可以使用@BeforeEach 和@AfterEach 注解，而且嵌套的层次没有限制。

```java
@DisplayName("嵌套测试")
class TestingAStackDemo {

    Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {

            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
            }

            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }
    }
}
```

## 参数化测试

参数化测试是JUnit5很重要的一个新特性，它使得**用不同的参数多次运行测试**成为了可能，也为我们的单元测试带来许多便利。

利用**@ValueSource**等注解，指定入参，我们将可以使用不同的参数进行多次单元测试，而不需要每新增一个参数就新增一个单元测试，省去了很多冗余代码。（入参：参数传入）

- **@ValueSource**: 为参数化测试指定入参来源，支持八大基础类以及String类型，Class类型。
- **@NullSource**: 表示为参数化测试提供一个null的入参。
- **@EnumSource**: 表示为参数化测试提供一个枚举入参。
- **@CsvFileSource**：表示读取指定CSV文件内容作为参数化测试入参。
- **@MethodSource**：表示读取指定方法的返回值作为参数化测试入参(注意方法返回需要是一个流)。

当然如果参数化测试仅仅只能做到指定普通的入参还达不到让我觉得惊艳的地步。让我真正感到他的强大之处的地方在于他可以支持外部的各类入参。如：CSV、YML、JSON 文件甚至方法的返回值也可以作为入参。只需要去实现**ArgumentsProvider**接口，任何外部文件都可以作为它的入参。

```java
@ParameterizedTest
@ValueSource(strings = {"one", "two", "three"})
@DisplayName("参数化测试1")
public void parameterizedTest1(String string) {
    System.out.println(string);
    Assertions.assertTrue(StringUtils.isNotBlank(string));
}
@ParameterizedTest
@MethodSource("method")    //指定方法名
@DisplayName("方法返回值入参")
public void testWithExplicitLocalMethodSource(String name) {
    System.out.println(name);
    Assertions.assertNotNull(name);
}

static Stream<String> method() {
    return Stream.of("apple", "banana");
}
```

# 指标监控









# 高级特性













# 原理解析



