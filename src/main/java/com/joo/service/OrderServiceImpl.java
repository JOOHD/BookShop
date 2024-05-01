package com.joo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joo.mapper.AttachMapper;
import com.joo.mapper.BookMapper;
import com.joo.mapper.CartMapper;
import com.joo.mapper.MemberMapper;
import com.joo.mapper.OrderMapper;
import com.joo.model.AttachImageVO;
import com.joo.model.BookVO;
import com.joo.model.CartDTO;
import com.joo.model.CheckOrderVO;
import com.joo.model.MemberVO;
import com.joo.model.OrderCancelDTO;
import com.joo.model.OrderDTO;
import com.joo.model.OrderItemDTO;
import com.joo.model.OrderPageItemDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service // 꼭 붙여야 한다.
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private BookMapper bookMapper;
	
	/* 상품 정보 */
	@Override
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders) {
		
		// 주문은 다수의 갯수가 가능하니까 List.
		List<OrderPageItemDTO> result = new ArrayList<OrderPageItemDTO>();
		
		for(OrderPageItemDTO ord : orders ) {
			
			
			// log.info("orders : " + orders); // orders : view(장바구니 페이지) 전달받은 상품 정보(bookId)
			// 상품 정보 메서드를 호출하여 goodsInfo 반환받은 객체에 저장.
			OrderPageItemDTO goodsInfo = orderMapper.getGoodsInfo(ord.getBookId());
			
			// goodsInfo 변수에는 상품 수량이 없기 때문에 view로부터 전달받은 수량 대입.
			goodsInfo.setBookCount(ord.getBookCount());
			
			// (salePrice, totalPrice, savePoint, totalSavePoint) 값 세팅.
			goodsInfo.initSaleTotal();
			
			List<AttachImageVO> imageList = attachMapper.getAttachList(goodsInfo.getBookId());
			
			goodsInfo.setImageList(imageList);
			
			// 만들어내야 할 상품 정보가 모두 세팅된 객체(List) 추가.
			result.add(goodsInfo);
		}
		return result;
	}
	
	/* 주문 확인(재고, 금액 마이너스 방지) */
	@Override
	public int checkOrder(CheckOrderVO co) {
				
		BookVO book = bookMapper.getGoodsInfo(co.getBookId());
        co.setBookStock(book.getBookStock());
        return orderMapper.checkOrder(co);
		
	}

	@Override
	@Transactional // 여러 개의 쿼리 작업 시,
	public int order(OrderDTO ord) {
		
		/* 사용할 데이터 가져오기 */
			/* 회원 정보 */
			MemberVO member = memberMapper.getMemberInfo(ord.getMemberId());
			/* 주문 정보 */
			List<OrderItemDTO> ords = new ArrayList<>();
			for(OrderItemDTO oit : ord.getOrders()) {
				OrderItemDTO orderItem = orderMapper.getOrderInfo(oit.getBookId());
			
				log.info("oit : " + oit);
				
				// 수량 셋팅 (joo_book tb에는 bookCount가 없음)
				orderItem.setBookCount(oit.getBookCount());
				// 주문 아이템 번호 가져오기
				// orderItem.getOrderItemId();
				// 기본정보 셋팅
				orderItem.initSaleTotal();
				// List객체 추가
				ords.add(orderItem);
			}
			/* OrderDTO 셋팅 */
			ord.setOrders(ords);
			ord.getOrderPriceInfo();
			
		/* DB 주문, 주문상품(배송정보) 넣기 */
			/* orderId 만들기 및 OrderDTO 객체 orderId에 저장 */
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
			
			// joo_order tb에는 auto_increasement 적용 안함 -> 직접 부여 해주어야 됨.
			// orderId = "회원 아이디" + "_년도 월 일 분"
			String orderId = member.getMemberId() + format.format(date);
			
			
			ord.setOrderId(orderId);
			
			
			/* DB 넣기 */
			orderMapper.enrollOrder(ord); 				// joo_order 등록
			
			for(OrderItemDTO oit : ord.getOrders()) {	// joo_orderItem 등록
				
				oit.setOrderId(orderId);
				
				orderMapper.enrollOrderItem(oit);
			}
		
		/* 비용 포인트 변동 적용 */
			/* 비용 차감 & 변동 돈(money) Member객체 적용 */
			int calMoney = member.getMoney();
			calMoney -= ord.getOrderFinalSalePrice(); 	// 상품 주문에서 사용한 포인트 차감.
			if (member.getMoney() <= 0) { // 재고가 부족한 경우	           
	            return 0;  				  // 클라이언트에게 금액 부족 메시지 반환
	        }
			member.setMoney(calMoney);					// 주문으로 인해 획득할 포인트 변수에 저장.
			
			/* 포인트 차감, 포인트 증가 & 변동 포인트 Member객체 적용 */
			int calPoint = member.getPoint();
			calPoint = calPoint - ord.getUsePoint() + ord.getOrderSavePoint();	// 기존 포인트 - 사용 포인트 + 획득 포인트
			member.setPoint(calPoint);
			
			/* 변동 돈, 포인트 DB 적용 */
			orderMapper.deductMoney(member);
			
		/* 재고 변동 적용 */
			for(OrderItemDTO oit : ord.getOrders()) {
				/*변동 재고 값 구하기 */
				BookVO book = bookMapper.getGoodsInfo(oit.getBookId());
				book.setBookStock(book.getBookStock() - oit.getBookCount());
				
				if (book.getBookStock() <= 0) { // 재고가 부족한 경우	           
		            return 1;  					// 클라이언트에게 재고 부족 메시지 반환
		        }				
				log.info("book : " + book);
				
				/* 변동 값 DB 적용 */
				orderMapper.deductStock(book);
			}
		
		/* 장바구니 제거 */
			for(OrderItemDTO oit : ord.getOrders()) {
				CartDTO dto = new CartDTO();
				dto.setMemberId(ord.getMemberId());
				dto.setBookId(oit.getBookId());
				
				cartMapper.deleteOrderCart(dto);
			}	
			return 2; // 정상 결제
	}
	
	
	/* 주문 취소 */
	@Override
	@Transactional
	public void orderCancel(OrderCancelDTO dto) {
		
		/* 주문, 주문상품 객체 */
			/*회원*/
				MemberVO member = memberMapper.getMemberInfo(dto.getMemberId());
			/*주문상품*/
				List<OrderItemDTO> ords = orderMapper.getOrderItemInfo(dto.getOrderId());
				for(OrderItemDTO ord : ords) {
					ord.initSaleTotal();
				}
			/* 주문 */
				OrderDTO orw = orderMapper.getOrder(dto.getOrderId());
				orw.setOrders(ords);
			
				orw.getOrderPriceInfo();
			
			/* 주문상품 취소 DB */
				orderMapper.orderCancel(dto.getOrderId());
			
			/* 돈, 포인트, 재고 변환 */
				/* 돈 */
				int calMoney = member.getMoney();
				calMoney += orw.getOrderFinalSalePrice();
				member.setMoney(calMoney);
				
				/* 포인트 */
				int calPoint = member.getPoint();
				calPoint = calPoint + orw.getUsePoint() - orw.getOrderSavePoint();
				member.setPoint(calPoint);
				
					/* DB적용 */
					orderMapper.deductMoney(member);
				
			/* 재고 */
			for(OrderItemDTO ord : orw.getOrders()) {
				BookVO book = bookMapper.getGoodsInfo(ord.getBookId());
				book.setBookStock(book.getBookStock() + ord.getBookCount());
				orderMapper.deductStock(book);
			}		
		
		
	}


}
