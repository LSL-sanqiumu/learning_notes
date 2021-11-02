/*
	Servlet接口
	SUN公司负责制定
	请分析该接口的调用者和实现者
*/
//服务器端的小java程序，javaweb程序员负责编写这个小java程序
//每一个小java程序都会提供一个独立的服务
public interface Servlet 
{
	/*
		这是提供服务的方法
	*/
	void service();
}