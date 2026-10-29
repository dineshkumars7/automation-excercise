
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

public class SearchProductTest {

    @Test
    public void searchProduct() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        try {
            // 1. Launch browser and navigate to URL
            driver.get("https://automationexercise.com");

            // 2. Verify homepage
            Assert.assertTrue(
                driver.getTitle().contains("Automation Exercise"),
                "Home page is not displayed"
            );

            // 3. Click Products
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Products')]")
            )).click();

            // 4. Verify All Products page
            wait.until(ExpectedConditions.urlContains("/products"));

            WebElement allProductsHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(.,'All Products')]")
                )
            );

            Assert.assertTrue(
                allProductsHeading.isDisplayed(),
                "All Products page is not displayed"
            );

            // 5. Enter product name in search field
            String searchProduct = "Blue Top";

            WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.id("search_product")
                )
            );

            searchBox.sendKeys(searchProduct);

            // 6. Click Search
            driver.findElement(By.id("submit_search")).click();

            // 7. Verify SEARCHED PRODUCTS heading
            WebElement searchedProductsHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[text()='Searched Products']")
                )
            );

            Assert.assertTrue(
                searchedProductsHeading.isDisplayed(),
                "SEARCHED PRODUCTS heading is not displayed"
            );

            // 8. Get all displayed product cards
            List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(
                        ".features_items .product-image-wrapper"
                    )
                )
            );

            Assert.assertFalse(
                products.isEmpty(),
                "No products were displayed for the search"
            );

            // 9. Verify that each result matches the search term
            for (WebElement product : products) {

                String productName = product.findElement(
                    By.cssSelector(".productinfo p")
                ).getText();

                Assert.assertTrue(
                    productName.toLowerCase().contains(
                        searchProduct.toLowerCase()
                    ),
                    "Unexpected product in search results: "
                        + productName
                );

                System.out.println(
                    "Matching product: " + productName
                );
            }

            System.out.println(
                "Test Case 9 PASSED: Search results verified!"
            );

        } finally {
            // 10. Close browser
            driver.quit();
        }
    }
}
