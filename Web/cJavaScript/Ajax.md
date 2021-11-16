# Ajax

## 概述

Ajax（Asynchronous JavaScript and XML，异步JavaScript与XML技术），是一种有效利用JavaScript和DOM（Document Object Model，文档对象模型）的操作，以达到局部web页面替换加载的异步通信手段。利用ajax实时地从服务器获取内容（**无刷新获取数据**）。

XML，可拓展标记语言，全都是自定义标签，被设计来传输和存储数据。目前在ajax中已经被json取代。

ajax特点：可以无需刷新页面而与服务器端进行通信；允许你根据用户事件来更新部分页面内容。

AJAX 的缺点：没有浏览历史，不能回退；存在跨域问题(必须同源，不能跨页面) ； SEO 不友好。

核心对象：XMLHttpRequest，AJAX 的所有操作都是通过该对象进行的。

## Node.js安装

[Node.js 中文网 (nodejs.cn)](http://nodejs.cn/)

安装好后：`node -v`检测。

vscode终端执行`npm init --yes`进行初始化。

## Express的安装

安装了node.js后，在VScode的终端执行`npm i express`下载安装express（一个JavaScript运行时）。

## Ajax的使用

ajax操作的四步骤：

1. 创建XMLHttpRequest对象（AJAX的所有操作都经过XMLHttpRequest）；

   ```js
   const xhr = new XMLHttpRequest();
   ```

2. 设置请求信息；

   ```js
   xhr.open('POST','http://127.0.0.1:8000/server');
   // 请求头信息设置，可以不设置，也可以设置自定义的请求头，Content-Type是预定义的
   xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
   // 如果是自定义的要在后端设置 response.setHeader('Access-Control-Allow-Headers','*')，并把接收请求的类型设置为所有
   ```

3. 发送请求；

   ```js
   xhr.send();
   // xhr.send('向http://127.0.0.1:8000/server发送的信息');
   ```

4. 接收响应。

   ```js
   // 4.事件绑定 处理服务端返回的结果
   // onreadystatechange：on 当...的时候，
   // readystate xhr对象的属性，表示状态，有五个值（0表示未初始化 1表示open()已使用 2：send()方法以使用 3：服务端返回结果 4：服务端返回的所有结果）
   // change 改变
   // onreadystatechange会触发四次：1 2 3 4四次
   xhr.onreadystatechange = function(){
   // 4时服务端已经返回所有结果，可以进行处理
   if(xhr.readyState === 4) {
   // 判断响应状态 200 404 403 401 505
   	if(xhr.status >= 200 && xhr.status < 300) {
   // 处理结果 行 头 空行 体
   // console.log(xhr.status); // 状态码
   // console.log(xhr.statusText);// 状态字符串
   // console.log(xhr.getAllResponseHeaders()); // 所有响应头
   // console.log(xhr.response); // 响应体
   // 设置result的显示文本
   	result.innerHTML = xhr.response;
       }else {
   
   		}
   	}
   }
   ```

   

## JSON响应与处理

服务器端响应回json格式的字符串，可以手动转换或自动转换。

```js
// 手动转换 将JSON字符串转换为对象，再取值
let data = JSON.parse(xhr.response);
result.innerHTML = data.name;
// 自动转换 设置响应体数据类型是json，然后就可以自动转换
xhr.responseType = 'json';
result.innerHTML = xhr.response.name;
```

## IE缓存问题

IE会缓存服务器端响应回来的数据，也是说此时ajax不会及时地得到服务器响应的最新数据，触发相同的ajax请求时，走的是IE浏览器的缓存而不是服务器。解决：添加时间戳

```js
xhr.open('GET','http://127.0.0.1:8000/ie?t='+Date.now());
```

## 超时与网络异常

```js
xhr.timeout = 2000; // ms数，如果超过这个时间会取消请求
// 超时回调
xhr.ontimeout = function(){
    alert("网络异常，请稍后重试！！！");
}
// 网络异常回调
xhr.onerror = function(){
    alert("你的网络似乎出现了一些问题！！！");
}
```

## ajax请求取消

```js
const btns = document.querySelectorAll('button');
let x = null;
btns[0].onclick = function() {
    x = new XMLHttpRequest();
    x.open('','');
    s.send();
}
btns[1].onclick =  function(){ // 事件触发函数，当触发时就运行x.abort();取消ajax请求
    x.abort();
}
```

## 重复请求问题

为防止用户多次重复发起一个ajax请求，减轻服务器压力，可以这样设置（当再次发起同一个请求时取消前面的相同的请求）：

```js
const btns = document.querySelectorAll('button');
// 是否正在发送ajax请求
let isSending = false;
let x = null;
btns[0].onclick = function() {
    // p如果还是发起这个请求，则取消
    if(isSending) x.abort(); 
    // 创建新的请求
    x = new XMLHttpRequest();
    isSending = true;
    x.open('','');
    s.send();
    // 请求响应完（请求已经完成并结束）设置回false
    x.onreadystatechange = function(){
        if(x.readState === 4){
            isSending = false;
        }
    }
}
btns[1].onclick =  function(){ // 事件触发函数，当触发时就运行x.abort();取消ajax请求
    x.abort();
}
```

## jQuery发送ajax请求



## Axios发送请求



## 跨域













