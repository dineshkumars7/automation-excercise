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

public class VerifyScrollUpWithoutArrowTest {

    @Test
    public void verifyScrollUpWithoutArrow() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {

            // Step 1 & 2: Launch browser and open website
            driver.manage().window().maximize();
            driver.get("https://automationexercise.com/");

            // Step 3: Verify homepage
            Assert.assertTrue(
                    driver.getTitle().contains("Automation Exercise"),
                    "Home page is not displayed");

            // Step 4: Scroll down to bottom
            js.executeScript(
                    "window.scrollTo(0, document.body.scrollHeight)");

            // Step 5: Verify SUBSCRIPTION is visible
            WebElement subscriptionHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//*[contains(translate(.,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'SUBSCRIPTION')]")));

            Assert.assertTrue(
                    subscriptionHeading.isDisplayed(),
                    "Subscription section is not visible");

            // Verify page has scrolled down
            Long scrollPosition = (Long) js.executeScript(
                    "return window.pageYOffset;");

            Assert.assertTrue(
                    scrollPosition > 0,
                    "Page has not scrolled down");

            System.out.println(
                    "Page successfully scrolled down.");

            // Step 6: Scroll up without using arrow button
            js.executeScript("window.scrollTo(0, 0)");

            // Step 7: Verify homepage banner is visible
            By bannerText = By.xpath(
                    "//*[contains(.,'Full-Fledged practice website for Automation Engineers')]");

            WebElement homeBanner = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            bannerText));

            // Wait until page reaches the top
            wait.until(d ->
                    (Long) ((JavascriptExecutor) d)
                    .executeScript("return window.pageYOffset;") <= 5
            );

            Assert.assertTrue(
                    homeBanner.isDisplayed(),
                    "Homepage banner is not visible");

            // Verify final scroll position
            Long finalScrollPosition = (Long) js.executeScript(
                    "return window.pageYOffset;");

            Assert.assertTrue(
                    finalScrollPosition <= 5,
                    "Page has not scrolled to the top");

            System.out.println(
                    "Page successfully scrolled up without arrow button.");

            System.out.println(
                    "TC26 PASSED: Scroll down and scroll up verified successfully.");

        } finally {

            driver.quit();
        }
    }
}
