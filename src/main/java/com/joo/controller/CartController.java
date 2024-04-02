package com.joo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joo.model.CartDTO;
import com.joo.model.MemberVO;
import com.joo.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;

	private static final Logger log = LoggerFactory.getLogger(CartController.class);
	/*
	 * 0: 등록 실패
	 * 1: 등록 성공
	 * 2: 등록된 데이터 존재
	 * 5: 로그인 필요
	 */
	
	/* 장바구니 등록 */
	@PostMapping("/cart/add")
	@ResponseBody // 반환 값은 int 지만, @ResponseBody 때문에 반환 값 String 
	public String addCartPOST(CartDTO cart, HttpServletRequest request) {
		
		// 로그인 체크
		HttpSession session = request.getSession();
		
		MemberVO vo = (MemberVO)session.getAttribute("member");	
		if(vo == null) {
			return "5";	// 로그인되지 않았을 경우			
		}
		
		// 카트 등록
		int result = cartService.addCart(cart);
		
		// 반환 받은 값을 반환 하도록 코드를 추가(int -> String 위해 빈 문자열을 더하기 연산 하였따) 
		return result + "";
	}
	
	/* 장바구니 정보 리스트 */
	@GetMapping("/cart/{memberId}")
	public String cartPageGET(@PathVariable("memberId") String memberId, Model model) {
		
		model.addAttribute("cartInfo", cartService.getCartList(memberId));
		
		return "/cart";
	}
}
