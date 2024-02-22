package com.joo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.AuthorMapper;
import com.joo.model.AuthorVO;
import com.joo.model.Criteria;

@Service
public class AuthorServiceImpl implements AuthorService {

	private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);
	
	@Autowired
	AuthorMapper authorMapper;

	@Override
	public void authorEnroll(AuthorVO author) throws Exception {
		
		log.info("(service) authorEnroll()....." + author);
		
		authorMapper.authorEnroll(author);
		
	}

	@Override
	public List<AuthorVO> authorGetList(Criteria cri) throws Exception {
		
		log.info("(service) authorGetList()....." + cri);
		
		return authorMapper.authorGetList(cri);
	}

	@Override
	public int authorGetTotal(Criteria cri) throws Exception {
		
		log.info("(service) authorGetTotal()....." + cri);
		
		return authorMapper.authorGetTotal(cri);
	}

	@Override
	public AuthorVO authorGetDetail(int authorId) throws Exception {
		
		log.info("(service) authorGetDetail()....." + authorId);
		
		return authorMapper.authorGetDetail(authorId);
	}

	@Override
	public int authorModify(AuthorVO author) throws Exception {

		log.info("(service) authorModify()....." + author);
		
		return authorMapper.authorModify(author);
	}
	
	@Override
	public int authorDelete(int authorId) {
		
		log.info("(service)authorDelete...............");
		
		return authorMapper.authorDelete(authorId);
	}
	
}
