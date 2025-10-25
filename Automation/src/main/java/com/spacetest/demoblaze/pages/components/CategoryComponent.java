package com.spacetest.demoblaze.pages.components;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class CategoryComponent extends BaseComponent {

    public CategoryComponent(WebDriver driver) {
        super(driver);
    }

    // --- Locators ---
    @FindBy(id = "tbodyid")
    private WebElement productGridContainer;

    private By productTitleLocator = By.cssSelector("#tbodyid .card-title a");
    
    // New locator for each product "card" as a whole
    private By productCardLocator = By.cssSelector("#tbodyid .card.h-100");

    // --- Page Actions ---
    
    /**
     * Clicks a category and waits for the product list to refresh.
     */
    public void clickCategoryAndWait(String categoryName) {
        // 1. Get a reference to the first product *before* we click.
        WebElement firstProduct = wait.until(
            ExpectedConditions.visibilityOfElementLocated(productTitleLocator)
        );

        // 2. Click the category link
        WebElement categoryLink = driver.findElement(By.linkText(categoryName));
        wait.until(ExpectedConditions.elementToBeClickable(categoryLink)).click();

        // 3. Wait for the old element to go stale.
        wait.until(ExpectedConditions.stalenessOf(firstProduct));
        
        // 4. Wait for the new first product to appear.
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(productTitleLocator)
        );
    }

    public List<String> getProductNames() {
        List<WebElement> productElements = driver.findElements(productTitleLocator);
        return productElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
    public List<ProductCardComponent> getAllProducts() {
        // Wait for at least one card to be present
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCardLocator));
        
        List<WebElement> productCardElements = driver.findElements(productCardLocator);
        List<ProductCardComponent> productCards = new ArrayList<>();
        
        // Wrap each WebElement in our new component class
        for (WebElement element : productCardElements) {
            productCards.add(new ProductCardComponent(driver, element));
        }
        return productCards;
    }
}