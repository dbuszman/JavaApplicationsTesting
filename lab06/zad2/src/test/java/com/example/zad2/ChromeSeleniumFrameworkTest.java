package com.example.zad2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


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
	
	@Test
	public void selectTest(){
		driver.get("http://www.seleniumframework.com/Practiceform/");
		Select select = new Select(driver.findElement(By.xpath("id('vfb-12')"))); 
		select.selectByVisibleText("Option 2");
		
		String selectedOption = new Select(driver.findElement(By.xpath("id('vfb-12')"))).getFirstSelectedOption().getText();
		
		assertEquals("Option 2", selectedOption);
	}
	
	@Test
	public void verificationCodeTest(){
		driver.get("http://www.seleniumframework.com/Practiceform/");
		element = driver.findElement(By.xpath("id('item-vfb-2')/ul/li[1]/span/label"));
		
		String verCode = element.getText();
		verCode = verCode.substring(verCode.length() - 2);

		element = driver.findElement(By.id("vfb-3"));
		element.sendKeys(verCode);
		
		driver.findElement(By.name("vfb-submit")).click();
		
		element = driver.findElement(By.id("form_success"));
		
		assertEquals("Your form was successfully submitted. Thank you for contacting us.", element.getText());
	}
	
	@AfterClass
	public static void cleanup() {
		driver.quit();
	}
}
