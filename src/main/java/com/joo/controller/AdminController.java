package com.joo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joo.model.AttachImageVO;
import com.joo.model.AuthorVO;
import com.joo.model.BookVO;
import com.joo.model.Criteria;
import com.joo.model.PageDTO;
import com.joo.service.AdminService;
import com.joo.service.AuthorService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private AdminService adminService;
	
	/* 관리자 메인 페이지 이동 */
	@GetMapping(value="main")
	public void adminMainGET() throws Exception{
		
		log.info("관리자 페이지 이동");
	}
	
	/* 상품 등록 페이지 접속 */
	@GetMapping(value="goodsEnroll")
	public void goodsEnrollGET(Model model) throws Exception {
		
		log.info("상품 등록 페이지 접속");
		
		// static method가 아니기 때문에 바로 사용 불가, OM 클래스를 인스턴스화 하야 사용.
		ObjectMapper objm = new ObjectMapper();
		
		// 리스트 데이터를 goodsEnroll.jsp에 전달 해주어야 하기 떄문에 list 변수에 저장.
		List list = adminService.cateList();
		
		// java 객체를 String 타입의 JSON 형식으로 변환.
		String cateList = objm.writeValueAsString(list);
		
		model.addAttribute("cateList", cateList);
		
	}
	
	/* 상품 등록 */
	@PostMapping(value="/goodsEnroll")
	public String goodsEnrollPOST(BookVO book, RedirectAttributes rttr) {
	
		log.info("goodsEnrollPOST...." + book);
		
		adminService.bookEnroll(book);
		
		rttr.addFlashAttribute("enroll_result", book.getBookName());
		
		return "redirect:/admin/goodsManage";
	}
	
	/* 상품 관리 페이지 접속 */
	@GetMapping(value="goodsManage")
	public void goodsManageGET(Criteria cri, Model model) throws Exception {
		
		log.info("상품 관리 페이지 접속");
		
		/* 상품 리스트 데이터 */
		List list = adminService.goodsGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}
		
		/* 페이지 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, adminService.goodsGetTotal(cri)));
		
	}
	
	/* 상품 조회 페이지, 상품 수정 페이지 */
	@GetMapping({"/goodsDetail", "/goodsModify"})
	public void goodsGetInfoGET(int bookId, Criteria cri, Model model) throws JsonProcessingException {
		
		log.info("goodsGetInfo()........." + bookId);
		
		ObjectMapper mapper = new ObjectMapper();
		
		/* 카테고리 리스트 데이터 */
		model.addAttribute("cateList", mapper.writeValueAsString(adminService.cateList()));		
		
		/* 목록 페이지 조건 정보 */
		model.addAttribute("cri", cri);
		
		/* 조회 페이지 정보 */
		model.addAttribute("goodsInfo", adminService.goodsGetDetail(bookId));
		
	}	
	
	/* 상품 정보 수정 */
	@PostMapping("/goodsModify")
	public String goodsModifyPOST(BookVO vo, RedirectAttributes rttr) {
		
		log.info("goodsModifyPOST........." + vo);
		
		int result = adminService.goodsModify(vo);
		
		rttr.addFlashAttribute("modify_result", result);
		
		return "redirect:/admin/goodsManage";
		
	}
	
	/* 상품 정보 삭제 */
	@PostMapping("/goodsDelete")
	public String goodsDeletePOST(int bookId, RedirectAttributes rttr ) {
		
		log.info("goodsDeletePOST.........");
		
		int result = adminService.goodsDelete(bookId);
		
		rttr.addFlashAttribute("delete_result", result);
		
		return "redirect:/admin/goodsManage";
		
	}
	
	/* 작가 등록 페이지 접속 */
	@GetMapping(value="authorEnroll")
	public void authorEnrollGET() throws Exception {
		log.info("작가 등록 페이지 접속");
	}
	
	/* 작가 관리 페이지 접속 */
	@GetMapping(value="authorManage")
	public void authorManageGET(Criteria cri, Model model) throws Exception {
		
		log.info("작가 관리 페이지 접속........" + cri);
		
		/* 작가 목록 출력 데이터 */
		List list = authorService.authorGetList(cri);

		/* 검색 결과 존재 유무 판단 */
		if(!list.isEmpty()) {	// isEmpty() type boolean true or false
			model.addAttribute("list", list);			// 작가 존재 o
		} else {
			model.addAttribute("listCheck", "empty");   // 작가 존재 x 
		}
		
		/* 페이지 이동 인터페이스 데이터 */
        model.addAttribute("pageMaker", new PageDTO(cri, authorService.authorGetTotal(cri)));   
	} 
	
	/* 작가 등록 */
	@PostMapping(value="authorEnroll.do")
	public String authorEnrollPOST(AuthorVO author, RedirectAttributes rttr) throws Exception {
	
		log.info("authorEnroll :" + author);
		
		authorService.authorEnroll(author); // 작가 등록 쿼리 수행
		
		// server로부터 받은 '성공 여부 데이터'를 체크하여 존재할 시 작가 등록 성공 경고창을 나타냄.
		// 그러나 만약에 데이터가 계속 남아있으면 경고창은 계속 남음, 이를 방지하고자 addFlash 일회성 메서드 사용.
		rttr.addFlashAttribute("enroll_result", author.getAuthorName());
		
		return "redirect:/admin/authorManage";
	}
	
	/* 작가 상세, 수정 페이지 */
	@GetMapping({"/authorDetail", "/authorModify"})
	public void authorGetInfoGET(int authorId, Criteria cri, Model model) throws Exception{
		
		log.info("authorDetail......" + authorId);
		
		/* 작가 관리 페이지 정보 */
		model.addAttribute("cri", cri);
		
		/* 선택 작가 정보 */
		model.addAttribute("authorInfo", authorService.authorGetDetail(authorId));
	}
	
	/* 작가 정보 수정 */
	//@PostMapping(value="/authroModify")
	@RequestMapping(value="/authorModify", method = {RequestMethod.POST})
	public String authorModifyPOST(AuthorVO author, RedirectAttributes rttr) throws Exception{
		
		log.info("authorModify....." + author);
		
		int result = authorService.authorModify(author);
		
		rttr.addFlashAttribute("modify_result", result);
		
		return "redirect:/admin/authorManage";
	}
	
	/* 작가 검색 팝업창 */
	@GetMapping("/authorPop")
	public void authorPopGET(Criteria cri, Model model) throws Exception{
		
		// log.info("authorPopGET....");
		
		cri.setAmount(5); // 기존 목록 리스트는 10이지만 팝업창 크기가 작기에 조정.
		
		/* 게시물 출력 데이터 */
		List list = authorService.authorGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 작가 존재하지 않을 경우
		}
		
		
		/* 페이지 이동 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, authorService.authorGetTotal(cri)));	
	}
	
	/* 작가 정보 삭제 */
	@PostMapping("/authorDelete")
	public String authorDeletePOST(int authorId, RedirectAttributes rttr) {
		
		log.info("authorDeletePOST.....");
		
		int result = 0;
		
		try {
		
			result = authorService.authorDelete(authorId);
			
		} catch (Exception e) {

			e.printStackTrace();
			
			result = 2;
			
			rttr.addFlashAttribute("delete_result", result);
			
			return "redirect:/admin/authorManage";
		}
		
		rttr.addFlashAttribute("delete_result", result);
		
		return "redirect:/admin/authorManage";
		
	}
	
	/* 첨부 파일 업로드 */
	@PostMapping(value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE) // goodsEnroll.jsp ajax url
	public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPOST(MultipartFile[] uploadFile) { // 여러 개의 파일 업로드 처리를 위해 [] 추가.
		
		log.info("uploadAjaxActionPOST..........");
		
		/*
		  1. 이미지 파일 저장
		  2. 썸네일 이미지 파일 생성 및 저장
		  3. 각 이미지 정보 List 객체에 저장
		  4. ResponseEntity를 통해서 뷰(view)로 http 상태 코드가 200이고 http Body에 이미지 정보가 담긴 List 객체를 전송
		  5. MIME TYPE을 사용하여 이미지 파일 종류 체크.
		*/
		
		/* 이미지 파일 체크 */
		for(MultipartFile multipartFile : uploadFile) { // 전달받은 모든 파일(uploadFile)의 유형을 체크하기 위해 for문 구현
			 
			// 전달받은 파일(uploadFile)을 File 객체로 만들어주고 File 참조 변수에 대입.
			File checkFile = new File(multipartFile.getOriginalFilename()); // getOriginalFilename() : view에서 파일 이름 그대로 가져오는 메서드
			
			// MIME TYPE을 저장할 String type 변수 선언 후, null 초기화.
			String type = null;
			
			// 반환 메서드 probeContentType() MIME TYPE -> String 반환/ IOException 발생 가능성 높음.
			try {	
				type = Files.probeContentType(checkFile.toPath());
				log.info("MIME TYPE : " + type);	
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
			// MIME TYPE = image일 경우 첫 단어가 image로 시작, 이를 위해 startWIth() 메서드 사용.
			if(!type.startsWith("image")) {
				
				// status를 반환 위해, ResponseEntity 객체에 첨부해줄 값이 null임을 알리기 위해, <AttachImageVO> 타입 참조 변수 선언
				List<AttachImageVO> list = null;
				
				// if 구현부가 실행이 되었다는 것은 image가 아니라는 것이기 때문에 메서드 종료. 
				return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST); // view에 반환 시, (null, 400);
			}
		}// for
		
		// 업로드 폴더 경로
		String uploadFolder = "C:\\upload";
		
		/* 날짜 폴더 경로 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		// '오늘의 날짜' 데이터를 얻기 위해서 변수 선언 및 초기화
		Date date = new Date(); 
		
		// date 변수를 "yyyy-MM-dd" 형식의 문자열로 변환을 해주기 위해 format 호출 
		String str = sdf.format(date); // (문자열) str = "yyyy-MM-dd" (date = 오늘 날짜)
		
		// 2024-01-15 -> '/'(경로 구분자)로 변경해주어야 한다. replace 메서드 사용.
		String datePath = str.replace("-", File.separator);
		
		/* 폴더 생성 */
		File uploadPath = new File(uploadFolder, datePath); // (업로드 폴더, 업로드 날짜)
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs(); // 한개 mkdir() or 여러개 mkdirs()
		}
		
		/* 이미지 정보 담는 객체 */
		List<AttachImageVO> list = new ArrayList();
		
		/* 향상된 for */
		for(MultipartFile multipartFile : uploadFile) {
			
			/* 이미지 정보 객체 */
			AttachImageVO vo = new AttachImageVO();
			
			/* 파일 이름 */
			String uploadFileName = multipartFile.getOriginalFilename(); // view로부터 전달받은 파일 이름을 그대로 사용위해 Origin() 사용.
			vo.setFileName(uploadFileName);
			vo.setUploadPath(datePath);
			
			/* uuid 적용 파일 이름 */
			String uuid = UUID.randomUUID().toString(); // uuid 타입이기 때문에 toString()을 사용하여 String 타입으로 변환.
			vo.setUuid(uuid);
			
			/* UUID_파일이름 형식 */
			uploadFileName = uuid + "_" + uploadFileName;
			
			/* 파일 위치, 파일 이름을 합친 File 객체 */
			File saveFile = new File(uploadPath, uploadFileName); // (파일 위치, 파일 이름)
			
			/* 파일 저장 */
			try { // IOException, IllegalStateException 예방
				
				// view로부터 전달받은 파일을 지정한 폴더에 저장위해 transferTo() 사용.
				multipartFile.transferTo(saveFile); 
				
				/* 썸네일 생성 (ImageIO) */
				/* 방법 1
				// ImageIO를 통해 썸네일을 만들기 위해선 원본 'File 객체'와 '썸네일 이미지 파일'의 File 객체가 필요.
				File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
				
				// BufferedImage 타입으로 변경. (buffered original image)
				BufferedImage bo_image = ImageIO.read(saveFile);
				
					// 비율 
					double ratio = 3;
				
					// 넓이 높이
					int width = (int) (bo_image.getWidth() / ratio); // 파라미터 부여해야 할 넓이, 높이 값은 int형 -> 형변환
					int height = (int) (bo_image.getHeight() / ratio);
				
				// resize 된 이미지 객체 생성 및 선언. (넓이, 높이, 생성될 이미지의 타입)
				BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
				
				// bt_image에 그래픽을 그리기 위한 객체 생성.
				Graphics2D graphic = bt_image.createGraphics();
				
				// 그래픽 적용된 이미지 resize (그려놓고자 하는 이미지, x좌표, y좌표, 넓이, 높이, ImageObserver)
				graphic.drawImage(bo_image, 0, 0, width, height, null);
				
				// 제작한 썸네일 이미지(bt_image)를 파일로 만들어 준다. (파일로 저장할 이미지, 저장할 타입, 저장될 경로와 이름으로 생성한 File 객체).
				ImageIO.write(bt_image, "jpg", thumbnailFile);
				*/
				
				/* 방법 2 */
				File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
				
				BufferedImage bo_image = ImageIO.read(saveFile);
				
					//비율 
					double ratio = 3;
					
					//넓이 높이
					int width = (int) (bo_image.getWidth() / ratio);
					int height = (int) (bo_image.getHeight() / ratio);
				
				// thumbnailaotr라이브러리는 ImageIO를 통한 코드 작성 보다 훨씬 간단히 생성.
				Thumbnails.of(saveFile)
				.size(160, 160)
				.toFile(thumbnailFile);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 이미지 정보가 저장된 AttachImageVO 객체를 List의 요소로 추가 
			list.add(vo);
			
		} // for
		
		// Http Body에 추가될 데이터는 List<AttachImageVO>이고, 상태 코드 OK(200) ResponseEntity 객체 반환.
		ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list, HttpStatus.OK);
		
		return result;
	}
	
	/* 이미지 파일 삭제 */
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName) { // 파일 이름/경로 전달받기 위해 String type, fileName 파라미터
		
		log.info("deleteFile.........." + fileName);
		
		File file = null;
		
		try { // fileName = thumbnailFile, decode는 url에서 문자(%20, %2F) 방지.
			
			/* 썸네일 파일 삭제 */ 
			file  = new File("c:\\upload\\" + URLDecoder.decode(fileName,"UTF-8"));
			
			file.delete();
			
			/* 원본 파일 삭제 */
			String originFileName = file.getAbsolutePath().replace("s_", "");
			
			// String 클래스의 replace()는 첫 번째 인자를 두 번째 인자 문자열로 치환.
			
			log.info("originFileName : " + originFileName);
			
			file = new File(originFileName);
			
			file.delete();
		
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
		} // catch
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	/* 비동기방식 로그아웃 메서드 */
	@PostMapping(value="logout.do")
	@ResponseBody
	public void logoutPOST(HttpServletRequest request) throws Exception {
		
		// 반환 타입은 String으로 하면 되지만 메서드의 작업만 수행만 요구하기에 void로 반환.
		log.info("비동기 로그아웃 메서드 진입");
		
		/*
		 * 사용자가 로그인하면 그 사용자의 정보(예: 아이디, 권한 등)를 
		 * 세션에 저장하여 세션을 통해 사용자를 식별하거나 사용자 관련 정보를 얻을 수 있습니다. 
		 */
		
		HttpSession session = request.getSession();
		
		session.invalidate();
	}
}
