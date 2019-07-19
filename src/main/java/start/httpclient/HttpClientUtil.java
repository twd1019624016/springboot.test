package start.httpclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	@Autowired
	private  ObjectMapper objectMapper = new ObjectMapper();

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
	  /**
     * 执行请求核心方法
     *
     * @param httpUriRequest
     * @param rh
     * @return
     */

    public <T> T doRequest(HttpUriRequest httpUriRequest, ResponseHandler<T> rh) {

        return doRequest(httpUriRequest, null, rh);


    }

    public <T> T doRequest(HttpUriRequest httpUriRequest, Map<String, String> headers, ResponseHandler<T> rh) {

        Args.notNull(httpUriRequest, "httpUriRequest 请求");
        Args.notNull(rh, "rh 响应处理器");

        T result = null;
        try {

            if (MapUtils.isNotEmpty(headers)) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpUriRequest.addHeader(entry.getKey(), entry.getValue());
                }
            }

            result = httpClient.execute(httpUriRequest, rh);

        } catch (ClientProtocolException e) {
            logger.error("请求出错 {}", e.getMessage());
        } catch (IOException e) {
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
	
	public <T> T doPost(String url, HttpEntity entity, Map<String, String> headers, ResponseHandler<T> rh) {

        Args.notBlank(url, "url 请求提交地址");
        Args.notNull(rh, "rh 响应处理器");

        HttpPost post = new HttpPost(url);
        post.setEntity(entity);

        T doRequest = doRequest(post, headers, rh);

        return doRequest;


    }
    /**
     * 设置 请求级别的 requestConfig
     *
    **/
    public <T> T doPost(String url, HttpEntity entity, Map<String, String> headers, ResponseHandler<T> rh
            ,RequestConfig requestConfig) {

        Args.notBlank(url, "url 请求提交地址");
        Args.notNull(rh, "rh 响应处理器");

        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        post.setEntity(entity);

        T doRequest = doRequest(post, headers, rh);

        return doRequest;


    }
    /**
     * get 请求
     * @param url
     * @param rh
     * @return
     */
    public <T> T doGet(String url, ResponseHandler<T> rh) {
        return doGet(url, null, rh);

    }

    public <T> T doGet(String url, Map<String, String> headers, ResponseHandler<T> rh) {

        Args.notBlank(url, "url 请求提交地址");
        Args.notNull(rh, "rh 响应处理器");

        HttpGet get = new HttpGet(url);
        T doRequest = doRequest(get, headers, rh);

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
	
	   /**
     * 提交json 数据
     *
     * @param url
     * @param e
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public <E> String doPostJSON(String url, E e) throws JsonProcessingException, UnsupportedEncodingException {
        Args.notBlank(url, "url 请求提交地址");

        return this.doPostJSON(url, e, defaultHandler, Boolean.FALSE);

    }

    /**
     * 提交json 数据
     *
     * @param url
     * @param e
     * @param isUrlEncoder 是否需要url编码
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public <E> String doPostJSON(String url, E e, Boolean isUrlEncoder, Boolean isUrlDecoder)
            throws JsonProcessingException, UnsupportedEncodingException {
        Args.notBlank(url, "url 请求提交地址");

        String doPostJSON = this.doPostJSON(url, e, defaultHandler, isUrlEncoder);

        if (isUrlDecoder && !StringUtils.EMPTY.equals(doPostJSON)) {
            doPostJSON = URLDecoder.decode(doPostJSON, "UTF-8");
        }
        return doPostJSON;

    }

    /**
     * 提交json 数据
     *
     * @param url
     * @param e
     * @param rh
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public <T, E> T doPostJSON(String url, E e, ResponseHandler<T> rh, Boolean isUrlEncoder)
            throws JsonProcessingException, UnsupportedEncodingException {
        Args.notBlank(url, "url 请求提交地址");
        Args.notNull(rh, "rh 响应处理器");

        String writeValueAsString = objectMapper.writeValueAsString(e);

        if (isUrlEncoder) {
            writeValueAsString = URLEncoder.encode(writeValueAsString, "UTF-8");
        }
        StringEntity entity = new StringEntity(writeValueAsString, ContentType.APPLICATION_JSON);

        T doPost = doPost(url, entity, rh);

        return doPost;
    }

    /**
     * 提交json 数据
     *
     * @param url
     * @param e
     * @param rh
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public <T, E> T doPostJSON(String url, E e, Map<String, String> headers, ResponseHandler<T> rh)
            throws JsonProcessingException {

        Args.notBlank(url, "url 请求提交地址");
        Args.notNull(rh, "rh 响应处理器");

        String writeValueAsString = objectMapper.writeValueAsString(e);

        logger.info("http post-json 调用外部接口，请求 url={},请求参数 param={}", url, writeValueAsString);

        StringEntity entity = new StringEntity(writeValueAsString, ContentType.APPLICATION_JSON);
        T doPost = doPost(url, entity, headers, rh);

        return doPost;
    }

    private <T, E> T doPostJSON(String url, E e, Object o, ResponseHandler<T> rh, RequestConfig requestConfig)
            throws JsonProcessingException {
        Args.notBlank(url, "url 请求提交地址");
        Args.notNull(rh, "rh 响应处理器");

        String writeValueAsString = objectMapper.writeValueAsString(e);

        logger.info("http post-json 调用外部接口，请求 url={},请求参数 param={}", url, writeValueAsString);

        StringEntity entity = new StringEntity(writeValueAsString, ContentType.APPLICATION_JSON);
        T doPost = doPost(url, entity, null, rh,requestConfig);

        return doPost;

    }

    public <T, E> T doPostJSON(String url, E e, ResponseHandler<T> rh)
            throws IOException {
         return this.doPostJSON(url,e,null,rh);
    }

    public <T, E> T doPostJSON(String url, E e, ResponseHandler<T> rh,RequestConfig requestConfig)
            throws IOException {
        return this.doPostJSON(url,e,null,rh,requestConfig);
    }



    public <T, E> T doGet(String url, E e, Map<String, String> headers, ResponseHandler<T> rh) throws IOException {

        Args.notBlank(url, "url 请求提交地址");
        Args.notNull(rh, "rh 响应处理器");

        if (Objects.nonNull(e)) {
            Map<String, Object> map = objectMapper.convertValue(e, new TypeReference<Map<String, Object>>() {
            });
            if (MapUtils.isNotEmpty(map)) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(map.size());
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    if (Objects.nonNull(entry.getKey())) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString()));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs), TEXT_PLAIN_UTF8.getCharset());
            }
        }

        logger.info("http post-get 调用外部接口，请求 url={}", url);
        return doGet(url,headers, rh);
    }

    public <E> String doGet(String url, E e) throws IOException {
        return doGet(url, e, null,defaultHandler);

    }

    public <T, E> T  doGet(String url, E e, ResponseHandler<T> rh) throws IOException {
        return doGet(url, e, null,rh);

    }

}
