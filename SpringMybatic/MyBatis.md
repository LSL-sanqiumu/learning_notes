```java
Connection conn = null;
PreparedStatement ps =null;
Result rs = null;
try{
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/jdbctest?"; 
    String name = "root";
    String password = "123456";
    conn = DriverManager.getConnection(url, name, password);
    String sql = "";
    ps = conn.preparedStatement(sql);
    rs = ps.executeQuery();
    while(rs.next()){
        String id = rs.getString("id");
        String name = rs.getString("name");
        String age = rs.getString("age");
        
        Student s = new Student();
        s.setId(id);
        s.setName(name);
        s.setAge(age);
    }
    
}catch (Exception){
    
}finally{
    if(rs != null){
        try{
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    if(ps != null){
        try{
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    if(conn != null){
        try{
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
```

# mybatis配置

依赖：

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.6</version>
</dependency>
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.25</version>
</dependency>
```

resources目录下的配置：mybatis-config.xml、jdbc.properties、BlogMapper.xml

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
                <property name="url" value="${jdbc.url}"/>
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



# 概述

MyBatis是什么？MyBatis用来干什么？

MyBatis和Hibernate是一个持久层框架，专门封装JDBC，用于简化JDBC编程；

框架：框架在表现形式上就是别人写好编译好的一堆字节码文件，通常打成jar包，使用框架把框架的jar包导入classpath当中就可以了；使用框架的目的：提高开发的效率，很多繁琐重复的程序已经被提前封装好，直接使用就行了；所有的java框架都是基于反射机制+XML配置一起完成的。

JDBC缺点：

- 重复代码多，会降低开发效率（rs.getXxx()取数据库数据并setXxx(xx)封装数据的时候，开发繁琐）；

  ```java
   while(rs.next()){
          String id = rs.getString("id");
          String name = rs.getString("name");
          String age = rs.getString("age");
          
          Student s = new Student();
          s.setId(id);
          s.setName(name);
          s.setAge(age);
      }
  //而mybatis框架中：封装了JDBC代码，使用反射机制帮我们自动创建对象并给属性赋值
  ```

- sql语句编写在java程序中，sql语句不支持配置，所以就会导致后面要修改语句的时候得重新修改源代码，而重新修改后又得重新编译/重新部署等，并且修改java源代码违反了开闭原则OCP。（互联网分布式架构方面的项目，并发量很大，系统需要不断的优化，各方面的优化，其中有一条非常重要的优化是SQL优化）。------SQL语句可以写到配置文件中，此条确定并不那么主要。

# mybatis的使用

## mybatis-config.xml

### 配置数据库

mybatis-config.xml：构建 SqlSessionFactory的配置文件，全局配置文件

配置文件头信息：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
   
</configuration>
```

配置文件内容：（两种方式：通过配置文件或直接配置）

```xml
<environment id="development">
     <transactionManager type="JDBC"/>
     <dataSource type="POOLED">
     <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
     <property name="url" value="jdbc:mysql://localhost:3306/jdbctest"/>
     <property name="username" value="root"/>
     <property name="password" value="123456"/>
     </dataSource>
</environment>
```

```xml
<!-- jdbc.properties文件的key也不能与前面的name一样 -->
<!-- 即dataSource的property的name 和value="${jdbc.driver}"的大括号里面的值不能一样 -->
<!-- 否则会报错，这是为啥？？？ -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC" />
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="BlogMapper.xml"/>
    </mappers>
</configuration>
```

使用到的jdbc.properties文件：

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/jdbctest?characterEncoding=utf8
username=root
password=123456
```

为什么要另外使用jdbc.properties文件配置driver等属性值？

${com.mysql.cj.jdbc.Driver}这样的表达式获取不到com.mysql.cj.jdbc.Driver，因为MapperScannerConigurer实际是在解析加载bean定义阶段的，这个时候要是设置sqlSessionFactory的话，会导致提前初始化一些类，这个时候，PropertyPlaceholderConfigurer还没来得及替换定义中的变量，导致把表达式当作字符串复制了，也就是本来加载com.mysql.cj.jdbc.Driver驱动的，变成了加载${com.mysql.cj.jdbc.Driver}驱动，找不到该驱动就出错了；解决方法就是使用properties配置文件，直接在全局配置文件mybatis-config.xml中加载属性值，就不需要在mybatis-config.xml里对数据库连接参数硬编码了。

### 其他设置

在Mybatis全局配置文件中可以为某个Java类起别名：(注意配置文件内各标签的顺序有要求)

```xml
<typeAliases>
        <typeAlias type="com.lsl.domain.Student" alias="Student"/>
