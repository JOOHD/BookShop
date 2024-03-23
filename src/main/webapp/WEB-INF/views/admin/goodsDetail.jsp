<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/admin/goodsDetail.css">

<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/26.0.0/classic/ckeditor.js"></script>
	<style type="text/css">
	#result_card img{
		max-width: 100%;
	    height: auto;
	    display: block;
	    padding: 5px;
	    margin-top: 10px;
	    margin: auto;	
	}
	</style>
</head>
</head>
<body>

	<%@include file="../includes/admin/header.jsp"%>
	<div class="admin_content_wrap">
		<div class="admin_content_subject">
			<span>상품 상세</span>
		</div>

		<div class="admin_content_main">

			<div class="form_section">
				<div class="form_section_title">
					<label>책 제목</label>
				</div>
				<div class="form_section_content">
					<input name="bookName"
						value="<c:out value="${goodsInfo.bookName}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>등록 날짜</label>
				</div>
				<div class="form_section_content">
					<input
						value="<fmt:formatDate value='${goodsInfo.regDate}' pattern='yyyy-MM-dd'/>"
						disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>최근 수정 날짜</label>
				</div>
				<div class="form_section_content">
					<input
						value="<fmt:formatDate value='${goodsInfo.updateDate}' pattern='yyyy-MM-dd'/>"
						disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>작가</label>
				</div>
				<div class="form_section_content">
					<input id="authorName_input" readonly="readonly"
						value="${goodsInfo.authorName }" disabled>

				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>출판일</label>
				</div>
				<div class="form_section_content">
					<input name="publeYear" autocomplete="off" readonly="readonly"
						value="<c:out value="${goodsInfo.publeYear}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>출판사</label>
				</div>
				<div class="form_section_content">
					<input name="publisher"
						value="<c:out value="${goodsInfo.publisher}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>책 카테고리</label>
				</div>
				<div class="form_section_content">
					<div class="cate_wrap">
						<span>대분류</span> <select class="cate1" disabled>
							<option value="none">선택</option>
						</select>
					</div>
					<div class="cate_wrap">
						<span>중분류</span> <select class="cate2" disabled>
							<option value="none">선택</option>
						</select>
					</div>
					<div class="cate_wrap">
						<span>소분류</span> <select class="cate3" name="cateCode" disabled>
							<option value="none">선택</option>
						</select>
					</div>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>상품 가격</label>
				</div>
				<div class="form_section_content">
					<input name="bookPrice"
						value="<c:out value="${goodsInfo.bookPrice}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>상품 재고</label>
				</div>
				<div class="form_section_content">
					<input name="bookStock"
						value="<c:out value="${goodsInfo.bookStock}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>상품 할인율</label>
				</div>
				<div class="form_section_content">
					<input id="discount_interface" maxlength="2" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>책 소개</label>
				</div>
				<div class="form_section_content bit">
					<textarea name="bookIntro" id="bookIntro_textarea" disabled>${goodsInfo.bookIntro}</textarea>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>책 목차</label>
				</div>
				<div class="form_section_content bct">
					<textarea name="bookContents" id="bookContents_textarea" disabled>${goodsInfo.bookContents}</textarea>
				</div>
			</div>
			
			<div class="form_section">
				<div class="form_Section_title">
					<label>상품 이미지</label>
				</div>
				<div class="form_section_content">
					<div id="uploadResult">
					
					</div>
				</div>
			</div>

			<div class="btn_section">
				<button id="cancelBtn" class="btn">상품 목록</button>
				<button id="modifyBtn" class="btn enroll_btn">수정</button>
			</div>
		</div>

		<!-- localhost:8080/admin/goodsDetail?pageNum=1&amount=10&keyword=&bookId=61 -->
		<form id="moveForm" action="/admin/goodsManage" method="get" >
			<input type="hidden" name="pageNum" value="${cri.pageNum}">
			<input type="hidden" name="amount" value="${cri.amount}">
			<input type="hidden" name="keyword" value="${cri.keyword}">
        </form>

	</div>
	<%@include file="../includes/admin/footer.jsp"%>

