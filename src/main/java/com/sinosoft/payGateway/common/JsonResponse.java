package com.sinosoft.payGateway.common;



import com.sinosoft.payGateway.exception.BusinessException;
import com.sinosoft.payGateway.exception.ErrorCode;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsonResponse {

  //失败
  public static final int Error = -1;
  
  public static final int SUCCESS = 0;
  
  /**认证失败*/
  public static final int  Authentication_Failure   = ErrorCode.Authentication_Failure;
  
  /**没有权限*/
  public static final int  Permission_Denied   = ErrorCode.Permission_Denied;
  
  /**成功标志：1-成功；2-失败 */
  private long status;
  
  /**自定义的错误状态*/
  private String code;
  
  /**自定义的错误描述*/
  private String message;
  
  private Object data;

  public JsonResponse()
  {
    this.status = SUCCESS;
    this.code = ErrorCode.SUCCESS;
    this.message = "Success";
  }
  public JsonResponse(long status, String message, Object data)
  {
    this.status = status;
    this.message = message;
    this.data = data;
  }
  
  public JsonResponse(Object data)
  {
	if((data instanceof BusinessException)){
		  this.status = Error;
		  this.code = ((BusinessException)data).getErrorCode();
	      this.message = ((BusinessException)data).getMessage();
		
	}else if ((data instanceof Exception)) {
	      this.status = Error;
	      this.code = ErrorCode.Business_Common_Error;
	      this.message = ((Exception)data).getLocalizedMessage();
    } else {
	      this.status = SUCCESS;
	      this.code = ErrorCode.SUCCESS;
	      this.message = "Success";
	      this.data = data;
    }
  }
  public JsonResponse data(Object data) {
	this.status = SUCCESS;
	this.code = ErrorCode.SUCCESS;
    this.data = data;
    return this;
  }
  public JsonResponse success( ){
	  this.status = SUCCESS;
	  this.code = ErrorCode.SUCCESS;
	  this.message = "Success";
	  return this;
  }
  public JsonResponse error(String message){
	  this.status = Error;
	  this.code = ErrorCode.Business_Common_Error;
	  this.message = message;
	  return this;
  }
  public JsonResponse error(String code,String message){
	  this.status = Error;
	  this.code = code;
	  this.message = message;
	  return this;
  }
  public JsonResponse authenticationFailure(String message){
	  this.status = Authentication_Failure;
	  this.code = ErrorCode.Business_Common_Error;
	  if(message != null && !"".equals(message)){
		  this.message = message;
	  }else{
		  this.message = "用户认证失败！";
	  }
	  return this;
  }
  public JsonResponse permissionDenied(String message){
	  this.status = Permission_Denied;
	  this.code = ErrorCode.Business_Common_Error;
	  if(message != null && !"".equals(message)){
		  this.message = message;
	  }else{
		  this.message = "您没有权限！";
	  }
	  return this;
  }
}
