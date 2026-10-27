
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

public class RegisterExistingEmailTest {

    @Test
    public void registerWithExistingEmail() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        String name = "tonystark712";
        String existingEmail = "tonystark712@gmail.com";

        try {
            // 1. Launch browser and navigate to URL
            driver.get("https://automationexercise.com");

            // 2. Verify homepage is displayed
            Assert.assertTrue(
                driver.getTitle().contains("Automation Exercise"),
                "Home page is not displayed"
            );

            // 3. Click Signup / Login
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Signup / Login')]")
            )).click();

            // 4. Verify New User Signup! heading
            WebElement signupHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'New User Signup!')]")
                )
            );

            Assert.assertTrue(signupHeading.isDisplayed());

            // 5. Enter name
            driver.findElement(
                By.cssSelector("[data-qa='signup-name']")
            ).sendKeys(name);

            // 6. Enter an already registered email address
            driver.findElement(
                By.cssSelector("[data-qa='signup-email']")
            ).sendKeys(existingEmail);

            // 7. Click Signup
            driver.findElement(
                By.cssSelector("[data-qa='signup-button']")
            ).click();

            // 8. Verify duplicate email error
            WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                        "//*[contains(.,'Email Address already exist!')]"
                    )
                )
            );

            Assert.assertTrue(
                errorMessage.getText().contains(
                    "Email Address already exist!"
                ),
                "Duplicate email error was not displayed"
            );

            System.out.println(
                "Test Case 5 PASSED: Existing email error displayed."
            );

        } finally {
            // 9. Close browser
            driver.quit();
        }
    }
}
