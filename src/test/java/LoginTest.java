
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

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);
    }

    @Test
    public void invalidLoginTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();

        Assert.assertTrue(
                actualError.contains(
                        "Username and password do not match"
                ),
                "Expected login error message was not displayed."
        );
    }
    @Test
    public void validLoginTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Wait for the Products page title
        boolean productsVisible = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("title")
                )
        ).getText().equals("Products");

        Assert.assertTrue(
                productsVisible,
                "Login failed: Products page title was not displayed."
        );
    }

    @Test
    public void productCountTest() {
        // Login
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Create ProductsPage object
        ProductsPage productsPage = new ProductsPage(driver);

        // Verify product count
        int actualProductCount = wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .visibilityOfElementLocated(
                                By.className("inventory_item")
                        )
        ) != null ? productsPage.getProductCount() : 0;

        Assert.assertEquals(
                actualProductCount,
                6,
                "Expected 6 products to be displayed."
        );
    }

    @Test
    public void addProductToCartTest() {

        // Login
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Wait for products to load
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_item")
                )
        );

        // Create ProductsPage object
        ProductsPage productsPage = new ProductsPage(driver);

        // Add one product to the cart
        productsPage.addProductToCart("Sauce Labs Backpack");

        // Get cart quantity
        int actualCartCount = productsPage.getCartCount();

        // Verify expected quantity is 1
        Assert.assertEquals(
                actualCartCount,
                1,
                "Expected cart quantity to be 1."
        );
    }



    @Test
    public void verifyCartProductTest() {

        // Step 1: Login
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Step 2: Wait for products to load
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_item")
                )
        );

        // Step 3: Create ProductsPage object
        ProductsPage productsPage = new ProductsPage(driver);

        // Step 4: Add one product
        productsPage.addProductToCart("Sauce Labs Backpack");

        // Step 5: Open the cart
        productsPage.clickShoppingCart();

        // Step 6: Verify product name
        String actualProductName = productsPage.getCartProductName();

        Assert.assertEquals(
                actualProductName,
                "Sauce Labs Backpack",
                "Product name does not match."
        );

        // Step 7: Verify product quantity
        String actualQuantity = productsPage.getCartProductQuantity();

        Assert.assertEquals(
                actualQuantity,
                "1",
                "Expected cart item quantity to be 1."
        );
    }
    @Test
    public void removeProductFromCartTest() {

        // Step 1: Login
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Step 2: Wait for products page
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_item")
                )
        );

        // Step 3: Add a product
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart("Sauce Labs Backpack");

        // Step 4: Open the cart
        productsPage.clickShoppingCart();

        // Step 5: Remove the product
        productsPage.removeProductFromCart("Sauce Labs Backpack");

        // Step 6: Verify the cart is empty
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.className("cart_item")
                )
        );

        int actualCount = productsPage.getCartItemCount();

        Assert.assertEquals(
                actualCount,
                0,
                "Cart should be empty after removing the product."
        );
    }


    @Test
    public void verifyCartBadgeDisappearsTest() {

        // Step 1: Login
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Step 2: Wait for the products page
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_item")
                )
        );

        // Step 3: Add a product
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart("Sauce Labs Backpack");

        // Step 4: Verify the badge is displayed
        Assert.assertTrue(
                productsPage.isCartBadgeDisplayed(),
                "Cart badge should be displayed after adding a product."
        );

        // Step 5: Open the cart and remove the product
        productsPage.clickShoppingCart();
        productsPage.removeProductFromCart("Sauce Labs Backpack");

        // Step 6: Verify the badge disappears
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.className("shopping_cart_badge")
                )
        );

        Assert.assertFalse(
                productsPage.isCartBadgeDisplayed(),
                "Cart badge should disappear after removing the last product."
        );
    }

    @Test
    public void checkoutInformationTest() {

        // Step 1: Login
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Step 2: Wait for the Products page
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_item")
                )
        );

        // Step 3: Add a product and open the cart
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.clickShoppingCart();

        // Step 4: Click Checkout
        productsPage.clickCheckout();

        // Step 5: Enter checkout information
        productsPage.enterFirstName("Santhiya");
        productsPage.enterLastName("Devi");
        productsPage.enterPostalCode("600001");

        // Step 6: Continue
        productsPage.clickContinue();

        // Step 7: Verify the checkout overview page
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("title")
                )
        );

        String actualTitle = productsPage.getPageTitle();

        Assert.assertEquals(
                actualTitle,
                "Checkout: Overview",
                "Checkout overview page was not displayed."
        );
    }


    @Test
    public void completeOrderTest() {

        // Step 1: Login
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Step 2: Wait for the Products page
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_item")
                )
        );

        // Step 3: Add a product and open the cart
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.clickShoppingCart();

        // Step 4: Open checkout
        productsPage.clickCheckout();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("first-name")
                )
        );

        // Step 5: Enter checkout information
        productsPage.enterFirstName("Santhiya");
        productsPage.enterLastName("Devi");
        productsPage.enterPostalCode("600001");
        productsPage.clickContinue();

        // Step 6: Verify checkout overview
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("finish")
                )
        );

        Assert.assertEquals(
                productsPage.getPageTitle(),
                "Checkout: Overview",
                "Checkout overview page was not displayed."
        );

        // Step 7: Complete the order
        productsPage.clickFinish();

        // Step 8: Verify order confirmation
        String actualMessage =
                productsPage.getOrderConfirmationMessage();

        Assert.assertEquals(
                actualMessage,
                "Thank you for your order!",
                "Order confirmation message does not match."
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}