<script>
	
		$(document).ready(function(){
	
			/*
				현재의 할인율 값은 소수로 출력되는데 사용자가 보기 편하도록 자연수(1~99)로 출력되도록 할 것입니다.
				
				Oracle인 경우, mysql은 해당x
			
				* 출판일 값 가공 *				
				let publeYear = '${goodsInfo.publeYear}';
				let length = publeYear.indexOf(" ");
				
				publeYear = publeYear.substring(0, length);
				
				$("input[name='publeYear']").attr("value", publeYear);
			*/
			
			/* 할인율 값 삽입 */
			let bookDiscount = '<c:out value="${goodsInfo.bookDiscount}"/>' * 100;
			$("#discount_interface").attr("value", bookDiscount);
			
			
			/*
				하지만 ckeditor가 적용된 상태에서 <textarea>에 disabled 속성을 추가해주더라도,
				사용자가 해당 페이지에 접속했을 때 '책 소개', '책 목차'를 클릭하거나 입력이 가능합니다. 
				(물론 사용자가 입력을 하거나 수정을 하더라도 서버의 DB 데이터에는 영향을 끼치지는 않습니다만 )
			*/
			
			/* 책 소개 */
			ClassicEditor
				.create(document.querySelector('#bookIntro_textarea'))
				.then(editor =>{
					console.log(editor);
					editor.isReadOnly = true;
				})
				
				.catch(error=>{
					console.error(error);
				});	
			
			/* 책 목차 */
			ClassicEditor
				.create(document.querySelector('#bookContents_textarea'))
				.then(editor =>{
					console.log(editor);
					editor.isReadOnly = true;
				})
				
				.catch(error=>{
					console.error(error);
				});
			
			/* 카테고리 */
			let cateList = JSON.parse('${cateList}');

			let cate1Array = new Array();
			let cate2Array = new Array();
			let cate3Array = new Array();
			let cate1Obj = new Object();
			let cate2Obj = new Object();
			let cate3Obj = new Object();
			
			let cateSelect1 = $(".cate1");		
			let cateSelect2 = $(".cate2");
			let cateSelect3 = $(".cate3");
			
			/* 카테고리 배열 초기화 메서드 */
			function makeCateArray(obj,array,cateList, tier){
				for(let i = 0; i < cateList.length; i++){
					if(cateList[i].tier === tier){
						obj = new Object();
						
						obj.cateName = cateList[i].cateName;
						obj.cateCode = cateList[i].cateCode;
						obj.cateParent = cateList[i].cateParent;
						
						array.push(obj);				
						
					}
				}
			}	
			
			/* 배열 초기화 */
			makeCateArray(cate1Obj,cate1Array,cateList,1);
			makeCateArray(cate2Obj,cate2Array,cateList,2);
			makeCateArray(cate3Obj,cate3Array,cateList,3);
			
			/* 
				종,소분류 카테고리에서 선택된 항목에 대한 데이터를 저장할 변수를 선언 후,
				소분류 변수에는 db에 저장된 사용자가 선택한 카테고리 코드로 초기화한다
			*/
			let targetCate2 = ''; // 선택된 항목에 대한 데이터를 저장할 변수
			let targetCate3 = '${goodsInfo.cateCode}'; // db에 저장된 사용자가 선택한 카테고리 코드로 초기화
			
			/* targetCate3 변수는 현재 오로지 코드만 저장되어있다. 이변수를 cateParent, cateName 값도 포함된 객체를 저장되도록 추가 */
			for(let i = 0; i < cate3Array.length; i++){
				if(targetCate3 === cate3Array[i].cateCode){
					targetCate3 = cate3Array[i];
				}
			}
			
			console.log("${goodsInfo.bookPrice}");
			
			console.log('targetCate3 : ' + targetCate3);
			console.log('targetCate3.cateName : ' + targetCate3.cateName);
			console.log('targetCate3.cateCode : ' + targetCate3.cateCode);
			console.log('targetCate3.cateParent : ' + targetCate3.cateParent);
			
			/* cate3Array에 담긴 소분류 데이터를 모두 비교하여 targetCate3의 cateParent와 동일한 값을 가지는 데이터들 조분류 <select>항목에 추가 */
			for(let i = 0; i < cate3Array.length; i++){
				if(targetCate3.cateParent === cate3Array[i].cateParent){
					cateSelect3.append("<option value='"+cate3Array[i].cateCode+"'>"+cate3Array[i].cateName+"</option>");
				}
			}
			
			/* DB에 저장된 값에 해당하는 카테고리 <option> 태그에 select 속서잉 추가되도록 코드를 작성 */
			$(".cate3 option").each(function(i,obj){
				if(targetCate3.cateCode === obj.value){
					$(obj).attr("selected", "selected");
				}
			});
			
			/* 중분류 */
			for(let i = 0; i < cate2Array.length; i++){
				if(targetCate3.cateParent === cate2Array[i].cateCode){
					targetCate2 = cate2Array[i];	
				}
			}
			
			for(let i = 0; i < cate2Array.length; i++){
				if(targetCate2.cateParent === cate2Array[i].cateParent){
					cateSelect2.append("<option value='"+cate2Array[i].cateCode+"'>" + cate2Array[i].cateName + "</option>");
				}
			}		
			
			$(".cate2 option").each(function(i,obj){
				if(targetCate2.cateCode === obj.value){
					$(obj).attr("selected", "selected");
				}
			});
			
			/* 대분류 */
			for(let i = 0; i < cate1Array.length; i++){
				cateSelect1.append("<option value='"+cate1Array[i].cateCode+"'>" + cate1Array[i].cateName + "</option>");
			}
			
			$(".cate1 option").each(function(i,obj){
				if(targetCate2.cateParent === obj.value){
					$(obj).attr("selected", "selected");
				}
			});
			
			/*
			console.log('cate1Array : ' + cate1Array);
			console.log('cate2Array : ' + cate2Array);
			console.log('cate3Array : ' + cate3Array);
			
			console.log('cate1Obj : ' + cate1Obj);
			console.log('cate2Obj : ' + cate2Obj);
			console.log('cate3Obj : ' + cate3Obj);
			
			console.log('cateSelect1 : ' + cateSelect1);
			console.log('cateSelect2 : ' + cateSelect2);
			console.log('cateSelect3 : ' + cateSelect3);
			
			console.log('makeCateArray : ' + makeCateArray);
			*/
			
			console.log('targetCate2 : ' + targetCate2);
			console.log('targetCate2.cateName : ' + targetCate2.cateName);
			console.log('targetCate2.cateCode : ' + targetCate2.cateCode);
			console.log('targetCate2.cateParent : ' + targetCate2.cateParent);
			
			/* 이미지 정보 호출 */
			let bookId = '<c:out value="${goodsInfo.bookId}"/>';
			let uploadResult = $("#uploadResult");
			
			// 요청 url, 전달 데이터, 골백함수
			$.getJSON("/getAttachList", {bookId : bookId}, function(arr){
				
				if(arr.length === 0){
					
					let str = "";
					str += "<div id='result_card'>";
					str += "<img src='/resources/img/noImage.png'>";
					str += "</div>;"
					
					uploadResult.html(str);
					
					return;
				}
				
				let str = "";
				let obj = arr[0]; // 서버에서 배열로 데이터를 작성했기에, 뷰에서 배열로 전달 받는다.
								  // 작성한 상품 이미지는 하나의 이미지만 저장하고 출력하기때문에 배열의 첫 번째 요소로 변수를 지정.
				
			 	let fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
			 	str += "<div id='result_card'";
				str += "data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "'data-filename='" + obj.fileName + "'";
				str += ">";
				str += "<img src='/display?fileName=" + fileCallPath +"'>";
				str += "</div>";
				
				uploadResult.html(str);	
			});
			
		}); // $(document).ready
		
		/* 목록 이동 버튼 */
		$("#cancelBtn").on("click", function(e){
			e.preventDefault();
			
			$("#moveForm").submit();	
		});	
		
		/* 수정 페이지 이동 */
		$("#modifyBtn").on("click", function(e){
			e.preventDefault();
			
			let addInput = '<input type="hidden" name="bookId" value="${goodsInfo.bookId}">';
			
			$("#moveForm").append(addInput);
			$("#moveForm").attr("action", "/admin/goodsModify"); // <form id="moveForm" action="/admin/goodsManage" method="get" >
			$("#moveForm").submit();
		});	
	
</script>
</body>
</html>