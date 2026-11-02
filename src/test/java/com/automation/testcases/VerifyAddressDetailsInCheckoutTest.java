package com.automation.testcases;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyAddressDetailsInCheckoutTest {

    @Test
    public void verifyAddressDetailsInCheckout() {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        try {

            // Step 1 & 2: Launch browser and open website
            driver.manage().window().maximize();
            driver.get("https://automationexercise.com/");

            // Step 3: Verify homepage
            Assert.assertTrue(
                    driver.getTitle().contains("Automation Exercise"),
                    "Home page is not displayed");

            // Step 4: Click Signup / Login
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Signup / Login')]"))).click();

            // Generate unique email for every execution
            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            String email = "testuser" + timestamp + "@example.com";

            // Step 5: Enter signup name and email
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("name"))).sendKeys("Automation Tester");

            driver.findElement(By.name("email")).sendKeys(email);

            driver.findElement(By.xpath(
                    "//button[@data-qa='signup-button']")).click();

            // Account Information
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("id_gender1"))).click();

            driver.findElement(By.id("password"))
                    .sendKeys("Test@12345");

            new Select(driver.findElement(By.id("days")))
                    .selectByVisibleText("10");

            new Select(driver.findElement(By.id("months")))
                    .selectByVisibleText("May");

            new Select(driver.findElement(By.id("years")))
                    .selectByVisibleText("1997");

            driver.findElement(By.id("newsletter")).click();
            driver.findElement(By.id("optin")).click();

            // Address details
            String firstName = "Automation";
            String lastName = "Tester";
            String company = "ABC Technologies";
            String address1 = "No. 10, Test Street";
            String address2 = "Near Bus Stand";
            String country = "India";
            String state = "Tamil Nadu";
            String city = "Chennai";
            String zipcode = "600001";
            String mobile = "9876543210";

            driver.findElement(By.id("first_name"))
                    .sendKeys(firstName);

            driver.findElement(By.id("last_name"))
                    .sendKeys(lastName);

            driver.findElement(By.id("company"))
                    .sendKeys(company);

            driver.findElement(By.id("address1"))
                    .sendKeys(address1);

            driver.findElement(By.id("address2"))
                    .sendKeys(address2);

            new Select(driver.findElement(By.id("country")))
                    .selectByVisibleText(country);

            driver.findElement(By.id("state"))
                    .sendKeys(state);

            driver.findElement(By.id("city"))
                    .sendKeys(city);

            driver.findElement(By.id("zipcode"))
                    .sendKeys(zipcode);

            driver.findElement(By.id("mobile_number"))
                    .sendKeys(mobile);

            // Create account
            driver.findElement(By.xpath(
                    "//button[@data-qa='create-account']")).click();

            // Step 6: Verify ACCOUNT CREATED
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'ACCOUNT CREATED!')]")));

            Assert.assertTrue(
                    driver.getPageSource().contains("ACCOUNT CREATED!"),
                    "Account creation failed");

            driver.findElement(By.xpath(
                    "//a[@data-qa='continue-button']")).click();

            // Step 7: Verify logged-in username
            WebElement loggedInUser = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(.,'Logged in as')]")));

            Assert.assertTrue(
                    loggedInUser.getText().contains("Automation Tester"),
                    "Logged-in username is incorrect");

            // Step 8: Add first product to cart
            By firstProduct = By.cssSelector(
                    ".features_items .product-image-wrapper");

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    firstProduct, 0));

            WebElement product = driver.findElements(firstProduct).get(0);

            product.findElement(
                    By.cssSelector(".productinfo a.add-to-cart")).click();

            // Wait for confirmation modal
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".modal-content")));

            // Continue Shopping
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Continue Shopping')]")))
                    .click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(".modal-content")));

            // Step 9: Click Cart
            driver.findElement(
                    By.xpath("//a[@href='/view_cart']")).click();

            // Step 10: Verify Cart page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("cart_info_table")));

            Assert.assertTrue(
                    driver.getCurrentUrl().contains("/view_cart"),
                    "Cart page is not displayed");

            // Step 11: Proceed to Checkout
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Proceed To Checkout')]")))
                    .click();

            // Wait for checkout address sections
            WebElement deliveryAddress = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("address_delivery")));

            WebElement billingAddress = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("address_invoice")));

            // Step 12: Verify Delivery Address
            verifyAddress(
                    deliveryAddress,
                    firstName, lastName, company,
                    address1, address2, city, state,
                    zipcode, country, mobile);

            System.out.println("Delivery address verified successfully.");

            // Step 13: Verify Billing Address
            verifyAddress(
                    billingAddress,
                    firstName, lastName, company,
                    address1, address2, city, state,
                    zipcode, country, mobile);

            System.out.println("Billing address verified successfully.");

            // Step 14: Delete Account
            driver.findElement(
                    By.xpath("//a[contains(.,'Delete Account')]")).click();

            // Step 15: Verify ACCOUNT DELETED
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'ACCOUNT DELETED!')]")));

            Assert.assertTrue(
                    driver.getPageSource().contains("ACCOUNT DELETED!"),
                    "Account deletion failed");

            driver.findElement(By.xpath(
                    "//a[@data-qa='continue-button']")).click();

            System.out.println(
                    "TC23 PASSED: Delivery and billing addresses match registration details.");

        } finally {

            driver.quit();
        }
    }

    // Reusable method to validate address details
    public void verifyAddress(
            WebElement addressBlock,
            String firstName,
            String lastName,
            String company,
            String address1,
            String address2,
            String city,
            String state,
            String zipcode,
            String country,
            String mobile) {

        String actualAddress = addressBlock.getText();

        List<String> expectedDetails = Arrays.asList(
                firstName + " " + lastName,
                company,
                address1,
                address2,
                city + " " + state + " " + zipcode,
                country,
                mobile
        );

        for (String expected : expectedDetails) {

            Assert.assertTrue(
                    actualAddress.contains(expected),
                    "Address detail missing or incorrect: " + expected
                            + "\nActual address:\n" + actualAddress);
        }
    }
}
