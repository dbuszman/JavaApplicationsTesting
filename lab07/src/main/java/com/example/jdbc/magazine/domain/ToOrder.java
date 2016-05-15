package com.example.jdbc.magazine.domain;

public class ToOrder {

	private long idOrder;

	private long idMagazine;

	private int orderedAmount;

	private double price;

	public ToOrder() {
	}

	public ToOrder(int orderedAmount, double price) {
		this.orderedAmount = orderedAmount;
		this.price = price;
	}

	public long getId() {
		return idOrder;
	}

	public void setId(long idOrder) {
		this.idOrder = idOrder;
	}

	public long getMagazineId() {
		return idMagazine;
	}

	public void setMagazineId(long idMagazine) {
		this.idMagazine = idMagazine;
	}

	public int getOrderedAmount() {
		return orderedAmount;
	}

	public void setOrderedAmount(int orderedAmount) {
		this.orderedAmount = orderedAmount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
