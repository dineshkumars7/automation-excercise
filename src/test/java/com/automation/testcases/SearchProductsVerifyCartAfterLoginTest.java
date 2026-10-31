
package com.automation.testcases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductsVerifyCartAfterLoginTest {

    @Test
    public void searchProductsAndVerifyCartAfterLogin() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        Actions actions = new Actions(driver);

        try {

            // Step 1 & 2: Launch browser and open website
            driver.manage().window().maximize();
            driver.get("https://automationexercise.com/");

            // Step 3: Click Products
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/products']"))).click();

            // Step 4: Verify All Products page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'ALL PRODUCTS')]")));

            Assert.assertTrue(
                    driver.getCurrentUrl().contains("/products"),
                    "User is not navigated to Products page");

            // Step 5: Search product
            String searchProduct = "Blue Top";

            WebElement searchBox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("search_product")));

            searchBox.sendKeys(searchProduct);

            driver.findElement(By.id("submit_search")).click();

            // Step 6: Verify SEARCHED PRODUCTS
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'SEARCHED PRODUCTS')]")));

            // Step 7: Verify matching products are displayed
            By productCards = By.cssSelector(
                    ".features_items .product-image-wrapper");

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    productCards, 0));

            List<WebElement> cards =
                    driver.findElements(productCards);

            Assert.assertTrue(cards.size() > 0,
                    "No searched products are displayed");

            // Store product names before adding to cart
            List<String> expectedProductNames = new ArrayList<>();

            for (WebElement card : cards) {

                String productName = card.findElement(
                        By.cssSelector(".productinfo p"))
                        .getText().trim();

                Assert.assertTrue(
                        productName.toLowerCase().contains(
                                searchProduct.toLowerCase()),
                        "Unexpected product displayed: " + productName);

                expectedProductNames.add(productName);
            }

            // Step 8: Add all matching products to cart
            for (int i = 0; i < cards.size(); i++) {

                // Re-find cards to avoid stale element issues
                List<WebElement> currentCards =
                        driver.findElements(productCards);

                WebElement card = currentCards.get(i);

                actions.moveToElement(card).perform();

                WebElement addToCart = card.findElement(
                        By.cssSelector(".productinfo a.add-to-cart"));

                wait.until(ExpectedConditions.elementToBeClickable(
                        addToCart)).click();

                // Wait for confirmation modal
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".modal-content")));

                // Click Continue Shopping if more products remain
                WebElement continueShopping = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath("//button[contains(.,'Continue Shopping')]")));

                continueShopping.click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.cssSelector(".modal-content")));
            }

            // Step 9: Open Cart
            driver.findElement(By.xpath("//a[@href='/view_cart']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("cart_info_table")));

            // Verify cart contains all searched products
            List<WebElement> cartRows = driver.findElements(
                    By.cssSelector("#cart_info_table tbody tr"));

            List<String> cartProductNames = new ArrayList<>();

            for (WebElement row : cartRows) {

                String name = row.findElement(
                        By.cssSelector(".cart_description h4 a"))
                        .getText().trim();

                cartProductNames.add(name);
            }

            Assert.assertEquals(
                    cartProductNames.size(),
                    expectedProductNames.size(),
                    "Cart product count does not match searched products");

            for (String productName : expectedProductNames) {

                Assert.assertTrue(
                        cartProductNames.contains(productName),
                        "Product missing from cart: " + productName);
            }

            System.out.println(
                    "Products successfully added to cart before login.");

            // Step 10: Click Signup / Login
            driver.findElement(
                    By.xpath("//a[contains(.,'Signup / Login')]")).click();

            // Enter login details
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("email"))).sendKeys("your_email@example.com");

            driver.findElement(By.name("password"))
                    .sendKeys("your_password");

            driver.findElement(By.xpath(
                    "//button[@data-qa='login-button']")).click();

            // Verify successful login
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(.,'Logged in as')]")));

            System.out.println("Login successful.");

            // Step 11: Navigate to Cart again
            driver.findElement(
                    By.xpath("//a[@href='/view_cart']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("cart_info_table")));

            // Step 12: Verify products remain in cart after login
            List<WebElement> cartRowsAfterLogin =
                    driver.findElements(
                            By.cssSelector("#cart_info_table tbody tr"));

            List<String> cartNamesAfterLogin = new ArrayList<>();

            for (WebElement row : cartRowsAfterLogin) {

                String name = row.findElement(
                        By.cssSelector(".cart_description h4 a"))
                        .getText().trim();

                cartNamesAfterLogin.add(name);
            }

            Assert.assertEquals(
                    cartNamesAfterLogin.size(),
                    expectedProductNames.size(),
                    "Cart product count changed after login");

            for (String productName : expectedProductNames) {

                Assert.assertTrue(
                        cartNamesAfterLogin.contains(productName),
                        "Product missing after login: " + productName);
            }

            System.out.println(
                    "TC20 PASSED: All searched products remain in cart after login.");

        } finally {

            driver.quit();
        }
    }
}
