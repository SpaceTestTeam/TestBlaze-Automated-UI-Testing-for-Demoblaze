package com.spacetest.demoblaze.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BasePage;
import com.spacetest.demoblaze.pages.components.NavigationBar;

public class ProductPage extends BasePage {

    // Include the NavigationBar as a component
    private NavigationBar navigationBar;


    public ProductPage(WebDriver driver) {
        // Uses the new BasePage constructor
        super(driver);
        this.navigationBar = new NavigationBar(driver);
    }

    // --- Locators ---
    @FindBy(css = ".name")
    private WebElement productName;

    @FindBy(css = ".price-container")
    private WebElement productPrice;
    
    // We use this button to confirm the page has loaded
    @FindBy(linkText = "Add to cart")
    private WebElement addToCartButton;


    @FindBy(css = "#more-information p")
    private WebElement productDescription;

    @FindBy(css = ".product-image img")
    private WebElement productImage;


    // --- Page Methods ---
    public NavigationBar navBar() {
        return navigationBar;
    }
    
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
    public String getProductDescription() {
        wait.until(ExpectedConditions.visibilityOf(productDescription));
        return productDescription.getText();
    }

    public boolean isProductImageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(productImage));
        return productImage.isDisplayed();
    }

    /**
     * Clicks 'Add to cart' and waits for the alert.
     */
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Gets the text from the JS alert and accepts it.
     */
    public String getAlertTextAndAccept() {
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}