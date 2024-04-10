package com.joo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joo.model.OrderPageDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"주문 페이지 Controller"})
@Controller
public class OrderController {

	@ApiOperation(value = "상품 주문")
	@GetMapping("/order/{memberId}")
	public void orderPageGET(@PathVariable("memberId") String memberId, OrderPageDTO opd, Model model) {
		
		System.out.println("memberId : " + memberId);
		System.out.println("orders : " + opd.getOrders());
	}
}
