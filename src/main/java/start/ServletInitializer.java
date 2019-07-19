package start;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

public class ServletInitializer extends SpringBootServletInitializer {
	
	@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(Application.class);  
    }  
      
    @Override  
    public void onStartup(ServletContext servletContext)  
    throws ServletException {  
     FilterRegistration.Dynamic openEntityManagerInViewFilter = servletContext.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class);  
         openEntityManagerInViewFilter.setInitParameter("entityManagerFactoryBeanName","entityManagerFactory");  
         openEntityManagerInViewFilter.addMappingForUrlPatterns(null, false, "/*");  
    super.onStartup(servletContext);  
    }  
}
