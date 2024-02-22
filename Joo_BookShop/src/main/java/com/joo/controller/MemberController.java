package com.joo.controller;

import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joo.model.MemberVO;
import com.joo.service.MemberService;

@Controller
@RequestMapping(value = "/member") // 회원 관련 호출은 명확하게 구분 짓기 위해
public class MemberController {

	private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberservice;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;

	/* 회원가입 페이지 이동 */
	@GetMapping(value = "/join")
	public void joinGet() {

		log.info("회원가입 페이지 진입");
	}

	/* 회원가입 페이지 */
	@PostMapping(value = "/join")
	public String joinPOST(@RequestBody MemberVO member) throws Exception {

		String rawPw = "";    // 인코딩 전 비밀번호
		String encodePw = ""; // 인코딩 후 비밀번호
		
		rawPw = member.getMemberPw(); 			// 비밀번호 가져오기
		encodePw = pwEncoder.encode(rawPw);		// 비밀번홍 인코딩
		member.setMemberPw(encodePw);			// 인코딩된 비밀번호 member 객체에 다시 저장

		// 회원가입 쿼리 실행
		memberservice.memberJoin(member);

		return "redirect:/main";
	}

	/*
	 * 회원가입 try ~ catch 사용시
	 * 
	 * @PostMapping joinPOST(MemberVO member) { try { log.info("join 진입");
	 * 
	 * // 회원가입 서비스 실행 memebrservice.memberjoin(member);
	 * 
	 * log.info("join Service 성공!"); } catch (Exception e) {
	 * log.info("회원가입 중 에러 발생: " + e.getMessage(), e); // 예외 처리 로직 추가 // 예를 들어, 에러
	 * 페이지로 리다이렉트 or 에러 메시지를 표시하는 등의 작업 수행 return "errorPage"; } }
	 */

	/* 로그인 페이지 이동 */
	@GetMapping(value = "/login")
	public void loginGet() {

		log.info("로그인 페이지 진입");
	}
	
	/* 로그인 */
	@PostMapping(value="login.do")
	public String loignPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception {
	
		
		/*
		 * lvo = 아이디, 비밀번호가 일치한 회원정보가 MemberVO 객체에 저장.
		 * 그 반대로, 존재하지 않을 경우 lvo 값은 null이 저장된다. 
		 */
		HttpSession session = request.getSession();
		String rawPw = "";
		String encodePw = "";
		
		MemberVO lvo = memberservice.memberLogin(member);
		
		/*
		if(lvo == null) {	// 일치하지 않는 아이디, 비밀번호 입력 경우.
			int result = 0; // 0 = false, 1 = true.
			rttr.addFlashAttribute("result", result);
			return "redirect:/member/login";
		}
		*/
		
		if(lvo != null) {	// 일치하는 아이디 존재시, (로그인 성공)
		
			rawPw = member.getMemberPw(); // 사용자가 제출한 비밀번호
			encodePw = lvo.getMemberPw(); // DB에 저장한 인코딩된 비밀번호
			
			if(true == pwEncoder.matches(rawPw, encodePw)) { // 비밀번호 일치여부 판단
				
				lvo.setMemberPw("");	  			// 인코딩된 비밀번호 정보 지움
				session.setAttribute("member",lvo); // session에 사용자의 정보 저장
				return "redirect:/main";			// 메인 페이지 이동
				
			} else {
				
				rttr.addFlashAttribute("result", 0);
				return "redirect:/member/login"; 	// 로그인 페이지로 이동
			}
		
				
		} else {			// 일치하는 아이디가 존재하지 않을 시, (로그인 실패)			
		
			rttr.addFlashAttribute("result", 0); 
			return "redirect:/member/login"; 		// 로그인 페이지로 이동
		}
		
	}
	
	/* 메인페이지 로그아웃 */
	@GetMapping(value="logout.do")
	public String logoutMainGET(HttpServletRequest request) throws Exception {
		
		log.info("logoutMainGET 메서드 진입");
		
		HttpSession session = request.getSession();

		// invalidate() : session 전체 무효, removeAttribute() : 특정 이름을 session 객체를 타겟팅하여 삭제.
		session.invalidate();
		
		return "redirect:/main";
	}

	/* 아이디 중복 검사 */
	@RequestMapping(value = "/memberIdChk", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception {

		log.info("memberIdChk() 진입");

		int result = memberservice.idCheck(memberId);

		log.info("결과값 = " + result);

		if (result != 0) {
			return "fail"; // 중복 아이디가 존재
		} else {
			return "success"; // 중복 아이디 존재x
		}
	}

	/* 이메일 인증 */
	@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public String mailCheckGET(String email) throws Exception {

		/* view 로 부터 넘어온 데이터 확인 */
		log.info("이메일 데이터 전송 확인");
		log.info("인증번호 : " + email);

		/* 인증번호(난수) 생성 */
		Random random = new Random();
		// 111111 ~ 999999 범위 숫자 위해, nextInt() 메서드는 0(포함).
		int checkNum = random.nextInt(888888) + 111111;
		log.info("인증번호" + checkNum);
		
		/* 이메일 보내기 */
		String setFrom = "hddong728@naver.com";
		String toMail = email; // view로부터 받은 이메일 주소인 변수 email 사용.
		String title = "회원가입 인증 이메일 입니다.";
		String content = 
				"홈페이지를 방문해주셔서 감사합니다." +
			    "<br><br>" +
				"인증 번호는 " + checkNum + "입니다." +
			    "<br>" +
				"해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
		
		try {
			
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true);
			mailSender.send(message);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// int checkNum 으로 생성, ajax를 통한 view 반환시에는 String
		// 대부눈 int로 설정한 변수를 ajax를 통해 전송, 대부분 문자열러 변환하여 전송
		// ajax에서 dataType : text 설정하면 'parseInt()' 함수 사용. 
		String num = Integer.toString(checkNum);
	
		return num;
	}
}
