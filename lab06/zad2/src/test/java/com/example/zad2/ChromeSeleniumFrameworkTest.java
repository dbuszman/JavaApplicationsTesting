package com.example.zad2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.ActionChainExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class ChromeSeleniumFrameworkTest {
	
	private static WebDriver driver;
	WebElement element;
	
	private static final String URL = "http://www.seleniumframework.com/Practiceform/";
	private static final String SOURCE_DRIVER = "/opt/devel/chromedriver";


	@BeforeClass
	public static void driverSetup() {
		// change path to chromedriver.exe
		System.setProperty("webdriver.chrome.driver", SOURCE_DRIVER);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}
	
	@Test
	public void mainPageTest() {		
		driver.get(URL);
		String title = driver.getTitle(); 
		
		String expected = "Selenium Framework | Practiceform"; 
		assertEquals(expected, title);
	}
	
	@Test
	public void checkboxTest(){
		driver.get(URL);
		
		String checkboxId = "vfb-6-0";
		element = driver.findElement(By.id(checkboxId));
		element.click();
		
		boolean firstOptionIsChecked = element.isSelected();
		
		assertTrue(firstOptionIsChecked);
	}
	
	@Test
	public void selectTest(){
		driver.get(URL);
		
		String selectXPath = "id('vfb-12')";
		element = driver.findElement(By.xpath(selectXPath));
		
		Select select = new Select(element); 
		select.selectByVisibleText("Option 2");
		
		String selectedOption = new Select(element).getFirstSelectedOption().getText();
		
		assertEquals("Option 2", selectedOption);
	}
	
	@Test
	public void verificationCodeTest(){
		driver.get(URL);
		
		String verCodeXPath = "id('item-vfb-2')/ul/li[1]/span/label";
		element = driver.findElement(By.xpath(verCodeXPath));
		
		String verCode = element.getText();
		verCode = verCode.substring(verCode.length() - 2);

		element = driver.findElement(By.id("vfb-3"));
		element.sendKeys(verCode);
		
		driver.findElement(By.name("vfb-submit")).click();
		
		element = driver.findElement(By.id("form_success"));
		
		String expected = "Your form was successfully submitted. Thank you for contacting us.";
		
		assertEquals(expected, element.getText());
	}
	
	@Test
	public void dragAndDropTest(){
		
		driver.get(URL);
		
		WebElement dragElement = driver.findElement(By.id("draga"));

		WebElement dropElement = driver.findElement(By.id("dragb"));
		
		
		Actions builder = new Actions(driver);
		
		Action dragAndDrop = builder.clickAndHold(dragElement)
				 
			     .moveToElement(dropElement)
	 
			    .release(dropElement)
	 
			   .build();
	 
			  dragAndDrop.perform();
			  
		
		assertTrue(dropElement.isDisplayed());
			  
	}
	
	@Test
	public void alertTest(){
		
		driver.get(URL);
		
		String alertXPath = "//*[@id=\"alert\"]";
		driver.findElement(By.xpath(alertXPath)).click();
		Alert alert = driver.switchTo().alert();
		
		String expected = "Please share this website with your friends and in your organization.";
		assertEquals(expected, alert.getText());
		alert.accept();
	}
	
	@Test
	public void linkTest(){
		
		driver.get(URL);
		
		driver.findElement(By.linkText("This is a link")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		
		String title = driver.getTitle(); 
		
		String expected = "Selenium Framework | Selenium, Cucumber, Ruby, Java et al.";
		assertEquals(expected, title);
	}
	
	@AfterClass
	public static void cleanup() {
		driver.quit();
	}
}
