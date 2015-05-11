package org.vaadin.marcus.ui.views.render;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import org.joda.time.format.DateTimeFormat;
import org.vaadin.marcus.entity.Order;
import org.vaadin.marcus.util.MyTheme;

import java.text.NumberFormat;

public class QuickOrderLayout extends CssLayout {

    public QuickOrderLayout(Order order) {
        addStyleName(MyTheme.ORDER_LAYOUT);
        setWidth("100%");
        Label orderIdLabel = new Label("Order " + order.getId());
        Label orderTime = new Label();
        Label lineItems = new Label();
        Label orderTotal = new Label();
        addComponents(orderIdLabel, orderTime, lineItems, orderTotal);

        orderIdLabel.addStyleName(MyTheme.ORDER_ID);

        orderTime.setWidth("30%");
        lineItems.setWidth("55%");
        orderTotal.setWidth("15%");

        orderTime.setValue(order.getOrderTime().toString(DateTimeFormat.mediumDateTime()));
        orderTime.setSizeUndefined();
        orderTime.addStyleName(MyTheme.LABEL_BOLD);

        lineItems.setContentMode(ContentMode.HTML);
        StringBuilder lineItemsContent = new StringBuilder();
        lineItemsContent.append("<b>Line items:</b><br/>");

        order.getLineItems().forEach(lineItem -> {
            lineItemsContent.append(lineItem.getQuantity());
            lineItemsContent.append(" ");
            lineItemsContent.append(lineItem.getProduct().getName());
            lineItemsContent.append(" – ");
            lineItemsContent.append(NumberFormat.getCurrencyInstance().format(lineItem.getProduct().getPrice()));
            lineItemsContent.append("<br/>");
        });
        lineItems.setValue(lineItemsContent.toString());

        orderTotal.setSizeUndefined();
        orderTotal.setValue(NumberFormat.getCurrencyInstance().format(order.getOrderTotal()));
        orderTotal.addStyleName(MyTheme.LABEL_BOLD);
        orderTotal.addStyleName(MyTheme.ORDER_TOTAL);


    }
}
