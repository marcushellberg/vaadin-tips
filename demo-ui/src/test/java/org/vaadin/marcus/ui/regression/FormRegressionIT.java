package org.vaadin.marcus.ui.regression;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.ComboBoxElement;
import com.vaadin.testbench.elements.GridElement;
import com.vaadin.testbench.elements.NotificationElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vaadin.marcus.ui.pageobjects.MainViewPO;
import org.vaadin.marcus.ui.pageobjects.TBUtils;

import static org.junit.Assert.assertTrue;

public class FormRegressionIT extends TestBenchTestCase {

    private MainViewPO mainViewPO;

    @Before
    public void setUp() {
        mainViewPO = TBUtils.openInitialView().login();

        // our context is now in the main view
        setDriver(mainViewPO.getDriver());
    }

    @Test
    public void testForm() {
        // Inlining is fine for the first test, but you should pretty quickly
        // refactor this into a page object for the FormView
        $(ButtonElement.class).caption("Form").first().click();

        ComboBoxElement orderStatusComboBox = $(ComboBoxElement.class).caption("Order status").first();
        ComboBoxElement productComboBox = $(ComboBoxElement.class).caption("Product").first();
        GridElement listItemsGrid = $(GridElement.class).first();
        ButtonElement addButton = $(ButtonElement.class).caption("Add").first();
        ButtonElement addOrderButton = $(ButtonElement.class).caption("Add Order").first();

        orderStatusComboBox.openPopup();
        orderStatusComboBox.selectByText(orderStatusComboBox.getPopupSuggestions().get(0));

        productComboBox.openPopup();
        String selectedProduct = productComboBox.getPopupSuggestions().get(0);
        productComboBox.selectByText(selectedProduct);

        addButton.click();

        assertTrue(listItemsGrid.getRow(0).getText().contains(selectedProduct));

        addOrderButton.click();

        NotificationElement notification = $(NotificationElement.class).first();
        assertTrue(notification.getCaption().contains("Saved"));
        notification.close();
    }

    @After
    public void tearDown() {
        mainViewPO.getDriver().quit();
    }
}
