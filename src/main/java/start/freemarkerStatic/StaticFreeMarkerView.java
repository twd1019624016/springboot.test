package start.freemarkerStatic;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;


import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StaticFreeMarkerView extends FreeMarkerView {

	@Override
	protected void doRender(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		exposeModelAsRequestAttributes(model, request);

		SimpleHash fmModel = buildTemplateModel(model, request, response);

		if (logger.isDebugEnabled()) {
			logger.debug("Rendering FreeMarker template [" + getUrl() + "] in FreeMarkerView '" + getBeanName() + "'");
		}

		Locale locale = RequestContextUtils.getLocale(request);

		
		if (Boolean.FALSE.equals(model.get("STATIC_HTML"))) {
			processTemplate(getTemplate(locale), fmModel, response);
		} else {
			/*createHTML(getTemplate(locale), fmModel, request, response);*/
			storeHtml(getTemplate(locale), fmModel, request, response);
			/*redisHtml(getTemplate(locale), fmModel, request, response);*/
		}
	}
	//存储到硬盘上
	public void createHTML(Template template, SimpleHash model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, TemplateException, ServletException {
		String basePath = request.getSession().getServletContext().getRealPath(File.separator);
		String requestHTML = FreeMarkerUtil.getRequestHTML(request);
		String htmlPath = basePath + requestHTML;
		// 判断文件是否存在
		Object existOrFile = FreeMarkerUtil.isExist(request, htmlPath);
		if (existOrFile instanceof File) {
			File htmlFile = (File) existOrFile;

			if (!htmlFile.getParentFile().exists()) {
				htmlFile.getParentFile().mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			// 处理模版
			template.process(model, out);
			out.flush();
			out.close();
			if (logger.isDebugEnabled()) {
				logger.debug(" FreeMarker template [" + getUrl() + "] 生成静态文件 路径为: '" + htmlPath + "'");
			}
		}

		requestHTML = requestHTML.replace("\\", "/");
		/* 将请求转发到生成的文件 */
		if (logger.isDebugEnabled()) {
			logger.debug(" FreeMarker template [" + getUrl() + "] 转发请求到: '" + requestHTML + "'");
		}
		request.getRequestDispatcher(requestHTML).forward(request, response);
	}

	//存储到缓存中
	public void storeHtml(Template template, SimpleHash model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String Key = FreeMarkerUtil.getRequestHTML(request);
		
		// 判断文件是否存在
		Object existOrCachePage = FreeMarkerUtil.isCacheExist(Key);
		if (existOrCachePage instanceof Boolean) {
			
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			Writer out = new BufferedWriter(new OutputStreamWriter(arrayOutputStream, "UTF-8"));
			
			// 处理模版
			template.process(model, out);
			out.flush();
			out.close();
			byte[] byteArray = arrayOutputStream.toByteArray();
			existOrCachePage = FreeMarkerUtil.storgePage(Key, byteArray);
			if (logger.isDebugEnabled()) {
				logger.debug(" FreeMarker template [" + getUrl() + "] 生成静态文件 路径为: '" + Key+ "'");
			}
		}

		((CachePage) existOrCachePage).process(response);
	}
	
	//存储到redis中
	/*	public void redisHtml(Template template, SimpleHash model, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			String Key = FreeMarkerUtil.getRequestHTML(request);
			
			// 判断文件是否存在
			Object existOrCachePage = FreeMarkerUtil.isRedisExist(Key);
			if (existOrCachePage instanceof Boolean) {
				
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				Writer out = new BufferedWriter(new OutputStreamWriter(arrayOutputStream, "UTF-8"));
				
				// 处理模版
				template.process(model, out);
				out.flush();
				out.close();
				byte[] byteArray = arrayOutputStream.toByteArray();
				existOrCachePage = FreeMarkerUtil.storgeRedisPage(Key, byteArray);
				if (logger.isDebugEnabled()) {
					logger.debug(" FreeMarker template [" + getUrl() + "] 生成静态文件 路径为: '" + Key+ "'");
				}
			}

			((CachePage) existOrCachePage).process(response);
		}*/


}
