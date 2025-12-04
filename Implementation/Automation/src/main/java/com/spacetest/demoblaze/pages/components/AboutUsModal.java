package com.spacetest.demoblaze.pages.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.spacetest.demoblaze.base.BaseComponent;

public class AboutUsModal extends BaseComponent {

    // --- Constructor ---
    public AboutUsModal(WebDriver driver) {
        super(driver);
        // Wait for the modal title to be visible before proceeding
        wait.until(ExpectedConditions.visibilityOf(modalTitle));
    }

    // --- Locators ---
    @FindBy(id = "videoModalLabel")
    private WebElement modalTitle;

    // The modal's 'X' icon (for AU03)
    @FindBy(xpath = "//div[@id='videoModal']//button[@class='close']")
    private WebElement closeIcon;

    // The 'Close' button in the footer (for AU02)
    @FindBy(xpath = "(//button[@type='button'][normalize-space()='Close'])[4]")
    private WebElement closeButton;

    // --- Video Player Locators ---
    
    // The "Play" button
    @FindBy(css = ".vjs-big-play-button")
    private WebElement playButton;

    // The poster image (this disappears when 'play' is clicked)
    @FindBy(css = ".vjs-poster")
    private WebElement videoPosterImage;

    // --- Action Methods ---
    
    public void clickPlayVideo() {
        wait.until(ExpectedConditions.elementToBeClickable(playButton)).click();
    }
    
    public void clickCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(modalTitle)); // Wait for it to close
    }
    
    public void clickCloseIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(closeIcon)).click();
        wait.until(ExpectedConditions.invisibilityOf(modalTitle)); // Wait for it to close
    }

    // --- Verification & Helper Methods ---
    
    public String getModalTitleText() {
        return modalTitle.getText();
    }
    
    public boolean isVideoPlayerDisplayed() {
        return videoPosterImage.isDisplayed();
    }
    
    /**
     * Verifies if the video is playing by checking if the poster image is gone.
     * @return true if the poster image is invisible, false otherwise.
     */
    public boolean isVideoPlaying() {
        try {
            // Wait for the poster image to become invisible
            return wait.until(ExpectedConditions.invisibilityOf(videoPosterImage));
        } catch (Exception e) {
            return false; // Timed out, poster is still visible
        }
    }
}