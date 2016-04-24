package com.example.zad1;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class SimpleComputerServiceSiteTest {

	private static WebDriver driver;
	WebElement element;
	
	
	private static final String USERNAME = "Admin";
	private static final String PASSWORD = "Admin@1";
	
	private static final String LASTNAME = "Kowalski";
	private static final String FIRSTMIDNAME = "Jan";
	private static final String SPECIALIZATION = "Notebooks";
    private static final String TELEPHONE = "241-532-421";

	@Before
	public void driverSetup() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	@Test
	public void homePage(){
		driver.get("http://simplecomputerservice.azurewebsites.net/");
		
		String title = driver.getTitle(); 
		assertEquals("Home Page - Computer Service", title);
	}
	
	@Test
	public void LogInToAppClickTest(){
		driver.get("http://simplecomputerservice.azurewebsites.net/");
		driver.findElement(By.linkText("Log in")).click();
		
		String targetXPath = "/html/body/div[2]/h2";
		
		String header = driver.findElement(By.xpath(targetXPath)).getText();
		
		String expectedHeader = "Log in.";
		
		assertEquals(expectedHeader, header);
		/*File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);

		try {
			FileUtils.copyFile(screenshot, new File("test/resources/polsat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);*/
	}
	
	@Test
	public void LogInToAppTest(){
		driver.get("http://simplecomputerservice.azurewebsites.net/");
		driver.findElement(By.linkText("Log in")).click();
		
		element = driver.findElement(By.id("Username"));
		element.sendKeys(USERNAME);
		
		element = driver.findElement(By.id("Password"));
		element.sendKeys(PASSWORD);

		element.submit();
		
		String targetXPath = "id('logoutForm')/ul/li[1]/a";
		
		String welcomeUser = driver.findElement(By.xpath(targetXPath)).getText();
		
		String expectedWelcomeUser = "Hello Admin!";
		
		assertEquals(expectedWelcomeUser, welcomeUser);
	}
	
	@Test
	public void AddingTechnicianTest(){
		driver.get("http://simplecomputerservice.azurewebsites.net/");
		driver.findElement(By.linkText("Technicians")).click();
		
		element = driver.findElement(By.id("Username"));
		element.sendKeys(USERNAME);
		
		element = driver.findElement(By.id("Password"));
		element.sendKeys(PASSWORD);

		element.submit();
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);

		try {
			FileUtils.copyFile(screenshot, new File("test/resources/before-add.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.linkText("Create New")).click();
		
		element = driver.findElement(By.id("LastName"));
		element.sendKeys(LASTNAME);
		
		element = driver.findElement(By.id("FirstMidName"));
		element.sendKeys(FIRSTMIDNAME);
		
		element = driver.findElement(By.id("Specialization"));
		element.sendKeys(SPECIALIZATION);
		
		element = driver.findElement(By.id("TelephoneNumber"));
		element.sendKeys(TELEPHONE);
		
		element.submit();
		
		File screenshot2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);

		try {
			FileUtils.copyFile(screenshot2, new File("test/resources/after-add.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.linkText("Notebook")).click();
		
		String targetXPath = "id('TechList')/table/tbody/tr[last()]";
		
		String lastAddedTechWithNotebookSpec = driver.findElement(By.xpath(targetXPath)).getText();
		
		String expectedLastAddedTechWithNotebookSpec = LASTNAME + " " + FIRSTMIDNAME;
		
		assertEquals(expectedLastAddedTechWithNotebookSpec, lastAddedTechWithNotebookSpec);
	}

	@After
	public void cleanp() {
		driver.quit();
	}
}
