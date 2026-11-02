
package com.automation.testcases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartFromRecommendedItemsTest {

    @Test
    public void addToCartFromRecommendedItems() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        Actions actions = new Actions(driver);

        try {

            // Step 1 & 2: Launch browser and open website
            driver.manage().window().maximize();
            driver.get("https://automationexercise.com/");

            // Step 3: Scroll to bottom of page
            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript(
                    "window.scrollTo(0, document.body.scrollHeight)");

            // Step 4: Verify RECOMMENDED ITEMS are visible
            WebElement recommendedHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//*[contains(translate(.,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'RECOMMENDED ITEMS')]")));

            Assert.assertTrue(
                    recommendedHeading.isDisplayed(),
                    "Recommended Items section is not visible");

            // Locate recommended product cards
            By recommendedProducts = By.cssSelector(
                    ".recommended_items .item");

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    recommendedProducts, 0));

            List<WebElement> products =
                    driver.findElements(recommendedProducts);

            Assert.assertTrue(
                    products.size() > 0,
                    "No recommended products are displayed");

            // Select the first recommended product
            WebElement firstProduct = products.get(0);

            // Capture product name before adding to cart
            String expectedProductName = firstProduct.findElement(
                    By.cssSelector(".productinfo p"))
                    .getText().trim();

            // Step 5: Click Add To Cart
            WebElement addToCartButton = firstProduct.findElement(
                    By.cssSelector("a.add-to-cart"));

            actions.moveToElement(addToCartButton).perform();

            wait.until(ExpectedConditions.elementToBeClickable(
                    addToCartButton)).click();

            // Wait for confirmation modal
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".modal-content")));

            // Step 6: Click View Cart
            WebElement viewCartButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//a[contains(.,'View Cart')]")));

            viewCartButton.click();

            // Step 7: Verify cart page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("cart_info_table")));

            // Get product names from cart
            List<WebElement> cartProducts = driver.findElements(
                    By.cssSelector(
                            "#cart_info_table .cart_description h4 a"));

            boolean productFound = false;

            for (WebElement cartProduct : cartProducts) {

                String actualProductName =
                        cartProduct.getText().trim();

                if (actualProductName.equals(expectedProductName)) {
                    productFound = true;
                    break;
                }
            }

            Assert.assertTrue(
                    productFound,
                    "Recommended product was not found in cart: "
                            + expectedProductName);

            System.out.println(
                    "TC22 PASSED: Recommended product successfully added to cart.");

            System.out.println(
                    "Product Name: " + expectedProductName);

        } finally {

            driver.quit();
        }
    }
}
