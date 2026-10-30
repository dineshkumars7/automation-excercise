
package com.automation.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewCategoryProductsTest {

    @Test
    public void viewCategoryProducts() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        try {

            // 1. Launch browser and open website
            driver.get("https://automationexercise.com");
            driver.manage().window().maximize();

            // 2. Verify home page
            Assert.assertTrue(
                    driver.getTitle().contains("Automation Exercise"),
                    "Home page is not displayed"
            );

            // 3. Verify Categories heading is visible
            By categoryHeading = By.xpath(
                    "//h2[contains(.,'Category')]"
            );

            Assert.assertTrue(
                    wait.until(ExpectedConditions
                            .visibilityOfElementLocated(categoryHeading))
                            .isDisplayed(),
                    "Categories heading is not visible"
            );

            // 4. Verify Women category is visible
            By womenCategory = By.xpath(
                    "//a[@href='#Women']"
            );

            Assert.assertTrue(
                    wait.until(ExpectedConditions
                            .visibilityOfElementLocated(womenCategory))
                            .isDisplayed(),
                    "Women category is not visible"
            );

            // 5. Click Women category
            wait.until(ExpectedConditions.elementToBeClickable(
                    womenCategory
            )).click();

            // 6. Click Tops subcategory
            By womenTops = By.xpath(
                    "//a[contains(@href,'/category_products/2')]"
            );

            wait.until(ExpectedConditions.elementToBeClickable(
                    womenTops
            )).click();

            // 7. Verify Women - Tops Products heading
            By womenTopsHeading = By.xpath(
                    "//h2[contains(translate(.,"
                    + "'abcdefghijklmnopqrstuvwxyz',"
                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),"
                    + "'WOMEN - TOPS PRODUCTS')]"
            );

            String actualWomenHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            womenTopsHeading
                    )
            ).getText().trim();

            Assert.assertEquals(
                    actualWomenHeading.toUpperCase(),
                    "WOMEN - TOPS PRODUCTS",
                    "Women Tops category page is incorrect"
            );

            System.out.println(
                    "Women - Tops category displayed successfully!"
            );

            // 8. Click Men category in sidebar
            By menCategory = By.xpath(
                    "//a[@href='#Men']"
            );

            wait.until(ExpectedConditions.elementToBeClickable(
                    menCategory
            )).click();

            // 9. Click Jeans subcategory
            By menJeans = By.xpath(
                    "//a[contains(@href,'/category_products/6')]"
            );

            wait.until(ExpectedConditions.elementToBeClickable(
                    menJeans
            )).click();

            // 10. Verify navigation to Men - Jeans page
            wait.until(ExpectedConditions.urlContains(
                    "/category_products/6"
            ));

            Assert.assertTrue(
                    driver.getCurrentUrl().contains(
                            "/category_products/6"
                    ),
                    "User was not navigated to Men Jeans page"
            );

            // Verify category heading
            By menJeansHeading = By.xpath(
                    "//h2[contains(translate(.,"
                    + "'abcdefghijklmnopqrstuvwxyz',"
                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),"
                    + "'MEN - JEANS PRODUCTS')]"
            );

            String actualMenHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            menJeansHeading
                    )
            ).getText().trim();

            Assert.assertEquals(
                    actualMenHeading.toUpperCase(),
                    "MEN - JEANS PRODUCTS",
                    "Men Jeans category page is incorrect"
            );

            System.out.println(
                    "Men - Jeans category displayed successfully!"
            );

            System.out.println(
                    "TC18 PASSED: Category navigation verified!"
            );

        } finally {
            driver.quit();
        }
    }
}
