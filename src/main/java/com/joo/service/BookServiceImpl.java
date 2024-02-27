package com.joo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.AdminMapper;
import com.joo.mapper.BookMapper;
import com.joo.model.BookVO;
import com.joo.model.Criteria;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;

	/* 상픔 검색 */
	@Override
	public List<BookVO> getGoodsList(Criteria cri) {

		log.info("getGoodsList().......");

		/*	
		 	getAuthorIdList()는 Criteria 객체의 authorArr 값을 저장하게 해주는 역할인 
			getGoodsList() Service에서 Criteria authorArr이 부여되면, 이 객체를 그대로 getGoodsTotal에 사용.
			그래서 불 필요한 중복을 방지하고자 getGoodsList에만 구현.		  
		 */
		String type= cri.getType();
		String[] typeArr = type.split("");
		
		for(String t : typeArr) {
			if(t.equals("A")) {
				String[] authorArr = bookMapper.getAuthorIdList(cri.getKeyword());
				cri.setAuthorArr(authorArr);
			}
		}
		
		return bookMapper.getGoodsList(cri);
	}

	/* 상품 총 갯수 */
	@Override
	public int goodsGetTotal(Criteria cri) {

		log.info("getGoodsList().......");

		return bookMapper.goodsGetTotal(cri);
	}

}
