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

public class VerifyScrollUpAndDownTest {

    @Test
    public void verifyScrollUpAndDown() {

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

            // Step 4: Scroll down to bottom of page
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

            // Verify that the page is actually scrolled down
            Long scrollPosition = (Long) js.executeScript(
                    "return window.pageYOffset;");

            Assert.assertTrue(
                    scrollPosition > 0,
                    "Page has not scrolled down");

            System.out.println(
                    "Page successfully scrolled down.");

            // Step 6: Click the upward arrow button
            // Automation Exercise uses a floating scroll-up button
            By scrollUpArrow = By.id("scrollUp");

            WebElement arrowButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            scrollUpArrow));

            arrowButton.click();

            // Step 7: Verify homepage banner text is visible
            By bannerText = By.xpath(
                    "//*[contains(.,'Full-Fledged practice website for Automation Engineers')]");

            WebElement homeBanner = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            bannerText));

         // Wait until the page returns to the top
            wait.until(d ->
                (Long) ((JavascriptExecutor) d)
                    .executeScript("return window.pageYOffset;") <= 5
            );

            Assert.assertTrue(
                    homeBanner.isDisplayed(),
                    "Homepage banner text is not visible");

            // Verify page is scrolled to top
            Long finalScrollPosition = (Long) js.executeScript(
                    "return window.pageYOffset;");

            Assert.assertTrue(
                    finalScrollPosition <= 5,
                    "Page has not scrolled back to the top");

            System.out.println(
                    "Page successfully scrolled up.");

            System.out.println(
                    "TC25 PASSED: Scroll down and scroll up verified successfully.");

        } finally {

            driver.quit();
        }
    }
}

