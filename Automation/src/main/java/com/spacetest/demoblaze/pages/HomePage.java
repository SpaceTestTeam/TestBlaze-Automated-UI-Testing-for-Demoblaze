package com.spacetest.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import com.spacetest.demoblaze.base.BasePage;
import com.spacetest.demoblaze.constants.Constants;
import com.spacetest.demoblaze.pages.components.CategoryComponent;
import com.spacetest.demoblaze.pages.components.NavigationBar;
import com.spacetest.demoblaze.pages.components.ProductSlider;

public class HomePage extends BasePage {

    // 1. Declare its components
    private NavigationBar navigationBar;
    private ProductSlider productSlider;
    private CategoryComponent categoryComponent;

    // --- Constructor ---
    public HomePage(WebDriver driver) {
        // This constructor assumes BasePage has a constructor
        // that takes a driver AND a URL.
        super(driver, Constants.BASE_URL); 
        
        // 2. Initialize the components
        this.navigationBar = new NavigationBar(driver);
        this.productSlider = new ProductSlider(driver);
        this.categoryComponent = new CategoryComponent(driver);
    }

    // 3. Create "getter" methods to access the components
    public NavigationBar navBar() {
        return navigationBar;
    }

    public ProductSlider slider() {
        return productSlider;
    }

    public CategoryComponent categories() {
        return categoryComponent;
    }
    
    // You can add a 'visit()' method here if your BasePage doesn't
    // automatically open the URL in its constructor.
    public void visit() {
        driver.get(Constants.BASE_URL);
    }
}