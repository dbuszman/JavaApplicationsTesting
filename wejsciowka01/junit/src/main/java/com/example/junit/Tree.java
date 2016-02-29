package com.example.junit;

public class Tree {
	
	private String name;
	
	private String type;
	
	private int amount;
	
	public Tree(){
	}
	
	public Tree(String name, String type, int amount){
		this.name = name;
		this.type = type;
		this.amount = amount;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}



}
