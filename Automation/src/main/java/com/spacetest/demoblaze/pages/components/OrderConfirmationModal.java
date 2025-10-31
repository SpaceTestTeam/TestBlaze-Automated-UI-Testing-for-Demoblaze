package com.spacetest.demoblaze.pages.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;
import com.spacetest.demoblaze.constants.Constants; 

public class OrderConfirmationModal extends BaseComponent {

    public OrderConfirmationModal(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(successMessage));
    }
    
    // --- Locators ---
    @FindBy(css = ".sweet-alert h2")
    private WebElement successMessage; // "Thank you for your purchase!"

    @FindBy(css = ".sweet-alert .lead")
    private WebElement confirmationDetails; // Contains ID and Amount

    @FindBy(css = ".confirm.btn-primary")
    private WebElement okButton;

    // --- Methods ---
    
    public String getSuccessMessage() {
        return successMessage.getText();
    }
    
    public String getConfirmationDetails() {
        return wait.until(ExpectedConditions.visibilityOf(confirmationDetails)).getText();
    }
    
    /**
     * Clicks OK and waits for the redirect.
     * This includes a hard-coded Thread.sleep() because the
     * SweetAlert's "OK" button's redirect event is not
     * attached immediately, causing a race condition.
     */
    public void clickOK() {
        // Wait for the modal's dynamic text to load
        wait.until(ExpectedConditions.visibilityOf(confirmationDetails));
        
        try {
            // THIS IS THE FIX YOU FOUND:
            // This is a last resort, but this specific "SweetAlert"
            // modal attaches its redirect event AFTER it becomes visible,
            // so we must add a hard pause.
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Now that we've paused, the button's JS is ready
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
        
        // And finally, wait for the redirect to complete
        wait.until(ExpectedConditions.urlToBe(Constants.HOMEPAGEURL));
    }
    public String getOrderAmount() {
        String details = getConfirmationDetails();
        // Use regex to find "Amount: 360"
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("Amount: (\\d+) USD")
                                                .matcher(details);
        if (matcher.find()) {
            return matcher.group(1); // Return the captured number
        }
        return null; // Return null if not found
    }
}