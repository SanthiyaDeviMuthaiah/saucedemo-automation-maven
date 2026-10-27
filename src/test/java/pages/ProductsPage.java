
package pages;

import java.time.Duration;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Products page
    private final By productsTitle = By.className("title");
    private final By addBackpackButton =
            By.id("add-to-cart-sauce-labs-backpack");
    private final By removeBackpackButton =
            By.id("remove-sauce-labs-backpack");

    // Cart
    private final By cartButton = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartItem = By.className("inventory_item_name");
    private final By cartQuantity = By.className("cart_quantity");

    // Checkout information
    private final By checkoutButton = By.id("checkout");
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");

    // Order completion
    private final By finishButton = By.id("finish");
    private final By completeOrderMessage =
            By.className("complete-header");


    public void enterFirstName(String firstName) {
        enterText(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        enterText(lastNameField, lastName);
    }

    public void enterPostalCode(String postalCode) {
        enterText(postalCodeField, postalCode);
    }

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    // Products page title
    public String getProductsTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productsTitle)).getText();
    }

    // Add backpack to cart

    // Add backpack to cart

    // Add backpack to cart and verify the button state

    public void addBackpackToCart() {
        WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(addBackpackButton)
        );

        addButton.click();

        // Wait until the cart badge shows quantity 1
        WebElement badge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartBadge)
        );

        wait.until(driver ->
                "1".equals(driver.findElement(cartBadge).getText())
        );

        System.out.println("Cart quantity: " + badge.getText());

        Assert.assertEquals(
                badge.getText(),
                "1",
                "Cart quantity should be 1 after adding the backpack."
        );
    }

    // Remove backpack from cart

    public void removeBackpackFromCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(removeBackpackButton)
        ).click();

        // Wait until the cart badge is no longer visible.
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(cartBadge)
        );

        // Confirm that the cart page no longer contains the removed product.
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.className("cart_item")
                )
        );

        System.out.println("Backpack removed successfully.");
    }

    // Check whether cart badge is visible
    public boolean isCartBadgeDisplayed() {
        try {
            return wait.until(
                            ExpectedConditions.visibilityOfElementLocated(cartBadge))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Open cart

    // Open cart and wait for the cart page


    public void openCart() {
        WebElement cart = wait.until(
                ExpectedConditions.elementToBeClickable(cartButton)
        );

        System.out.println("Before click: " + driver.getCurrentUrl());
        System.out.println("Cart badge: " +
                driver.findElement(cartBadge).getText());

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", cart);

        wait.until(ExpectedConditions.urlContains("cart.html"));

        System.out.println("Cart opened: " + driver.getCurrentUrl());
    }

    // Get cart item name
    public String getCartItemName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartItem)).getText();
    }

    // Get cart quantity
    public String getCartQuantity() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartQuantity)).getText();
    }

    // Click Checkout and wait for the information form


    public void clickCheckout() {
        By checkoutButton = By.id("checkout");

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(checkoutButton)
        );

        System.out.println("Before checkout: " + driver.getCurrentUrl());

        // Try a normal Selenium click first
        button.click();

        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        } catch (TimeoutException e) {
            System.out.println("Normal click did not navigate.");
            System.out.println("URL: " + driver.getCurrentUrl());

            // Diagnostic fallback: click the same button using JavaScript
            WebElement checkout = driver.findElement(checkoutButton);

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", checkout);

            wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        }

        System.out.println("Checkout page opened: " + driver.getCurrentUrl());
    }

    // Diagnostic: inspect checkout inputs before typing
    public void printCheckoutFormDetails() {
        System.out.println(
                "Current URL: " + driver.getCurrentUrl());

        for (WebElement field : driver.findElements(
                By.cssSelector(".checkout_info input"))) {

            System.out.println(
                    "id=" + field.getAttribute("id")
                            + ", data-test=" + field.getAttribute("data-test")
                            + ", value=[" + field.getAttribute("value") + "]"
                            + ", displayed=" + field.isDisplayed()
                            + ", enabled=" + field.isEnabled());
        }
    }

    // Enter text into a checkout field




    private void enterText(By locator, String value) {
        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        field.click();

        // Clear any existing value before typing.
        field.clear();
        field.sendKeys(value);

        // Wait for the value to appear in the input.
        wait.until(driver ->
                value.equals(
                        driver.findElement(locator).getDomProperty("value")
                )
        );

        String actualValue = driver.findElement(locator)
                .getDomProperty("value");

        System.out.println(
                "Verified: " + locator + ", value=[" + actualValue + "]"
        );

        Assert.assertEquals(
                actualValue,
                value,
                "Text was not entered correctly into " + locator
        );
    }

    // Read entered checkout values
    public String getFirstNameValue() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField)).getAttribute("value");
    }

    public String getLastNameValue() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        lastNameField)).getAttribute("value");
    }

    public String getPostalCodeValue() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        postalCodeField)).getAttribute("value");
    }

    // Continue to checkout overview
    public void clickContinue() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueButton)).click();

        try {
            wait.until(driver ->
                    driver.getCurrentUrl().contains(
                            "checkout-step-two.html")
                            || !driver.findElements(
                                    By.cssSelector("[data-test='error']"))
                            .isEmpty());
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Checkout did not proceed. Current URL: "
                            + driver.getCurrentUrl()
                            + ". Check the checkout form values.", e);
        }

        if (!driver.getCurrentUrl().contains(
                "checkout-step-two.html")) {

            String error = driver.findElement(
                    By.cssSelector("[data-test='error']")).getText();

            throw new AssertionError(
                    "Checkout validation failed: " + error);
        }

        System.out.println("Checkout moved to the Overview page.");
    }

    // Get checkout overview title
    public String getCheckoutOverviewTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productsTitle)).getText();
    }

    // Finish order
    public void clickFinish() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        finishButton)).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        completeOrderMessage));

        System.out.println(
                "URL after Finish: " + driver.getCurrentUrl());
    }

    // Get order confirmation message
    public String getCompleteOrderMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        completeOrderMessage)).getText();
    }
}
