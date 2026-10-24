
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.ProductsPage;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    private void loginSuccessfully() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }
    @Test
    public void invalidLoginTest() {
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLogin();

        By errorMessage = By.cssSelector("[data-test='error']");

        String actualError = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        errorMessage)).getText();

        Assert.assertTrue(
                actualError.contains(
                        "Username and password do not match"),
                "Expected an invalid-login error.");
    }

    @Test
    public void validLoginTest() {
        loginSuccessfully();

        Assert.assertEquals(
                productsPage.getProductsTitle(),
                "Products");
    }

    @Test
    public void productCountTest() {
        loginSuccessfully();

        int productCount = driver.findElements(
                By.className("inventory_item")).size();

        Assert.assertEquals(
                productCount, 6,
                "Expected six products.");
    }

    @Test
    public void addProductToCartTest() {
        loginSuccessfully();

        productsPage.addBackpackToCart();

        Assert.assertTrue(
                productsPage.isCartBadgeDisplayed(),
                "Cart badge should appear after adding a product.");

        Assert.assertEquals(
                driver.findElement(
                        By.className("shopping_cart_badge")).getText(),
                "1");
    }

    @Test
    public void verifyCartProductAndQuantityTest() {
        loginSuccessfully();

        productsPage.addBackpackToCart();
        productsPage.openCart();

        Assert.assertEquals(
                productsPage.getCartItemName(),
                "Sauce Labs Backpack");

        Assert.assertEquals(
                productsPage.getCartQuantity(),
                "1");
    }

    @Test
    public void removeProductFromCartTest() {
        loginSuccessfully();

        productsPage.addBackpackToCart();
        productsPage.openCart();
        productsPage.removeBackpackFromCart();

        Assert.assertFalse(
                productsPage.isCartBadgeDisplayed(),
                "Cart badge should disappear after removing the item.");
    }

    @Test
    public void checkoutInformationTest() {
        loginSuccessfully();

        productsPage.addBackpackToCart();
        productsPage.openCart();
        productsPage.clickCheckout();

        productsPage.enterFirstName("Santhiya");
        productsPage.enterLastName("Devi");
        productsPage.enterPostalCode("600001");

        // Verify the fields before clicking Continue.
        Assert.assertEquals(
                productsPage.getFirstNameValue(),
                "Santhiya",
                "First name was not entered correctly.");

        Assert.assertEquals(
                productsPage.getLastNameValue(),
                "Devi",
                "Last name was not entered correctly.");

        Assert.assertEquals(
                productsPage.getPostalCodeValue(),
                "600001",
                "Postal code was not entered correctly.");

        productsPage.clickContinue();

        Assert.assertEquals(
                productsPage.getCheckoutOverviewTitle(),
                "Checkout: Overview",
                "Checkout did not navigate to the overview page.");
    }

    @Test
    public void completeOrderTest() {
        loginSuccessfully();

        productsPage.addBackpackToCart();
        productsPage.openCart();
        productsPage.clickCheckout();

        productsPage.enterFirstName("Santhiya");
        productsPage.enterLastName("Devi");
        productsPage.enterPostalCode("600001");

        // Verify all field values before continuing.
        Assert.assertEquals(
                productsPage.getFirstNameValue(),
                "Santhiya");

        Assert.assertEquals(
                productsPage.getLastNameValue(),
                "Devi");

        Assert.assertEquals(
                productsPage.getPostalCodeValue(),
                "600001");
        System.out.println(
                "First name: " + productsPage.getFirstNameValue());

        System.out.println(
                "Last name: " + productsPage.getLastNameValue());

        System.out.println(
                "Postal code: " + productsPage.getPostalCodeValue());

        productsPage.clickContinue();

        Assert.assertEquals(
                productsPage.getCheckoutOverviewTitle(),
                "Checkout: Overview");

        productsPage.clickFinish();

        Assert.assertEquals(
                productsPage.getCompleteOrderMessage(),
                "Thank you for your order!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
