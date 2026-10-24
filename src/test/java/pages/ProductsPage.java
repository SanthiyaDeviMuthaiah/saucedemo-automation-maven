
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductsPage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By pageTitle = By.className("title");
    By inventoryItems = By.className("inventory_item");
    By shoppingCart = By.className("shopping_cart_link");

    // Constructor
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Get page title
    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    // Get number of products displayed
    public int getProductCount() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(inventoryItems)
        );
        return driver.findElements(inventoryItems).size();
    }
    public void addProductToCart(String productName) {
        By addButton = By.xpath(
                "//div[text()='" + productName +
                        "']/ancestor::div[@class='inventory_item']//button"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(addButton)
        ).click();
    }
    public int getCartCount() {
        By cartBadge = By.className("shopping_cart_badge");

        return Integer.parseInt(
                driver.findElement(cartBadge).getText()
        );
    }

    public String getCartProductName() {
        By cartProductName = By.className("inventory_item_name");

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartProductName)
        ).getText();
    }

    public String getCartProductQuantity() {
        By quantity = By.className("cart_quantity");

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(quantity)
        ).getText();
    }


    public void removeProductFromCart(String productName) {
        By removeButton = By.xpath(
                "//div[text()='" + productName +
                        "']/ancestor::div[@class='cart_item']//button"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(removeButton)
        ).click();
    }
    public int getCartItemCount() {
        return driver.findElements(
                By.className("cart_item")
        ).size();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(
                By.className("shopping_cart_badge")
        ).isEmpty();
    }

    public void clickShoppingCart() {
        driver.findElement(shoppingCart).click();
    }

    public void clickCheckout() {
        By checkoutButton = By.id("checkout");

        wait.until(
                ExpectedConditions.elementToBeClickable(checkoutButton)
        ).click();
    }

    public void enterFirstName(String firstName) {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(By.id("last-name")).sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);
    }

    public void clickContinue() {
        By continueButton = By.id("continue");

        wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        ).click();
    }

    public void clickFinish() {
        By finishButton = By.id("finish");

        wait.until(
                ExpectedConditions.elementToBeClickable(finishButton)
        ).click();
    }

    public String getOrderConfirmationMessage() {
        By confirmationMessage = By.className("complete-header");

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        confirmationMessage
                )
        ).getText();
    }



}
