package com.spacetest.demoblaze.pages.components;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    // --- NEW: Pagination Locators ---
    @FindBy(id = "prev2")
    private WebElement prevButton;

    @FindBy(id = "next2")
    private WebElement nextButton;


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
        @SuppressWarnings("null")
        WebElement categoryLink = driver.findElement(By.linkText(categoryName));
        wait.until(ExpectedConditions.elementToBeClickable(categoryLink)).click();

        // 3. Wait for the old element to go stale.
        wait.until(ExpectedConditions.stalenessOf(firstProduct));
        
        // 4. Wait for the new first product to appear.
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(productTitleLocator)
        );
    }

    @SuppressWarnings("null")
    public List<String> getProductNames() {
        // THIS IS THE FIX: Wait for at least ONE product title to be visible.
        wait.until(ExpectedConditions.visibilityOfElementLocated(productTitleLocator));

        // Now that we know the products have loaded, we can safely get them all.
        @SuppressWarnings("null")
        List<WebElement> productElements = driver.findElements(productTitleLocator);
        return productElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
    public List<ProductCardComponent> getAllProducts() {
        // Wait for at least one card to be present
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCardLocator));
        
        @SuppressWarnings("null")
        List<WebElement> productCardElements = driver.findElements(productCardLocator);
        List<ProductCardComponent> productCards = new ArrayList<>();
        
        // Wrap each WebElement in our new component class
        for (WebElement element : productCardElements) {
            productCards.add(new ProductCardComponent(driver, element));
        }
        return productCards;
    }
    public void clickNextPage() {
        // 1. Get a reference to the current product list
        WebElement firstProduct = wait.until(
            ExpectedConditions.visibilityOfElementLocated(productTitleLocator)
        );
        
        // 2. Click the 'Next' button
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        
        // 3. Wait for the old product list to go stale
        wait.until(ExpectedConditions.stalenessOf(firstProduct));
        
        // 4. Wait for the new list to appear
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(productTitleLocator)
        );
    }

    /**
     * Clicks the 'Previous' page button and waits for the product list to refresh.
     */
    public void clickPrevPage() {
        WebElement firstProduct = wait.until(
            ExpectedConditions.visibilityOfElementLocated(productTitleLocator)
        );
        
        wait.until(ExpectedConditions.elementToBeClickable(prevButton)).click();
        
        wait.until(ExpectedConditions.stalenessOf(firstProduct));
        
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(productTitleLocator)
        );
    }

    /**
     * Checks if the 'Next' button is visible.
     * Uses a short wait and try-catch to avoid NoSuchElementException.
     */
    public boolean isNextButtonDisplayed() {
        try {
            // Use a short 2-second wait just to be sure
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            return shortWait.until(ExpectedConditions.visibilityOf(nextButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the 'Previous' button is visible.
     * Uses a short wait and try-catch to avoid NoSuchElementException.
     */
    public boolean isPrevButtonDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            return shortWait.until(ExpectedConditions.visibilityOf(prevButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductGridWidth() {
        wait.until(ExpectedConditions.visibilityOf(productGridContainer));
        return productGridContainer.getSize().getWidth();
    }
}