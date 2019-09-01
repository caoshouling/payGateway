package com.sinosoft.payGateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class MyUserDetailService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);
    @Autowired
    PasswordEncoder  bcryptPasswordEncoder;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.info("用户{}登录-- "+userName);
		//AuthorityUtils.commaSeparatedStringToAuthorityList 逗号隔开的字符串转换成List<GrantedAuthority> 
		return new User(userName, bcryptPasswordEncoder.encode("123"),  AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
	}
   
}
