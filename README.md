
# AI-Powered End-to-End QA Automation Framework

## Project Overview
This project demonstrates end-to-end UI test automation using Selenium WebDriver, Java, TestNG, and Maven. It automates key user journeys on the SauceDemo demo application, including login, product selection, cart validation, and checkout.

The project is being developed as part of my QA Automation/SDET learning and portfolio.

## Technologies Used
- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model (POM)
- Git and GitHub
- IntelliJ IDEA

## Automated Test Scenarios
- Verify login with valid credentials.
- Verify login with invalid credentials.
- Validate the products page and product count.
- Add a product to the shopping cart.
- Verify the cart product name and quantity.
- Remove a product from the cart.
- Validate checkout information.
- Complete checkout and verify the order confirmation.

## Framework Structure

```text
saucedemo-automation-maven/
├── pom.xml
├── .gitignore
├── README.md
└── src/
    └── test/
        └── java/
            ├── LoginTest.java
            └── pages/
                ├── LoginPage.java
                └── ProductsPage.java
```

## Prerequisites
- Java JDK
- Maven
- Google Chrome
- IntelliJ IDEA or another Java IDE

## How to Run the Tests

1. Clone this repository:

   ```bash
   git clone https://github.com/SanthiyaDeviMuthaiah/saucedemo-automation-maven.git
   ```

2. Open the project in IntelliJ IDEA.
3. Ensure Maven dependencies are downloaded.
4. Run the TestNG test class `LoginTest.java`.

Alternatively, run the tests from the project root using:

```bash
mvn test
```

## Application Under Test
SauceDemo: https://www.saucedemo.com/

## Future Enhancements
- Integrate API automation using REST Assured.
- Add AI-assisted test case generation and prioritization.
- Integrate automated test execution with Jenkins CI/CD.
- Improve test reporting and failure diagnostics.

## Author
Santhiya Devi Muthaiah

QA Automation Engineer | Selenium with Java | API Testing
