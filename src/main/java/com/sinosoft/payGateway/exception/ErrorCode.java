package com.sinosoft.payGateway.exception;

public final class ErrorCode {
	
	//成功
	public final static  String  SUCCESS = "0000";
	
	/**认证失败*/
	public static final int  Authentication_Failure   = 1001;
	  
	/**没有权限*/
	public static final int  Permission_Denied   = 1002;
	 
	/**方法参数异常，进入不了方法*/
	public static final String  Method_Params_Error = "A0001";
	
	 /**1开头的：业务异常  1001 普通业务异常*/
	public static final String  Business_Common_Error = "A1000";
	 
	 /**业务异常：数据校验异常*/
	public static final String  Business_DataValid_Error = "A1001";
	 
	/**B-违反内部业务规则*/
	public final static  String B0101 = "B0101";
	
	/** D-系统内部错误*/
	public final static  String DataBase_Error = "D0101";

	/**D010101 数据库操作未执行或回滚*/
	public final static  String D010101 = "D010101";
	
	/**
	 * E-需要确认行为
	 */
	public final static  String E0101 = "E0101";

	/**
	 * 代码配置缺失
	 */
	public final static  String CFG0101 = "CFG0101";
	
	/**
	 * 车险平台
	 */
	public final static  String P0101 = "P0101";
	
	/**第三方服务-响应超时*/
	public final static  String  ThirdPart_Socket_TimeOut  = "TP_001";
	/**第三方服务-连接超时*/
	public final static  String  ThirdPart_Connect_TimeOut = "TP_002";
	/**第三方服务-返回数据异常*/
	public final static  String  ThirdPart_Data_Error   = "TP_003";
	/**第三方服务-未知异常*/
	public final static  String  ThirdPart_UnKnow_Error = "TP_999";
	
	/**FTP-连接异常*/
	public final static  String  SFTP_Connect_Error  = "SFTP_001";
	/**FTP-超时*/
	public final static  String  SFTP_TimeOut_Error  = "SFTP_002";
	/**FTP-进入路径失败*/
	public final static  String  SFTP_EnterDir_Error   = "SFTP_003";
	/**FTP-文件或目录操作异常*/
	public final static  String  SFTP_FileOperate_Error = "SFTP_004";
	/**FTP-未知异常*/
	public final static  String  SFTP_Other_Error = "SFTP_999";
	
	/**
	 * 权限问题
	 */
	public final static  String POWER01 = "Power01";
	
	/**
	 * Z-系统未知错误
	 */
	public final static  String INTERNAL_SERVER_ERROR = "Z0101";
	

	
}
