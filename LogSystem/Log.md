# 日志系统

# 概述

**日志文件：**

日志文件是用于记录系统操作事件的文件集合，可分为事件日志和消息日志。具有处理历史数据、诊断问题的追踪以及理解系统的活动等重要作用。
在计算机中，日志文件是记录在操作系统或其他软件运行中发生的事件或在通信软件的不同用户之间的消息的文件。记录是保持日志的行为。在最简单的情况下，消息被写入单个日志文件。许多操作系统，软件框架和程序包括日志系统。广泛使用的日志记录标准是在因特网工程任务组（IETF）RFC5424中定义的syslog。 syslog标准使专用的标准化子系统能够生成，过滤，记录和分析日志消息。

**调试日志：**

软件开发中，我们经常需要去调试程序，做一些信息、状态的输出便于我们查询程序的运行状况。为了让我们能够更加灵活和方便的控制这些调试的信息，所以我们需要专业的日志技术。java中寻找解决bug时会需要重现bug，而调试（也就是debug）可以在程序运行中暂停程序运行，查看程序在运行中的情况。日志主要作用就是为了更方便的去重现问题（bug）。

**系统日志：**

系统日志是记录系统中硬件、软件和系统问题的信息，同时还可以监视系统中发生的事件。用户可以通过系统日志来检查错误发生的原因，或者寻找受到攻击时攻击者留下的痕迹。系统日志包括系统日志、应用程序日志和安全日志。

系统日志的价值：系统日志策略可以在故障刚刚发生时就向你发送警告信息，系统日志帮助你在最短的时间内发现问题。 系统日志是一种非常关键的组件，因为系统日志可以让你充分了解自己的环境。这种系统日志信息对于 决定故障的根本原因或者缩小系统攻击范围来说是非常关键的，因为系统日志可以让你了解故障或者袭 击发生之前的所有事件。为虚拟化环境制定一套良好的系统日志策略也是至关重要的，因为系统日志需 要和许多不同的外部组件进行关联。良好的系统日志可以防止你从错误的角度分析问题，避免浪费宝贵的排错时间。另外一种原因是借助于系统日志，管理员很有可能会发现一些之前从未意识到的问题，在 几乎所有刚刚部署系统日志的环境当中。

# Java日志框架

**搭建日志需要考虑的问题：** 

1. 控制日志输出的内容和格式 
2. 控制日志输出的位置 
3. 日志优化：异步日志，日志文件的归档和压缩 
4. 日志系统的维护 
5. 面向接口开发 —— 日志的门面 —— 一套代码操作具体日志实现

**现有日志框架：**

1. 日志门面：JCL（Jakarta Commons Logging）、slf4j（ Simple Logging Facade for Java）
2. 日志实现：JUL（java util logging）、logback、log4j、log4j2 

# JUL

JUL（Java util Logging）是Java原生的日志框架，使用时不需要另外引用第三方类库，相对其他日志框架使用方便、学习简单，能够在小型应用中灵活使用。  

## 架构

![](img/1.JUL架构.png)

1. Loggers：被称为记录器，应用程序通过获取Logger对象并调用其API来发布日志信息。Logger通常时应用程序访问日志系统的入口程序。
2. Appenders：也被称为Handlers，每个Logger都会关联一组Handlers，Logger会将日志交给关联Handlers处理，由Handlers负责将日志做记录。Handlers在此是一个抽象，其具体的实现决定了日志记录的位置可以是控制台、文件、网络上的其他日志服务或操作系统日志等。
3. Layouts：也被称为Formatters，它负责对日志事件中的数据进行转换和格式化。Layouts决定了数据在一条日志记录中的最终形式。
4. Level：每条日志消息都有一个关联的日志级别。该级别粗略指导了日志消息的重要性和紧迫，我可以将Level和Loggers，Appenders做关联以便于我们过滤消息。
5. Filters：过滤器，根据需要定制哪些信息会被记录，哪些信息会被放过  

总体流程：

1. 初始化LogManager，加载logging.properties配置文件，添加Logger到LogManager中。
2. 从单例Bean LogManager获取Logger。
3. 设置日志级别Level。
4. Handler处理日志输出。
5. Formatter处理日志格式。

## 使用JUL

**案例：**

```java
public class JULTest {
    @Test
    public void test(){
        // 获取日志记录器，通常以当前类全限定路径来获取
        Logger logger = Logger.getLogger("com.lsl.JULTest");
        // 日志记录输出
        logger.info("日志信息 INFO");
        // 通用方法
        logger.log(Level.INFO,"日志信息 INFO");
        // 通过占位符来输出变量值
        String str = "日志信息 INFO";
        Integer num = 333;
        logger.log(Level.INFO,"日志信息：{0},{1}",new Object[]{str,num});
    }
}
```

## 日志级别

java.util.logging.Level中定义了日志的级别：

1. SEVERE（最高值）：程序出现严重问题并造成终止。
2. WARNING：程序出现一些问题但不会种子程序。
3. INFO （默认级别）：消息记录，记录数据库的连接信息、IO传递信息、网络通信信息等。
4. CONFIG：配置信息，比如加载了配置文件、读取了配置问题。
5. FINE：debug日志记录信息，记录一些程序运行的状态、执行流程、参数传递信息。颗粒度低一点。
6. FINER：debug日志记录信息，记录一些程序运行的状态、执行流程、参数传递信息。
7. FINEST（最低值）：debug日志记录信息，记录一些程序运行的状态、执行流程、参数传递信息。颗粒度高一点。

两个特殊的级别：

1. OFF，可用来关闭日志记录。
2. ALL，启用所有消息的日志记录。  

测试了7个日志级别，但是默认只实现info以上的级别，如下：

```java
public class JULTest {
    @Test
    public void test(){
        // 获取日志记录器，通常以当前类全限定路径来获取
        Logger logger = Logger.getLogger("com.lsl.JULTest");
        // 只能输出前三个信息，默认情况下其他的都被过滤了
        logger.severe("server");
        logger.warning("warning");
        logger.info("info");
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }
}
```

**自定义日志级别配置：**

```java
public class JULTest {
    @Test
    public void test() throws IOException {
        // 获取日志记录器，通常以当前类全限定路径来获取
        Logger logger = Logger.getLogger("com.lsl.JULTest");
        // 关闭系统默认配置
        logger.setUseParentHandlers(false);
        /* 自定义日志级别 start */
        // 一、 b.创建handler对象，输出日志到控制台
        ConsoleHandler consoleHandler = new ConsoleHandler();
        // c.创建formatter对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        // d.进行关联
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
        // e.设置日志级别
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        // 二、输出到日志文件，日志文件需要先创建好
        FileHandler fileHandler = new FileHandler("d:/logs/jul.log");
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);
        /* 自定义日志级别 end */

        logger.severe("server");
        logger.warning("warning");
        logger.info("info");
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }
}
```

