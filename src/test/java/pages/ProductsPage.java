
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

    private final By productsTitle =
            By.className("title");

    private final By addBackpackButton =
            By.id("add-to-cart-sauce-labs-backpack");

    private final By removeBackpackButton =
            By.id("remove-sauce-labs-backpack");

    private final By cartButton =
            By.className("shopping_cart_link");

    private final By cartBadge =
            By.className("shopping_cart_badge");

    private final By cartItem =
            By.className("inventory_item_name");

    private final By cartQuantity =
            By.cssSelector(".cart_quantity");

    private final By checkoutButton =
            By.id("checkout");

    private final By firstNameField =
            By.id("first-name");

    private final By lastNameField =
            By.id("last-name");

    private final By postalCodeField =
            By.id("postal-code");

    private final By continueButton =
            By.id("continue");

    private final By finishButton =
            By.id("finish");

    private final By completeOrderMessage =
            By.className("complete-header");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    public String getProductsTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productsTitle)).getText();
    }

    public void addBackpackToCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addBackpackButton)).click();
    }

    public void removeBackpackFromCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        removeBackpackButton)).click();
    }

    public void openCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        cartButton)).click();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    public String getCartItemName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartItem)).getText();
    }

    public String getCartQuantity() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartQuantity)).getText();
    }

    public void clickCheckout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        checkoutButton)).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField));
    }


    private void enterText(By locator, String value) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));

        field.click();
        field.clear();
        field.sendKeys(value);

        try {
            wait.until(driver -> {
                WebElement currentField = driver.findElement(locator);

                return value.equals(
                        currentField.getAttribute("value"));
            });
        } catch (TimeoutException e) {
            String actualValue = driver.findElement(locator)
                    .getAttribute("value");

            throw new AssertionError(
                    "Could not enter the expected value into "
                            + locator
                            + ". Expected: " + value
                            + ", Actual: '" + actualValue + "'",
                    e);
        }

        System.out.println(
                "Verified value for " + locator + ": " + value);
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



    public void clickContinue() {
        wait.until(
                        ExpectedConditions.elementToBeClickable(continueButton))
                .click();

        try {
            wait.until(driver ->
                    driver.getCurrentUrl().contains("checkout-step-two.html")
                            || !driver.findElements(
                                    By.cssSelector("[data-test='error']"))
                            .isEmpty());

        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Checkout did not proceed. Current URL: "
                            + driver.getCurrentUrl()
                            + ". Check the checkout form values.",
                    e);
        }

        if (!driver.getCurrentUrl().contains("checkout-step-two.html")) {
            String error = driver.findElement(
                    By.cssSelector("[data-test='error']")).getText();

            throw new AssertionError(
                    "Checkout validation failed: " + error);
        }

        System.out.println("Checkout moved to the Overview page.");
    }



    public String getCheckoutOverviewTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productsTitle)).getText();
    }


    public void clickFinish() {
        wait.until(
                        ExpectedConditions.elementToBeClickable(finishButton))
                .click();

        try {
            wait.until(
                    ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(
                                    completeOrderMessage),
                            ExpectedConditions.urlContains(
                                    "checkout-complete.html")
                    )
            );

            System.out.println("URL after Finish: " + driver.getCurrentUrl());

        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Finish was clicked, but the order confirmation "
                            + "did not appear. Current URL: "
                            + driver.getCurrentUrl(),
                    e);
        }
    }


    public String getCompleteOrderMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        completeOrderMessage)).getText();
    }
}
