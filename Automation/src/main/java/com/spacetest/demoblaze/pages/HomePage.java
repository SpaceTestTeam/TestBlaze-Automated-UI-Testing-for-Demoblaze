package com.spacetest.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.spacetest.demoblaze.base.BasePage;
import com.spacetest.demoblaze.constants.Constants;

public class HomePage extends BasePage {
    // --- Constructor ---
    public HomePage(WebDriver driver) {
        super(driver,Constants.BASE_URL);
    }
    // --- Locators (using PageFactory @FindBy) ---
    @FindBy(id = "nava")
    private WebElement logo;

    @FindBy(xpath = "(//a[@class='nav-link'])[1]")
    private WebElement homeLink;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    @FindBy(xpath= "(//a[normalize-space()='Contact'])[1]")
    private WebElement contactLink;

    @FindBy(id = "exampleModalLabel")
    private WebElement contactModalTitle;

    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Close'])[1]")
    private WebElement contactModalCloseButton;

    @FindBy(xpath = "//a[normalize-space()='About us']")
    private WebElement aboutUsLink;

    @FindBy(id = "videoModalLabel")
    private WebElement aboutUsModalTitle;

    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Close'])[4]")
    private WebElement aboutUsModalCloseButton;

    @FindBy(id = "login2")
    private WebElement loginLink;

    @FindBy(id = "logInModalLabel")
    private WebElement loginModalTitle;

    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Close'])[3]")
    private WebElement loginModalCloseButton;

    @FindBy(id = "signin2")
    private WebElement signupLink;

    @FindBy(id = "signInModalLabel")
    private WebElement signupModalTitle;

    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Close'])[2]")
    private WebElement signupModalCloseButton;
    

    // --- Page Actions ---
    public void clickLogo() {
        logo.click();
    }

    public void clickHomeLink() {
        homeLink.click();
    }
    public void clickCartLink() {
        cartLink.click();
    }
    public void clickContactLink() {
        contactLink.click();
        wait.until(ExpectedConditions.visibilityOf(contactModalTitle));
    }
    public boolean isContactModalDisplayed(){
        return contactModalTitle.isDisplayed();
    }
    public String getContactModalTitleText(){
        return contactModalTitle.getText();
    }
    public void closeContactModal() {
        contactModalCloseButton.click();
    }

    public void clickAboutUsLink() {
        aboutUsLink.click();
        wait.until(ExpectedConditions.visibilityOf(aboutUsModalTitle));
    }
    public boolean isAboutUsModalDisplayed(){
        return aboutUsModalTitle.isDisplayed();
    }
    public String getAboutUsModalTitleText(){
        return aboutUsModalTitle.getText();
    }
    public void closeAboutUsModal() {
        aboutUsModalCloseButton.click();
    }

    public void clickLoginLink() {
        loginLink.click();
        wait.until(ExpectedConditions.visibilityOf(loginModalTitle));
    }
    public boolean isLoginModalDisplayed(){
        return loginModalTitle.isDisplayed();
    }
    public String getLoginModalTitleText(){
        return loginModalTitle.getText();
    }
    public void closeLoginModal() {
        loginModalCloseButton.click();
    }

    public void clickSignupLink() {
        signupLink.click();
        wait.until(ExpectedConditions.visibilityOf(signupModalTitle));
    }
    public boolean isSignupModalDisplayed(){
        return signupModalTitle.isDisplayed();
    }
    public String getSignupModalTitleText(){
        return signupModalTitle.getText();
    }
    public void closeSignupModal() {
        signupModalCloseButton.click();
    }
}
