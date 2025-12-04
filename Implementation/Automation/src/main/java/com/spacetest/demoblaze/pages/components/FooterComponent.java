package com.spacetest.demoblaze.pages.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class FooterComponent extends BaseComponent {

    public FooterComponent(WebDriver driver) {
        super(driver);
    }

    // --- Locators ---
    @FindBy(id = "fotcont")
    private WebElement footerContainer;
    
    // Using xpath to find the <h4> tag with the "Get in Touch" text
    @FindBy(xpath = "//div[@id='fotcont']//h4[contains(., 'Get in Touch')]")
    private WebElement getInTouchHeader;

    // Using xpath to find the <p> tag containing the address
    @FindBy(xpath = "//div[@id='fotcont']//p[contains(., 'Address:')]")
    private WebElement addressText;

    // Using xpath to find the <p> tag containing the phone
    @FindBy(xpath = "//div[@id='fotcont']//p[contains(., 'Phone:')]")
    private WebElement phoneText;

    // Using xpath to find the <p> tag containing the email
    @FindBy(xpath = "//div[@id='fotcont']//p[contains(., 'Email:')]")
    private WebElement emailText;

    // --- Verification Methods ---

    /**
     * Waits for the footer to be visible and returns its header text.
     * @return Text of the "Get in Touch" header.
     */
    public String getGetInTouchHeaderText() {
        wait.until(ExpectedConditions.visibilityOf(getInTouchHeader));
        return getInTouchHeader.getText();
    }

    /**
     * Waits for the footer to be visible and returns the address text.
     * @return Text containing the address.
     */
    public String getAddressText() {
        wait.until(ExpectedConditions.visibilityOf(addressText));
        return addressText.getText();
    }
    
    /**
     * Waits for the footer to be visible and returns the phone text.
     * @return Text containing the phone number.
     */
    public String getPhoneText() {
        wait.until(ExpectedConditions.visibilityOf(phoneText));
        return phoneText.getText();
    }
    
    /**
     * Waits for the footer to be visible and returns the email text.
     * @return Text containing the email.
     */
    public String getEmailText() {
        wait.until(ExpectedConditions.visibilityOf(emailText));
        return emailText.getText();
    }
}