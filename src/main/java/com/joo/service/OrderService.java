package com.joo.service;

import java.util.List;

import com.joo.model.CheckOrderVO;
import com.joo.model.OrderDTO;
import com.joo.model.OrderPageItemDTO;

public interface OrderService {

	/* 주문 정보 */
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders);
	
	/* 주문 */
	public void order(OrderDTO ord);
	
	/* 주문 확인(재고, 금액 마이너스 방지) */
	// public int checkOrder(CheckOrderVO co);
}
