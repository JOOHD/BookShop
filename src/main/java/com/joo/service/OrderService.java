package com.joo.service;

import java.util.List;

import com.joo.model.OrderDTO;
import com.joo.model.OrderPageItemDTO;

public interface OrderService {

	/* 주문 정보 */
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders);
	
	/* 주문 */
	public void order(OrderDTO ord);
}
