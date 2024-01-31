※ shoppingmall index ※

0.프로젝트 base
(STS3.99, Java 11, Apache 8.5, Oracle & MySQL, MyBatis)

1. 데이터베이스 테이블 생성 및 VO 생성
   1) Oracle
   2) MySQL
   3) VO생성

2. MemberMapper.xml 추가
   1) insert 쿼리문 테스트 및 관리자 아이디 만들기
   2) mapper.xml 작성

3. MemberMapper.java 인터페이스 추가

4. Mapper에 추가한 쿼리문 테스트(MemberMapperTests.java)

5. MemberService.java 추가

6. MemberController.java 회원가입 메서드 추가<br/>
	ㄴ사용자가 작성한 회원가입 정보를 MemberVO 클라스 타입의 member를 통해 데이터 받아옴.<br/>
	ㄴ회원의 정보가 담긴 member를 매개변수로 한 memberJoin() 메서드를 호출.<br/>
	ㄴ호출된 메서드를 통해 BOOK_MEMBER 테이블에 데이터를 INSERT 쿼리문 실행.<br/>

7. 아이디 중복검사 메서드 작성 및 테스트(Javascript)

8. ajax success 작성

9. email 전송, view(회원가입 페이지) 입력한 email 주소 Controller 전송<br/>
ㄴ데이터가 view에서 null로 넘어오면 controller에서 log를 찍거나 debug 해보기.

10. SMTP Server 메일 pom.xml/root-context(mailSender Bean) 등록.<br/>
	ㄴ1)인증번호 (난수, 메일 전송)<br/>
	ㄴ2)인증번호 입력란 변환, 일치여부 검사<br/><br/>

	-인증번호-<br/>
	대부눈 int로 설정한 변수를 ajax를 통해 전송, 대부분 문자열러 변환하여 전송<br/>
	ajax에서 dataType : text 설정하면 'parseInt()' 함수 사용.

11. 주소 API 검색 -> 검색된 주소가 회원가입 주소란에 입력되는 것을 목표.<br/>
	우편번호, 주소 정보 해당 필드에 맞게 코드 수정하기(JQuery version)

12. 유효성 검사(입력란 미입력 방지)<br/>
	아이디, 비밀번호, 이름, 메일/인증번호, 주소 , 최종 유효성 검사 및 전송, 테스트

13. 로그인 (DB 요청하여 ID, PW 일치 여부 확인)<br/>
	ㄴ쿼리문은 제출받은 로그인, 비밀번호와 비교하여 일치하면 해당 아이디의 정보를 반환,<br/>
	ㄴ없을 시 null을 반환하도록 할 것.<br/><br/>

	ㄴMemberVO(데이터 전달),<br/> 
	ㄴHttpServletRequest(로그인 성공시, session에 회원 정보 저장),<br/>
		  ㄴ로그인이 되었다는 것은 session에 사용자의 정보를 저장하였다는 의미.<br/>
	ㄴRedirectAttribute(로그인 실패 시, 실패 정보 전달).<br/><br/>

	ㄴ로그인 성공 시, 회원가입/로그인 버튼 사라짐, 회원의 정보만 노출.<br/>
	ㄴBCryptPasswordEncoder(비밀번호 된 인코딩, DB에 저장된 인코딩 된 비밀번호 일치여부)<br/>
	ㄴmember 객체에 저장된 비밀번호 꺼냄, encoder() 메서드를 사용하여 인코딩.<br/>
	ㄴ인코딩을 된 비밀번호를 member 객체에 다시 저장, memberJoin() 메서드 실행, 쿼리 실행<br/>
14. 로그아웃 

15. gnb_area<br/> 
	로그인 (<c:if> 태그를 통해 구분)<br/>
	ㄴ1)로그인하지 않은 상태('로그인', '회원가입', '고객센터'),<br/>
	ㄴ2)로그인한 상태('로그아웃', '마이 페이지', '장바구니'),<br/> 
	ㄴ3)관리자 계정 로그인한 상태<br/><br/> 
	
	로그아웃 (세션 제거 요청 방식을 비동기 방식으로 구현)<br/>
	ㄴ버튼 클릭 -> javascript 메서드 -> ajax 로그아웃 요청 -> server 로그아웃(화면 이동없이)<br/><br/>

