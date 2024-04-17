package com.joo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.joo.model.MemberVO;
import com.joo.model.OrderDTO;
import com.joo.model.OrderPageDTO;
import com.joo.service.MemberService;
import com.joo.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"주문 페이지 Controller"})
@Slf4j
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MemberService memberService;
	
	@ApiOperation(value = "상품 주문 페이지")
	@GetMapping("/order/{memberId}")
	public String orderPageGET(@PathVariable("memberId") String memberId, OrderPageDTO opd, Model model) {
		
		// 상품 정보
		model.addAttribute("orderList", orderService.getGoodsInfo(opd.getOrders()));
		// 회원 정보
		model.addAttribute("memberInfo", memberService.getMemberInfo(memberId));
		
		return "/order";
	}
	
	@ApiOperation(value = "상품 주문 처리")
	@PostMapping("/order")
	public String orderPagePost(OrderDTO od, HttpServletRequest request) { // view에서 받은 정보(OrderDTO)
		
		orderService.order(od);	
		
		MemberVO member = new MemberVO();
		member.setMemberId(od.getMemberId());
		log.info("member : " + member);
		
		HttpSession session = request.getSession();
		
		try {
				MemberVO memberLogin = memberService.memberLogin(member);
				memberLogin.setMemberPw("");
				session.setAttribute("member", memberLogin);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/main";
	}
}
