
package com.automation.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageSubscriptionTest {

    @Test
    public void verifyHomePageSubscription() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        try {
            // 1. Launch browser and navigate to URL
            driver.get("https://automationexercise.com");
            driver.manage().window().maximize();

            // 2. Verify homepage
            Assert.assertTrue(
                driver.getTitle().contains("Automation Exercise"),
                "Home page is not displayed"
            );

            // 3. Scroll down to the footer
            JavascriptExecutor js =
                (JavascriptExecutor) driver;

            js.executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
            );

            // 4. Verify SUBSCRIPTION heading
            WebElement subscriptionHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                        "//*[contains(translate(.,"
                        + "'abcdefghijklmnopqrstuvwxyz',"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),"
                        + "'SUBSCRIPTION')]"
                    )
                )
            );

            Assert.assertTrue(
                subscriptionHeading.isDisplayed(),
                "SUBSCRIPTION heading is not displayed"
            );

            // 5. Enter email address
            String email = "seleniumtest123@example.com";

            WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.id("susbscribe_email")
                )
            );

            emailField.sendKeys(email);

            // 6. Click subscription arrow button
            driver.findElement(
                By.id("subscribe")
            ).click();

            // 7. Verify success message
            WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".alert-success")
                )
            );

            Assert.assertTrue(
                successMessage.getText().contains(
                    "You have been successfully subscribed!"
                ),
                "Subscription success message is not displayed"
            );

            System.out.println(
                "Test Case 10 PASSED: Subscription successful!"
            );

        } finally {
            // 8. Close browser
            driver.quit();
        }
    }
}
