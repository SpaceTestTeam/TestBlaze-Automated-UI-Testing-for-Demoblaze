package com.spacetest.demoblaze.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.spacetest.demoblaze.constants.Constants;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String pageUrl = Constants.BASE_URL;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));
        // Initialize the WebElements defined with @FindBy
        PageFactory.initElements(driver, this);
    }
    public BasePage(WebDriver driver , String pageUrl) {
        this(driver);
        this.pageUrl = pageUrl;
        navigateToPage();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateToPage() {
        driver.get(pageUrl);
    }
}
