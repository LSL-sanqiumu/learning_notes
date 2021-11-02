# spring boot项目导出

数据库配置好，以你云服务器的数据库名称（myblogs）、数据库用户名（username）、密码（password）为准：

![](img-springboot\数据库配置 .png)

pom.xml要另外添加的插件配置：

```xml
<build>
        <finalName>lsl-myblog-1.0</finalName><!--设置生成的jar包的名称，可以不加此行-->
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.lsl.BlogApplication</mainClass> <!--指定主类，按你路径设置-->
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!--忽略Junit代码-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.4.2</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
        </plugins>

    </build>
```

项目编码配置：

```xml
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>
```

主类要继承SpringBootServletInitializer类并重写configure方法：

```java
@SpringBootApplication
public class BlogApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }
}
```

以上配置好后执行maven的clean生命周期，然后再package，在idea的直接点击就好了。

![](img-springboot\maven.png)

导出的jar包在target目录下，可先在本地命令行执行（执行前得先配置好本地的数据库，否则连接不上数据库就执行不成功），cmd进入jar包所在目录后执行`java -jar xxx.jar`，执行好后再浏览器测试一下。

# 云服务器

这里使用的是腾讯云的的个人新用户免费领取体验15天的服务器，注册并实名认证后再领取便可。

![](img-springboot\服务器.png)

领取成功后在自己账户里找到实例，点击id名称进入操作界面，这里只需要重置密码和加载密钥即可：

![](img-springboot\服务器配置.png)

密钥密码用于登陆云服务器。下面的云服务系统配置需要用到。

# Linux系统配置

## 连接服务器

先下载一个软件MobaXterm_Portable，方便操作Linux系统的云服务器，下载压缩包版本的，解压后即可以，不用安装。

