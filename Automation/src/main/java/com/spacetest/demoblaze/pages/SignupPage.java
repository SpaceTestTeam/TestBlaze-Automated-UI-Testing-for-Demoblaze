package com.spacetest.demoblaze.pages;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class SignupPage extends BaseComponent {

    // --- Constructor ---
    public SignupPage(WebDriver driver) {
        super(driver);
        // Wait for the modal title to be visible before proceeding
        wait.until(ExpectedConditions.visibilityOf(signupModalTitle));
    }

    // --- Locators ---
    @FindBy(id = "signInModalLabel")
    private WebElement signupModalTitle;

    @FindBy(id = "sign-username")
    private WebElement usernameField;

    @FindBy(id = "sign-password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Sign up']")
    private WebElement signupButton;

    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Close'])[2]")
    private WebElement closeButton;

    // --- Action Methods ---
    
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void clickSignupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signupButton)).click();
    }

    public void clickCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(signupModalTitle)); // Wait for it to close
    }

    /**
     * Helper method to fill the form and click "Sign up".
     */
    public void signupAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignupButton();
    }

    // --- Verification & Helper Methods ---

    /**
     * Waits for, switches to, and gets the text from a JS alert.
     * @return The text from the alert.
     */
    public String getAlertTextAndAccept() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
    
    public boolean isUsernameFieldDisplayed() {
        return usernameField.isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return passwordField.isDisplayed();
    }
    
    public boolean isSignupButtonDisplayed() {
        return signupButton.isDisplayed();
    }
}