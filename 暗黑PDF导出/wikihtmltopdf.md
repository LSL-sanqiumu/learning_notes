Windows下使用wkhtmltopdf实现typora的暗黑主题文件导出为PDF的方案。

官方下载：[wkhtmltopdf](https://wkhtmltopdf.org/downloads.html)

安装在D盘，记住安装所在目录，然后设置环境变量，把bin目录加进path环境变量里。

- 命令行运行`wkhtmltopdf --version`验证是否安装完成并配置好环境变量

把typora导出的HTML文件转为带背景的PDF文件：

1. typora导出的HTML文件放在bin所在目录
2. 运行cmd命令行（如果你安装到的目录是需要管理员权限的，那么运行cmd也得是用管理员身份运行）
3. 命令行里进入到html文件所在目录，然后运行：`wkhtmltopdf --disable-smart-shrinking --page-size A4 -B 0 -L 0 -R 0 -T 0 --zoom 1.1 MySql.html MySql.pdf ` 
4. 输出PDF文件和HTML文件所在目录一置

--disable-smart-shrinking  这个参数一定要加上，加上页面就不缩小了；

其他见参考：[HTML 转 PDF 之 wkhtmltopdf 工具精讲 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/137469275)

