package com.example.seleniumdemo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class SomeSiteTest {

	private static WebDriver driver;
	WebElement element;

	@BeforeClass
	public static void driverSetup() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	@Test
	public void homePage(){
		driver.get("http://www.teleman.pl");
		
		element = driver.findElement(By.linkText("Polsat"));
		assertNotNull(element);
	}
	
	@Test
	public void polsatPage(){
		driver.get("http://www.teleman.pl/");
		driver.findElement(By.linkText("Polsat")).click();
		element = driver.findElement(By.linkText("Polsat"));
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);

		try {
			FileUtils.copyFile(screenshot, new File("test/resources/polsat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@AfterClass
	public static void cleanp() {
		driver.quit();
	}
}
