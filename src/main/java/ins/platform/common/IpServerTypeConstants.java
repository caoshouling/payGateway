package ins.platform.common;

public final class IpServerTypeConstants {
	
	/**
	 * 网络类型的取值为0-内网；      1-网通； 2-电信；       3-域名  
	 *             intranet CNC    telecom  domainname
	 */
	public  final static String IP_NETTYPE_INTRANET = "0";
	
	public  final static String IP_NETTYPE_CNC = "1";
	
	public  final static String IP_NETTYPE_TELECOM = "2";
	
	public  final static String IP_NETTYPE_DOMAINNAME = "3";
	
	
	/**
	 * 江苏中介平台地址
	 */
	public static final String Agency_Plateform_32_URL = "AgencyPlateformUrl_32";
	/**
	 * 品牌化投保单保存接口
	 */
	public static final String Brand_Save_URL = "BrandSaveUrl";
	
	/**
	 * 销管服务地址类型 - 旧地址-Xfile调用
	 */
	public static final String Old_SaleRule_URL = "SaleRuleUrl";
    /**
     * 销管服务地址类型 - 新地址 JSON调用
     */
	public static final String New_SaleRule_URL = "NewSaleRuleUrl";
	
	/**
	 * 权限
	 */
	public  final static String POWER_URL = "powerUrl";
	/**
	 * 双核
	 */
	public  final static String Undwrt_URL = "undwrtUrl";
	/**
	 * 信雅达影像 - 前端地址
	 */
	public  final static String Image_URL = "imageUrl";
	
	/**
	 * 信雅达影像 - 接口地址
	 */
	public  final static String Image_API_URL = "imageAPIURL";
	/**
	 * 收付接口URL
	 */
	public  final static String Payment_Interface_URL = "paymentInterfaceUrl";
	/**
	 * 短信接口
	 */
	public  final static String SmsRuleUrl = "SmsRuleUrl";
	/**
	 * 精友解析服务地址
	 */	
	public  final static String JYVinUrl = "JYVinUrl";
	/**
	 * Indigo服务器地址,浏览器预览时会用到
	 */
	public  final static String INDIGO_SERVER_URL = "IndigoPrint_URL";
	
	/**
	 * 中间应用idpxml地址：打印的XML字符串要传送的地址
	 */
	public  final static String INDIGO_IDPXML_URL = "IndigoPrintClient_URL";
	
	/**
	 * 下载预览控件地址
	 */
	public  final static String INDIGO_DOWLOAD_WIDGET_URL = "IndigoPrint_PrePageUrl";
	/**
	 * 打印预览的HTML页面地址
	 */
	public  final static String INDIGO_HTMLPAGE_URL = "IndigoPrint_HtmlPage_Url";
	/**
	 * 查询
	 */
	public  final static String CARMODEL_COMMONSERVICE_URL = "CarModelCommonServiceUrl";
	
	/**
	 * Vat后端交互地址
	 */
	public  final static String VAT_URL = "VatURL";
	/**
	 * Vat浏览器访问地址
	 */
	public  final static String VATURL_PAGE = "VatURL_Page";
	
	/**
	 * 价税分离实际uri地址
	 */
	public static final String Vat_ProposalTax_URL_Path= "rest/proposalTaxService/caculate";
	
	public static final String Vat_EndorTax_URL_Path = "rest/endorseTaxService/caculate";
	
	/**
	 * 校验客户重复名称
	 */
	public  final static String CHEKC_INSURED_DUPLICATENAME_URL  = "PersonDetailURL";
	
	/**
	 * Cifcore平台驾驶行为评分访问地址
	 */
	public  final static String CARMODEL_CIFCORESERVICE_URL = "CarModelCifcoreServiceUrl";
	
	/**
	 * 广东交管车辆数据综合服务批平台
	 */
	public  final static String TrafficVehicle_4400_Prpall_URL = "TrafficVehicle_4400_prpall";
	
	/**
	 * 电子保单下载地址接口访问地址
	 */
	public  final static String EPOLICY_DOWNLOAD_URL = "EpolicyDownLoad";
	
	/**
	 * 电子保单下载链接地址
	 */
	public  final static String EPOLICY_LINK_URL = "EpolicyLink";
	
	/**
	 * 影像下载通知接口访问地址
	 */
	public  final static String IMAGE_NOTIFY_URL = "ImageNotify_URL";
		
	/** 更新反洗钱系统的客户身份识别信息地址 */
	public static final String SERVERTYPE_CUSTOMER_IDENFIED_UPDATE = "CustomerIdenfiedUpdateUrl";
	/** 回写反洗钱系统的客户身份识别信息地址 */
	public static final String SERVERTYPE_CUSTOMER_IDENFIED_CALLBACK = "CustomerIdenfiedCallBackUrl";
	/** 删除反洗钱系统的客户身份识别信息地址 */
	public static final String SERVERTYPE_CUSTOMER_IDENFIED_DELETE = "CustomerIdenfiedDeleteUrl";
	
	/** 反洗钱校验地址 */
	public static final String FXQ_CHECK_URL = "FxqTradeCheck";
	/** 反洗钱客户身份识别信息录入地址 */
	public static final String FXQ_IDENTIFY_URL = "FxqIdenfiedPageAccess";
	/** 反洗钱交易更新地址 */
	public static final String FXQ_UPDATE_URL = "FxqBusinessUpdate";
	/** 反洗钱大额可疑信息录入地址 */
	public static final String FXQ_TRADE_URL = "FxqTradePageAccess";
	
	/** 支付平台的地址  */
	public static final String PAY_PLATFORM_URL = "payPlatformUrl";

	/**影像系统rest服务的基础地址****/
	public static final String IMAGE_RestService_URL = "ImageRestService_URL";

}
