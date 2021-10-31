# Git的安装

下载：[Git - Downloads (git-scm.com)](https://git-scm.com/downloads)，按默认形式安装即可。安装完成后会出现一个命令行窗口，然后进行如下设置：

- `$ git config --global user.name "Your Name"`：设置全局用户名。
- `$ git config --global user.email "email@example.com"`：设置全局email地址。

注意`git config`命令的`--global`，表示你这台机器上所有的Git仓库都会使用这个配置，也可以对某个仓库指定不同的用户名和Email地址。

--------------------------------------------------------------------------------------------------------------------------------------------------------------

单独指定仓库的用户名和email地址的方法（进入要单独设置的仓库后运行下面的命令）：

- $ git config user.name "Your Name"。
- $ git config user.email "email@example.com"。

查看用户名和email地址：

- `$ git config user.name`。
- `$ git config user.email`。

查看配置：

- `$ git config --system --list`：查看系统配置。
- `$ git config --global --list`：查看当前用户(global)配置。
- `$ git config --local --list`：查看仓库配置信息。

# Git的使用

Git是一个由Linux用C语言编写创建的分布式版本控制系统，其只能跟踪文本文件的改动，比如TXT文件，网页，所有的程序代码等等。

版本控制系统：可以告诉你每次的改动，比如在第5行加了一个单词“Linux”，在第8行删了一个单词“Windows”。而图片、视频这些二进制文件，虽然也能由版本控制系统管理，但没法跟踪文件的变化，只能把二进制文件每次改动串起来，也就是只知道图片从100KB改成了120KB，但到底改了啥，版本控制系统不知道，也没法知道。Microsoft的Word格式是二进制格式，因此，版本控制系统是没法跟踪Word文件的改动的，如果要真正使用版本控制系统，就要以纯文本方式编写文件。

编码：因为文本是有编码的，比如中文有常用的GBK编码，日文有Shift_JIS编码，如果没有历史遗留问题，强烈建议使用标准的UTF-8编码，所有语言使用同一种编码，既没有冲突，又被所有平台所支持。不要用Windows下的记事本，容易出问题。

创建与提交：

- `git init [dictionary]`：直接创建目录并初始化为可被Git管理的仓库（也可以gitbash中进入指定目录执行`git init`将其初始化为一个git仓库）；
- `git add 文件(带后缀)`：在仓库目录中找到指定文件并添加至git仓库；
- `git add *`：添加仓库目录所有改动了的内容至git仓库；
- `git commit -m "提交说明"`：将文件添加至git仓库（建议每次提交，提交说明都不要留空）；
- 提交后显示信息说明：
  - `1 file changed`：1个文件被改动（我们新添加的文件）；`2 insertions`：插入了两行内容（文件中所含内容）。

本地仓库与GitHub远程仓库连接：

1. `$ ssh-keygen -t rsa -C "youremail@example.com"`：创建SSH key（默认生成在C盘用户目录下的.ssh目录里）；
2. 在GitHub添加SSH Keys，`id_rsa.pub`文件的内容加入key文本框；
3. 添加好后在GitHub创建好远程仓库；
4. `$ git remote add origin SSH链接地址`：将origin（默认的，可以自己定义）与链接地址绑定；
5. `$ git push -u origin master`





























