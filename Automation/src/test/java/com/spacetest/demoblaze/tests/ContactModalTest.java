package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.pages.components.ContactModal;

public class ContactModalTest extends BaseTest {

    @Test(description = "CF01: Verify contact form works correctly with valid data", 
          groups = {"Contact", "Smoke"})
    public void testValidContactForm() {
        // 1. Arrange
        ContactModal contactModal = homePage.navBar().clickContactLink();
        
        // 2. Act
        contactModal.fillContactForm("test@example.com", "Test User", "This is a test message.");
        contactModal.clickSendMessage();
        
        // 3. Assert
        String alertText = contactModal.getAlertTextAndAccept();
        Assert.assertEquals(alertText, "Thanks for the message!!");
        
        // 4. Verify modal closed
        Assert.assertTrue(homePage.navBar().isContactLinkClickable(), 
            "Contact modal did not close after sending message.");
    }

    @Test(description = "CF07, CF08: Verify 'Close' button functionality", 
          groups = {"Contact", "Regression"})
    public void testCloseButton() {
        // 1. Arrange
        ContactModal contactModal = homePage.navBar().clickContactLink();

        // 2. Act
        contactModal.clickCloseButton();

        // 3. Assert
        Assert.assertTrue(homePage.navBar().isContactLinkClickable(), 
            "Contact modal did not close after clicking 'Close' button.");
    }
    
    @Test(description = "CF10: Verify the presence of contact elements", 
          groups = {"Contact", "UI"})
    public void testContactModalUI() {
        ContactModal contactModal = homePage.navBar().clickContactLink();
        SoftAssert softAssert = new SoftAssert();

        // 2. Assert
        softAssert.assertEquals(contactModal.getModalTitleText(), "New message", "Modal title is incorrect.");
        softAssert.assertTrue(contactModal.isEmailFieldDisplayed(), "Email field not displayed");
        softAssert.assertTrue(contactModal.isNameFieldDisplayed(), "Name field not displayed");
        softAssert.assertTrue(contactModal.isMessageFieldDisplayed(), "Message field not displayed");
        softAssert.assertTrue(contactModal.isSendButtonDisplayed(), "Send button not displayed");
        softAssert.assertAll();
        
        // 3. Cleanup
        contactModal.clickCloseButton();
    }
    
    // Test cases CF02, CF03, CF04, CF05, CF06 (Invalid data) are not automated
    // because the application lacks any client-side validation for these fields.
    // The form submits successfully regardless of input.
}