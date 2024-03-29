package com.joo.model;

public class CateFilterDTO {

	/* 카테고리 이름 */
	private String cateName;
	
	/* 카테고리 넘버 */
	private String cateCode;;
	
	/* 카테고리 상품 수 */
	private int cateCount;	
	
	/* 국내,국외 분류 */
	private String cateGroup;

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCateCode() {
		return cateCode;
	}

	// cateCode 변수에 값이 들어올때(국내1/외2) 첫 번째 숫자가 국내/외 구분을 할 숫자이고 추출하여, cateGroup 변수에 값 저장.
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
		this.cateGroup = cateCode.split("")[0]; 
	}

	public int getCateCount() {
		return cateCount;
	}

	public void setCateCount(int cateCount) {
		this.cateCount = cateCount;
	}

	public String getCateGroup() {
		return cateGroup;
	}

	public void setCateGroup(String cateGroup) {
		this.cateGroup = cateGroup;
	}

	@Override
	public String toString() {
		return "CateFilterDTO [cateName=" + cateName + ", cateCode=" + cateCode + ", cateCount=" + cateCount
				+ ", cateGroup=" + cateGroup + "]";
	}
}
