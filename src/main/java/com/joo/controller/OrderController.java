package com.joo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joo.model.OrderPageDTO;
import com.joo.service.MemberService;
import com.joo.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"주문 페이지 Controller"})
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MemberService memberService;
	
	@ApiOperation(value = "상품 주문")
	@GetMapping("/order/{memberId}")
	public String orderPageGET(@PathVariable("memberId") String memberId, OrderPageDTO opd, Model model) {
		
		// 상품 정보
		model.addAttribute("orderList", orderService.getGoodsInfo(opd.getOrders()));
		// 회원 정보
		model.addAttribute("memberInfo", memberService.getMemberInfo(memberId));
		
		return "/order";
	}
}
