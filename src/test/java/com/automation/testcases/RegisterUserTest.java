package com.automation.testcases;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterUserTest {

    @Test
    public void registerUser() {

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

            // Step 5: Verify New User Signup
            WebElement signupHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(.,'New User Signup!')]")));

            Assert.assertTrue(
                    signupHeading.isDisplayed(),
                    "New User Signup section is not visible");

            // Generate unique email for every execution
            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            String name = "Automation Tester";
            String email = "automation" + timestamp + "@example.com";
            String password = "Test@12345";

            // Step 6: Enter name and email
            driver.findElement(By.name("name")).sendKeys(name);

            driver.findElement(By.name("email")).sendKeys(email);

            // Step 7: Click Signup
            driver.findElement(By.xpath(
                    "//button[@data-qa='signup-button']")).click();

            // Step 8: Verify ENTER ACCOUNT INFORMATION
            WebElement accountHeading = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//*[contains(.,'ENTER ACCOUNT INFORMATION')]")));

            Assert.assertTrue(
                    accountHeading.isDisplayed(),
                    "Account information page is not displayed");

            // Step 9: Fill account information

            // Select Title
            driver.findElement(By.id("id_gender1")).click();

            // Verify name and email are already populated
            String actualName = driver.findElement(
                    By.id("name")).getAttribute("value");

            String actualEmail = driver.findElement(
                    By.id("email")).getAttribute("value");

            Assert.assertEquals(actualName, name,
                    "Name does not match");

            Assert.assertEquals(actualEmail, email,
                    "Email does not match");

            // Enter password
            driver.findElement(By.id("password"))
                    .sendKeys(password);

            // Select Date of Birth
            new Select(driver.findElement(By.id("days")))
                    .selectByVisibleText("10");

            new Select(driver.findElement(By.id("months")))
                    .selectByVisibleText("May");

            new Select(driver.findElement(By.id("years")))
                    .selectByVisibleText("1997");

            // Step 10: Newsletter checkbox
            driver.findElement(By.id("newsletter")).click();

            // Step 11: Special offers checkbox
            driver.findElement(By.id("optin")).click();

            // Step 12: Fill address information

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

            // Step 13: Click Create Account
            driver.findElement(By.xpath(
                    "//button[@data-qa='create-account']")).click();

            // Step 14: Verify ACCOUNT CREATED
            WebElement accountCreated = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(.,'ACCOUNT CREATED!')]")));

            Assert.assertTrue(
                    accountCreated.isDisplayed(),
                    "Account creation confirmation is not visible");

            System.out.println("Account created successfully.");

            // Step 15: Click Continue
            driver.findElement(By.xpath(
                    "//a[@data-qa='continue-button']")).click();

            // Step 16: Verify Logged in as username
            WebElement loggedInUser = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(.,'Logged in as')]")));

            Assert.assertTrue(
                    loggedInUser.getText().contains(name),
                    "Logged-in username is incorrect");

            System.out.println("Login verification successful.");

            // Step 17: Click Delete Account
            driver.findElement(By.xpath(
                    "//a[contains(.,'Delete Account')]")).click();

            // Step 18: Verify ACCOUNT DELETED
            WebElement accountDeleted = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(.,'ACCOUNT DELETED!')]")));

            Assert.assertTrue(
                    accountDeleted.isDisplayed(),
                    "Account deletion confirmation is not visible");

            System.out.println(
                    "TC1 PASSED: User registered and account deleted successfully.");

            // Click Continue
            driver.findElement(By.xpath(
                    "//a[@data-qa='continue-button']")).click();

        } finally {

            driver.quit();
        }
    }
}
