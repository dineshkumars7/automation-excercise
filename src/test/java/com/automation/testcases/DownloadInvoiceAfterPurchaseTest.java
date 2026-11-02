package com.automation.testcases;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DownloadInvoiceAfterPurchaseTest {

    @Test
    public void downloadInvoiceAfterPurchase() throws Exception {

        // Configure download folder
        Path downloadDir = Paths.get(
                System.getProperty("user.dir"),
                "downloads", "TC24");

        Files.createDirectories(downloadDir);

        // Remove old files so we can verify this execution's download
        try (Stream<Path> files = Files.list(downloadDir)) {
            files.forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        // Chrome download settings
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");

        options.setExperimentalOption("prefs", java.util.Map.of(
                "download.default_directory",
                downloadDir.toAbsolutePath().toString(),
                "download.prompt_for_download", false,
                "download.directory_upgrade", true,
                "plugins.always_open_pdf_externally", true
        ));

        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        try {

            // Step 1 & 2: Launch browser and open website
            driver.get("https://automationexercise.com/");

            // Step 3: Verify homepage
            Assert.assertTrue(
                    driver.getTitle().contains("Automation Exercise"),
                    "Home page is not displayed");

            // Step 4: Add first product to cart
            By productCards = By.cssSelector(
                    ".features_items .product-image-wrapper");

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    productCards, 0));

            WebElement firstProduct =
                    driver.findElements(productCards).get(0);

            new Actions(driver).moveToElement(firstProduct).perform();

            firstProduct.findElement(
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

            // Step 5: Click Cart
            driver.findElement(
                    By.xpath("//a[@href='/view_cart']")).click();

            // Step 6: Verify Cart page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("cart_info_table")));

            Assert.assertTrue(
                    driver.getCurrentUrl().contains("/view_cart"),
                    "Cart page is not displayed");

            // Step 7: Proceed to Checkout
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Proceed To Checkout')]")))
                    .click();

            // Step 8: Click Register / Login
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Register / Login')]")))
                    .click();

            // Generate unique email
            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            String email = "invoiceuser" + timestamp + "@example.com";

            // Step 9: Signup
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("name"))).sendKeys("Invoice Tester");

            driver.findElement(By.name("email")).sendKeys(email);

            driver.findElement(By.xpath(
                    "//button[@data-qa='signup-button']")).click();

            // Account information
            wait.until(ExpectedConditions.elementToBeClickable(
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
            driver.findElement(By.id("first_name"))
                    .sendKeys("Invoice");

            driver.findElement(By.id("last_name"))
                    .sendKeys("Tester");

            driver.findElement(By.id("company"))
                    .sendKeys("ABC Technologies");

            driver.findElement(By.id("address1"))
                    .sendKeys("No. 10, Test Street");

            driver.findElement(By.id("address2"))
                    .sendKeys("Near Bus Stand");

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

            // Create account
            driver.findElement(By.xpath(
                    "//button[@data-qa='create-account']")).click();

            // Step 10: Verify ACCOUNT CREATED
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'ACCOUNT CREATED!')]")));

            Assert.assertTrue(
                    driver.getPageSource().contains("ACCOUNT CREATED!"),
                    "Account creation failed");

            driver.findElement(By.xpath(
                    "//a[@data-qa='continue-button']")).click();

            // Step 11: Verify logged-in username
            WebElement loggedInUser = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(.,'Logged in as')]")));

            Assert.assertTrue(
                    loggedInUser.getText().contains("Invoice Tester"),
                    "Login verification failed");

            // Step 12: Go to Cart
            driver.findElement(
                    By.xpath("//a[@href='/view_cart']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("cart_info_table")));

            // Step 13: Proceed to Checkout
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Proceed To Checkout')]")))
                    .click();

            // Step 14: Verify address and order review
            WebElement deliveryAddress = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("address_delivery")));

            Assert.assertTrue(
                    deliveryAddress.getText().contains("Invoice Tester"),
                    "Delivery address is incorrect");

            WebElement orderReview = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("cart_info")));

            Assert.assertTrue(
                    orderReview.isDisplayed(),
                    "Order review is not displayed");

            // Step 15: Enter comment and Place Order
            driver.findElement(By.name("message"))
                    .sendKeys("Please deliver the order safely.");

            driver.findElement(By.xpath(
                    "//a[contains(.,'Place Order')]")).click();

            // Step 16: Enter payment details
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("name_on_card")))
                    .sendKeys("Invoice Tester");

            driver.findElement(By.name("card_number"))
                    .sendKeys("4111111111111111");

            driver.findElement(By.name("cvc"))
                    .sendKeys("123");

            driver.findElement(By.name("expiry_month"))
                    .sendKeys("12");

            driver.findElement(By.name("expiry_year"))
                    .sendKeys("2030");

            // Step 17: Pay and Confirm Order
            driver.findElement(By.id("submit")).click();

            // Step 18: Verify order success
            WebElement orderSuccess = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                "//*[contains(.,'Your order has been placed successfully!') "
                                + "or contains(.,'Your order has been confirmed!')]")));

            Assert.assertTrue(
                    orderSuccess.isDisplayed(),
                    "Order confirmation message is not displayed");

            System.out.println("Order placed successfully.");

            // Step 19: Download Invoice
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Download Invoice')]")))
                    .click();

            // Verify invoice file is downloaded
            Path downloadedFile = waitForDownloadedFile(downloadDir);

            Assert.assertNotNull(
                    downloadedFile,
                    "Invoice file was not downloaded");

            Assert.assertTrue(
                    Files.exists(downloadedFile),
                    "Invoice file does not exist");

            Assert.assertTrue(
                    Files.size(downloadedFile) > 0,
                    "Downloaded invoice is empty");

            System.out.println(
                    "Invoice downloaded successfully: "
                            + downloadedFile.getFileName());

            // Step 20: Click Continue
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Continue')]")))
                    .click();

            // Step 21: Delete Account
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Delete Account')]")))
                    .click();

            // Step 22: Verify ACCOUNT DELETED
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(.,'ACCOUNT DELETED!')]")));

            Assert.assertTrue(
                    driver.getPageSource().contains("ACCOUNT DELETED!"),
                    "Account deletion failed");

            driver.findElement(By.xpath(
                    "//a[@data-qa='continue-button']")).click();

            System.out.println(
                    "TC24 PASSED: Order placed, invoice downloaded and account deleted.");

        } finally {

            driver.quit();
        }
    }

    // Wait until a downloaded file appears and its temporary
    // Chrome download extension disappears.
    private Path waitForDownloadedFile(Path directory)
            throws Exception {

        long endTime = System.currentTimeMillis() + 30000;

        while (System.currentTimeMillis() < endTime) {

            try (Stream<Path> files = Files.list(directory)) {

                List<Path> downloadedFiles = files
                        .filter(Files::isRegularFile)
                        .filter(path -> !path.getFileName()
                                .toString().endsWith(".crdownload"))
                        .toList();

                if (!downloadedFiles.isEmpty()) {

                    Path file = downloadedFiles.get(0);

                    if (Files.size(file) > 0) {
                        return file;
                    }
                }
            }

            Thread.sleep(500);
        }

        return null;
    }
}
