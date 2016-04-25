package com.example.zad2;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeSeleniumFrameworkTest {
	
	private static WebDriver driver;
	WebElement element;

	@BeforeClass
	public static void driverSetup() {
		// change path to chromedriver.exe
		System.setProperty("webdriver.chrome.driver", "/opt/devel/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	@Test
	public void mainPageTest() {		
		driver.get("http://www.seleniumframework.com/Practiceform/");
		String title = driver.getTitle(); 
		assertEquals("Selenium Framework | Practiceform", title);
	}
	
	@Test
	public void checkboxTest(){
		driver.get("http://www.seleniumframework.com/Practiceform/");
		driver.findElement(By.id("vfb-6-0")).click();
		
		boolean firstOptionIsChecked = driver.findElement(By.id("vfb-6-0")).isSelected();
		
		assertTrue(firstOptionIsChecked);
	}
	
	

	@AfterClass
	public static void cleanup() {
		driver.quit();
	}
}
