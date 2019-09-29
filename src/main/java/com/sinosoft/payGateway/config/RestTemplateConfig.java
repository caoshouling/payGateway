package com.sinosoft.payGateway.config;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 使用HttpClient连接池管理
 * @author CSL
 *
 */
@Configuration
public class RestTemplateConfig {
	
	
    /**
     * 长连接存活时间，单位为s
     */
    private final static  int keep_alive_time = 60;
     
     
    /**
     * 连接到服务器，5秒连接不上，就超时。正常5秒内能连上
     */
 	private final static  int connectTimeout = 5000;
 	
 	/**
 	 * 从连接池中获取连接：20秒内超时。
 	 */
 	private final static int connectRequestTimeout = 2000;
 	
 	/**
 	 * 等待业务处理超时时间：这里设置5分钟。如果时间不够，要么服务端系统出问题，要么设计不合理。
 	 */
 	private final static  int socketTimeout = 300000;

 	
 	
 	 @Bean
     public RestTemplate restTemplate() {

         return new RestTemplate(httpRequestFactory());

     }
     @Bean
     public ClientHttpRequestFactory httpRequestFactory() {

         return new HttpComponentsClientHttpRequestFactory(closeableHttpClient());

     }
     @Bean
 	public RequestConfig requestConfig(){
 		return RequestConfig.custom()
 				.setConnectionRequestTimeout(connectRequestTimeout)
 				.setConnectTimeout(connectTimeout)
 				.setSocketTimeout(socketTimeout)
 				.build();
 	}
     @Bean("CloseableHttpClient")
     public CloseableHttpClient closeableHttpClient(){
     	CloseableHttpClient httpclient = HttpClients.custom()
                 .setConnectionManager(poolingClientConnectionManager())
                 .setDefaultRequestConfig(requestConfig())
                  //重试策略
                 .setRetryHandler(httpRequestRetryHandler())
                 .build();
     	
     	return httpclient;
  
    }
     
    @Bean("PoolingHttpClientConnectionManager")
	public PoolingHttpClientConnectionManager poolingClientConnectionManager(){
		
	   /**
		 * 长连接的使用： 如果请求过后，超过了60秒空闲，那么自动回收。
		 *  原因：一些HTTP服务器使用非标准的Keep-Alive标头来向客户端通信它们打算在服务器端保持连接的时间段（以秒为单位）。
		 *     如果响应中不存在Keep-Alive头，HttpClient会假定连接可以无限期地保持活动，即永久保持。
		 *     因为如果无限制，那么连接一直只能供这个host使用，不释放就不能用来调用其他服务，所以需要我们设置关闭长连接的时间。
		 *  说明：重写ConnectionKeepAliveStrategy的方式也能实现，如果这里使用了keep_alive_time同时也定义了ConnectionKeepAliveStrategy
		 *      那么httpClient会使用较小的那个值。
		*/
    	PoolingHttpClientConnectionManager poolHttpcConnManager = new PoolingHttpClientConnectionManager(keep_alive_time, TimeUnit.SECONDS);
		//设置整个连接池最大连接数，默认20
		poolHttpcConnManager.setMaxTotal(200);
		// 路由基数:默认2。 对于每一个服务器host的请求，最多40个链接。如果超过40个小于
		poolHttpcConnManager.setDefaultMaxPerRoute(40);
	
		return poolHttpcConnManager;
	}
    
    public HttpRequestRetryHandler  httpRequestRetryHandler(){
    	   
	    // 请求重试处理
	    HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
	        public boolean retryRequest(IOException exception,
	                int executionCount, HttpContext context) {
	        	
	            if (executionCount >= 5) {// 如果已经重试了5次，就放弃
	                return false;
	            }
	            //业务处理超时，不重试
	            if (exception instanceof SocketTimeoutException) {
	                return false;
	            }
	            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
	                return true;
	            }
	            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
	                return false;
	            }
	            if (exception instanceof InterruptedIOException) {// 超时
	                return false;
	            }
	            if (exception instanceof UnknownHostException) {// 目标服务器不可达
	                return false;
	            }
	            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
	                return false;
	            }
	            if (exception instanceof SSLException) {// SSL握手异常
	                return false;
	            }
	
	            HttpClientContext clientContext = HttpClientContext
	                    .adapt(context);
	            HttpRequest request = clientContext.getRequest();
	            // 如果请求是幂等的，就再次尝试
	            if (!(request instanceof HttpEntityEnclosingRequest)) {
	                return true;
	            }
	            return false;
	        }
	     };
     
     return httpRequestRetryHandler;
    }
    

   
}
