
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

public class AddProductsToCartTest {

    @Test
    public void addTwoProductsToCart() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        Actions actions = new Actions(driver);

        try {
            // 1. Launch browser and open website
            driver.get("https://automationexercise.com");
            driver.manage().window().maximize();

            // 2. Verify home page
            Assert.assertTrue(
                    driver.getTitle().contains("Automation Exercise"),
                    "Home page was not opened"
            );

            // 3. Click Products
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/products']")
            )).click();

            // 4. Wait for product cards
            By productCards =
                    By.cssSelector(".features_items .product-image-wrapper");

            wait.until(ExpectedConditions
                    .numberOfElementsToBeMoreThan(productCards, 1));

            List<WebElement> products =
                    driver.findElements(productCards);
            
//            for(WebElement product:products) {
//            	System.out.println(product.findElement(By.cssSelector(".productinfo p"))
//                        .getText().trim());
//            }

            // Capture the first two product names before adding
            String firstProductName = products.get(0)
                    .findElement(By.cssSelector(".productinfo p"))
                    .getText().trim();
            

            String secondProductName = products.get(1)
                    .findElement(By.cssSelector(".productinfo p"))
                    .getText().trim().replaceAll("\\s+", " ");

            // 5. Hover over first product and click Add to cart
            actions.moveToElement(products.get(0)).perform();

            
            products.get(0)
                    .findElement(By.cssSelector(".product-overlay a.add-to-cart"))
                    .click();

            // 6. Click Continue Shopping
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Continue Shopping')]")
            )).click();

           
            // Re-find elements in case the page DOM has changed
            products = driver.findElements(productCards);

            // 7. Hover over second product and click Add to cart
            actions.moveToElement(products.get(1)).perform();

            products.get(1)
                    .findElement(By.cssSelector(".product-overlay a.add-to-cart"))
                    .click();

            
            // 8. Click View Cart in the confirmation modal
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//u[normalize-space()='View Cart']/ancestor::a")
            )).click();

            // 9. Wait for cart rows
            By cartRows = By.cssSelector("#cart_info_table tbody tr");

            wait.until(ExpectedConditions
                    .numberOfElementsToBeMoreThan(cartRows, 1));

            List<WebElement> rows = driver.findElements(cartRows);

            Assert.assertEquals(rows.size(), 2,
                    "Cart should contain exactly two products");

            // 10. Verify product names, prices, quantities and totals
            verifyCartProduct(
                    rows.get(0), firstProductName, driver  );

            System.out.println("============First product verification is successfully completed =============");
            
            verifyCartProduct(rows.get(1), secondProductName, driver );
            
            System.out.println("============Second product verification is successfully completed =============");
            

            System.out.println("TC12 PASSED: Both products verified in cart.");

        } finally {
            driver.quit();
        }
    }

    private void verifyCartProduct(
    		
    		
            WebElement row,
            String expectedName,
            WebDriver driver) {
    	
    	

    	System.out.println("Verification starts");
        String actualName = row
                .findElement(By.cssSelector(".cart_description h4 a"))
                .getText().trim();

        String priceText = row
                .findElement(By.cssSelector(".cart_price p"))
                .getText().trim();

        String quantityText = row
                .findElement(By.cssSelector(".cart_quantity button"))
                .getText().trim();

        String totalText = row
                .findElement(By.cssSelector(".cart_total_price"))
                .getText().trim();

        // Verify product identity
        System.out.println(actualName + "---" + expectedName);
        Assert.assertEquals(actualName, expectedName,
                "Unexpected product in cart");

        // Verify quantity: each newly added product should have quantity 1
        int quantity = Integer.parseInt(quantityText);
        Assert.assertEquals(quantity, 1,
                "Product quantity should be 1");

        // Convert displayed currency values to numbers
        double price = Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
        double total = Double.parseDouble(totalText.replaceAll("[^0-9.]", ""));

        // Verify total = price x quantity
        Assert.assertEquals(total, price * quantity, 0.01,
                "Product total price is incorrect");

        System.out.println(
                "Product: " + actualName
                + " | Price: " + priceText
                + " | Quantity: " + quantity
                + " | Total: " + totalText
        );
        
        System.out.println("Verification ends");
    }
}
