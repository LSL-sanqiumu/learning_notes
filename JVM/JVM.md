# JVM

# 编译JDK

[jdk/jdk12: 06222165c35f / (java.net)](https://hg.openjdk.java.net/jdk/jdk12/file/06222165c35f)，点击browse后就跳转到源码目录，然后选择下载何种压缩包（zip、gz、bz2）。

在CentOS7上编译。

**1.gcc/g++的升级**

由于该系统自带的gcc/g++版本过低（4.8.5），因此需要升级gcc/g++，这里给出一个便捷的方法：

```bash
yum install centos-release-scl
yum install devtoolset-9-gcc* # 注：这里9是gcc的大版本号，可以换成其他版本
scl enable devtoolset-9 bash
mv /usr/bin/gcc /usr/bin/gcc-4.8.5
ln -s /opt/rh/devtoolset-9/usr/bin/gcc /usr/bin/gcc # 创建软连接，替换原来的gcc
mv /usr/bin/g++ /usr/bin/g++-4.8.5
ln -s /opt/rh/devtoolset-9/usr/bin/g++ /usr/bin/g++ # 替换原来的g++
gcc --version
g++ --version
# 注意/opt/rh/devtoolset-9/usr/bin/gcc 和 /opt/rh/devtoolset-9/usr/bin/g++ 
# 可用locate g++ locate gcc 找到gcc和g++所在地址
```

记录一下：g++的安装命令：

```bash
yum install gcc-c++ libstdc++-devel
```

**2.JDK的升级**

首先卸载自带的JDK：

```bash
rpm -qa | grep java # 查看已有jdk版本
yum -y remove java-1.7.0-openjdk* # 删除1.7
yum -y remove java-1.8.0-openjdk* # 删除1.8
java -version # 检查是否删除干净
```

然后，去Oracle官网下载一个JDK11，压缩包版，放入`/opt`目录。

然后安装这个JDK，安装在`/usr/local/java`下放置这个JDK：

```bash
tar -zxvf jdk-11.0.13_linux-x64_bin.tar.gz # 解压
mv /opt/jdk-11.0.13 /usr/local/java # 移动
vim /etc/profile # 修改配置文件，加入的内容如下图，9版本后不要再配CLASSPATH
source /etc/profile
```

![](img/1.环境变量.png)

（这里我持怀疑态度，后面再查查看）这里有一个坑，就是网上的教程一般会把环境变量写在/etc/profile这个文件下，但是这并不是永久有效的，想要让它永久有效，就得写在.bashrc文件中：()

```bash
locate .bashrc # 查看该文件在哪 root目录下有一个 home里面的用户目录下也有一个
vim .bashrc # 插入和上面一样的内容即可
source .bashrc
```

**3.检测配置**

解压放在`/opt`目录的jdk12：

```bash
tar -zxvf jdk12-06222165c35f.tar.gz
```

进入解压目录下，切换为root用户，就可以开始编译了。为保证所需环境配置符合，先检查一下环境是否配置好：

```bash
bash configure
```

执行后出现错误：

![](img/2.Configureerror.png)

g++的原因，找了许久后，发现是我在gcc/g++那一步出错：ln -s /opt/rh/devtoolset-9/usr/bin/g++ /usr/bin/g++ 弄成了 `ln -s /opt/rh/devtoolset-9/root/bin/g++ /usr/bin/g++`，通过`locate g++` 发现不是root，而是usr。修改后再次执行 `bash configure`，终于不再报error，如下：

![](img/3.bashConfigure.png)

**4.编译**

执行：

```bash
make clean
make images
```

然后出现错误：

![](img/4.error.png)

出现这种错误的原因是gcc版本太高，gcc对这些行为进行了警告（（这种特性从gcc8开始，如果你是更早的符合要求的gcc，可能不会出现这样的问题）），为了继续编译，需要重新指定configure选项，忽略这些警告；依次执行以下操作：

```bash
make clean
make dist-clean
bash configure --disable-warnings-as-errors
make images
```

n分钟后，编译成功：

![](img/5.finish.png)

编译的产物在build文件夹下的`linux-x86_64-server-release`文件夹，在这个文件夹中，有名为jdk的文件夹，cd进去，就是我们熟悉的JDK目录了。运行`./bin/java -version`可查看当前JDK版本号。



