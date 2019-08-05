package start;



import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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
	
	/*@Bean*/
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        // #允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        // 允许Get的请求方法
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
