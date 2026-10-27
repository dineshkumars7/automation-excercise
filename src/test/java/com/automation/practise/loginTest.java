package com.automation.practise;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class loginTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver=new ChromeDriver();
		driver.get("https://automationexercise.com/login");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		
		WebElement name = driver.findElement(By.xpath("//input[@name='email']"));
		name.sendKeys("tonystark712@gmail.com");
		
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Tonystark@712");
		driver.findElement(By.xpath(" //button[normalize-space()='Login']")).click();
		
		
		
		// Verification
        boolean loginSuccessful = driver.findElements(
            By.xpath("//a[contains(.,'Logged in as')]")
        ).size() > 0;

        if (loginSuccessful) {
            System.out.println("TEST PASSED: Login successful");
        } else {
            System.out.println("TEST FAILED: Login was not verified");
        }
        
        
        driver.findElement(By.xpath("//a[@href='/products']")).click();
        driver.findElement(By.xpath("//input[@name='search']")).sendKeys("Shirt");
        driver.findElement(By.xpath("//button[@id='submit_search']")).click();
        
//        List<WebElement> products = driver.findElements(
//        	    By.xpath("//div[@class='single-product']")
//        	);
        
        List<WebElement> paragraphs = driver.findElements(
        	    By.xpath("//div[@class='single-product']//p")
        	);

        	for (WebElement p : paragraphs) {
        	    System.out.println(p.getText());
        	}
        System.out.println(paragraphs.size());
        

//        	for (WebElement product : products) {
//        	    String paragraph = product.findElement(By.xpath(".//p")).getText();
//        	    System.out.println(paragraph);
//        	}
        

        // Close browser after verification
        driver.quit();
		

	}

}
