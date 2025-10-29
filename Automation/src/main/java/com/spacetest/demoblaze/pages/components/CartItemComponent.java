package com.spacetest.demoblaze.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class CartItemComponent extends BaseComponent {

    private WebElement rootElement; // This will be the <tr> element

    // Locators relative to the root element
    private By name = By.cssSelector("td:nth-child(2)");
    private By price = By.cssSelector("td:nth-child(3)");
    private By deleteLink = By.cssSelector("td:nth-child(4) a");

    public CartItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver);
        this.rootElement = rootElement;
    }

    public String getName() {
        return wait.until(ExpectedConditions.visibilityOf(rootElement.findElement(name))).getText();
    }

    public String getPrice() {
        return wait.until(ExpectedConditions.visibilityOf(rootElement.findElement(price))).getText();
    }

    public void clickDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(rootElement.findElement(deleteLink))).click();
    }
}