# 谷粒商城项目

自顶向下，从项目中去学习所用到的技术。

## 1.分布式基础-全栈开发篇

基础篇，前端页面制作、前后端分离的后台管理系统的增删改查。

### 环境配置

#### 虚拟机

**虚拟机安装：**

1. 安装VirtualBox、Vagrant（安装完成重启后，任意目录的cmd窗口执行`vagrant` 检测是否安装成功）。
2. 创建VB_Linuxs文件夹，并在该目录下打开cmd窗口。
3. 镜像准备与安装：（可VB_Linuxs目录的cmd窗口中执行`Vagrant init centos/7`，然后执行`vagrant up`启动环境、安装，这样太慢，不建议。）
   1. 进[Vagrant box centos/7 - Vagrant Cloud (vagrantup.com)](https://app.vagrantup.com/centos/boxes/7)点击找到`virtualbox`字样右侧下载按钮下载box文件（可获取下载链接然后通过IDM下载，这样快一些），下载完成后把该文件放进VB_Linuxs文件夹。
   2. 进入VB_Linuxs目录的cmd窗口，执行以下命令：
      1. 添加安装镜像：`vagrant box add MyVBcentos7  ./CentOS-7-x86_64-Vagrant-2004_01.VirtualBox.box`。（`vagrant box add box配置名称 .box文件`）
      2. 执行初始化命令：`vagrant init MyVBcentos7`，然后就会得到一个Vagrantfile核心配置文件，网络等配置都在里面。
      3. 启动虚拟机：`vagrant up`。
4. 安装完成后在cmd执行`vagrant ssh`进入虚拟机，默认的用户名是vagrant，root用户的密码是`vagrant`；`sudo -i`切换到root用户。

**设置虚拟机固定IP：**

1. 先在cmd窗口执行`ipconfig`，查看IP：如图是`192.168.56.1`，因此设置私有IP时也要以前三个开始。

   ![](java_imgs/1.虚拟机IP.png)

2. 打开Vagrantfile文件找到`config.vm.network "private_network", ip: "192.168.33.10"`，然后将后面的ip设置为`192.168.56.10`。

**远程登录设置：**每次都要进入VB_Linuxs目录来使用`vagrant up`启动虚拟机（关机也是进入这个目录然后执行`vagrant  halt`），如果需要使用账号密码连接xshell，则要先开启支持账号密码登录（若只使用vs Box连接则可以忽略）：

1. 打开Vagrantfile文件，修改：

   - 将`config.vm.network "public_network"`此处注释放开。

   - config.vm.provider处修改为以下：

     ```file
         config.vm.provider "virtualbox" do |vb|
             vb.memory = "1024"
             vb.name= "MyVBcentos7"
             vb.cpus= 2
         end
     ```

   - 保存。

2. `vagrant up`、`vagrant ssh`、`sudo -i`，然后查看IP：`ip a`，第3个eth1下的即是，然后可在其他cmd窗口ping一下用于测试是否可连通。

3. `vi /etc/ssh/sshd_config`，将`PasswordAuthentication  no`的no改为yes，输入passwd可修改root用户密码。

4. `systemctl restart sshd`，重启即可。

#### Docker安装及MySQL等

Docker安装见Docked.md。

**Docker中安装MySQL：**

1、`docker pull mysql:5.7`

2、`docker run -p 3306:3306 --name mysql -v /mydata/mysql/log:/var/log/mysql -v /mydata/mysql/data:/var/lib/mysql -v /mydata/mysql/conf:/etc/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7`

3、MySQL配置——my.cnf：（放于宿主主机的mydata/mysql/conf目录下）

```
[client]
default-character-set=utf8
[mysql]
default-character-set=utf8
[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci' 
init_connect='SET NAMES utf8' 
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve
```

4、重启MySQL容器。

5、`docker exec -it mysql /bin/bash`，进入容器文件系统登陆MySQL。

6、`grant all privileges on *.* to 'root'@'%' identified by 'root' with grant option; flush privileges;`：设置远程登陆。

**Docker中安装Redis：**

1、`docker pull redis`。

2、`mkdir -p /mydata/redis/conf`，将redis的配置文件redis.conf放入里面，修改配置文件。

3、`docker run -p 6379:6379 --name redis -v /mydata/redis/data:/data -v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf -d redis redis-server /etc/redis/redis.conf`。

4、`docker exec -it redis redis-cli`。

#### 开发工具及环境

JDK：1.8版本及以上。

Maven：3.6.1。

IDEA插件：MyBatisX、Lombok。

VScode插件：Auto CloseTag、Auto Rename Tag、ESLint、HTMLCSS Support、HTML Snippets、JavaScript(ES6)、LiverServer、OpenInBrowser、Vetur。

HTML Snippets

1. Vetur —— 语法高亮、智能感知、Emmet 等 包含格式化功能， Alt+Shift+F （格式化全文），Ctrl+K Ctrl+F（格式化选中代码，两个 Ctrl 需要同时按着）
2. EsLint —— 语法纠错 
3. Auto Close Tag —— 自动闭合 HTML/XML 标签 
4. Auto Rename Tag —— 自动完成另一侧标签的同步修改 
5. JavaScript(ES6) code snippets — — ES6 语 法 智 能 提 示 以 及 快 速 输 入 ， 除 js 外 还 支 持.ts，.jsx，.tsx，.html，.vue，省去了配置其支持各种包含 js 代码文件的时间 
6. HTML CSS Support —— 让 html 标签上写 class 智能提示当前项目所支持的样式 
7. HTML Snippets —— html 快速自动补全 
8. Open in browser —— 浏览器快速打开
9. Live Server —— 以内嵌服务器方式打开 
10. Chinese (Simplified) Language Pack for Visual Studio Code —— 中文语言包

Git仓库：gulimall。

## 微服务搭建

1、在gulimall主目录里创建这些Maven模块——包为com.learn.gulimall.xxx (ware、orderd、...)，模块名为——（gulimall-product（商品服务）、gulimall-order（订单服务）、gulimall-ware（仓储服务）、gulimall-coupon（优惠卷服务）、gulimall-member（会员服务）），然后通过SpringBoot的向导来创建，然后修改各项目的pom.xml文件：（需要修改artifactId、name、description）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.13.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.learn.gulimall</groupId>
    <artifactId>gulimall-product</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>gulimall-product</name>
    <description>谷粒商城-商品服务</description>
    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR3</spring-cloud.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
            <version>2.2.10.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.2.13.RELEASE</version>
            </plugin>
        </plugins>
    </build>

</project>
```

2、将gulimall设置为总项目——添加一个pom.xml，如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.learn.gulimall</groupId>
    <artifactId>gulimall</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>gulimall</name>
    <description>聚合</description>
    <packaging>pom</packaging>
    <modules>
        <module>gulimall-product</module>
        <module>gulimall-coupon</module>
        <module>gulimall-member</module>
        <module>gulimall-order</module>
        <module>gulimall-ware</module>
    </modules>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR3</spring-cloud.version>
    </properties>


</project>
```

3、配置总项目目录gulimall的.gitignore，加上以下：

```
**/mvnw
**/mvnw.cmd
**/.mvn
**/target/
.idea
**/.gitignore
```

4、提交并push。

## 数据库初始化

数据库：数据库使用utf8mb4

![](java_imgs/3.database.png)

数据库表文件，依次执行进相应数据库：

![](java_imgs/2.db.png)



## 人人开源后台管理系统搭建

### renren-fast

[renren-fast: renren-fast是一个轻量级的Spring Boot2.1快速开发平台，其设计目标是开发迅速、学习简单、轻量级、易扩展；使用Spring Boot、Shiro、MyBatis、Redis、Bootstrap、Vue2.x等框架，包含：管理员列表、角色管理、菜单管理、定时任务、参数管理、代码生成器、日志管理、云存储、API模块(APP接口开发利器)、前后端分离等。 (gitee.com)](https://gitee.com/renrenio/renren-fast)

拉取后删除.git文件后放入gulimall总项目里，并在gulimall的pom.xml文件的module标签里加入。

如果pom.xml文件报红，基本都是插件那块报错，那么就去Maven仓库把那些插件的依赖找到并加入即可。经测试，加上以下两个即可：

```xml
<dependency>
   <groupId>org.codehaus.mojo</groupId>
   <artifactId>wagon-maven-plugin</artifactId>
   <version>1.0</version>
</dependency>
<dependency>
   <groupId>com.spotify</groupId>
   <artifactId>docker-maven-plugin</artifactId>
   <version>0.4.14</version>
</dependency>
```

然后再去application-dev.xml文件里配置好MySQL数据库即可。

启动并访问`localhost:8080/renren-fast/`测试，返回json字符串即可。

### renren-vue

1、拉取项目到本地

[renren-fast-vue: renren-fast-vue基于vue、element-ui构建开发，实现renren-fast后台管理前端功能，提供一套更优的前端解决方案。 (gitee.com)](https://gitee.com/renrenio/renren-fast-vue)



2、要先下载Node.js 10.16.3版本。再在cmd窗口执行：`node config set  registry https://registry.npm.taobao.org/`。在Node.js的安装目录里找到Node.js的npmrc文件并修改为如下：

```
prefix=${APPDATA}\npm
registry = https://registry.npm.taobao.org/
```

3、VScode以管理员模式启动并打开拉取到的项目，然后VScode里打开终端，并执行`npm install`。

4、如果报错node-sass4.9.0相关的，那就再运行`npm install node-sass@npm:sass --ignore-scripts`，运行完成之后直接`npm run dev`，成功！如果报其它错误，仔细看报错信息，然后去网上查找原因与解决方案。

6、运行成功之后跳转到页面，然后可与renren-fast连调：![](java_imgs/4.renren.png)

登录账户：admin；密码：admin。







