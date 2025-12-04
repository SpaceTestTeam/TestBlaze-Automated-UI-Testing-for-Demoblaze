package com.spacetest.demoblaze.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;
import com.spacetest.demoblaze.pages.ProductPage;

// This component represents a *single* product card on the homepage
public class ProductCardComponent extends BaseComponent {

    private WebElement rootElement; // The ".card.h-100" element

    // --- Updated Locators (relative to rootElement) ---
    private By titleLink = By.cssSelector(".card-title a");
    
    // This is the <a> tag wrapping the image (for clicking)
    private By imageLink = By.cssSelector("a:first-child"); 
    
    // This is the <img> tag itself (for verifying src/display)
    private By imageTag = By.cssSelector(".card-img-top"); 
    
    private By priceLabel = By.cssSelector(".card-block h5");
    private By descriptionText = By.cssSelector(".card-text");

    public ProductCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver);
        this.rootElement = rootElement;
    }

    // --- Verification Methods (Now with Waits) ---
    
    public String getTitle() {
        // Wait for element to be present inside the root element
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(rootElement, titleLink)).getText();
    }

    public String getPrice() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(rootElement, priceLabel)).getText();
    }

    public String getDescription() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(rootElement, descriptionText)).getText();
    }

    public String getImageUrl() {
        // Wait for the <img> tag to be present, then get its 'src'
        WebElement img = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(rootElement, imageTag));
        return img.getAttribute("src");
    }

    @SuppressWarnings("null")
    public boolean isImageDisplayed() {
        // Wait for the <img> tag to be *visible*
        try {
            // We use visibilityOf here for a stronger check
            wait.until(ExpectedConditions.visibilityOf(rootElement.findElement(imageTag)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // --- Action Methods (Now with Waits) ---
    
    @SuppressWarnings("null")
    public ProductPage clickTitle() {
        // Wait for the <a> tag to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(rootElement.findElement(titleLink))).click();
        return new ProductPage(driver); 
    }

    @SuppressWarnings("null")
    public ProductPage clickImage() {
        // Wait for the <a> tag (imageLink) to be clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(rootElement.findElement(imageLink))).click();
        return new ProductPage(driver);
    }
}