package com.joo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.joo.model.MemberVO;

public class CartInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		/*  /cart/** url을 이용하는 사용자가 요청 시, Controller로 요청 가기 전, 로그인 하였는지 확인 차.
		 	비로그인 회원이면 리다이렉트, 로그인 시 session "member"라는 키로 로그인 사용자 정보 데이터를 저장.
		 	따라서 session의 "member"를 불러들여서 데이터가 있는지 확인하여 회원인지 판단
		*/
		
		HttpSession session = request.getSession();
		
		MemberVO vo = (MemberVO)session.getAttribute("member");
		
		if (vo == null) {
			response.sendRedirect("/main");
			return false;
		} else {
			return true;
		}
		
	}

	
}
