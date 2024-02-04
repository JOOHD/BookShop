package com.joo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joo.mapper.AdminMapper;
import com.joo.model.BookVO;
import com.joo.model.CateVO;
import com.joo.model.Criteria;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
	
	// private static final Logger log = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private AdminMapper adminMapper;

	/* 상품 등록 */
	@Transactional
	@Override
	public void bookEnroll(BookVO book) {

		log.info("(service)bookEnroll..........");

		// Mapper 상품 등록 호출
		adminMapper.bookEnroll(book);
		
		// ImageList 유효성 검사.
		if(book.getImageList() == null || book.getImageList().size() <= 0) {
			return;
		}
		
		/*  
		//	기본 for문
			for(int i = 0; i < book.getImageList().size(); i++){
			
				AttachImageVO vo = book.getImageList().get(i);
				vo.setBookId(book.getBookId());
				vo.setFileName(book.getFileName());
				
				adminMapper.imageEnroll(vo);
			}
			
		//	향상된 for문
			for(AttachImageVO attach : book.getImageList()){}	
		*/
		
        // 람다식 활용한 for문
		book.getImageList().forEach(attach ->{	// 'BookVO' 객체의 이미지 목록 가져오기(attach 변수로 이미지 받기.
			
			attach.setBookId(book.getBookId()); // 순회 중인 이미지(attach)의 bookId를 'BookVO' 객체의 bookId로 설정.
												// 이미지를 등록할 때 각 이미지가 어떤 상품에 속하는지를 나타 냄. 
			adminMapper.imageEnroll(attach);	// attach를 imageEnroll() 메서드에 전달. 이 메서드는 db에 등록하는 역할.
			
			log.info("attach : " + attach);
		});
	}

	/* 카테고리 리스트 */
	@Override
	public List<CateVO> cateList() {

		log.info("(service)cateList............");

		return adminMapper.cateList();
	}

	/* 상품 리스트 */
	@Override
	public List<BookVO> goodsGetList(Criteria cri) {

		log.info("(service)goodsGetList.............");

		return adminMapper.goodsGetList(cri);
	}

	/* 상품 총 갯수 */
	@Override
	public int goodsGetTotal(Criteria cri) {

		log.info("(service)goodsGetTotal.............");

		return adminMapper.goodsGetTotal(cri);
	}

	/* 상품 조회 페이지 */
	@Override
	public BookVO goodsGetDetail(int bookId) {
	
		log.info("(service)goodsGetDetail.............");
		
		return adminMapper.goodsGetDetail(bookId);
	}

	/* 상품 정보 수정 */
	@Override
	public int goodsModify(BookVO vo) {

		log.info("(service)goodsGetModify.............");
				
		return adminMapper.goodsModify(vo);
	}

	/* 상품 정보 삭제 */
	@Override
	public int goodsDelete(int bookId) {
		
		log.info("(service)goodsDelete................");
		
		return adminMapper.goodsDelete(bookId);
		
	}

}
