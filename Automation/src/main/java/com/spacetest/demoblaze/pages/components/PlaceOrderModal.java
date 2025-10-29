package com.spacetest.demoblaze.pages.components;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class PlaceOrderModal extends BaseComponent {

    public PlaceOrderModal(WebDriver driver) {
        super(driver);
        // Wait for the modal to be visible
        wait.until(ExpectedConditions.visibilityOf(modalTitle));
    }

    // --- Locators ---
    @FindBy(id = "orderModalLabel")
    private WebElement modalTitle;

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "card")
    private WebElement cardField;

    @FindBy(xpath = "//button[text()='Purchase']")
    private WebElement purchaseButton;

    @FindBy(xpath = "//div[@id='orderModal']//button[text()='Close']")
    private WebElement closeButton;

    // --- Methods ---
    
    public boolean isModalDisplayed() {
        return modalTitle.isDisplayed();
    }
    
    public void fillOrderForm(String name, String creditCard) {
        wait.until(ExpectedConditions.visibilityOf(nameField)).sendKeys(name);
        wait.until(ExpectedConditions.visibilityOf(cardField)).sendKeys(creditCard);
    }
    
    public void clickPurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(purchaseButton)).click();
    }
    
    public void clickClose() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(modalTitle));
    }

    /**
     * Waits for, switches to, and gets the text from a JS alert.
     * This is for when the form is submitted empty.
     * @return The text from the alert.
     */
    public String getAlertTextAndAccept() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}