## Logger父子关系

JUL中Logger之间存在父子关系，这种父子关系通过树状结构存储，JUL在初始化时会创建一个顶层RootLogger作为所有Logger的父Logger，存储上作为树状结构的根节点。父子关系通过路径来关联。  RootLogger有默认的格式器和转换器。

```java
@Test
public void testLogParent() throws Exception {
    // 日志记录器对象父子关系 com.lsl的父为com
    Logger logger1 = Logger.getLogger("com.lsl");
    Logger logger2 = Logger.getLogger("com");
    System.out.println(logger1.getParent() == logger2);
    // 所有日志记录器对象的顶级父元素 class为：java.util.logging.LogManager$RootLogger@61e717c2，name：
    System.out.println("logger2 parent:" + logger2.getParent() + "，name：" +
            logger2.getParent().getName());
    // 一、自定义日志级别
    // a.关闭系统默认配置
    logger2.setUseParentHandlers(false);
    // b.创建handler对象
    ConsoleHandler consoleHandler = new ConsoleHandler();
    // c.创建formatter对象
    SimpleFormatter simpleFormatter = new SimpleFormatter();
    // d.进行关联
    consoleHandler.setFormatter(simpleFormatter);
    logger2.addHandler(consoleHandler);
    // e.设置日志级别
    logger2.setLevel(Level.ALL);
    consoleHandler.setLevel(Level.ALL);
    // 测试日志记录器对象父子关系
    logger1.severe("severe");
    logger1.warning("warning");
    logger1.info("info");
    logger1.config("config");
    logger1.fine("fine");
    logger1.finer("finer");
    logger1.finest("finest");
}
```

## JUL配置文件

默认的配置文件路径`$JAVAHOME\jre\lib\logging.properties`。

使用配置文件来配置：

```properties
## RootLogger处理器（获取时设置），默认为ConsoleHandler
handlers= java.util.logging.ConsoleHandler,java.util.logging.FileHandler
# 设置RootLogger日志等级
.level= INFO

## TestLog日志处理器
TestLog.handlers= java.util.logging.FileHandler
# TestLog日志等级
TestLog.level= INFO
# 忽略父日志处理
TestLog.useParentHandlers=false


## 控制台处理器
# 输出日志级别
java.util.logging.ConsoleHandler.level = INFO
# 输出日志格式
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
java.util.logging.ConsoleHandler.encoding = UTF-8

java.util.logging.SimpleFormatter.format = %4$s: %5$s [%1$tc]%n

## 文件处理器
# 输出日志级别
java.util.logging.FileHandler.level=INFO
# 输出日志格式
java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter
# 输出日志文件路径
java.util.logging.FileHandler.pattern = D:\\logs\\TestLog.log
# 输出日志文件限制大小（50000字节）
java.util.logging.FileHandler.limit = 50000
# 输出日志文件限制个数
java.util.logging.FileHandler.count = 10
# 输出日志文件 是否是追加
java.util.logging.FileHandler.append=true
```

```java
@Test
public void testProperties() throws Exception {
    // 读取自定义配置文件
    InputStream in = JULTest.class.getClassLoader().getResourceAsStream("logging.properties");
    // 获取日志管理器对象
    LogManager logManager = LogManager.getLogManager();
    // 通过日志管理器加载配置文件
    logManager.readConfiguration(in);
    Logger logger = Logger.getLogger("com.lsl.JULTest");
    logger.severe("severe");
    logger.warning("warning");
    logger.info("info");
    logger.config("config");
    logger.fine("fine");
    logger.finer("finer");
    logger.finest("finest");
}
```

![](img/2.日志原理.png)

# Log4j

Log4j是Apache下的一款开源的日志框架，通过在项目中使用 Log4J，我们可以将日志信息输出到控制台、文件、甚至是数据库中。我们可以控制每一条日志的输出格式，通过定义日志的输出级别可以更灵活地控制日志的输出过程，方便项目的调试。

官方网站： http://logging.apache.org/log4j/1.2/  。

```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

入门案例：

```java
public class Log4jTest {
    @Test
    public void test(){
        // 没有自定义配置文件，需要初始化配置信息
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Log4jTest.class);
        logger.info("hello log4j");

