package com.joo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.AdminMapper;
import com.joo.mapper.AttachMapper;
import com.joo.mapper.BookMapper;
import com.joo.model.AttachImageVO;
import com.joo.model.BookVO;
import com.joo.model.CateFilterDTO;
import com.joo.model.CateVO;
import com.joo.model.Criteria;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private AdminMapper adminMapper;

	/* 상픔 검색 */
	@Override
	public List<BookVO> getGoodsList(Criteria cri) {

		log.info("getGoodsList().......");

		/*
		 * getAuthorIdList()는 Criteria 객체의 authorArr 값을 저장하게 해주는 역할인 getGoodsList()
		 * Service에서 Criteria authorArr이 부여되면, 이 객체를 그대로 getGoodsTotal에 사용. 그래서 불필요한 중복을
		 * 방지하고자 getGoodsList에만 구현.
		 */
		String type = cri.getType();
		String[] typeArr = type.split("");
		String[] authorArr = bookMapper.getAuthorIdList(cri.getKeyword());

		if (type.equals("A") || type.equals("AC") || type.equals("AT") || type.equals("ACT") || type.equals("T")) {
			if (authorArr.length == 0) { // authorArr이 빈 배열일 경우
				return new ArrayList<BookVO>(); // Controller에 요소가 없는 빈 List 반환.
			}
		}

		for (String t : typeArr) {
			if (t.equals("A")) {
				cri.setAuthorArr(authorArr);
			}
		}

		List<BookVO> list = bookMapper.getGoodsList(cri);

		list.forEach(book -> { // 이미지 정보를 DB로부터 호출, 값을 BookVO 객체의 imageList에 저장.

			int bookId = book.getBookId();

			List<AttachImageVO> imageList = attachMapper.getAttachList(bookId);

			book.setImageList(imageList);
		});

		// 상품 정보/이미지 저장된 변수, return bookMapper.getGoodsList(cri);
		return list;
	}

	/* 상품 총 갯수 */
	@Override
	public int goodsGetTotal(Criteria cri) {

		log.info("getGoodsList().......");

		return bookMapper.goodsGetTotal(cri);
	}

	/* 국내 카테고리 리스트 */
	@Override
	public List<CateVO> getCateCode1() {

		log.info("getCateCode1().......");

		return bookMapper.getCateCode1();
	}

	/* 외국 카테고리 리스트 */
	@Override
	public List<CateVO> getCateCode2() {

		log.info("getCateCode1().......");

		return bookMapper.getCateCode2();
	}

	/* 검색 대상 카테고리 리스트 */
	@Override
	public String[] getCateList(Criteria cri) {
		
		// 코드 번호를 String 배열에 담아서 반환.
		log.info("getCateList().......");
		
		return bookMapper.getCateList(cri);
	}

	/* 카테고리 정보(+검색대상 갯수) */
	@Override
	public CateFilterDTO getCateInfo(Criteria cri) {
		
		// '카테고리 이름', '카테고리 코드', '개수' 정보 CateFilterVO 반환.
		log.info("getCateInfo().......");
		
		return bookMapper.getCateInfo(cri);			
	}

	/* 검색결과 카테고리 필터 정보 */
	@Override
	public List<CateFilterDTO> getCateInfoList(Criteria cri) {

		log.info("getCateInfoList().......");
		
		// 반환할 데이터 담길 그릇 생성
		List<CateFilterDTO> filterInfoList = new ArrayList<CateFilterDTO>();
		
		String[] typeArr = cri.getType().split("");
		String [] authorArr;
		
		// type 분류
		for(String type : typeArr) {
			if(type.equals("A")) {
				// authorId의 정보가 필요, authorIdList 호출하여 id 반환 후, cri에 추가.
				authorArr = bookMapper.getAuthorIdList(cri.getKeyword());
				if(authorArr.length == 0) {
					return filterInfoList;
				}
				cri.setAuthorArr(authorArr);					
			}
		}
		
		// '카테고리 코드' 반환해주는 리스트 호출
		String[] cateList = bookMapper.getCateList(cri);
		
		// cateCode 기존 값을 유지 해 주기 위한 임식 변수 생성.
		// String tempCateCode = cri.getCateCode();
		
		// cateCode 분류
		for(String cateCode : cateList) {
			cri.setCateCode(cateCode);
			
			log.info("cateCode : " + cateCode);
			log.info("cri : " + cri);
			
			// 카테고리 필터 정보를 List 객체에 담기.
			CateFilterDTO filterInfo = bookMapper.getCateInfo(cri);
			filterInfoList.add(filterInfo); // getCateInfoList() 반환 할 List객체 요소로 추가.
			
			log.info("filterInfo : " + filterInfo);
			log.info("filterInfoList : " + filterInfoList);
		}
		
		// 임시 카테고리 코드 값을 cateCode에 저장. 
		// cri.setCateCode(tempCateCode);

		// '카테고리 코드'가 담긴 List 객체 "filterInfoList" 추가.
		return filterInfoList;
	}

	@Override
	public BookVO getGoodsInfo(int bookId) {
		
		log.info("getGoodsInfo..........");
		
		BookVO goodsInfo = bookMapper.getGoodsInfo(bookId);
		goodsInfo.setImageList(adminMapper.getAttachInfo(bookId));
		
		return goodsInfo;
	}
	
	

}
