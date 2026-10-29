# Automation Exercise - Selenium Automation Testing

## Project Overview
This project contains automated test cases for the [Automation Exercise](https://automationexercise.com/) practice website using Java, Selenium WebDriver, TestNG, and Maven.

The goal is to practise web application testing by automating user workflows, validating UI elements, and verifying expected application behaviour.

## Tech Stack
- **Language:** Java
- **Automation Tool:** Selenium WebDriver
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **IDE:** Eclipse
- **Browser:** Google Chrome
- **Version Control:** Git and GitHub

## Test Cases Automated

| Test Case | Description | Key Validation |
|---|---|---|
| TC02 | Login with valid credentials and delete account | Successful login and account deletion |
| TC03 | Login with invalid credentials | Error message displayed |
| TC04 | Logout user | Redirected to login page |
| TC05 | Register with an existing email | Duplicate email error displayed |
| TC06 | Contact Us form | Form submission, file upload, success message |
| TC08 | Verify all products and product details | Product name, category, price, availability, condition, and brand |

## Project Structure

```text
automation-exercise/
├── src/
│   ├── main/
│   └── test/
├── pom.xml
├── testng.xml
└── README.md
```

## Prerequisites
- Java JDK installed
- Eclipse IDE
- Maven configured
- Google Chrome browser
- Selenium and TestNG dependencies configured in `pom.xml`

## How to Run
1. Clone or download this repository.
2. Import the project into Eclipse as an existing Maven project.
3. Update the test credentials where required.
4. Ensure any required test files and file paths are configured correctly.
5. Run an individual test class using **Run As → TestNG Test**, or execute the configured TestNG suite.

## Skills Practised
- Locating web elements using XPath and CSS selectors
- Using `findElement()` and `findElements()`
- Handling explicit waits with `WebDriverWait`
- Validating expected results with TestNG assertions
- Handling JavaScript alerts
- Uploading files through Selenium
- Navigating between web pages
- Verifying product information
- Organising automated tests using Java and TestNG

## Disclaimer
This project is intended for learning and practice. Tests that submit forms or delete accounts should use appropriate test data and disposable test accounts.

## Author
Created as part of my hands-on learning journey in Selenium automation testing.
