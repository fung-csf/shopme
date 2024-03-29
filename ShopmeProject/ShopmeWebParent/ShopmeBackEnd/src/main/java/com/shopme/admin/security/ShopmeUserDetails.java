package com.shopme.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

/*
 *  Think of it as a wrapper class for  Type User
 *  
 *  ShopmeUserDetailsService will use this dao class
 * 
 * 	this class  for spring security
 * */
public class ShopmeUserDetails implements UserDetails {

	private User user;

	public ShopmeUserDetails(User user) {
		this.user = user;
	}

	/* get a list of assigned roles to user */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<Role> roles = user.getRoles();

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	// "true" means account not expired
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled();
	}
	
	public String getFullname() {
		
		return this.user.getFirstName() + " " + this.user.getLastName();
	}
	

	public void setFirstName(String firstName) {
		
		this.user.setFirstName(firstName);
	}
	
	public void setLastName(String lastName) {
		
		this.user.setLastName(lastName);
	}
	
	public boolean hasRole(String roleName) {
		return user.hasRole(roleName);
	}

}
