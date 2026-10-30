
package com.automation.testcases;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderRegisterBeforeCheckoutTest {

    @Test
    public void placeOrderRegisterBeforeCheckout() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        Actions actions = new Actions(driver);

        String uniqueEmail = "automation"
                + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + "@example.com";

        String userName = "Automation Tester";

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

            // 4. Enter signup name and email
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[data-qa='signup-name']")
            )).sendKeys(userName);

            driver.findElement(
                    By.cssSelector("input[data-qa='signup-email']")
            ).sendKeys(uniqueEmail);

            driver.findElement(
                    By.cssSelector("button[data-qa='signup-button']")
            ).click();

            // 5. Verify account information page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//b[contains(.,'ENTER ACCOUNT INFORMATION')]")
            ));

            // 6. Fill account information
            driver.findElement(By.id("id_gender1")).click();

            driver.findElement(By.id("password"))
                    .sendKeys("TestPassword123");

            new Select(driver.findElement(By.id("days")))
                    .selectByVisibleText("10");

            new Select(driver.findElement(By.id("months")))
                    .selectByVisibleText("May");

            new Select(driver.findElement(By.id("years")))
                    .selectByVisibleText("1997");

            driver.findElement(By.id("newsletter")).click();
            driver.findElement(By.id("optin")).click();

            // 7. Fill address details
            driver.findElement(By.id("first_name"))
                    .sendKeys("Automation");

            driver.findElement(By.id("last_name"))
                    .sendKeys("Tester");

            driver.findElement(By.id("company"))
                    .sendKeys("QA Practice Company");

            driver.findElement(By.id("address1"))
                    .sendKeys("123 Test Street");

            driver.findElement(By.id("address2"))
                    .sendKeys("Test Area");

            new Select(driver.findElement(By.id("country")))
                    .selectByVisibleText("India");

            driver.findElement(By.id("state"))
                    .sendKeys("Tamil Nadu");

            driver.findElement(By.id("city"))
                    .sendKeys("Chennai");

            driver.findElement(By.id("zipcode"))
                    .sendKeys("600001");

            driver.findElement(By.id("mobile_number"))
                    .sendKeys("9876543210");

            // 8. Create account
            driver.findElement(
                    By.cssSelector("button[data-qa='create-account']")
            ).click();

            // 9. Verify ACCOUNT CREATED
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//b[contains(.,'ACCOUNT CREATED!')]")
            ));

            System.out.println("Account created successfully");

            // 10. Click Continue
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[data-qa='continue-button']")
            )).click();

            // 11. Verify logged-in username
            WebElement loggedInUser = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(.,'Logged in as')]")
                    )
            );

            Assert.assertTrue(
                    loggedInUser.getText().contains(userName),
                    "Username is not displayed"
            );

            // 12. Add first product to cart
            By productCard = By.cssSelector(
                    ".features_items .product-image-wrapper"
            );

            WebElement firstProduct = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(productCard)
            );

            actions.moveToElement(firstProduct).perform();

            firstProduct.findElement(
                    By.cssSelector(".productinfo a.add-to-cart")
            ).click();

            // 13. Click Continue Shopping
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Continue Shopping')]")
            )).click();

            // 14. Click Cart
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Cart')]")
            )).click();

            // 15. Verify cart page
            wait.until(ExpectedConditions.urlContains("view_cart"));

            Assert.assertTrue(
                    driver.getCurrentUrl().contains("view_cart"),
                    "Cart page is not displayed"
            );

            // 16. Verify cart contains a product
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#cart_info_table tbody tr")
            ));

            // 17. Proceed to checkout
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Proceed To Checkout')]")
            )).click();

            // 18. Verify delivery address
            WebElement deliveryAddress = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("address_delivery")
                    )
            );

            Assert.assertTrue(
                    deliveryAddress.getText().contains("Automation"),
                    "Delivery name is incorrect"
            );

            Assert.assertTrue(
                    deliveryAddress.getText().contains("Chennai"),
                    "Delivery city is incorrect"
            );

            // 19. Verify order review
            WebElement orderDetails = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("cart_info")
                    )
            );

            Assert.assertTrue(
                    orderDetails.isDisplayed(),
                    "Order details are not displayed"
            );

            // 20. Enter comment
            driver.findElement(By.name("message"))
                    .sendKeys("Please deliver the order carefully.");

            // 21. Click Place Order
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Place Order')]")
            )).click();

            // 22. Enter payment details
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("name_on_card")
            ));

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

            // 23. Pay and confirm order
            driver.findElement(By.id("submit")).click();

            // 24. Verify successful order
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

            // 25. Delete account
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Delete Account')]")
            )).click();

            // 26. Verify account deletion
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//b[contains(.,'ACCOUNT DELETED!')]")
            ));

            System.out.println("Account deleted successfully!");

            // 27. Click Continue
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[data-qa='continue-button']")
            )).click();

            System.out.println(
                    "TEST CASE 15 PASSED SUCCESSFULLY!"
            );

        } finally {
            driver.quit();
        }
    }
}
