package com.sinosoft.payGateway.exception;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.sinosoft.payGateway.common.JsonResponse;

import cn.hutool.core.exceptions.ExceptionUtil;

@ControllerAdvice
public class GlobalHandlerExceptionResolver {
	
    
	 Logger logger =  LoggerFactory.getLogger(GlobalHandlerExceptionResolver.class);
	 @ExceptionHandler(Exception.class)
	 public ModelAndView exceptionHandler(HttpServletRequest request ,Exception exception) {
		    
		    JsonResponse response = new JsonResponse();
		    StringBuffer msg =new StringBuffer();
		    
		    if(exception instanceof ServletRequestBindingException){
		    	
		    	if(exception instanceof MissingServletRequestParameterException){
			    	MissingServletRequestParameterException parameterException = (MissingServletRequestParameterException)exception;
			    	msg.append("缺少参数："+parameterException.getMessage());
			    	logger.info(msg.toString());
			    	response.error(ErrorCode.Method_Params_Error,msg.toString());
			    
			    }else if(exception instanceof MissingRequestCookieException){
			    	MissingRequestCookieException cookieException = (MissingRequestCookieException)exception;
			    	msg.append("缺少Cookie信息："+cookieException.getMessage());
			    	logger.info(msg.toString());
			    	response.error(ErrorCode.Method_Params_Error,msg.toString());
			    
			    }else if(exception instanceof MissingRequestHeaderException){
			    	MissingRequestHeaderException cookieException = (MissingRequestHeaderException)exception;
			    	msg.append("缺少请求头信息："+cookieException.getMessage());
			    	logger.info(msg.toString());
			    	response.error(ErrorCode.Method_Params_Error,msg.toString());
			    
			    }else{
			    	msg.append(exception.getMessage());
			    	logger.info("请求数据异常："+msg.toString());
			    	response.error(ErrorCode.Method_Params_Error,msg.toString());
			    }
		    //方法参数异常
		    }else if(exception instanceof MethodArgumentNotValidException){
		    	MethodArgumentNotValidException validException = (MethodArgumentNotValidException)exception;
		    	BindingResult  result=validException.getBindingResult();
		    	List<ObjectError> errors=result.getAllErrors();
		    	for(ObjectError error:errors){
		    		msg.append(error.getObjectName());
		    		msg.append(":\t");
		    		msg.append(error.getDefaultMessage());
		    		msg.append("\n");
		    	}
		    	logger.info("参数校验返回："+msg.toString());
		    	response.error(ErrorCode.Method_Params_Error,msg.toString());
		    //Hibernate 校验层提示
		    }else if(exception instanceof ConstraintViolationException){
		    	ConstraintViolationException validatorExceptions = (ConstraintViolationException) exception;
		    	Set<ConstraintViolation<?>> contraints=validatorExceptions.getConstraintViolations();
		    	for(ConstraintViolation<?> contraint:contraints){
		    		msg.append(contraint.getMessageTemplate());
		    		msg.append("\n");
		    	}
		    	logger.info("数据校验返回："+msg.toString());
		    	response.error(ErrorCode.Business_DataValid_Error,msg.toString());
		    //业务异常	
		    }else if(exception instanceof BusinessException|| exception.getCause() instanceof BusinessException){
		    	BusinessException businessException = (BusinessException)(exception.getCause());
                if(exception instanceof BusinessException){
                	businessException = (BusinessException)(exception);
                }else{
                	businessException = (BusinessException)(exception.getCause());
                }
		    	if(businessException != null){
		    		msg.append(businessException.getMessage());
		    	}else{
		    		msg.append(exception.getMessage());
		    	}
		    	logger.info("业务提示："+msg.toString());
		    	response.error(ErrorCode.Business_Common_Error,msg.toString());
		    }else if(ExceptionUtil.isFromOrSuppressedThrowable(exception, SQLException.class)   ){
		    	msg.append("数据库操作异常："+exception.getMessage());
		    	logger.error("数据库操作异常："+exception.getMessage(),exception);
		    	response.error(ErrorCode.DataBase_Error, msg.toString());
		    }else{
		    	msg.append("系统异常："+ExceptionUtil.stacktraceToString(exception,500));
		    	logger.error("系统异常："+exception.getMessage(),exception);
		    	response.error(ErrorCode.INTERNAL_SERVER_ERROR, msg.toString());
		    }
		    ModelAndView mv = new ModelAndView();
		   
		   
		    if(isJsonRequest(request)){
		    	FastJsonJsonView view = new FastJsonJsonView();
		        view.setAttributesMap((JSONObject) JSON.toJSON(response));
		        mv.setView(view);
		    }else{
		    	mv.addObject(response);
		    	mv.setViewName("/error");
		    }
		    
	        return mv;
	 }

	/**
	 * html必须以html结尾，如果是希望返回Json，那么可以不写后缀或者后缀为.json
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isJsonRequest(HttpServletRequest request){
		
		String requestUri = request.getRequestURI();
		
		if(requestUri != null){
			if(requestUri.endsWith(".json") || !requestUri.endsWith(".html")){
				return true;
			}
		}
		return false;
	}
}
