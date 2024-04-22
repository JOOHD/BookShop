package com.joo.mapper;

import com.joo.model.BookVO;
import com.joo.model.CartDTO;
import com.joo.model.CheckOrderVO;
import com.joo.model.MemberVO;
import com.joo.model.OrderDTO;
import com.joo.model.OrderItemDTO;
import com.joo.model.OrderPageItemDTO;

public interface OrderMapper {

	/* 주문 상품 정보 */
	public OrderPageItemDTO getGoodsInfo(int bookId);
	
	/* 주문 상품 정보(주문 처리) */
	public OrderItemDTO getOrderInfo(int bookId);
	
	/* 주문 테이블 등록 */
	public int enrollOrder(OrderDTO ord);
	
	/* 주문 아이템 테이블 등록 */
	public int enrollOrderItem(OrderItemDTO orid);
	
	/* 주문 금액 차감 */
	public int deductMoney(MemberVO member); // 회원의 돈, 포인트 차감 메서드
	
	/* 주문 재고 차감 */
	public int deductStock(BookVO book);
	
	/* 카트 주문 제거 */
	public int deleteOrderCart(CartDTO dto);

	/* 주문 확인(재고, 금액 마이너스 방지) */
	// public int checkOrder(CheckOrderVO co);
}

