package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.spacetest.demoblaze.base.BaseTest;

public class HomePageTest extends BaseTest {
    @Test(description = "Verify clicking the 'Logo' navigates to the homepage")
    public void testLogoLink() {
        // 2. Action: Click the 'Logo'
        homePage.clickLogo();
        // 3. Verification: Check that we are back at the main homepage URL
        Assert.assertEquals(homePage.getPageUrl(), "https://www.demoblaze.com/index.html",
                "Logo link did not navigate back to the homepage.");
    }

    @Test(description = "Verify clicking the 'Home' navigates to the homepage")
    public void testContactLink() {
        // 2. Action: Click the 'Contact' link
        homePage.clickHomeLink();
        // 3. Verification: Check that we are back at the main homepage URL
        Assert.assertEquals(homePage.getPageUrl(), "https://www.demoblaze.com/index.html",
                "Logo link did not navigate back to the homepage.");
    }
}
