# JDBC概述

Java DataBase Connectivity（Java语言连接数据库），SUN公司制定的一套接口，接口都有调用者和实现者，Java程序员就是调用者，数据库厂家就是实现者，JDBC本质上就是一套接口。

为什么要面向接口编程？为了降低程序耦合度，提高程序拓展能力。面向抽象编程（多态机制就是典型的）。

为什么要制定这套jdbc接口？因为数据库的底层原理都不一样，每一个数据库产品都有自己独特的实现原理。

数据库厂商负责接口实现类的编写，也就是数据库驱动由各大厂商提供，所有的数据库驱动都以jar包形式存在，jar包内由很多接口实现类的.class文件，下载驱动去数据库官网可以下载。

驱动下载：

[MySQL :: Download MySQL Connector/J (Archived Versions)](https://downloads.mysql.com/archives/c-j/)，驱动8.0需要jdk1.8+才行，5.1.46既可以满足jdk8版本又能满足mysql库是8和5.7版本的，比较好！

驱动配置：将驱动配置到环境变量的classpath变量上（mysql-connector-java-5.1.46-bin.jar），使用idea时可以使用maven导入依赖。

# 所需依赖

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-jdbc</artifactId>
  <version>5.3.8</version>
</dependency>
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.46</version>
</dependency>
```

# JDBC编程六步

JDBC编程六步如下：

1. 注册驱动。（有两种注册驱动的方式）
2. 获取连接（表示JVM的进程和数据库进程之间的通道打开了）。
3. 获取数据库操作对象（用于发送SQL语句到数据库的对象）。
4. 发送并执行SQL语句。
5. 处理查询结果集（第4步执行select语句时才有结果集）。
6. 释放资源。

## 使用Statement

```java
public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/mysqltest?useUnicode=true&characterEncoding=utf8&useSSL=false";
    String user = "root";
    String passwd = "123456";
    Connection conn = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
        // 第一步：加载驱动，如果是Oracle数据库就是：new oracle.jdbc.driver.OracleDriver() */
        Driver driver = new com.mysql.jdbc.Driver();
		DriverManager.registerDriver(driver);
        // 第二步：创建与数据库的连接
        conn = DriverManager.getConnection(url,user,passwd);
        // 第三步：获取数据库操作对象
        statement = conn.createStatement();
        // 第四步：利用数据库操作对象来执行SQL语句
        rs = statement.executeQuery("select * from info");
        // 第五步：处理接收到的结果集
        while(rs.next()){
            // JDBC中所有下标从1开始；以下为取第二列的数据
            String name = rs.getString(2);
            // 根据字段名字获取
            String school = rs.getString("school");
            System.out.println(name + "：" + school);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        // 第六步：释放资源
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
```

URL：统一资源定位符，网络中某个资源的绝对路径，包括通信协议（jdbc:mysql://）、服务器IP地址（localhost）、软件端口（3306）、资源（database）。通信协议就是通信之前提前定好的数据传送格式。

## 使用PreparedStatement

```properties
#jdbc.properties文件
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mysqltest?useUnicode=true&characterEncoding=utf8&useSSL=false
user=root
passwd=123456
# 使用8.0新版时，驱动需要改成`Class.forName(com.mysql.cj.jdbc.Driver);`
# 然后url后面还需要加一串字符`&serverTimezone=Asia/Shanghai`来指定时区。
```

```java
public static void main(String[] args) {
    // 绑定类路径下的jdbc.properties文件
    ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
    String driver = bundle.getString("driver");
    String url = bundle.getString("url");
    String user = bundle.getString("user");
    String passwd = bundle.getString("passwd");

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        // 第一步：加载驱动
        Class.forName(driver);
        // 第二步：创建与数据库的连接
        conn = DriverManager.getConnection(url,user,passwd);
        // 第三步：获取数据库操作对象，注意该方法的prepare，没有d
        ps = conn.prepareStatement("select * from info");
        // 第四步：利用数据库操作对象来执行SQL语句
        rs = ps.executeQuery();
        // 第五步：处理接收到的结果集
        while(rs.next()){
            // JDBC中所有下标从1开始；以下为取第二列的数据
            String name = rs.getString(2);
            // 根据字段名字获取
            String school = rs.getString("school");
            System.out.println(name + "：" + school);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }finally {
        // 第六步：释放资源
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
```

# JDBC编程涉及的类

## DriverManager

用于加载数据库驱动。

```java
// 原本是DriverManager.registerDriver(new com.mysql.jdbc.Driver()); 
// 看源码，得知用以下代码也可以实现驱动加载
Class.forName("com.mysql.jdbc.Driver"); // 规定写法，加载驱动
```

## Connection

用于获取JVM运行的Java进程与数据库之间的连接。

```java
Connection connection = (Connection) DriverManager.getConnection(url,username,password);
// connection 代表数据库  可以实现数据库设置自动提交、事务提交、事务回滚
// connection.rollback(); connection.commit(); connection.seyAutoCommit();
```

## Statement

### 方法介绍

1. JDBC中的Statement对象用于向数据库发送SQL语句，要完成对数据库的增删改查操作，只需要通过这个对象向数据库发送增删改查语句即可。
2. Statement对象的方法：
   - `executeUpdate()`：用于向数据库发送增、删、改的sql语句，executeUpdate执行完后，将会返回一个整数（即增删改语句导致了数据库几行数据发生了变化)。
   - `executeQuery()`：用于向数据库发送查询语句，该方法会返回代表查询结果的ResultSet对象。

```java
// 获取用来执行SQL的对象Statement
Statement statement = connection.createStatement();
// 用Statement去执行SQL
String sql = "SELECT * FROM users";
ResultSet resultSet = statement.executeQuery(sql);
// statement.executeQuery()：查询操作（DQL），返回查询结果集
// statement.execute()：执行任何的SQL语句
// statement.executeUpdate()：更新、插入、删除（DML），返回一个受影响的记录条数（一行为一个记录（数据））
```

### 方法测试

1. 工具类（进行以下的测试时注意添加mysql-connector-java依赖）

   ```java
   public class JdbcUtils {
       /*使用配置文件时出错，暂时不知原因
       private static String driver = null;
       private static String url = null;
       private static String username = null;
       private static String password = null;
       static {
           try {
               InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
               Properties pro = new Properties();
               pro.load(in);
               driver = pro.getProperty("driver");
               url = pro.getProperty("url");
               username = pro.getProperty("username");
               password = pro.getProperty("password");
               Class.forName(driver);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       */
       private static String driver = "com.mysql.jdbc.Driver";
       private static String url = "jdbc:mysql://localhost:3306/jdbcstudy?useUnicode=true&characterEncoding=utf8&useSSL=false";
       private static String username = "root";
       private static String password = "123456";
       static {
           try {
               Class.forName(driver);
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }
       }
       //获取连接
       public static Connection getConnections() throws SQLException {
           return DriverManager.getConnection(url,username,password);
       }
       //释放连接
       public static void release(Connection connection, Statement statement, ResultSet resultSet){
           if (resultSet != null) {
               try {
                   resultSet.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
           if (statement != null){
               try {
                   statement.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
           if (connection != null){
               try {
                   connection.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
   }
   ```

2. 执行增删改的方法：都用executUpdate()

   ```java
   public static void main(String[] args) {
           Connection connection = null;
           Statement statement = null;
           ResultSet resultSet = null;
           try {
               connection = JdbcUtils.getConnections();
               statement = connection.createStatement();
               String sql = "这里是增、删、改的SQL语句";
               //String sql2 = "SELECT * FROM users";
               //resultSet = statement.executeQuery(sql2);
               int i = statement.executeUpdate(sql);
               if (i > 0){
                   System.out.println("插入成功!");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }finally {
               JdbcUtils.release(connection,statement,resultSet);
           }
       }
   ```

3. 用于查询的方法

   ```java
   public static void main(String[] args) {
       Connection connection = null;
       Statement statement = null;
       ResultSet resultSet = null;
       try {
           connection = JdbcUtils.getConnections();
           statement = connection.createStatement();
           String sql = "SELECT * FROM `users`";
           resultSet = statement.executeQuery(sql);
           while (resultSet.next()){
               System.out.println(resultSet.getObject("id"));
               System.out.println(resultSet.getObject("NAME"));
               System.out.println(resultSet.getObject("PASSWORD"));
               System.out.println(resultSet.getObject("email"));
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }finally {
           JdbcUtils.release(connection,statement,resultSet);
       }
   }
   ```

## PreparedStatement



## 关于URL

```java
String url = "jdbc:mysql://localhost:3306/jdbcstudy?useUnicode=true&characterEncoding=utf8&useSSL=false";
// 协议(jdbc:mysql)://主机地址:端口号/数据库名?参数1&参数2&参数3......
// Oracle的是 jdbc:oracle:thin:@localhost:1521:sid
```

## ResultSet

ResultSet用于接收查询结果集。

获得指定数据：

```java
ResultSet resultSet = statement.executeQuery(sql);
resultSet.getObject(); // 不知道列类型的情况下可使用
// 如果知道列类型
resultSet.getString(int columnIndex); // 不管数据库中数据是什么类型都以String形式取出
resultSet.getInt(int columnIndex);
resultSet.getDouble(int columnIndex);
```

遍历，指针：

```java
resultSet.afterLast(); // 最后面
resultSet.beforeFirst(); // 最前面
resultSet.next(); // 下一个
resultSet.previous(); // 移动到前一行
resultSet.absolute(row); // 移动到指定行
```

释放资源：

```java
// 释放连接资源
resultSet.close();
// statement.cancel();
statement.close();
connection.close();
```

# JDBC编程模板

```java
public static void main(String[] args) {
    ResourceBundle bundle = ResourceBundle.getBundle("jdbc");//绑定jdbc.properties文件
    String driver = bundle.getString("className");
    String url = bundle.getString("url");
    String user = bundle.getString("user"); 
    String password = bundle.getString("password"); 

    Connection conn = null;
    PreparedStatement ps = null；
        ResultSet rs = null;
    try{	
        Class.forName(driver); 
        conn = DriverManager.getConnection(url, user, password);
        String sql = "select * from `t_users` where `username`=?  and `password`=?";
        //执行wps这，会发送SQL语句框子给DBMS，然后DBMS对SQL语句预先编译
        ps = conn.prepareStatement(sql);
        //给占位符赋值，1代表第一个问号，setString会自动加单引号''
        ps.setString(1, user);
        ps.setString(2, password);
        //执行SQL语句
        rs = ps.executeQuery();
        while(rs.next();){
            //光标指向的行有数据，取数据，getString()：不管数据库中数据是什么类型都以String形式取出
            String data = rs.getString(1);//JDBC中所有下标从1开始，取第一列的数据
            String data = rs.getString(2);
            String data = rs.getString("column_name");//以纵行名字获取
        }
    }catch (SQLException e) {
        e.printStackTrace();
    }finally {
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```



# SQL注入问题

**为什么会出现SQL注入？**

用户输入的信息中含有SQL语句的关键字，并且这些关键字参与SQL语句的编译过程，导致SQL语句被曲解，进而达到SQL注入。

**解决SQL注入：**

只要用户提供的信息不参与SQL语句的编译过程，就可以解决了。

要想用户信息不参与SQL语句编译，就必须使用Statement接口的子接口PreparedStatement（预编译的数据库操作对象），PreparedStatement的原理是：预先对SQL语句框架进行编译，然后再给SQL语句传值。

**PreparedStatement和Statement：**

- PreparedStatement解决了SQL注入问题。
- 使用Statement来执行SQL语句，编译一次执行一次；PreparedStatement，编译一次可执行n次，效率高一点。（MySQL中，编译过的SQL语句没有任何改变，再次执行时不会再编译而是直接执行，例如第一次执行语句是先编译再执行，第二次则是直接执行而不用再经过编译这一过程）
- PreparedStatement会在编译阶段做参数类型的安全检查。

综上所述，PreparedStatement使用情况最多，Statement使用很少。业务方面要求支持SQL注入的时候，使用Statement（凡是业务方面的要求是需要进行SQL语句拼接的）。实际案例：例如页面的升序、降序，就是往SQL语句后面拼接`order by desc`等。

# JDBC事务提交

JDBC中的事务是自动提交的，只要执行任意一条DML语句，则自动提交一次，这是JDBC默认的事务行为；但在实际的业务中，通常都是n条DML语句共同联合才能完成的，必须保证这些DML语句在同一个事务中同时成功或同时失败。

```java
conn.setAutoCommit(false); // 关闭事务自动提交

conn.commit(); // 提交事务

try {
	conn.rollback(); // 回滚
} catch (SQLException e1) {
	e1.printStackTrace();
}
```



# 封装工具类

封装三部分：驱动注册、获取连接、资源关闭。

```java
public class DBUtil {
    private DBUtil(){} //防止别人new对象
    // 随着类加载而完成驱动注册
    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/databbase", "root", "123456");
    }
    public static void close(Connection conn, Statement ps, ResultSet rs){
        if(rs != null) {
            try {
                rs.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps != null) {
            try {
                ps.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        } 
    } 
}
```

# 悲观锁和乐观锁

**悲观锁：**事务必须排队执行，数据被锁住，不允许并发。

```mysql
select xxx from table_name where xxx='xx' for update;
```

加上`for update`后会锁定相应数据，锁定一横行（即锁定一条记录），这也叫行级锁或悲观锁。

**乐观锁：**（[mysql中的乐观锁是怎么实现的 - MySQL数据库 - 亿速云 (yisu.com)](https://www.yisu.com/zixun/232037.html)）

- 乐观锁，大多是基于数据版本( Version )记录机制实现。何谓数据版本?即为数据增加一个版本标识，在基于数据库表的版本解决方案中，一般是通过为数据库表增加一个 "version" 字段来实现。读取出数据时，将此版本号一同读出，之后更新时，对此版本号加一。此时，将提交数据的版本数据与数据库表对应记录的当前版本信息进行比对，如果提交的数据版本号大于数据库表中当前版本号，则予以更新，否则认为是过期数据。

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

直接在Java程序中配置和使用：

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

通过配置文件druid.properties：

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

直接在Java程序中配置和使用：

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

通过c3p0-config.xml文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>
    <!-- This is default config! -->
<!--    <default-config>-->
<!--        <property name="initialPoolSize">10</property>-->
<!--        <property name="maxIdleTime">30</property>-->
<!--        <property name="maxPoolSize">100</property>-->
<!--        <property name="minPoolSize">10</property>-->
<!--        <property name="maxStatements">200</property>-->
<!--    </default-config>-->

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
         超时后将抛出SQLException，如设为0则无限期等待。单位毫秒。默认: 0 -->
        <property name="checkoutTimeout">3000</property>
        <!--当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。默认值: 3 -->
        <property name="acquireIncrement">5</property>
        <!--定义在从数据库获取新连接失败后重复尝试的次数。默认值: 30 ；小于等于0表示无限次-->
        <property name="acquireRetryAttempts">5</property>
        <!--重新尝试的时间间隔，默认为：1000毫秒-->
        <property name="acquireRetryDelay">1000</property>
        <!--关闭连接时，是否提交未提交的事务，默认为false，即关闭连接，回滚未提交的事务 -->
        <property name="autoCommitOnClose">false</property>
        <!--c3p0将建一张名为Test的空表，并使用其自带的查询语句进行测试。如果定义了这个
                参数那么属性preferredTestQuery将被忽略。你不能在这张Test表上进行任何操作，
                它将只供c3p0测试使用。默认值: null -->
        <property name="automaticTestTable">Test</property>
        <!--如果为false，则获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常，
                但是数据源仍有效保留，并在下次调用getConnection()的时候继续尝试获取连接。
                如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。默认: false-->
        <property name="breakAfterAcquireFailure">false</property>
        <!--每60秒检查所有连接池中的空闲连接。默认值: 0，不检查 -->
        <property name="idleConnectionTestPeriod">60</property>
        <!--maxStatementsPerConnection定义了连接池内单个连接所拥有的最大缓存statements数。
                 默认值: 0 -->
        <!--JDBC的标准参数，用以控制数据源内加载的PreparedStatements数量。
                但由于预缓存的statements
       属于单个connection而不是整个连接池。所以设置这个参数需要考虑到多方面的因素。
        如果maxStatements与maxStatementsPerConnection均为0，则缓存被关闭。Default: 0-->
        <property name="maxStatements">200</property>
        <property name="maxStatementsPerConnection">100</property>
    </named-config>


    <!-- This is my config for oracle -->
<!--    <named-config name="oracle">-->
<!--        <property name="driverClass">oracle.jdbc.driver.OracleDriver</property>-->
<!--        <property name="jdbcUrl">jdbc:oracle:thin:@localhost:1521:</property>-->
<!--        <property name="user">scott</property>-->
<!--        <property name="password">abc123</property>-->
<!--        <property name="initialPoolSize">10</property>-->
<!--        <property name="maxIdleTime">30</property>-->
<!--        <property name="maxPoolSize">100</property>-->
<!--        <property name="minPoolSize">10</property>-->
<!--        <property name="maxStatements">200</property>-->
<!--    </named-config>-->
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

