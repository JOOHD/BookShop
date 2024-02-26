package com.joo.mapper;

import java.util.List;

import com.joo.model.BookVO;
import com.joo.model.Criteria;

public interface BookMapper {
	
	/* 상품 검색 */
	public List<BookVO> getGoodsList(Criteria cri); // 페이징 데이터 호출

	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri); // 검색 결과에 대한 데이터
}
