

# ES6

[ES6 入门教程 - ECMAScript 6入门 (ruanyifeng.com)](https://es6.ruanyifeng.com/)

# let和const

**let：**块级作用域有效，不允许重复声明，不存在变量提升，会有暂时性死区（这导致typeof不再百分百安全）。

**块级作用域：**

1. 为什么要有块级作用域？为了解决一些不合理场景，例如使用var时存在的内层变量覆盖外层变量的情况（var是可以重复声明的，以最后声明为准，因此会造成覆盖）、用来计数的循环变量泄露为全局变量。
2. `let`实际上为 JavaScript 新增了块级作用域。
3. 块级作用域可以嵌套，每一层块级作用域内都可以使用let声明其他层的同名变量。
4. 块级作用域的出现，实际上使得获得广泛应用的匿名立即执行函数表达式（匿名 IIFE）不再必要了。

**const：**用于声明只读常量，变量指向的那个内存地址所保存的数据不得改动（基本类型是值本身，复合数据类型是是一个指针）。真的想将对象冻结，应该使用`Object.freeze(obj)`方法。

ES6变量声明有6种方式：var、function、let、const、import、class。



# 变量解构赋值

ES6 允许按照一定模式，**从数组和对象中提取值，对变量进行赋值**，这被称为解构赋值（Destructuring）。

1. 从数组中提取值，对变量赋值。

2. 从对象中取值，对变量赋值。

3. 默认值：解构赋值允许指定默认值。



应用场景： 频繁使用对象方法、数组元素，就可以使用解构赋值形式。事实上，只要某种数据结构具有 Iterator 接口，都可以采用数组形式的解构赋值。



# 字符串

## 字符串拓展

1. 只要将码点放入大括号，就能正确解读该字符，不再局限于码点在`\u0000`~`\uFFFF`之间的字符，有了这种表示法之后，JavaScript 共有 6 种方法可以表示一个字符。（`\u{20BB7}`；`'\u{1F680}' === '\uD83D\uDE80'`）

2. ES6 为字符串添加了遍历器接口（详见《Iterator》一章），使得字符串可以被`for...of`循环遍历。（除了遍历字符串，这个遍历器最大的优点是可以识别大于`0xFFFF`的码点，传统的`for`循环无法识别这样的码点。）

3. JavaScript 字符串允许直接输入字符，以及输入字符的转义形式。

4. 对`JSON.stringify()`的改造。

5. **模板字符串**（template string）是增强版的字符串，用反引号（`）标识，特点：

   - 字符串中可以出现换行符，反引号括起来的字符串是咋样输出就是咋样，模板字符串可以嵌套。

   - 可以使用 `${xxx}` 形式引用变量。

   - ```js
     // 模板字符串可以写成函数，在需要时执行	
     let func = (name) => `Hello ${name}!`;
     func('Jack') // "Hello Jack!"
     ```

   - 通过模板字符串，生成正式模板。

   - 标签模板。


应用场景： 当遇到字符串与变量拼接的情况使用模板字符串。

## 字符串新增API

String.fromCodePoint()
String.raw()
实例方法：codePointAt()
实例方法：normalize()
实例方法：includes(), startsWith(), endsWith()
实例方法：repeat()
实例方法：padStart()，padEnd()
实例方法：trimStart()，trimEnd()
实例方法：matchAll()
实例方法：replaceAll()
实例方法：at()

传统上，JavaScript 只有`indexOf`方法，可以用来确定一个字符串是否包含在另一个字符串中。ES6 又提供了三种新方法。

- **includes(xxx)**：返回布尔值，表示是否找到了参数字符串。
- **startsWith(xxx)**：返回布尔值，表示参数字符串是否在原字符串的头部。
- **endsWith(xxx)**：返回布尔值，表示参数字符串是否在原字符串的尾部。
- 三个方法都支持第二个参数，表示开始搜索的位置。
- `repeat(n)`：返回一个新字符串，表示将原字符串重复`n`次。
- `padStart(strLength, str)`用于头部补全，`padEnd(strLength, str)`用于尾部补全。
- trimStart()，trimEnd() ：消除头部、尾部空格，它们返回的都是新字符串，不会修改原始字符串。（浏览器还部署了额外的两个方法，`trimLeft()`是`trimStart()`的别名，`trimRight()`是`trimEnd()`的别名。）



# 函数拓展

## 对象、函数的声明简化

ES6 允许在大括号里面，直接写入变量和函数，作为对象的属性和方法，使得书写更加简洁。

```js
// 原始写法
const isme = {
    myName : "陆拾陆",
    myAge : 22,
    reflect : function(){
    	alert("吾日三省吾身，一心向学~");
	}
}
console.log(isme.myName + isme.myAge);
isme.reflect();
```

```js
// 简化写法
let myName = "陆拾陆";
let myAge = 22;
let reflect1 = function(){
    alert("吾日三省吾身");
}
const isme = {
    myName,
    myAge,
    reflect1,
    reflect2(){
    	alert("一心向学~");
	}
}
console.log(isme.myName + isme.myAge);
isme.reflect1();
isme.reflect2();
```



## 函数参数默认值

ES6，允许给函数的参数赋初始值。

```js
// 参数默认声明，不能再此使用let、const声明，并且默认值是用到再计算
function sum(x,y=3){
    // let y = 4; // 错误
    console.log(x+y);
}
sum(2);
```

```js
// 解析赋值的对象参数，如果不传入参数，那么就不会生成解构变量，如果方法内继续引用就会错误，解决方法是提供函数参数默认值
function connect({host="127.0.0.1", username,password, port}){
console.log(host + ":"+ port +"/" + username + "/" + password);
}
connect({
host: 'lsl.com',
username: 'root',
password: '123456',
port: 8080
});
// 提供函数参数默认值，不传参时默认为空对象，注意此时参数是对象
function connect({host="127.0.0.1", username,password, port}={}){
console.log(host + ":"+ port +"/" + username + "/" + password);
}
```

参数默认值设置的位置一般位于尾部，如果非尾部设置，那么设置了默认值的参数是无法忽略的，必须有值，如果是undefined就会触发默认值，null则不会：

```js
function fun(x,y=2,x){
    return [x,y,z];
}
fun(1,,3); // 报错
fun(1,null,3); // y=null，默认值不会生效
```

指定了默认值以后，函数的`length`属性，将返回没有指定默认值的参数个数。也就是说，指定了默认值后，`length`属性将失真。这是因为`length`属性的含义是，该函数预期传入的参数个数。某个参数指定默认值以后，预期传入的参数个数就不包括这个参数了。同理，后文的 rest 参数也不会计入`length`属性。再者非尾部参数设置默认值，那么设置了默认值的参数的后面的参数也就不会再计入length了。

设置了默认值，函数会在声明初始化时生成一个单独的作用域，也就是说在参数声明处是一个作用域，函数体又是一个作用域。

默认值的应用：将默认值设置为一个函数抛出错误，表示此参数不得省略；将函数参数默认值设置为undefined，表示可忽略。



## rest参数

ES6 引入 rest 参数，用于获取函数的实参，用来代替 arguments。rest参数的作用是：**接收多余的传入函数的参数，并存放在一个数组中**。

- 函数的length属性，不包含rest参数。
- rest参数只能放在形参声明处的最后。

```js
// 使用rest参数的形式如下，theArgs是自定义的，但不能和前面形参名相同
function data(a,...theArgs){   
    console.log(theArgs);   
}
// theArgs 数组接收到了二号、三号、四号
data("一号","二号","三号","四号");
```

Rest参数和arguments对象的区别：

1. rest参数只包括那些没有给出名称的参数，arguments包含所有参数。
2. arguments 对象不是真正的数组，而rest 参数是数组实例，可以直接应用sort, map, forEach, pop等方法。
3. arguments 对象拥有一些自己额外的功能。 

rest参数可以被解构：

```java
function data(...[a,b,c]){
    return a + b + c;
}
alert(data("一"));   // 一undefinedundefined
alert(data("一","二")); // 一二undefined
alert(data("一","二","三")); // 一二三
```

## 严格模式

从 ES5 开始，函数内部可以设定为严格模式。

```js
function doSomething(a, b) {
  'use strict';
  // code
}
```

ES2016 做了一点修改，规定只要函数参数使用了默认值、解构赋值、或者扩展运算符，那么函数内部就不能显式设定为严格模式，否则会报错。

## name属性

函数的`name`属性，返回该函数的函数名。

ES6 对这个属性的行为做出了一些修改。如果将一个匿名函数赋值给一个变量，ES5 的`name`属性，会返回空字符串，而 ES6 的`name`属性会返回实际的函数名。

`Function`构造函数返回的函数实例，`name`属性的值为`anonymous`。



## 箭头函数

ES6允许使用箭头（`=>`）来定义函数，箭头函数提供了一种更加简洁的函数书写方式，箭头函数多用于匿名函数的定义。

```js
// 括号部分：指定传入参数，单个参数、多个参数或无参数，和函数时一致
// 语句部分：使用{}，表示代码块，单语句时可不使用{}—测试表示返回值语句，如果返回对象则需要括号()包住
var fun = 函数的括号部分 => 函数内部语句; 
```

注意：

1. 箭头函数没有自己的`this`对象。（箭头函数没有自己的`this`对象，内部的`this`就是定义时上层作用域中的`this`。）
2. 不可以当作构造函数。
3. 不可以使用`arguments`对象。（以下三个变量在箭头函数之中也是不存在的，指向外层函数的对应变量：`arguments`、`super`、`new.target`。）
4. 不可以使用`yield`命令，因此箭头函数不能用作 Generator 函数。
5. 可以使用解构传参。

## 尾调用优化

尾调用（Tail Call）是函数式编程的一个重要概念，本身非常简单，一句话就能说清楚，就是指**某个函数的最后一步是调用另一个函数**。（注意函数的最后一步操作）

“尾调用优化”（Tail call optimization），即只保留内层函数的调用帧，注意，目前只有 Safari 浏览器支持尾调用优化，Chrome 和 Firefox 都不支持。



## 函数参数尾道号

新的语法允许函数定义和调用时，参数尾部可以有一个逗号。这样的规定也使得，函数参数与数组和对象的尾逗号规则，保持一致了。



## Function.prototype.toString()

ES2019 对函数实例的toString()方法做出了修改。

toString()方法返回函数代码本身，以前会省略注释和空格。而修改后的toString()方法，明确要求返回一模一样的原始代码。

## catch 命令的参数省略

JavaScript 语言的`try...catch`结构，以前明确要求`catch`命令后面必须跟参数，接受`try`代码块抛出的错误对象。ES2019 做出了改变，允许catch语句省略参数。但是，为了保证语法正确，还是必须写。

```js
try {
  // ...
} catch {
  // ...
}
```



# 对象拓展





# 新增对象方法

1. Object.is()
2. Object.assign()
3. Object.getOwnPropertyDescriptors()
4. __proto__属性，Object.setPrototypeOf()，Object.getPrototypeOf()
5. Object.keys()，Object.values()，Object.entries()
   - Object.keys(obj)：返回一个包含对象自身所有可遍历的属性的键名的数组（继承的属性不算）。
   - Object.values(obj)：对象自身可遍历的键值。
   - `Object.entries()`：返回一个数组，成员是参数对象自身的（不含继承的）所有可遍历（enumerable）属性的键值对数组。
6. Object.fromEntries()
7. Object.hasOwn()



# 运算符拓展

 扩展运算符（spread）也就是这三个点`...`，其能将数组转换为以逗号分隔的参数序列；它就像是 rest 参数的逆运算，对数组进行解包，将一个数组转为用逗号分隔的一个参数序列。

```js
let str = [1,2,3];
console.log(str);
console.log(...str);
```

上述输出：

![](img/1.es6_拓展运算符.png)

应用：（数组合并、数组克隆、伪数组转换，对象同理）

```js
<div id = "d1"></div>
<div id = "d2"></div>

//1. 数组的合并 
const arr1 = ['v1','v2'];
const arr2 = ['v2','v4'];
// 传统的合并方式：const arr = arr1.concat(arr2);
const arr = [...arr1, ...arr2];
console.log(arr);
//2. 数组的克隆
const source = ['E','G','M'];
const copy = [...source]; // ['E','G','M']
console.log(copy);
//3. 将伪数组转为真正的数组
const divs = document.querySelectorAll('div');
const divArr = [...d];
console.log(divArr); // arguments
```



# 其他拓展



## 正则的拓展



## 数值的拓展





## 数组的拓展





# Symbol





# Set和Map





# Proxy



# Reflect







# Promise









# Iterator和for...of循环









# Generator







# async函数







# Class









# Module







# 编程风格







# 读懂规格











# 异步遍历器







# ArrayBuffer







# 装饰器（Decorator）