16. 인터셉터<br/>
	ㄴ일반 계정 or 로그인 하지 않은 계정이 관리자 페이지 접속 막기.<br/>
		  ㄴ접속 메서드에 세션 체크를 통해 권한이 없는 관리자가 접근 시 메서드 실행되지 않게.<br/>
			  ㄴ문제는 AdminController 모든 메서드에 적용해야 됨, 이를 interceptor해결.<br/>

17. 작가 테이블/ 관리자 페이지/ footer 생성<br/>

18. 작가 등록  (server)<br/>
	ㄴ1)domain layer : AuthorVO 생성<br/>
	ㄴ2)persistance layer : AuthorMapper.xml/ AuthorMapper.java 생성<br/>
	     ㄴxml <insert> 태그의 id 속성 값은 AuthorMapper.java 작성한 등록 메서드명과 동일<br/>
	ㄴ3)business Logic layer : AuthorService.java / AuthorServiceImpl.java 생성<br/>
	ㄴ4)presentation layer : AuthorController.java 생성<br/><br/>

    작가 등록 (client)<br/>
	ㄴAuthorEnroll.jsp  <form action="/admin/authorEnroll.do" method="post"id="enroll"><br/>
	ㄴ작가 등록에 사용할 데이터 AuthorVO 객체를 파라미터로 전달 받기 때문에, <br/>
	ㄴVO클래스 변수 이름과 .jsp의 input, select 태그의 name/id 속성 값이 일치하도록 작성.<br/>
		  ㄴxml query 문 변수와 DB Column명과 일치하는것과 같은 맥락.<br/><br/>

19. 작가 목록 <br/>
	ㄴincludes - header/footer 구현<br/>
	ㄴpaging - criteria/pageMaker 구현<br/>
	ㄴ검색 인터페이스 구현 & 검색 결과 없을 시 view 처리 구현<br/>
20. 작가 상세 <br/>
	ㄴ목록 페이지와 마찬가지로 Criteria, pageMaker 구현<br/>
	ㄴ작가 목록 페이지에서 authorId 클릭시, 작가 상세 페이지로 이동, 이동 후, 목록/수정 버튼 구현<br/>
21. 작가 수정 <br/>
	ㄴMapper, Service, Controller 메서드 생성하지 않고, url mapping authorGetInfoGET()활용<br/>
	ㄴ@GetMapping({"/authorDetail", "/authorModify"}) 복수 url 구현<br/>
