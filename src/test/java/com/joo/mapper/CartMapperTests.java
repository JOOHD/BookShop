package com.joo.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joo.model.CartDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartMapperTests {

	@Autowired
	private CartMapper mapper;
	
	/* 카트 추가 */
	@Test
	public void addCart() throws Exception {
		
		String memberId = "admin";
		int bookId = 2;
		int count = 28;
		
		CartDTO cart = new CartDTO();
		cart.setMemberId(memberId);
		cart.setBookId(bookId);
		cart.setBookCount(count);
		
		int result = 0;
		result = mapper.addCart(cart);
		
		System.out.println("결과 : " + result);
	}
	
	/* 카트 삭제 */
	@Test
	public void deleteCartTest() {
		int cartId = 2;
		
		mapper.deleteCart(cartId);
	}
	
	/* 카트 수량 변경 */
	@Test
	public void modifyCartTest() {
		int cartId = 1;
		int count = 28;
		
		CartDTO cart = new CartDTO();
		cart.setCartId(cartId);
		cart.setBookCount(count);
		
		mapper.modifyCount(cart);
	}
	
	/* 카트 목록 */
	@Test
	public void getCartTest() {
		
		String memberId = "test";
		
		List<CartDTO> list = mapper.getCart(memberId);
		for(CartDTO cart : list ) {
			System.out.println(cart);
			cart.initSalePrice();
			System.out.println("init cart : " + cart);
		}
	}

	@Test
	public void checkCartTest() {
		
		String memberId = "test";
		int bookId = 6;
		
		CartDTO cart = new CartDTO();
		cart.setMemberId(memberId);
		cart.setBookId(bookId);
		
		CartDTO resultCart = mapper.checkCart(cart);
		System.out.println("결과 : " + resultCart);
	}
}
