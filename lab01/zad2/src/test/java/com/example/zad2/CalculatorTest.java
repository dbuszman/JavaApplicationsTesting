package com.example.zad2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalculatorTest {
	
	private Calculator calc = new Calculator();

	public static double EPSILON = 0.01;
		
		@Test
		public void addCheck(){
			
			assertEquals(6.0, calc.add(3.5, 2.5), EPSILON);
			
		}
		
		@Test
		public void subCheck(){
			
			assertEquals(1.3, calc.sub(3.5, 2.2), EPSILON);
			
		}
		
		@Test
		public void multiCheck(){
			
			assertEquals(14.0, calc.multi(5.6, 2.5), EPSILON);
			
		}
		
		@Test
		public void divCheck(){
			
			assertEquals(7.36, calc.div(18.4, 2.5), EPSILON);
			
		}
		
		@Test
		public void greaterCheck(){
			
			assertTrue(calc.greater(3.4, 2.1));
			assertFalse(calc.greater(2.9, 3.1));
			
		}

}
