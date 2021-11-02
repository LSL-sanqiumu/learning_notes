# 依赖管理

```xml
<!-- spring-boot-starter-parent几乎声明了所有开发中常用的依赖的版本号,自动版本仲裁机制 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.5.5</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
<!-- 导入starter场景启动器 spring-boot-starter是所有场景启动器最底层的依赖 -->
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <version>2.5.5</version>
</dependency>
```

自动仲裁机制：引入的依赖默认都可以不写版本号（Maven的继承特性），引入非版本仲裁的jar依赖，要写版本号；

修改依赖的默认版本号：

```xml
<!-- 1、查看spring-boot-starter-parent 里的 spring-boot-dependencies，里面规定了依赖版本对应的key-->
<!-- 2、在当前项目里面重写版本号配置 -->
    <properties>
        <mysql.version>5.1.43</mysql.version>
    </properties>
```

starter场景启动器：

1、见到很多 spring-boot-starter-* ： *就是指某种场景
2、只要引入starter，这个场景的所有常规需要的依赖我们都自动引入
3、SpringBoot所有支持的场景有：https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter
4、见到的  *-spring-boot-starter： 第三方为我们提供的简化开发的场景启动器。



# 自动配置

### 自动配置特性

1. 自动配好Tomcat： 引入Tomcat依赖、配置Tomcat

   ```xml
   <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-tomcat</artifactId>
         <version>2.3.4.RELEASE</version>
         <scope>compile</scope>
   </dependency>
   ```

2. 自动配好SpringMVC：引入SpringMVC全套组件、自动配好SpringMVC常用组件（功能）；

3. 自动配好Web常见功能，如：字符编码问题， SpringBoot帮我们配置好了所有web开发的常见场景；

4. 默认的包结构：

   - 主程序所在包及其下面的所有子包里面的组件都会被默认扫描进来，无需以前的包扫描配置；

   - 可以使用`@SpringBootApplication(scanBasePackages="com.lsl")`或者`@ComponentScan `改变扫描路径；

     ```java
     @SpringBootApplication 等同于下面三个注解的功能集合体
     @SpringBootConfiguration // 配置类
     @EnableAutoConfiguration // 自动配置
     @ComponentScan // 包扫描，默认扫描当前包和子包，使spring注解生效
     ```

5. 各种配置拥有默认值：默认配置最终都是映射到某个类上，如：MultipartProperties；配置文件的值最终会绑定每个类上，这个类会在容器中创建对象；

6. 可以按需加载所有自动配置项：

7.  非常多的starter：

   - 引入了哪些场景，哪些场景的自动配置就会开启；
   - SpringBoot所有的自动配置功能都在 spring-boot-autoconfigure 包里面。

### 源码解析

1.主配置类的@SpringBootApplication：

```java
@SpringBootConfiguration // 代表配置类
@EnableAutoConfiguration // 自动配置
@ComponentScan(         // 包扫描，默认扫描当前包和子包，使spring注解生效
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {}
```

2.@EnableAutoConfiguration：

```java
// EnableAutoConfiguration
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {}
// AutoConfigurationPackage 自动配置包，利用Registrar导入主配置类的包及自包下所有的组件
@Import({Registrar.class})
public @interface AutoConfigurationPackage {}
// Registrar
static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {
        Registrar() {
        }
		// (new AutoConfigurationPackages.PackageImports(metadata)).getPackageNames() 返回主类所在包名
        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            AutoConfigurationPackages.register(registry, (String[])(new AutoConfigurationPackages.PackageImports(metadata)).getPackageNames().toArray(new String[0])); 
            // 得到主类包名然后封装进数组，进行注册
        }
```

 @AutoConfigurationPackage ：自动配置包，利用Registrar导入主配置类的包及自包下所有的组件；

@Import({AutoConfigurationImportSelector.class})：

```java
// 给容器批量导入组件
public String[] selectImports(AnnotationMetadata annotationMetadata) {
    if (!this.isEnabled(annotationMetadata)) {
        return NO_IMPORTS;
    } else {
        AutoConfigurationImportSelector.AutoConfigurationEntry autoConfigurationEntry = this.getAutoConfigurationEntry(annotationMetadata);
        return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
    }
}

// 通过 List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes); 获取要导入到
// 容器中的配置类
```

![](img/config.png)

该文件写死了springboot启动就默认加载的所有配置类，最终是按需配置（按照条件装配规则）。

