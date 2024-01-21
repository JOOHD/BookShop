package com.joo.model;

public class PageDTO {

	/* 시작 페이지 */
    private int pageStart; // 시작 번호
    
    /* 끝 페이지 */
    private int pageEnd;   // 끝 번호
    
    /* 이전 페이지, 다음 페이지 존재유무 */
    private boolean prev, next;
    
    /*전체 게시물 수*/
    private int total;
    
    /* 현재 페이지, 페이지당 게시물 표시수 정보 */
    private Criteria cri;
 
    /* 생성자 */
    public PageDTO(Criteria cri, int total) {
    	this.cri = cri;
    	this.total = total;
    	
    	/* 작성한 생성자에선 다음의 순서대로 값들을 계산하여 각 변수를 초기화
    	 * "화면에 보일 마지막 페이지" -> "화면에 보여질 시작 페이지" -> "전체 마지막 페이지 계산"
    	 * -> "화면에 보여질 마지막 페이지 유효한지 체크" -> "화면에 보여질 페이지 이전페이지 존재 여부"
    	 * -> "화면에 보여질 페이지 다음페이지 존재 여부"
    	 */
    	
    	/* 
    	 * (int) 형변환 이유는 Math.ceil = double type
    	 * 그냥 나누고 뒤에 소수점이 몇이든 상관없이 올림해주고 * 10을 해주면 pageEnd 
    	 */
    	
    	/* 마지막 페이지 */
    	this.pageEnd = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
    	
    	/* 시작 페이지 */
    	this.pageStart = this.pageEnd -9;
    	
    	/* 전체 마지막 페이지 */
    	int realEnd = (int)(Math.ceil(total * 1.0/cri.getAmount()));
    	
    	/* 전체 마지막 페이지(realEnd)가 화면에 보이는 마지막페이지(pageEnd)보다 작은 경우, 보이는 페이지(pageEnd) 값 조정 */
    	if(realEnd < this.pageEnd ) {
    		this.pageEnd = realEnd;
    	}
    	
    	/* 시작 페이지(pageStart)값이 1보다 큰 경우 true */
        this.prev = this.pageStart > 1;
        
        /* 마지막 페이지(pageEnd)값이 1보다 큰 경우 true */
        this.next = this.pageEnd < realEnd;
    }

	public int getpageStart() {
		return pageStart;
	}

	public void setpageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getpageEnd() {
		return pageEnd;
	}

	public void setpageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	@Override
	public String toString() {
		return "PageMakerDTO [pageStart=" + pageStart + ", pageEnd=" + pageEnd + ", prev=" + prev + ", next=" + next
				+ ", total=" + total + ", cri=" + cri + "]";
	}
   
    
}
