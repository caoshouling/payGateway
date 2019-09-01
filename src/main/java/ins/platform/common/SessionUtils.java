package ins.platform.common;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ins.platform.dcode.po.User;

public class SessionUtils {

	/**
	 * 获取当前用户
	 * @return
	 */
	public static User getCurrentUser(){
		
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
		.map((value) -> (ServletRequestAttributes)(value))
		.map(ServletRequestAttributes ::getRequest)
		.map(HttpServletRequest :: getSession)
		.map(session -> session.getAttribute(WebConstant.USER_SESSION))
		.map(value -> (User)value)
		.orElse(null);
		
	}
}
