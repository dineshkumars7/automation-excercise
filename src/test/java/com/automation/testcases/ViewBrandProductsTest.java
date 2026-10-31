
package com.automation.testcases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewBrandProductsTest {

    @Test
    public void viewBrandProducts() {

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

            // 3. Click Products
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/products']")
            )).click();

            // 4. Verify All Products page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(.,'All Products')]")
            ));

            // 5. Verify Brands heading is visible
            By brandsHeading = By.xpath(
                    "//div[contains(@class,'brands-name')]//h2"
            );

            Assert.assertTrue(
                    wait.until(ExpectedConditions
                            .visibilityOfElementLocated(brandsHeading))
                            .isDisplayed(),
                    "Brands heading is not visible"
            );

            // 6. Verify brand list is displayed
            By brandLinks = By.cssSelector(
                    ".brands-name ul li a"
            );

            wait.until(ExpectedConditions
                    .numberOfElementsToBeMoreThan(brandLinks, 1));

            List<WebElement> brands =
                    driver.findElements(brandLinks);

            Assert.assertTrue(
                    brands.size() > 0,
                    "Brand list is empty"
            );

            System.out.println(
                    "Brands displayed: " + brands.size()
            );

            // 7. Click Polo brand
            By poloBrand = By.xpath(
                    "//div[contains(@class,'brands-name')]"
                    + "//a[contains(.,'Polo')]"
            );

            wait.until(ExpectedConditions.elementToBeClickable(
                    poloBrand
            )).click();

            // 8. Verify Polo brand page
            wait.until(ExpectedConditions.urlContains(
                    "/brand_products/"
            ));

            By poloHeading = By.xpath(
                    "//h2[contains(translate(.,"
                    + "'abcdefghijklmnopqrstuvwxyz',"
                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),"
                    + "'BRAND - POLO PRODUCTS')]"
            );

            String actualPoloHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            poloHeading
                    )
            ).getText().trim();

            Assert.assertTrue(
                    actualPoloHeading.toUpperCase()
                            .contains("BRAND - POLO PRODUCTS"),
                    "Polo brand page is not displayed"
            );

            // 9. Verify Polo products are displayed
            By productCards = By.cssSelector(
                    ".features_items .product-image-wrapper"
            );

            wait.until(ExpectedConditions
                    .numberOfElementsToBeMoreThan(productCards, 0));

            List<WebElement> poloProducts =
                    driver.findElements(productCards);

            Assert.assertTrue(
                    poloProducts.size() > 0,
                    "No Polo products are displayed"
            );

            System.out.println(
                    "Polo products displayed: " + poloProducts.size()
            );

            // 10. Click H&M brand
            By hmBrand = By.xpath(
                    "//div[contains(@class,'brands-name')]"
                    + "//a[contains(.,'H&M')]"
            );

            wait.until(ExpectedConditions.elementToBeClickable(
                    hmBrand
            )).click();

            // 11. Verify H&M brand page
            wait.until(ExpectedConditions.urlContains(
                    "/brand_products/"
            ));

            By hmHeading = By.xpath(
                    "//h2[contains(translate(.,"
                    + "'abcdefghijklmnopqrstuvwxyz',"
                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),"
                    + "'BRAND - H&M PRODUCTS')]"
            );

            String actualHmHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            hmHeading
                    )
            ).getText().trim();

            Assert.assertTrue(
                    actualHmHeading.toUpperCase()
                            .contains("BRAND - H&M PRODUCTS"),
                    "H&M brand page is not displayed"
            );

            // 12. Verify H&M products are displayed
            wait.until(ExpectedConditions
                    .numberOfElementsToBeMoreThan(productCards, 0));

            List<WebElement> hmProducts =
                    driver.findElements(productCards);

            Assert.assertTrue(
                    hmProducts.size() > 0,
                    "No H&M products are displayed"
            );

            System.out.println(
                    "H&M products displayed: " + hmProducts.size()
            );

            System.out.println(
                    "TC19 PASSED: Brand navigation verified!"
            );

        } finally {
            driver.quit();
        }
    }
}