        // 6个日志级别
        logger.fatal("fatal");  // 严重错误，一般会造成系统崩溃和终止运行
        logger.error("error");  // 错误信息，但不会影响系统运行
        logger.warn("warn");    // 警告信息，可能会发生问题
        logger.info("info");    // 程序运行信息，数据库的连接、网络、IO操作等
        logger.debug("debug");  // 调试信息，一般在开发阶段使用，记录程序的变量、参数等
        logger.trace("trace");  // 追踪信息，记录程序的所有流程信息
    }
}
```

每个Logger都被了一个日志级别（log level），用来控制日志信息的输出。日志级别从高到低分为：

1. fatal 指出每个严重的错误事件将会导致应用程序的退出。
2. error 指出虽然发生错误事件，但仍然不影响系统的继续运行。
3. warn  表明会出现潜在的错误情形。
4. info   一般和在粗粒度级别上，强调应用程序的运行全程。
5. debug 一般用于细粒度级别上，对调试应用程序非常有帮助。
6. trace 是程序追踪，可以用于输出程序运行中的变量，显示执行的流程。

还有两个特殊的级别：

1. OFF，可用来关闭日志记录。
2. ALL，启用所有消息的日志记录。

## Log4J 组件

Log4J 主要由 Loggers (日志记录器)、Appenders（输出端）和 Layout（日志格式化器）组成。其中 Loggers 控制日志的输出级别与日志是否输出；Appenders 指定日志的输出方式（输出到控制台、文件、数据库等）；Layout 控制日志信息的输出格式。

**Loggers ：**

日志记录器：负责收集处理日志记录，实例的命名就是类“XX”的full quailied name（类的全限定名）， Logger的名字大小写敏感，其命名有继承机制，例如：name为org.apache.commons的logger会继承 name为org.apache的logger。

RootLogger：Log4J中有一个特殊的logger叫做“root”，他是所有logger的根，也就意味着其他所有的logger都会直接 或者间接地继承自root。root logger可以用Logger.getRootLogger()方法获取。

 **Appenders：**

Appender 用来指定日志输出到哪个地方，可以同时指定日志的输出目的地。Log4j 常用的输出目的地有以下几种：

1. ConsoleAppender：将日志输出到控制台。
2. FileAppender：将日志输出到文件中。
3. DailyRollingFileAppender：将日志输出到一个日志文件，并且每天输出到一个新的文件。
4. RollingFileAppender：将日志信息输出到一个日志文件，并且指定文件的尺寸，当文件大 小达到指定尺寸时，会自动把文件改名，同时产生一个新的文件。
5. JDBCAppender：把日志信息保存到数据库中。

**Layouts：**

布局器 Layouts用于控制日志输出内容的格式，让我们可以使用各种需要的格式输出日志。Log4j常用 的Layouts：

1. org.apache.log4j.HTMLLayout：格式化日志输出为HTML表格形式 。
2. org.apache.log4j.SimpleLayout：简单的日志输出格式化，打印的日志格式为（info - message）。
3.  org.apache.log4j.PatternLayout：最强大的格式化器，可以根据自定义格式输出日志，如果没有指定转换格式那 就是用默认的转换格式。

在 log4j.properties 配置文件中，我们定义了日志输出级别与输出端，在输出端中分别配置日志的输出格式。

```markdown
** log4j 采用类似 C 语言的 printf 函数的打印格式格式化日志信息，具体的占位符及其含义如下：**
    %m 输出代码中指定的日志信息
    %p 输出优先级，及 DEBUG、INFO 等
    %n 换行符（Windows平台的换行符为 "\n"，Unix 平台为 "\n"）
    %r 输出自应用启动到输出该 log 信息耗费的毫秒数
    %c 输出打印语句所属的类的全名
    %t 输出产生该日志的线程全名
    %d 输出服务器当前时间，默认为 ISO8601，也可以指定格式，如：%d{yyyy年MM月dd日 HH:mm:ss}
    %l 输出日志时间发生的位置，包括类名、线程、及在代码中的行数。如：Test.main(Test.java:10)
    %F 输出日志消息产生时所在的文件名称
    %L 输出代码中的行号
    %% 输出一个 "%" 字符
** 可以在 % 与字符之间加上修饰符来控制最小宽度、最大宽度和文本的对其方式。如：**
    %5c 输出category名称，最小宽度是5，category<5，默认的情况下右对齐
    %-5c 输出category名称，最小宽度是5，category<5，"-"号指定左对齐,会有空格
    %.5c 输出category名称，最大宽度是5，category>5，就会将左边多出的字符截掉，<5不会有空格
    %20.30c category名称<20补空格，并且右对齐，>30字符，就从左边交远销出的字符截掉
```

```properties
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
```

**开启lof4j的内部日志：**

```java
LogLog.setInternalDebugging(true);
```



## 配置Log4J

log4j.properties：（文件名确定的，放于类路径下即可）

```properties
# 指定顶级父元素默认配置信息，指定日志级别与Appender
# console、file、logDB的配置见：输出到控制台、输出到文件、输出到数据库这三部分
log4j.rootLogger=info,console,file,logDB
```

### 输出到控制台

```properties
# 配置：输出到控制台
#指定控制台日志输出的Appender
log4j.appender.console=org.apache.log4j.ConsoleAppender
#指定输出消息格式 org.apache.log4j.PatternLayout的默认规则是：%m%n
log4j.appender.console.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
```

### 输出到文件

将输出的日志保存到一个日志文件：（目录和文件会自动创建，相对于磁盘根目录）

```properties
#配置：输出到文件
#指定日志输出的Appender
log4j.appender.file=org.apache.log4j.FileAppender
#指定输出消息格式 layout
log4j.appender.file.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.file.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
#指定日志保存路径 默认以追加方式存进文件
log4j.appender.file.file=D:\\logs\\log4j.log
#log4j.appender.FILE.append=true
#设置字符集
log4j.appender.file.encoding=UTF-8
```

保存日志到文件并将日志文件按照一定规则拆分：（文件会自动创建）

```properties
#按照文件大小拆分的 Appender对象
#指定日志输出的Appender
log4j.appender.rollingFile=org.apache.log4j.RollingFileAppender
#指定输出消息格式 layout
log4j.appender.rollingFile.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.rollingFile.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
#指定日志保存路径 默认以追加方式存进文件
log4j.appender.rollingFile.file=/log/log4j.log
#log4j.appender.FILE.append=true
#设置字符集
log4j.appender.rollingFile.encoding=UTF-8
#指定日志文件内容的大小，如果日志文件超过1MB就会拆分，拆分数量最大为10个
#超过10个会按照时间进行覆盖
log4j.appender.rollingFile.maxFileSize=1MB
#指定日志文件的数量 默认1个
log4j.appender.rollingFile.maxBackupIndex=10
```

保存日志到文件并将日志文件按照时间规则来创建：

```properties
#按照时间规则拆分的 Appender对象
#指定日志输出的Appender
log4j.appender.dailyFile=org.apache.log4j.DailyRollingFileAppender
#指定输出消息格式 layout
log4j.appender.dailyFile.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.dailyFile.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
#指定日志保存路径 默认以追加方式存进文件
log4j.appender.dailyFile.file=/log/log4j.log
#log4j.appender.FILE.append=true
#设置字符集
log4j.appender.dailyFile.encoding=UTF-8
#按照日期拆分的规则
log4j.appender.dailyFile.datePattern='.'yyyy-MM-dd-HH-mm
# 例如：log4j.log.2022-03-29-17-49；经常的做法是以天为拆分单位
```

有三种方式：file、rollingFile、dailyFile，选用哪种就在rootLogger指定日志级别与Appender：

```properties
log4j.rootLogger=info,console,dailyFile
```

### 输出到数据库

创建好数据库和数据库表：

```mysql
create database log4j character set utf8 collate utf8_general_ci;
create table log4j.`log` (
    `log_id` int(11) NOT NULL AUTO_INCREMENT,
    `project_name` varchar(255) DEFAULT NULL COMMENT '目项名',
    `create_date` varchar(255) DEFAULT NULL COMMENT '创建时间',
    `level` varchar(255) DEFAULT NULL COMMENT '优先级',
    `category` varchar(255) DEFAULT NULL COMMENT '所在类的全名',
    `file_name` varchar(255) DEFAULT NULL COMMENT '输出日志消息产生时所在的文件名称 ',
    `thread_name` varchar(255) DEFAULT NULL COMMENT '日志事件的线程名',
    `line` varchar(255) DEFAULT NULL COMMENT '号行',
    `all_category` varchar(255) DEFAULT NULL COMMENT '日志事件的发生位置',
    `message` varchar(4000) DEFAULT NULL COMMENT '输出代码中指定的消息',
    PRIMARY KEY (`log_id`)
)engine=innodb default charset=utf8;
```

将日志输出到MySQL中数据库的配置如下：

```properties
#输出到mysql
log4j.appender.logDB=org.apache.log4j.jdbc.JDBCAppender
log4j.appender.logDB.layout=org.apache.log4j.PatternLayout
log4j.appender.logDB.Driver=com.mysql.jdbc.Driver
log4j.appender.logDB.URL=jdbc:mysql://localhost:3306/log4j?characterEncoding=utf8&useUnicode=true&useSSL=false
log4j.appender.logDB.User=root
log4j.appender.logDB.Password=123456
log4j.appender.logDB.Sql=INSERT INTO log(project_name,create_date,level,category,file_name,thread_name,line,all_category,message) values('logSystem','%d{yyyy-MM-ddHH:mm:ss}','%p','%c','%F','%t','%L','%l','%m')
```

需要在rootLogger指定指定logDB。

需要导入MySQL的驱动的依赖：

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
```

