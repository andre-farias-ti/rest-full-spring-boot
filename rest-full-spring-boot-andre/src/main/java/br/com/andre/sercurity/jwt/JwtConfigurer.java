package br.com.andre.sercurity.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends 
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

	@Autowired
	private JwtTokenProvider tokenProvaider;

	public JwtConfigurer(JwtTokenProvider tokenProvaider) {
		this.tokenProvaider = tokenProvaider;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtTokenFilter custonFilter = new JwtTokenFilter(tokenProvaider);
		http.addFilterBefore(custonFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
}
