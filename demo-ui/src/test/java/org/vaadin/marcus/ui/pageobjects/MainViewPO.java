package org.vaadin.marcus.ui.pageobjects;

import com.vaadin.testbench.TestBenchTestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainViewPO extends TestBenchTestCase {
    public MainViewPO(WebDriver driver) {
        setDriver(driver);
    }

    public boolean isDisplayed() {
        return getDriver().findElement(By.className("valo-menu-title")).isDisplayed();
    }
}
