package com.sinosoft.payGateway.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.sinosoft.payGateway.security.authentication.MyAuthenctiationFailureHandler;
import com.sinosoft.payGateway.security.authentication.MyAuthenticationSuccessHandler;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig   extends WebSecurityConfigurerAdapter {
	
	@Autowired
	MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;
	
	@Autowired
	MyUserDetailService myUserDetailService;
	@Autowired
	private Filter verificationCodeFilter;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		//有StandardPasswordEncoder Pbkdf2PasswordEncoder等。官方推荐BCryptPasswordEncoder
	    return new BCryptPasswordEncoder();
	}
 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//内存模式
		//auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123")).roles("ADMIN");
		
		System.out.println("密码："+passwordEncoder().encode("123"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		
		//禁用CSRF
		http.csrf().disable();
		
		http.authorizeRequests()
		.antMatchers("/monitoring",
				     "/druid",
				     "/druid/**",
				     "/plugin/**",
				     "/404",
				     "/500", 
				     "/auth/login",
				     "/auth/vcode", 
				     "/error").permitAll()
        .and().authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		
		.loginPage(SecurityConstant.Login_Page_Url)
		.permitAll()
		.usernameParameter("userName")
		.passwordParameter("password")
		.loginProcessingUrl(SecurityConstant.Login_Process_Url)
		.permitAll()
		.failureUrl(SecurityConstant.Login_Failure_Url) //因为用了failureHandler，这里设置其实无效
		.defaultSuccessUrl(SecurityConstant.Login_Default_Success_Url) //因为用了successHandler，这里设置其实无效
//		.failureHandler(myAuthenctiationFailureHandler) //失败处理器
		.successHandler(myAuthenticationSuccessHandler) //成功处理器
		.and()
		.userDetailsService(myUserDetailService) //通过这个服务类获取用户
		//.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class)//验证码过滤器
		.rememberMe()//记住我，默认过期时间是两周
		.rememberMeParameter("remenberMe")
		.key("SDIOUI457485349")//如果不指定Key，那么每次重启服务key都会重新随机生成在，这样每次重启自动登录cookie失效。而且多实例的情况下访问的不是同一个实例，自动登录策略会失效。
		.and()
		.sessionManagement()
		.invalidSessionUrl(SecurityConstant.Login_Page_Url) //session失效的地址
		 //注意：UserDetails对象必须重写hashCode 和 equals方法，否则无效（不会认为是同一个用户）
		.maximumSessions(1) //最大会话数为1，默认新登录的会踢掉旧的会话 .maxSessionsPreventsLogin(true) 可以设置阻止新登录的，而不是踢掉旧的
		
		;
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//解决静态资源被拦截的问题
        web.ignoring()
        .antMatchers("/","/js/**","/css/**","/html/**","/plugin/**","/index.html")
        // swagger start
        .antMatchers("/swagger-ui.html")
        .antMatchers("/swagger-resources/**")
        .antMatchers("/images/**")
        .antMatchers("/webjars/**")
        .antMatchers("/v2/api-docs")
        .antMatchers("/configuration/ui")
        .antMatchers("/configuration/security");
         // swagger
	}
    /**
     * 使用会话管理时，必须配置这个事件源，转化为spring的事件机制。
     * 注册Bean来订阅关心的事件。
     * Spring Security事件是通过监听session的销毁事件来触发会话信息表相关的清理工作，
     * 
     * 如果不配置，那么会出现：比如设置阻止新登录的策略来实现会话并发控制时，logout后，新的浏览器还是不能登录。
     * 
     * @return
     */
	@Bean
	public HttpSessionEventPublisher httpSessonEventPublisher(){
		
		return new HttpSessionEventPublisher();
	}
    
}
