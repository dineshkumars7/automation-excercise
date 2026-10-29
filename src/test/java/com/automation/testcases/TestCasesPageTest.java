package com.automation.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCasesPageTest {
	@Test
	public void testCasePage(){
		WebDriver driver=new ChromeDriver();
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		
//		1. Launch the  browser and open the URL
		driver.get("http://automationexercise.com");
//		2.Verify the home page is visible 
		Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),"Home page is not visible");
		
//		3. Search for the Testcase link and click
		
		wait.until(ExpectedConditions.elementToBeClickable
				(By.xpath(" (//a[normalize-space()='Test Cases'])[1]"))).click();
		
//		4.verify the user is navigated the TestCase page
		WebElement pageHeader=wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath(" //b[normalize-space()='Test Cases']")));
		Assert.assertTrue(pageHeader.getText().contains("TEST CASES"),"Test case page is not opened");
//		5.This is the final statement to confirm all the steps is executed
		System.out.println("Test case 7: Verify Test case page is executed successfully");
		
	}

}
