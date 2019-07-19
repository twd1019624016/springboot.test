package start.freemarkerStatic;

import java.io.File;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import freemarker.cache.CacheStorage;
import freemarker.cache.SoftCacheStorage;



public class FreeMarkerUtil {

	private static String Folder = "static";

	private static String Suffix = ".html";
	
	private static final CacheStorage STORAGE = new SoftCacheStorage();
	/**
	 * 计算要生成的<strong>静态</strong>文件相对路径.
	 */
	public static String getRequestHTML(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		
		requestURI = requestURI.replace("/", File.separator);
		
		 Enumeration<?> pNames = request.getParameterNames();  
	        while (pNames.hasMoreElements()) {  
	            String name = (String) pNames.nextElement();  
	            String value = request.getParameter(name);  
	            requestURI = requestURI + "_" + name + "=" + value;  
	        }  
	  
		return  File.separator + Folder +  requestURI + Suffix;
		
	}
	//存储到硬盘上
	public static Boolean isExist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String requestHTML = getRequestHTML(request);
		
		String basePath = request.getSession().getServletContext().getRealPath(File.separator);
		String htmlPath  =  basePath + requestHTML;
		//判断文件是否存在
		Object existOrFile = FreeMarkerUtil.isExist(request, htmlPath);
		if(existOrFile instanceof Boolean) {
			requestHTML = requestHTML.replace("\\", "/");
			request.getRequestDispatcher(requestHTML).forward(request, response);
			return Boolean.TRUE;
		}
		
		
		return Boolean.FALSE;
	}

	public static Object isExist(HttpServletRequest request, String path) {
		File htmlFile = new File(path);

		return htmlFile.exists() ? Boolean.TRUE : htmlFile;
	}
	
//存储到缓存中
	
	public static CachePage storgePage(String key,byte[] byteArray) {
		CachePage cachePage =new CachePage(byteArray) ;
		STORAGE.put(key, cachePage);
		return cachePage;
	}
	
	public static Boolean isCacheExist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String key = getRequestHTML(request);
		//判断文件是否存在
		Object cacheExist = isCacheExist(key);
		if(cacheExist instanceof CachePage) {
			
		  ((CachePage)cacheExist).process(response);
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}

	public static Object isCacheExist(String key) {
		Object cachePage = STORAGE.get(key);
		return cachePage == null ? Boolean.FALSE:cachePage;
	}
// 存储到 redis
	
	public static CachePage storgeRedisPage(String key,byte[] byteArray) {
		CachePage cachePage =new CachePage(byteArray) ;
	/*	RedisCacheUtil.putBySerializable(key, cachePage);*/
		return cachePage;
	}
	
	public static Boolean isRedisExist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String key = getRequestHTML(request);
		//判断文件是否存在
	/*	Object redisExist = isRedisExist(key);
		if(redisExist instanceof CachePage) {
			
		  ((CachePage)redisExist).process(response);
			return Boolean.TRUE;
		}*/
		
		return Boolean.FALSE;
	}

	/*public static Object isRedisExist(String key) {
		Object cachePage = RedisCacheUtil.getBySerializable(key);
		return cachePage == null ? Boolean.FALSE:cachePage;
	}*/
	
	
	
}
