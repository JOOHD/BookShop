package com.joo.model;

public class CheckOrderVO {

	private int money;
	private int bookStock;
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getBookStock() {
		return bookStock;
	}
	public void setBookStock(int bookStock) {
		this.bookStock = bookStock;
	}
	@Override
	public String toString() {
		return "CheckOrderVO [money=" + money + ", bookStock=" + bookStock + "]";
	}
	
	
}
