package com.joo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joo.model.BookVO;
import com.joo.model.MemberVO;
import com.joo.model.OrderDTO;
import com.joo.model.OrderItemDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderMapperTests {

	@Autowired
	private OrderMapper mapper;
	
	/* 상품 정보(주문 처리) */
	@Test
	public void getOrderInfoTest() {
		
		OrderItemDTO orderInfo = mapper.getOrderInfo(46);
		
		System.out.println("result : " + orderInfo);
	}

	
	/* joo_order 테이블 등록 */
	@Test
	public void enrollOrderTest() {
		
		OrderDTO ord = new OrderDTO();
		List<OrderItemDTO> orders = new ArrayList<OrderItemDTO>();
		
		OrderItemDTO order1 = new OrderItemDTO();
		
		order1.setBookId(46);
		order1.setBookCount(48);
		order1.setBookPrice(40800);
		order1.setBookDiscount(0.48);
		order1.initSaleTotal();
		
		ord.setOrders(orders);
		
		ord.setOrderId("2024_test");
		ord.setAddressee("test");
		ord.setMemberId("admin");
		ord.setMemberAddr1("test");
		ord.setMemberAddr2("test");
		ord.setMemberAddr3("test");
		ord.setOrderState("배송중비");
		ord.getOrderPriceInfo();
		ord.setUsePoint(1000);
		
		mapper.enrollOrder(ord);
	}

	
	/* joo_itemOrder 테이블 등록 */
	@Test
	public void enrollOrderItemTest() {
		
		OrderItemDTO oid = new OrderItemDTO();
		
		oid.setOrderId("2024_test");
		oid.setBookId(45);
		oid.setBookCount(3);
		oid.setBookPrice(30700);
		oid.setBookDiscount(0.05);
				
		oid.initSaleTotal();
		
		mapper.enrollOrderItem(oid);
		
	}

	
	/* 회원 돈, 포인트 정보 변경 */
	@Test
	public void deductMoneyTest() {
		
		MemberVO member = new MemberVO();
		
		member.setMemberId("admin");
		member.setMoney(500000);
		member.setPoint(10000);
		
		mapper.deductMoney(member);
	}
	
	
	/* 상품 재고 변경 */
	@Test
	public void deductStockTest() {
		BookVO book = new BookVO();
		
		book.setBookId(48);
		book.setBookStock(48);
		
		mapper.deductStock(book);
	}

}
