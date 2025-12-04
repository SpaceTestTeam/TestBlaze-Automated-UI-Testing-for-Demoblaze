package com.spacetest.demoblaze.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class ProductSlider extends BaseComponent {

    public ProductSlider(WebDriver driver) {
        super(driver);
    }

    // --- Locators ---
    @FindBy(xpath = "(//span[@class='carousel-control-next-icon'])[1]")
    private WebElement nextCarouselButton;

    @FindBy(xpath = "(//span[@class='carousel-control-prev-icon'])[1]")
    private WebElement prevCarouselButton;

    // **BUG FIX:** We use a 'By' locator to find the *currently* active image
    // Using @FindBy here will fail with a StaleElementReferenceException
    private By activeImageLocator = By.cssSelector(".carousel-item.active img");


    // --- Page Actions ---
    public void clickNextCarouselButton() {
        nextCarouselButton.click();
    }

    public void clickPrevCarouselButton() {
        prevCarouselButton.click();
    }

    @SuppressWarnings("null")
    public String getActiveCarouselImageSrc() {
        // Find the element dynamically
        return driver.findElement(activeImageLocator).getAttribute("src");
    }

    public void waitForCarouselChange(String previousImageSrc) {
        // **BUG FIX:** The wait must use the 'By' locator, not a stale WebElement
        wait.until(ExpectedConditions.not(
            ExpectedConditions.attributeToBe(activeImageLocator, "src", previousImageSrc)
        ));
    }
}