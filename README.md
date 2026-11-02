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

A total of **25 test cases** have been automated using Java, Selenium WebDriver, and TestNG on the [Automation Exercise](https://automationexercise.com/) practice website.

| Test Case | Description | Key Validation |
|---|---|---|
| TC01 | Register User | Account registration, user details validation, successful login, and account deletion |
| TC02 | Login with valid credentials and delete account | Successful login and account deletion |
| TC03 | Login with invalid credentials | Error message displayed for invalid credentials |
| TC04 | Logout user | Successful logout and redirection to login page |
| TC05 | Register with an existing email | Duplicate email error displayed |
| TC06 | Contact Us form | Form submission, file upload, and success message |
| TC07 | Verify all products and product details | Product name, category, price, availability, condition, and brand |
| TC08 | Search Product | Search functionality and verification of matching products |
| TC09 | Verify Subscription in home page | Subscription email submission and success message |
| TC10 | Verify Subscription in Cart page | Subscription functionality from the Cart page |
| TC11 | Add Products in Cart | Add multiple products and verify product details, quantity, price, and total |
| TC12 | Verify Product Quantity in Cart | Update product quantity and verify the cart quantity |
| TC13 | Place Order: Register while Checkout | Registration during checkout, payment, and successful order placement |
| TC14 | Place Order: Register before Checkout | Register before checkout and complete the purchase |
| TC15 | Place Order: Login before Checkout | Login with existing credentials and complete the purchase |
| TC16 | Remove Products From Cart | Remove selected products and verify remaining cart items |
| TC17 | View Category Products | Verify product listings under Women and Men categories |
| TC18 | View & Cart Brand Products | Verify product listings under different brands |
| TC19 | Search Products and Verify Cart After Login | Search products, add them to cart, log in, and verify cart persistence |
| TC20 | Add Review on Product | Submit a product review and verify success message |
| TC21 | Add to Cart from Recommended Items | Add recommended product to cart and verify cart contents |
| TC22 | Verify Address Details in Checkout Page | Verify delivery and billing addresses against registration details |
| TC23 | Download Invoice after Purchase Order | Complete purchase, download invoice, and verify downloaded file |
| TC24 | Verify Scroll Up using Arrow button and Scroll Down functionality | Verify scrolling down and scrolling up using the arrow button |
| TC25 | Verify Scroll Up without Arrow button and Scroll Down functionality | Verify scrolling down and scrolling up using JavaScript |

### Automation Coverage

- User Registration and Authentication
- Login and Logout Functionality
- Contact Us Form and File Upload
- Product Listing and Product Search
- Product Details and Category Validation
- Cart Management and Quantity Verification
- Checkout and Order Placement
- Payment and Order Confirmation
- Address Details Verification
- Product Review Submission
- Subscription Functionality
- Invoice Download Verification
- Page Scrolling and UI Interaction

## Technologues & Tools used
- Java
- Selenium WebDriver
- TestNG
- Maven
- Eclipse IDE
- Chrome Browser
- Git and GitHub

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
