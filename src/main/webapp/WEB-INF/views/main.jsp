<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome BookMall</title>
<link rel="stylesheet" href="resources/css/main.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
<body>

	<div class="wrapper">
		<div class="wrap">
			<div class="top_gnb_area">
				<ul class="list">
					<c:if test="${member == null}">
						<!-- 로그인 x-->
						<li><a href="/member/login">로그인</a></li>
						<li><a href="/member/join">회원가입</a></li>
					</c:if>
					<c:if test="${member != null }">
						<!-- 로그인 o -->
						<c:if test="${member.adminCk == 1}">
							<!-- 관리자 계정 -->
							<li><a href="/admin/main">관리자 페이지</a></li>
						</c:if>
						<li><a id="gnb_logout_button">로그아웃</a></li>
						<li>마이페이지</li>
						<li><a href="/cart/${member.memberId}">장바구니</a></li>
						<li>고객센터</li>
					</c:if>
				</ul>
			</div>
			<div class="top_area">
				<!-- 로고 영역 -->
				<div class="logo_area">
					<a href="/main"><img src="resources/img/HLogo.png"></a>
				</div>
				<div class="search_area">
					<div class="search_wrap">
						<form id="searchForm" action="/search" method="get">
							<div class="search_input">
								<select name="type">
	               					<option value="T">책 제목</option>
	               					<option value="A">작가</option>
	               				</select>
								<input type="text" name="keyword">
								<button class="btn search_btn">검 색</button>
							</div>
						</form>
					</div>
				</div>
				<div class="login_area">

					<!-- 로그인 하지 않은 상태 -->
					<c:if test="${member == null }">
						<div class="login_button">
							<a href="/member/login">로그인</a>
						</div>
						<span> <a href="/member/join">회원가입</a></span>
					</c:if>

					<!-- 로그인한 상태 -->
					<c:if test="${member != null}">
						<div class="login_success_area">
							<span>회원 : ${member.memberName}</span> <span>충전금액 :
								${member.money}</span> <span>포인트 : ${member.point}</span> <a
								href="member/logout.do">로그아웃</a>
						</div>
					</c:if>
					
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="navi_bar_area">
				<div class="dropdown">
				    <button class="dropbtn">국내 
				      <i class="fa fa-caret-down"></i>
				    </button>
				    <div class="dropdown-content">
				    	<c:forEach items="${cate1}" var="cate">
				    		<a href="search?type=C&cateCode=${cate.cateCode}">${cate.cateName}</a>
				    	</c:forEach>		      		      		      
				    </div>			
				</div>
				<div class="dropdown">
				    <button class="dropbtn">국외 
				      <i class="fa fa-caret-down"></i>
				    </button>
				    <div class="dropdown-content">
				    	<c:forEach items="${cate2}" var="cate">
				    		<a href="search?type=C&cateCode=${cate.cateCode}">${cate.cateName}</a>
				    	</c:forEach>      		      		      
				    </div>			
				</div>
			</div>
			<div class="content_area">
				<h1>content area</h1>
			</div>
			<!-- Footer 영역 -->
			<div class="footer_nav">
				<div class="footer_nav_container">
					<ul>
						<li>회사소개</li>
						<span class="line">|</span>
						<li>이용약관</li>
						<span class="line">|</span>
						<li>고객센터</li>
						<span class="line">|</span>
						<li>광고문의</li>
						<span class="line">|</span>
						<li>채용정보</li>
						<span class="line">|</span>
					</ul>
				</div>
			</div>
			<!-- class="footer_nav" -->

			<div class="footer">
				<div class="footer_container">

					<div class="footer_left">
						<img src="resources/img/FLogo.png">
					</div>
					<div class="footer_right">
						(주) JooBook 대표이사 : OOO <br> 사업자등록번호 : ooo-oo-ooooo <br>
						대표전화 : oooo-oooo(발신자 부담전화) <br> <br> COPYRIGHT(C) <strong>Joo.study.com</strong>
						ALL RIGHTS RESERVED.
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<!-- class="footer" -->
		</div>
	</div>

	<script>
		/* gnb_area 로그아웃 버튼 적용 */
		$("#gnb_logout_button").click(function() {
			// alert("버튼 작동");
			$.ajax({
				type : "GET", // "POST"에서 변경 Interceptor에 의해 main 페이지로 강제 이동.
				url : "/member/logout.do",
				success : function(data) {
					alert("로그아웃 성공");
					document.location.reload(); // 현재 페이지 새로고침
				}
			}) //ajax end
		});
	</script>


</body>
</html>