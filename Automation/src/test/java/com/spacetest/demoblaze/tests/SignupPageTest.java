package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.base.TestDataProviders;
import com.spacetest.demoblaze.pages.SignupPage;
import org.testng.ITestContext;
// ... (other imports)

public class SignupPageTest extends BaseTest {

    @Test(description = "SU01: Verify successful sign up with valid credentials", 
          groups = {"Signup", "Smoke"},
          // We give this test a unique name so 'Login' can depend on it
          testName = "testValidSignup") 
    public void testValidSignup(ITestContext context) {
        // 1. Arrange
        String randomUser = generateRandomUsername(); // From BaseTest
        String randomPassword = "testpass123";
        
        System.out.println("SIGNUP: Creating user: " + randomUser);
        
        SignupPage signupPage = homePage.navBar().clickSignupLink();
        
        // 2. Act
        signupPage.signupAs(randomUser, randomPassword);
        
        // 3. Assert
        String alertText = signupPage.getAlertTextAndAccept();
        Assert.assertEquals(alertText, "Sign up successful.");
        
        // 4. SAVE THE DATA
        // This saves the new credentials for other tests in the same <test> run
        context.setAttribute("newUsername", randomUser);
        context.setAttribute("newPassword", randomPassword);
    }
    @Test(description = "SU02, SU03, SU04, SU05: Verify sign up fails with invalid/empty credentials",
          groups = {"Signup", "Regression"}, dataProvider = "invalidSignupData", dataProviderClass = TestDataProviders.class)
    public void testInvalidSignup(String username, String password, String expectedError) {
        SignupPage signupPage = homePage.navBar().clickSignupLink();
        signupPage.signupAs(username, password);
        String alertText = signupPage.getAlertTextAndAccept();
        Assert.assertEquals(alertText, expectedError);
    }

    @Test(description = "SU09, SU10: Verify 'Close' button functionality", groups = {"Signup", "Regression"})
    public void testCloseButton() {
        // 1. Arrange
        SignupPage signupPage = homePage.navBar().clickSignupLink();

        // 2. Act
        signupPage.clickCloseButton();

        // 3. Assert (Modal is closed if the link is clickable again)
        Assert.assertTrue(homePage.navBar().isSignupLinkClickable(), "Signup modal did not close.");
    }
    
    @Test(description = "SU12: Verify UI presence of sign up elements", groups = {"Signup", "UI"})
    public void testSignupModalUI() {
        SignupPage signupPage = homePage.navBar().clickSignupLink();
        SoftAssert softAssert = new SoftAssert();

        // 2. Assert
        softAssert.assertTrue(signupPage.isUsernameFieldDisplayed(), "Username field not displayed");
        softAssert.assertTrue(signupPage.isPasswordFieldDisplayed(), "Password field not displayed");
        softAssert.assertTrue(signupPage.isSignupButtonDisplayed(), "Sign up button not displayed");
        softAssert.assertAll();
        
        // 3. Cleanup
        signupPage.clickCloseButton();
    }
}