//package com.automation.testcases;
//
//import java.time.Duration;
//import java.util.Scanner;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//public class ContactUsFormTest {
//	@Test
//	public void contactUsForm() {
//		WebDriver driver=new ChromeDriver();
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		
////		Scanner sc =new Scanner(System.in);
//		
////		System.out.println("Enter the name, email, subject and message one by one");
////		String name=sc.next();
////		String email=sc.next();
////		String Subject=sc.next();
////		String message=sc.next();
//		try {
////			1.open the browser and enter the url
//			driver.get("http://automationexcercise.com");
////			3.Verify home page is visible successfully 
//			Assert.assertTrue(driver.getTitle().contains("AutomationExcercise"), "Home page is not visible succesfully");
////			4.Click on ContactUs button
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@href='/contact_us'])[1]"))).click();
////			5.verify GET IN TOUCH is visible
//			WebElement title=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Get In Touch']")));
//			Assert.assertTrue(title.getText().contains("Get In Touch"),"Contact us page is not visible yet");
//			
////			6.Enter the name, email, subject and message
//			
////		This is the final test step to verify the program is done 	
//			System.out.println("Test case 6 is successfully verified");
//		}
//		finally {
//			driver.quit();
//		}
//
//	}
//
//}


package com.automation.testcases;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactUsFormTest {

    @Test
    public void contactUsForm() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        try {
            // 1. Launch browser and navigate to URL
            driver.get("https://automationexercise.com");

            // 2. Verify homepage
            Assert.assertTrue(
                driver.getTitle().contains("Automation Exercise"),
                "Home page is not displayed"
            );

            // 3. Click Contact Us
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Contact us')]")
            )).click();

            // 4. Verify GET IN TOUCH
            WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[normalize-space()='Get In Touch']")
                )
            );

            Assert.assertTrue(
                heading.isDisplayed(),
                "GET IN TOUCH heading is not displayed"
            );

            // 5. Enter name, email, subject and message
            driver.findElement(
                By.cssSelector("[data-qa='name']")
            ).sendKeys("Test User");

            driver.findElement(
                By.cssSelector("[data-qa='email']")
            ).sendKeys("testuser@example.com");

            driver.findElement(
                By.cssSelector("[data-qa='subject']")
            ).sendKeys("Testing Contact Us Form");

            driver.findElement(
                By.cssSelector("[data-qa='message']")
            ).sendKeys(
                "This is an automated test message using Selenium."
            );

            // 6. Upload file - replace with an existing file path
            String filePath =
                "C:\\Users\\DineshkumarSelvaraj\\Downloads\\sample.txt";

//            File f= new File ("C:\\\\Users\\\\YOUR_USER\\\\Documents\\\\sample.txt");
//            String filePaths=f.getAbsolutePath();
            
            driver.findElement(
                By.cssSelector("input[name='upload_file']")
            ).sendKeys(filePath);

            // 7. Click Submit
            driver.findElement(
                By.cssSelector("[data-qa='submit-button']")
            ).click();

            // 8. Handle JavaScript confirmation alert
            Alert alert = wait.until(
                ExpectedConditions.alertIsPresent()
            );

            alert.accept();

            // 9. Verify success message
            WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                        "//*[contains(text(),"
                        + "'Success! Your details have been submitted successfully.')]"
                    )
                )
            );

            Assert.assertTrue(
                successMessage.getText().contains(
                    "Success! Your details have been submitted successfully."
                ),
                "Contact form success message was not displayed"
            );

            System.out.println(
                "Contact Us form submitted successfully!"
            );

            // 10. Click Home button on the confirmation page
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//a[normalize-space()='Home' "
                    + "and contains(@class,'btn')]"
                )
            )).click();

            // 11. Verify homepage after clicking Home
            wait.until(ExpectedConditions.titleContains(
                "Automation Exercise"
            ));

            Assert.assertTrue(
                driver.getCurrentUrl().contains("automationexercise.com"),
                "User did not return to the home page"
            );

            System.out.println(
                "Test Case 6 PASSED: Contact Us form verified!"
            );

        } finally {
            // 12. Close browser
            driver.quit();
        }
    }
}
