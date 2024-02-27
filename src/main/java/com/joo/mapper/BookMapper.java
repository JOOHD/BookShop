package com.joo.mapper;

import java.util.List;

import com.joo.model.BookVO;
import com.joo.model.Criteria;

public interface BookMapper {
	
	/* 상품 검색 */
	public List<BookVO> getGoodsList(Criteria cri); // 페이징 데이터 호출

	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri); // 검색 결과에 대한 데이터
	
	/* 
	   	작성할 Mapper 메서드의 반환 결과를 Criteria authorArr 변수에 저장 할 것이다.
	   	authorArr은 String 타입 -> 작가 이름이 중복 될 경우를 방지하기 위해.
	   		ex) '유흥준'이 2명 or '유흥'만 검색을 할 경우 해당 키워드를 가진 authorId를 모두 반환할 수 있기 떄문에. 
	 */
	
	/* 작가 id 리스트 요청 */
	public String[] getAuthorIdList(String keyword); 
}
