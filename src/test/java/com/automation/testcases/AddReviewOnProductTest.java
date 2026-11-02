
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

public class AddReviewOnProductTest {

    @Test
    public void addReviewOnProduct() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        try {

            // Step 1 & 2: Launch browser and open website
            driver.manage().window().maximize();
            driver.get("https://automationexercise.com/");

            // Step 3: Click Products
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/products']"))).click();

            // Step 4: Verify ALL PRODUCTS page
            wait.until(ExpectedConditions.urlContains("/products"));

            Assert.assertTrue(
                    driver.findElement(By.xpath(
                            "//*[contains(.,'ALL PRODUCTS')]"))
                            .isDisplayed(),
                    "ALL PRODUCTS page is not displayed");

            // Step 5: Click View Product for the first product
            WebElement firstProduct = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("(//a[contains(@href,'/product_details/')])[1]")));

            firstProduct.click();

            // Step 6: Verify Write Your Review is visible
            WebElement reviewHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(.,'Write Your Review')]")));

            Assert.assertTrue(
                    reviewHeading.isDisplayed(),
                    "Write Your Review section is not visible");

            // Step 7: Enter name, email and review
            driver.findElement(By.id("name"))
                    .sendKeys("Test User");

            driver.findElement(By.id("email"))
                    .sendKeys("testuser@example.com");

            driver.findElement(By.id("review"))
                    .sendKeys(
                            "The product quality is good. "
                            + "The design and overall experience are excellent.");

            // Step 8: Click Submit
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("button-review"))).click();

            // Step 9: Verify success message
            WebElement successMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//*[contains(.,'Thank you for your review.')]")));

            Assert.assertTrue(
                    successMessage.isDisplayed(),
                    "Review submission success message is not displayed");

            System.out.println(
                    "TC21 PASSED: Product review submitted successfully.");

        } finally {

            driver.quit();
        }
    }
}
