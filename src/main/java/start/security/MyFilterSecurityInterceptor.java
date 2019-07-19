/*package start.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;


@Component  
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	@Autowired
	private CustomInvocationSecurityMetadataSourceService mySecurityMetadataSource;


	private CustomAccessDecisionManager myAccessDecisionManager;
	        
	
	private AuthenticationManager authenticationManager;
	
	

	public CustomAccessDecisionManager getMyAccessDecisionManager() {
		return myAccessDecisionManager;
	}

	@Resource
	public void setMyAccessDecisionManager(CustomAccessDecisionManager myAccessDecisionManager) {
		this.myAccessDecisionManager = myAccessDecisionManager;
		super.setAccessDecisionManager(myAccessDecisionManager);
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	@Resource
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);

	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		System.out.println("filter..........................");
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}

	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		System.out.println("filtergergetghrthetyetyetyetyj");
		return this.mySecurityMetadataSource;
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("MyFilterSecurityInterceptor 初始化");
		
	}

}
*/