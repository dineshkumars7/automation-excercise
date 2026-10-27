
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

public class LoginWithIncorrectCredentialsTest {

    @Test
    public void loginWithIncorrectCredentials() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        try {
            // 1. Launch browser and navigate to URL
            driver.get("https://automationexercise.com");
            
            driver.manage().window().maximize();

            // 2. Verify homepage is displayed
            Assert.assertTrue(
                driver.getTitle().contains("Automation Exercise"),
                "Home page is not displayed"
            );
            
            
            // 3. Click Signup / Login
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Signup / Login')]")
            )).click();

            // 4. Verify Login to your account
            WebElement loginHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'Login to your account')]")
                )
            );

            Assert.assertTrue(loginHeading.isDisplayed());

            // 5. Enter incorrect email and password
            driver.findElement(
                By.cssSelector("[data-qa='login-email']")
            ).sendKeys("wronguser123@example.com");

            driver.findElement(
                By.cssSelector("[data-qa='login-password']")
            ).sendKeys("WrongPassword123");

            // 6. Click Login
            driver.findElement(
                By.cssSelector("[data-qa='login-button']")
            ).click();

            // 7. Verify incorrect login error message
            WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                        "//*[contains(.,'Your email or password is incorrect!')]"
                    )
                )
            );

            Assert.assertTrue(
                errorMessage.getText().contains(
                    "Your email or password is incorrect!"
                ),
                "Incorrect login error message was not displayed"
            );

            System.out.println(
                "Test Case 3 PASSED: Incorrect login error displayed."
            );

        } finally {
            // 8. Close browser
            driver.quit();
        }
    }
}
