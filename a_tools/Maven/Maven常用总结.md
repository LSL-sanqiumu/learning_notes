# 下载

官网：http://maven.apache.org

# 环境变量配置

- 新建环境变量：`MAVEN_HOME`，变量值为解压后的`maven文件夹目录地址`（使用绝对路径）
- 在Path环境变量里新建一个变量值：`%MAVEN_HOME%\bin`

配置完成后检测：

- 打开命令行输入`mvn -version`，如下显示则完成，若没有完成则检测上面变量路径

# settings.xml配置

settings.xml里配置：

- 设置本地仓库：<localRepository>D:\Environment\.m2\repository</localRepository>
- 镜像配置：
  - 使用阿里的镜像：
  
    ```
    <mirror>
          <id>nexus-aliyun</id>
          <mirrorOf>*,!jeecg,!jeecg-snapshots</mirrorOf>
          <name>Nexus aliyun</name>
          <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </mirror>
    ```
  
    
  
  - 使用私服：
  
    ```
    <mirror>
          	<id>nexus-aliyun</id>
            <name>Nexus aliyun</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public</url>
            <mirrorOf>*</mirrorOf>
    </mirror>
    ```

# pom.xml的使用

引入依赖

引入插件

设置

