</typeAliases>
```

```xml
<!--自动为某个包下所有的类起别名，别名默认为简类名-->
<typeAliases>
        <package name="com.lsl.domain"/>
</typeAliases>
```

全局配置mybatis-config.xml中可以单独指定SQL语句映射配置文件，也可以映射到某个包下全部的SQL语句映射配置文件：

```xml
<mappers>
	<mapper resource="BlogMapper.xml"/>
</mappers>
<!--映射到该包下所有的xml文件，使用该方式时对包下的mapper文件和文件的namespace属性有要求-->
<mappers>
	<package resource="com.lsl.blog.dao"/>
</mappers>
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lsl.dao.GetService"> <!-- 要指定接口，而且该文件名要和接口名完全一致 -->
    <select id="getAll" resultType="com.lsl.pojo.Student">
        select `name`,`age` from mysqltest.t_student
    </select>

</mapper>
```

## BlogMapper.xml

```xml
<!-- 定义SQL语句 -->
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

### SQL简单语句

查询语句：

```xml
<!--mybatis自动创建对象并把查询结果放到对象对应的属性上-->
<!--查询结果集的列名要和对象的属性名对应，不对应的时候使用as起别名-->
<select id="getAll" resultType="com.lsl.domain.Student">
    select id as sid,name as sname,birth as sbirth from t_student
</select>
```

插入语句：

```xml
<insert id="save" parameterType="com.lsl.domain.Student">
    insert into t_student
        (id, name, birth) values (#{sid}, #{sname}, #{sbirth})
</insert>
```

修改语句：

```xml
<update id="update" parameterType="com.lsl.domain.Student">
    update t_student set
        name=#{sname}, birth=#{sbirth}
    where id=#{sid}
</update>
```

删除语句：

```xml
<delete id="delete">
    delete from t_student where id=#{dasf}
</delete>
```

### SQL传值

当传入多个参数时，动态拼接SQL语句，在SQL映射文件里实现动态语句。

传入的参数是数组，使用foreach遍历值：

- collection：参数名称，根据Mapper接口的参数名确定，也可以使用@Param注解指定参数名；

- item：参数调用名称，通过此属性来获取集合单项的值；

- open：相当于prefix，即在循环前添加前缀；

- close：相当于suffix，即在循环后添加后缀；

- index：索引、下标、key。


```xml
<delete id="deleteByIds">
	delete from t-student where id in 
    	<!-- 在in后实现（值1，值2，值3，...） 值由传入的参数决定-->
    	<foreach collection="array" open="(" close=")" separator="," item="stuId">
    		#{stuId}
    	</foreach>
</delete>

<delete id="deleteByIds">
	delete from t-student where id in（ 
    	<!--stuId：循环array中的值-->
    	<foreach collection="array" separator="," item="stuId">
    		#{stuId}
    	</foreach>
    ）
</delete>
```

传入的参数类型是List集合时（就是把里面的值传入）：

```mysql
INSERT INTO `表`(`字段`,`字段`,`字段`,...) VALUES (数据1,数据2,数据3),(数据1,数据2,数据3),... 

```



where和if：什么是分页查询？为什么要有分页查询？

当数据量过大时，可能会导致各种各样的问题发生，例如：服务器资源被耗尽，因数据传输量过大而使处理超时，等等。最终都会导致查询无法完成。
解决这个问题的一个策略就是“分页查询”，也就是说不要一次性查询所有的数据，每次只查询一“页“的数据。这样分批次地进行处理，可以呈现出很好的用户体验，对服务器资源的消耗也不大。数据库管理系统将数据分区成固定大小的区块，称为“页”。

分页查询的SQL语句：

分页查询时浏览器会向服务器提交：pageNo（页码）、pageSize（每页显示的数据）、查询条件。

```mysql
select s.* from table_name s where ... order by table.xx asc/desc  limit (pageNo-1)*pageSize,pageSize;
```

分页查询Java代码：

浏览器提交的数据？查询条件（可能没有也可能有多个）、pageNo（页码）、pageSize（每页的记录条数）。

服务器响应的数据？

符合查询的“当前页”的数据、符合查询条件的“总记录”条数。

JSON：

```java
{
    "total" : 50,
    "dataList" :[{"id":"","name":"","birth":"",...}, {"id":"", "name":"", "birth":"", ...}, ...]
}
```

以上JSON对应的java对象，是一个Map集合：

```java
Map<String, Object>集合
    key			value
    total		50    (需要SQL语句，SQL1)
    dataList 	datalist  （需要SQL语句，SQL2）
```