### 总结—总配置文件

log4j.properties：（文件名得是这个，放于类路径下）

```properties
# 指定顶级父元素默认配置信息，指定日志级别与Appender；日志的输出级别与输出端 console file mysql，
log4j.rootLogger=info,console,dailyFile,logDB

# 配置：输出到控制台
#指定控制台日志输出的Appender
log4j.appender.console=org.apache.log4j.ConsoleAppender
#指定输出消息格式 org.apache.log4j.PatternLayout的默认规则是：%m%n
log4j.appender.console.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n

#配置：输出到文件
#指定日志输出的Appender
log4j.appender.file=org.apache.log4j.FileAppender
#指定输出消息格式 layout
log4j.appender.file.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.file.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
#指定日志保存路径 默认以追加方式存进文件
log4j.appender.file.file=D:\\logs\\log4j.log
#log4j.appender.FILE.append=true
#设置字符集
log4j.appender.file.encoding=UTF-8

#按照文件大小拆分的 Appender对象
#指定日志输出的Appender
log4j.appender.rollingFile=org.apache.log4j.RollingFileAppender
#指定输出消息格式 layout
log4j.appender.rollingFile.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.rollingFile.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
#指定日志保存路径 默认以追加方式存进文件
log4j.appender.rollingFile.file=/log/log4j.log
#log4j.appender.FILE.append=true
#设置字符集
log4j.appender.rollingFile.encoding=UTF-8
#指定日志文件内容的大小，如果日志文件超过1MB就会拆分，拆分数量最大为10个
#超过10个会按照时间进行覆盖
log4j.appender.rollingFile.maxFileSize=1MB
#指定日志文件的数量 默认1个
log4j.appender.rollingFile.maxBackupIndex=10

#按照时间规则拆分的 Appender对象
#指定日志输出的Appender
log4j.appender.dailyFile=org.apache.log4j.DailyRollingFileAppender
#指定输出消息格式 layout
log4j.appender.dailyFile.layout=org.apache.log4j.PatternLayout
#指定输出消息格式的内容
log4j.appender.dailyFile.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
#指定日志保存路径 默认以追加方式存进文件
log4j.appender.dailyFile.file=/log/log4j.log
#log4j.appender.FILE.append=true
#设置字符集
log4j.appender.dailyFile.encoding=UTF-8
#按照日期拆分的规则
log4j.appender.dailyFile.datePattern='.'yyyy-MM-dd-HH-mm

#输出到mysql
log4j.appender.logDB=org.apache.log4j.jdbc.JDBCAppender
log4j.appender.logDB.layout=org.apache.log4j.PatternLayout
log4j.appender.logDB.Driver=com.mysql.jdbc.Driver
log4j.appender.logDB.URL=jdbc:mysql://localhost:3306/log4j?characterEncoding=utf8&useUnicode=true&useSSL=false
log4j.appender.logDB.User=root
log4j.appender.logDB.Password=123456
log4j.appender.logDB.Sql=INSERT INTO log(project_name,create_date,level,category,file_name,thread_name,line,all_category,message) values('logSystem','%d{yyyy-MM-ddHH:mm:ss}','%p','%c','%F','%t','%L','%l','%m')
```

## 自定义的Logger

自定义的Logger，为了变量不同业务场景的日志的分类记录。在log4j.properties里配置：

```properties
# RootLogger配置
log4j.rootLogger = trace,console
# 自定义Logger的配置的格式：log4j.logger.logger所在包=日志输出级别和输出位置
# 比如我在com.lsl下某个类使用了一个Logger，将其打印的日志保存到文件，那么设置如下：
log4j.logger.com.lsl = info,file
log4j.logger.org.apache = error
```

```java
public class Log4jTest {
    @Test
    public void testCustomLogger() throws Exception {
        // 自定义 com.lsl
        Logger logger1 = Logger.getLogger(Log4jTest.class);
        logger1.fatal("fatal"); // 严重错误，一般会造成系统崩溃和终止运行
        logger1.error("error"); // 错误信息，但不会影响系统运行
        logger1.warn("warn"); // 警告信息，可能会发生问题
        logger1.info("info"); // 程序运行信息，数据库的连接、网络、IO操作等
        logger1.debug("debug"); // 调试信息，一般在开发阶段使用，记录程序的变量、参数等
        logger1.trace("trace"); // 追踪信息，记录程序的所有流程信息
        // 自定义 org.apache
        Logger logger2 = Logger.getLogger(Logger.class);
        logger2.fatal("fatal logger2"); // 严重错误，一般会造成系统崩溃和终止运行
        logger2.error("error logger2"); // 错误信息，但不会影响系统运行
        logger2.warn("warn logger2"); // 警告信息，可能会发生问题
        logger2.info("info logger2"); // 程序运行信息，数据库的连接、网络、IO操作等
        logger2.debug("debug logger2"); // 调试信息，一般在开发阶段使用，记录程序的变量、参数等
        logger2.trace("trace logger2"); // 追踪信息，记录程序的所有流程信息
    }
}
```

# JCL

全称为Jakarta Commons Logging，是Apache提供的一个通用日志API。

1. 它是为 "所有的Java日志实现"提供一个统一的接口，它自身也提供一个日志的实现，但是功能非常常弱 （SimpleLog）。所以一般不会单独使用它。
2. 它允许开发人员使用不同的具体日志实现工具: Log4j、Jdk 自带的日志（JUL)；JCL 有两个基本的抽象类：Log(基本记录器)和LogFactory(负责创建Log实例)。

