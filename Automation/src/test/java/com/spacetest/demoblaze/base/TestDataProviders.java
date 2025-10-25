package com.spacetest.demoblaze.base;

import org.testng.annotations.DataProvider;

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

    /**
     * Provides data for invalid login attempts.
     * @return A 2D array: {username, password, expectedMessage}
     */
    @DataProvider(name = "invalidLoginData")
    public static Object[][] getInvalidLoginData() {
        return new Object[][] {
            {"invalidUser", "pass", "User does not exist."},
            {"testuser", "wrongpass", "Wrong password."}
            // You would need to get the exact alert messages
        };
    }
}