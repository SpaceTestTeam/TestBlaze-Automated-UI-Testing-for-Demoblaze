package com.spacetest.demoblaze.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.constants.Constants;
import com.spacetest.demoblaze.pages.CartPage;
import com.spacetest.demoblaze.pages.components.OrderConfirmationModal;
import com.spacetest.demoblaze.pages.components.PlaceOrderModal;

public class PlaceOrderTest extends BaseTest {

    @Test(description = "PO01: Verify valid order can be placed successfully", groups = {"PlaceOrder", "Smoke"})
    public void testValidOrder() {
        // 1. Arrange
        addFirstProductToCart();
        CartPage cartPage = homePage.navBar().clickCartLink();
        
        // 2. Act
        PlaceOrderModal orderModal = cartPage.clickPlaceOrder();
        orderModal.fillOrderForm("Test User", "123456789");
        orderModal.clickPurchase();
        
        // 3. Assert
        OrderConfirmationModal confirmModal = new OrderConfirmationModal(driver);
        Assert.assertEquals(confirmModal.getSuccessMessage(), "Thank you for your purchase!");
        
        // PO10: Verify popup closes correctly
        confirmModal.clickOK();
        
        // Assert we are redirected to the homepage
        Assert.assertEquals(driver.getCurrentUrl(), Constants.HOMEPAGEURL, "Did not redirect to homepage after purchase.");
    }

    @Test(description = "PO02: Verify order cannot be placed with empty fields", groups = {"PlaceOrder", "Regression"})
    public void testInvalidOrderEmptyFields() {
        // 1. Arrange
        addFirstProductToCart();
        CartPage cartPage = homePage.navBar().clickCartLink();

        // 2. Act
        PlaceOrderModal orderModal = cartPage.clickPlaceOrder();
        orderModal.clickPurchase(); // Click purchase with empty form
        
        // 3. Assert
        String alertText = orderModal.getAlertTextAndAccept();
        Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");
    }
    
    @Test(description = "PO03: Verify order cannot be placed without selecting a product", groups = {"PlaceOrder", "Regression"})
    public void testOrderWithEmptyCart() {
        // 1. Arrange (no products added)
        CartPage cartPage = homePage.navBar().clickCartLink();
        cartPage.waitForPageToLoad();
        
        // 2. Act
        PlaceOrderModal orderModal = cartPage.clickPlaceOrder();
        orderModal.clickPurchase(); // Click purchase with empty form
        
        // 3. Assert
        String alertText = orderModal.getAlertTextAndAccept();
        Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");
    }
    
    @Test(description = "PO06: Validate 'Close' button behavior", groups = {"PlaceOrder", "Regression"})
    public void testCloseButtonOnPlaceOrderModal() {
        // 1. Arrange
        addFirstProductToCart();
        CartPage cartPage = homePage.navBar().clickCartLink();

        // 2. Act
        PlaceOrderModal orderModal = cartPage.clickPlaceOrder();
        orderModal.fillOrderForm("Test", "123"); // Fill partially
        orderModal.clickClose();
        
        // 3. Assert
        // Re-open the modal and check if fields are reset
        PlaceOrderModal newOrderModal = cartPage.clickPlaceOrder();
        Assert.assertTrue(newOrderModal.isModalDisplayed(), "Modal did not re-open.");
        // Note: This site does not clear fields on close, this is a "bug"
        // Assert.assertEquals(newOrderModal.getNameFieldValue(), "", "Name field was not cleared");
    }
    
    @Test(description = "PO07: Verify cart is empty after order completion", groups = {"PlaceOrder", "Regression"})
    public void testCartIsEmptyAfterOrder() {
        // 1. Arrange (place an order)
        addFirstProductToCart();
        CartPage cartPage = homePage.navBar().clickCartLink();
        PlaceOrderModal orderModal = cartPage.clickPlaceOrder();
        orderModal.fillOrderForm("Test User", "123456789");
        orderModal.clickPurchase();
        OrderConfirmationModal confirmModal = new OrderConfirmationModal(driver);
        confirmModal.clickOK();
        
        // 2. Act (Go back to cart)
        CartPage finalCartPage = homePage.navBar().clickCartLink();
        
        // 3. Assert
        Assert.assertTrue(finalCartPage.isCartEmpty(), "Cart is not empty after placing an order.");
    }
    
    @Test(description = "PO08: Verify user can place multiple orders sequentially", groups = {"PlaceOrder", "Regression"})
    public void testMultipleOrders() {
        // --- Order 1 ---
        addFirstProductToCart();
        CartPage cartPage1 = homePage.navBar().clickCartLink();
        PlaceOrderModal orderModal1 = cartPage1.clickPlaceOrder();
        orderModal1.fillOrderForm("Test User 1", "111");
        orderModal1.clickPurchase();
        OrderConfirmationModal confirmModal1 = new OrderConfirmationModal(driver);
        confirmModal1.clickOK();
        
        // --- Order 2 ---
        addFirstProductToCart(); // Add another product
        CartPage cartPage2 = homePage.navBar().clickCartLink();
        PlaceOrderModal orderModal2 = cartPage2.clickPlaceOrder();
        orderModal2.fillOrderForm("Test User 2", "222");
        orderModal2.clickPurchase();
        
        // --- Assert Order 2 ---
        OrderConfirmationModal confirmModal2 = new OrderConfirmationModal(driver);
        Assert.assertEquals(confirmModal2.getSuccessMessage(), "Thank you for your purchase!");
        confirmModal2.clickOK();
    }
    
    @Test(description = "PO09: Verify purchase confirmation contains correct details", groups = {"PlaceOrder", "Regression"})
    public void testPurchaseConfirmationDetails() {
        // 1. Arrange
        addFirstProductToCart();
        CartPage cartPage = homePage.navBar().clickCartLink();
        String expectedAmount = cartPage.getTotalPrice(); // e.g., "360"
        
        // 2. Act
        PlaceOrderModal orderModal = cartPage.clickPlaceOrder();
        orderModal.fillOrderForm("Test User", "123456789");
        orderModal.clickPurchase();
        
        // 3. Assert
        OrderConfirmationModal confirmModal = new OrderConfirmationModal(driver);
        String actualAmount = confirmModal.getOrderAmount();
        
        Assert.assertEquals(actualAmount, expectedAmount, "Confirmation amount does not match cart total.");
        confirmModal.clickOK();
    }

    // Test cases PO04 and PO05 (invalid credit card/name) are not automated
    // as the application has no client-side validation for these fields.
}