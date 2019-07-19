package start.jwt;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class JwtAuthorizeFilter
 */
@WebFilter("/tokenUser")
public class JwtAuthorizeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public JwtAuthorizeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// place your code here
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        
        String auth = httpRequest.getHeader("Authorization");
        if(auth != null && auth.length() >7) {
        	String headStr = auth.substring(0, 6).toLowerCase();
        	
        	if("bearer".compareTo(headStr) ==0 ){
        		auth = auth.substring(7,auth.length());
        		
        		if(null != JwtHelper.parseJWT(auth, JwtHelper.key)) {
        			chain.doFilter(httpRequest, response);
        			
        			return;
        		}
        	}
        }
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;    
        httpResponse.setCharacterEncoding("UTF-8");      
        httpResponse.setContentType("application/json; charset=utf-8");     
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
		
        return;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
