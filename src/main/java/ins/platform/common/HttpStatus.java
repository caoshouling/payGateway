package ins.platform.common;

/**
 * Http状态码，和appache定义的一样
 * @see org.apache.http.HttpStatus
 * @author CSL
 * 
 * 
	所有状态码的第一个数字代表了响应的五种状态之一。

	1xx 消息
	
	100客户端应该继续请求
	101服务交换协议
	102服务器已经收到请求并正在处理
	103恢复终止的PUT或POST请求
	122URI长度超过2083个字符
	
	2xx 成功
	
	200请求成功，正常响应
	201请求已完成，新的资源创建
	202已经接受请求，等待接受处理
	203服务器已成功处理了请求，但返回的信息可能来自另一来源
	204请求已被处理，但没有任何内容返回
	205没有新的内容，但浏览器应该重置它所显示的内容
	206服务器成功处理了部分 GET 请求
	207XML消息，可以包含一系列独立的响应
	208results previously returned 
	226request fulfilled, reponse is instance-manipulations
	
	3xx 重定向
	
	300客户请求的文档可以在多个位置找到
	301客户请求的文档已经被永久移动到其他地方
	302客户请求的文档被临时移动到其他地方
	303对应当前请求的响应可以在另一个URI上被找到
	304请求资源从上次请求以来未被修改
	305客户请求的文档应该通过指定的代理服务器提取
	306在最新版的规范中，306状态码已经不再被使用
	307请求的资源现在临时从不同的URI响应请求
	308connect again to a different URI using the same method
	
	4xx 客户端错误
	
	400请求包含语法错误
	401访问被拒绝，客户试图未经授权访问受密码保护的页面
	402该状态码是为了将来可能的需求而预留的
	403资源不可用
	404请求的资源在服务器上不存在
	405请求方法（GET、POST、HEAD等）对指定的资源不适用
	406请求资源的MIME类型和客户在Accpet头中所指定的不兼容
	407要求进行代理身份验证
	408在服务器许可的等待时间内，客户一直没有发出任何请求
	409由于请求和资源的当前状态相冲突，因此请求不能成功
	410请求资源不可用
	411请求没有指定被请求资源的长度
	412请求头中指定的一些前提条件失败
	413目标文档的大小超过服务器当前愿意处理的大小
	414URI长度超过服务器能够处理长度的上限
	415不支持的MIME媒体类型
	416服务器不能处理客户在请求中指定的Range头
	417在请求头Expect中指定的预期内容无法满足服务器
	418I'm a teapot
	420Twitter rate limiting
	422语义错误，无法响应请求
	423请求资源被锁定
	424由于之前的某个请求发生的错误，导致当前请求失败
	426客户端应当切换到TLS/1.0
	428origin server requires the request to be conditional
	429user has sent too many requests in a given amount of time
	431server is unwilling to process the request
	444server returns no information and closes the connection
	449request should be retried after performing action
	450Windows Parental Controls blocking access to webpage
	451the server cannot reach the client's mailbox
	499connection closed by client while HTTP server is processing
	
	5xx 服务器错误
	
	500服务器遇到了意料不到的情况，不能完成客户的请求
	501服务器不支持实现请求所需要的功能
	502从上游服务器接收到无效的响应
	503服务不可用
	504网关不能及时地从远程服务器获得应答
	505服务器不支持请求中使用的HTTP版本
	506content negotiation for the request results in a circular reference
	507服务器无法存储完成请求所必须的内容
	508服务器处理请求时检测到一个无限循环
	509超过带宽的限制 服务器暂时无法提供服务
	510further extensions to the request are required
	511客户端需要进行身份验证以获得网络访问
	598network read timeout behind the proxy 
	599network connect timeout behind the proxy
 *
 */
public interface HttpStatus {
	
	  // --- 1xx Informational ---

    /** {@code 100 Continue} (HTTP/1.1 - RFC 2616) */
    public static final int SC_CONTINUE = 100;
    /** {@code 101 Switching Protocols} (HTTP/1.1 - RFC 2616)*/
    public static final int SC_SWITCHING_PROTOCOLS = 101;
    /** {@code 102 Processing} (WebDAV - RFC 2518) 服务器已经收到请求并正在处理 */
    public static final int SC_PROCESSING = 102;

    // --- 2xx Success ---

    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    public static final int SC_OK = 200;
    /** {@code 201 Created} (HTTP/1.0 - RFC 1945) */
    public static final int SC_CREATED = 201;
    /** {@code 202 Accepted} (HTTP/1.0 - RFC 1945) */
    public static final int SC_ACCEPTED = 202;
    /** {@code 203 Non Authoritative Information} (HTTP/1.1 - RFC 2616) */
    public static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;
    /** {@code 204 No Content} (HTTP/1.0 - RFC 1945) */
    public static final int SC_NO_CONTENT = 204;
    /** {@code 205 Reset Content} (HTTP/1.1 - RFC 2616) */
    public static final int SC_RESET_CONTENT = 205;
    /** {@code 206 Partial Content} (HTTP/1.1 - RFC 2616) */
    public static final int SC_PARTIAL_CONTENT = 206;
    /**
     * {@code 207 Multi-Status} (WebDAV - RFC 2518)
     * or
     * {@code 207 Partial Update OK} (HTTP/1.1 - draft-ietf-http-v11-spec-rev-01?)
     */
    public static final int SC_MULTI_STATUS = 207;

    // --- 3xx Redirection ---

