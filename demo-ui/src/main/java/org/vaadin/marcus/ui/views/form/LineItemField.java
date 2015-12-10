package org.vaadin.marcus.ui.views.form;

import com.google.common.collect.Lists;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.*;
import org.vaadin.marcus.entity.LineItem;
import org.vaadin.marcus.service.OrderService;
import org.vaadin.marcus.util.FieldGroupUtil;
import org.vaadin.marcus.util.MyTheme;
import org.vaadin.marcus.util.converter.PercentageConverter;

import java.util.List;

public class LineItemField extends CustomField<List> {

    private Grid lineItemsGrid;
    private BeanItemContainer<LineItem> container;
    private boolean spacing = true;

    public LineItemField(String caption) {
        setCaption(caption);
    }

    @Override
    protected Component initContent() {
        setWidth("100%");

        VerticalLayout rootLayout = new VerticalLayout();
        LineItemForm lineItemForm = new LineItemForm();
        lineItemsGrid = new Grid();
        rootLayout.addComponents(lineItemForm, lineItemsGrid);

        rootLayout.setWidth("100%");
        rootLayout.setSpacing(spacing);

        lineItemsGrid.setWidth("100%");
        container = new BeanItemContainer<>(LineItem.class, Lists.newArrayList());
        lineItemsGrid.setContainerDataSource(container);
        lineItemsGrid.setColumnOrder("product", "quantity", "discount");

        return rootLayout;
    }


    @Override
    protected void setInternalValue(List newValue) {
        // We override set internal value to get the internal value of the field to show in the Grid.
        if (lineItemsGrid != null) {
            container = new BeanItemContainer<>(LineItem.class, (List<LineItem>) newValue);
            lineItemsGrid.setContainerDataSource(container);
        }
        super.setInternalValue(newValue);
    }

    private class LineItemForm extends HorizontalLayout {
        private ComboBox product;
        private TextField quantity;
        private TextField discount;
        private BeanFieldGroup<LineItem> fieldGroup;
        private Button addButton;

        public LineItemForm() {
            setSpacing(spacing);
            setWidth("100%");

            initLayout();
            initFieldGroup();
        }

        private void initFieldGroup() {
            fieldGroup = new BeanFieldGroup<>(LineItem.class);
            fieldGroup.bindMemberFields(this);
            FieldGroupUtil.improveUX(fieldGroup, addButton, null);
            resetDataSource();
        }

        private void resetDataSource() {
            fieldGroup.setItemDataSource(new BeanItem<>(new LineItem()));
        }

        private void initLayout() {
            // Note that our field names are equal to the propertyIds of our item
            product = new ComboBox("Product");
            quantity = new TextField("Quantity");
            discount = new TextField("Discount");
            addButton = new Button("Add", click -> add());
            addComponents(product, quantity, discount, addButton);

            OrderService.get().getProducts().forEach(product::addItem);
            product.setWidth("100%");
            product.setRequired(true);
            product.setNullSelectionAllowed(false);

            quantity.setWidth("80px");
            quantity.setRequired(true);
            quantity.addValidator(new IntegerRangeValidator("Must be between 1 and 1", 1, 10));

            discount.setWidth("80px");
            discount.setRequired(true);
            discount.addValidator(new DoubleRangeValidator("Must be between 0 and 50%", 0.0, 0.5));
            discount.setConverter(new PercentageConverter());

            addButton.addStyleName(MyTheme.BUTTON_PRIMARY);

            setComponentAlignment(addButton, Alignment.BOTTOM_RIGHT);
            setExpandRatio(product, 1);
        }

        private void add(){
            try {
                fieldGroup.commit();
                addLineItem(fieldGroup.getItemDataSource().getBean());
                resetDataSource();
            } catch (FieldGroup.CommitException e) {
                Notification.show("Check your input");
            }
        }

    }

    private void addLineItem(LineItem lineItem) {
        List<LineItem> currentLineItems = Lists.newArrayList(getValue());
        currentLineItems.add(lineItem);

        // Call set value to ensure that all listeners get triggered
        setValue(currentLineItems);
    }

    @Override
    public Class<? extends List> getType() {
        return List.class;
    }

}
