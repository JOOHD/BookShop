package com.joo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	/*
	 * 적은 확률이지만, 작업 중 세션이 완전히 제거되지 않아 로그인을 위해 
	 * 새로운 세션을 저장할 때 발생할 에러 방지 위해 MemberController 진입 직전 세션 제거 작업
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// 로그인 진행 시, console창에 확인.
		System.out.println("LoginInterceptor preHandle 작동");
		
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		return true;
	}
}
