package start.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DemoListener implements ServletContextListener{
	 
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("==>DemoListener启动");
		
	}
 
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
 
}
