/**
 * 
 */
package com.sinosoft.payGateway.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinosoft.payGateway.common.JsonResponse;
import com.sinosoft.payGateway.security.SecurityConstant;

import ins.platform.common.WebConstant;

/**
 * 浏览器环境下登录失败的处理器
 * 
 * @author zhailiang
 *
 */
@Component("myAuthenctiationFailureHandler")
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapper objectMapper;

	public MyAuthenctiationFailureHandler() {
		//设置失败的地址
		setDefaultFailureUrl(SecurityConstant.Login_Failure_Url);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		logger.info("登录失败");
		
		if (request.getRequestURI().endsWith(".json")) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new JsonResponse(exception.getMessage())));
		}else{
			super.onAuthenticationFailure(request, response, exception);
		}
		
	}
}