SQL语句要编写多少？如何编写？

需要两条SQL语句。

```mysql
SQL1：
select count(*) from table s where ...动态条件
SQL2：
select s.* from table_name s  where ...动态条件 order by `字段` desc limit (pageNo-1)*pageSize,pageSize;
```

实现分页查询的服务端程序（这里只开发服务，不实现前端展示）：

- 企业中开发的时候，很多程序员只负责开发服务器端，开发完成之后，对外提供“接口开发文档/API接口”，不是interface，文档中应该描述这些信息：第一：接口地址；第二：接口参数（pageNo、pageSize、name、birth...）；第三：接口返回值（有的接口返回是xml，安全级别较高的系统返回的都是XML字符串，XML更加严谨；JSON：解析方便，体积小，轻便；XML体积大，解析困难，代码难度大）。
- 如何调用接口：这里的接口是一个URL，使用HttpClient组件。HttpClient组件基于Java语言实现，免费开源，作用是发送get或post请求。使用这种方式可以达到异构系统整合，不同语言系统可以通信，交换数据。

浏览器请求 ---> 服务器controller ---> 获取浏览器提交的数据（查询条件数据） --- > 数据用Map封装后以方法参数形式传入service ---> service结合传入的参数调用dao层方法进行数据操作得到返回的数据 ---> 得到返回数据后经过封装再以方法返回值返回 ---> controller中得到查询后的数据，通过插件进行JSON转换，响应给浏览器。

【注意】：mybatis中的SQL语句不支持数学运算。

```xml
<select id="getTotalByCondition" parameterType="Map" resultMap="Long">
	select count(*) from t_student s 
    <where>
        <if test="name1 != null and name1 !=''">
            and s.name like '%' #{name1}'%'
        </if>
        <if test="name1 != null and name1 !=''">
            and s.birth = #{birth1}
        </if>
    </where>
</select>

<select id="getDataListByCondition" parameterType="Map" resultMap="Student">
	select s.* from t_student s 
    <where>
        <if test="name1 != null and name1 !=''">
            and s.name like '%' #{name1}'%'
        </if>
        <if test="name1 != null and name1 !=''">
            and s.birth = #{birth1}
        </if>
    </where>
    order by birth desc
    limit #{startIndex} , #{pageSize1}
</select>
<!-- mybatis 中的占位符#{} 两边必须要有空格，不然mybatis无法识别这里有一个占位符 -->
```

jackson插件依赖：

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.3</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.12.3</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.12.3</version>
</dependency>
```

java对象 <----> json字符串可以互相转换。

```java
// 通过ObjectMapper将对象转为json字符串
ObjectMapper om = new ObjectMapper();
String json = om.writeValueAsStream(java对象); 
```



### resultType

使用Map类型封装查询结果：resultType专门用来指定**查询结果集**封装的数据类型，可以使用javabean（Java含有get、set方法的class）、简单类型、Map等，不能省略，只有select语句有。javabean不够用的情况下使用Map集合封装（跨表的情况下）。

使用resultType为Map的时候，会自动将查询结构的字段名作为Map的key。

```xml
<select id="sById" resultType="Map">
    select s.id,s.name,u.usercode
    from t_student s join t_user u
    on s.id=u.id
    where s.id=#{id}
</select>
```

```java
List<Map<String,String>> sById = sqlSession1.selectList("sById", "1001");
```



### parameterType

parameterType属性：专门用来给SQL语句的占位符传值，翻译为参数类型，占位符必须使用`#{属性名}`；只有当parameterType是简单类型时可以省略不写。简单类型有17个：
byte short int long double float char boolean
Byte Short Integer Long Double Float Character Boolean
String

```xml
<select id="getById" parameterType="java.lang.String" resultType="com.lsl.domain.Student">
    select id as sid,name as sname,birth as sbirth
    from t_student where id=#{fafasfa};
    <!--当一个SQL语句的占位符只有一个 这时{}内可以随意 -->
</select>
```

parameterType专门用来给SQL语句传值的，可以使用javabean（Java含有get、set方法的class）、简单类型、Map等。

```xml
<!--
    parameterType="java.util.Map"
    parameterType="java.util.HasMap"
    parameterType="Map"
    parameterType="map"
-->
<insert id="putmap" parameterType="map">
    insert into t_student(id,name,birth) values(#{xh},#{mz},#{sr})
</insert>
```

```java
Map<String, String> map = new HashMap<>();
map.put("mz","龙舌兰");
map.put("xh","1001");
map.put("sr","1999.09.09");
sqlSession1.insert("putmap", map);
```

