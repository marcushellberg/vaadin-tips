package org.vaadin.marcus.ui.pageobjects;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

import static junit.framework.Assert.assertTrue;

public class LoginViewPO extends TestBenchTestCase {
    public LoginViewPO(WebDriver driver) {
        setDriver(driver);
    }

    public MainViewPO login() {
        TextFieldElement usernameTextField = $(TextFieldElement.class).caption("Username").first();
        PasswordFieldElement passwordPasswordField = $(PasswordFieldElement.class).caption("Password").first();
        ButtonElement loginButton = $(ButtonElement.class).caption("Login").first();

        String name = "Test";
        usernameTextField.sendKeys(name);
        passwordPasswordField.sendKeys("test");
        loginButton.click();

        MainViewPO mainViewPO = new MainViewPO(driver);
        assertTrue(mainViewPO.isDisplayed());

        return mainViewPO;
    }
}