![](img/3.JCL.png)

## 入门案例

1、添加依赖

```xml
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.2</version>
</dependency>
```

2、输出日志

```java
public class JCLTest {
    @Test
    public void testQuick() throws Exception {
        Log log = LogFactory.getLog(JCLTest.class);
        log.info("hello");
        log.fatal("fatal");
        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");
    }
}
```

没有导入log4j的依赖时，使用的是JDK本身的日志框架；导入log4j的依赖后，使用的是log4j的。

使用日志门面：

1. 面向接口开发，不再依赖具体的实现类。减少代码的耦合。
2. 项目通过导入不同的日志实现类来实现灵活地切换日志框架。
3. 统一API，方便开发者学习和使用。
4. 统一配置便于项目日志的管理  。

## JCL原理

1、通过LogFactory动态地加载Log实现类：

![](img/4.JCLyuan.png)

2、

# 日志门面

当我们的系统变的更加复杂的时候，我们的日志就容易发生混乱。随着系统开发的进行，可能会更新不同的日志框架，造成当前系统中存在不同的日志依赖，让我们难以统一的管理和控制。就算我们强制要求所有的模块使用相同的日志框架，系统中也难以避免使用其他类似spring、mybatis等其他的第三方框架，它们依赖于与我们规定所不同的其它日志框架，而且他们自身的日志系统就有着不一致性，依然会出现日志体系的混乱。

因此我们借鉴JDBC的思想，为日志系统也提供一套门面，那么我们就可以面向这些接口规范来开发，避免了直接依赖具体的日志框架。这样我们的系统在日志中，就存在了日志的门面和日志的实现。

1. 常见的日志门面 ：JCL、slf4j。
2. 常见的日志实现：JUL、log4j、logback、log4j2。

日志门面与日志接口的关系：

![](img/5.日志门面与日志实现.png)

日志框架出现的历史顺序：log4j ===>JUL===>JCL===> slf4j ===> logback ===> log4j2。

现在比较经典的就是：1.slf4j+logback；2.slf4j+log4j2。

# [SLF4J](https://www.slf4j.org/)

## 入门案例

1、依赖

```xml
<!-- 日志门面 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
<!-- slf4j内置的简单实现 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>2.0.0-alpha5</version>
    <scope>test</scope>
</dependency>
```

2、测试代码

```java
public class Slf4jTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);
    @Test
    public void test(){
        LOGGER.error("Error");
        LOGGER.warn("Warn");
        LOGGER.info("Info");
        // 使用占位符输出
        String str = "URL:";
        Integer num = 33;
        LOGGER.info("用户：{},{}",str,num);
        // 异常
        try {
            int i = 1 / 0;
        }catch (Exception e){
            LOGGER.error("异常：",e);
        }
    }
}
```

## 绑定日志实现Binging

SLF4J支持各种日志框架。SLF4J发行版附带了几个称为“SLF4J绑定”的jar文件，每个绑定对应一个受支持的框架。  

![](img/6.slf4j.png)

如果添加了slf4j-nop.jar，slf4j将不会使用任何的日志框架，功能关闭。

**绑定logback日志实现：**

```xml
<!-- 日志门面 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
<!-- slf4j内置的简单实现 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
<!--<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>2.0.0-alpha5</version>
    <scope>test</scope>
</dependency>-->
<!-- 日志实现 logback -->
<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.3.0-alpha12</version>
    <scope>test</scope>
</dependency>
```

如果加入了多个日志实现的依赖，那么默认会使用第一个，如上依赖配置，不注释掉slf4j-simple时，使用的就是slf4j-simple（以第一个日志实现依赖为标准）。

```java
public class Slf4jTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);
    @Test
    public void test(){
        LOGGER.error("Error");
        LOGGER.warn("Warn");
        LOGGER.info("Info");
    }
}
```

**绑定log4j日志实现：**（导入slf4j的再导入以下即可）

```xml
<!-- 日志门面 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
<!-- 适配器 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>2.0.0-alpha5</version>
    <scope>test</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

引入log4j.properties，测试：

```java
public class Slf4jTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);
    @Test
    public void test(){
        LOGGER.error("Error");
        LOGGER.warn("Warn");
        LOGGER.info("Info");
    }
}
```

**绑定JUL日志实现：**

```xml
<!-- 日志门面 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-jdk14 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>2.0.0-alpha5</version>
    <scope>test</scope>
</dependency>
```

```java
public class Slf4jTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);
    @Test
    public void test(){
        LOGGER.error("Error");
        LOGGER.warn("Warn");
        LOGGER.info("Info");
    }
}
```

**总结：**使用slf4j的日志实现绑定流程:

1. 添加slf4j-api的依赖。
2. 使用slf4j的API在项目中进行统一的日志记录。
3. 绑定具体的日志实现框架：
   1. 绑定已经实现了slf4j的日志框架，直接添加对应依赖。
   2. 绑定没有实现slf4j的日志框架，先添加日志的适配器，再添加实现类的依赖（如log4j）。
4. slf4j只会绑定一个日志实现框架（如果出现多个默认使用第一个依赖日志实现）。

## 桥接旧的日志框架Bridging

通常，您依赖的某些组件依赖于SLF4J以外的日志记录API。您也可以假设这些组件在不久的将来不会切换到SLF4J。为了解决这种情况，SLF4J附带了几个桥接模块，这些模块将对log4j，JCL和java.util.logging API的调用重定向，就好像它们是对SLF4J API一样。

**桥接解决的是项目中日志的遗留问题，当系统中存在之前的日志API，可以通过桥接转换到slf4j的实现：**

1. 先去除之前老的日志框架的依赖 。
2. 添加SLF4J提供的桥接组件——桥接器引入。
3. 为项目添加SLF4J的具体实现。

主要的几个桥接器如下：

```xml
<!-- log4j -->
<!-- https://mvnrepository.com/artifact/org.slf4j/log4j-over-slf4j -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>log4j-over-slf4j</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
<!-- jcl -->
<!-- https://mvnrepository.com/artifact/org.slf4j/jcl-over-slf4j -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
<!-- jul -->
<!-- https://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jul-to-slf4j</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
```

注意事项：

1. jcl-over-slf4j.jar 和 slf4j-jcl.jar 不能同时部署。前一个jar文件将导致JCL将日志系统的选择委托给SLF4J，后一个jar文件将导致SLF4J将日志系统的选择委托给JCL，从而导致无限循环。
2. log4j-over-slf4j.jar 和 slf4j-log4j12.jar 也不能同时出现。
3. jul-to-slf4j.jar和slf4j-jdk14.jar 也不能同时出现。
4. 所有的桥接都只对Logger日志记录器对象有效，如果程序中调用了内部的配置类或者是Appender、Filter等对象，将无法产生效果。  

# LogBack

Logback是由log4j创始人设计的另一个开源日志组件，性能比log4j要好。

官方网站：https://logback.qos.ch/index.html 。

Logback主要分为三个模块：

1. logback-core：其它两个模块的基础模块。
2. logback-classic：它是log4j的一个改良版本，同时它完整实现了slf4j API。
3. logback-access：访问模块与Servlet容器集成提供通过Http来访问日志的功能。

后续的日志代码都是通过SLF4J日志门面搭建日志系统，所以在代码是没有区别，主要是通过修改配置文件和pom.xml依赖。  

## logback入门案例

1、依赖导入

```xml
<!-- 日志门面 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
<!-- 日志实现 logback -->
<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.3.0-alpha12</version>
    <scope>test</scope>
