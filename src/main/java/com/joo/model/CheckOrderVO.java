package com.joo.model;

import lombok.Data;

@Data
public class CheckOrderVO {

	private int money;
	private int bookStock;
	
	private int bookId;
	private String memberId;

}
