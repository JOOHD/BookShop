package com.joo.service;

import java.util.List;

import com.joo.model.CartDTO;

public interface CartService {

	/*
	   인프러 김영한 강사님께서는 상황에 따라 단순히 호출해주는 역할만 할 경우, 
	   상황에 따라 Controller 직접 Mapper(Entity) 객체에 접근을 해도 상관없다.
	   
	   하지만 이번 프로젝트는 Controller - Service - Mapper를 의존하는 구조를 일관성 있게 해 주기로 함.
	   ex) ModifyCount, Controller에서 Mapper로 바로 호출하지 않고, Service에서도 호출.
	 */
	
	/* 카트 추가 */
	public int addCart(CartDTO cart);
	
	/* 카트 삭제 */
	public int deleteCart(int cartId);
	
	/* 카트 수량 수정 */
	public int modifyCount(CartDTO cart);
	
	/* 카트 목록 */
	public List<CartDTO> getCart(String memberId);	
	
	/* 카트 확인 */
	public CartDTO checkCart(CartDTO cart);
	
	/* 장바구니 정보 리스트 */
	public List<CartDTO> getCartList(String memberId);
}