</dependency>
```

2、logger：

```java
public class LogBackTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogBackTest.class);
    @Test
    public void test(){
        LOGGER.info("Hello LogBack");
    }
}
```

## logback的配置文件

**logback会依次读取以下类型配置文件：**（如果这些配置文件均不存在会采用默认配置）

1. logback.groovy。
2. logback-test.xml。
3. logback.xml 。

**logback组件之间的关系：**

1. Logger：日志的记录器，把它关联到应用的对应的context上后，主要用于存放日志对象，也可以定义日志类型、级别。
2.  Appender：用于指定日志输出的目的地，输出目的地可以是控制台、文件、数据库等等。
3.  Layout：负责把事件转换成字符串，格式化的日志信息的输出。在logback中Layout对象被封装在encoder中。



### 输出到控制台

**logback.xml 配置：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 配置集中管理属性 引用这些属性值的格式：${name} -->
    <property name="pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} %c [%thread] %-5level %msg%n"/>
    <!-- 控制台输出的 appender -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!-- 控制输出流对象 默认是System.out  该为System.err-->
        <target>System.err</target>
        <!-- 日志消息格式配置 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
    </appender>
    <!-- 配置输出信息级别和appender 用于选用哪个appender -->
    <root level="ALL">
        <appender-ref ref="console"/>
    </root>
</configuration>
```

**测试：**

```java
public class LogBackTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogBackTest.class);
    @Test
    public void test(){
        LOGGER.info("Hello LogBack");
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.debug("debug");
        LOGGER.trace("trace");
    }
}
```

**关于输出格式的设置的字符代表：**

```properties
<!-- 日志输出格式：
    %-5level 级别从左显示5个字符宽度
    %d{yyyy-MM-dd HH:mm:ss.SSS}	日期
    %c	类的完整名称
    %M	为method，方法
    %L	为行号
    %thread	线程名称
    %m或者%msg	信息
    %n	换行
-->
<!-- 格式化输出：
	%d	表示日期，
	%thread	表示线程名，
	%-5level	级别从左显示5个字符宽度
	%msg：日志消息，
	%n	是换行符
-->
```

### 输出到文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 集中管理属性值 引用这些属性值的格式为：${name} -->
    <!-- 输出到控制台的格式 -->
    <property name="pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} %c [%thread] %-5level %msg%n"/>
    <!-- 输出的日志文件的位置 /logs相对于磁盘根路径，如果是./logs则是相对于当前项目模块根路径 -->
    <property name="log_dir" value="./logs"/>

    <!-- 控制台输出的 appender对象 -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!-- 控制输出流对象 默认是System.out  该为System.err-->
        <target>System.err</target>
        <!-- 日志消息格式配置 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
    </appender>
    <!-- 输出到文件 appender对象 -->
    <appender name="file" class="ch.qos.logback.core.FileAppender">
        <file>${log_dir}/logback.log</file>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
    </appender>
    <!-- html格式的日志文件 appender对象 -->
    <!-- 日志会以html表格的形式展示 -->
    <appender name="htmlFile" class="ch.qos.logback.core.FileAppender">
        <file>${log_dir}/logback.html</file>
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="ch.qos.logback.classic.html.HTMLLayout">
                <pattern>%level%d{yyyy-MM-ddHH:mm:ss}%c%M%L%thread%m</pattern>
            </layout>
        </encoder>
    </appender>
	<!-- 选用file、htmlFile这两个appender，输出日志级别为全部日志级别 -->
    <root level="ALL">
        <!--<appender-ref ref="console"/>-->
        <appender-ref ref="file"/>
        <appender-ref ref="htmlFile"/>
    </root>
</configuration>
```

### 日志拆分

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 集中管理属性值 引用这些属性值的格式为：${name} -->
    <!-- 输出到控制台的格式 -->
    <property name="pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} %c [%thread] %-5level %msg%n"/>
    <!-- 输出的日志文件的位置 /logs相对于磁盘根路径，如果是./logs则是相对于当前项目模块根路径 -->
    <property name="log_dir" value="./logs"/>

    <!-- 将日志拆分和规定压缩的 appender对象 -->
    <appender name="rollFile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log_dir}/roll_logback.log</file>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
        <!-- 指定拆分规则 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 按照时间和压缩格式声明拆分的文件名 -->
            <!-- %d{yyyy-MM-dd-HH-mm-ss}用于指定按年、月、日、时、分或秒来进行拆分 -->
            <!--<FileNamePattern>${log_dir}/rolling.%d{yyyy-MM-dd-HH-mm-ss}.log%i.gz</FileNamePattern>-->
            <FileNamePattern>${log_dir}/rolling.%d{yyyy-MM-dd-HH-mm-ss}.log%i</FileNamePattern>
            <!-- 按照文件大小拆分 -->
            <maxFileSize>1MB</maxFileSize>
        </rollingPolicy>
    </appender>

    <root level="ALL">
        <appender-ref ref="rollFile"/>
    </root>
</configuration>
```

### 过滤器与异步日志

