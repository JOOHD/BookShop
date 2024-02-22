package com.joo.model;

import lombok.Data;

@Data
public class AttachImageVO { 
	
	// '경로', 'uuid', '파일 이름'에 대한 데이터를 DB에 보관, 따라서 앞서 말한 3가지 데이터를 뷰로 전송.

	/* 경로 */
	private String uploadPath;
	
	/* uuid */
	private String uuid;
	
	/* 파일 이름 */
	private String fileName;
	
	/* 상품 id */
	private int bookId;
}
