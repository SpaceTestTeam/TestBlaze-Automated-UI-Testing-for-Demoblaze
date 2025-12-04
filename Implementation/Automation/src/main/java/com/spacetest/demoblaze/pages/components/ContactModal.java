package com.spacetest.demoblaze.pages.components;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class ContactModal extends BaseComponent {

    // --- Constructor ---
    public ContactModal(WebDriver driver) {
        super(driver);
        // Wait for the modal title to be visible before proceeding
        wait.until(ExpectedConditions.visibilityOf(modalTitle));
    }

    // --- Locators ---
    @FindBy(id = "exampleModalLabel")
    private WebElement modalTitle;

    @FindBy(id = "recipient-email")
    private WebElement contactEmailField;

    @FindBy(id = "recipient-name")
    private WebElement contactNameField;

    @FindBy(id = "message-text")
    private WebElement messageField;

    @FindBy(xpath = "//button[text()='Send message']")
    private WebElement sendMessageButton;
    
    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Close'])[1]")
    private WebElement closeButton;

    // --- Action Methods ---
    
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(contactEmailField)).sendKeys(email);
    }

    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(contactNameField)).sendKeys(name);
    }
    
    public void enterMessage(String message) {
        wait.until(ExpectedConditions.visibilityOf(messageField)).sendKeys(message);
    }
    
    /**
     * Helper method to fill out the entire form.
     */
    public void fillContactForm(String email, String name, String message) {
        enterEmail(email);
        enterName(name);
        enterMessage(message);
    }

    public void clickSendMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(sendMessageButton)).click();
    }
    
    public void clickCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(modalTitle)); // Wait for it to close
    }

    // --- Verification & Helper Methods ---

    public String getAlertTextAndAccept() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
    
    public String getModalTitleText() {
        return modalTitle.getText();
    }

    public boolean isEmailFieldDisplayed() {
        return contactEmailField.isDisplayed();
    }

    public boolean isNameFieldDisplayed() {
        return contactNameField.isDisplayed();
    }
    
    public boolean isMessageFieldDisplayed() {
        return messageField.isDisplayed();
    }
    
    public boolean isSendButtonDisplayed() {
        return sendMessageButton.isDisplayed();
    }
}