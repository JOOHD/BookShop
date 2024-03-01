package com.joo.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joo.model.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AdminMapperTests {

	@Autowired
	private AdminMapper mapper;
	
	/* 상품 등록  
	@Test
	public void bookEnrollTest() throws Exception{
		
		BookVO book = new BookVO();
		
		book.setBookName("calendar test");
		book.setAuthorId(96);
		book.setPubleYear("2023-12-31");
		book.setPublisher("calendar test");
		book.setCateCode("102001");
		book.setBookPrice(40000);
		book.setBookStock(100);
		book.setBookDiscount(0.15);
		book.setBookIntro("책 소개 ");
		book.setBookContents("책 목차 ");
		
		mapper.bookEnroll(book);
	}
	*/
	
	/* 카테고리 리스트
	@Test
	public void cateListTest() throws Exception{
		
		System.out.println("cateList().........." + mapper.cateList());
	}
	 */
	
	/* 상품 리스트 & 상품 총 갯수 
	@Test
	public void goodsGetListTest() {
		
		Criteria cri = new Criteria();
		
		/* 검색 조건 
		cri.setKeyword("테스트");
		
		/* 검색 리스트 
		List list = mapper.goodsGetList(cri);
		for(int i = 0; i < list.size(); i++) {
			System.out.println("result......" + i + " : " + list.get(i));
		}
		
		/* 상품 총 갯수 
		int result = mapper.goodsGetTotal(cri);
		System.out.println("result......" + result);
		
	}
	*/
	
	/* 상품 조회 페이지 
	@Test
	public void goodsGetDetailTest() {
		
		int bookId = 2000;
		
		BookVO result = mapper.goodsGetDetail(bookId);
		
		System.out.println("상품 조회 데이터 : " + result);
	}
	*/
	
	/* 상품 정보 수정 
	@Test
	public void goodsModifyTest() {
		
		BookVO book = new BookVO();
		
		book.setBookId(1);
		book.setBookName("mapper 테스트");
		book.setAuthorId(7);
		book.setPubleYear("2024-01-09");
		book.setPublisher("출판사1");
		book.setCateCode("103002");
		book.setBookPrice(70000);
		book.setBookStock(100);
		book.setBookDiscount(0.65);
		book.setBookIntro("책 소개 ");
		book.setBookContents("책 목차 ");
		
		mapper.goodsModify(book);
	}
	*/
	
	/* 상품 정보 삭제 
	@Test
	public void goodsDeleteTest() {
		
		int bookId = 100;
		
		int result = mapper.goodsDelete(bookId);
		
		if(result == 1) {
			System.out.println("삭제 성공");
		}
	}
	*/
	
	/* 이미지 등록 
	@Test
	public void imageEnrollTest() {
		
		AttachImageVO vo = new AttachImageVO();
		
		vo.setBookId(1);
		vo.setFileName("Test");
		vo.setUploadPath("Test");
		vo.setUuid("Test");
		
		mapper.imageEnroll(vo);
	}
	*/
	
	/* 지정 상품 이미지 삭제 
	@Test
	public void deleteImageAllTEST() {
		
		int bookId = 40;
		
		mapper.deleteImageAll(bookId);
	}
	*/
	
	/* 어제자 날짜 이미지 리스트 
	@Test
	public void checkImageList() {
		
		mapper.checkFileList();
		
	}
	*/
	
	/* 지정 상품 이미지 정보 얻기 
	@Test
	public void getAttachInfoTest() {
		
		int bookId = 41;
		
		List<AttachImageVO> list = mapper.getAttachInfo(bookId);
		
		System.out.println("list : " + list );
	}
	*/
	
	
}
