package com.joo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class AuthorVO {

	/*
	 * 데이터를 전달하거나, 테이블로부터 반환받은 데이터를 담을 객체를 정의하는 클래스 생성
	 * joo_author table 에 있는 column 기준으로 변수 작성.
	 */
	
	/* 작가 아이디 */
    private int authorId;
    
    /* 작가 이름 */
    private String authorName;
    
    /* 국가 id */
    private String nationId;
    
    /* 작가 국가 */
    private String nationName;
    
    /* 작가 소개 */
    private String authorIntro;
    
    /*등록 날짜*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
    
    /* 수정 날짜 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getNationId() {
		return nationId;
	}

	public void setNationId(String nationId) {
		this.nationId = nationId;
		if(nationId.equals("01")) {
			this.nationName = "국내";
		} else {
			this.nationName = "국외";
		}
			
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getAuthorIntro() {
		return authorIntro;
	}

	public void setAuthorIntro(String authorIntro) {
		this.authorIntro = authorIntro;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "AuthorVO [authorId=" + authorId + ", authorName=" + authorName + ", nationId=" + nationId
				+ ", nationName=" + nationName + ", authorIntro=" + authorIntro + ", regDate=" + regDate
				+ ", updateDate=" + updateDate + "]";
	}
    
}
