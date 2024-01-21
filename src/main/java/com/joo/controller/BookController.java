package com.joo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {
	
	private static final Logger log = LoggerFactory.getLogger(BookController.class);

	@GetMapping(value = "main")
	public void mainPageGet() {
		
		log.info("메인 페이지 진입");
	}
}
