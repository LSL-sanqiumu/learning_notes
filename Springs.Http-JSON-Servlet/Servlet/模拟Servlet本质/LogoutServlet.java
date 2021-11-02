
/*
	服务器端的每一个小java程序，都提供一个独立的服务
	这个LogoutServlet专门完成系统安全退出
*/
public class LogoutServlet implements Servlet
{
	public void service(){
		System.out.println("System logout.....");
	}
}