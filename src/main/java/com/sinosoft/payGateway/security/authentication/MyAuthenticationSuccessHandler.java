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
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinosoft.payGateway.common.JsonResponse;
import com.sinosoft.payGateway.security.SecurityConstant;

/**
 * 浏览器环境下登录成功的处理器
 * 
 * @author zhailiang
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper objectMapper;


	public MyAuthenticationSuccessHandler() {
		//设置默认的成功的地址。默认：如果原请求就是登录，那么就用这里设置的地址；如果原请求为其他的url，因为没登录系统自动调转到登录页面，那么登录后将跳回原url
		setDefaultTargetUrl(SecurityConstant.Login_Default_Success_Url);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		logger.info("登录成功");
	
		if (request.getRequestURI().endsWith(".json")) {
			response.setContentType("application/json;charset=UTF-8");
			String type = authentication.getClass().getSimpleName();
			response.getWriter().write(objectMapper.writeValueAsString(new JsonResponse(type)));
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

}
