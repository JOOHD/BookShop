package com.joo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joo.model.CartDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartServiceTests {

	@Autowired
	private CartService service;
	
	// 등록 테스트
	@Test
	public void addCartTest() {		
		//given
		String memberId = "test";
		int bookId = 6;
		int count = 5;
		
		CartDTO dto = new CartDTO();
		dto.setMemberId(memberId);
		dto.setBookId(bookId);
		dto.setBookCount(count);
		
		//when
		int result = service.addCart(dto);
		
		//then
		System.out.println("** result : " + result);
		
	}
	
}
