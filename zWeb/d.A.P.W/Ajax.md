

# AJAX

# 概述

Ajax（Asynchronous JavaScript and XML，异步JavaScript与XML技术），是一种有效利用JavaScript和DOM（Document Object Model，文档对象模型）的操作，以达到局部web页面替换加载的异步通信手段。利用ajax实时地从服务器获取内容（**无刷新获取数据**）。XML，可拓展标记语言，全都是自定义标签，被设计来传输和存储数据。目前在ajax中已经被json取代。

1. AJAX不能称为一种技术，它是多种技术的综合产物。AJAX可以让浏览器发送一种特殊的请求，这种请求可以是：异步的（各自独立走一条道（线程），互不干扰）。
2. 什么是异步，什么是同步？
   - 假设有t1和t2线程，t1和t2线程并发，就是异步。
   - 假设有t1和t2线程，t2在执行的时候，必须等待t1线程执行到某个位置之后t2才能执行，那么t2在等t1，显然他们是排队的，排队的就是同步。
   - AJAX是可以发送异步请求的。也就是说，在同一个浏览器页面当中，可以发送多个ajax请求，这些ajax请求之间不需要等待，是并发的。
3. AJAX代码属于WEB前端的JS代码，和后端的java没有关系，后端也可以是php语言，也可以是C语言。
4. AJAX 应用程序可能使用 XML 来传输数据，但将数据作为纯文本或 JSON 文本传输也同样常见。
5. AJAX可以更新网页的部分，而不需要重新加载整个页面。（页面局部刷新）
6. AJAX可以做到在同一个网页中同时启动多个请求，类似于在同一个网页中启动“多线程”，一个“线程”一个“请求”。

优缺点：

1. 特点：可以无需刷新页面而与服务器端进行通信；允许你根据用户事件来更新部分页面内容。

2. 缺点：没有浏览历史，不能回退；存在跨域问题(必须同源，不能跨页面) ； SEO 不友好。

3. 核心对象：XMLHttpRequest（浏览器内置的一个对象），AJAX 的所有操作都是通过该对象进行的，而服务器响应Ajax请求返回的数据是普通文本、XML字符串或者json字符串。

传统的请求：

1. 直接在浏览器地址栏上输入URL、点击超链接、提交form表单。
2. 使用JS代码发送请求（window.open(url)、document.location.href = url、window.location.href = url 等）。

传统请求存在的问题：

1. 页面全部刷新导致了用户的体验较差。
2. 传统的请求导致用户的体验有空白期。（用户的体验是不连贯的）



# XMLHttpRequest对象

XMLHttpRequest对象是AJAX的核心对象，发送请求以及接收服务器数据的返回，全靠它了。XMLHttpRequest对象，现代浏览器都是支持的，都内置了该对象。直接用即可。

```javascript
// 创建XMLHttpRequest对象
var xhr = new XMLHttpRequest();
```

XMLHttpRequest对象的方法：

| 方法                                 | 描述                                                         |
| :----------------------------------- | :----------------------------------------------------------- |
| `abort()`                            | 取消请求                                                     |
| `getAllResponseHeaders()`            | 获取响应的全部头部信息                                       |
| `getResponseHeader()`                | 获取响应的特定的头部信息                                     |
| `open(method, url, async, user psw)` | 打开通道，规定请求<br>method：请求类型 GET 或 POST ；url：文件位置async：true（异步）或 false（同步）；user：可选的用户名称；psw：可选的密码 |
| `send()`                             | 将请求发送到服务器，用于 GET 请求                            |
| `send(string)`                       | 将请求发送到服务器，用于 POST 请求                           |
| `setRequestHeader()`                 | 向要发送的报头添加标签/值对                                  |

XMLHttpRequest对象的属性：

| 属性                 | 描述                                                         |
| :------------------- | :----------------------------------------------------------- |
| `onreadystatechange` | 定义当 readyState 属性发生变化时被调用的函数                 |
| `readyState`         | 记录保存了 XMLHttpRequest 的状态。0：请求未初始化     1：服务器连接已建立     2：请求已收到    3：正在处理请求    4：请求已完成且响应已就绪 |
| `responseText`       | 将服务器响应的数据以 字符串 返回                             |
| `responseXML`        | 将服务器响应的数据以 XML数据 返回                            |
| `status`             | 返回请求的状态号；200: "OK"    403: "Forbidden"   404: "Not Found" |
| `statusText`         | 返回状态文本（比如 "OK" 或 "Not Found"）                     |

# 发起Ajax请求

## Ajax GET请求

发送Ajax  前端代码：

```html
<body>
    <button id="ajaxr" type="button">发送ajax请求</button>
    <span id="content">123</span>
    <script type="text/javascript">
        window.onload = function (){
            let btn = document.getElementById('ajaxr');
            let con = document.getElementById('content');
            btn.onclick = function (){
                // 1.创建XMLHttpRequest对象
                let xhr = new XMLHttpRequest();
                // 2.接收响应返回的数据
                xhr.onreadystatechange = function (){
                    if (this.readyState === 4){
                        if (this.status === 200){
                            alert(this.responseText);
                            content.innerHTML = this.responseText;
                        }
                    }else {
                        alert(xhr.status);
                    }
                }
                // 3.开启通道
                xhr.open('GET','/ajax/ajaxr',true);
                // 4.发起请求
                xhr.send();
            }
        }
    </script>
</body>
```

