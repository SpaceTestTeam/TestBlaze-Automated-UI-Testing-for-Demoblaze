package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.constants.Constants;

public class HomePageTest extends BaseTest {
    @Test(description = "Verify clicking the 'Logo' navigates to the homepage",groups = {"NavBar"})
    public void testLogoLink() {
        // 2. Action: Click the 'Logo'
        homePage.clickLogo();
        // 3. Verification: Check that we are back at the main homepage URL
        Assert.assertEquals(homePage.getPageUrl(), Constants.HOMEPAGEURL,
                "Logo link did not navigate back to the homepage.");
    }

    @Test(description = "Verify clicking the 'Home' navigates to the homepage",groups = {"NavBar"})
    public void testHomeLink() {
        // 2. Action: Click the 'Home' link
        homePage.clickHomeLink();
        // 3. Verification: Check that we are back at the main homepage URL
        Assert.assertEquals(homePage.getPageUrl(),Constants.HOMEPAGEURL,
                "Logo link did not navigate back to the homepage.");
    }

     @Test(description = "Verify clicking the 'Cart' navigates to the cartpage",groups = {"NavBar"})
    public void testCartLink() {
        // 2. Action: Click the 'Home' link
        homePage.clickCartLink();
        // 3. Verification: Check that we are back at the main homepage URL
        Assert.assertEquals(homePage.getPageUrl(),Constants.CARTPAGEURL,
                "Logo link did not navigate back to the cartpage.");
    }

    @Test(description = "Verify clicking the 'Contact' opens the contact modal",groups = {"NavBar"})
    public void testContactLink() {
        // 2. Action: Click the 'Contact' link
        homePage.clickContactLink();
        // 3. Verification: Check that the contact modal is displayed with correct title
        Assert.assertTrue(homePage.isContactModalDisplayed(), "Contact modal is not displayed.");
        Assert.assertEquals(homePage.getContactModalTitleText(), "New message",
                "Contact modal title is incorrect.");
        // Close the modal after verification
        homePage.closeContactModal();
    }

    @Test(description = "Verify clicking the 'About Us' opens the About Us modal",groups = {"NavBar"})
    public void testAboutUsLink() {
        // 2. Action: Click the 'About Us' link
        homePage.clickAboutUsLink();
        // 3. Verification: Check that the About Us modal is displayed with correct title
        Assert.assertTrue(homePage.isAboutUsModalDisplayed(), "About Us modal is not displayed.");
        Assert.assertEquals(homePage.getAboutUsModalTitleText(), "About us",
                "About Us modal title is incorrect.");
        // Close the modal after verification
        homePage.closeAboutUsModal();
    }
    @Test(description = "Verify clicking the 'Login' opens the Login modal",groups = {"NavBar"})
    public void testLoginLink() {
        // 2. Action: Click the 'Login' link
        homePage.clickLoginLink();
        // 3. Verification: Check that the Login modal is displayed with correct title
        Assert.assertTrue(homePage.isLoginModalDisplayed(), "Login modal is not displayed.");
        Assert.assertEquals(homePage.getLoginModalTitleText(), "Log in",
                "Login modal title is incorrect.");
        // Close the modal after verification
        homePage.closeLoginModal();
    }
    @Test(description = "Verify clicking the 'Sign up' opens the Sign up modal",groups = {"NavBar"})
    public void testSignupLink() {
        // 2. Action: Click the 'Sign up' link
        homePage.clickSignupLink();
        // 3. Verification: Check that the Sign up modal is displayed with correct title
        Assert.assertTrue(homePage.isSignupModalDisplayed(), "Sign up modal is not displayed.");
        Assert.assertEquals(homePage.getSignupModalTitleText(), "Sign up",
                "Sign up modal title is incorrect.");
        // Close the modal after verification
        homePage.closeSignupModal();
    }
}
