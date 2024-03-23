package com.joo.service;

import java.util.List;

import com.joo.model.BookVO;
import com.joo.model.CateFilterDTO;
import com.joo.model.CateVO;
import com.joo.model.Criteria;

public interface BookService {
	
	/* 상품 검색 */
	public List<BookVO> getGoodsList(Criteria cri);
	
	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri);
	
	/* 상품 정보 */
	public BookVO getGoodsInfo(int bookId);
	
	/* 작가 id 리스트 요청 */
	// public String[] getAuthorIdList(String keyword); 구현해도 상관 없다. 
	
	/* 국내 카테고리 리스트 */
	public List<CateVO> getCateCode1();
	
	/* 국내 카테고리 리스트 */
	public List<CateVO> getCateCode2();
	
	/* 검색 대상 카테고리 리스트 */
	public String[] getCateList(Criteria cri);
	
	/* 카테고리 정보(+검색대상 갯수) */
	public CateFilterDTO getCateInfo(Criteria cri);
	
	/* 카테고리 검색결과 필터 정보 */
	public List<CateFilterDTO> getCateInfoList(Criteria cri);

}
