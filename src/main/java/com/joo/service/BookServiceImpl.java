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

	@Override
	public List<BookVO> getGoodsList(Criteria cri) {

		log.info("getGoodsList().......");

		return bookMapper.getGoodsList(cri);
	}

	@Override
	public int goodsGetTotal(Criteria cri) {

		log.info("getGoodsList().......");

		return bookMapper.goodsGetTotal(cri);
	}

}
