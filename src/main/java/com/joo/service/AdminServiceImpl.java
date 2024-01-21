package com.joo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.AdminMapper;
import com.joo.model.BookVO;
import com.joo.model.CateVO;
import com.joo.model.Criteria;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

	// private static final Logger log =
	// LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private AdminMapper adminMapper;

	@Override
	public void bookEnroll(BookVO book) {

		log.info("(service)bookEnroll..........");

		adminMapper.bookEnroll(book);

	}

	@Override
	public List<CateVO> cateList() {

		log.info("(service)cateList............");

		return adminMapper.cateList();
	}

	@Override
	public List<BookVO> goodsGetList(Criteria cri) {

		log.info("(service)goodsGetList.............");

		return adminMapper.goodsGetList(cri);
	}

	@Override
	public int goodsGetTotal(Criteria cri) {

		log.info("(service)goodsGetTotal.............");

		return adminMapper.goodsGetTotal(cri);
	}

	@Override
	public BookVO goodsGetDetail(int bookId) {
	
		log.info("(service)goodsGetDetail.............");
		
		return adminMapper.goodsGetDetail(bookId);
	}

	@Override
	public int goodsModify(BookVO vo) {

		log.info("(service)goodsGetModify.............");
				
		return adminMapper.goodsModify(vo);
	}

	@Override
	public int goodsDelete(int bookId) {
		
		log.info("(service)goodsDelete................");
		
		return adminMapper.goodsDelete(bookId);
		
	}

}
