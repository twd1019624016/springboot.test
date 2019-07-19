package start.freemarkerStatic;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class PageCacheHandlerInterceptor extends HandlerInterceptorAdapter {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		try {
			/*Boolean exist = FreeMarkerUtil.isExist(request, response);*/
			Boolean exist = FreeMarkerUtil.isCacheExist(request, response);
			/*Boolean exist = FreeMarkerUtil.isRedisExist(request, response);*/
			if (exist) {
				return false;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return true;
	}

	

	
}