处理Ajax请求  后端代码：

```java
public class AjaxRequestInterface extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf8");
        PrintWriter out = response.getWriter();
        out.print("<html>");
        out.print("<head><title>HelloServlet</title></head>");
        out.print("<body><h1 align='center'>Welcome study Servlet</h1><br><h2 align='center'>Ajax Interface<h2></body>");
        out.print("</html>");
        // 设置响应的内容类型以及字符集
        response.setContentType("text/html;charset=UTF-8");
        out.print("<font color='red'>ajax！！！</font>");
    }
}
```

AJAX get请求如何提交数据呢？get请求提交数据是在“请求行”上提交，格式是：`url?name=value&name=value&name=value....`；这个get请求提交数据的格式是HTTP协议中规定的，遵循协议即可。

## AJAX GET请求的缓存问题

**对于低版本的IE浏览器来说，AJAX的get请求可能会走缓存。存在缓存问题。对于现代的浏览器来说，大部分浏览器都已经不存在Ajax get缓存问题了。**

AJAX GET请求缓存问题:

1. 在HTTP协议中是这样规定get请求的：GET 请求会被缓存起来。
2. POST请求在HTTP协议中规定的是：POST 请求不会被浏览器缓存。
3. 发送AJAX GET请求时，在同一个浏览器上，前后发送的AJAX请求路径一样的话，对于低版本的IE来说，第二次的 Ajax GET 请求会走缓存，不走服务器。

GET请求缓存的优缺点：

- 优点：直接从浏览器缓存中获取资源，不需要从服务器上重新加载资源，速度较快，用户体验好。
- 缺点：无法实时获取最新的服务器资源。

浏览器什么时候会走缓存？发起的请求是一个GET请求，并且请求路径已经被浏览器缓存过了（第二次发送请求的时候，这个路径没有变化，就会走浏览器缓存）。

如果是低版本的IE浏览器，怎么解决 Ajax GET 请求的缓存问题呢？

- 可以在请求路径url后面添加一个时间戳，这个时间戳是随时变化的。所以每一次发送的请求路径都是不一样的，这样就不会走浏览器的缓存了。
- 采用时间戳：`"url?t=" + new Date().getTime()`、或者可以通过随机数：`"url?t=" + Math.random()`，也可以随机数+时间戳....。

```js
xhr.open('GET','http://127.0.0.1:8000/ie?t='+Date.now(),true);
```

## Ajax POST请求

```js
let xhr = new XMLHttpRequest();
xhr.open('POST','http://127.0.0.1:8000/server');
// 设置请求头的内容类型。模拟form表单提交数据
xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded") 
// 获取表单中的数据
let username = document.getElementById("username").value;
let password = document.getElementById("password").value;
// send函数中的参数就是发送的数据，这个数据在“请求体”当中发送
xhr.send("username="+username+"&password="+password)
```

## Ajax四步骤总结

ajax操作的四步骤：

1. 创建XMLHttpRequest对象（AJAX的所有操作都经过XMLHttpRequest）：

   ```js
   const xhr = new XMLHttpRequest();
   ```

2. 设置请求信息：

   ```js
   xhr.open('POST','http://127.0.0.1:8000/server');
   // 请求头信息设置，可以不设置，也可以设置自定义的请求头，Content-Type是预定义的
   xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
   // 如果是自定义的要在后端设置 response.setHeader('Access-Control-Allow-Headers','*')，并把接收请求的类型设置为所有
   ```

3. 发送请求：

   ```js
   xhr.send();
   // xhr.send('这里是向http://127.0.0.1:8000/server发送的信息');
   ```

4. 接收响应的数据：

   ```js
   // onreadystatechange：当readystate改变的时候触发，onreadystatechange会触发四次：1 2 3 4四次
   xhr.onreadystatechange = function(){
       // readyState=4时服务端已经完成处理并响应完成，可以进行处理
       if(xhr.readyState === 4) {
           // 判断响应状态 200 404 403 401 505
           if(xhr.status >= 200 && xhr.status < 300) {
               // console.log(xhr.status); // 状态码
               // console.log(xhr.statusText);// 状态字符串
               // console.log(xhr.getAllResponseHeaders()); // 所有响应头
               // console.log(xhr.response); // 响应体
               result.innerHTML = xhr.response;
           }else {
               
           }
       }
   }
   ```



# JSON响应与处理

服务器端响应回json格式的字符串，可以手动转换或自动转换。

```js
// 手动转换 将JSON字符串转换为对象，再取值
let data = JSON.parse(xhr.response);
result.innerHTML = data.name;
// 自动转换 设置响应体数据类型是json，然后就可以自动转换
xhr.responseType = 'json';
result.innerHTML = xhr.response.name;
```

