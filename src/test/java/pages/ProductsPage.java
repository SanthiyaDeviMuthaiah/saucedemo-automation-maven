
package pages;

import java.time.Duration;

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
    private final By cartQuantity = By.cssSelector(".cart_quantity");

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
    public void addBackpackToCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addBackpackButton)).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartBadge));
    }

    // Remove backpack from cart
    public void removeBackpackFromCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        removeBackpackButton)).click();
    }

    // Open cart
    public void openCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        cartButton)).click();
    }

    // Check whether cart badge is visible
    public boolean isCartBadgeDisplayed() {
        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            cartBadge));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
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
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        checkoutButton)).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField));
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
                ExpectedConditions.elementToBeClickable(locator));

        field.click();
        field.clear();
        field.sendKeys(value);

        String actualValue = field.getDomProperty("value");

        System.out.println(
                "Field: " + locator
                        + ", expected=[" + value + "]"
                        + ", actual=[" + actualValue + "]"
                        + ", activeElement="
                        + field.equals(driver.switchTo().activeElement()));
    }

    public void enterFirstName(String firstName) {
        enterText(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        enterText(lastNameField, lastName);
    }

    public void enterPostalCode(String postalCode) {
        enterText(postalCodeField, postalCode);
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
