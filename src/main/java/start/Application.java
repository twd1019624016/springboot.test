package start;



import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@EnableAutoConfiguration
@ComponentScan
@ServletComponentScan
@ImportResource (value={"classpath:/applicationContext.xml" })
@MapperScan(basePackages = "classpath:/*")
public class Application {

	@Value("${name}")
	private String name;
	
	@RequestMapping("/")
	public String hello() {
		return "he he he 32323事物天天" + name;
	}
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
		application.getId();
		
	}
	
	
}
