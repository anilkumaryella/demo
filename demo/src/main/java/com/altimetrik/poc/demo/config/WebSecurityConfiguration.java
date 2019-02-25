/*package com.altimetrik.poc.demo.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	
	@Inject
	private TokenManager tokenManager;
	
	@Inject
	ApiUserAuthenticationProvider authenticationProvider;
	
	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.authenticationProvider(authenticationProvider);
	}
	
	public void configure(WebSecurity web) throws Exception{
		
		web.ignoring()
		.antMatchers(new String[] {"/","/"});
	}
	
	
	
	
	protected void configure(HttpSecurity http) throws Exception{
		
		SuccessfulAuthenticationHandler successfulAuthenticationHandler=new SuccessfulAuthenticationHandler(this.tokenManager);
		
		UserLogoutHandler successfulLogoutHandler=new UserLogoutHandler(this.tokenManager);
		
		http
				.addFilterBefore(expiredSessionFilter(), HeaderAuthenticationFilter.class)
				.formLogin().successHandler(successfulAuthenticationHandler).loginProcessingUrl("/login"); 
				// @formatter:on

		
		
		
		
	}
}
*/