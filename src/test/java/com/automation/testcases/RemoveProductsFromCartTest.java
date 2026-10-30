
package com.automation.testcases;

import java.time.Duration;
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

public class RemoveProductsFromCartTest {

    @Test
    public void removeProductFromCart() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        Actions actions = new Actions(driver);

        try {

            // 1. Launch browser and navigate to website
            driver.get("https://automationexercise.com");
            driver.manage().window().maximize();

            // 2. Verify home page
            Assert.assertTrue(
                    driver.getTitle().contains("Automation Exercise"),
                    "Home page is not displayed"
            );

            // 3. Locate first two products
            By productCards = By.cssSelector(
                    ".features_items .product-image-wrapper"
            );

            wait.until(ExpectedConditions
                    .numberOfElementsToBeMoreThan(productCards, 1));

            List<WebElement> products =
                    driver.findElements(productCards);

            // Capture product names
            String firstProductName = products.get(0)
                    .findElement(By.cssSelector(".productinfo p"))
                    .getText().trim();

            String secondProductName = products.get(1)
                    .findElement(By.cssSelector(".productinfo p"))
                    .getText().trim();

            // 4. Add first product to cart
            actions.moveToElement(products.get(0)).perform();

            products.get(0)
                    .findElement(By.cssSelector(".productinfo a.add-to-cart"))
                    .click();

            // 5. Continue Shopping
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Continue Shopping')]")
            )).click();

            // Re-find products
            products = driver.findElements(productCards);

            // 6. Add second product to cart
            actions.moveToElement(products.get(1)).perform();

            products.get(1)
                    .findElement(By.cssSelector(".productinfo a.add-to-cart"))
                    .click();

            // 7. Continue Shopping
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Continue Shopping')]")
            )).click();

            // 8. Click Cart
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Cart')]")
            )).click();

            // 9. Verify cart page
            wait.until(ExpectedConditions.urlContains("view_cart"));

            Assert.assertTrue(
                    driver.getCurrentUrl().contains("view_cart"),
                    "Cart page is not displayed"
            );

            // 10. Verify two products are present
            By cartRows = By.cssSelector(
                    "#cart_info_table tbody tr"
            );

            wait.until(ExpectedConditions
                    .numberOfElementsToBe(cartRows, 2));

            List<WebElement> cartProducts =
                    driver.findElements(cartRows);

            Assert.assertEquals(
                    cartProducts.size(), 2,
                    "Two products should be present in cart"
            );

            // 11. Verify product names before removal
            String cartFirstProduct = cartProducts.get(0)
                    .findElement(
                            By.cssSelector(".cart_description h4 a")
                    ).getText().trim();

            String cartSecondProduct = cartProducts.get(1)
                    .findElement(
                            By.cssSelector(".cart_description h4 a")
                    ).getText().trim();

            Assert.assertEquals(cartFirstProduct, firstProductName);
            Assert.assertEquals(cartSecondProduct, secondProductName);

            // 12. Click X button for first product only
            cartProducts.get(0)
                    .findElement(
                            By.cssSelector(".cart_delete a")
                    ).click();

            // 13. Wait until first product is removed
            wait.until(ExpectedConditions
                    .numberOfElementsToBe(cartRows, 1));

            // 14. Verify remaining product
            List<WebElement> remainingProducts =
                    driver.findElements(cartRows);

            Assert.assertEquals(
                    remainingProducts.size(), 1,
                    "Only one product should remain"
            );

            String remainingProductName =
                    remainingProducts.get(0)
                    .findElement(
                            By.cssSelector(".cart_description h4 a")
                    ).getText().trim();

            Assert.assertEquals(
                    remainingProductName,
                    secondProductName,
                    "Incorrect product remains in cart"
            );

            // 15. Verify removed product is no longer displayed
            Assert.assertNotEquals(
                    remainingProductName,
                    firstProductName,
                    "Removed product is still present"
            );

            System.out.println("First Product: " + firstProductName);
            System.out.println("Second Product: " + secondProductName);
            System.out.println("Remaining Product: " + remainingProductName);

            System.out.println(
                    "TC17 PASSED: Product removed successfully!"
            );

        } finally {
            driver.quit();
        }
    }
}
