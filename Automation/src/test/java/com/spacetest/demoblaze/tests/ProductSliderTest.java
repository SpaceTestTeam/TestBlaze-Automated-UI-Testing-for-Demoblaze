package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.spacetest.demoblaze.base.BaseTest;

public class ProductSliderTest extends BaseTest {

    @Test(description = "Verify slider changes images automatically", groups = {"Carousel", "Regression"})
    public void testCarouselAutoSlide() throws InterruptedException {
        // 1. Get the source of the first image
        String firstImageSrc = homePage.slider().getActiveCarouselImageSrc();
        System.out.println("First image: " + firstImageSrc);

        // 2. Wait for the slide to change automatically
        homePage.slider().waitForCarouselChange(firstImageSrc);
        
        // 3. Get the source of the second image
        String secondImageSrc = homePage.slider().getActiveCarouselImageSrc();
        System.out.println("Second image: " + secondImageSrc);

        // 4. Assert that the images are different
        Assert.assertNotEquals(firstImageSrc, secondImageSrc, 
            "Slider did not change image automatically.");
    }

    @Test(description = "Verify slider arrows change images on click", groups = {"Carousel", "Regression"})
    public void testCarouselArrowNavigation() {
        // 1. Get the source of the initial image
        String firstImageSrc = homePage.slider().getActiveCarouselImageSrc();
        System.out.println("First image: " + firstImageSrc);

        // 2. Click the 'Next' arrow and wait
        homePage.slider().clickNextCarouselButton();
        homePage.slider().waitForCarouselChange(firstImageSrc);
        String secondImageSrc = homePage.slider().getActiveCarouselImageSrc();
        System.out.println("Second image (after Next): " + secondImageSrc);
        
        // 3. Assert the image changed
        Assert.assertNotEquals(firstImageSrc, secondImageSrc, 
            "Slider did not change image on Next arrow click.");

        // 4. Click the 'Previous' arrow and wait
        homePage.slider().clickPrevCarouselButton();
        homePage.slider().waitForCarouselChange(secondImageSrc);
        String thirdImageSrc = homePage.slider().getActiveCarouselImageSrc();
        System.out.println("Third image (after Previous): " + thirdImageSrc);

        // 5. Assert that we are back to the first image
        Assert.assertEquals(thirdImageSrc, firstImageSrc, 
            "Slider did not return to the first image on Previous arrow click.");
    }
}