什么情况下使用Map传值？

当javabean不够用的时候；什么时候javabean不够用？一般一张表对应一个javabean，当传值的时候，一些值时A表的，一些值时B表的，而使用select等语句只能传入对象或单个值，这时如果要传入A、B表的值就只能再建一个class，也就是原有的javabean不够用，得再建一个javabean。但单独为一条语句建一个javabean是不明智的，这时就可以使用Map集合传入多个值给语句了。

### 问题

mybatis中配置文件的路径问题？

Java中加载BlogMapper.xml、BlogMapper.xml映射mybatis-config.xml都是从类的根路径下开始寻找，在maven项目中BlogMapper.xml、mybatis-config.xml放于resources目录下时，直接使用配置文件带后缀的全名称当路径就好；当BlogMapper.xml放于java目录下某个包中时，引用其的路径应该是相对于java目录的路径，例如`<mapper resource="com/lsl/test/BlogMapper.xml"/>`。（普通Java项目的略）

mybatis中配置文件因为没有网络而没有提示？

本地导入ddt约束。mybatis-3-mapper.dtd、mybatis-3-config.dtd，从XML Catalog导入。





##  SqlSessionFactory

每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。构建 SqlSessionFactory可以使用Java代码的方式或xml配置的方式，这里用xml配置方式构建 SqlSessionFactory，如下：

```java
String resource = "mybatis-config.xml";
//加载配置文件
InputStream inputStream = Resources.getResourceAsStream(resource);
//通过SqlSessionFactoryBuilder()获得SqlSessionFactory 的实例
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```



## SqlSession

事务开启与业务代码：通过sqlSessionFactory对象的openSession()方法开启会话、事务，该方法会返回一个sqlSession（SqlSession等同于Connection），一个专门用来执行SQL语句的一个会话对象。

```java
public class MainTest {
    public static void main(String[] args) {
        SqlSession sqlSession = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            //事务提交机制关闭，等同于：conn.setAutoCommit(false)
            //SqlSession等同于Connection，专门用来执行SQL语句的一个会话对象
            //开启事务
            sqlSession = sqlSessionFactory.openSession();
            //执行核心业务代码，调用BlogMapper.xml里的SQL语句
            List<Student> student = sqlSession.selectList("getAll");
            for(Student s : student){
                System.out.println(s.getSid() + s.getSname() + s.getSbirth() );
            }
            //没有异常则事务结束，提交
            sqlSession.commit();
        } catch (IOException e) {
            if (sqlSession != null){
                //回滚
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }
}
```

 sqlSession对象调用语句：

- ```java
  // 增，返回造成影响的记录条数
  sqlSession.insert("save", stu);  // 传参
  sqlSession.insert("save"); // 不传入参数，直接执行指定SQL语句
  ```

- ```java
  // 修改，返回造成影响的记录条数
  sqlSession.update("update", stu2); 
  ```

- ```java
  sqlSession.selectOne("getById", "1"); // 查，返回某条数据，后面是传参
  sqlSession.selectList("getAll"); // 查，返回List集合
  ```

- ```java
  sqlSession.delete("delete","2"); // 删除，返回造成影响的记录条数
  ```



## maven配置导出问题

由于maven的约定大于配置，maven项目的java目录的配置文件默认是不会导出并生效的，解决方案就是在pom/xml文件中加入以下配置，maven项目中都可以加上以下配置防止资源导出失败的问题：

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
        </resource>
    </resources>
</build>
```

## 使用总结

1. 熟悉配置：（依赖的话导入mybatis的依赖，因为要连接数据库，所以还要mysql-connector-java依赖）

   - mybatis-config.xml：相当于配置数据库驱动、数据库连接，还要映射SQL语句文件；
   - mapper.xml：SQL语句文件，用来声明SQL语句，如何声明见下面；
   - jdbc.properties：为避免mybatis-config.xml失效，将mybatis-config.xml里面的用来配置驱动、连接数据库的值放到这个文件然后再加载到mybatis-config.xml里使用；

2. Java中使用SQL语句：

   1. 通过配置文件mybatis-config.xml来创建SqlSessionFactory（得先加载文件到内存）：

      ```java
      String resource = "mybatis_config.xml";
      // 字节输入流 加载文件
      InputStream ras = Resources.getResourceAsStream(resource); 
      SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(ras);
      ```

   2. 获取SqlSession（等同于Connection），通过这个进行事务处理、执行语句等操作。

# Log4j

mybatis执行SQL语句后打印SQL语句，可以借助第三方的开源的免费的组件：log4j，该组件专门用来负责记录日志，很多的框架都使用了或集成了该组件，例如spring、springMVC、MyBatis、Hibernate等。

Log4j1：

```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

