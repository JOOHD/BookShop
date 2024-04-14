package com.joo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.AttachMapper;
import com.joo.mapper.OrderMapper;
import com.joo.model.AttachImageVO;
import com.joo.model.OrderPageItemDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service // 꼭 붙여야 한다.
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
	@Override
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders) {
		
		// 주문은 다수의 갯수가 가능하니까 List.
		List<OrderPageItemDTO> result = new ArrayList<OrderPageItemDTO>();
		
		for(OrderPageItemDTO ord : orders ) {
			
			/*
			 * orders : [bookId=45, bookCount=5, bookName=null, bookPrice=0, bookDiscount=0.0, salePrice=0, totalPrice=0, point=0, totalPoint=0]...
			 * ord : orders[0]
			 * goodsInfo1 : [bookId=45, bookCount=0, bookName=ideology, bookPrice=30700, bookDiscount=0.05, salePrice=0, totalPrice=0, point=0, totalPoint=0]
			 * goodsInfo2 : [bookId=45, bookCount=5, bookName=ideology, bookPrice=30700, bookDiscount=0.05, salePrice=0, totalPrice=0, point=0, totalPoint=0]
			 * goodsInfo3 : [bookId=45, bookCount=5, bookName=ideology, bookPrice=30700, bookDiscount=0.05, salePrice=29165, totalPrice=145825, point=1458, totalPoint=7290]
			 */
			
			
			log.info("orders : " + orders); // orders : view(장바구니 페이지) 전달받은 상품 정보(bookId)
			log.info("ord : " + ord);
			// 상품 정보 메서드를 호출하여 goodsInfo 반환받은 객체에 저장.
			OrderPageItemDTO goodsInfo = orderMapper.getGoodsInfo(ord.getBookId());
			
			log.info("goodsInfo1 : " + goodsInfo); 
			
			// goodsInfo 변수에는 상품 수량이 없기 때문에 view로부터 전달받은 수량 대입.
			goodsInfo.setBookCount(ord.getBookCount());
			
			log.info("goodsInfo2 : " + goodsInfo); 
			
			// (salePrice, totalPrice, savePoint, totalSavePoint) 값 세팅.
			goodsInfo.initSaleTotal();
			
			List<AttachImageVO> imageList = attachMapper.getAttachList(goodsInfo.getBookId());
			
			goodsInfo.setImageList(imageList);
			
			log.info("goodsInfo3 : " + goodsInfo); // 계산된 값 세팅
			// 만들어내야 할 상품 정보가 모두 세팅된 객체(List) 추가.
			result.add(goodsInfo);
		}
		return result;
	}

}
