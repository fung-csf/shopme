package com.shopme.admin.security;

import org.aspectj.weaver.ast.And;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	/*
	 * refer to this link: https://www.codejava.net/frameworks/spring-boot/fix-
	 * websecurityconfigureradapter-deprecated
	 * 
	 */
	
	
	 /* This bean will be called by spring security when performing authentication*/ 
	@Bean
	public UserDetailsService  userDetailsService()	{
		return new ShopmeUserDetailsService();
	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     
	    authProvider.setUserDetailsService(userDetailsService());
	    authProvider.setPasswordEncoder(passwordEncoder());
	 
	    return authProvider;
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// any request need to be authenticated (require login)
		// custom login page at "/login" - handler method in MainController class
		http.authorizeHttpRequests()
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll()
			.and().logout().permitAll();
		
		http.authenticationProvider(authenticationProvider());
		
		
		return http.build();
	}

	//NOTE: antMatchers() changed to requestMatchers()
	//stack: https://stackoverflow.com/questions/74907533/the-method-antmatchersstring-is-undefined-for-the-type
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		//permit access to the assets in the following directories
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}

}
