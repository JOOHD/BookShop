package com.joo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.AttachMapper;
import com.joo.mapper.CartMapper;
import com.joo.model.AttachImageVO;
import com.joo.model.CartDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private AttachMapper attachMapper;	// 장바구니 이미지 삽입 위해.
	
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
			
			/* 종합 정보 초기화 */
			dto.initSalePrice();			
			
			/* 이미지 정보 얻기 */
			int bookId = dto.getBookId();
			
			List<AttachImageVO> imageList = attachMapper.getAttachList(bookId);
			
			// log.info("imageList : " + imageList);
			
			// log.info("dto2 : " + dto);
			dto.setImageList(imageList);
			// log.info("dto2 : " + dto);
			
		}
		
		return cart; // 세팅된 값 반환
	}

	@Override
	public int modifyCount(CartDTO cart) {
		
		return cartMapper.modifyCount(cart);
	}
	
	@Override
	public int deleteCart(int cartId) {
		
		return cartMapper.deleteCart(cartId);
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
