package com.joo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joo.model.AttachImageVO;
import com.joo.model.BookVO;
import com.joo.model.Criteria;
import com.joo.model.PageDTO;
import com.joo.service.AttachService;
import com.joo.service.BookService;
import com.joo.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"상품 관련 페이지 Controller"})
@Controller
public class BookController {

	private static final Logger log = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private AttachService attachService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private CartService cartService;

	@ApiOperation(value = "이미지 정보 반환")
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AttachImageVO>> getAttachList(int bookId) {

		log.info("getAttachList.........." + bookId);

		return new ResponseEntity<List<AttachImageVO>>(attachService.getAttachList(bookId), HttpStatus.OK);
	}

	@ApiOperation(value = "메인 페이지 이동")
	@GetMapping(value = "main")
	public void mainPageGet(Model model) {

		log.info("메인 페이지 진입");

		model.addAttribute("cate1", bookService.getCateCode1());
		model.addAttribute("cate2", bookService.getCateCode2());
	}

	@ApiOperation(value = "이미지 출력")
	@GetMapping("/display")
	public ResponseEntity<byte[]> getImage(String fileName) { // '파일 경로'+'파일 이름'을 전달받기 위해 String 타입

		log.info("getImage().........." + fileName);

		// file = ('파일 경로' + '파일 이름') , '\\' 두개 사용한 이유는 인식할 수 있게 escape 문자 사용.
		File file = new File("c:\\upload\\" + fileName);

		// 뷰로 반환할 ResponseEntity 객체의 선언.
		ResponseEntity<byte[]> result = null;

		try {

			// Response의 header와 관련된 설정의 객체 선언.
			HttpHeaders header = new HttpHeaders();

			// header의 'Content Type'에 대상 파일의 MIME TYPE을 부여해주는 코드를 추가.
			header.add("Content-type", Files.probeContentType(file.toPath()));

			// 첫 번째 파람 = 출력시킬 대상 이미지 데이터 파일/ copyTo = 파일을 복사하여 Byte 배열로 반환.
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@ApiOperation(value = "상품 검색")
	@GetMapping("/search")
	public String searchGoodsGET(Criteria cri, Model model) {

		log.info("cri : " + cri);

		// 상품 검색
		List<BookVO> list = bookService.getGoodsList(cri);

		log.info("pre list : " + list);

		if (!list.isEmpty()) { // 상품 목록이 있는 경우,
			model.addAttribute("list", list);

			log.info("list : " + list);
		} else { 			   // 상품 목록이 없는 경우.
			model.addAttribute("listCheck", "empty");

			return "search";
		}
		// 페이지 번호, 상품 게시물 리스트
		model.addAttribute("pageMaker", new PageDTO(cri, bookService.goodsGetTotal(cri)));

		String[] typeArr = cri.getType().split("");

		for (String s : typeArr) {
			if (s.equals("T") || s.equals("A")) {
				model.addAttribute("filter_info", bookService.getCateInfoList(cri));
			}
		}

		return "search";
	}
	
	@ApiOperation(value = "상품 상세")
	@GetMapping("/goodsDetail/{bookId}") // {bookId} : Spring에서 사용자가 전송한 식별자 값을 변수로 인식하도록 템플릿 변수
	public String goodsDetailGet(@PathVariable int bookId, Model model) {
		
		log.info("goodsDetailGET()...........");
		
		model.addAttribute("goodsInfo", bookService.getGoodsInfo(bookId));
		
		return "/goodsDetail";
	}
}
