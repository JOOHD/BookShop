package com.joo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.joo.model.MemberVO;

public class AdminInterceptor implements HandlerInterceptor{

	/*
	 * "/admin/**"에 접근하는 사용자의 adminCk가 1인지 확인하는 작업이 핵심,  
	 * "member" session 정보를 MemberVO 타입의 변수에 담은 후, 
	 * 해당 변수를 통해 adminCk 값 호출. 
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO lvo = (MemberVO)session.getAttribute("member"); // view(jsp)에서의 member를 나타냄.
		
		if(lvo == null || lvo.getAdminCk() == 0) { // 관리자 계정이 아닌 경우

			response.sendRedirect("/main"); // 메인페이지로 리다이렉트
			
			return false;
		}
		
		return true; // 관리자 계정 로그인 경우(lvo != null && lvo.getAdminCk() == 1
	}
	
	
}
