package com.sinosoft.payGateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cn.hutool.core.bean.BeanUtil;
import ins.platform.dcode.dao.UserMapper;
import ins.platform.dcode.po.User;


@Component
public class MyUserDetailService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);
    @Autowired
    PasswordEncoder  bcryptPasswordEncoder;
    @Autowired
	UserMapper userMapper;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.info("loadUserByUsername - 用户{}登录-- "+userName);
		//模拟通过用户名查询用户
		User user = userMapper.selectUserByUserName(userName);
		//AuthorityUtils.commaSeparatedStringToAuthorityList 逗号隔开的字符串转换成List<GrantedAuthority> 
	    if(user == null){
	    	logger.error("用户[{}]不存在",userName);
			throw new UsernameNotFoundException("用户"+userName+"不存在");
	    }
	    //数据库中的密码是没加密的，所以这里要加密才行。
	    user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
	    
	    UserDetail userDetail = new UserDetail();
		BeanUtil.copyProperties(user, userDetail, false);
		userDetail.setAuthoritys(AuthorityUtils.commaSeparatedStringToAuthorityList("USER,ADMIN"));
		
		
		return userDetail;
	}
   
}