22. 상품 등록<br/>
	※ table ※<br/>
	ㄴauthorId, cateCode, joo_author table에 있는 데이터만 외래 키를 지정.<br/>
	ㄴ외래 키 등록은 작업의 편의를 위해서 특정 기능을 '상품 등록' 페이지에 적용한 뒤에 등록<br/><br/>

	※ CateVO ※<br/>
	ㄴ컬럼 할인율(bookDiscount decimal(2,2))소수 둘째 자리까지 허용한 고정 소수점 타입으로 지정.<br/>
	ㄴ컬럼 책소개(bookContents) 경우 위지윅 활용을 위해 text type 사용.<br/><br/>
	
	※ category ※<br/>
	ㄴ카테고리를 <select>/<option>설정할 수도 있지만, List 통째로 view에 전송/JSON타입으로전송<br/>
	ㄴ이번에는 JSON타입으로 변환(Jackson)하여 View로 넘기고, javascript 통해 가공하여 출력.<br/>
	ㄴ카테고리 코드 규칙, 책 상품을 3가지 단계로 분류(1단계, 2단계, 3단계) tier은 해당 단계 확인.<br/><br/>

	※ 팝업창 구현 ※<br/>
	ㄴwysiwyg editor/datepicker CDN 방식 적용.<br/>
		  ㄴdatepicker 'autocomplete(<input>클릭시 이전 데이터 방지)' 'readonly'속성 추가<br/>
	ㄴ작가 팝업창 띄우기/작가 검색 후 선택할 인터페이스 구현/선택한 데이터goodsEnroll에 전달<br/><br/>

	※ 카테고리 리스트 구현 ※<br/>
	ㄴ카테고리 리스트의 데이터를 JSON형식으로 SERVER -> VIEW 전송.<br/>
	ㄴJSON형식의 String 데이터를 활용하여 <select>, <option> 태그 구현.<br/>
	ㄴcateList 객체에는 '대,중,소'가 섞여 있는 상태, 이 객체를 tier에 맞게 분류하여 배열에 저장.<br/>
	ㄴ반복된 코드 메서드화 하여, 배열 초기화 후, 대/중/소분류 <option> 태그 출력.<br/><br/>
	
	※ 유효성 검사 ※<br/>
	ㄴ변수를 두개로 나누어 구현하여 입력 값, 체크 변수를 통해 true/false를 이용하여 display 적용.<br/><br/>

	※ 할인율 입력란 인터페이스 & 외래키 & 유효성 검사 설정 ※<br/>
	ㄴ사용자는 할인률 입력란 정수 입력, 서버에 전송은 해당 값을 소수로 변경 되도록,<br/>
		  ㄴ할인율 입력란에 문자를 입력 못하도록 유효성 체크  <br/>
	ㄴ사용자가 제품 가격과 할인율을 입력하였을 때 할인된 가격 볼 수 있도록 구현.<br/>

	※ 상품 목록 페이지 구현 ※<br/>
	ㄴgoodsManage(상품 목록)페이지에 출력 시킬 column 은 <br/>
		  'bookId', 'bookName', 'authorName', 'cateName', 'bookPrice', 'bookStock'<br/>
		  	문제는 'bookName', 'cateName' 열의 정보는 'joo_book' 테이블에 없다.<br/>
	ㄴ따라서 같이 출력 되도록 'JOIN' 사용<br/><br/>

	※ 상품 조회 페이지 구현 ※<br/>
	ㄴ조회 페이지에는 상품의 이름 클릭시, 조회 페이지 이동과 함께, 선택한 상품에 관한 등록된 정보 출력.<br/>
	ㄴ'상품ID', '상품 등록 날짜', '상품 수정 날짜'가 출력되도록 코드를 추가.<br/>
	ㄴ각 <input>, <select> 태그에 사용자가 입력을 할 수 없도록 disabled 속성을 추가.<br/>
	ㄴ각 <input>, <textarea> 태그에 서버로부터 전달받은 상품 정보가 출력되도록 코드 추가.<br/><br/>

	※ 카테고리 리스트 데이터 ※<br/><br/>

23. 상품 수정<br/>
	※ 상품 수정 페이지 이동 ※<br/>
	ㄴ수정 페이지로 이동할 수 있는 인터페이스인 버튼과 페이지 이동에 사용될 URL 매핑 메서드를 작성.<br/>
	ㄴ <form> 태그 내부에 'bookId' 항목의 type이 hidden인 <input> 태그를 추가하였습니다. (수정을 수행하는 쿼리에서는 bookId가 필요로 하기 때문에 추가하였습니다.)<br/>
	ㄴ해당 <form> 태그는 수정 페이지에서 조회 페이지 이동할 때, <br/>
		  조회 페이지에서 목록 페이지로 이동을 할 때 필요로 한 데이터 들입니다. )<br/><br/>

23. 상품/작가 정보 삭제 <br/>