过滤器，细粒度筛选。以name为rollFile的appender为例：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 集中管理属性值 引用这些属性值的格式为：${name} -->
    <!-- 输出到控制台的格式 -->
    <property name="pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} %c [%thread] %-5level %msg%n"/>
    <!-- 输出的日志文件的位置 /logs相对于磁盘根路径，如果是./logs则是相对于当前项目模块根路径 -->
    <property name="log_dir" value="./logs"/>

    <!-- 控制台输出的 appender -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!-- 控制输出流对象 默认是System.out  该为System.err-->
        <target>System.err</target>
        <!-- 日志消息格式配置 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
    </appender>

    <!-- 将日志拆分和规定压缩的 appender对象 -->
    <appender name="rollFile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log_dir}/roll_logback.log</file>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
        <!-- 指定拆分规则 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 按照时间和压缩格式声明拆分的文件名 -->
            <!-- %d{yyyy-MM-dd-HH-mm-ss}用于指定按年、月、日、时、分或秒来进行拆分 -->
            <!--<FileNamePattern>${log_dir}/rolling.%d{yyyy-MM-dd-HH-mm-ss}.log%i.gz</FileNamePattern>-->
            <FileNamePattern>${log_dir}/rolling.%d{yyyy-MM-dd-HH-mm-ss}.log%i</FileNamePattern>
            <!-- 按照文件大小拆分 -->
            <maxFileSize>1MB</maxFileSize>
        </rollingPolicy>
        <!-- 日志级别过滤器 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- 日志过滤规则-->
            <level>ERROR</level> <!-- 超过error级别的日志就记录 -->
            <!-- ACCEPT 接收  NEUTRAL 往后走   DENY拒绝 -->
            <onMatch>ACCEPT</onMatch> <!-- 接收当前error级别以上的 -->
            <onMismatch>DENY</onMismatch> <!-- 拒绝当前error级别以下的 -->
        </filter>
    </appender>

    <root level="ALL">
        <appender-ref ref="console"/>
        <appender-ref ref="rollFile"/>
    </root>
</configuration>
```

异步日志（可提升性能）：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 集中管理属性值 引用这些属性值的格式为：${name} -->
    <!-- 输出到控制台的格式 -->
    <property name="pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} %c [%thread] %-5level %msg%n"/>
    <!-- 输出的日志文件的位置 /logs相对于磁盘根路径，如果是./logs则是相对于当前项目模块根路径 -->
    <property name="log_dir" value="./logs"/>

    <!-- 控制台输出的 appender -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!-- 控制输出流对象 默认是System.out  该为System.err-->
        <target>System.err</target>
        <!-- 日志消息格式配置 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
    </appender>

    <!-- 将日志拆分和规定压缩的 appender对象 -->
    <appender name="rollFile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log_dir}/roll_logback.log</file>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
        <!-- 指定拆分规则 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 按照时间和压缩格式声明拆分的文件名 -->
            <!-- %d{yyyy-MM-dd-HH-mm-ss}用于指定按年、月、日、时、分或秒来进行拆分 -->
            <!--<FileNamePattern>${log_dir}/rolling.%d{yyyy-MM-dd-HH-mm-ss}.log%i.gz</FileNamePattern>-->
            <FileNamePattern>${log_dir}/rolling.%d{yyyy-MM-dd-HH-mm-ss}.log%i</FileNamePattern>
            <!-- 按照文件大小拆分 -->
            <maxFileSize>1MB</maxFileSize>
        </rollingPolicy>
        <!-- 日志级别过滤器 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- 日志过滤规则-->
            <level>ERROR</level> <!-- 超过error级别的日志就记录 -->
            <!-- ACCEPT 接收  NEUTRAL 往后走   DENY拒绝 -->
            <onMatch>ACCEPT</onMatch> <!-- 接收当前error级别以上的 -->
            <onMismatch>DENY</onMismatch> <!-- 拒绝当前error级别以下的 -->
        </filter>
    </appender>

    <!-- 异步日志 -->
    <appender name="async" class="ch.qos.logback.classic.AsyncAppender">
        <!-- 指定某个具体的appender -->
        <appender-ref ref="rollFile"/>
    </appender>

    <root level="ALL">
        <appender-ref ref="console"/>
        <appender-ref ref="async"/>
    </root>
</configuration>
```

### 自定义logger对象

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 集中管理属性值 引用这些属性值的格式为：${name} -->
    <!-- 输出到控制台的格式 -->
    <property name="pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} %c [%thread] %-5level %msg%n"/>
    <!-- 输出的日志文件的位置 /logs相对于磁盘根路径，如果是./logs则是相对于当前项目模块根路径 -->
    <property name="log_dir" value="./logs"/>

    <!-- 控制台输出的 appender -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!-- 控制输出流对象 默认是System.out  该为System.err-->
        <target>System.err</target>
        <!-- 日志消息格式配置 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
    </appender>

    <!-- 将日志拆分和规定压缩的 appender对象 -->
    <appender name="rollFile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log_dir}/roll_logback.log</file>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${pattern}</pattern>
        </encoder>
        <!-- 指定拆分规则 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 按照时间和压缩格式声明拆分的文件名 -->
            <!-- %d{yyyy-MM-dd-HH-mm-ss}用于指定按年、月、日、时、分或秒来进行拆分 -->
            <!--<FileNamePattern>${log_dir}/rolling.%d{yyyy-MM-dd-HH-mm-ss}.log%i.gz</FileNamePattern>-->
            <FileNamePattern>${log_dir}/rolling.%d{yyyy-MM-dd-HH-mm-ss}.log%i</FileNamePattern>
            <!-- 按照文件大小拆分 -->
            <maxFileSize>1MB</maxFileSize>
        </rollingPolicy>
        <!-- 日志级别过滤器 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- 日志过滤规则-->
            <level>ERROR</level> <!-- 超过error级别的日志就记录 -->
            <!-- ACCEPT 接收  NEUTRAL 往后走   DENY拒绝 -->
            <onMatch>ACCEPT</onMatch> <!-- 接收当前error级别以上的 -->
            <onMismatch>DENY</onMismatch> <!-- 拒绝当前error级别以下的 -->
        </filter>
    </appender>

    <!-- 异步日志 -->
    <appender name="async" class="ch.qos.logback.classic.AsyncAppender">
        <!-- 指定某个具体的appender -->
        <appender-ref ref="rollFile"/>
    </appender>

    <root level="ALL">
        <appender-ref ref="console"/>
        <appender-ref ref="async"/>
    </root>
    <!-- 自定义logger对象 com.lsl子包下所有类信息都属于自定义logger对象的子实现 -->
    <!-- additivity：该自定义logger对象是否要继承rootLogger -->
    <logger name="com.lsl" level="info" additivity="false">
        <appender-ref ref="console"/>
    </logger>
