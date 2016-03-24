package org.vaadin.marcus.ui.pageobjects;

import com.vaadin.testbench.TestBench;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TBUtils {

    private static final String TARGET_URL = "http://localhost:8080/demo-ui/?restartApplication=true";

    public static LoginViewPO openInitialView() {
        WebDriver driver = TestBench.createDriver(new FirefoxDriver());
        driver.get(TARGET_URL);
        LoginViewPO initialView = new LoginViewPO(driver);
        return initialView;
    }
}