# Docker

是什么？一种虚拟化容器技术，为了解决运行环境‘配置问题。

能干嘛？一次构建，到处运行。

去哪下？

怎么玩？

docker技术——镜像技术（一次镜像，到处运行——将环境、配置与应用程序一起打包）、docker引擎。

docker三件套：

1. 镜像（image）：只读的容器模板，用于创建一个或多个容器。
2. 容器（container）：镜像实例，看看作是一个简易版linux环境。
3. 仓库（repository）：集中存放镜像的地方，官方的仓库是Docker Hub。

# 安装

**安装前准备：**

1. 确认Linux版本是CentOS7及以上版本：`cat /etc/rehat-release`。

2. 卸载系统上的旧版本：

   ```
   sudo yum remove docker \
                     docker-client \
                     docker-client-latest \
                     docker-common \
                     docker-latest \
                     docker-latest-logrotate \
                     docker-logrotate \
                     docker-engine
   ```

3. 安装gcc、gcc++：`yum -y install gcc`、`yum -y install gcc-c++`。

**安装：**

1. 通过仓库[Docker’s repositories](https://docs.docker.com/engine/install/centos/#install-using-the-repository) 来下载，便于更新和升级，推荐的方法。步骤如下：

   1. 建立stable仓库：

      ```scss
      sudo yum install -y yum-utils
      
      # 设置为这个就好
      sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
      # 设置了国外的仓库，下载镜像会很慢，建议不要执行该操作
      sudo yum-config-manager \
          --add-repo \
          https://download.docker.com/linux/centos/docker-ce.repo
      ```

   2. 更新yum软件包索引：`yum makecache fast`

   3. 安装最新版本的Docker Engine：`sudo yum install docker-ce docker-ce-cli containerd.io docker-compose-plugin`；以下为下载其他版本的方法：

      ```scss
      # 列出各版本，用于选择性地安装需要的版本
      yum list docker-ce --showduplicates | sort -r
      # 选择性安装版本
      sudo yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io docker-compose-plugin
      ```

   4. 开启Docker：`sudo systemctl start docker`。

   5. 运行一个程序，测试是否安装成功：`sudo docker run hello-world`。（或者`docker version`）

   6. Docker卸载：

      1. `systemctl stop docker`。
      2. `sudo yum remove docker-ce docker-ce-cli containerd.io docker-compose-plugin`。
      3. `sudo rm -rf /var/lib/docker`。
      4. `sudo rm -rf /var/lib/containerd`。

2. 通过RPM package 下载安装。

3. 测试或开发环境，一些用户通过自动化的脚步安装。

**阿里云镜像加速：**阿里云的容器镜像服务里有镜像加速器，按照里面的操作文档操作。

# Docker常用命令

## 帮助启动类命令

![](img/1.docker启动.png)



## 镜像命令

1. 列出本地主机上的镜像：**`docker images [-a\-q]`**，可选项-a表示列出本地所有镜像，包含历史，-q则是只显示ID。
   - REPOSITORY：表示镜像的仓库源      TAG：镜像的标签版本号   IMAGE ID：镜像ID     CREATED：镜像创建时间    SIZE：镜像大小
   - 同一仓库源可以有多个 TAG版本，代表这个仓库源的不同个版本，我们使用 `REPOSITORY:TAG` 来定义不同的镜像。如果你不指定一个镜像的版本标签，例如你只使用 ubuntu，docker 将默认使用 `ubuntu:latest`做镜像标签。
2. 查找有没有hello-word这个镜像：**`docker search [选项] hello-word`**。
   - **`docker search --limit 5 镜像名称`**——列出五个，`--limit`选项——只列出N个镜像，默认25个。
3. 下载镜像：**`docker pull 镜像名称[:TAG]`**，如果没有加后面的可选项，则默认下载最新的版本。
4. 查看镜像、容器、数据卷各自所占的空间：**`docker system df`**。
5. 删除镜像：
   1. **`docker rmi 某个XXX镜像名字ID`**：删除一个镜像。
   2. **`docker rmi 镜像名称1:TAG1 镜像名称2:TAG2 ...`**：删除多个。
   3. **`docker rmi -f $(docker images -qa)`**：强置删除全部镜像。

面试题：docker虚悬镜像是什么？仓库名、标签都是`<none>`的镜像，俗称虚悬镜像——dangling image。

![](img/2.虚悬镜像.png)



## 容器命令

**1.新建并启动容器：**`docker run [OPTIONS] image [COMMAND] [ARG...]`

常用OPTION说明：

- `--name="容器新名字"`：为容器指定一个名称；`-d`:：后台运行容器并返回容器ID，也即启动守护式容器(后台运行)。

- `-i`：以交互模式运行容器，通常与 -t 同时使用；`-t`：为容器重新分配一个伪输入终端；通常与 -i 同时使用，也就是启动交互式容器（前台有伪终端，等待交互）。`docker run -it centos /bin/bash`：使用镜像`centos:latest`以交互模式启动一个容器，在容器内执行`/bin/bash`命令。

- `-P`：随机端口映射；`-p`：指定端口映射。（设置宿主机端口与docker内容器暴露的端口以确定访问docker内哪个容器）

  ![](img/3.容器启动参数.png)

交互式容器：以Ubuntu为例：`docker pull ubuntu`，然后`docker run ubuntu`，此时不会有该容器——Ubuntu系统的操作终端；而`docker run -it ubuntu /bin/bash`，此时就会以交互模式启动该容器并在该容器内执行一个`/bin/bash`指令，从而可以进入到一个终端用来操作该微小Ubuntu系统，如下图：

![](img/4.交互式容器.png)

`docker run -it --name=myUbuntu ubuntu bash`：使用`--name`指定系统名字。

**2.罗列正在运行的容器：**`docker ps [选项]`

OPTIONS说明（常用）：

1. `-a`：列出当前所有正在运行的容器和历史上运行过的。
2. `-l `：显示最近创建的容器。
3. `-n N`：显示最近N个创建的容器。
4. `-q `：静默模式，只显示容器编号。

**3.退出容器的两种方式：**

1. `exit`：run容器后，执行exit退出时容器会停止。
2. `ctrl + p + q`：run容器后，同时执行这三个键退出容器时容器不会停止。

**4.启动已经停止的容器：**`docker start 容器ID或容器名称`

**5.重启容器：**`docker restart 容器ID或容器名称`

**6.停止容器：**`docker stop 容器ID或容器名称`

**7.强制停止容器：**`docker kill 容器ID或容器名称`

**8.删除已经停止的容器：**`docker rm 容器ID`（一次性删除多个：`docker rm -f $(docker ps -a -q)`或`docker ps -a -q | xargs docker rm`）

**9.非常重要：**

1. 要注意的是Docker容器后台运行，必须要有一个前台进程，容器运行命令如果不是一直挂起的命令那就会自动退出，这是因为Docker的机制问题。因此启动守护式容器（后台运行容器）的最佳解决方案就是将要运行的程序以前台进程的形式运行（常见的是命令行模式）

   ```
   示例：
   docker run -it redis:6.0.8
   docker run -d redis:6.0.8
   ```

2. 查看docker容器日志：`docker logs 容器ID`。

3. 查看容器内运行程序：`docker top 容器ID`。

4. 查看容器内部细节：`docker inspect 容器ID`。

5. 重新进入正在运行的容器并以命令行交互：

   1. `docker exec -it 容器ID 交互/bin/bash等`。
   2. `docker attach 容器ID`。
   3. 两个命令的区别：attach直接进入容器启动命令的终端，不会启动新的线程，使用exit退出时会导致容器停止；exec是在容器中打开新的终端，并且可以启动新的线程，使用exit退出不会导致容器停止。

**10：从容器内拷贝文件到主机：**`docker cp 容器ID:容器内路径 目的主机路径`

**11：导入和导出容器：**

1. `docker export 容器ID > 文件名.tar或文件名.tar.gz `：导出为镜像，以便恢复。
2. `cat 文件名.tar | docker import - 镜像用户/镜像名:镜像版本号`：导入-恢复。











