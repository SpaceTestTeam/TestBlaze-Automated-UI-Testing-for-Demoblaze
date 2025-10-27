package com.spacetest.demoblaze.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class LoginPage extends BaseComponent {

    // --- Constructor ---
    public LoginPage(WebDriver driver) {
        super(driver);
        // Wait for the modal itself to be visible
        wait.until(ExpectedConditions.visibilityOf(loginModalTitle));
    }

    // --- Locators ---
    @FindBy(id = "logInModalLabel")
    private WebElement loginModalTitle;
    
    @FindBy(id = "loginusername")
    private WebElement usernameField;

    @FindBy(id = "loginpassword")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement loginButton;

    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Close'])[3]")
    private WebElement closeButton;

    // --- Action Methods ---
    
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }
    
    /**
     * Clicks the login button. Does NOT handle the alert.
     */
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
    
    public void clickCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(loginModalTitle)); // Wait for it to close
    }

    /**
     * Helper method to perform a full login.
     * This method does NOT handle successful login (which just closes the modal).
     * It's designed for tests that expect an alert (e.g., bad password).
     */
    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    // --- Verification Methods ---

    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }
    
    public String getPasswordFeildType() {
        return passwordField.getAttribute("type"); // Should be "password"
    }

    public boolean isUsernameFieldDisplayed() {
        return usernameField.isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return passwordField.isDisplayed();
    }

    /**
     * Clicks login and expects an alert.
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