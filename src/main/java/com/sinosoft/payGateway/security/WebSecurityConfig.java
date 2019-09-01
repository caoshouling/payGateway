package com.sinosoft.payGateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.accept.ContentNegotiationStrategy;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig   extends WebSecurityConfigurerAdapter{
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123")).roles("ADMIN");
	  
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
				     "/error").permitAll()
        .and().authorizeRequests()
		.anyRequest().authenticated()
		.and().formLogin();
		
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


	
    
}
