package com.spacetest.demoblaze.base;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseComponent {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BaseComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Or get from Constants
        PageFactory.initElements(driver, this);
    }
}