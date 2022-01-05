# 概述

微服务架构是一种架构模式，它提倡将单一的应用程序划分为一组小的服务-也就是微服务，服务之间互相协调、互相配合，为用户提供最终价值。每个微服务运行在其独立的进程中，服务与服务间采用轻量级的通信机制互相协作（通常是基于HTTP协议的RESTfulAPI）。每个服务都围绕着具体的业务进行构建，并且能够被独立地部署到生产环境、类生产环境等。另外，为了避免统一的、集中式的服务管理机制，对具体一个服务而言，应该根据业务上下文，选择合适的语言、工具对其进行构建。

（提出者：Martin Fowler）

分布式的微服务架构体系：

![](img/1.微服务架构.png)

SpringCloud：分布式微服务架构的一站式解决方案，是多种微服务架构落地技术的集合体，俗称微服务全家桶。

SpringCloud与SpringBoot版本问题：官网查看对应版本：[Spring Cloud](https://spring.io/projects/spring-cloud#overview)。

本次学习需要的各开发环境版本选型：

![](img/2.版本选型.png)

组件停更替换：

![](img/3.组件停更替换.png)

# 父工程构建

## 1.创建父工程

![](img/4.父工程.png)

- （maven版本3.5以上）使用该maven骨架创建maven项目。
- 父工程名字：learning_springcloud；创建好后删除src目录。
- 项目字符编码：UTF-8。
- Java版本：Java8。

注解生效激活，如下：

![](img/5.注解生效激活.png)

## 2.父工程pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.lsl.springcloud</groupId>
  <artifactId>learning_springcloud</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>pom</packaging>
  <!-- 统一管理jar包版本 -->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <junit.version>4.12</junit.version>
    <log4j.version>1.2.17</log4j.version>
    <lombok.version>1.16.18</lombok.version>
    <mysql.version>5.1.47</mysql.version>
    <druid.version>1.2.8</druid.version>
    <mybatis.spring.boot.version>2.2.0</mybatis.spring.boot.version>
  </properties>
  <dependencyManagement>
    <dependencies>
      <!-- springboot 2.2.2 -->
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>2.2.2.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!-- spring-cloud Hoxton.SR1 -->
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>Hoxton.SR1</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!--      -->
      <!--spring cloud 阿里巴巴-->
      <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>2.1.0.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!-- mysql -->
      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
        <scope>runtime</scope>
      </dependency>
      <!-- druid -->
      <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>${druid.version}</version>
      </dependency>
      <!--mybatis-->
      <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${mybatis.spring.boot.version}</version>
      </dependency>
      <!--junit-->
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>${junit.version}</version>
      </dependency>
      <!--log4j-->
      <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>${log4j.version}</version>
      </dependency>
    </dependencies>
  </dependencyManagement>
  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <fork>true</fork>
          <addResources>true</addResources>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

# 支付模块

![](img/6.支付模块.png)

## 1.环境构建

1. 创建maven项目：cloud-provider-payment8001。
2. 依赖导入，创建com.lsl.springcloud包和主配置类。
3. 配置文件：application.yml。

application.yml：

```yml
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://localhost:3306/myspringcloud?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 1234

mybatis:
  mapperLocations: classpath:mapper/*.xml
  type-aliases-package: com.lsl.springcloud.entities
```

pom.xml：

```xml
<dependencies>
    <!--包含了sleuth+zipkin-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>
    <!--eureka-client-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
    <!--
    <dependency>
        <groupId>com.atguigu.springcloud</groupId>
        <artifactId>cloud-api-commons</artifactId>
        <version>${project.version}</version>
    </dependency>
    -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.2.8</version>
    </dependency>
    <!--mysql-connector-java-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!--jdbc-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

主配置类：

```java
@SpringBootApplication
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class,args);
    }
}
```

## 2.业务开发

### 1.建表

```sql
CREATE DATABASE `myspringcloud` CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `payment`(
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `serial` varchar(200) DEFAULT '',
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

### 2.构建实体类

com.lsl.springcloud.entities.Payment：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
```

JSON封装体CommonResult：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message){
        this(code, message, null);
    }
}
```

### 3.DAO接口

```java
@Mapper
public interface PaymentDao {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}
```

### 4.整合MyBatis

application.yml：（上面的环境构建已经配置了，这里仅作记录）

```yml
spring:  
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://localhost:3306/myspringcloud?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 123456

mybatis:
  mapperLocations: classpath:mapper/*.xml
  type-aliases-package: com.lsl.springcloud.entities
```

resources/mapper/PaymentMapper.xml：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.lsl.springcloud.dao.PaymentDao">
    <insert id="create" parameterType="Payment" useGeneratedKeys="true" keyProperty="id">
        insert into payment(serial)  values(#{serial});
    </insert>

    <resultMap id="BaseResultMap" type="com.lsl.springcloud.entities.Payment">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <id column="serial" property="serial" jdbcType="VARCHAR"/>
    </resultMap>

    <select id="getPaymentById" parameterType="Long" resultMap="BaseResultMap">
        select * from payment where id=#{id};
    </select>
</mapper>
```

com.lsl.springcloud.service：

```java
public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
```

```java
@Service
public class PayServiceImpl implements PaymentService{

    @Resource
    private PaymentDao paymentDao;
    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
```

com.lsl.springcloud.controller：

```java
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);

        if(result > 0)
        {
            return new CommonResult(200,"插入数据库成功,serverPort: ",result);
        }else{
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentById(id);

        if(payment != null)
        {
            return new CommonResult(200,"查询成功,serverPort:  ",payment);
        }else{
            return new CommonResult(444,"没有对应记录,查询ID: "+id,null);
        }
    }
}
```

### 5.测试

1.先往数据库表中插入一条数据，如何启动服务，访问[localhost:8001/payment/get/1](http://localhost:8001/payment/get/1)，能显示json数据则成功。

2.因为浏览器对post请求不友好，所以使用postman来发送post请求。

# 热部署

热部署在**开发时使用，生产环境则要关闭**。

## 1.Adding devtools to your project

支付模块添加以下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

## 2.Adding plugin to your pom.xml

下段配置复制到聚合父类总工程的pom.xml：

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
        <fork>true</fork>
        <addResources>true</addResources>
      </configuration>
    </plugin>
  </plugins>
</build>
```

## 3.Enabling automatic build

File -> Settings -> Build -> Complier 下面项勾选以下四个选项：

- Automatically show first error in editor。
- Display notification on build completion。
- Build project automatically。
- Compile independent modules in parallel。

## 4.Update the value of

键入`Ctrl + Shift + Alt + / `，打开Registry，找到并勾选以下两个选项：

- actionSystem.assertFocusAccessFromEdt。
- compiler.automake.allow.when.app.running。

## 5.重启idea

重启后启动支付项目，会发现一个error，如下：（不影响使用，忽略这个错误）

![](img/7.druid_error.png)

重启后，修改程序，会发现修改后间隔一定时间项目会自动重新启动。

# 消费者订单模块

1. 建Module
2. 加依赖
3. 写YML：配置一个80端口
4. 主启动
5. 写业务
6. 做测试

## 2.加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 3.写yml

```yml
server:
	port: 80
```



## 5.写业务

实体类和支付模块的两个一致。

使用RestTemplate，需要先将其注册到容器中，所以先建立配置类，com/lsl/springcloud/config：

```java
@Configuration
public class ApplicationContextConfig {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

![](img/8.restTemplate.png)

创建controller：

```java
@RestController
@Slf4j
public class OrderController {
    public static final String PAYMENT_URL = "http://localhost:8001";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){

        return restTemplate.postForObject(PAYMENT_URL+"/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id, CommonResult.class);
    }

}
```

## 6.测试

打开支付模块和消费者模块，然后：

- 访问：`http://localhost/consumer/payment/get/1`。返回json数据则成功。

访问：`http://localhost/consumer/payment/create?serial=222222`发现存储进数据库的值为null，这是因为支付模块的controller的create方法中参数接收处忘了加@RequestBody注解。

## Run Dashboard窗口

我使用的当前版本出现的是service窗口，不用再配置，如果没出现，重启就出现了。

通过修改idea的workspace.xml的方式来**快速打开Run Dashboard窗口**（这个用来显示哪些Spring Boot工程运行，停止等信息。）如果运行了多个微服务没有出现该窗口，那么就进行如下设置：

1. 选中父项目然后右键---> open in ---> Explorer，进入项目找到.idea目录。

2. 进入.idea目录找到workspace.xml文件进行设置：

   ```xml
     <component name="RunDashboard">
       <option name="configurationTypes">
         <set>
           <option value="SpringBootApplicationConfigurationType" />
         </set>
       </option>
       <option name="ruleStates">
         <list>
           <RuleState>
             <option name="name" value="ConfigurationTypeDashboardGroupingRule" />
           </RuleState>
           <RuleState>
             <option name="name" value="StatusDashboardGroupingRule" />
           </RuleState>
         </list>
       </option>
     </component>
   ```

3. 重启就好。

## RestTemplate

RestTemplate提供了多种便捷访问远程Http服务的方法，是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集

官网地址

使用：使用restTemplate访问restful接口非常的简单粗暴无脑。

- (url, requestMap, ResponseBean.class)这三个参数分别代表REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。

