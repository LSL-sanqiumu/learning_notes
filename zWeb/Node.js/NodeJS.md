# Node.js

# npm包管理

## 安装软件包

`npm` 是 Node.js 标准的软件包管理器。[**Yarn**](https://yarnpkg.com/en/) *是 npm 的一个替代选择。（另一个代码的软件包管理工具）*

1. `npm install`：会读取项目根目录下的`package.json`（包配置文件），安装配置所需的所有依赖，会在 `node_modules` 文件夹（如果尚不存在则会创建）中安装项目所需的所有东西。（如果只是下载了项目还没有 `node_modules` 依赖包，并且想先安装新的版本，可以执行该指令）
2. `npm install <package-name> [--save | --save-dev]`：安装特定的软件包
   - `--save`：安装并添加条目到 `package.json` 文件的 dependencies。
   - `--save-dev`：安装并添加条目到 `package.json` 文件的 devDependencies。
   - `devDependencies` 通常是开发的工具（例如测试的库），而 `dependencies` 则是与生产环境中的应用程序相关。
3. `npm update`：检查所有软件包是否有满足版本限制的更新版本。
   - `npm update <package-name>`：指定单个软件包进行更新。
4. `npm install <package>@<version>`：安装特定版本的软件包。
   - `npm install -g webpack@4.16.4`：全局安装特定版本的软件包。
5. 将所有软件包更新到新的主版本：
   1. 先安装`npm-check-updates` 软件包：`npm install -g npm-check-updates`。
   2. 运行：`ncu -u`；这会升级 `package.json` 文件的 `dependencies` 和 `devDependencies` 中的所有版本，以便 npm 可以安装新的主版本。
   3. 更新：`npm update`。







## 运行任务

运行任务：package.json 文件支持一种用于指定命令行任务（可通过使用以下方式运行）的格式：

```json
// package.json 内scripts用于指定任务名称所对应的命令
{
  "scripts": {
    "start-dev": "node lib/server-development",
    "start": "node lib/server-production",
    "serve": "vue-cli-service serve",
    "build": "vue-cli-service build",
    "lint": "vue-cli-service lint"
  },
}
```

```c#
npm run <task-name>  // 执行，<task-name>是scripts所指定的任务名称
npm run serve // 例子
```

使用此特性运行 Webpack 是很常见的：

```json
{
  "scripts": {
    "watch": "webpack --watch --progress --colors --config webpack.conf.js",
    "dev": "webpack --progress --colors --config webpack.conf.js",
    "prod": "NODE_ENV=production webpack -p --config webpack.conf.js",
  },
}
```

```
npm run watch
npm run dev
npm run prod
```

## 软件包安装目录

1. `npm install <package-name>：`使用npm局部安装软件包，软件包会被安装到当前文件树中的 `node_modules` 文件夹下，并且在当前文件夹中存在的 `package.json` 文件的 `dependencies` 属性中添加软件包条目。
2. `npm install -g <package-name>`：全局安装，不会将软件包安装到本地文件夹下，而是使用全局的位置。查看全局位置`npm root -g`，（在 macOS 或 Linux 上，此位置可能是 `/usr/local/lib/node_modules`。 在 Windows 上，可能是 `C:\Users\YOU\AppData\Roaming\npm\node_modules`。）。

## 使用与执行

使用或执行 npm 安装的软件包：

```js
const _ = require('packagename'); // 将软件包导入程序中
```

## package.json



[package.json 指南 (nodejs.cn)](http://nodejs.cn/learn/the-package-json-guide)



## package-lock.json



[package-lock.json 文件 (nodejs.cn)](http://nodejs.cn/learn/the-package-lock-json-file)



## 查看 npm 包安装的版本

1. `npm list`：查看所有已安装的 npm 软件包（包括它们的依赖包）的最新版本。
2. `npm list -g`：查看全局安装的软件包。
3. `npm list packagename`：指定名称来获取特定软件包的版本。
4. `npm view packagename version`：查看软件包在 npm 仓库上最新的可用版本。



## npm 的语义版本控制

语义版本控制的概念很简单：所有的版本都有 3 个数字：`x.y.z`。

- 第一个数字是主版本。
- 第二个数字是次版本。
- 第三个数字是补丁版本。

当发布新的版本时，不仅仅是随心所欲地增加数字，还要遵循以下规则：

- 当进行不兼容的 API 更改时，则升级主版本。
- 当以向后兼容的方式添加功能时，则升级次版本。
- 当进行向后兼容的缺陷修复时，则升级补丁版本。

该约定在所有编程语言中均被采用，每个 `npm` 软件包都必须遵守该约定，这一点非常重要，因为整个系统都依赖于此。

为什么这么重要？

因为 `npm` 设置了一些规则，可用于在 `package.json` 文件中选择要将软件包更新到的版本（当运行 `npm update` 时）。

规则使用了这些符号：

- `^`
- `~`
- `>`
- `>=`
- `<`
- `<=`
- `=`
- `-`
- `||`

这些规则的详情如下：

- `^`: 只会执行不更改最左边非零数字的更新。 如果写入的是 `^0.13.0`，则当运行 `npm update` 时，可以更新到 `0.13.1`、`0.13.2` 等，但不能更新到 `0.14.0` 或更高版本。 如果写入的是 `^1.13.0`，则当运行 `npm update` 时，可以更新到 `1.13.1`、`1.14.0` 等，但不能更新到 `2.0.0` 或更高版本。
- `~`: 如果写入的是 `〜0.13.0`，则当运行 `npm update` 时，会更新到补丁版本：即 `0.13.1` 可以，但 `0.14.0` 不可以。
- `>`: 接受高于指定版本的任何版本。
- `>=`: 接受等于或高于指定版本的任何版本。
- `<=`: 接受等于或低于指定版本的任何版本。
- `<`: 接受低于指定版本的任何版本。
- `=`: 接受确切的版本。
- `-`: 接受一定范围的版本。例如：`2.1.0 - 2.6.2`。
- `||`: 组合集合。例如 `< 2.1 || > 2.6`。

可以合并其中的一些符号，例如 `1.0.0 || >=1.1.0 <1.2.0`，即使用 1.0.0 或从 1.1.0 开始但低于 1.2.0 的版本。

还有其他的规则：

- 无符号: 仅接受指定的特定版本（例如 `1.2.1`）。
- `latest`: 使用可用的最新版本。



## 卸载 npm 软件包

1. `npm uninstall [-S | -D | --save | --save-dev] <package-name>`：卸载，从项目的根文件夹（包含 `node_modules` 文件夹的文件夹）中运行。（如果使用 `-S` 或 `--save` 标志，则此操作还会移除 `package.json` 文件中的引用。如果程序包是开发依赖项（列出在 `package.json` 文件的 devDependencies 中），则必须使用 `-D` 或 `--save-dev` 标志从文件中移除）。
2. `npm uninstall -g <package-name>`：卸载全局安装的软件包。

可以在系统上的任何位置运行此命令，因为当前所在的文件夹无关紧要。



## 全局与本地软件包

本地和全局的软件包之间的主要区别是：

- **本地的软件包** 安装在运行 `npm install <package-name>` 的目录中，并且放置在此目录下的 `node_modules` 文件夹中。
- **全局的软件包** 放在系统中的单独位置（确切的位置取决于设置），无论在何处运行 `npm install -g <package-name>`。

通常，所有的软件包都应本地安装。一些流行的全局软件包的示例有：

- `npm`
- `create-react-app`
- `vue-cli`
- `grunt-cli`
- `mocha`
- `react-native-cli`
- `gatsby-cli`
- `forever`
- `nodemon`

可能已经在系统上安装了一些全局软件包。 可以通过在命令行上运行以下命令查看：`npm list -g --depth 0`。



## npm 依赖与开发依赖

当使用 `npm install <package-name>` 安装 npm 软件包时，是将其安装为依赖项。该软件包会被自动地列出在 package.json 文件中的 `dependencies` 列表下（在 npm 5 之前：必须手动指定 `--save`）。

当添加了 `-D` 或 `--save-dev` 标志时，则会将其安装为开发依赖项（会被添加到 `devDependencies` 列表）。

开发依赖是仅用于开发的程序包，在生产环境中并不需要。 例如测试的软件包、webpack 或 Babel。当投入生产环境时，如果输入 `npm install` 且该文件夹包含 `package.json` 文件时，则会安装它们，因为 npm 会假定这是开发部署。需要设置 `--production` 标志（`npm install --production`），以避免安装这些开发依赖项。

