3.按需开启自动配置

自动配置流程：

- 先加载所有的自动配置类：xxxAutoConfigConfiguration；
- 每个自动配置类按照条件进行生效，默认都会绑定配置文件指定的值。xxxxProperties里面拿。xxxProperties和配置文件进行了绑定；
- 生效的配置类就会给容器中装配很多组件，只要容器中有这些组件，相当于这些功能就有了；
- 定制化配置
  - 用户直接自己@Bean替换底层的组件；
  - 用户去看这个组件是获取的配置文件什么值就去修改。

xxxxxAutoConfiguration ---> 组件  ---> xxxxProperties里面拿值  ----> application.properties



# 注解

## 组件注解

spring的注解于bean的创建。@Configuration、@Bean、@Component、@Controller、@Service、@Repository、@ComponentScan、@Import、@Conditional

### @Configuration

`@Configuration(proxyBeanMethods = true)`：注册组件，最佳实践：如果是不被依赖的使用false。

### @Import

`@Import(xxx.class, xxx.class, ...)`：注册组件，默认注册组件的名默认为全类名。

### @Conditional

条件装配，用于满足指定条件时进行组件注入。有许多拓展注解。

### @ImporeResource

`@ImporeResource("classpath:beans.xml")`：允许导入通过传统的bean.xml文件来创建的组件。

## 配置绑定注解

1.@ConfigurationProperties + @Component

@ConfigurationProperties(prefix="xxx")将注册进容器的组件的属性绑定，和配置文件**application.properties**的带有前缀的绑定（xxx.属性名）。

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix="cat")
public class Cat {
    private String name;
    private String age;
}
```

2.@EnableConfigurationProperties(xxx.class) + @ConfigurationProperties 

用于在1的方式中不能使用@Conponet的情况下，配置类上加入@EnableConfigurationProperties(xxx.class)，实现配置绑定功能和组件自动注册进容器。

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix="cat")
public class Cat {
    private String name;
    private String age;
}
```

```java
@Configuration(proxyBeanMethods = false)
// 开启Cat.class的配置绑定功能
// 并把Car这个组件自动注册到容器 注册进去的组件名格式为：cat-com.lsl.pojo.Cat （类名小写-q
@EnableConfigurationProperties(Cat.class)
public class Config {

}
```



# 配置文件-yml

YAML 是 "YAML Ain't Markup Language"（意为 YAML 不是一种标记语言）的递归缩写。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）。 非常适合用来做以数据为中心的配置文件。

基本语法
● `key: value`：kv之间有空格；
● 大小写敏感；
● 使用缩进表示层级关系；
● 缩进不允许使用tab，只允许空格；
● 缩进的空格数不重要，只要相同层级的元素左对齐即可；
● `#`表示注释；
● 字符串无需加引号，如果要加，' '与" "表示字符串内容会被 转义/不转义(例如转义字符"\n"原本就是表示换行的转义字符，单引号时会被再次转义，双引号时不被转义（此时就是原来的换行）)

数据类型：

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

```yaml
# yaml表示对象
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

配置处理器：

```xml
<!-- 配置处理器的依赖，使用yml配置时提示功能 -->       
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

# 最佳实践

1. 根据所需引入——场景starter和其他依赖：引入场景的artifactId见【https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter】。
2. 可以查看自动配置（XxxAutoConfigure）做了哪些功能（选做）：
   - 方法一：自己分析自动配置类（引入场景对应的自动配置一般都生效了）；
   - 方法二：配置文件中debug=true开启自动配置报告（Negative（不生效）\ Positive（生效））。
3. 是否需要定制或修改一些功能：
   - 参照文档修改配置文件 [Common Application Properties (spring.io)](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties)；
   - 自己分析xxxxProperties绑定了哪些可以在配置文件中配置的。
4. 自定义加入或者替换组件：
   - @Bean、@Component等；
   - 自定义器  **XXXXXCustomizer**。

开发技巧：

idea中搜索安装lombok插件，并在springboot项目中引入Lombok插件来简化JavaBean，使用@Data注解生成getset方法，@ToString生成tostring方法，@NoArgsConstructor，@AllArgsConstructor，生成无参有参构造器，@EqualsAndHashCode重写equals和hashcode方法，@Slf4j日志记录器：

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

