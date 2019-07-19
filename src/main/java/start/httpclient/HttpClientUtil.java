package start.httpclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static final ContentType TEXT_PLAIN_UTF8 = ContentType.create("text/plain", Consts.UTF_8);
	public static final ContentType TEXT_PLAIN_ASCII = ContentType.create("text/plain", Consts.ASCII);

	private static final ResponseHandler<String> defaultHandler = new ResponseHandler<String>() {

		@Override
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
				
				String line;
				StringBuilder sb = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				String result = sb.toString();
				return result;
			} else {
				HttpEntity r_entity = response.getEntity();
				String responseString = EntityUtils.toString(r_entity);
				logger.error("请求出错 {}", responseString);
				return null;
			}
		}
	};

	@Autowired
	@Qualifier("linkfaceHttpClient")
	private CloseableHttpClient httpClient;

	/**
	 * 执行请求核心方法
	 * 
	 * @param httpUriRequest
	 * @param rh
	 * @return
	 */
	public <T> T doRequest(HttpUriRequest httpUriRequest, ResponseHandler<T> rh) {
		Args.notNull(httpUriRequest, "httpUriRequest 请求");
		Args.notNull(rh, "rh 响应处理器");

		T result = null;
		try {
			result = httpClient.execute(httpUriRequest, rh);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error("请求出错 {}", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("请求出错 {}", e.getMessage());
		}
		return result;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param entity
	 * @param rh
	 * @return
	 */
	public <T> T doPost(String url, HttpEntity entity, ResponseHandler<T> rh) {
		Args.notBlank(url, "url 请求提交地址");
		Args.notNull(rh, "rh 响应处理器");

		HttpPost post = new HttpPost(url);
		post.setEntity(entity);
		
		T doRequest = doRequest(post, rh);

		return doRequest;

	}

	/**
	 * 使用默认处理器
	 * @param url
	 * @param e
	 * @param filebytes
	 * @return
	 */
	public <E> String doPostMultipartForm(String url, E e, Map<String, byte[]> filebytes) {
		return this.doPostMultipartForm(url, e, filebytes, defaultHandler);
	}
	
	public  String doPostMultipartForm(String url,Map<String, byte[]> filebytes) {
		return this.doPostMultipartForm(url, null, filebytes);
	}
	
	
	/**
	 * 无附加参数 上传文件
	 * @param url
	 * @param filebytes
	 * @param rh
	 * @return
	 */
	public <T> T doPostMultipartForm(String url, Map<String, byte[]> filebytes, ResponseHandler<T> rh) {
		return this.doPostMultipartForm(url, null, filebytes, rh);
	}
	/**
	 * MultipartForm utf8
	 * 
	 * @param url
	 *            请求地址
	 * @param e
	 *            中 不能包含 byte[],file,inputStream，等未实现 Serializable 的类
	 * @param rh
	 *            响应处理类
	 * @return
	 */
	public <T, E> T doPostMultipartForm(String url, E e, Map<String, byte[]> filebytes, ResponseHandler<T> rh) {
		Args.notBlank(url, "url 请求提交地址");
		Args.notNull(rh, "rh 响应处理器");

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setStrictMode().setBoundary("tdprequestLinkfaceBoundary");

		if (null != e) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = objectMapper.convertValue(e, Map.class);
			// 添加键值对参数
			if (MapUtils.isNotEmpty(map))
				for (Map.Entry<String, Object> param : map.entrySet()) {
					multipartEntityBuilder.addTextBody(param.getKey(), String.valueOf(param.getValue()),
							TEXT_PLAIN_UTF8);
				}
		}
		// 添加文件参数
		if (MapUtils.isNotEmpty(filebytes)) {
			AtomicInteger atomicInteger = new AtomicInteger(0);
			for (Map.Entry<String, byte[]> fileByte : filebytes.entrySet()) {
				multipartEntityBuilder.addBinaryBody(fileByte.getKey(), fileByte.getValue(),ContentType.APPLICATION_OCTET_STREAM,"file"+atomicInteger.incrementAndGet());
			}
		}

		HttpEntity entity = multipartEntityBuilder.build();

		T doPost = doPost(url, entity, rh);

		return doPost;

	}

	
	
	/**
	 * 使用默认处理器提交form表单
	 * @param url
	 * @param e
	 * @return
	 */
	public <E> String doPostUrlEncodedForm(String url, E e) {
		return this.doPostUrlEncodedForm(url, e, defaultHandler);
	}
	/**
	 * UrlEncodedForm urf8
	 * 
	 * @param url
	 * @param e
	 * @param rh
	 * @return
	 */
	public <T, E> T doPostUrlEncodedForm(String url, E e, ResponseHandler<T> rh) {
		Args.notBlank(url, "url 请求提交地址");
		Args.notNull(rh, "rh 响应处理器");

		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (null != e) {
			@SuppressWarnings("unchecked")
			Map<String, Object> params = objectMapper.convertValue(e, Map.class);

			if (CollectionUtils.isNotEmpty(pairs)) {
				for (Map.Entry<String, Object> param : params.entrySet()) {
					pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
				}
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, Consts.UTF_8);

		T doPost = doPost(url, entity, rh);

		return doPost;
	}

}