下载链接： [MobaXterm Xserver with SSH, telnet, RDP, VNC and X11 - Download (mobatek.net)](https://mobaxterm.mobatek.net/download.html)。

运行解压后得到的MobaXterm_Personal_21.2.exe，运行后界面选择Session弹出Sessionsettings面板，只需要填：1.Remote host，复制你服务器公网IP到这里，2.下面再勾上Use private key再加入密钥的.pem文件（服务器上加载后会自动下载一个.pem文件）即可。

![](img-springboot\X.png)

点击后出现以下界面，第一次可能还需要输入你的服务器登陆密码，login as输入root（这里不做新用户创建的说明），登陆成功如下。

需要注意的是输入密码的时候是不显示任何东西的，只有那一个输入标一直停在那，输入完按回车就好。

![](img-springboot\X-连接服务器.png)

## 服务器配置

登陆好后就可以使用黑框框和左边的目录面板操作配置了。要清楚Linux的一些基本命令：

- `cd xxx`或`cd /usr/locla/src`：进入指定目录
- `mkdir xxx`：创建xxx目录
- 按Ctrl+C就会停止执行运行后不断执行的jar进程

## 安装jdk

先查看已安装的jdk信息：`rpm -qa | grep java`、`yum list installed | grep java`。

如果有安装了的话就卸载jdk：`rpm -e --nodeps java-1.6.0-openjdk-1.6.0.35-1.13.7.1.el6_6.i686`、`yum remove java`	。

使用wget直接下载：

下载jdk的命令：`wget https://download.oracle.com/otn/java/jdk/8u281-b09/89d678f2be164786b292527658ca1605/jdk-8u281-linux-x64.rpm?AuthParam=1621754254_802d88100118779a5f34af51037249b3`。

下载后安装：`rpm -ivh jdk-8u281-linux-x64.rpm`，.rpm就是链接的jdk-8u281-linux-x64.rpm。

安装完成后可通过`which java`查看JDK安装路径，环境变量可不用配置。如果想配置的话，执行如下命令：

```
JAVA_HOME=/usr/java/jdk1.8.0_281-amd64 #java文件目录，可通过which java查看
CLASSPATH=.:$JAVA_HOME/lib
PATH=$JAVA_HOME/bin:$PATH
export JAVA_HOME CLASSPATH PATH
刷新环境变量：source /etc/profile
```

检查：`java  -version`、`avac  -version`

（也可以从官网上下载压缩包后直接拖拽进去，通过MobaXterm_Portable上传。）：

先使用`tar -zxvf `解压tar.gz安装包，再创建目录 /usr/bin/java，然后将jdk文件加移动到该目录，最后设置环境变量。

## 安装MySQL

### 检查：

先检查是否安装MySQL：`rpm -qa | grep mysql`，如果安装了则会显示，然后删除`rpm -e --nodeps 显示的数据库名称`

再次执行查询命令，不显示任何东西；然后查询MySQL目录：`whereis mysql`、`find / -name mysql`，删除查询出来的所有目录`rm -rf /usr/bin/mysql /usr/include/mysql /data/mysql /data/mysql/mysql `，（命令删除内容只是示例）。

![](img-springboot\MySQL.png)

验证是否删除完毕：再次执行`whereis mysql`、`find / -name mysql`，如果前者只显示mysql：，后者不显示如何东西即完成。

### 安装：

下载安装包，要进去你找得到的路径，通过`wget https://dev.mysql.com/get/Downloads/MySQL-5.7/mysql-5.7.24-linux-glibc2.12-x86_64.tar.gz`下载，或者直接拿后面的链接去浏览器访问后就会自动下载，推荐到浏览器下载，这样速度快。

1.下载好压缩包，使用`tar xzvf mysql-5.7.24-linux-glibc2.12-x86_64.tar.gz`进行解压（以上述链接的压缩包为例）；

然后解压完成后，可以看到当前目录下多了一个解压文件，移动该文件到**/usr/local/mysql**目录，执行移动命令为：

`mv mysql-5.7.24-linux-glibc2.12-x86_64 /usr/local/mysql`

2.在**/usr/local/mysql**目录下创建data目录：

`mkdir /usr/local/mysql/data`

3.更改mysql目录下所有的目录及文件夹所属的用户组和用户，以及权限：

```
chown -R mysql:mysql /usr/local/mysql
chmod -R 755 /usr/local/mysql
```

4.编译安装并初始化mysql，**务必记住初始化输出日志末尾的密码（数据库管理员临时密码）**

```
 cd /usr/local/mysql/bin
 ./mysqld --initialize --user=mysql --datadir=/usr/local/mysql/data --basedir=/usr/local/mysql
```

如果出现这个错误：

![](img-springboot\错误2.png)

出现该问题首先检查该链接库文件有没有安装使用 命令进行核查：

```text
[root@localhost bin]# rpm -qa|grep libaio   
[root@localhost bin]# 
```

运行命令后发现系统中无该链接库文件：

```text
[root@localhost bin]#  yum install  libaio-devel.x86_64
```

安装成功后，继续运行数据库的初始化命令，此时可能会出现如下错误：

![](img-springboot\数据库error.png)

执行如下命令后，再次运行数据库的初始化命令：

```text
[root@localhost bin]#  yum -y install numactl
```

5.运行初始化命令成功后，输出日志，**注意一定要记录下末尾的临时登陆密码**：

![](img-springboot\数据库初始化密码.png)

6.编辑配置文件my.cnf，添加配置如下：

```
[root@localhost bin]#  vi /etc/my.cnf

[mysqld]
datadir=/usr/local/mysql/data
port = 3306
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
symbolic-links=0
max_connections=400
innodb_file_per_table=1
#表名大小写不明感，敏感为
lower_case_table_names=1
```

该配置文件要加入到usr/local/mysql目录下，usr目录和root目录是同一级目录（登陆时进入的目录的就是root目录）。

![](img-springboot\usr.png)

7.启动mysql服务器：` /usr/local/mysql/support-files/mysql.server start`，出现如下的则成功。

![](img-springboot\成功.png)

如果出现如下提示信息：

```text
Starting MySQL... ERROR! The server quit without updating PID file
```

就查看是否存在mysql和mysqld的服务，如果存在，则结束进程，再重新执行启动命令

```text
#查询服务，查询出来的前两项就是root PID
ps -ef|grep mysql 
ps -ef|grep mysqld

#结束进程，PIN由上面查询得到
kill -9 PID 

#解决问题后启动服务
 /usr/local/mysql/support-files/mysql.server start
```

8.添加软连接，并重启mysql服务

```
[root@localhost /]#  ln -s /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql 
[root@localhost /]#  ln -s /usr/local/mysql/bin/mysql /usr/bin/mysql
[root@localhost /]#  service mysql restart
```

9、登录mysql，修改密码(此时密码为步骤5生成的临时密码)，数据库密码要和spring boot项目设置的一致：

```mysql
[root@localhost /]#  mysql -u root -p
Enter password:
mysql>set password for root@localhost = password('yourpassword'); 这里是设置密码
```

10.开放远程连接：（MySQL语句末尾要加分号）

```text
mysql>use mysql;
msyql>update user set user.Host='%' where user.User='root';
mysql>flush privileges;
```

11、用`exit;`语句推出MySQL，然后设置开机自动启动，按如下步骤设置：

```text
1、将服务文件拷贝到init.d下，并重命名为mysql
[root@localhost /]# cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysqld
2、赋予可执行权限
[root@localhost /]# chmod +x /etc/init.d/mysqld
3、添加服务
[root@localhost /]# chkconfig --add mysqld
4、显示服务列表
[root@localhost /]# chkconfig --list
```

数据库安装完毕，数据库安装参考如下：

作者：风来啦
链接：https://zhuanlan.zhihu.com/p/87069388
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

# jar包导入和运行

**我的项目的最后操作。**

jar包导入：我把jar包放进/usr/local/src目录下，进入目录直接拉过去就好了；

## 配置数据库和数据库表

根据jar包之前的配置文件指定好的数据库创建数据库：

```MySQL
CREATE DATABASE `test` CHARACTER SET utf8 COLLATE utf8_general_ci; #数据库名称要和项目设置的一样
```

然后先执行一下jar包，初始化数据库，要进入到jar包所在目录执行：`java -jar xxx.jar`。

执行完毕后按CTRL+C结束进程，进入数据库：`mysql -u root -p`，输入密码登陆数据库。

然后创建用户数据，依次执行，分号不能少：

```MySQL
INSERT INTO `myblogs`.`t_user` (`id`) VALUES ('1'); 
UPDATE `myblogs`.`t_user` SET `avatar` = '/images/admin.png' WHERE `id` = '1'; 
UPDATE `myblogs`.`t_user` SET `create_time` = '2021-07-21 21:59:54' WHERE `id` = '1'; 
UPDATE `myblogs`.`t_user` SET `email` = 'xxx@163.com' WHERE `id` = '1'; 
UPDATE `myblogs`.`t_user` SET `nickname` = '潮汐' WHERE `id` = '1'; 
UPDATE `myblogs`.`t_user` SET `password` = 'e10adc3949ba59abbe56e057f20f883e' , `type` = '1' , `update_time` = '2021-07-21 22:00:17' , `username` = 'test' WHERE `id` = '1';  #这里的密码是经MD5加密处理后的
```

创建完毕后就是设置jar包在服务器后台不断运行。

## jar包不停运行

如果直接执行`nohub java -jar xxx.jar &`会报下面的错误：

```
nohup: ignoring input and appending output to ‘nohup.out’
```

原因是因为使用 nohup 会产生日志文件，默认写入到 nohup.out；

（在使用nohup命令的时候，经常由于输出nohup.out的路径没有写入权限，而无法使用nohup。这是可以使用Linux重定向的方法，将nohup.out重定向至一个有写入权限的路径，或者直接扔到/dev/null中。）解决方法就是将 nohup 的日志输出到 /dev/null，这个目录会让所有到它这的信息自动消失：

`nohup ./program >/dev/null 2>/dev/null &）`

执行完毕，退出MobaXterm_Portable，去浏览器访问公网IP:8080。

如果访问不了可能是防火墙的问题，如下操作：CentOS6.9系统下的防火墙

输入service iptables status后出现如下图情况，说明防火墙并没有开启。

![](img-springboot\防火墙.png)



查询Linux系统版本：`cat /etc/issue `;

查看防火墙状态：`service iptables status`，记得在CentOS6.9中是输入iptables，service iptable status 命令并不可行;

关闭防火墙：`service iptables stop`；

永久开启防火墙： chkconfig iptables on；

查看状态：chkconfig --list iptables；

永久关闭防火墙： chkconfig iptables off。

Over