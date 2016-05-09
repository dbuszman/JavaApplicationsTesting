package com.example.jdbc.magazine.domain;

public class Magazine {
	
	private long idPosition;
	
	private String name;
	
	private int amount;
	
	private int margin;

	public Magazine(){
	}
	
	public Magazine(String name, int amount, int margin){
		this.name = name;
		this.amount = amount;
		this.margin = margin;
	}
	
	public long getId() {
		return idPosition;
	}

	public void setId(long idPosition) {
		this.idPosition = idPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}
	
}
