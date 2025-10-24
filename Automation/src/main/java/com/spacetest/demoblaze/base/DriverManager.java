package com.spacetest.demoblaze.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class DriverManager  {
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<WebDriver>();
    public static WebDriver getDriver() {
        if(driverThread.get() == null) {
            WebDriver driver = new ChromeDriver();
            driverThread.set(driver);
        }
        return driverThread.get();
    }
    public static void quitDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove(); // This is crucial to prevent memory leaks
        }
    }
}   
