package com.spacetest.demoblaze.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
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

}
