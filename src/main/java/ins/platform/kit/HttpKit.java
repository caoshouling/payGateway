package ins.platform.kit;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.payGateway.common.SpringTool;
import com.sinosoft.payGateway.exception.BusinessException;
import com.sinosoft.payGateway.exception.ErrorCode;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
 
/**
 * 
 * HttpClient知识：
 * 
 * 1.服务器启动但接口不存在，立即返回HTML页面：Error 404--Not Found  The server has not found anything matching the Request-URI
 * 2.IP或者端口被拒绝，立即返回： java.net.ConnectException: Connection refused: connect
 * 3.IP不存在：java.net.SocketTimeoutException: connect timed out            ---受connectTimeout参数影响
 * 4.应用处理太慢了，响应超时：java.net.SocketTimeoutException: Read timed out   ---受socketTimeout参数影响
 * 
 * @author sinosoft
 *
 */
public class HttpKit {
    /**
     * 文件扩展名与MineType的映射
     */
	public final static Map<String ,String >  EXTENSION_MIMETYPE_MAP = new HashMap<String,String>();
	public final static int socketTimeout = 120000;// 服务器开始处理数据到响应的超时时间,默认两分钟
	public final static int connectTimeout = 10000;// 建立连接TCP链接超时时间，一般如果地址不存在时会超时
	public final static int ConnectionRequestTimeout = 20000;// 从连接池中获取连接的时间
	public static Logger logger = LoggerFactory.getLogger(HttpKit.class);
	static{
		
		EXTENSION_MIMETYPE_MAP.put("*","application/octet-stream");
		EXTENSION_MIMETYPE_MAP.put("css","text/css");
		EXTENSION_MIMETYPE_MAP.put("doc","application/msword");
		EXTENSION_MIMETYPE_MAP.put("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		EXTENSION_MIMETYPE_MAP.put("gif","image/gif");
		EXTENSION_MIMETYPE_MAP.put("gz","application/x-gzip");
		EXTENSION_MIMETYPE_MAP.put("htm","text/html");
		EXTENSION_MIMETYPE_MAP.put("html","text/html");
		EXTENSION_MIMETYPE_MAP.put("shtml","text/html");
		EXTENSION_MIMETYPE_MAP.put("jpe","image/jpeg");
		EXTENSION_MIMETYPE_MAP.put("jpeg","image/jpeg");
		EXTENSION_MIMETYPE_MAP.put("jpg","image/jpeg");
		EXTENSION_MIMETYPE_MAP.put("png","image/png");
		EXTENSION_MIMETYPE_MAP.put("bmp","image/bmp");
		EXTENSION_MIMETYPE_MAP.put("js","application/x-javascript");
		EXTENSION_MIMETYPE_MAP.put("pdf","application/pdf");
		EXTENSION_MIMETYPE_MAP.put("pps","application/vnd.ms-powerpoint");
		EXTENSION_MIMETYPE_MAP.put("ppt","application/vnd.ms-powerpoint");
		EXTENSION_MIMETYPE_MAP.put("tar","application/x-tar");
		EXTENSION_MIMETYPE_MAP.put("txt","text/plain");
		EXTENSION_MIMETYPE_MAP.put("xml","text/xml");
		EXTENSION_MIMETYPE_MAP.put("xls","application/vnd.ms-excel");
		EXTENSION_MIMETYPE_MAP.put("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		EXTENSION_MIMETYPE_MAP.put("wps","application/vnd.ms-works");
		EXTENSION_MIMETYPE_MAP.put("wks","application/vnd.ms-works");
		EXTENSION_MIMETYPE_MAP.put("zip","application/zip");
	}
	/**
	 * 获取Mime类型
	 * @param fileName
	 * @return
	 */
	public static String getMimeType(String fileName){
		String extension = "";
		String mineType = "";
		if(StrKit.isNoEmpty(fileName)){
			extension = fileName.substring(fileName.lastIndexOf(".")+1);
			mineType = EXTENSION_MIMETYPE_MAP.get(extension) ;
		}
		if(StrKit.isEmpty(mineType)){
			mineType = EXTENSION_MIMETYPE_MAP.get("*") ;
		}
		return  mineType;
	}
	
	/**
	 * 获取访问者IP 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {

		String ip = null;

		// X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader("X-Forwarded-For");
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// Proxy-Client-IP：apache 服务代理
			ipAddresses = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// WL-Proxy-Client-IP：weblogic 服务代理
			ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// HTTP_CLIENT_IP：有些代理服务器
			ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// X-Real-IP：nginx服务代理
			ipAddresses = request.getHeader("X-Real-IP");
		}

		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ipAddresses != null && ipAddresses.length() != 0) {
			ip = ipAddresses.split(",")[0];
		}

		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取来访者的浏览器版本
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestBrowserInfo(HttpServletRequest request) {
		String browserVersion = null;
		String header = request.getHeader("user-agent");
		if (header == null || header.equals("")) {
			return "";
		}
		if (header.indexOf("MSIE") > 0) {
			browserVersion = "IE";
		} else if (header.indexOf("Firefox") > 0) {
			browserVersion = "Firefox";
		} else if (header.indexOf("Chrome") > 0) {
			browserVersion = "Chrome";
		} else if (header.indexOf("Safari") > 0) {
			browserVersion = "Safari";
		} else if (header.indexOf("Camino") > 0) {
			browserVersion = "Camino";
		} else if (header.indexOf("Konqueror") > 0) {
			browserVersion = "Konqueror";
		}
		return browserVersion;
	}

	/**
	 * 获取系统版本信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestSystemInfo(HttpServletRequest request) {
		String systenInfo = null;
		String header = request.getHeader("user-agent");
		if (header == null || header.equals("")) {
			return "";
		}
		// 得到用户的操作系统
		if (header.indexOf("NT 6.0") > 0) {
			systenInfo = "Windows Vista/Server 2008";
		} else if (header.indexOf("NT 5.2") > 0) {
			systenInfo = "Windows Server 2003";
		} else if (header.indexOf("NT 5.1") > 0) {
			systenInfo = "Windows XP";
		} else if (header.indexOf("NT 6.0") > 0) {
			systenInfo = "Windows Vista";
		} else if (header.indexOf("NT 6.1") > 0) {
			systenInfo = "Windows 7";
		} else if (header.indexOf("NT 6.2") > 0) {
			systenInfo = "Windows Slate";
		} else if (header.indexOf("NT 6.3") > 0) {
			systenInfo = "Windows 9";
		} else if (header.indexOf("NT 5") > 0) {
			systenInfo = "Windows 2000";
		} else if (header.indexOf("NT 4") > 0) {
			systenInfo = "Windows NT4";
		} else if (header.indexOf("Me") > 0) {
			systenInfo = "Windows Me";
		} else if (header.indexOf("98") > 0) {
			systenInfo = "Windows 98";
		} else if (header.indexOf("95") > 0) {
			systenInfo = "Windows 95";
		} else if (header.indexOf("Mac") > 0) {
			systenInfo = "Mac";
		} else if (header.indexOf("Unix") > 0) {
			systenInfo = "UNIX";
		} else if (header.indexOf("Linux") > 0) {
			systenInfo = "Linux";
		} else if (header.indexOf("SunOS") > 0) {
			systenInfo = "SunOS";
		}
		return systenInfo;
	}

	/**
	 * 获取来访者的主机名称
	 * 
	 * @param ip
	 * @return
	 */
	public static String getHostName(String ip) {
		InetAddress inet;
		try {
			inet = InetAddress.getByName(ip);
			return inet.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 命令获取mac地址
	 * 
	 * @param cmd
	 * @return
	 */
	private static String callCmd(String[] cmd) {
		String result = "";
		String line = "";
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 *
	 *
	 * @param cmd
	 *            第一个命令
	 *
	 * @param another
	 *            第二个命令
	 *
	 * @return 第二个命令的执行结果
	 *
	 */

	private static String callCmd(String[] cmd, String[] another) {
		String result = "";
		String line = "";
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);
			proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令
			proc = rt.exec(another);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 *
	 *
	 * @param ip
	 *            目标ip,一般在局域网内
	 *
	 * @param sourceString
	 *            命令处理的结果字符串
	 *
	 * @param macSeparator
	 *            mac分隔符号
	 *
	 * @return mac地址，用上面的分隔符号表示
	 *
	 */

	private static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
		String result = "";
		String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(sourceString);
		while (matcher.find()) {
			result = matcher.group(1);
			if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
				break; // 如果有多个IP,只匹配本IP对应的Mac.
			}
		}
		return result;
	}

	/**
	 * @param ip
	 *            目标ip
	 * @return Mac Address
	 *
	 */

	private static String getMacInWindows(final String ip) {
		String result = "";
		String[] cmd = { "cmd", "/c", "ping " + ip };
		String[] another = { "cmd", "/c", "arp -a" };
		String cmdResult = callCmd(cmd, another);
		result = filterMacAddress(ip, cmdResult, "-");
		return result;
	}

	/**
	 *
	 * @param ip
	 *            目标ip
	 * @return Mac Address
	 *
	 */
	private static String getMacInLinux(final String ip) {
		String result = "";
		String[] cmd = { "/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a" };
		String cmdResult = callCmd(cmd);
		result = filterMacAddress(ip, cmdResult, ":");
		return result;
	}

	/**
	 * 获取MAC地址
	 *
	 * @return 返回MAC地址
	 */
	public static String getMacAddress(String ip) {
		String macAddress = "";
		macAddress = getMacInWindows(ip).trim();
		if (macAddress == null || "".equals(macAddress)) {
			macAddress = getMacInLinux(ip).trim();
		}
		return macAddress;
	}
	/**
	 * Post请求，模拟表单提交
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doPostForm(String postUrl, Map<String,String> params,String encoding, int socketTimeOutParam,String serviceName) throws Exception {
        String resPonse = "";
        //注意：使用连接池时，不能关闭。 而单独使用时必须finally中关闭 
		CloseableHttpClient httpClient = createPoolStyleCloseableHttpClient();
		try {
			HttpPost httpPost = new HttpPost("http://httpbin.org/post");
			httpPost.setURI(URI.create(postUrl));
		    //  设置请求和传输超时时间
			if (socketTimeOutParam <= 0) {
				socketTimeOutParam = socketTimeout;
			}
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(socketTimeOutParam)
					.setConnectTimeout(connectTimeout).build();
			httpPost.setConfig(requestConfig);
			
	        List<NameValuePair> list = new ArrayList<NameValuePair>();
	        if(params != null){
	        	Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
	  	        while(iterator.hasNext()){
	  	            Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
	  	            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
	  	        }
	        }
	        if(list.size() > 0){
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,encoding);
	            httpPost.setEntity(entity);
	        }
	        CloseableHttpResponse  httpResponse = httpClient.execute(httpPost);
			
			try {	
				resPonse = EntityUtils.toString(httpResponse.getEntity(), encoding);
			} finally {
				httpResponse.close();
			}
		
		}catch (ConnectTimeoutException e) {
			logger.error("URL["+postUrl+"]连接超时："+e.getMessage());
			throw new BusinessException(ErrorCode.ThirdPart_Connect_TimeOut,"连接"+serviceName+"超时（超过"+(connectTimeout/1000)+"秒）");
			
		} catch (SocketTimeoutException e) {
			logger.error("URL["+postUrl+"]响应超时："+e.getMessage());
			throw new BusinessException(ErrorCode.ThirdPart_Socket_TimeOut, serviceName+"响应超时（超过"+(socketTimeOutParam/1000)+"秒）");
		} catch (Exception e) {
			logger.error("URL["+postUrl+"]未知异常："+e.getMessage(),e);
			throw new BusinessException(ErrorCode.ThirdPart_UnKnow_Error, serviceName+"出现未知异常");
		}
//		finally{
//			httpClient.close();
//		}
		return resPonse;
		
	}
 
	
	/**
	 * 发送Post请求
	 * @param url
	 * @param content
	 * @param encoding
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws Exception
	 */
	public static String doPost(String postUrl, String content,String encoding,int socketTimeoutMillis,String serviceName) throws  Exception {
        String resPonse = "";
        //注意：使用连接池时，不能关闭。 而单独使用时必须finally中关闭 
      	CloseableHttpClient httpClient = createPoolStyleCloseableHttpClient();
		try {
			HttpPost httpPost = new HttpPost("http://httpbin.org/post");
			httpPost.setURI(URI.create(postUrl));
			//如果没有传送超时，默认两分钟
			if (socketTimeoutMillis <= 0){
				socketTimeoutMillis = socketTimeout;
			}
			RequestConfig requestConfig = RequestConfig.custom()  
						 //从连接池中获取连接的超时时间  
	                    .setConnectionRequestTimeout(ConnectionRequestTimeout)   
	                     //与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间  
	                    .setConnectTimeout(connectTimeout) 
	                    .setSocketTimeout(socketTimeoutMillis)//socket读数据超时时间：从服务器获取响应数据的超时时间  
	                    .build();  
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new ByteArrayEntity(content.getBytes(encoding)));
			CloseableHttpResponse  httpResponse = httpClient.execute(httpPost);
			
			try {	
				resPonse = EntityUtils.toString(httpResponse.getEntity(), encoding);
			   
			} finally {
				httpResponse.close();
			}
		} catch (ConnectTimeoutException e) {
			logger.error("URL["+postUrl+"]连接超时："+e.getMessage());
			throw new BusinessException(ErrorCode.ThirdPart_Connect_TimeOut,"连接"+serviceName+"超时（超过"+(connectTimeout/1000)+"秒）");
			
		} catch (SocketTimeoutException e) {
			logger.error("URL["+postUrl+"]响应超时："+e.getMessage());
			throw new BusinessException(ErrorCode.ThirdPart_Socket_TimeOut, serviceName+"响应超时（超过"+(socketTimeout/1000)+"秒）");
		} catch (Exception e) {
			logger.error("URL["+postUrl+"]未知异常："+e.getMessage(),e);
			throw new BusinessException(ErrorCode.ThirdPart_UnKnow_Error, serviceName+"出现未知异常");
		}finally{
			//使用连接池不能关闭客户端，单独使用必须关闭
			//httpClient.close();
		}
		return resPonse;
		
	}
	/**
	 * 发送Post请求
	 * @param url
	 * @param content
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, String content,String encoding,String serviceName) throws Exception {
        
		return doPost(url, content, encoding, 0,serviceName);
		
	}
		/**
		 * 使用SOAP1.1发送消息
		 * 
		 * @param postUrl
		 * @param soapXml
		 * @param soapAction
		 * @return
		 * @throws Exception 
		 */
		public static String doPostSoap1_1(String postUrl, String soapXml,
				String soapAction, int socketTimeout,String serviceName) {
		String retStr = "";
		//注意：使用连接池时，不能关闭。 而单独使用时必须finally中关闭 
      	CloseableHttpClient closeableHttpClient = createPoolStyleCloseableHttpClient();
      	
		HttpPost httpPost = new HttpPost(postUrl);
                //  设置请求和传输超时时间
		//如果没有传送超时，默认两分钟
		if (socketTimeout <= 0){
			socketTimeout = HttpKit.socketTimeout;
		}
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction);
			StringEntity data = new StringEntity(soapXml,
					Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient
					.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
			}
			
		} catch (ConnectTimeoutException e) {
			logger.error("URL["+postUrl+"]连接超时："+e.getMessage());
			throw new BusinessException(ErrorCode.ThirdPart_Connect_TimeOut,"连接"+serviceName+"超时（超过"+(connectTimeout/1000)+"秒）");
			
		} catch (SocketTimeoutException e) {
			logger.error("URL["+postUrl+"]响应超时："+e.getMessage());
			throw new BusinessException(ErrorCode.ThirdPart_Socket_TimeOut, serviceName+"响应超时（超过"+(socketTimeout/1000)+"秒）");
		} catch (Exception e) {
			logger.error("URL["+postUrl+"]未知异常："+e.getMessage(),e);
			throw new BusinessException(ErrorCode.ThirdPart_UnKnow_Error, serviceName+"出现未知异常");
		}finally{
			// 释放资源
			//closeableHttpClient.close();
		}
		return retStr;
	}
 
