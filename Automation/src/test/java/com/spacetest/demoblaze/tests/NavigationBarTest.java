package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.constants.Constants;

public class NavigationBarTest extends BaseTest {

    @Test(description = "Verify clicking the 'Logo' navigates to the homepage", groups = {"NavBar"})
    public void testLogoLink() {
        // 1. Action: (You might need to navigate away first)
        // 2. Action: Click the 'Logo'
        homePage.navBar().clickLogo();
        // 3. Verification
        Assert.assertEquals(homePage.getPageUrl(), Constants.HOMEPAGEURL,
                "Logo link did not navigate back to the homepage.");
    }

    @Test(description = "Verify clicking the 'Home' navigates to the homepage", groups = {"NavBar"})
    public void testHomeLink() {
        // 1. Action: (You might need to navigate away first)
        // 2. Action: Click the 'Home' link
        homePage.navBar().clickHomeLink();
        // 3. Verification
        Assert.assertEquals(homePage.getPageUrl(), Constants.HOMEPAGEURL,
                "Home link did not navigate back to the homepage.");
    }

    @Test(description = "Verify clicking the 'Cart' navigates to the cartpage", groups = {"NavBar"})
    public void testCartLink() {
        // 2. Action: Click the 'Cart' link
        homePage.navBar().clickCartLink();
        // 3. Verification
        Assert.assertEquals(homePage.getPageUrl(), Constants.CARTPAGEURL,
                "Cart link did not navigate to the cartpage.");
    }

    @Test(description = "Verify clicking the 'Contact' opens the contact modal", groups = {"NavBar", "Smoke"})
    public void testContactLink() {
        // 2. Action
        homePage.navBar().clickContactLink();
        // 3. Verification
        Assert.assertTrue(homePage.navBar().isContactModalDisplayed(), "Contact modal is not displayed.");
        Assert.assertEquals(homePage.navBar().getContactModalTitleText(), "New message",
                "Contact modal title is incorrect.");
        // Close the modal
        homePage.navBar().closeContactModal();
    }

    @Test(description = "Verify clicking the 'About Us' opens the About Us modal", groups = {"NavBar"})
    public void testAboutUsLink() {
        // 2. Action
        homePage.navBar().clickAboutUsLink();
        // 3. Verification
        Assert.assertTrue(homePage.navBar().isAboutUsModalDisplayed(), "About Us modal is not displayed.");
        Assert.assertEquals(homePage.navBar().getAboutUsModalTitleText(), "About us",
                "About Us modal title is incorrect.");
        // Close the modal
        homePage.navBar().closeAboutUsModal();
    }

    @Test(description = "Verify clicking the 'Login' opens the Login modal", groups = {"NavBar", "Smoke"})
    public void testLoginLink() {
        // 2. Action
        homePage.navBar().clickLoginLink();
        // 3. Verification
        Assert.assertTrue(homePage.navBar().isLoginModalDisplayed(), "Login modal is not displayed.");
        Assert.assertEquals(homePage.navBar().getLoginModalTitleText(), "Log in",
                "Login modal title is incorrect.");
        // Close the modal
        homePage.navBar().closeLoginModal();
    }

    @Test(description = "Verify clicking the 'Sign up' opens the Sign up modal", groups = {"NavBar", "Smoke"})
    public void testSignupLink() {
        // 2. Action
        homePage.navBar().clickSignupLink();
        // 3. Verification
        Assert.assertTrue(homePage.navBar().isSignupModalDisplayed(), "Sign up modal is not displayed.");
        Assert.assertEquals(homePage.navBar().getSignupModalTitleText(), "Sign up",
                "Sign up modal title is incorrect.");
        // Close the modal
        homePage.navBar().closeSignupModal();
    }
}