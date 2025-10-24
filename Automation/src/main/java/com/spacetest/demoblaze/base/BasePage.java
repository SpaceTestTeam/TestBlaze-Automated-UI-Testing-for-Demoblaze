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
    public BasePage(WebDriver driver , String pageUrl) {
        this.driver = driver;
        this.pageUrl = pageUrl;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Initialize the WebElements defined with @FindBy
        PageFactory.initElements(driver, this);
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
