package com.sinosoft.payGateway.exception;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String message ;
	
	protected String errorCode; 
	
	public BusinessException(){
	}
	public BusinessException(String errorCode,String message, Throwable cause) {
	        super(message, cause);
	        this.errorCode = errorCode;
	        this.message = message;
	}
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		this.message = message;
	}
	public static BusinessException errorCode(String errorCode){
		BusinessException exception =new BusinessException();
		exception.errorCode = errorCode;
		return exception;
	}
	
	public BusinessException msg(String message){
		this.message = message;
		return this;
	}
	
	public BusinessException code(String errorCode){
		this.errorCode = errorCode;
		return this;
	}
	
	public void  ret() throws BusinessException{
		throw this;
	}
	
	public static BusinessException errorMessage(String message){
		BusinessException exception =new BusinessException();
		exception.message = message;
		exception.errorCode = ErrorCode.B0101;
		return exception;
	}
	
	public BusinessException(String message){
		super(message);
		this.message = message;
		this.errorCode = ErrorCode.B0101; 
	}
	
	public BusinessException(String errorCode,String message){
		super(message);
		this.errorCode=errorCode;
		this.message = message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	} 

	@Override
	public String getMessage() {
		return  this.message;
	}
	

}
