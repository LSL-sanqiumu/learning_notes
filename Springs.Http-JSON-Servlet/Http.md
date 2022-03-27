# TCP/IP

客户端（Client）：发送请求获取服务器资源的一端。

服务器端（Server）：提供资源响应的一端。

WWW提议：致力于全世界的研究者们进行知识共享。

TCP/IP协议族：互联网相关的各类协议族的统称；TCP/IP协议族按层次分层：应用层、传输层、网络层、数据链路层。

|         分层         |                             作用                             |
| :------------------: | :----------------------------------------------------------: |
|        应用层        | 决定了提供应用服务时的通信活动<br>FTP文件传输协议  DNS域名系统  HTTP超文本传输协议 |
|        传输层        | 为应用层提供处于网络连接中的两台计算机间的数据传输<br>TCP  UDP |
| 网络层（网络互连层） | 处理网络上流动的数据包<br>规定了如何把数据包通过怎样的路径传输给对方 |
|        链路层        |                   ()处理连接网络的硬件部分                   |

## IP

IP(Internet Protocol)，网际协议，作用是把各种数据包传送给对方，确保数据传输准确的两个重要条件是IP地址和MAC地址。

IP地址：节点被分配到的地址；MAC地址：网卡所属的固定地址。

IP地址可以和MAC地址进行配对，IP地址可变，MAC地址基本上不会改变。

ARP协议（Address Resolution Protocol），用以解析地址的协议，根据通信方的IP地址可以反查对应的MAC地址，借助此协议就可以通过MAC地址进行通信。

## TCP

TCP，提供可靠的字节流服务（为了方便传而输将大块数据分割成以报文段为单位的数据包来进行管理），为了更容易传输大数据才把数据分割，TCP协议能确认数据最终是否送达对方。

TCP为了准确无误传送数据达到目的地，采用了三次握手(three-way handshaking)策略：

1. 第一次握手：发送端发送一个带SYN标志的数据包给接收端；
2. 第二次握手：收到数据包后回传一个带SYN/ACK标志的数据包给发送端（告诉发送端我已经接收到数据）；
3. 第三次握手：发送端接收到确认信息，再回传一个ACK标志的数据包，握手结束。
4. 握手过程中莫名中断，TCP协议会按相同顺序再次发送相同的数据包。

## DNS

DNS(Domain Names System)，提供域名到IP地址之间的解析服务。

计算机可以被赋予IP地址，也可以被赋予主机名和域名，一般都是通过主机名或域名来访问主机而不是通过IP地址，因为IP地址是一串数字，不容易记忆。DNS协议通过域名查找IP地址，或逆向从IP地址查找域名的服务，这样就可以通过域名来访问主机。

## URI和URL

URL(Uniform Resource Locator，统一资源定位符)，访问页面资源的地址，比如网页地址，是URI的子集。

URI(Uniform Resource Identifier)，由某个协议方案表示的资源的定位标识符，协议方案就是访问资源所使用的协议类型名称，比如采用HTTP协议时，协议方案就是http，此时URI就是以http开头；除此还有ftp、mailto、telnet、file等。

> http://user:pass@www.example.jp:8080/dir/index.html?uid=1#ch1

以上是一个绝对URI的格式，详解如下：

1. `http://`：协议方案名http；
2. `user:pass`：可选项，指定用户名和密码作为从服务器获取资源时的登录信息，省略时@也去掉；
3. `www.example.jp`：服务器地址；
4. `8080`：服务器端口号；
5. `dir/index.html`：带层次的文件路径，通过该路径定位特指的资源。
6. `uid=1`：查询字符串，针对指定的资源传入任意参数；
7. `cha`：片段标识符，可选。

# HTTP协议

什么是http协议？超文本传输协议，浏览器和服务器之间的一种通讯协议，该协议是W3C负责制定的，其本质上就是提前指定好了的数据传送格式。浏览器和服务器都必须按照这种格式接收与发送数据。目前使用的HTTP协议版本号是HTTP1.1。因为HTTP是无状态协议，不对请求和响应之间的通信状态进行保存，所以为了达到保存状态的效果，引入了Cookie技术。

HTTP协议包括两部分：

- 请求协议：从Browser发送到Server的时候采用的数据传送格式；
- 响应协议：从Server发送到Browser时时候采用的数据传送格式。

## 请求协议

请求协议(请求报文)包括：请求行（请求行包括(请求方式 URI 协议版本号)，例如： `POST /webapp10/login HTTP/1.1`）、消息报头、空白行（专门用来分离消息报头和请求体的）、请求体。

**GET请求**：如下是一个GET请求的登陆页面的请求，由于请求方式为GET，所以发送的数据在请求行上发送！故请求体为空：

