package com.joo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.joo.mapper.OrderMapper;
import com.joo.model.OrderPageItemDTO;

public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders) {
		
		// 주문은 다수의 갯수가 가능하니까 List.
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		
		for(OrderPageItemDTO ord : orders ) {
			
			// 상품 정보 메서드를 호출하여 goodsInfo 반환받은 객체에 저장.
			OrderPageItemDTO goodsInfo = orderMapper.getGoodsInfo(ord.getBookId());
			
			// goodsInfo 변수에는 상품 수량이 없기 때문에 view로부터 전달받은 수량 대입.
			goodsInfo.setBookCount(ord.getBookCount());
		}
		return result;
	}

}
