package com.spacetest.demoblaze.base;

import org.testng.annotations.DataProvider;

import com.spacetest.demoblaze.constants.Constants;

public class TestDataProviders {

    /**
     * Provides data for testing product categories.
     * @return A 2D array: {categoryName, expectedProduct}
     */
    @DataProvider(name = "categoryData")
    public static Object[][] getCategoryData() {
        return new Object[][] {
            {"Phones", "Samsung galaxy s6"},
            {"Laptops", "Sony vaio i5"},
            {"Monitors", "Apple monitor 24"}
        };
    }

    @DataProvider(name = "invalidLoginData")
    public static Object[][] getInvalidLoginData() {
        return new Object[][] {
            {"testuser", "wrongpass", "Wrong password."},
            {"invalidUser", "test123", "User does not exist."}
        };
    }

    @DataProvider(name = "emptyLoginData")
    public static Object[][] getEmptyLoginData() {
        return new Object[][] {
            {"", "", "Please fill out Username and Password."},
            {"testuser", "", "Please fill out Username and Password."},
            {"", "test123", "Please fill out Username and Password."}
        };
    }

    @DataProvider(name = "invalidSignupData")
    public static Object[][] getInvalidSignupData() {
        return new Object[][] {
            // Test Case SU02
            {Constants.VALID_USERNAME, Constants.VALID_PASSWORD, "This user already exist."},
            // Test Case SU03
            {"", "", "Please fill out Username and Password."},
            // Test Case SU04
            {"", "testpass123", "Please fill out Username and Password."},
            // Test Case SU05
            {"testuser123", "", "Please fill out Username and Password."}
        };
    }
}