package com.sinosoft.payGateway.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sinosoft.payGateway.security.authentication.MyAuthenctiationFailureHandler;

import ins.platform.common.WebConstant;
import ins.platform.kit.StrKit;

@Component("verificationCodeFilter")
public class VerificationCodeFilter extends OncePerRequestFilter {
	@Autowired
	private MyAuthenctiationFailureHandler authenticationFailureHandler;
	
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//只处理登录请求
		if((request.getContextPath() + SecurityConstant.Login_Process_Url).equals(request.getRequestURI())){
			try{
				this.VerificationCode(request,response);
			}catch (VerificationCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
			
			
		}
        filterChain.doFilter(request, response);
		

	}
	
	private  void VerificationCode(HttpServletRequest request, HttpServletResponse response) throws VerificationCodeException{
		
		String requestCode = request.getParameter("vercode");
		HttpSession session = request.getSession(false);
		String vcode =  "";
		if(session != null){
			vcode = (String) session.getAttribute(WebConstant.USER_LOGIN_VALID_CODE);
			//不管成功还是失败，都清楚验证码，客户端要每次刷新
			session.removeAttribute(WebConstant.USER_LOGIN_VALID_CODE);
		}
	
     	if(StrKit.isEmpty(requestCode)){
     		throw new VerificationCodeException("请先录入验证码!");
     	}else if(!requestCode.equalsIgnoreCase(vcode)){
     		throw new VerificationCodeException( "验证码错误！");
     	}
	}

}
