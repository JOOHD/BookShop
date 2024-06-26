package com.joo.model;

import java.util.List;

public class OrderPageDTO {	// OrderPageItemDTO 클래스의 객체를 요소로 가지는 List 타입의 변수를 가지는 클래스

	/* 상품 데이터 여러개를 담는 변수 */
	private List<OrderPageItemDTO> orders;

	public List<OrderPageItemDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderPageItemDTO> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "OrderPageDTO [orders=" + orders + "]";
	}
	
	
}
