package com.joo.mapper;

import java.util.List;

import com.joo.model.AttachImageVO;

/* 첨부파일과 관련된 요청만을 처리하는 클래스 */
public interface AttachMapper {
	
	/* 이미지 데이터 반환 */
	public List<AttachImageVO> getAttachList(int bookId); // 이미지 정보를 반환받기 위해 bookId

	
}
