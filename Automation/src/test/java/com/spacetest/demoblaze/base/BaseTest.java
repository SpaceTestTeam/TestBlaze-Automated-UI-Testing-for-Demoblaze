package com.spacetest.demoblaze.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.spacetest.demoblaze.constants.Constants;
import com.spacetest.demoblaze.pages.HomePage;

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
}   