</configuration>
```

## 配置文件转换器

logback官方有一个转换器，用于将log4j.properties文件转换为logback.xml。

## logback-access的使用

logback-access模块与Servlet容器（如Tomcat和Jetty）集成，以提供HTTP访问日志功能。我们可以使用logback-access模块来替换tomcat的访问日志。  步骤如下：

1. 将logback-access.jar与logback-core.jar复制到`$TOMCAT_HOME/lib/`目录下。

2. 修改`$TOMCAT_HOME/conf/server.xml`文件，Host元素中添加以下（注释掉原来的）：  

   ```xml
   <Valve className="ch.qos.logback.access.tomcat.LogbackValve" />
   ```

3. logback默认会在`$TOMCAT_HOME/conf`下查找文件 logback-access.xml，该文件内容如下：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <configuration>
     <!-- always a good activate OnConsoleStatusListener -->
     <statusListener class="ch.qos.logback.core.status.OnConsoleStatusListener"/> 
     
     <property name="LOG_DIR" value="${catalina.base}/logs"/>
    
     <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
       <file>${LOG_DIR}/access.log</file>
       <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
         <fileNamePattern>access.%d{yyyy-MM-dd}.log.zip</fileNamePattern>
       </rollingPolicy>
    
       <encoder>
   		<!-- 日志消息表达式 -->
   		<pattern>%h %l %u %user %date "%r" %s %b</pattern>
   		<!--<pattern>combined</pattern>-->
       </encoder>
     </appender>
     
     <appender-ref ref="FILE"/>
   </configuration>
   ```

4. 官方配置： https://logback.qos.ch/access.html#configuration  

# Log4j2

Apache Log4j 2是对Log4j的升级版，参考了logback的一些优秀的设计，并且修复了一些问题，因此带来了一些重大的提升，主要有：

1. 异常处理，在logback中，Appender中的异常不会被应用感知到，但是在log4j2中，提供了一些异常处理机制。
2. 性能提升， log4j2相较于log4j 和logback都具有很明显的性能提升，后面会有官方测试的数据。
3. 自动重载配置，参考了logback的设计，当然会提供自动刷新参数配置，最实用的就是我们在生产上可以动态的修改日志的级别而不需要重启应用。
4. 无垃圾机制，log4j2在大部分情况下，都可以使用其设计的一套无垃圾机制，避免频繁的日志收集
   导致的jvm gc。

官网： https://logging.apache.org/log4j/2.x/  

## 入门案例

目前市面上最主流的日志门面就是SLF4J，虽然Log4j2也是日志门面，因为它的日志实现功能非常强大，性能优越。所以大家一般还是将Log4j2看作是日志的实现，Slf4j + Log4j2应该是未来的大势所趋。

1、日志门面与log4j2依赖

```xml
<!-- log4j2 日志门面api -->
<!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.17.1</version>
</dependency>
<!-- log4j2 日志实现 -->
<!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.17.1</version>
</dependency>
```

2、测试

```java
public class Log4j2Test {
    public static final Logger LOGGER = LogManager.getLogger(Log4j2Test.class);

    @Test
    public void test(){
        LOGGER.fatal("fatal");
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.trace("trace");
    }
}
```

实际发中，大多使用slf4j作为日志门面。

```xml
<!-- 日志门面 -->
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.0-alpha5</version>
</dependency>
<!-- 适配器 -->
<!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j-impl -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.17.1</version>
    <scope>test</scope>
</dependency>
<!-- log4j2 日志实现 -->
<!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.17.1</version>
</dependency>
```



## 配置文件

log4j2默认加载classpath下的` log4j2.xml` 文件中的配置。  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- status="warn"：日志框架本身的输出日志的级别
	monitorInterval="5"：自动加载配置文件的时间不低于5秒 
-->
<Configuration status="warn" monitorInterval="5">
    <!-- 集中配置属性值来管理 使用时：${name} 即可引用value -->
    <properties>
        <property name="LOG_HOME">D:/logs</property>
    </properties>
    <!-- 日志处理器 -->
    <Appenders>
        <!-- 控制台输出的 appender -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] [%-5level] %c{36}:%L --- %m%n" />
        </Console>
        <!-- 日志文件输出的 appender -->
        <File name="file" fileName="${LOG_HOME}/myfile.log">
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] %l %c{36} - %m%n" />
        </File>
        <!-- 随机读写流的日志文件输出的的 appender 性能提高 -->
        <RandomAccessFile name="accessFile" fileName="${LOG_HOME}/myAcclog.log">
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] %l %c{36} - %m%n" />
        </RandomAccessFile>
        <!-- 按照一定规则拆分的日志文件的 appender -->
        <!-- filePattern：以天为单位拆分 -->
        <RollingFile name="rollingFile" fileName="${LOG_HOME}/myrollog.log"
                     filePattern="D:/logs/$${date:yyyy-MM-dd}/myrollog-%d{yyyyMM-dd-HH-mm}-%i.log">
            <!-- 日志级别过滤器 高于则放行 低于则拦截 -->
            <ThresholdFilter level="debug" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] %l %c{36} - %msg%n" />
            <Policies>
                <!-- 系统启动就出发拆分规则，生成一个新日志文件 -->
                <OnStartupTriggeringPolicy />
                <!-- 按照文件大小进行拆分 -->
                <SizeBasedTriggeringPolicy size="10 MB" />
                <!-- 按照时间节点进行拆分 规则由filePattern定义 -->
                <TimeBasedTriggeringPolicy />
            </Policies>
            <!-- 同一目录下，输出日志文件个数限定为30个，超过则进行覆盖 -->
            <DefaultRolloverStrategy max="30" />
        </RollingFile>
    </Appenders>
    <!-- logger定义 -->
    <Loggers>
        <!-- 使用rootLogger配置 日志级别是trace -->
        <Root level="trace">
            <!-- 指定日志使用的处理器 -->
            <AppenderRef ref="Console" />
        </Root>
    </Loggers>
</Configuration>
```

## 异步日志

同步日志：

异步日志：

使用log4j2的异步日志还需要以下依赖：

```xml
<!-- https://mvnrepository.com/artifact/com.lmax/disruptor -->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.4</version>
</dependency>
```





## 无垃圾记录



# SpringBoot日志

```xml
<dependency>
    <artifactId>spring-boot-starter-logging</artifactId>
    <groupId>org.springframework.boot</groupId>
</dependency>
```

总结：

1. springboot 底层默认使用logback作为日志实现。
2. 使用了SLF4J作为日志门面。
3. 将JUL也转换成slf4j。
4. 也可以使用log4j2作为日志门面，但是最终也是通过slf4j调用logback。

配置：

日志实现切换：