	/**
	 * 使用SOAP1.2发送消息
	 * 
	 * @param postUrl
	 * @param soapXml
	 * @param soapAction
	 * @return
	 * @throws Exception 
	 */
	public static String doPostSoap1_2(String postUrl, String soapXml,
			String soapAction, int socketTimeout) throws Exception {
		String retStr = "";
		//注意：使用连接池时，不能关闭。 而单独使用时必须finally中关闭 
      	CloseableHttpClient closeableHttpClient = createPoolStyleCloseableHttpClient();
      	
		HttpPost httpPost = new HttpPost(postUrl);
                // 设置请求和传输超时时间
		//如果没有传送超时，默认两分钟
		if (socketTimeout <= 0){
			socketTimeout = HttpKit.socketTimeout;
		}
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type",
					"application/soap+xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction);
			StringEntity data = new StringEntity(soapXml,
					Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient
					.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
			}
			
		} catch (Exception e) {
			logger.error("doPostSoap1_2 exception:"+e.getMessage(), e);
			throw e;
		}finally{
			// 释放资源
			//closeableHttpClient.close();
		}
		return retStr;
	}
 
	public static String getIp(String path) {
        String url = "";
        URI effectiveURI = null;
        try {
        	URI uri = URI.create(path);
        	effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
            effectiveURI = null;
        }
        
        if (effectiveURI!=null) {
        	url = effectiveURI.toString();
        }
        return url;
        
    }
	/**
	 * 解析成查询参数
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws IORuntimeException 
	 */
	public static String getString(HttpServletRequest request) throws IORuntimeException, IOException{
		
		return IoUtil.read(request.getInputStream(), "UTF-8");
		
	}
	
	/**
	 * 通过连接池的方式，和RestTemplate共用一个连接池
	 * 
	 * @return
	 */
	public static CloseableHttpClient createPoolStyleCloseableHttpClient(){
		
		return (CloseableHttpClient) SpringTool.getBean("CloseableHttpClient");
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		
		
	}
	

}