package com.spacetest.demoblaze.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.spacetest.demoblaze.base.BaseTest;
import com.spacetest.demoblaze.pages.ProductPage;
import com.spacetest.demoblaze.pages.components.ProductCardComponent;

public class CategoryMenuTest extends BaseTest {

    @Test(dataProvider = "categoryData", 
          description = "Verify categories show correct products",
          groups = {"Categories", "Regression"},
          dataProviderClass = com.spacetest.demoblaze.base.TestDataProviders.class)
    public void testCategoryProducts(String categoryName, String expectedProduct) {
        System.out.println("Testing category: " + categoryName);
        
        // 1. Action: Click the category and wait
        homePage.categories().clickCategoryAndWait(categoryName);
        
        // 2. Get all product names from the page.
        List<String> productNames = homePage.categories().getProductNames();
        
        // 3. Verification
        Assert.assertTrue(productNames.size() > 0, 
            "No products were displayed for category: " + categoryName);
            
        System.out.println("Found products: " + productNames);
        Assert.assertTrue(productNames.contains(expectedProduct), 
            "Expected product '" + expectedProduct + "' was not found for category: " + categoryName);
    }
    @Test(description = "Verify all product details (name, price, image, description) are displayed", 
          groups = {"Categories", "Regression"})
    public void testAllProductDetailsArePresent() {
        // Use SoftAssert to report *all* failures, not just the first one
        SoftAssert softAssert = new SoftAssert();
        
        // 1. Get all products on the homepage
        List<ProductCardComponent> products = homePage.categories().getAllProducts();
        
        // 2. Verify there are products to test
        Assert.assertTrue(products.size() > 0, "No products found on the page.");
        
        // 3. Loop through each product and check its details
        for (ProductCardComponent product : products) {
            String productName = product.getTitle();
            
            softAssert.assertFalse(product.getTitle().isEmpty(), "Product name is missing.");
            softAssert.assertFalse(product.getPrice().isEmpty(), "Product price is missing for: " + productName);
            softAssert.assertFalse(product.getDescription().isEmpty(), "Product description is missing for: " + productName);
            softAssert.assertTrue(product.isImageDisplayed(), "Product image is not displayed for: " + productName);
            softAssert.assertFalse(product.getImageUrl().isEmpty(), "Product image 'src' is missing for: " + productName);
        }
        
        // 4. Report all failures at the end
        softAssert.assertAll();
    }
    @Test(description = "Verify clicking a product title navigates to the correct product page", 
          groups = {"Categories", "Regression"})
    public void testClickProductTitleNavigates() {
        // 1. Get the first product's details from the homepage
        ProductCardComponent firstProduct = homePage.categories().getAllProducts().get(0);
        String expectedProductName = firstProduct.getTitle();
        String expectedProductPrice = firstProduct.getPrice(); // e.g., "$360"
        
        // 2. Action: Click the title
        ProductPage productPage = firstProduct.clickTitle();
        
        // 3. Verification: Check details on the new page
        String actualName = productPage.getProductName();
        String actualPrice = productPage.getProductPrice(); // e.g., "$360 *includes tax"
        
        Assert.assertEquals(actualName, expectedProductName, "Product name on details page is incorrect.");
        Assert.assertTrue(actualPrice.contains(expectedProductPrice), 
            "Product price on details page is incorrect. Expected to find '" + 
            expectedProductPrice + "' in '" + actualPrice + "'");
    }
    @Test(description = "Verify clicking a product image navigates to the correct product page", 
          groups = {"Categories", "Regression"})
    public void testClickProductImageNavigates() {
        // 1. Get the first product
        ProductCardComponent firstProduct = homePage.categories().getAllProducts().get(0);
        String expectedProductName = firstProduct.getTitle();
        
        // 2. Action: Click the image
        ProductPage productPage = firstProduct.clickImage();
        
        // 3. Verification: Check name on the new page
        String actualName = productPage.getProductName();
        
        Assert.assertEquals(actualName, expectedProductName, 
            "Product name on details page is incorrect after clicking image.");
    }
    @Test(description = "Verify 'Previous' button is not displayed on the first page",
          groups = {"Pagination", "Regression"})
    public void testPrevButtonHiddenOnFirstPage() {
        // 1. Check if 'Next' is there (to confirm we have pagination)
        Assert.assertTrue(homePage.categories().isNextButtonDisplayed(), 
            "'Next' button is not displayed, cannot test pagination.");
        
        // 2. Assert that 'Previous' is NOT displayed
        Assert.assertFalse(homePage.categories().isPrevButtonDisplayed(), 
            "'Previous' button IS visible on the first page.");
    }
    
    @Test(description = "Verify clicking 'Next' and 'Previous' navigates pages",
          groups = {"Pagination", "Regression"})
    public void testPaginationNavigation() {
        // --- Test Case 2: Verify 'Next' button ---

        // 1. Get the list of products on Page 1
        List<String> page1Products = homePage.categories().getProductNames();
        
        // 2. Action: Click the 'Next' page button
        homePage.categories().clickNextPage();
        
        // 3. Get the list of products on Page 2
        List<String> page2Products = homePage.categories().getProductNames();

        // 4. Verification
        Assert.assertNotEquals(page1Products, page2Products, 
            "Product list did not update after clicking 'Next'.");
        
        Assert.assertTrue(homePage.categories().isPrevButtonDisplayed(), 
            "'Previous' button is NOT visible on the second page.");

        
        // --- Test Case 3: Verify 'Previous' button ---

        // 5. Action: Click the 'Previous' page button
        homePage.categories().clickPrevPage();
        
        // 6. Get the products from the (new) current page
        List<String> page1ProductsAgain = homePage.categories().getProductNames();
        
        // 7. Verification
        Assert.assertNotEquals(page2Products, page1ProductsAgain,
            "Product list did not update after clicking 'Previous'.");
            
        Assert.assertEquals(page1Products, page1ProductsAgain,
            "Product list after clicking 'Previous' does not match the original Page 1 list.");
    }
}