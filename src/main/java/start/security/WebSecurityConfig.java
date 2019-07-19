/*package start.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyFilterSecurityInterceptor mySecurityFilter;

	@Autowired
	private CustomerUserDetailsService customerUserDetialsService;

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/home").permitAll().anyRequest().authenticated().antMatchers("/hello")
				.hasAuthority("ADMIN")

				.and().formLogin().loginPage("/login").permitAll().successHandler(loginSuccessHandler())

				.and().logout().logoutSuccessUrl("/home").permitAll().invalidateHttpSession(true).and().rememberMe()
				.tokenValiditySeconds(1209600)

				.and().csrf().disable();
	}

	
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception { //指定密码加密所使用的加密器为passwordEncoder() //需要将密码加密后写入数据库
	 * auth.userDetailsService(customerUserDetialsService).passwordEncoder(
	 * passwordEncoder()); auth.eraseCredentials(false); }
	 * 
	 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(4); }
	 

	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();

	}

}
*/