package com.spacetest.demoblaze.tests;

import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.spacetest.demoblaze.base.BaseTest;
import java.util.List;

public class ResponsiveLayoutTest extends BaseTest {

    @Test(description = "Verify responsive layout behavior when resizing browser window",
          groups = {"Responsive", "Regression"})
    public void testLayoutAdjustsOnResize() {
        
        // 1. Get the width at default (desktop) size
        int initialWidth = homePage.categories().getProductGridWidth();
        System.out.println("Initial (Desktop) Width: " + initialWidth);
        
        // 2. Resize to a tablet viewport
        driver.manage().window().setSize(new Dimension(768, 1024));
        int tabletWidth = homePage.categories().getProductGridWidth();
        System.out.println("Tablet Width: " + tabletWidth);

        // 3. Verification
        Assert.assertTrue(tabletWidth < initialWidth, 
            "Layout did not adjust for tablet size. Width remained " + tabletWidth);
    }
    
    @Test(description = "Verify responsive layout on mobile devices",
          groups = {"Responsive", "Regression"})
    public void testProductsLayoutOnMobile() {
        
        // 1. Get the width at default (desktop) size
        int initialWidth = homePage.categories().getProductGridWidth();
        System.out.println("Initial (Desktop) Width: " + initialWidth);
        
        // 2. Resize to a mobile viewport (e.g., iPhone 12 Pro)
        driver.manage().window().setSize(new Dimension(390, 844));
        int mobileWidth = homePage.categories().getProductGridWidth();
        System.out.println("Mobile Width: " + mobileWidth);

        // 3. Verification
        Assert.assertTrue(mobileWidth < initialWidth, 
            "Layout did not adjust for mobile size. Width remained " + mobileWidth);
            
        // 4. Verify products are still displayed
        List<String> productNames = homePage.categories().getProductNames();
        System.out.println("Products on Mobile: " + productNames);
        Assert.assertTrue(productNames.size() > 0, 
            "Products are not displayed on mobile layout.");
    }
}