
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

public class VerifyProductsTest {

    @Test
    public void verifyAllProductsAndProductDetails() {

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
                "All Products heading is not displayed"
            );

            // 5. Verify products list is visible
            List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".features_items .product-image-wrapper")
                )
            );

            Assert.assertFalse(
                products.isEmpty(),
                "Products list is empty"
            );

            System.out.println(
                "Number of product cards: " + products.size()
            );

            // 6. Click View Product for the first product
            WebElement firstProduct = products.get(0);

            WebElement viewProductButton = firstProduct.findElement(
                By.xpath(".//a[contains(@href,'/product_details/')]")
            );

            viewProductButton.click();

            // 7. Verify navigation to product detail page
            wait.until(ExpectedConditions.urlContains("/product_details/"));

            // 8. Verify product name
            WebElement productName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".product-information h2")
                )
            );

            Assert.assertTrue(
                productName.isDisplayed(),
                "Product name is not displayed"
            );

            // 9. Verify category
            WebElement category = driver.findElement(
                By.xpath(
                    "//div[contains(@class,'product-information')]"
                    + "//p[contains(.,'Category:')]"
                )
            );

            Assert.assertTrue(
                category.getText().contains("Category:"),
                "Product category is missing"
            );

            // 10. Verify price
            WebElement price = driver.findElement(
                By.cssSelector(".product-information span span")
            );

            Assert.assertTrue(
                price.isDisplayed() && !price.getText().trim().isEmpty(),
                "Product price is missing"
            );

            // 11. Verify availability
            WebElement availability = driver.findElement(
                By.xpath(
                    "//div[contains(@class,'product-information')]"
                    + "//p[contains(.,'Availability:')]"
                )
            );

            Assert.assertTrue(
                availability.getText().contains("Availability:"),
                "Product availability is missing"
            );

            // 12. Verify condition
            WebElement condition = driver.findElement(
                By.xpath(
                    "//div[contains(@class,'product-information')]"
                    + "//p[contains(.,'Condition:')]"
                )
            );

            Assert.assertTrue(
                condition.getText().contains("Condition:"),
                "Product condition is missing"
            );

            // 13. Verify brand
            WebElement brand = driver.findElement(
                By.xpath(
                    "//div[contains(@class,'product-information')]"
                    + "//p[contains(.,'Brand:')]"
                )
            );

            Assert.assertTrue(
                brand.getText().contains("Brand:"),
                "Product brand is missing"
            );

            // 14. Print the verified product details
            System.out.println("Product Name: " + productName.getText());
            System.out.println("Category: " + category.getText());
            System.out.println("Price: Rs. " + price.getText());
            System.out.println("Availability: " + availability.getText());
            System.out.println("Condition: " + condition.getText());
            System.out.println("Brand: " + brand.getText());

            System.out.println(
                "Test Case 8 PASSED: Product details verified!"
            );

        } finally {
            driver.quit();
        }
    }
}
