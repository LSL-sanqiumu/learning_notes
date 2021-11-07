# 基本概念

**MQ（message queue）：**

MQ本质是个遵循FIFO原则的队列(First Input First Output，先入先出)，只不过队列中的内容是消息（message），还是一种跨进程的通信机制，用于上下游传递消息。

**流量消峰：**

对特殊时期时的大量请求进行分时间段处理，接收请求但把处理的时间往后延时。

**应用解耦:**

一个应用可能由不同的系统模块相互配合来完成需求的实现，应用解耦就是将模块中的各系统的依赖降低，使得某一系统出现故障而不影响另一系统的功能，实际就是将应用中模块要处理的信息缓存进消息队列，使得模块之间的依赖降低，提升系统可用性。

**异步处理：**

同步和异步？

# MQ分类

## ActiveMQ

优点：单机吞吐量万级，时效性 ms 级，可用性高，基于主从架构实现高可用性，消息可靠性较低的概率丢失数据。

缺点:官方社区现在对 ActiveMQ 5.x 维护越来越少，高吞吐量场景较少使用。

使用场景：



## Kafka

为大数据而生的消息中间件，以其百万级 TPS 的吞吐量名声大噪，迅速成为大数据领域的宠儿，在数据采集、传输、存储的过程中发挥
着举足轻重的作用。

优点: 性能卓越，单机写入 TPS 约在百万条/秒，最大的优点，就是吞吐量高。时效性 ms 级可用性非常高，kafka 是分布式的，一个数据多个副本，少数机器宕机，不会丢失数据，不会导致不可用,消费者采用 Pull 方式获取消息, 消息有序, 通过控制能够保证所有消息被消费且仅被消费一次;有优秀的第三方KafkaWeb 管理界面 Kafka-Manager；在日志领域比较成熟，被多家公司和多个开源项目使用；功能支持： 功能较为简单，主要支持简单的 MQ 功能，在大数据领域的实时计算以及日志采集被大规模使用。

缺点：Kafka 单机超过 64 个队列/分区，Load 会发生明显的飙高现象，队列越多，load 越高，发送消息响应时间变长，使用短轮询方式，实时性取决于轮询间隔时间，消费失败不支持重试；支持消息顺序，但是一台代理宕机后，就会产生消息乱序，社区更新较慢。

适用场景：一开始的目的就是用于日志收集和传输，适合产生大量数据的互联网服务的数据收集业务。

## RocketMQ

 RocketMQ 出自阿里巴巴的开源产品，用 Java 语言实现，在设计时参考了 Kafka，并做出了自己的一些改进。被阿里巴巴广泛应用在订单，交易，充值，流计算，消息推送，日志流式处理，binglog 分发等场景。

优点:单机吞吐量十万级,可用性非常高，分布式架构,消息可以做到 0 丢失,MQ 功能较为完善，还是分布式的，扩展性好,支持 10 亿级别的消息堆积，不会因为堆积导致性能下降,源码是 java 我们可以自己阅读源码，定制自己公司的 MQ。

 缺点：支持的客户端语言不多，目前是 java 及 c++，其中 c++不成熟；社区活跃度一般,没有在MQ核心中去实现 JMS 等接口,有些系统要迁移需要修改大量代码。

适用场景：为金融互联网领域、可靠性要求很高的场景，电商。

## RabbitMQ

2007 年发布，是一个在AMQP(高级消息队列协议)基础上完成的，可复用的企业消息系统，是当前最主流的消息中间件之一。

优点：由于 erlang 语言的高并发特性，性能较好；吞吐量到万级，MQ 功能比较完备,健壮、稳定、易用、跨平台、支持多种语言 如：Python、Ruby、.NET、Java、JMS、C、PHP、ActionScript、XMPP、STOMP等，支持 AJAX 文档齐全；开源提供的管理界面非常棒，用起来很好用,社区活跃度高；更新频率相当高。

缺点：商业版需要收费,学习成本较高。

适用场景：数据量不大，中小型公司。

# RabbitMQ

## 概念

RabbitMQ，一个消息中间件，负责消息数据的接收、存储和转发，不浮躁处理数据。

官方：[Messaging that just works — RabbitMQ](https://www.rabbitmq.com/)。

四大核心概念：

- 生产者：产生数据发送消息的程序是生产者。
- 交换机：负责接收消息并把消息推送进队列。
- 队列：本质是大的消息缓冲区。
- 消费者：

## 安装

erlang下载：[el/8/erlang-24.1-1.el8.x86_64.rpm - rabbitmq/erlang · packagecloud](https://packagecloud.io/rabbitmq/erlang/packages/el/8/erlang-24.1-1.el8.x86_64.rpm)

rabbitMQ下载，下载rpm包：[Release RabbitMQ 3.9.8 · rabbitmq/rabbitmq-server (github.com)](https://github.com/rabbitmq/rabbitmq-server/releases/tag/v3.9.8)

**Erlang环境安装：（建议rpm安装，不过容易出错）**

1. `yum -y install make gcc gcc-c++ kernel-devel m4 ncurses-devel openssl-devel`：安装环境准备；
2. `yum -y install ncurses-devel`：安装ncurses；
3. `tar -zxvf otp_src_18.2.1.tar.gz `：解压（http://erlang.org/download/otp_src_23.2.tar.gz：下载安装压缩包）；
4. `cd otp_src_24.1`：进入目录；
5. `./configure --prefix=/usr/local/erlang --with-ssl --enable-threads --enable-smp-support --enable-kernel-poll --enable-hipe --without-javac`：执行该命令设定安装规则；（我是直接`./configure`）
6. `make && make install`：安装（我是直接`make install`）；
7. `erl`：检查是否安装成功。（` erl -eval 'erlang:display(erlang:system_info(otp_release)), halt().'  -noshell`可查出版本号）。

**rabbitMQ安装：**

- `rpm -ivh --nodeps rabbitmq-server-3.9.8-1.el7.noarch.rpm`。

**启动服务：**

- `systemctl list-unit-files | grep rabbitmq-server`：查看服务是否开机自启动；
- `/sbin/service rabbitmq-server start`：启动服务；（启动过程要一点时间）
- `/sbin/service rabbitmq-server status`：查看服务状态；
- `/sbin/service rabbitmq-server stop`：停止服务。

**开启 web 管理插件：**

- `rabbitmq-plugins enable rabbitmq_management`；（开启服务也能安装的嘛）
- 访问：[RabbitMQ Management地址](http://192.168.137.129:15672/)；（15672端口），默认用户名密码都是`guest`，登陆不上是因为这个用户没有设置权限。

**新建用户与权限设置：**

- `rabbitmqctl add_user admin 123`：创建用户admin并设置密码为123；
- `rabbitmqctl set_user_tags admin administrator`：设置用户角色；
- `rabbitmqctl set_permissions -p "/" admin ".*" ".*" ".*"`：设置admin用户权限（拥有`/`根目录下所有文件的读、写、配置权）；
  - 权限命令格式：`set_permissions [-p <vhostpath>] <user> <conf> <write> <read>`）。
- `rabbitmqctl list_users`：查询用户。

else：

- `rabbitmqctl stop_app`：关闭；
- `rabbitmqctl reset`：清除；
- `rabbitmqctl start_app`：重启。

# 简单队列

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/com.rabbitmq/amqp-client -->
    <dependency>
        <groupId>com.rabbitmq</groupId>
        <artifactId>amqp-client</artifactId>
        <version>5.13.1</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.11.0</version>
    </dependency>
</dependencies>
```





