````http
GET /webapp10/login?username=admin&password=123 HTTP/1.1    //请求行
Accept: text/html, application/xhtml+xml, image/jxr, */* 消息报头
X-HttpWatch-RID: 50301-10022
Referer: http://localhost:8080/webapp10/
Accept-Language: zh-CN
User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko
Accept-Encoding: gzip, deflate
Host: localhost:8080
Connection: Keep-Alive
														 空白行
														 请求体
````

**POST请求**：如下是一个POST请求的登陆页面的请求，请求体展示了请求的内容`username=admin&password=***`：

````http
POST /webapp10/login HTTP/1.1                             请求行						
Accept: text/html, application/xhtml+xml, image/jxr, */*  消息报头
X-HttpWatch-RID: 50301-10054
Referer: http://localhost:8080/webapp10/
Accept-Language: zh-CN
User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko
Content-Type: application/x-www-form-urlencoded
Accept-Encoding: gzip, deflate
Host: localhost:8080
Content-Length: 27
Connection: Keep-Alive
Cache-Control: no-cache
						               空白行
username=admin&password=123            请求体
````

## 响应协议

响应协议包括四部分：状态行（协议版本号 状态码，例如：`HTTP/1.1 200 OK`）、响应报头、空白行（是用来分离响应报头和响应体的）、响应体。

响应协议中要重点掌握的状态码：

- 200 响应成功正常结束；

- 404  资源未找到；

- 500 服务器内部错误。

如下是服务器的响应：

````http
HTTP/1.1 200 OK             状态行
Server: Apache-Coyote/1.1   响应报头
Content-Type: text/html;charset=UTF-8
Content-Length: 21
Date: Thu, 13 Aug 2020 10:15:13 GMT
	                        空白行
<h1>登陆成功</h1>            响应体
````

## GET与POST的区别

什么情况下浏览器发送的请求是GET请求，什么情况下浏览器发送的请求是POST请求？

只有当使用表单form，并且form的标签的method属性设置为method="post"，才是POST请求方式，其余剩下的所有请求方式都是基于GET请求。

GET请求和POST请求有什么区别？

- GET请求在请求行上提交数据，格式`uri?name=value&name=value`，这种提交方式最终提交的数据会显示在浏览器地址栏上；

- POST请求在请求体中提交数据，相对安全，提交格式`name=value&name=value`，这种提交方式最终不会显示在浏览器地址栏上；
- POST请求在请求体中提交数据，所以POST请求提交的数据没有长度限制【POST可以提价大数据】；
- GET请求在请求行上提交数据，所以GET请求提交的数据长度有限制；
- GET请求只能提交字符串数据，POST请求可以提交任何类型的数据，包括视频...，所以**文件上传必须使用POST请求**；
- GET请求最终的结果，会被浏览器缓存收纳，而POST不会被缓存收纳（为什么GET会被缓存？）。

GET请求和POST请求应该如何选择？

- 有敏感数据  ---> POST；
- 传送的数据不是普通字符串  ----->  POST；
- 传送的数据非常多 ---->  POST；
- 这个请求是为了修改服务器端资源 ---->  POST；
- GET请求多数情况下是**从服务器中读取资源**，这个读取的资源在短时间内不会发生变化，所以GET请求的结果最终会被浏览器缓存起来；
- POST请求是为了修改服务器端的资源，而每一次修改结果都是不同的，最终结果没有必要被浏览器缓存。

## 缓存解决方案

浏览器将资源缓存后，缓存的资源是和某个特定的路径绑定在一起的，只要浏览器再发送这个相同的请求路径，这个时候浏览器就会去缓存中获取资源，不再访问服务器，以这种方式降低服务器的压力，提高用户体验。

但是有的时候我们并不希望走缓存，希望每一次后台访问服务器，可以在请求路径后面添加时间戳，例如：`http://ip:port/oa/system/logout?timetamp=1234564635423`

# 响应状态码

2xx：请求正常处理完毕

- `200 ok`：客户端发来的请求在服务器端被正常处理；
- `204 No Content`：请求处理成功但没有资源返回；
- `206 Partial Content`：客户端进行范围请求，服务端成功执行这样的get请求。

3xx：

4xx：服务器无法处理请求

- `404 Not Found`：服务器上无法找到请求的资源。

5xx：服务器本身发生错误

- `500 Internal Server Error`：内部资源故障，也可能是应用存在bug或某些临时的故障；
- `503 Service Unavailable`：服务器暂时处于超负载或正在进行停机维护，无法处理请求。

不少返回的状态码都是错误的，例如web应用程序内部发生错误，但状态码依然返回200 OK 的情况。

# HTTPS

HTTP没有加密机制，但可以通过SSL（Secure Socket Layer，安全嵌套层），或TLS（Transport Layer Security，安全层传输协议）的组合使用，加密HTTP的通信内容。

HTTPS：与SSL组合使用的HTTP被称为HTTPS（HTTP Secure，超文本传输安全协议）或HTTP over SSL。用SSL来建立安全通信线路。SSL不仅提供加密处理，还使用了一种被称为证书的手段。

# HttpClient库

















