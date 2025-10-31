package com.spacetest.demoblaze.tests;

import java.util.List;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.constants.Constants;
import com.spacetest.demoblaze.pages.CartPage;
import com.spacetest.demoblaze.pages.ProductPage;
import com.spacetest.demoblaze.pages.components.CartItemComponent;
import com.spacetest.demoblaze.pages.components.OrderConfirmationModal;
import com.spacetest.demoblaze.pages.components.PlaceOrderModal;
import com.spacetest.demoblaze.pages.components.ProductCardComponent;

public class CartPageTest extends BaseTest {

    @Test(description = "CP_01: Verify cart page navigation", groups = {"Cart", "Smoke"})
    public void testCartPageNavigation() {
        CartPage cartPage = homePage.navBar().clickCartLink();
        cartPage.waitForPageToLoad();
        Assert.assertEquals(driver.getCurrentUrl(), Constants.CARTPAGEURL);
    }

    @Test(description = "CP_02, CP_03: Verify product details and total price", groups = {"Cart", "Smoke"})
    public void testProductDetailsAndTotal() {
        // 1. Setup
        String productName = addFirstProductToCart();
        
        // 2. Act
        CartPage cartPage = homePage.navBar().clickCartLink();
        cartPage.waitForPageToLoad();
        List<CartItemComponent> items = cartPage.getCartItems();
        
        // 3. Assert
        Assert.assertEquals(items.size(), 1, "Cart should have one item.");
        CartItemComponent item = items.get(0);
        String itemPrice = item.getPrice();
        
        Assert.assertEquals(item.getName(), productName);
        Assert.assertEquals(cartPage.getTotalPrice(), itemPrice, "Total price should match the single item price.");
    }

    @Test(description = "CP_04, CP_17: Verify removing product", groups = {"Cart", "Regression"})
    public void testRemovingProduct() {
        String productName = addFirstProductToCart();
        CartPage cartPage = homePage.navBar().clickCartLink();
        cartPage.waitForPageToLoad();
        
        // Act
        cartPage.deleteItemByName(productName);
        
        // Assert
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after deleting the item.");
    }
    
    // @Test(description = "CP_05: Verify 'Place Order' button", groups = {"Cart", "Smoke"})
    // public void testPlaceOrderSuccess() {
    //     addFirstProductToCart();
    //     CartPage cartPage = homePage.navBar().clickCartLink();
        
    //     PlaceOrderModal orderModal = cartPage.clickPlaceOrder();
    //     orderModal.fillOrderForm("Test User", "123456789");
    //     orderModal.clickPurchase();
        
    //     OrderConfirmationModal confirmModal = new OrderConfirmationModal(driver);
    //     Assert.assertEquals(confirmModal.getSuccessMessage(), "Thank you for your purchase!");
    //     confirmModal.clickOK();
        
    //     // Assert we are redirected to the homepage
    //     Assert.assertEquals(driver.getCurrentUrl(), Constants.HOMEPAGEURL, "Did not redirect to homepage after purchase.");
    // }

    // @Test(description = "CP_06, CP_10: Verify checkout with empty cart", groups = {"Cart", "Regression"})
    // public void testCheckoutWithEmptyCart() {
    //     CartPage cartPage = homePage.navBar().clickCartLink();
    //     cartPage.waitForPageToLoad();
        
    //     // Assert cart is empty
    //     Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty at the start of the test.");
        
    //     // Act
    //     PlaceOrderModal orderModal = cartPage.clickPlaceOrder();
    //     orderModal.clickPurchase(); // Click purchase with empty form
        
    //     // Assert
    //     String alertText = orderModal.getAlertTextAndAccept();
    //     Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");
    // }
    
    @Test(description = "CP_08: Verify cart persistence after refresh", groups = {"Cart", "Regression"})
    public void testCartPersistenceAfterRefresh() {
        String productName = addFirstProductToCart();
        @SuppressWarnings("unused")
        CartPage cartPage = homePage.navBar().clickCartLink();
        
        // Act
        driver.navigate().refresh();
        CartPage refreshedCartPage = new CartPage(driver); // Re-init page object
        
        // Assert
        Assert.assertFalse(refreshedCartPage.isCartEmpty(), "Cart is empty after refresh.");
        Assert.assertEquals(refreshedCartPage.getCartItems().get(0).getName(), productName);
    }
    
    @Test(description = "CP_09: Verify cart persistence after logout/login", groups = {"Cart", "Regression"})
    public void testCartPersistenceAfterLogin() {
        // 1. Setup
        loginAsValidUser();
        String productName = addFirstProductToCart();
        homePage.navBar().clickLogout();
        
        // 2. Act
        loginAsValidUser(); // Login again
        CartPage cartPage = homePage.navBar().clickCartLink();
        
        // 3. Assert
        Assert.assertFalse(cartPage.isCartEmpty(), "Cart is empty after logging back in.");
        Assert.assertEquals(cartPage.getCartItems().get(0).getName(), productName);
    }

    @Test(description = "CP_11: Verify cart layout on mobile", groups = {"Cart", "Responsive"})
    public void testCartLayoutOnMobile() {
        driver.manage().window().setSize(new Dimension(390, 844));
        CartPage cartPage = homePage.navBar().clickCartLink();
        cartPage.waitForPageToLoad();
        
        Assert.assertTrue(cartPage.isPlaceOrderButtonVisible(), "'Place Order' button not visible on mobile.");
    }
    
    @Test(description = "CP_12: Verify 'Home' button from cart", groups = {"Cart", "Navigation"})
    public void testNavHomeFromCart() {
        CartPage cartPage = homePage.navBar().clickCartLink();
        cartPage.waitForPageToLoad();
        
        cartPage.navBar().clickHomeLink();
        Assert.assertEquals(driver.getCurrentUrl(), Constants.HOMEPAGEURL);
    }
    
    @Test(description = "CP_14: Verify duplicate product add", groups = {"Cart", "Regression"})
    public void testDuplicateProductAdd() {
        // 1. Act
        addFirstProductToCart(); // Add item 1
        addFirstProductToCart(); // Add same item again
        
        // 2. Assert
        CartPage cartPage = homePage.navBar().clickCartLink();
        // This site's behavior is to add duplicates as separate rows
        Assert.assertEquals(cartPage.getCartItems().size(), 2, "Cart should show two rows for the duplicate item.");
    }
    
    @Test(description = "CP_16: Verify currency consistency", groups = {"Cart", "Regression"})
    public void testCurrencyConsistency() {
        SoftAssert softAssert = new SoftAssert();
        
        // 1. Get prices from Home, Detail, and Cart
        ProductCardComponent productCard = homePage.categories().getAllProducts().get(0);
        String homePrice = productCard.getPrice();
        
        ProductPage productPage = productCard.clickTitle();
        String detailPrice = productPage.getProductPrice();
        
        productPage.clickAddToCart();
        productPage.getAlertTextAndAccept();
        
        CartPage cartPage = productPage.navBar().clickCartLink();
        String cartPrice = cartPage.getCartItems().get(0).getPrice();
        
        // 2. Assert
        softAssert.assertTrue(homePrice.contains("$"), "Currency symbol missing on homepage.");
        softAssert.assertTrue(detailPrice.contains("$"), "Currency symbol missing on detail page.");
        softAssert.assertTrue(cartPrice.contains("$"), "Currency symbol missing in cart.");
        
        softAssert.assertEquals(cartPrice, homePrice, "Price in cart does not match price on homepage.");
        softAssert.assertTrue(detailPrice.contains(homePrice), "Detail page price does not contain the homepage price.");
        
        softAssert.assertAll();
    }
}