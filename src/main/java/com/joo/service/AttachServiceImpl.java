package com.joo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joo.mapper.AttachMapper;
import com.joo.model.AttachImageVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AttachServiceImpl implements AttachService {

	@Autowired
	private AttachMapper attachMapper;
	
	@Override
	public List<AttachImageVO> getAttachList(int bookId) {

		log.info("getAttachList.........");
		
		return attachMapper.getAttachList(bookId);
		
	}

	
}
