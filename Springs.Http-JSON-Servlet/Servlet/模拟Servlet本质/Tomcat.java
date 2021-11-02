
/*
	服务器开发商，开发的web服务器，web容器，web container
	web服务器不知道有哪些Servlet，不知道有：logout,login,delete!!!!!!!
	但是知道这些所有的Servlet都实现了SUN公司的Servlet接口
*/
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class Tomcat //Tomcat服务器是纯java语言实现的，没有使用其他编程语言
{
	public static void main(String[] args) throws Exception{
		
		System.out.println("正在启动Tomcat服务器，请稍后...");
		System.out.println("Tomcat服务器启动成功，开始接收浏览器的访问");
		Scanner s = new Scanner(System.in);
		System.out.print("请输入请求路径：");
		//获取用户的请求路径
		String requestPath = s.next();
		//Tomcat服务器负责解析web.xml文件
		//Tomcat服务器提前把代码已经写完了
		//web.xml文件名是Servlet规范，不能修改
		//web.xml文件的存放位置也是Servlet规范，不能修改
		//web.xml文件中所有的配置信息也是Servlet规范，不能随意编写
		//web.xml文件的编写者：javaweb程序员
		FileReader reader = new FileReader("web.xml");
		Properties pro = new Properties();
		pro.load(reader);
		reader.close();

		//通过key获取value
		String className = pro.getProperty(requestPath);

		//通过反射机制创建对象
		Class c = Class.forName(className);
		Servlet servlet = (Servlet)c.newInstance();

		//面向接口调用即可
		//多态
		servlet.service();
		
	}
}
