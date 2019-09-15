package com.sinosoft.payGateway.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ins.platform.dcode.po.User;

public class UserDetail extends User implements UserDetails{
	
	
    private String userName;
    
	private static final long serialVersionUID = 1L;
	
	private List<GrantedAuthority> authoritys;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authoritys;
	}

	public void setAuthoritys(List<GrantedAuthority> authoritys) {
		this.authoritys = authoritys;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getUsername() {
		
		return super.getUserName();
	}
    //账户是否过期
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
    //账户是否被锁定
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
    //凭证是否过期
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
    //是否启用
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public int hashCode() {
		return userName.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserDetail){
			return this.userName.equals(((UserDetail)obj).userName);
		}
		return false;
	}
    
}