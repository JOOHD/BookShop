package com.joo.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joo.model.AuthorVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AuthorMapperTests {
 
    @Autowired
    private AuthorMapper mapper;
    
    /* 작가 등록 테스트 */
    @Test
    public void authorEnroll() throws Exception{
        
        AuthorVO author = new AuthorVO();
        
        author.setNationId("00");
        author.setAuthorName("12/15 테스트");
        author.setAuthorIntro("테스트 소개");
        
        mapper.authorEnroll(author);
        
    }
    
    
    /* 작가 리스트 테스트 
    @Test
    public void authorGetListTest() throws Exception{
    	
    	Criteria cri = new Criteria(1,10); // 3페이지 & 10개 행 표지
    	cri.setKeyword("김난도");
    	
    	List<AuthorVO> list = mapper.authorGetList(cri);
    	
    	for(int i = 0; i < list.size(); i++) {
    		System.out.println("list" + i + "........." + list.get(i));
    	}
    }
    */
    
    /* 작가 총 수 
    @Test
    public void authorGetTotalTest() throws Exception{
    	
    	Criteria cri = new Criteria();
    	
    	cri.setKeyword("김난도");
    	
    	int total = mapper.authorGetTotal(cri);
    	
    	System.out.println("total........" + total);
    }
    */
    
    /* 작가 상세 페이지 
    @Test
    public void authorGetDetailTest() throws Exception{
    	
    	int authorId = 30;
    	
    	// authorId를 받아와야 하기 때문에 AuthorVO 클래스를 타입으로 두었다.
    	AuthorVO author = mapper.authorGetDetail(authorId);
    	
    	System.out.println("author......" + author);
    }
    */
    
    /* 작가 수정 페이지
    @Test
    public void authorModifyTest() {
    	
    	AuthorVO author = new AuthorVO();
    	
    	author.setAuthorId(261);
    	System.out.println("수정 전.........." + mapper.authorGetDetail(author.getAuthorId()));
    	
    	author.setAuthorName("디오게네스");
    	author.setNationId("02");
    	author.setAuthorIntro("소개 수정 하였습니다.");
    	
    	mapper.authorModify(author);
    	System.out.println("수정 후.........." + mapper.authorGetDetail(author.getAuthorId()));

    }
    */
}