```xml
<!-- 项目打包时排除lombok的jar包 -->
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <excludes>
            <exclude>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </exclude>
        </excludes>
    </configuration>
</plugin>
```

dev-tools：ctrl + f9 重启

```xml
 <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
```

为了能够在配置文件中有提示，processor：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

```xml
<!-- 项目打包时排除processor的jar包 -->
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
```

SpringInitializr：项目初始化向导，选择场景快速创建spring boot项目。

# web开发场景

## 静态资源

springboot默认会在classpath路径下的**默认静态资源路径** ` /static (or /public or /resources or /META-INF/resources)`里寻找对应的静态资源文件，当访问某些资源时，映射路径不经controller处理就默认来静态资源路径下寻找。

原理：`静态映射/**`

请求进来先寻找controller是否能进行处理，不能处理的所有请求就交给了静态资源处理器来处理，如果找不到资源就404错误。      

```yaml
spring:
  mvc:
    # 改变静态资源的映射前缀，默认没有前缀 "localhost:port/res/xxx"
    static-path-pattern: /res/**
  web:
    # 修改默认静态资源路径，修改后原默认静态资源路径失效
    resources:
      static-locations: [classpath:/newstatic/,classpath:/newtemplates/]
      #static-locations: classpath:/newstatic/
```

还支持webjar，会自动映射 /webjars/**，见https://www.webjars.org/

```xml
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>jquery</artifactId>
        <version>3.5.1</version>
    </dependency>
```
访问地址：http://localhost:8080/webjars/jquery/3.5.1/jquery.js   后面地址要按照依赖里面的包路径。

静态资源路径下的index.html可以作为欢迎页面，访问会自动跳转指这个页面，但是是在没有配置静态资源前缀的前提下。

静态资源路径下的favicon.ico图标可以作为页面标签图，当没有配置静态资源路径前缀的时候才有效。

## 请求处理

### Rest风格

以前使用（/getUser   获取用户     /deleteUser 删除用户    /editUser  修改用户       /saveUser 保存用户）为映射名称来表示对资源的操作。

现在使用Rest风格：Rest风格是使用HTTP请求方式动词来表示对资源的操作（/user    GET-获取用户    DELETE-删除用户     PUT-修改用户  POST-保存用户），表单需要提交隐藏参数。使用Rest风格的表单和RequestMapping映射如下设置：

```java
public class RestController {
    @GetMapping(value = "/user")
//    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String get() {
        return "get获取用户";
    }
    @DeleteMapping("/user")
//    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String delete() {
        return "delete删除用户";
    }
    @PutMapping(value = "/user")
//    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String modify() {
        return "put修改用户";
    }
    @PostMapping("/user")
//    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String save() {
        return "post保存用户";
    }
}
```

```html
<form action="/user" method="get">
    <input name="_method" type="hidden" value="GET"/>
    <input type="submit" value="获取">
</form>
<form action="/user" method="post">
    <input name="_method" type="hidden" value="PUT"/>
    <input type="submit" value="修改">
</form>
<form action="/user" method="post">
    <input name="_method" type="hidden" value="DELETE"/>
    <input type="submit" value="删除">
</form>
<form action="/user" method="post">
    <input name="_method" type="hidden" value="POST"/>
    <input type="submit" value="保存">
</form>
```

【注意】默认不开启Rest风格，需要手动开启，兼容的PUT、DELETE、PATCH的在表单必须是post请求，不影响表单原生get、post请求：

```yaml
spring:
  mvc:
    hiddenmethod:
      filter:
        enabled: true # 选择性开启表单rest风格
```

定制`_method`：创建配置类往容器注册HiddenHttpMethodFilter组件

```java
@Configuration(proxyBeanMethods = false)
public class WebConfig {
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
        HiddenHttpMethodFilter methodFilter = new HiddenHttpMethodFilter();
        methodFilter.setMethodParam("_m");
        return methodFilter;
    }
}
```

关于rest风格的原理，见该类：

```java
  @Bean
  @ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
  @ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled", matchIfMissing = false)
  public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
    return new OrderedHiddenHttpMethodFilter();
  }
```

请求映射原理：

所有的请求映射都在HanderMapping中...

### 普通参数注解

- @PathVariable：获取路径变量，可以指定key来获取某一个，也可以直接获取全部变量值；
- @RequestParam：获取请求参数；
- @CookieValue：获取cookie；
- @RequestBody：获取请求体，post请求才有；
- @RequestHeader：获取请求头：

![](img/requestheader.png)



```html
<body>
<a href="/act/3/lsl?age=18&inters=coding&inters=read">/act/3/lsl</a>
<form action="/save" method="POST">
    <input type="text" name="text">
    <input type="submit" value="提交">
</form>
</body>
```

```java
@Controller
public class RestController {
    @GetMapping(value = "/act/{id}/{name}")
    @ResponseBody
    public Map<String, Object> test(@PathVariable("name") String name,
                                    @PathVariable("id") Integer id,
                                    @PathVariable Map<String,String> kv,
                                    @RequestHeader("user-agent") String useragent,
                                    @RequestHeader Map<String,String> rh,
                                    @RequestParam("age", required = false) String age,
                                    @RequestParam("inters") List<String> inters,
                                    @RequestParam Map<String,String> params,
                                    @CookieValue("Webstorm-b05eb23e") String ck,
                                    @CookieValue("Webstorm-b05eb23e") Cookie cookie) {
        Map<String, Object> map = new HashMap<>();
        // @PathVariable 获取路径变量
        map.put("id",id); 
        map.put("name",name);
        map.put("kv",kv); 
        // @RequestHeader 获取请求头
        map.put("useragent", useragent);
        map.put("rh",rh);
        // @@RequestParam 获取请求参数，, required = false表示不是必须的
        map.put("age",age);
        map.put("inters",inters);
        map.put("params",params);
        // @CookieValue 获取cookie
        map.put("ck", ck);
        map.put("cookie",cookie);
        return map;
    }
    @PostMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> test(@RequestBody String body) {
        Map<String, Object> map = new HashMap<>();
        // @RequestBody 获取请求体
        map.put("requestBody",body);
        return map;
    }
}
```

@RequestAttribute：获取统一请求中请求欲的值

```java
@Controller
public class Common {
    @GetMapping(value = "/goto")
    public String forward(HttpServletRequest request){
        request.setAttribute("args","转发成功了...");
        request.setAttribute("info","这是在同一请求域中");
        return "forward:/page";
    }
    @GetMapping(value = "/page")
    @ResponseBody
    public Map<String, Object> page(@RequestAttribute("args") String args, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String info = (String) request.getAttribute("info");
        map.put("args",args);
        map.put("info",info);
        return map;
    }
}
```

@MatrixVariable：获取矩阵变量（springboot默认不开启矩阵变量功能）

- 在请求路径后面携带信息：`/act/3/lsl?age=18&inters=coding&inters=read`；
- 通过矩阵变量携带信息：`/index/{id;name;age;inters}`。

开启矩阵变量功能两个方法：一是继承WebMvcConfigurer并重写configurePathMatch方法；二是向容器中注册重写了configurePathMatch方法的组件WebMvcConfigurer。

```java
@Configuration(proxyBeanMethods = false)
public class WebConfig implements WebMvcConfigurer{
    // 组件
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
				// 不移除分号后面内容
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }
        };
    }
    // 实现WebMvcConfigurer并重写
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
```

@MatrixVariable的使用方式如下：

```html
<!-- 第一个分号前表示路径 -->
<a href="/index/path;name=lsl;age=21;inters=read">@MatrixVariable：获取矩阵变量</a>
<a href="/index/path;name=lsl;age=21;inters=code,read,learn">@MatrixVariable：获取矩阵变量</a>
<a href="/index/path1;age=18/path2;age=21">@MatrixVariable：获取矩阵变量</a>
```

```java
// 映射路径要使用路径变量的方式
@ResponseBody
@GetMapping(value = "/index/{path}")
public Map<String, Object> test(@MatrixVariable("age") Integer age,
                                 @MatrixVariable("inters") List<String> inters) {
    Map<String, Object> map = new HashMap<>();
    map.put("age",age);
    map.put("inters", inters);
    return map;
}
@ResponseBody
@GetMapping(value = "/index/{path1}/{path2}")
public Map<String, Object> test(@MatrixVariable(value = "age", pathVar = "path1") Integer age1,
                                @MatrixVariable(value = "age", pathVar = "path2") Integer age2) {
    Map<String, Object> map = new HashMap<>();
    map.put("age1",age1);
    map.put("age2",age2);
    return map;
}
```

@ModelAttribute：获取

### 复杂参数

**Map**、**Model（map、model里面的数据会被放在request的请求域（跳转前放入）  相当于request.setAttribute）**、**RedirectAttributes（ 重定向携带数据）**、**ServletResponse（response）**、Errors/BindingResult、SessionStatus、UriComponentsBuilder、ServletUriComponentsBuilder。

### 自定义对象参数

数据绑定：请求处理方法形参是对象时，表单提交的数据会自动和对象属性进行绑定。

- WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name)；WebDataBinder ：web数据绑定器，将请求参数的值绑定到指定的JavaBean里面；
- WebDataBinder 利用它里面的 Converters 将请求数据转成指定的数据类型。再次封装到JavaBean中；
- GenericConversionService：在设置每一个值的时候，找它里面的所有converter那个可以将这个数据类型（request带来参数的字符串）转换到指定的类型（JavaBean -- Integer）
  byte -- > file

```java
// 自定义数据绑定：这里自定义绑定value值`name,age`封装成cat对象
@Bean
public WebMvcConfigurer webMvcConfigurer(){
    return new WebMvcConfigurer(){
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new Converter<String, Cat>() {
                @Override
                public Cat convert(String source) {
                    if (!StringUtils.isEmpty(source)) {
                        Cat cat = new Cat();
                        String[] split = source.split(",");
                        cat.setName(split[0]);
                        cat.setAge(Integer.parseInt(split[1]));
                        return cat;
                    }
                    return null;
                }
            });
        }
    };
}
```

## 响应处理

响应分为响应页面和响应数据。

响应数据===》内容协商功能：根据客户端接收能力不同，返回不同媒体类型的数据。

springboot内容协商的实现-springboot底层已经实现好了，当支持：

json支持xml的模块依赖（springboot底层利用MessageConverters会实现内容协商，根据请求头来决定发送json数据或者xml等数据，前提是springboot项目里支持各种数据）：

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```



为了方便内容协商，开启基于请求参数的内容协商功能：

```yaml
spring:
    contentnegotiation:
      favor-parameter: true # 开启请求参数的内容协商模式
```

开启后在请求路径中携带format=json来指定响应返回的数据：

- http://localhost:8888/test?format=json；
- [localhost:8888/test?format=xml&xxxx](http://localhost:8888/test?format=xml&xxxx)。

如果想要返回其他的类型，比如pdf等，就可以通过自定义协商管理器来实现，实现多协议数据兼容，步骤：

1. 添加自定义的MessageConverters进系统底层，系统底层会统计出所有的MessageConverters可以操作哪些数据类型；
2. 客户端内容协商。

自定义MessageConverters：

```java
public class MyMessageConverter implements HttpMessageConverter<Cat> {
    @Override
    //
    public boolean canRead(Class clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(Cat.class);
    }
    // 获取所有的内容协商的类型
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        // 设置请求头媒体类型
        return MediaType.parseMediaTypes("application/x-lsl");
    }

    @Override
    public Cat read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Cat cat, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // 自定义协议数据写出格式
        String data = cat.getName() + "//" + cat.getAge();
        // 写出
        OutputStream body = outputMessage.getBody();
        body.write(data.getBytes());
    }
}
```

注册进容器：

```java
@Configuration(proxyBeanMethods = false)
public class WebConfig implements WebMvcConfigurer{
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MyMessageConverter());
            }
        };
    }
}
```

如何通过路径参数的方式来进行自定义类型的内容协商？

```java
@Configuration(proxyBeanMethods = false)
public class WebConfig implements WebMvcConfigurer{
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){

            @Override
            // 自定义参数模式内容协商策略
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                // Map<String, MediaType> mediaTypes
                Map<String, MediaType> mediaTypes = new HashMap<>();
                // 指定支持哪些参数对应的哪些类型
                mediaTypes.put("json",MediaType.APPLICATION_JSON);
                mediaTypes.put("xml",MediaType.APPLICATION_XML);
                mediaTypes.put("ll",MediaType.parseMediaType("application/x-lsl"));
                ParameterContentNegotiationStrategy parameterStrategy = new ParameterContentNegotiationStrategy(mediaTypes);
                // 可以指定参数的key 默认format
                parameterStrategy.setParameterName("for");
                configurer.strategies(Arrays.asList(parameterStrategy));
            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MyMessageConverter());
            }
        };
    }
}
```



通过：[localhost:8888/test?format=ll](http://localhost:8888/test?format=ll)   访问返回自定义类型成功。



## 拦截器

自定义拦截器：访问路径拦截

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

自定义拦截器的注册和配置：

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

## 文件上传

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

```yaml
spring:
  servlet:
    multipart:
      # 默认限制上传单个文件不超过1MB，总请求不超过10MB
      max-file-size: 10MB
      max-request-size: 100MB
```

## 错误页面

默认情况下，Spring Boot提供/error处理所有错误的映射，对于机器客户端，它将生成JSON响应，其中包含错误，HTTP状态和异常消息的详细信息。对于浏览器客户端，响应一个“ whitelabel”错误视图，以HTML格式呈现相同的数据。

静态资源路径下的`error/`路径下的4xx.html、5xx.html会自动被解析。

定制错误处理逻辑：

1. 静态资源路径下自定义错误页`error/404.html` 、`error/5xx.html`（有精确的错误状态码页面就匹配精确，没有就找 4xx.html；如果都没有就触发白页）；
2. @ControllerAdvice+@ExceptionHandler处理全局异常（底层是 ExceptionHandlerExceptionResolver 支持的；常用的方式）；
3. @ResponseStatus+自定义异常 ：
   - 底层是 ResponseStatusExceptionResolver ，把responsestatus注解的信息底层调用 response.sendError(statusCode, resolvedReason)；tomcat发送的/error。

自定义异常解析器：

```java
@Order(value = Ordered.HIGHEST_PRECEDENCE) // 越小优先级越高
@Component
public class MyExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            response.sendError(511,"511错误");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView();
    }
}
```

## 原生组件注入

Servlet、Filter、Listener



# 数据

## jdbc场景

jdbc场景导入和数据库驱动导入：

```xml
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
<!-- 修改版本号的方法2 -->
<properties>
    <java.version>1.8</java.version>
    <mysql.version>5.1.46</mysql.version>
</properties>
```

jdbc场景导入内容：HikariCP（数据库连接池）、spring-jdbc。官方不知道要使用哪些数据库，所以数据库驱动没有安装进去，但数据库驱动的版本仲裁还是存在的。

jdbc场景的自动配置：

1. DataSourceAutoConfiguration ： 数据源的自动配置：
   - 修改数据源相关的配置：**spring.datasource****；
   - 数据库连接池的配置，是当自己容器中没有DataSource才自动配置的；
   - **底层配置好的连接池是：**HikariDataSource**。
2. DataSourceTransactionManagerAutoConfiguration： 事务管理器的自动配置；

3. JdbcTemplateAutoConfiguration： springboot自带的**JdbcTemplate**，其自动配置，可以来对数据库进行crud：
   - 可以修改这个配置项@ConfigurationProperties(prefix = "spring.jdbc") 来修改JdbcTemplate；
   -  @Bean @Primary    JdbcTemplate；容器中有这个组件。
4. JndiDataSourceAutoConfiguration： jndi的自动配置；

5. XADataSourceAutoConfiguration： 分布式事务相关的。

修改配置项：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mysqltest?useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: 123456
    type: # 数据库连接池 默认的是com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.jdbc.Driver
```

## 连接池与操作

配置数据库连接池，以druid（[alibaba/druid: 阿里云计算平台DataWorks(https://help.aliyun.com/document_detail/137663.html) 团队出品，为监控而生的数据库连接池 (github.com)](https://github.com/alibaba/druid)）为例：

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.8</version>
</dependency>
```

```java
@Component
public class MyDataSources {
    // 将数据源装配进容器 自动配置默认先判断容器有没有数据源，如果没有就使用默认的数据源
    // 数据绑定配置项
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        DruidDataSource duridDataSource = new DruidDataSource();
        return duridDataSource;
    }
}
```

连接池starter引入方式：

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.17</version>
</dependency>
```

druid配置：[github.com](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)



使用JdbcTemplate对数据库数据进行操作：

 JdbcTemplate是Spring对JDBC的封装，目的是使JDBC更加易于使用。JdbcTemplate是Spring的一部分。JdbcTemplate处理了资源的建立和释放。他帮助我们避免一些常见的错误，比如忘了总要关闭连接。他运行核心的JDBC工作流，如Statement的建立和执行，而我们只需要提供SQL语句和提取结果。在JdbcTemplate中执行SQL语句的方法大致分为3类：

1. execute：可以执行所有SQL语句，一般用于执行DDL语句；
2. update：用于执行insert、update、delete等DML语句；
3. queryXxx：用于DQL数据查询语句。

```java
@Slf4j
@SpringBootTest
class SpringbootFileApplicationTests {

    @Autowired
    // 使用JdbcTemplate来进行SQL操作
    JdbcTemplate template;

    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() {
        log.info("数据源类型：{}",dataSource.getClass());
        List<Map<String, Object>> maps = template.queryForList("select  * from info");
        for (int i = 0; i < maps.size(); i++) {
            System.out.println(maps.get(i));
        }
    }
}
```

## 整合mybatis

参考官方：[GitHub - mybatis/spring-boot-starter: MyBatis integration with Spring Boot](https://github.com/mybatis/spring-boot-starter)

1、依赖：

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.0</version>
</dependency>
```

2、配置

```java
// Dao层
@Mapper
public interface InfoMapper {
    public Student getStudent(Long id);
}
```

SQL映射文件，namespace绑定xxxMapper接口，SQL语句id要和对应接口方法的名字一致：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lsl.dao.InfoMapper">
    <select id="getStudent" resultType="com.lsl.pojo.Student">
        select name, age, school, nowadays from info where id=#{id}
    </select>
</mapper>
```

mybatis-config全局配置文件：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 开启驼峰命名 表中字段 t_user -》 t-->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```

这个config-location: classpath:mybatis/mybatis-config.xml和configuration不能共存，要么使用mybatis-config配置，要么使用configuration（建议：使用configuration来进行mybatis全局配置）：

```yaml
mybatis:
  # 引入mybatis全局配置文件
  # config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations: classpath:mybatis/mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true
```

service层：

```java
@Service
public class StudentService {
    @Autowired
    InfoMapper infoMapper;
    public Student getStudentById(Long id) {
        return infoMapper.getStudent(id);
    }
}
```

使用：

```java
@Controller
public class TController {
//    @Autowired
//    JdbcTemplate jdbcTemplate;
    @Autowired
    StudentService studentService;

    @GetMapping(value = "get")
    @ResponseBody
    public Student getById(@RequestParam("id") Long id) {
        return studentService.getStudentById(id);
    }
}
```

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

注解模式Mybatis：

```java
@Mapper
public interface AnnoInfoMapper {
    @Select(value = "select name,age,school,nowadays from info where id=#{id}")
    public Student getByIds(Long id);
}
```

```xml
<!-- useGeneratedKeys="true" keyProperty="id" 作用在于插入完成后返回数据库中自增的id 会放进 -->
<insert id="addStudent" useGeneratedKeys="true" keyProperty="id">
    insert into info(name,age,school) values (#{name},#{age},#{school})
</insert>
<!-- 注解方式 -->
@Insert(value = "insert into info(name,age,school) values (#{name},#{age},#{school})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public boolean addStudent(Student student);
<!-- 最后返回的student的id属性值不再是null -->
@PostMapping(value = "/add")
    @ResponseBody
    public Student addStudent(Student student) {
        studentService.addStudent(student);
        return student;
    }
```

混合模式：注解与映射文件相结合（简单的方法使用注解，复杂的方法使用映射文件）



最佳实战：
● 引入mybatis-starter；
● 在application.yaml中配置，指定mapper-location位置即可；
● 编写Mapper接口并标注@Mapper注解；
● 简单方法直接注解方式；
● 复杂方法编写mapper.xml进行绑定映射；
● 在主配置类使用@MapperScan("com.atguigu.admin.mapper") 简化，其他的接口就可以不用标注@Mapper注解。

## 整合MybatisPlus

MyBatis-Plus（简称 MP）是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。
[简介 | MyBatis-Plus (baomidou.com)](https://baomidou.com/guide/)，建议安装 MybatisX 插件 。

```xml
<!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.3.4</version>
</dependency>
```

## 整合Redis

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

```yaml
spring:
  redis:
    host: 192.168.137.129
    port: 6379
```

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

使用：

```java
@SpringBootTest
@ComponentScan("com.lsl")
class SpringbootFileApplicationTests {
    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;
    @Test
    void testRedis() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("hello","hello world");
    }
}
```

切换使用jedis：

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```

```yaml
spring:
    redis:
      host: 192.168.137.129
      port: 6379
      client-type: jedis
```

# JUnit5

官方文档：[JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

# 指标监控



# 高级特性













































