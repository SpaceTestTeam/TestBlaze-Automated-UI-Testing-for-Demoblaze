package com.spacetest.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.spacetest.demoblaze.base.BasePage;

public class HomePage extends BasePage {
    // --- Constructor ---
    public HomePage(WebDriver driver) {
        super(driver);
    }
    // --- Locators (using PageFactory @FindBy) ---
    @FindBy(id = "nava")
    private WebElement logo;

    @FindBy(xpath = "(//a[@class='nav-link'])[1]")
    private WebElement homeLink;

    // --- Page Actions ---
    public boolean isLogoDisplayed() {
        return logo.isDisplayed();
    }

    public void clickLogo() {
        logo.click();
    }

    public void clickHomeLink() {
        homeLink.click();
    }


}
