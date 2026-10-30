
package com.automation.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderLoginBeforeCheckoutTest {

    @Test
    public void placeOrderLoginBeforeCheckout() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        Actions actions = new Actions(driver);

        // Replace these with an existing account's credentials
        String email = "tonystark712@gmail.com";
        String password = "Tonystark@712";
        String userName = "tonystark712";

        try {

            // 1. Launch browser and open website
            driver.get("https://automationexercise.com");
            driver.manage().window().maximize();

            // 2. Verify home page
            Assert.assertTrue(
                    driver.getTitle().contains("Automation Exercise"),
                    "Home page is not displayed"
            );

            // 3. Click Signup / Login
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Signup / Login')]")
            )).click();

            // 4. Enter existing email and password
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[data-qa='login-email']")
            )).sendKeys(email);

            driver.findElement(
                    By.cssSelector("input[data-qa='login-password']")
            ).sendKeys(password);

            // 5. Click Login
            driver.findElement(
                    By.cssSelector("button[data-qa='login-button']")
            ).click();

            // 6. Verify logged-in username
            WebElement loggedInUser = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(.,'Logged in as')]")
                    )
            );

            Assert.assertTrue(
                    loggedInUser.getText().contains(userName),
                    "Login username is incorrect"
            );

            System.out.println("Login successful!");

            // 7. Find first product
            By productCard = By.cssSelector(
                    ".features_items .product-image-wrapper"
            );

            WebElement firstProduct = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            productCard
                    )
            );

            // 8. Hover over product and click Add to Cart
            actions.moveToElement(firstProduct).perform();

            firstProduct.findElement(
                    By.cssSelector(".productinfo a.add-to-cart")
            ).click();

            // 9. Click Continue Shopping
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Continue Shopping')]")
            )).click();

            // 10. Click Cart
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Cart')]")
            )).click();

            // 11. Verify cart page
            wait.until(ExpectedConditions.urlContains("view_cart"));

            Assert.assertTrue(
                    driver.getCurrentUrl().contains("view_cart"),
                    "Cart page is not displayed"
            );

            // 12. Verify product exists in cart
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#cart_info_table tbody tr")
            ));

            System.out.println("Product added to cart successfully!");

            // 13. Proceed to Checkout
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Proceed To Checkout')]")
            )).click();

            // 14. Verify delivery address
            WebElement deliveryAddress = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("address_delivery")
                    )
            );

            Assert.assertTrue(
                    deliveryAddress.isDisplayed(),
                    "Delivery address is not displayed"
            );

            // 15. Verify order details
            WebElement orderDetails = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("cart_info")
                    )
            );

            Assert.assertTrue(
                    orderDetails.isDisplayed(),
                    "Order details are not displayed"
            );

            // 16. Enter comment
            driver.findElement(By.name("message"))
                    .sendKeys("Please deliver the order carefully.");

            // 17. Click Place Order
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Place Order')]")
            )).click();

            // 18. Wait for payment page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("name_on_card")
            ));

            // 19. Enter payment details
            driver.findElement(By.name("name_on_card"))
                    .sendKeys("Automation Tester");

            driver.findElement(By.name("card_number"))
                    .sendKeys("4111111111111111");

            driver.findElement(By.name("cvc"))
                    .sendKeys("123");

            driver.findElement(By.name("expiry_month"))
                    .sendKeys("12");

            driver.findElement(By.name("expiry_year"))
                    .sendKeys("2030");

            // 20. Pay and confirm order
            driver.findElement(By.id("submit")).click();

            // 21. Verify order success message
            WebElement successMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                "//*[contains(.,'Your order has been placed successfully!')]"
                            )
                    )
            );

            Assert.assertTrue(
                    successMessage.isDisplayed(),
                    "Order success message is not displayed"
            );

            System.out.println("Order placed successfully!");

            // 22. Delete Account
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Delete Account')]")
            )).click();

            // 23. Verify account deleted
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//b[contains(.,'ACCOUNT DELETED!')]")
            ));

            System.out.println("Account deleted successfully!");

            // 24. Click Continue
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[data-qa='continue-button']")
            )).click();

            System.out.println(
                    "TEST CASE 16 PASSED SUCCESSFULLY!"
            );

        } finally {
            driver.quit();
        }
    }
}
