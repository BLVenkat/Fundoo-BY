package com.bridgelabz.fundoo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.fundoo.exception.FundooException;
import com.bridgelabz.fundoo.util.TokenService;

@Component
public class TokenInterceptor implements HandlerInterceptor {
	
	@Autowired
	TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println(request.getRequestURI());

		if(request.getRequestURI().contains("user") || request.getRequestURI().contains("swagger") || request.getRequestURI().contains("/")) {
			System.out.println(request.getRequestURI());
			return true;
		}
		
		String token = request.getHeader("token");
		if(token == null) {
			throw new FundooException(HttpStatus.BAD_REQUEST.value(),"Token is not present in header");
		}
		tokenService.decodeToken(token);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
		System.out.println("post handle");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		System.out.println("after Completing response");

	}

}
