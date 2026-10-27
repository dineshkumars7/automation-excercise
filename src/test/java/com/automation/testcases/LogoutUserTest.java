
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

public class LogoutUserTest {

    @Test
    public void logoutUser() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        String email = "tonystark712@gmail.com";
        String password = "Tonystark@712";
        String username = "tonystark712";

        try {
            // 1. Launch browser and navigate to URL
            driver.get("https://automationexercise.com");

            // 2. Verify homepage
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

            // 5. Enter valid email and password
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

            // 8. Click Logout
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Logout')]")
            )).click();

            // 9. Verify navigation to login page
            wait.until(ExpectedConditions.urlContains("/login"));

            Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "User was not redirected to the login page"
            );

            // 10. Verify login form is visible again
            Assert.assertTrue(
                wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-qa='login-email']")
                    )
                ).isDisplayed(),
                "Login form is not displayed after logout"
            );

            System.out.println("Test Case 4 PASSED: Logout successful!");

        } finally {
            // 11. Close browser
            driver.quit();
        }
    }
}
