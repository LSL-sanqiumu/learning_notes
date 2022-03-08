## Tomcat

Tomcat服务器是一个实现了Servlet规范和JSP规范的容器，启用Tomcat服务器后可以通过URL去访问webapps目录下的内容，也可以这么说，例如`http://localhost:8081/xx/xx.html`，可以将`http://localhost:8081/`看作是webapps目录，访问服务器（物理服务器）上的资源可以说就是访问主机里的web container里面的web应用，Tomcat服务器就是一个web容器。

官网：[Apache Tomcat® - Welcome!](https://tomcat.apache.org/)

**Tomcat目录结构：**

1. bin：启动关闭的基本文件，命令目录；
2. conf：配置目录；
3. lib：依赖的包、核心库，库目录；
4. logs：日志；
5. webapps：放web应用（存放网站）（B端的URL的`http://localhost:8081/`去除后的剩余部分，就是相对于webapps目录的相对URL）；
6. work：存放jsp被访问后生成对应的server文件和 .class文件，jsp生成文件目录。

- 启动：运行bin文件夹里的startup.bat文件，浏览器访问localhost:8080即可进入一个webapp
- 关闭：shutdown.bat

**配置Tomcat：**（在conf文件夹的配置文件server.xml里配置）

1. 修改端口：

  - 默认端口8080，（http：80；mysql：3306；https：443）

  - ```xml
    	<<Connector port="8080" protocol="HTTP/1.1"
                   connectionTimeout="20000"
                   redirectPort="8443" />
    ```

2. 配置主机名称：

  - 默认主机名：localhost；默认网站应用存放位置：webapps；

  - 修改主机名：

     - 先找到如下配置项，然后修改name。

     ```xml
     <!-- 主机配置 -->
     <Host name="localhost"  appBase="webapps"
                unpackWARs="true" autoDeploy="true">
     ```

     - 在C:\Windows\System32\drivers\etc的hosts文件里添加修改后的name进去；

环境变量配置：可选项

**网站访问？**

1. 输入域名，回车进行访问
2. 先在本机里的C:\Windows\System32\drivers\etc里的hosts文件查找是否有这个域名的映射
   - 有的话就直接返回对应的ip地址，在这个地址中有我们需要访问的web程序，可以直接访问
   - 没有的话就去DNS服务器找，找到的话就返回，找不到就返回失败

网站发布：

1. 所写网站放到服务器(Tomcat)指定的webapps应用文件夹里，然后就可以访问了。

2. 所写的webapp应用应有的目录结构，servlet规范：

  - ```TXT
    webapps
    	|---ROOT
    	|---xxx：网站目录名
    		|---WEB-INF 
    			|---classes：java程序
    			|---lib：web应用所依赖的jar包
    			|---web.xml：网站配置文件
    		|---index.html：默认首页
    		|---static
    			|---css
    				|---style.css
    			|---js
    			|---img
    		|---。。。。。。
    ```
