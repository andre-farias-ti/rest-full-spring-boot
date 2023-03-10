package br.com.andre.sercurity.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean {
	
	@Autowired
	private JwtTokenProvider tokenProvaider;

	public JwtTokenFilter(JwtTokenProvider tokenProvaider) {
		this.tokenProvaider = tokenProvaider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String token = tokenProvaider.resolveToken((HttpServletRequest)request);
		
		if(token != null && tokenProvaider.validateToken(token)) {
			Authentication auth = tokenProvaider.getAuthentication(token);
			
			if(auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
		chain.doFilter(request, response);
	}

}