Log4j2：

```xml
<!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.14.0</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.14.0</version>
        </dependency>
```

Log4j1的使用：

- 导入依赖，在类的根路径加入.properties配置文件；

```properties
log4j.rootLogger=DEBUG,Console
log4j.appender.Console=org.apache.log4j.ConsoleAppender
log4j.appender.Console.layout=org.apache.log4j.PatternLayout
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%m
log4j.appender.org.apache=INFO
```



# web项目中使用

## mybatis

servlet3.1版本可以使用注解Annotation代替web.xml文件。

mybatis工具类：SqlSessionUtil，编写其要先了解mybatis核心对象的生命周期：SqlSessionFactory、SqlSessionFactoryBuilder、SqlSession。

SqlSessionFactory：

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但最好还是不要一直保留着它，以保证所有的 XML 解析资源可以被释放给更重要的事情。

SqlSessionFactoryBuilder：

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

SqlSession：

每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 怎么保证一个线程一个Connection、一个线程一个SqlSession？那就是将Connection和SqlSession放到ThreadLocal中。

```java
public class SqlSessionUtil {
    private SqlSessionUtil(){}
    private static SqlSessionFactory factory;
    private static ThreadLocal<SqlSession> local = new ThreadLocal<>();
    //随着类的加载而执行一次
    static{
        try {
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取当前线程中的sqlSession
    public static SqlSession getCurrentSqlSession(){
        SqlSession sqlSession = local.get();
        if (sqlSession == null){
            sqlSession = factory.openSession();
            //将sqlSession与线程绑定
            local.set(sqlSession);
        }
        return sqlSession;
    }
    public static void close(SqlSession sqlSession) {
        if (sqlSession != null){
            sqlSession.close();
            //TOMCAT服务器自带线程池，用过的线程t1，下一次可能还会使用线程t1
            //因此需要remove，解绑sqlSession
            local.remove();
        }
    }
    public static void rollback(SqlSession sqlSession){
        if (sqlSession != null){
            sqlSession.rollback();
        }
    }
}
```

使用动态代理来处理事务：

```java
public class TransactionInvocationHandler implements InvocationHandler {

    private Object target;
    private  Object returnValue = null;
    public TransactionInvocationHandler(Object target){
        this.target = target;
    }
    //获取代理对象实例
    public Object getProxy(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.getCurrentSqlSession();
            //调用目标service当中的方法
            returnValue = method.invoke(target, args);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
            SqlSessionUtil.rollback(sqlSession);
        }finally {
            SqlSessionUtil.close(sqlSession);
        }
        return returnValue;
    }
}
```

......

总结：

使用mybatis时，Java代码部分最终是通过获得SqlSession对象来去执行mapper配置文件中映射的SQL语句来对数据库操作的，由mybatis文档中知道三个核心对象的生命周期，为了提高代码的复用效率，在对生命周期的了解上，封装了获取SqlSession的工具类，通过该工具类的静态方法直接去获取SqlSession对象。

SqlMapper配置文件在开发中的存放规范与命名规范：

在实际开发中，编写SQL语句的配置文件一般和dao接口放在一起。并且SQL映射文件的文件名要和dao接口的名字一致。



## 关于省略dao实现类

使用mybatis框架，dao实现类可以不用写，框架会自动利用JDK的动态代理机制动态地在内存中生成daoimpl.class字节码文件，但是，利用这种方式需要这样做：

1. 获取dao实例对象变为：

   ```java
   // 执行该代码，必然会在底层JVM中生成dao实现类的字节码，并调用构造器构造实现类对象
   StudentDao studentDao = SqlSessionUtil.getCurrentSqlSession().getMapper(StudentDao.class);
   ```

2. BlogMapper.xml的namespace就必须是接口的全限定接口名（xxx.xxx.xx.XxDao）；

3. DAO接口的方法名必须和mapper配置文件中SQL语句的id一致（拿dao接口的方法名作为SQL语句的id）

此时的情况下，可以为SQL语句传多个值，但这些值必须是17种简单类型，传多个值时建议是大于1个小于3个的时候就使用#{arg0} #{arg1} #{arg2}的方式，参数过多则不建议使用。mybatis3.4版本之前不能使用  #{arg0} #{arg1} #{arg2}，3.4版本以下及3.4版本使用的是#{0} #{1} #{2}这种方式。

```java
int save2(String id, String name, String birth);
```













