package com.spacetest.demoblaze.pages;


import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BasePage;
import com.spacetest.demoblaze.pages.components.CartItemComponent;
import com.spacetest.demoblaze.pages.components.NavigationBar;
import com.spacetest.demoblaze.pages.components.PlaceOrderModal;

public class CartPage extends BasePage {

    private NavigationBar navigationBar;

    public CartPage(WebDriver driver) {
        super(driver);
        this.navigationBar = new NavigationBar(driver);
    }

    // --- Locators ---
    @FindBy(xpath = "//button[text()='Place Order']")
    private WebElement placeOrderButton;

    @FindBy(id = "totalp")
    private WebElement totalPrice;

    private By cartItemRowLocator = By.cssSelector("tbody#tbodyid tr.success");

    // --- Page Methods ---
    
    public NavigationBar navBar() {
        return navigationBar;
    }
    
    public void waitForPageToLoad() {
        // Wait for the "Place Order" button to be visible
        wait.until(ExpectedConditions.visibilityOf(placeOrderButton));
    }

    public boolean isPlaceOrderButtonVisible() {
        try {
            return placeOrderButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
public List<CartItemComponent> getCartItems() {
        
        // THIS IS THE FIX:
        // Wait for *at least one* item row to be visible
        // before we try to create the list.
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemRowLocator));
        } catch (Exception e) {
            // If no items are found after the wait, return an empty list.
            return new ArrayList<CartItemComponent>();
        }
        
        // Now that we've waited, we can safely find the elements
        List<WebElement> itemRows = driver.findElements(cartItemRowLocator);
        List<CartItemComponent> items = new ArrayList<>();
        for (WebElement row : itemRows) {
            items.add(new CartItemComponent(driver, row));
        }
        return items;
    }
    public String getTotalPrice() {
        // Wait for the total to be non-empty
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id("totalp"), "")));
        return totalPrice.getText();
    }
    
    public PlaceOrderModal clickPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
        return new PlaceOrderModal(driver);
    }
    
    /**
     * Checks if the cart is empty by trying to find an item for 2 seconds.
     */
    public boolean isCartEmpty() {
        return getCartItems().isEmpty();
    }

    /**
     * Deletes an item by its name and waits for it to disappear.
     */
    public void deleteItemByName(String name) {
        List<CartItemComponent> items = getCartItems();
        for (CartItemComponent item : items) {
            if (item.getName().equals(name)) {
                item.clickDelete();
                // Wait for the cart to auto-refresh
                wait.until(ExpectedConditions.numberOfElementsToBeLessThan(cartItemRowLocator, items.size()));
                break;
            }
        }
    }
}