    /** {@code 300 Mutliple Choices} (HTTP/1.1 - RFC 2616) */
    public static final int SC_MULTIPLE_CHOICES = 300;
    /** {@code 301 Moved Permanently} (HTTP/1.0 - RFC 1945) */
    public static final int SC_MOVED_PERMANENTLY = 301;
    /** {@code 302 Moved Temporarily} (Sometimes {@code Found}) (HTTP/1.0 - RFC 1945) */
    public static final int SC_MOVED_TEMPORARILY = 302;
    /** {@code 303 See Other} (HTTP/1.1 - RFC 2616) */
    public static final int SC_SEE_OTHER = 303;
    /** {@code 304 Not Modified} (HTTP/1.0 - RFC 1945) */
    public static final int SC_NOT_MODIFIED = 304;
    /** {@code 305 Use Proxy} (HTTP/1.1 - RFC 2616) */
    public static final int SC_USE_PROXY = 305;
    /** {@code 307 Temporary Redirect} (HTTP/1.1 - RFC 2616) */
    public static final int SC_TEMPORARY_REDIRECT = 307;

    // --- 4xx Client Error ---

    /** {@code 400 Bad Request} (HTTP/1.1 - RFC 2616) */
    public static final int SC_BAD_REQUEST = 400;
    /** {@code 401 Unauthorized} (HTTP/1.0 - RFC 1945) */
    public static final int SC_UNAUTHORIZED = 401;
    /** {@code 402 Payment Required} (HTTP/1.1 - RFC 2616) */
    public static final int SC_PAYMENT_REQUIRED = 402;
    /** {@code 403 Forbidden} (HTTP/1.0 - RFC 1945) */
    public static final int SC_FORBIDDEN = 403;
    /** {@code 404 Not Found} (HTTP/1.0 - RFC 1945) */
    public static final int SC_NOT_FOUND = 404;
    /** {@code 405 Method Not Allowed} (HTTP/1.1 - RFC 2616) */
    public static final int SC_METHOD_NOT_ALLOWED = 405;
    /** {@code 406 Not Acceptable} (HTTP/1.1 - RFC 2616) */
    public static final int SC_NOT_ACCEPTABLE = 406;
    /** {@code 407 Proxy Authentication Required} (HTTP/1.1 - RFC 2616)*/
    public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
    /** {@code 408 Request Timeout} (HTTP/1.1 - RFC 2616) */
    public static final int SC_REQUEST_TIMEOUT = 408;
    /** {@code 409 Conflict} (HTTP/1.1 - RFC 2616) */
    public static final int SC_CONFLICT = 409;
    /** {@code 410 Gone} (HTTP/1.1 - RFC 2616) */
    public static final int SC_GONE = 410;
    /** {@code 411 Length Required} (HTTP/1.1 - RFC 2616) */
    public static final int SC_LENGTH_REQUIRED = 411;
    /** {@code 412 Precondition Failed} (HTTP/1.1 - RFC 2616) */
    public static final int SC_PRECONDITION_FAILED = 412;
    /** {@code 413 Request Entity Too Large} (HTTP/1.1 - RFC 2616) */
    public static final int SC_REQUEST_TOO_LONG = 413;
    /** {@code 414 Request-URI Too Long} (HTTP/1.1 - RFC 2616) */
    public static final int SC_REQUEST_URI_TOO_LONG = 414;
    /** {@code 415 Unsupported Media Type} (HTTP/1.1 - RFC 2616) */
    public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;
    /** {@code 416 Requested Range Not Satisfiable} (HTTP/1.1 - RFC 2616) */
    public static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    /** {@code 417 Expectation Failed} (HTTP/1.1 - RFC 2616) */
    public static final int SC_EXPECTATION_FAILED = 417;

    /**
     * Static constant for a 418 error.
     * {@code 418 Unprocessable Entity} (WebDAV drafts?)
     * or {@code 418 Reauthentication Required} (HTTP/1.1 drafts?)
     */
    // not used
    // public static final int SC_UNPROCESSABLE_ENTITY = 418;

    /**
     * Static constant for a 419 error.
     * {@code 419 Insufficient Space on Resource}
     * (WebDAV - draft-ietf-webdav-protocol-05?)
     * or {@code 419 Proxy Reauthentication Required}
     * (HTTP/1.1 drafts?)
     */
    public static final int SC_INSUFFICIENT_SPACE_ON_RESOURCE = 419;
    /**
     * Static constant for a 420 error.
     * {@code 420 Method Failure}
     * (WebDAV - draft-ietf-webdav-protocol-05?)
     */
    public static final int SC_METHOD_FAILURE = 420;
    /** {@code 422 Unprocessable Entity} (WebDAV - RFC 2518) */
    public static final int SC_UNPROCESSABLE_ENTITY = 422;
    /** {@code 423 Locked} (WebDAV - RFC 2518) */
    public static final int SC_LOCKED = 423;
    /** {@code 424 Failed Dependency} (WebDAV - RFC 2518) */
    public static final int SC_FAILED_DEPENDENCY = 424;

    // --- 5xx Server Error ---

    /** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    public static final int SC_INTERNAL_SERVER_ERROR = 500;
    /** {@code 501 Not Implemented} (HTTP/1.0 - RFC 1945) */
    public static final int SC_NOT_IMPLEMENTED = 501;
    /** {@code 502 Bad Gateway} (HTTP/1.0 - RFC 1945) */
    public static final int SC_BAD_GATEWAY = 502;
    /** {@code 503 Service Unavailable} (HTTP/1.0 - RFC 1945) */
    public static final int SC_SERVICE_UNAVAILABLE = 503;
    /** {@code 504 Gateway Timeout} (HTTP/1.1 - RFC 2616) */
    public static final int SC_GATEWAY_TIMEOUT = 504;
    /** {@code 505 HTTP Version Not Supported} (HTTP/1.1 - RFC 2616) */
    public static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;

    /** {@code 507 Insufficient Storage} (WebDAV - RFC 2518) */
    public static final int SC_INSUFFICIENT_STORAGE = 507;

}
