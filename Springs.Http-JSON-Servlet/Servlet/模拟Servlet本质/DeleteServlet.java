
/*
	服务器端的每一个小java程序，都提供一个独立的服务
	这个DeleteServlet专门完成删除员工信息
*/
public class DeleteServlet implements Servlet
{
	public void service(){
		System.out.println("正在删除员工信息....");
	}
}