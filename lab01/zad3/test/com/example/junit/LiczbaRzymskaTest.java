package com.example.junit;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.example.junit.LiczbaRzymska;

@RunWith(Parameterized.class)
public class LiczbaRzymskaTest {
	
	  @Parameters
	  public static Collection<Object[]> data() {
	    Object[][] data = new Object[][] {
	      {1, "I"},
	      {2, "II"},
	      {3, "III"},
	      {4, "IV"},
	      {5, "V"},
	      {6, "VI"},
	      {7, "VII"},
	      {8, "VIII"},
	      {9, "IX"},
	      {10, "X"},
	      {40, "XL"},
	      {50, "L"},
	      {90, "XC"},
	      {100, "C"},
	      {400, "CD"},
	      {500, "D"},
	      {900, "CM"},
	      {1000, "M"}		
	    };
	    return Arrays.asList(data);
	  }

    private int fInput;

    private String fExpected;

    public LiczbaRzymskaTest(int input, String expected) {
        fInput= input;
        fExpected= expected;
    }
    
    public static int EXCEPTION_PARAM_1 = 0;
    public static int EXCEPTION_PARAM_2 = 4000;
	
    @Test
    public void test() {
    	LiczbaRzymska lrzymska = new LiczbaRzymska(fInput);
        assertEquals(fExpected, lrzymska.toString());
    }
    
    @Test(expected = NumberFormatException.class)  
	public void converseBelowRangeWithExceptionCheck() {  
    	new LiczbaRzymska(EXCEPTION_PARAM_1);
	} 
    
    @Test(expected = NumberFormatException.class)  
	public void converseAboveRangeWithExceptionCheck() {  
    	new LiczbaRzymska(EXCEPTION_PARAM_2);
	} 
}
