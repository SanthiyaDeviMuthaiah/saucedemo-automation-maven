# AI-Powered End-to-End QA Automation Framework

## Project Overview

This project demonstrates an end-to-end QA automation framework combining UI automation and REST API testing. It uses Selenium WebDriver, Java, TestNG, Maven, REST Assured, Extent Reports, and Jenkins.

The framework automates key SauceDemo user journeys and validates RESTful-Booker API operations. It also explores how AI-assisted testing can support test generation, test prioritisation, test maintenance, and failure analysis.

## Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Maven
- REST Assured
- Page Object Model (POM)
- Extent Reports
- Jenkins
- Git and GitHub
- IntelliJ IDEA

## Automated UI Test Scenarios

- Verify login with valid credentials.
- Verify login with invalid credentials.
- Verify locked-user login behaviour.
- Validate the products page and product count.
- Add a product to the shopping cart.
- Verify cart product name and quantity.
- Remove a product from the cart.
- Validate checkout information.
- Complete checkout and verify order confirmation.

## Automated API Test Scenarios

- Create and retrieve a booking.
- Update booking details using PUT.
- Partially update booking details using PATCH.
- Delete a booking and verify the response.
- Validate HTTP status codes and response headers.
- Verify error responses for invalid or deleted booking IDs.

## Framework Structure

```text
saucedemo-automation-maven/
├── pom.xml
├── .gitignore
├── README.md
├── AI_Testing_Case_Study.md
├── testng.xml
└── src/
    └── test/
        └── java/
            ├── LoginTest.java
            ├── ApiTest.java
            ├── pages/
            └── utils/
```

## Test Reporting

Extent Reports are used to provide a readable summary of automated test execution. The generated report can be opened in a browser to review test results.

## CI/CD Integration

The project is integrated with Jenkins using the GitHub repository. Jenkins checks out the source code and executes the Maven test suite.

## AI Testing Case Study

The accompanying `AI_Testing_Case_Study.md` discusses potential uses of AI in test-case generation, risk-based test prioritisation, defect prediction, test maintenance, and failure analysis.

These are described as AI-assisted approaches; they should not be interpreted as implemented AI functionality unless it is explicitly added to the framework.

## Prerequisites

- Java JDK
- Maven
- Google Chrome
- IntelliJ IDEA or another Java IDE
- Jenkins, if running the CI/CD job locally

## How to Run the Tests

Clone the repository:

```bash
git clone https://github.com/SanthiyaDeviMuthaiah/saucedemo-automation-maven.git
cd saucedemo-automation-maven
```

Run the complete test suite using Maven:

```bash
mvn clean test
```

Alternatively, open the project in IntelliJ IDEA, allow Maven dependencies to load, and run the configured TestNG suite.

## Applications Under Test

- SauceDemo: https://www.saucedemo.com/
- Restful-Booker: https://restful-booker.herokuapp.com/

## Author

Santhiya Devi Muthaiah

QA Automation Engineer | Selenium with Java | API Testing
