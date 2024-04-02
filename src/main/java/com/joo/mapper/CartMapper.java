package com.joo.mapper;

import java.util.List;

import com.joo.model.CartDTO;

public interface CartMapper {

	/* 카트 추가 */
	public int addCart(CartDTO cart) throws Exception; // 반환 타입 int : 성공 1, 실패 0
	
	/* 카트 삭제 */
	public int deleteCart(int cartId);
	
	/* 카트 수량 수정 */
	public int modifyCount(CartDTO cart); // 어떤 row인지 지정(cartId), 수량 변경(bookCount), 반환 타입 int
	
	/* 카트 목록 */
	public List<CartDTO> getCart(String memberId); // memberId 타입이 varchar여서 String, 반환 타입은 목록(List)
	
	/* 카트 확인 */
	public CartDTO checkCart(CartDTO cart); // 회원정보(memberId), 상품정보(bookId)
}
