package com.example.junit;

public class LiczbaRzymska {

	private int liczba;
	

	private static int[]    arabskie = { 1000,  900,  500,  400,  100,   90,  
                                      50,   40,   10,    9,    5,    4,    1 };
                                   
	private static String[] rzymskie = { "M",  "CM",  "D",  "CD", "C",  "XC",
                                    "L",  "XL",  "X",  "IX", "V",  "IV", "I" };
	
	public static int MIN_INTEGER = 1;
	public static int MAX_INTEGER = 3999;
	
	
	LiczbaRzymska(int n){
		   if (n < MIN_INTEGER)
			      throw new NumberFormatException();
			   if (n > MAX_INTEGER)
			      throw new NumberFormatException();
			   liczba = n;
	}
	
	public String toString() {
	   String l_rzymska = "";
	   int N = liczba; 
	   
	   for (int i = 0; i < arabskie.length; i++) {
	      while (N >= arabskie[i]) {
	    	  l_rzymska += rzymskie[i];
	         N -= arabskie[i];
	      }
	   }
	   return l_rzymska;
	}
	
}
