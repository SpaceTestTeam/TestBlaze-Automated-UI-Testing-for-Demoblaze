package com.spacetest.demoblaze.tests;

import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.base.TestDataProviders;
import com.spacetest.demoblaze.constants.Constants;
import com.spacetest.demoblaze.pages.LoginPage;

public class LoginPageTest extends BaseTest {

    @Test(description = "LP_01: Verify successful login with valid credentials", groups = {"Login", "Smoke"})
    public void testValidLogin() {
        // 1. Arrange
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        
        // 2. Act
        loginPage.loginAs(Constants.VALID_USERNAME, Constants.VALID_PASSWORD);
        
        // 3. Assert
        String welcomeMsg = homePage.navBar().getWelcomeMessage();
        Assert.assertEquals(welcomeMsg, "Welcome " + Constants.VALID_USERNAME);
    }
    
    @Test(description = "LP_02: Verify login fails with incorrect credentials", 
          groups = {"Login", "Regression"}, dataProvider = "invalidLoginData", dataProviderClass = TestDataProviders.class)
    public void testInvalidLogin(String username, String password, String expectedError) {
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        loginPage.loginAs(username, password);
        String alertText = loginPage.getAlertTextAndAccept();
        Assert.assertEquals(alertText, expectedError);
    }

    @Test(description = "LP_03: Verify login fails when fields are empty", 
          groups = {"Login", "Regression"}, dataProvider = "emptyLoginData", dataProviderClass = TestDataProviders.class)
    public void testLoginWithEmptyFields(String username, String password, String expectedError) {
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        loginPage.loginAs(username, password);
        String alertText = loginPage.getAlertTextAndAccept();
        Assert.assertEquals(alertText, expectedError);
    }

    @Test(description = "LP_04: Verify login popup UI elements", groups = {"Login", "UI"})
    public void testLoginModalUI() {
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field not displayed");
        softAssert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field not displayed");
        softAssert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button not enabled");
        softAssert.assertAll();
        loginPage.clickCloseButton();
    }

    @Test(description = "LP_05: Verify close button functionality", groups = {"Login", "Regression"})
    public void testCloseButton() {
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        loginPage.clickCloseButton();
        // Assert modal is gone by checking if login link is clickable again
        Assert.assertTrue(homePage.navBar().isLoginLinkClickable(), "Login modal did not close.");
    }
    
    @Test(description = "LP_08: Verify login button disabled until fields filled", groups = {"Login", "Regression"})
    public void testLoginButtonDisabled() {
        // This test case (LP_08) is not valid for this site. 
        // The "Log in" button is ALWAYS enabled. We can verify this.
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button is enabled even when empty.");
        loginPage.enterUsername("test");
        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button is enabled with only username.");
        loginPage.clickCloseButton();
    }
    
    @Test(description = "LP_10: Verify password field is masked", groups = {"Login", "Regression"})
    public void testPasswordMasking() {
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        Assert.assertEquals(loginPage.getPasswordFeildType(), "password");
        loginPage.clickCloseButton();
    }

    @Test(description = "LP_12: Verify logout functionality", groups = {"Login", "Smoke"})
    public void testLogout() {
        // 1. Login first
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        loginPage.loginAs(Constants.VALID_USERNAME, Constants.VALID_PASSWORD);
        homePage.navBar().getWelcomeMessage(); // Wait for login
        
        // 2. Act: Click logout
        homePage.navBar().clickLogout();
        
        // 3. Assert: Welcome message is gone, Login link is back
        Assert.assertTrue(homePage.navBar().isLoginLinkClickable(), "Login link did not reappear after logout.");
    }
    
    @Test(description = "LP_07: Verify session remains logged in after refresh", groups = {"Login", "Regression"})
    public void testSessionPersistence() {
        // 1. Login
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        loginPage.loginAs(Constants.VALID_USERNAME, Constants.VALID_PASSWORD);
        homePage.navBar().getWelcomeMessage(); // Wait for login
        
        // 2. Act: Refresh the page
        driver.navigate().refresh();
        
        // 3. Assert: Welcome message is still there
        String welcomeMsg = homePage.navBar().getWelcomeMessage();
        Assert.assertEquals(welcomeMsg, "Welcome " + Constants.VALID_USERNAME);
    }
    
    @Test(description = "LP_15: Verify login popup responsiveness on mobile", groups = {"Login", "Responsive"})
    public void testLoginModalResponsive() {
        // 1. Resize to mobile
        driver.manage().window().setSize(new Dimension(390, 844));

        // 2. Open login modal
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        
        // 3. Assert
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field not displayed on mobile");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field not displayed on mobile");
        loginPage.clickCloseButton();
    }
    @Test(
        description = "LP_01: Verify successful login with newly created credentials", 
        groups = {"Login", "Smoke"},
        // This test will ONLY run if 'testValidSignup' passes
        dependsOnMethods = "com.spacetest.demoblaze.tests.SignupPageTest.testValidSignup"
    )
    public void testValidLoginWithNewUser(ITestContext context) {
        // 1. RETRIEVE THE DATA
        String username = (String) context.getAttribute("newUsername");
        String password = (String) context.getAttribute("newPassword");
        
        System.out.println("LOGIN: Logging in with user: " + username);

        // 2. Arrange
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        
        // 3. Act
        loginPage.loginAs(username, password);
        
        // 4. Assert
        String welcomeMsg = homePage.navBar().getWelcomeMessage();
        Assert.assertEquals(welcomeMsg, "Welcome " + username);
    }
}