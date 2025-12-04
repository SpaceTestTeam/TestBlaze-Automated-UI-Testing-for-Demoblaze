package com.spacetest.demoblaze.base;

import java.util.UUID;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.spacetest.demoblaze.constants.Constants;
import com.spacetest.demoblaze.pages.HomePage;
import com.spacetest.demoblaze.pages.LoginPage;
import com.spacetest.demoblaze.pages.ProductPage;
import com.spacetest.demoblaze.pages.components.ProductCardComponent;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage; // All tests can use this a starting point
    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver();
        driver.get(Constants.BASE_URL);
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }
    @AfterMethod
    public void tearDown() {
        // 4. Quit the driver using your static manager
        DriverManager.quitDriver();
    }
    public String generateRandomUsername() {
        // Creates a short, unique string
        return "testuser_" + UUID.randomUUID().toString().substring(0, 8);
    }
    public String addFirstProductToCart() {
        homePage.navigateToPage(); // Ensure we are on the homepage
        
        // Find the first product and get its name
        ProductCardComponent productCard = homePage.categories().getAllProducts().get(0);
        String productName = productCard.getTitle();
        
        // Click, go to ProductPage, add to cart
        ProductPage productPage = productCard.clickTitle();
        productPage.waitForPageToLoad();
        productPage.clickAddToCart();
        productPage.getAlertTextAndAccept(); // Handle the "Product added" alert
        
        // Go back to the homepage for the next action
        productPage.navBar().clickHomeLink();
        
        return productName;
    }
    public void loginAsValidUser() {
        homePage.navigateToPage();
        LoginPage loginPage = homePage.navBar().clickLoginLink();
        loginPage.loginAs(Constants.VALID_USERNAME, Constants.VALID_PASSWORD);
        homePage.navBar().getWelcomeMessage(); // Wait for login to complete
    }
}   
