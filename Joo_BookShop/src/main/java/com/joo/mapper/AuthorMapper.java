package com.joo.mapper;

import java.util.List;

import com.joo.model.AuthorVO;
import com.joo.model.Criteria;

public interface AuthorMapper {

	/* 작가 등록 */
	public void authorEnroll(AuthorVO author);
	
	/* 작가 목록 */
	public List<AuthorVO> authorGetList(Criteria cri); // keyword 를 넘기기위해 cri
	
	/* 작가 총 수 */
	public int authorGetTotal(Criteria cri);
	
	/* 작가 상세 */
	public AuthorVO authorGetDetail(int authorId); // 작가 정보를 가져오기 위헤선, authorId 필요
	
	/* 작가 수정 */
	public int authorModify(AuthorVO author); // 수정 성공 = 1, 실패 = 0
	
	/* 작가 정보 삭제 */
	public int authorDelete(int authorId);
}