# 超时与网络异常

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

# ajax请求取消

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

# 重复请求问题

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

# 框架的使用

## jQuery发送ajax请求

### 通用方法

例子：

```html
<script type="text/javascript">
    /* 发送数组的方式一 */
    $(function(){
        $("#btn1").click(function (){
            $.ajax({
                "url":"send/array1.html", // 请求目标资源地址
                "type":"post", // 请求方法
                "data":{"array":[1,9,9,9]}, // 发送的请求参数
                "dataType":"text", // 如何对待服务器返回数据
                "success":function (response){ // 服务器成功处理请求后：调用回调参数,response是响应体数据
                    alert(response)
                },
                "error":function (response){ // 服务器处理请求失败后：调用的回调参数
                    alert(response)
                }
            });
        });
    });
    /* 发送数组的方式二 */
    $(function(){
        $("#btn2").click(function (){
            $.ajax({
                "url":"send/array2.html", // 请求目标资源地址
                "type":"post", // 请求方法
                "data":{       // 发送的请求参数
                    "array[0]": 1,
                    "array[1]": 1,
                    "array[2]": 1,
                    "array[3]": 1
                },
                "dataType":"text", // 如何对待服务器返回数据
                "success":function (response){ // 服务器成功处理请求后：调用回调参数,response是响应体数据
                    alert(response)
                },
                "error":function (response){ // 服务器处理请求失败后：调用的回调参数
                    alert(response)
                }
            });
        });
    });
    /* 发送数组的方式三 */
    $(function(){
        $("#btn3").click(function (){
            var array = [1,9,9,9]
            var requestBody = JSON.stringify(array);
            console.log(requestBody.length);
            $.ajax({
                "url":"send/array3.html", // 请求目标资源地址
                "type":"post", // 请求方法
                "contentType":"application/json;charset=utf-8", // 设置请求内容的具体类型，告诉服务器端本次请求的请求体是json类型
                "data": requestBody, // 发送的请求参数
                "dataType":"text", // 如何对待服务器返回数据
                "success":function (response){ // 服务器成功处理请求后：调用回调参数,response是响应体数据
                    alert(response)
                },
                "error":function (response){ // 服务器处理请求失败后：调用的回调参数
                    alert(response)
                }
            });
        });
    });
    /* 发送复杂对象 */
    $(function(){
        $("#btn4").click(function (){
            var student = {
                "stuId":6,
                "stuName":"Tom",
                "address":{
                    "province":"浙江",
                    "city":"杭州",
                    "street":"学源街"
                },
                "subjectList":[
                    {
                        "subjectName":"数据结构和算法",
                        "subjectScore":999
                    },
                    {
                        "subjectName":"计算机组成原理",
                        "subjectScore":999
                    }
                ],
                "map":{
                    "k1":"v1",
                    "k2":"v2",
                }
            };
            var requestBody = JSON.stringify(student);
            console.log(requestBody.length);
            $.ajax({
                "url":"send/compose/object.html", // 请求目标资源地址
                "type":"post", // 请求方法
                "contentType":"application/json;charset=utf-8", // 设置请求内容的具体类型，告诉服务器端本次请求的请求体是json类型
                "data": requestBody, // 发送的请求参数
                "dataType":"text", // 如何对待服务器返回数据
                "success":function (response){ // 服务器成功处理请求后：调用回调参数,response是响应体数据
                    alert(response)
                },
                "error":function (response){ // 服务器处理请求失败后：调用的回调参数
                    alert(response)
                }
            });
        });
    });
    /* 工具类 */
    $(function(){
        $("#btn5").click(function (){
            var student = {
                "stuId":6,
                "stuName":"Tom",
                "address":{
                    "province":"浙江",
                    "city":"杭州",
                    "street":"学源街"
                },
                "subjectList":[
                    {
                        "subjectName":"数据结构和算法",
                        "subjectScore":999
                    },
                    {
                        "subjectName":"计算机组成原理",
                        "subjectScore":999
                    }
                ],
                "map":{
                    "k1":"v1",
                    "k2":"v2",
                }
            };
            var requestBody = JSON.stringify(student);
            console.log(requestBody.length);
            $.ajax({
                "url":"send/compose/object.json", // 请求目标资源地址
                "type":"post", // 请求方法
                "contentType":"application/json;charset=utf-8", // 设置请求内容的具体类型，告诉服务器端本次请求的请求体是json类型
                "data": requestBody, // 发送的请求参数
                "dataType":"json", // 如何对待服务器返回数据
                "success":function (response){ // 服务器成功处理请求后：调用回调参数,response是响应体数据
                    alert(response)
                },
                "error":function (response){ // 服务器处理请求失败后：调用的回调参数
                    alert(response)
                }
            });
        });
        $("#btn6").click(function (){
            layer.msg("layer的弹框");
        });
    });

</script>
```





## Axios发送请求





# 跨域













