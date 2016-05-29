package com.example.deviceStorageJDBC.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Storage {
	
	private long idPosition;

	private String name;

	private int amount;

	private int margin;
	
	public Storage() {
	}
	
	public Storage(String name, int amount, int margin) {
		this.name = name;
		this.amount = amount;
		this.margin = margin;
	}

	public long getIdPosition() {
		return idPosition;
	}

	public void setIdPosition(long idPosition) {
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
