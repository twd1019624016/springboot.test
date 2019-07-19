package start;



import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import start.web.DemoFilter;
import start.web.DemoListener;
import start.web.DemoServlet;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	/*@Bean*/
	public FilterRegistrationBean getFilter() {
		
		DemoFilter demoFilter = new DemoFilter();
		FilterRegistrationBean filterRegistrationBean =
				new FilterRegistrationBean();
		
		filterRegistrationBean.setFilter(demoFilter);
		List<String> urlPattern = new ArrayList<>();
		
		urlPattern.add("/*");
		
		filterRegistrationBean.setUrlPatterns(urlPattern);
		filterRegistrationBean.setOrder(1);
		
		return filterRegistrationBean;
		
		
	}
	
	/*@Bean*/
	public ServletRegistrationBean getServlet() {
		DemoServlet demoServlet = new DemoServlet();
		
		ServletRegistrationBean bean = new ServletRegistrationBean();
		bean.setServlet(demoServlet);
		List<String> urlPattern = new ArrayList<>();
		
		
		urlPattern.add("/demoServlet");
		
		
		bean.setUrlMappings(urlPattern);
		bean.setLoadOnStartup(1);
		
		return bean;
	}
	
	/*@Bean*/
	public ServletListenerRegistrationBean<EventListener>
		getDemoListener() {
		
		ServletListenerRegistrationBean<EventListener> bean
			= new ServletListenerRegistrationBean<>();
		
		bean.setListener(new DemoListener());
		
		bean.setOrder(1);
		
		return bean;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
