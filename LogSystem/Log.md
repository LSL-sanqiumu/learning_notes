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

## 组件



## 配置





