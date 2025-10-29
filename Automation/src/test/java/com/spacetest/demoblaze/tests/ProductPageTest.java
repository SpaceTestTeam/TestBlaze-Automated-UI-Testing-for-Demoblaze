package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.constants.Constants;
import com.spacetest.demoblaze.pages.CartPage;
import com.spacetest.demoblaze.pages.ProductPage;
import com.spacetest.demoblaze.pages.components.ProductCardComponent;

public class ProductPageTest extends BaseTest {

    @Test(description = "PD_01, PD_02, PD_03: Verify product details page info is correct",
          groups = {"ProductPage", "Smoke"})
    public void testProductDetailsAreCorrect() {
        SoftAssert softAssert = new SoftAssert();

        // 1. Get product details from homepage
        ProductCardComponent productCard = homePage.categories().getAllProducts().get(0);
        String expectedName = productCard.getTitle();
        String expectedPrice = productCard.getPrice(); // e.g., "$360"
        
        // 2. Navigate to Product Page
        ProductPage productPage = productCard.clickTitle();
        productPage.waitForPageToLoad();
        
        // 3. Verifications
        softAssert.assertEquals(productPage.getProductName(), expectedName, "Product name is incorrect.");
        softAssert.assertTrue(productPage.getProductPrice().contains(expectedPrice), 
            "Product price is incorrect. Expected '" + productPage.getProductPrice() + "' to contain '" + expectedPrice + "'.");
        softAssert.assertTrue(productPage.isProductImageDisplayed(), "Product image is not displayed.");
        softAssert.assertFalse(productPage.getProductDescription().isEmpty(), "Product description is empty.");
        
        // 4. Report all failures
        softAssert.assertAll();
    }
    
    @Test(description = "PD_04, PD_07, PD_08: Verify 'Add to Cart' functionality (with alert)",
          groups = {"ProductPage", "Smoke"})
    public void testAddToCart() {
        // 1. Navigate to Product Page
        ProductPage productPage = homePage.categories().getAllProducts().get(0).clickTitle();
        String expectedName = productPage.getProductName();
        
        // 2. Action: Add to cart
        productPage.clickAddToCart();
        String alertText = productPage.getAlertTextAndAccept();
        
        // 3. Verify Alert
        Assert.assertEquals(alertText, "Product added", "Alert text after adding to cart is incorrect.");
        
        // 4. Verify product is in cart
        CartPage cartPage = productPage.navBar().clickCartLink();
        cartPage.waitForPageToLoad();
        String cartItemName = cartPage.getFirstProductNameInCart();
        
        Assert.assertEquals(cartItemName, expectedName, "Product was not found in the cart.");
    }

    @Test(description = "PD_05: Verify navigation back to homepage", groups = {"ProductPage", "Regression"})
    public void testNavToHomeFromProductPage() {
        // 1. Navigate to Product Page
        ProductPage productPage = homePage.categories().getAllProducts().get(0).clickTitle();
        productPage.waitForPageToLoad();

        // 2. Action: Click "Home" link
        productPage.navBar().clickHomeLink();
        
        // 3. Verify
        Assert.assertEquals(driver.getCurrentUrl(), Constants.HOMEPAGEURL, "Did not navigate back to homepage.");
    }
    
    @Test(description = "PD_06: Verify navigation between products", groups = {"ProductPage", "Regression"})
    public void testNavBetweenProducts() {
        // 1. Navigate to first product
        ProductPage productPage1 = homePage.categories().getAllProducts().get(0).clickTitle();
        String name1 = productPage1.getProductName();
        
        // 2. Go back home
        productPage1.navBar().clickHomeLink();
        homePage.categories().clickCategoryAndWait("Laptops"); // Click a category to refresh
        
        // 3. Navigate to second product
        ProductPage productPage2 = homePage.categories().getAllProducts().get(0).clickTitle();
        String name2 = productPage2.getProductName();
        
        // 4. Verify
        Assert.assertNotEquals(name1, name2, "Did not navigate to a different product.");
    }
    
    @Test(description = "PD_09: Verify product page refresh", groups = {"ProductPage", "Regression"})
    public void testProductPageRefresh() {
        // 1. Navigate to product and get name
        ProductPage productPage = homePage.categories().getAllProducts().get(0).clickTitle();
        String name1 = productPage.getProductName();
        
        // 2. Action: Refresh the page
        driver.navigate().refresh();
        
        // 3. Re-initialize ProductPage and get name again
        ProductPage refreshedPage = new ProductPage(driver);
        String name2 = refreshedPage.getProductName();
        
        // 4. Verify
        Assert.assertEquals(name2, name1, "Product details changed after refresh.");
    }
}