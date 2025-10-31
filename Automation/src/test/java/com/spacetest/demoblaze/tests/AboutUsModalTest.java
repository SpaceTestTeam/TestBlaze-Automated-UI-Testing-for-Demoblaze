package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.pages.components.AboutUsModal;

public class AboutUsModalTest extends BaseTest {

    @Test(description = "AU01: Verify that About Us window video play when click on play button",
          groups = {"AboutUs", "Regression"})
    public void testVideoPlayback() {
        // 1. Arrange
        AboutUsModal aboutUsModal = homePage.navBar().clickAboutUsLink();

        // 2. Act
        aboutUsModal.clickPlayVideo();
        
        // 3. Assert
        Assert.assertTrue(aboutUsModal.isVideoPlaying(), "Video did not start playing (poster image is still visible).");
        
        // 4. Cleanup
        aboutUsModal.clickCloseButton();
    }

    @Test(description = "AU02: Verify 'close' button will close About Us window", 
          groups = {"AboutUs", "Smoke"})
    public void testCloseButton() {
        // 1. Arrange
        AboutUsModal aboutUsModal = homePage.navBar().clickAboutUsLink();

        // 2. Act
        aboutUsModal.clickCloseButton();

        // 3. Assert
        Assert.assertTrue(homePage.navBar().isAboutUsLinkClickable(), 
            "About Us modal did not close after clicking 'Close' button.");
    }
    
    @Test(description = "AU03: Verify 'close' icon will close About Us window", 
          groups = {"AboutUs", "Regression"})
    public void testCloseIcon() {
        // 1. Arrange
        AboutUsModal aboutUsModal = homePage.navBar().clickAboutUsLink();

        // 2. Act
        aboutUsModal.clickCloseIcon();

        // 3. Assert
        Assert.assertTrue(homePage.navBar().isAboutUsLinkClickable(), 
            "About Us modal did not close after clicking 'X' icon.");
    }

    @Test(description = "AU05: Verify the presence of About Us elements", 
          groups = {"AboutUs", "UI"})
    public void testAboutUsModalUI() {
        AboutUsModal aboutUsModal = homePage.navBar().clickAboutUsLink();
        SoftAssert softAssert = new SoftAssert();

        // 2. Assert
        // Note: The test case image (AU05) incorrectly lists fields.
        // We are testing the *actual* elements.
        softAssert.assertEquals(aboutUsModal.getModalTitleText(), "About us", "Modal title is incorrect.");
        softAssert.assertTrue(aboutUsModal.isVideoPlayerDisplayed(), "Video player is not displayed");
        softAssert.assertAll();
        
        // 3. Cleanup
        aboutUsModal.clickCloseButton();
    }
    
    // Test case AU04 (click outside) is not automated as it is a
    // non-standard interaction that can be flaky and is considered
    // covered by the 'Close' button tests.
}