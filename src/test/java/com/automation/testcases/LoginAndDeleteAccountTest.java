package com.automation.testcases;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginAndDeleteAccountTest {

    @Test
    public void loginAndDeleteAccount() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        String email = "tonystark712@gmail.com";
        String password = "Tonystark@712";
        String username = "tonystark712";

        try {
            // 1. Launch browser and navigate to website
            driver.get("https://automationexercise.com");

            driver.manage().window().maximize();
            
            // 2. Verify homepage
            Assert.assertTrue(
                driver.getTitle().contains("Automation Exercise"),
                "Homepage was not displayed"
            );

            // 3. Click Signup / Login
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Signup / Login')]")
            )).click();

            // 4. Verify Login to your account
            WebElement loginHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Login to your account')]")
                )
            );

            Assert.assertTrue(loginHeading.isDisplayed());

            // 5. Enter email and password
            driver.findElement(
                By.cssSelector("[data-qa='login-email']")
            ).sendKeys(email);

            driver.findElement(
                By.cssSelector("[data-qa='login-password']")
            ).sendKeys(password);

            // 6. Click Login
            driver.findElement(
                By.cssSelector("[data-qa='login-button']")
            ).click();

            // 7. Verify logged-in username
            WebElement loggedInUser = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(.,'Logged in as')]")
                )
            );

            Assert.assertTrue(
                loggedInUser.getText().contains(username),
                "Logged-in username did not match"
            );

            // 8. Delete Account - permanently deletes this test account
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Delete Account')]")
            )).click();

			// 9. Verify account deletion
			
			WebElement deletedMessage = wait.until(
			    ExpectedConditions.visibilityOfElementLocated(
			        By.xpath("//h2[contains(.,'Account Deleted!')]")
			    )
			);
			
			Assert.assertTrue(
			    deletedMessage.getText().contains("ACCOUNT DELETED!"),
			    "Account deletion message was not displayed"
			);
			
			System.out.println("Test Case 2 PASSED!");


        } finally {
            // 10. Close browser, even if an assertion fails
            driver.quit();
        }
    }
}


