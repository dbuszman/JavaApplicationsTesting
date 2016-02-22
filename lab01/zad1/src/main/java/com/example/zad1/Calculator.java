package com.example.zad1;

public class Calculator {

	public Calculator(){
	}

	public int add(int n1, int n2){
		
		return n1 + n2;
		
	}
	
	public int sub(int n1, int n2){

		return n1 - n2;
		
	}
	
	public int multi(int n1, int n2){
		
		return n1 * n2;
		
	}
	
	public int div(int n1, int n2) {
		
		return n1 / n2;
		
	}
	
	public boolean greater(int n1, int n2){
		
		if (n1 > n2){
			return true;
		}
		else {
			return false;
		}
		
	}
	
}
