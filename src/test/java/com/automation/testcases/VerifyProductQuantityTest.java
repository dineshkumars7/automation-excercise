
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

public class VerifyProductQuantityTest {

    @Test
    public void verifyProductQuantityInCart() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            // 1. Launch browser and navigate to website
            driver.get("https://automationexercise.com");
            driver.manage().window().maximize();

            // 2. Verify home page
            Assert.assertTrue(
                    driver.getTitle().contains("Automation Exercise"),
                    "Home page is not displayed"
            );

            // 3. Click View Product for the first product
            By viewProduct = By.xpath(
                    "(//a[contains(@href,'/product_details/')])[1]"
            );

            wait.until(ExpectedConditions.elementToBeClickable(
                    viewProduct
            )).click();

            // 4. Verify product details page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".product-information h2")
            ));

            String productName = driver.findElement(
                    By.cssSelector(".product-information h2")
            ).getText().trim();

            Assert.assertFalse(productName.isEmpty(),
                    "Product details are not displayed");

            System.out.println("Selected Product: " + productName);

            // 5. Change quantity to 4
            WebElement quantity = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("quantity")
                    )
            );

            quantity.clear();
            quantity.sendKeys("4");

            // Verify quantity field before adding
            Assert.assertEquals(quantity.getAttribute("value"), "4",
                    "Quantity was not changed to 4");

            // 6. Click Add to Cart
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.cart")
            )).click();

            // 7. Click View Cart in confirmation modal
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//u[normalize-space()='View Cart']/ancestor::a")
            )).click();

            // 8. Wait for cart product row
            By cartRow = By.cssSelector("#cart_info_table tbody tr");

            WebElement row = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(cartRow)
            );

            // 9. Verify product name
            String cartProductName = row.findElement(
                    By.cssSelector(".cart_description h4 a")
            ).getText().trim();

            Assert.assertEquals(cartProductName, productName,
                    "Wrong product displayed in cart");

            // 10. Verify exact quantity is 4
            String cartQuantity = row.findElement(
                    By.cssSelector(".cart_quantity button")
            ).getText().trim();

            Assert.assertEquals(cartQuantity, "4",
                    "Cart quantity is not 4");

            System.out.println("Cart Product: " + cartProductName);
            System.out.println("Cart Quantity: " + cartQuantity);
            System.out.println("TC13 PASSED!");

        } finally {
            driver.quit();
        }
    }
}
