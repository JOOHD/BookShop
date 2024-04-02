package com.joo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.CartMapper;
import com.joo.model.CartDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public int addCart(CartDTO cart) {
		
		// 장바구니 데이터 체크
		CartDTO checkCart = cartMapper.checkCart(cart);
		
		if(checkCart != null) {
			return 2;
		}
		// 장바구니 등록 & 에러 시  0 반환
		try {
			return cartMapper.addCart(cart);
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public List<CartDTO> getCartList(String memberId) {
		
		// 장바구니 정보를 모두 가져오는 get()메서드 호출.
		List<CartDTO> cart = cartMapper.getCart(memberId);
		
		for(CartDTO dto : cart) { // for문을 사용하여 DTO의 4개(sale/total price, point/total) 변수를 초기화.
			
			log.info("dto : " + dto);
			dto.initSalePrice();
		}
		
		log.info("cart : " + cart);
		return cart; // 세팅된 값 반환
	}

	@Override
	public int deleteCart(int cartId) {
		
		return 0;
	}

	@Override
	public int modifyCount(CartDTO cart) {
		
		return 0;
	}

	@Override
	public List<CartDTO> getCart(String memberId) {
		
		return null;
	}

	@Override
	public CartDTO checkCart(CartDTO cart) {
		
		return null;
	}

	
}
