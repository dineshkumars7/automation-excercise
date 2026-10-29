
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

public class CartPageSubscriptionTest {

    @Test
    public void verifyCartPageSubscription() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        try {
            // 1. Launch browser and navigate to URL
            driver.get("https://automationexercise.com");
            driver.manage().window().maximize();
            Thread.sleep(2000);

            // 2. Verify homepage
            Assert.assertTrue(
                driver.getTitle().contains("Automation Exercise"),
                "Home page is not displayed"
            );

            // 3. Click Cart
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Cart')]")
            )).click();

            Thread.sleep(2000);

            // 4. Verify navigation to Cart page
            wait.until(ExpectedConditions.urlContains("/view_cart"));

            Assert.assertTrue(
                driver.getCurrentUrl().contains("/view_cart"),
                "User was not navigated to Cart page"
            );

            // 5. Scroll down to footer
            JavascriptExecutor js =
                (JavascriptExecutor) driver;

            js.executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
            );

            Thread.sleep(2000);

            // 6. Verify SUBSCRIPTION heading
            WebElement subscriptionHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                        "//h2[contains(translate(.,"
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

            // 7. Enter email address
            String email = "carttest123@example.com";

            WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.id("susbscribe_email")
                )
            );

            emailField.sendKeys(email);

            Thread.sleep(2000);

            // 8. Click subscription arrow button
            driver.findElement(By.id("subscribe")).click();

            // 9. Verify subscription success message
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
                "Test Case 11 PASSED: Cart page subscription verified!"
            );

        } finally {
            driver.quit();
        }
    }
}
