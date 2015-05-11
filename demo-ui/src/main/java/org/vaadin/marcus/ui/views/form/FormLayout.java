package org.vaadin.marcus.ui.views.form;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.ComboBox;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.util.NonEmptyCollectionValidator;

import java.util.Arrays;

public class FormLayout extends com.vaadin.ui.FormLayout {

    // If your field names are not the same as the property ids, remember to annotate them.
    @PropertyId("status")
    private final ComboBox statusField;
    @PropertyId("lineItems")
    private final LineItemField lineItemsField;

    public FormLayout() {
        setSpacing(true);
        statusField = new ComboBox("Order status");
        lineItemsField = new LineItemField("Line items");
        addComponents(statusField, lineItemsField);

        statusField.setNullSelectionAllowed(false);
        statusField.setRequired(true);
        Arrays.asList(Order.Status.values()).forEach(statusField::addItem);

        lineItemsField.setRequired(true);
        lineItemsField.addValidator(new NonEmptyCollectionValidator("An order must contain products"));
    }

}
