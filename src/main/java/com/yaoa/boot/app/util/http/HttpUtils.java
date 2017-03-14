package com.yaoa.boot.app.util.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;

/**
 * HTTP工具类
 * @author chenjianhui
 */
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
public class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 *  请求对象
	 */
	private HttpRequestBase request;
	
	/**
	 *  Post, put请求的参数
	 */
	private EntityBuilder builder;
	
	/**
	 *  get, delete请求的参数
	 */
	private URIBuilder uriBuilder; 
	/**
	 *  请求类型1-post, 2-get, 3-put, 4-delete
	 */
	private int type; 
	
	/**
	 *  请求的相关配置
	 */
	private Builder config; 
	/**
	 * 构建httpClient
	 */
	private HttpClientBuilder clientBuilder; 

	private static int bufferSize = 1024;

	private ConnectionConfig connConfig;

	private SocketConfig socketConfig;

	private ConnectionSocketFactory plainSF;

	private SSLContext sslContext;

	private LayeredConnectionSocketFactory sslSF;

	private Registry<ConnectionSocketFactory> registry;

	private PoolingHttpClientConnectionManager connManager;

	private CloseableHttpClient httpClient;

	private volatile CookieStore cookieStore;

	public static String defaultEncoding = "UTF-8";

	public static String readStream(InputStream in, String encoding) {
		if (in == null) {
			return null;
		}
		try {
			InputStreamReader inReader = null;
			if (encoding == null) {
				inReader = new InputStreamReader(in, defaultEncoding);
			} else {
				inReader = new InputStreamReader(in, encoding);
			}
			char[] buffer = new char[bufferSize];
			int readLen = 0;
			StringBuffer sb = new StringBuffer();
			while ((readLen = inReader.read(buffer)) != -1) {
				sb.append(buffer, 0, readLen);
			}
			inReader.close();
			return sb.toString();
		} catch (IOException e) {
			logger.error("读取返回内容出错", e);
		}
		return null;
	}

	private HttpUtils(HttpRequestBase request) {
		this.request = request;
		this.clientBuilder = HttpClientBuilder.create();
		this.config = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT);
		// 设置连接参数
		connConfig = ConnectionConfig.custom().setCharset(Charset.forName(defaultEncoding)).build();
		socketConfig = SocketConfig.custom().setSoTimeout(100000).build();
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
		plainSF = new PlainConnectionSocketFactory();
		registryBuilder.register("http", plainSF);
		// 指定信任密钥存储对象和连接套接字工厂
		try {
			TrustManager manager = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					
				}
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					
				}
			};
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[]{manager}, null);
			sslSF = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
			registryBuilder.register("https", sslSF);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		registry = registryBuilder.build();
		// 设置连接管理器
		connManager = new PoolingHttpClientConnectionManager(registry);
		connManager.setDefaultConnectionConfig(connConfig);
		connManager.setDefaultSocketConfig(socketConfig);
		// 指定cookie存储对象
		cookieStore = new BasicCookieStore();
		// 构建客户端
		httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).setConnectionManager(connManager)
				.build();
		if (request instanceof HttpPost) {
			this.type = 1;
			this.builder = EntityBuilder.create().setParameters(new ArrayList<NameValuePair>());
		} else if (request instanceof HttpGet) {
			this.type = 2;
			this.uriBuilder = new URIBuilder();
		} else if (request instanceof HttpPut) {
			this.type = 3;
			this.builder = EntityBuilder.create().setParameters(new ArrayList<NameValuePair>());
		} else if (request instanceof HttpDelete) {
			this.type = 4;
			this.uriBuilder = new URIBuilder();
		}
	}
	
	/**
	 * 设置信任自签名证书
	 * @param keyStorePath      密钥库路径
	 * @param keyStorepass      密钥库密码
	 * @return
	 */
	@SuppressWarnings("unused")
	private SSLContext setSSLContext(String keyStorePath, String keyStorepass){
	    FileInputStream instream = null;
	    KeyStore trustStore = null;
	    try {
	        trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        instream = new FileInputStream(new File(keyStorePath));
	        trustStore.load(instream, keyStorepass.toCharArray());
	        // 相信自己的CA和所有自签名的证书
	        sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
	        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
			plainSF = new PlainConnectionSocketFactory();
			registryBuilder.register("http", plainSF);
			sslSF = new SSLConnectionSocketFactory(sslContext);
			registryBuilder.register("https", sslSF);
			registry = registryBuilder.build();
	    } catch (Exception e) {
	    	logger.error(ExceptionUtils.getFullStackTrace(e));
	    } finally {
	        try {
	            instream.close();
	        } catch (IOException e) {
	        	logger.error(ExceptionUtils.getFullStackTrace(e));
	        }
	    }
	    return sslContext;
	}

	private HttpUtils(HttpRequestBase request, HttpUtils clientUtils) {
		this(request);
		this.httpClient = clientUtils.httpClient;
		this.config = clientUtils.config;
		this.setHeaders(clientUtils.getAllHeaders());
		this.SetCookieStore(clientUtils.cookieStore);
	}

	private static HttpUtils create(HttpRequestBase request) {
		return new HttpUtils(request);
	}

	private static HttpUtils create(HttpRequestBase request, HttpUtils clientUtils) {
		return new HttpUtils(request, clientUtils);
	}

	/**
	 * 创建post请求
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static HttpUtils post(String url) {
		return create(new HttpPost(url));
	}

	/**
	 * 创建get请求(不要再URL后面拼接参数)
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static HttpUtils get(String url) {
		return create(new HttpGet(url));
	}

	/**
	 * 创建put请求
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static HttpUtils put(String url) {
		return create(new HttpPut(url));
	}

	/**
	 * 创建delete请求
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static HttpUtils delete(String url) {
		return create(new HttpDelete(url));
	}

	/**
	 * 创建post请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return
	 */
	public static HttpUtils post(URI uri) {
		return create(new HttpPost(uri));
	}

	/**
	 * 创建get请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return
	 */
	public static HttpUtils get(URI uri) {
		return create(new HttpGet(uri));
	}

	/**
	 * 创建post请求
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static HttpUtils post(String url, HttpUtils clientUtils) {
		return create(new HttpPost(url), clientUtils);
	}

	/**
	 * 创建get请求
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static HttpUtils get(String url, HttpUtils clientUtils) {
		return create(new HttpGet(url), clientUtils);
	}

	/**
	 * 创建post请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return
	 */
	public static HttpUtils post(URI uri, HttpUtils clientUtils) {
		return create(new HttpPost(uri), clientUtils);
	}

	/**
	 * 创建get请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return
	 */
	public static HttpUtils get(URI uri, HttpUtils clientUtils) {
		return create(new HttpGet(uri), clientUtils);
	}

	/**
	 * 添加参数
	 * 
	 * @param parameters
	 * @return
	 */
	public HttpUtils setParameters(final NameValuePair... parameters) {
		if (builder != null) {
			builder.setParameters(parameters);
		} else {
			uriBuilder.setParameters(Arrays.asList(parameters));
		}
		return this;
	}

	/**
	 * 添加参数
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpUtils addParameter(final String name, final String value) {
		if (builder != null) {
			builder.getParameters().add(new BasicNameValuePair(name, value));
		} else {
			uriBuilder.addParameter(name, value);
		}
		return this;
	}

	/**
	 * 添加参数
	 * 
	 * @param parameters
	 * @return
	 */
	public HttpUtils addParameters(final NameValuePair... parameters) {
		if (builder != null) {
			builder.getParameters().addAll(Arrays.asList(parameters));
		} else {
			uriBuilder.addParameters(Arrays.asList(parameters));
		}
		return this;
	}

	/**
	 * 设置请求参数,会覆盖之前的参数
	 * 
	 * @param parameters
	 * @return
	 */
	public HttpUtils setParameters(final Map<String, String> parameters) {
		NameValuePair[] values = new NameValuePair[parameters.size()];
		int i = 0;

		for (Entry<String, String> parameter : parameters.entrySet()) {
			values[i++] = new BasicNameValuePair(parameter.getKey(), parameter.getValue());
		}
		setParameters(values);
		return this;
	}

	/**
	 * 设置请求参数,会覆盖之前的参数
	 * 
	 * @param file
	 * @return
	 */
	public HttpUtils setParameter(final File file) {
		if (builder != null) {
			builder.setFile(file);
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}

	/**
	 * 设置请求参数,会覆盖之前的参数
	 * 
	 * @param binary
	 * @return
	 */
	public HttpUtils setParameter(final byte[] binary) {
		if (builder != null) {
			builder.setBinary(binary);
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}

	/**
	 * 设置请求参数,会覆盖之前的参数
	 * 
	 * @param serializable
	 * @return
	 */
	public HttpUtils setParameter(final Serializable serializable) {
		if (builder != null) {
			builder.setSerializable(serializable);
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}

	/**
	 * 设置参数为Json对象
	 * @param parameter 参数对象
	 * @return
	 */
	public HttpUtils setParameterJson(final Object parameter) {
		if (builder != null) {
			try {
				setContentType(ContentType.APPLICATION_JSON);
				builder.setBinary(objMapper.writeValueAsBytes(parameter));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}
	
	/**
	 * 设置参数为Json对象
	 * @param parameter 参数对象
	 * @return
	 */
	public HttpUtils setParameterXml(final Object parameter) {
		if (builder != null) {
			try {
				setContentType(ContentType.APPLICATION_XML);
				builder.setBinary(xmlMapper.writeValueAsBytes(parameter));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}

	/**
	 * 设置请求参数,会覆盖之前的参数
	 * 
	 * @param stream
	 * @return
	 */
	public HttpUtils setParameter(final InputStream stream) {
		if (builder != null) {
			builder.setStream(stream);
		} else {
			throw new UnsupportedOperationException();
		}
		return this;
	}

	/**
	 * 设置请求参数,会覆盖之前的参数
	 * 
	 * @param text
	 * @return
	 */
	public HttpUtils setParameter(final String text) {
		if (builder != null) {
			builder.setText(text);
		} else {
			uriBuilder.setParameters(URLEncodedUtils.parse(text, Consts.UTF_8));
		}
		return this;
	}

	/**
	 * 设置内容编码
	 * 
	 * @param encoding
	 * @return
	 */
	public HttpUtils setContentEncoding(final String encoding) {
		if (builder != null)
			builder.setContentEncoding(encoding);
		return this;
	}

	/**
	 * 设置ContentType
	 * 
	 * @param contentType
	 * @return
	 */
	public HttpUtils setContentType(ContentType contentType) {
		if (builder != null)
			builder.setContentType(contentType);
		return this;
	}

	/**
	 * 设置ContentType
	 * 
	 * @param mimeType
	 * @param charset
	 *            内容编码
	 * @return
	 */
	public HttpUtils setContentType(final String mimeType, final Charset charset) {
		if (builder != null)
			builder.setContentType(ContentType.create(mimeType, charset));
		return this;
	}

	/**
	 * 添加参数
	 * 
	 * @param parameters
	 * @return
	 */
	public HttpUtils addParameters(Map<String, String> parameters) {
		List<NameValuePair> values = new ArrayList<>(parameters.size());
		for (Entry<String, String> parameter : parameters.entrySet()) {
			values.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
		}
		if (builder != null) {
			builder.getParameters().addAll(values);
		} else {
			uriBuilder.addParameters(values);
		}
		return this;
	}

	/**
	 * 添加Header
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpUtils addHeader(String name, String value) {
		request.addHeader(name, value);
		return this;
	}

	/**
	 * 添加Header
	 * 
	 * @param headers
	 * @return
	 */
	public HttpUtils addHeaders(Map<String, String> headers) {
		for (Entry<String, String> header : headers.entrySet()) {
			request.addHeader(header.getKey(), header.getValue());
		}
		return this;
	}

	/**
	 * 设置Header,会覆盖所有之前的Header
	 * 
	 * @param headers
	 * @return
	 */
	public HttpUtils setHeaders(Map<String, String> headers) {
		Header[] headerArray = new Header[headers.size()];
		int i = 0;
		for (Entry<String, String> header : headers.entrySet()) {
			headerArray[i++] = new BasicHeader(header.getKey(), header.getValue());
		}
		request.setHeaders(headerArray);
		return this;
	}

	/**
	 * 设置Header,会覆盖所有之前的Header
	 * 
	 * @param headers
	 * @return
	 */
	public HttpUtils setHeaders(Header[] headers) {
		request.setHeaders(headers);
		return this;
	}

	/**
	 * 获取所有Header
	 * 
	 * @return
	 */
	public Header[] getAllHeaders() {
		return request.getAllHeaders();
	}

	/**
	 * 移除指定name的Header列表
	 * 
	 * @param name
	 */
	public HttpUtils removeHeaders(String name) {
		request.removeHeaders(name);
		return this;
	}

	/**
	 * 移除指定的Header
	 * 
	 * @param header
	 */
	public HttpUtils removeHeader(Header header) {
		request.removeHeader(header);
		return this;
	}

	/**
	 * 移除指定的Header
	 * 
	 * @param name
	 * @param value
	 */
	public HttpUtils removeHeader(String name, String value) {
		request.removeHeader(new BasicHeader(name, value));
		return this;
	}

	/**
	 * 是否存在指定name的Header
	 * 
	 * @param name
	 * @return
	 */
	public boolean containsHeader(String name) {
		return request.containsHeader(name);
	}

	/**
	 * 获取Header的迭代器
	 * 
	 * @return
	 */
	public HeaderIterator headerIterator() {
		return request.headerIterator();
	}

	/**
	 * 获取协议版本信息
	 * 
	 * @return
	 */
	public ProtocolVersion getProtocolVersion() {
		return request.getProtocolVersion();
	}

	/**
	 * 获取请求Url
	 * 
	 * @return
	 */
	public URI getURI() {
		return request.getURI();
	}

	/**
	 * 设置请求Url
	 * 
	 * @return
	 */
	public HttpUtils setURI(URI uri) {
		request.setURI(uri);
		return this;
	}

	/**
	 * 设置请求Url
	 * 
	 * @return
	 */
	public HttpUtils setURI(String uri) {
		return setURI(URI.create(uri));
	}

	/**
	 * 设置一个CookieStore
	 * 
	 * @param cookieStore
	 * @return
	 */
	public HttpUtils SetCookieStore(CookieStore cookieStore) {
		if (cookieStore == null)
			return this;
		this.cookieStore = cookieStore;
		return this;
	}

	/**
	 * 添加Cookie
	 * 
	 * @param cookie
	 * @return
	 */
	public HttpUtils addCookie(Cookie... cookies) {
		if (cookies == null)
			return this;

		for (int i = 0; i < cookies.length; i++) {
			cookieStore.addCookie(cookies[i]);
		}
		return this;
	}

	/**
	 * 设置Socket超时时间,单位:ms
	 * 
	 * @param socketTimeout
	 * @return
	 */
	public HttpUtils setSocketTimeout(int socketTimeout) {
		config.setSocketTimeout(socketTimeout);
		return this;
	}

	/**
	 * 设置连接超时时间,单位:ms
	 * 
	 * @param connectTimeout
	 * @return
	 */
	public HttpUtils setConnectTimeout(int connectTimeout) {
		config.setConnectTimeout(connectTimeout);
		return this;
	}

	/**
	 * 设置请求超时时间,单位:ms
	 * 
	 * @param connectionRequestTimeout
	 * @return
	 */
	public HttpUtils setConnectionRequestTimeout(int connectionRequestTimeout) {
		config.setConnectionRequestTimeout(connectionRequestTimeout);
		return this;
	}

	/**
	 * 执行请求
	 * 
	 * @return
	 */
	public ResponseWrap execute() {
		settingRequest();
		if (httpClient == null) {
			httpClient = clientBuilder.build();
		}
		try {
			HttpClientContext context = HttpClientContext.create();
			CloseableHttpResponse response = httpClient.execute(request, context);
			return new ResponseWrap(httpClient, request, response, context, objMapper, xmlMapper);
		} catch (IOException e) {
			shutdown();
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 执行请求
	 * 
	 * @param responseHandler
	 * @return
	 */
	public <T> T execute(final ResponseHandler<? extends T> responseHandler) {
		settingRequest();
		if (httpClient == null)
			httpClient = clientBuilder.build();
		try {
			return httpClient.execute(request, responseHandler);
		} catch (IOException e) {
			shutdown();
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 关闭连接
	 */
	public void shutdown() {
		connManager.close();
	}

	private void settingRequest() {
		URI uri = null;
		if (uriBuilder != null) {
			try {
				uri = uriBuilder.setPath(request.getURI().toString()).build();
			} catch (URISyntaxException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		HttpEntity httpEntity = null;
		switch (type) {
		case 1:
			httpEntity = builder.build();
			if (httpEntity.getContentLength() > 0)
				((HttpPost) request).setEntity(builder.build());
			break;

		case 2:
			HttpGet get = ((HttpGet) request);
			get.setURI(uri);
			break;

		case 3:
			httpEntity = builder.build();
			if (httpEntity.getContentLength() > 0)
				((HttpPut) request).setEntity(httpEntity);
			break;

		case 4:
			HttpDelete delete = ((HttpDelete) request);
			delete.setURI(uri);
			break;
		}
		clientBuilder.setDefaultCookieStore(cookieStore);
		request.setConfig(config.build());
	}

	private static ObjectMapper objMapper;
	
	private static XmlMapper xmlMapper;

	static {
		objMapper = new ObjectMapper();
		objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);//设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		objMapper.enable(Feature.ALLOW_COMMENTS);
		objMapper.enable(Feature.ALLOW_SINGLE_QUOTES);
		
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		xmlMapper = new XmlMapper(module);
		xmlMapper.setSerializationInclusion(Include.NON_DEFAULT);//设置序列化不包含Java对象中为空的属性
		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}
