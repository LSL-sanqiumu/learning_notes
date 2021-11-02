# JSON

## 概念

JSON(JavaScript Object Notation, JS 对象简谱) 是一种轻量级的数据交换格式。它基于 ECMAScript (欧洲计算机协会制定的js规范)的一个子集，采用完全独立于编程语言的文本格式来存储和表示数据。简洁和清晰的层次结构使得 JSON 成为理想的数据交换语言。 易于人阅读和编写，同时也易于机器解析和生成，并有效地提升网络传输效率。

## JSON格式

在 JavaScript 是一门面向对象的语言，JavaScript 支持的类型都可以通过 JSON 来表示，例如字符串、数字、对象、数组等。JSON 是 JavaScript 对象的字符串表示法，它使用文本表示一个 JS 对象的信息，本质是一个字符串-特定格式的字符串。

JSON语法格式：

- 花括号用来包裹对象，对象则表示为键值对，数据由逗号分隔；
- 方括号保存数组。

```js
var obj = {a: 'Hello', b: 'World'}; //这是一个JavaScript对象，注意键名也是可以使用引号包裹的
var json = '{"a": "Hello", "b": "World"}'; //这是一个 JSON 字符串，本质是一个字符串
```

## 对象转换

JSON字符串转换为JavaScript 对象，使用 JSON.parse() 方法：

```js
var obj = JSON.parse('{"a": "Hello", "b": "World"}');
//返回结果是 {a: 'Hello', b: 'World'} 对象
```

要实现从JavaScript 对象转换为JSON字符串，使用 JSON.stringify() 方法：

```js
var json = JSON.stringify({a: 'Hello', b: 'World'});
//结果是 '{"a": "Hello", "b": "World"}' 字符串
```

## JSON解析工具

Jackson应该是目前比较好的json解析工具之一，其他的还有阿里巴巴的 fastjson 等。fastjson.jar是阿里开发的一款专门用于Java开发的包，可以方便的实现json对象与JavaBean对象的转换，实现JavaBean对象与json字符串的转换，实现json对象与json字符串的转换。实现json的转换方法很多，最后的实现结果都是一样的。

导入Jackson的依赖：

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>
</dependency>
```

fastjson 的依赖：

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.78</version>
</dependency>
```

fastjson 三个主要的类：

- JSONObject：代表 json 对象，JSONObject实现了Map接口, 猜想 JSONObject底层操作是由Map实现的；

- JSONObject：对应json对象，通过各种形式的get()方法可以获取json对象中的数据，也可利用诸如size()，isEmpty()等方法获取"键：值"对的个数和判断是否为空。其本质是通过实现Map接口并调用接口中的方法完成的；

- JSONArray：代表 json 对象数组，内部是有List接口中的方法来完成操作的。


JSON代表：JSONObject和JSONArray的转化：

- JSON类源码分析与使用
- 仔细观察这些方法，主要是实现json对象，json对象数组，javabean对象，json字符串之间的相互转化。

这种工具类，我们只需要掌握使用就好了，在使用的时候在根据具体的业务去找对应的实现。和以前的commons-io那种工具包一样，拿来用就好了！

## webapplication中使用json

web项目使用的到spring、servlet、springmvc、mybatis等配置好。

### 导入依赖：

```xml
	<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.12.4</version>
    </dependency>
	<!-- lombok是？ -->
    <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.20</version>
      <scope>provided</scope>
    </dependency>
```

### pojo使用到lombok：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private int age;
    private String sex;
}
```

### controller使用到@ResponseBody：

​    @ResponseBody用来干嘛的？使该方法返回的只是json格式的字符串。

```java
@Controller
public class JsonTest {

    @RequestMapping(value = "/json1", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String json1() throws JsonProcessingException {
        //创建一个jackson的对象映射器，用来解析数据
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user = new User("梁胜林", 3, "男");
        //将我们的对象解析成为json格式
        String str = mapper.writeValueAsString(user);
        //由于@ResponseBody注解，这里会将str转成json格式返回；十分方便
        return str;
    }
```

### 解决乱码：

1. @RequestMapping(value = "/json1", produces = "application/json;charset=utf-8")；

2. springmvc.xml中统一解决，不需要再为所有的request添加那样的设置：

   ```xml
   <mvc:annotation-driven>
       <mvc:message-converters register-defaults="true">
           <bean class="org.springframework.http.converter.StringHttpMessageConverter">
               <constructor-arg value="UTF-8"/>
           </bean>
           <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
               <property name="objectMapper">
                   <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                       <property name="failOnEmptyBeans" value="false"/>
                   </bean>
               </property>
           </bean>
       </mvc:message-converters>
   </mvc:annotation-driven>
   
   BEANS:
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
   ```

### 解决同一返回JSON字符串：

在类上直接使用 **@RestController** （不使用@Controller），这样子，里面所有的方法都只会返回 json 字符串，不用再为每一个方法都添加@ResponseBody ！我们在前后端分离开发中，一般都使用 @RestController ，十分便捷！

### 封装工具类：

```java
public class JsonUtils {

	public static String getJson(Object object) {
		return getJson(object,"yyyy-MM-dd HH:mm:ss");
	}

	public static String getJson(Object object,String dateFormat) {
		ObjectMapper mapper = new ObjectMapper();
		//不使用时间差的方式
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		//自定义日期格式对象
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		//指定日期格式
		mapper.setDateFormat(sdf);
		try {
		return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
		e.printStackTrace();
		}
		return null;
	}
}
```

## JSON与HTTP



## jaskson库

