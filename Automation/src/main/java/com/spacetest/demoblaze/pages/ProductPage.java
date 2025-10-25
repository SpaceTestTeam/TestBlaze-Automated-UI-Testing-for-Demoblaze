package com.spacetest.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BasePage;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        // Uses the new BasePage constructor
        super(driver);
    }

    // --- Locators ---
    @FindBy(css = ".name")
    private WebElement productName;

    @FindBy(css = ".price-container")
    private WebElement productPrice;
    
    // We use this button to confirm the page has loaded
    @FindBy(linkText = "Add to cart")
    private WebElement addToCartButton;

    // --- Page Methods ---
    
    /**
     * Waits for the 'Add to cart' button to be visible
     */
    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOf(addToCartButton));
    }

    /**
     * @return The name of the product (e.g., "Samsung galaxy s6")
     */
    public String getProductName() {
        waitForPageToLoad();
        return productName.getText();
    }

    /**
     * @return The price of the product (e.g., "$360 *includes tax")
     */
    public String getProductPrice() {
        waitForPageToLoad();
        return productPrice.getText();
    }
}