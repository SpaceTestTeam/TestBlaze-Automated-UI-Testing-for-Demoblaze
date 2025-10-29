package com.spacetest.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BasePage;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // --- Locators ---
    // Locator for the title of the first product in the cart table
    @FindBy(xpath = "//tbody[@id='tbodyid']/tr[@class='success']/td[2]")
    private WebElement firstProductInCart;

    // Locator for the "Place Order" button (to confirm page load)
    @FindBy(xpath = "//button[text()='Place Order']")
    private WebElement placeOrderButton;

    // --- Page Methods ---
    
    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOf(placeOrderButton));
    }

    /**
     * Gets the name of the first product listed in the cart.
     * @return The product name string.
     */
    public String getFirstProductNameInCart() {
        try {
            // Wait for the item to appear in the cart
            wait.until(ExpectedConditions.visibilityOf(firstProductInCart));
            return firstProductInCart.getText();
        } catch (Exception e) {
            return null; // Return null if cart is empty
        }
    }
}