24. 상품 이미지 업로드<br/>
	※ 상품 이미지를 업로드하고 등록한 이미지의 정보를 DB에 저장 ※<br/><br/>

	방법1 : 이미지 업로드 저장<br/>
		  ㄴ사용자가 이미지를 선택하였을때 이미지가 업로드(컴퓨터에 저장) 되고,<br/>
		     최종적으로 다른 상품 정보들이 작성된 뒤 '등록 버튼'을 눌렀을 때, <br/>
		    '업로드 된' 이미지의 정보가 DB에 저장되도록 하는 방식<br/>
	방법2 : 등록 이미지 정보 DB 저장<br/>
		  ㄴ사용자가 이미지를 선택을 하고 다른 상품 정보를 다 작성한 뒤,<br/>
		     '등록 버튼'을 눌렀을 때 이미지를 업로드하고, 업로드 이미지 정보를 DB 저장.<br/><br/>

	-> 방법1 진행 <br/>
		1.사용자가 이미지 선택,<br/>
		2.선택된 이미지 서버로 전송,<br/>
		3.전송 받은 이미지 저장 후, 저장 한 이미지 정보 VIEW로 재전송,<br/>
		4.전송 받은 데이터를 활용하여 이미지 미리보기,<br/>
		5.<input> 태그에 데이터 저장,<br/>
		6.'등록 버튼' 클릭,<br/>
		7.이미지 정보 DB 저장<br/>

		(첨부파일 처리 servlet3.0 / 페이지 이동 없이, AJAX 사용)<br/><br/>

	※ 파일 체크(Javascript) ※<br/>
	ㄴview 단계에서 사용자가 선택 한 파일이 개발자가 허용한 파일이 아닐 경우 경고창<br/>
		  ㄴ허용한 파일(jpg, png) / 파일의 크기는 (1MB)<br/><br/>

	※ 파일 객체 서버 전송 ※<br/>
	ㄴ이번 프로젝트는 화면의 이동 없이 첨부파일을 서버로 전송, FormData 객체 생성 후 저장하고 서버전송<br/><br/>

	※ 업로드한 이미지 저장될 폴더 생성 ※<br/>
	ㄴ업로드 하는 날짜에 맞게 폴더 생성, 생성된 폴더에 업로드 파일 저장. (uploadPath.mkdirs() 사용.)<br/>
	ㄴAdminController/uploadAjaxActionPOST 에 파일을 저장할 기본적 경로 String 타입의 변수 선언.<br/>
	ㄴ'yyyy/MM/dd' 형식의 String 데이터를 얻기 위해 SimpleDateFormat 클래스와 Date 클래스 사용.<br/>
		  ㄴSimpleD은 Date 클래스를 통해 얻은 날짜 -> 문자열 형식의 데이터로 변환하기 위해서 사용.<br/>
	ㄴ고유한 파일들을 관리하기 위해 저장 파일에 UUID 추가.<br/><br/>

	※ 썸네일 이미지 생성 및 저장 ※<br/>
	ㄴ이미지 호출 시, 썸네일 이미지를 호출 할 수 있도록 원본 이미지를 통해 썸네일 이미지를 생성하고 저장.<br/>
		    ㄴjava 클래스에서 제공하는, ImageIO, Thumbnailator 사용<br/>
			    ㄴ저장될 썸네일 이미지 경우 "s_" + "uuid_" + "원본파일 이름. 이미지 타입" 형식으로 저장.<br/>
				    ㄴMultipartFile/BufferedImage/ImageIO/Graphics2D 클래스 사용하여 이미지 파일 저장.<br/>
					    ㄴImageIO 대신 thumbnailaotr라이브러리 사용하여 코드 간결화.<br/><br/>
	
	※ 이미지 정보 view 반환 ※<br/>
	1.왜 이미지 정보를 view로 반환하려 하는가? <br/>
		    ㄴ업로드한 이미지 정보를 DB에 저장하기 위해 & 업로드한 이미지를 사용자가 미리 볼 수 있도록.<br/>
			    ㄴ'경로', 'uuid', '파일 이름' 3가지 데이터를 DB에 보관, view로 전송,<br/>
				    ㄴview 전송은 Command Object 객체를 view에 전송. (AttachImageVO 객체 생성)<br/>
	2.어떠한 정보를 보낼 것인가?<br/>
		    ㄴDB 저장 -> 이미지 화면 출력 -> 이미지 저장 경로/파일 이름 필요.<br/>
			    ㄴC:\upload + '유동적 경로' + 's_' + 'uuid' + '_' + '원본 파일 이름'<br/>
	3.어떠한 방식으로 view에 데이터를 전송할 것인가?<br/>
		    ㄴ화면의 이동 없이 서버와 뷰가 정보를 주고 받는 비동기 방식 사용.(@ResponseBody/ResponseEntity )<br/>
	4.여러 개의 이미지 파일 전달을 위해 ArrayList 활용, for문에서  VO 정보를 저장한 다음 해당 객체, ArrayList 요소에 저장.<br/><br/>

	※ 서버 단계 이미지 파일 체크 & view 에러 반환 ※<br/>
	ㄴview에서는 이미 파일체크를 하도록 코드를 작성 하지만 파일체크 코드가 작동 안 할시 차선책 구현.<br/>
		    ㄴ업로드를 수행하는 url 매핑 메서드에도 전달 받은 파일 체크 하는 코드를 추가.<br/>
			    ㄴ파일 체크 시, 아닐 경우 에러 상태 코드를 전송하여, view에서 경고창이 출력.<br/>
				    ㄴ파일 체크를 위해 MIME TYPE 속성 활용, probeContentType() 반환 메서드 사용.<br/>
				    ㄴMIME TYPE 타입이 이미지일 경우, 첫 단어가 image로 시작, 이 방법으로 판단.<br/>
					    ㄴview에서는 ajax의 error 속성을 추가하여 상태코드 400시, 콜백함수를 사용.<br/><br/>
	
	※ 업로드 이미지 출력 구현 ※<br/>	
	1.url 매핑 메서드 반환 & ContentType 명시<br/>
		 ㄴ비동기 방식 url을 호출하면 이미지를 반환해주는 url 매핑 메서드를 구현(미리보기 이미지 구현위해)<br/>
		     @ResponseBody & @ResponseEntity 중, body에 데이터를 첨부한다는 동일,<br/>
		     하지만 @ResponseEntity를 사용하여 header의 'ContentType' 이미지 파일임을 명확하게 설정,<br/>
		 ㄴurl 메서드 '파일경로' + '파일 이름' 파람으로 받고, 해당 데이터에 맞는 이미지파일 view에 전송.<br/>
			  ㄴ이미지 파일을 주고받기 위해서는 byte 배열 타입 사용.<br/>
			    (이미지 파일은 binary(0과 1로만 구성, text 제외 한 모든 파일 저장) 파일 해당,)<br/>
	2.file 객체 생성<br/>
		 ㄴ현재 관리자 관련 기능들을 구현주잉여서 AdminController에 작업을 진행하려 했으나,<br/>
		    관리자가 아닐 시, Interceptor 필터를 거쳐야 하기 때문에 접근에 제한.<br/>
		    이미지는 비로그인이여도 접근이 가능해야 하기 때문에, 상품 BookController.java에 작성.<br/><br/>
	
	정리 -> 파라미터 전달받은 '파일 경로', '파일 이름' 활용하여 File 객체 생성,<br/>
		     MIME TYPE에 대한 정보를 얻어, ResponseEntity img 데이터를 복사하여 body에 추가,<br/>
		     header의 'Content Type'에서 얻은 정보를 MIME TYPE으로 수정 후, Entity 객체를 view로 전송.<br/><br/>

	고정 경로 http://localhost:8080/display?fileName=test.png<br/>
	유동 경로 http://localhost:8080/display?fileName=2024/01/21/test.png<br/><br/>

	※ 업로드 이미지 선택 시, 이미지 미리 보기 ※ <br/>
	ㄴ업로드 이미지 요청 데이터(path, filename, uuid)를 받았을 때, ajax success 콜백 함수 이미지 데이터 출력.<br/>
		 ㄴ코드 양이 길어서 메서드를 선언하여, 구현부에 이미지를 출력하는 코드 작성하여 함수 호출.<br/>
			 ㄴ메서드를 미리 호출하고 인자 값으로 서버로 전달받은 result(path, filename, uuid) 부여.

	※ 이미지 삭제 기능 구현 ※ <br/>
	1.File 클래스의 delete() 메서드 사용.<br/>
	ㄴFile 클래스 인스턴스화.<br/>
	ㄴ생성자에 대상 파일의 경로인 문자열 데이터를 파라미터로 부여, <br/>
		 ㄴ삭제할 대상의 파일의 경로가 필요, url 매핑 메서드의 파라미터로 fileName 부여.<br/><br/>
	
	2.썸네일 파일 삭제 (미리보기 이미지의 우측 상단의 x표시 클릭 시, 삭제)<br/>
	ㄴencodeURIComponent() 메서드를 통해 UTF-8로 인코딩 된(썸네일 이미지 파일) 데이터 전송.<br/>
	ㄴ문자열 데이터는 "/" or "\" 구분하는데, UTF-8로 인코딩 되었기 때문에, 데이터 자체만으로는 삭제 불가.<br/>
		 ㄴ따라서 fileName에 담긴 데이터를 Decoding 해서 해당 데이터 삭제.<br/><br/>

	3.삭제 버튼 태그 data 속성 삽입<br/>
	ㄴimgDeleteBtn <div> 태그에, 해당 파일 경로 데이터를 심어 놓기.<br/>
		 ㄴdata-file 속성을 추가하고, UTF-8 인코딩해둔 변수(view에서 전달 해준 파일 decode)를 사용.<br/><br/>
	
	※ 미리보기 이미지 제거 및 서버에 이미지 파일 삭제 ※ <br/>
	삭제 메서드 기능이 적용되어야 할 상황이 두 가지.<br/>
	1.미리 보기 이미지의 'x'를 클릭 시, 삭제.<br/>
	2.이미지가 등록될 때 파일이 이미 존재를 한다면  삭제를 처리한 후 서버에 이미지 업로드 요청.<br/>
	ㄴ두 가지의 파일이 저장되게 되고, 미리보기 이미지도 두 가지 출력, 사용자가 이미지를 선택 시, 파일삭제<br/><br/>

	메서드 진행 순서<br/>
	1.서버에 파일 삭제 요청<br/>
	2.서버로부터 응답에 따른 처리<br/>
	3.서버로부터 응답에 따른 처리 성공 시, 미리 보기 태그를 지우는 작업 / 실패 시, 경고창 띄우기.<br/><br/>

	★★★★★ 업로드 이미지 정보 등록 ★★★★★<br/>
	- view에서는 상품 하나에 하나의 이미지만 추가 하지만, 서버에서는 여러 개의 이미지 처리도록 코드 구성.<br/>
		 따라서 이미지 정보를 따로 관리 할 수 있도록, 새로운 테이블 생성, bookId라는 컬럼을 추가하여,<br/>
		 해당 컬럼은 오직,joo_book 테이블의 bookId 에 존재하는 데이터만 등록 가능 하도록 외래키 설정.<br/><br/>

	1.서버에 등록<br/>
		ㄴ상품 이미지에 대한 정보를 BookVO 객체에 포함시켜서 전달 하도록 VO클래스에 변수 선언.<br/>
			 ㄴ서버에서는 여러 개의 이미지 등록 가능하도록 List 구조의 AttachImageVO 타입 변수 선언.<br/>
	2.view에 등록<br/> 
		ㄴ<input>태그 추가하기 앞서, 주의점 세 가지<br/>
			 1.javascript를 통해 동적으로 추가, 이미지가 등록되었을 때, 서버에 이미지 정보 전달.<br/>
			 2.<input> 태그 추가 시점, 두 가지 시점<br/>
				 ㄴ1)이미지 등록 후 미리보기 태그 추가될 때(ajax의 success 속성 동작 시점).<br/>
				 ㄴ2)상품 등록 페이지 버튼을 눌렀을 때, 서버에 데이터가 전송되기 전.<br/>
					 ㄴ여러 개의 이미지를 전달할 시에 '상품 등록 버튼을 눌렀을 때', <input> 태그를<br/>
						 추가하는 것이 용이하다.<br/>
					 ㄴindex를 활용하여 name 속성 값을 작성해야하기 때문이다.<br/>
					 ㄴ'상품등록 버튼을 누른다는 것은 등록할 이미지가 확정이 된 상태이기 때문에<br/>
						 등록된 이미지 개수만큼 for문을 통해 index를 지정해주면 된다.<br/>
			 3.<input> 태그의 어떤  name 속성 값 선언 할 것인지.<br/>
				 ㄴ기존 name 속성 값들은 BookVO의 멤버 변수명을 그대로 작성하면 되었다.<br/>
					 하지만, 이미지 정보의 경우 객체의 주소 값이 참조 변수이어서, java에서 특정<br/>
					 클래스의 멤버 변수에 접근할 때 "참조변수.멤버변수" 로 작성 해주어야 한다.<br/><br/>

				 ex) BookVO에 'private AttahImageVO imageVO; 변수를 선언, fileName을 전달 위해서는<br/>
					 <input> 태그의 name 속성 값 "imageVO.fileName"으로 작성 해주어야 한다.<br/><br/>

		ㄴgoodsEnroll.jsp, 즉 view 단계에서는 하나의 이미지 파일만 등록할 수 있도록 구현하고 있기 때문에,<br/>
			 이미지가 등록된 시점에 이미지 정보 <input> 태그가 추가 되도록 작업.